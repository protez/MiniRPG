package com.redapplecandy.minirpg;

import android.os.SystemClock;

import com.redapplecandy.minirpg.character.Party;
import com.redapplecandy.minirpg.maps.Level;
import com.redapplecandy.minirpg.ui.MessageBox;

public class Core {

	public static final int
		STATE_WALK_AROUND = 0,
		STATE_SHOW_MESSAGE = 1,
		STATE_WAIT_MESSAGE = 2,
		STATE_CHARACTER_SHEET = 3;
	
	/**
	 * Use later.
	 */
	private int m_savedState = STATE_WALK_AROUND;
	
	private int m_currentState = STATE_WALK_AROUND;
		
	private static Core m_instance = null;
	private RenderView m_renderView;
	private Party m_playerParty;
	
	private Camera m_camera;
	private Level m_currentLevel;
	
	private MessageBox m_messageBox = new MessageBox();
	
	private Core() {
		m_camera = new Camera(2, 2, Direction.RIGHT);
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
	
}
