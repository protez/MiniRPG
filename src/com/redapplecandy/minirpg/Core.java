package com.redapplecandy.minirpg;

import android.os.SystemClock;

import com.redapplecandy.minirpg.character.Party;
import com.redapplecandy.minirpg.graphics.Camera;
import com.redapplecandy.minirpg.maps.Level;
import com.redapplecandy.minirpg.math.Vec2;
import com.redapplecandy.minirpg.ui.MessageBox;
import com.redapplecandy.minirpg.util.MathUtils;

public class Core {

	public static final int
		STATE_WALK_AROUND = 0,
		STATE_SHOW_MESSAGE = 1,
		STATE_WAIT_MESSAGE = 2,
		STATE_CHARACTER_SHEET = 3,
		STATE_MOVING = 4,
		STATE_TURNING = 5;
	
	private int m_accumulatedAngle = 0;
	private int m_angleTarget = 0;
	private int m_angleInc = 0;
	private float m_degInc = 0;
	private Vec2 m_targetPos;
	private float m_walkAccumulator = 0.0f;
	private float m_walkSpeed = 0.0f;
	
	/**
	 * Use later.
	 */
	private int m_savedState = STATE_WALK_AROUND;
	
	private int m_currentState = STATE_WALK_AROUND;
		
	private static Core m_instance = null;
	private RenderView m_renderView;
	private Party m_playerParty;
	
	private Camera m_camera;
	
	private SimpleCamera m_simpleCamera;
	private Level m_currentLevel;
	
	private MessageBox m_messageBox = new MessageBox();
	
	private Core() {
		m_simpleCamera = new SimpleCamera(2, 2, Direction.RIGHT);
		
		m_camera = new Camera(new Vec2(4.5f, 4.5f), new Vec2(-1, 0), new Vec2(0, 0.66f));
		
		m_currentLevel = new Level();
	}
	
	public static Core instance() {
		if (m_instance == null) {
			m_instance = new Core();
		}
		return m_instance;
	}
	
	public void setRenderView(RenderView view) {
		m_renderView = view;
	}
	
	public void setPlayerParty(Party party) {
		m_playerParty = party;
	}
	
	public Level currentLevel() {
		return m_currentLevel;
	}
	
	public SimpleCamera getSimpleCamera() {
		return m_simpleCamera;
	}
	
	public Camera camera() {
		return m_camera;
	}
	
	public void setState(int state) {
		m_currentState = state;
		// TODO: Stuff that happens when the state changes.
		
		if (state == Core.STATE_SHOW_MESSAGE) {
			m_messageBox.show(0, Config.MAIN_WINDOW_HEIGHT - Config.MESSAGE_BOX_HEIGHT);
			setState(Core.STATE_WAIT_MESSAGE);
		}
		
	}
	
	public int currentState() {
		return m_currentState;
	}
	
	
	public MessageBox getMessageBox() {
		return m_messageBox;
	}
	
	public void startMessage(String message) {
		m_messageBox.setMessage(message);
		setState(Core.STATE_SHOW_MESSAGE);
	}
	
	public void step(float speed) {
		m_walkSpeed = speed;
		
		setState(Core.STATE_MOVING);
		m_targetPos = m_camera.pos.clone();
		if (speed > 0) {
			m_targetPos.x += m_camera.dir.x;
			m_targetPos.y += m_camera.dir.y;
		} else {
			m_targetPos.x -= m_camera.dir.x;
			m_targetPos.y -= m_camera.dir.y;
		}
		
		m_walkAccumulator = 0;
	}
	
	public void incStep() {
		
		m_camera.pos.x += m_camera.dir.x * m_walkSpeed;
		m_camera.pos.y += m_camera.dir.y * m_walkSpeed;
		m_walkAccumulator += m_walkSpeed;
		
		//if (m_targetPos.equals(m_camera.pos)) {
		if (m_walkAccumulator >= 1) {
			// Adjust
			m_camera.pos.x = m_targetPos.x;
			m_camera.pos.y = m_targetPos.y;
			
			setState(Core.STATE_WALK_AROUND);
		}
	}
	
	public void turn(int angles, int inc) {
		setState(Core.STATE_TURNING);
		m_accumulatedAngle = 0;
		m_angleTarget = angles;
		m_angleInc = inc;
		m_degInc = MathUtils.deg2rad(inc);
	}
	
	public void incAngle() {
		Vec2 dir = m_camera.dir.clone();
		Vec2 plane = m_camera.plane.clone();
		
		m_camera.dir.x = (float) (dir.x * Math.cos(m_degInc) - dir.y * Math.sin(m_degInc));
		m_camera.dir.y = (float) (dir.x * Math.sin(m_degInc) + dir.y * Math.cos(m_degInc));
		m_camera.plane.x = (float) (plane.x * Math.cos(m_degInc) - plane.y * Math.sin(m_degInc));
		m_camera.plane.y = (float) (plane.x * Math.sin(m_degInc) + plane.y * Math.cos(m_degInc));
		
		m_accumulatedAngle += m_angleInc;
		if (Math.abs(m_accumulatedAngle) >= Math.abs(m_angleTarget)) {
			setState(Core.STATE_WALK_AROUND);
		}
	}
	
}
