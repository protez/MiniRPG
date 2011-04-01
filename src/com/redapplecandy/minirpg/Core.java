package com.redapplecandy.minirpg;

import com.redapplecandy.minirpg.character.Party;
import com.redapplecandy.minirpg.maps.Level;

public class Core {

	public static final int
		STATE_WALK_AROUND = 0,
		STATE_SHOW_MESSAGE = 1,
		STATE_WAIT_MESSAGE = 2,
		STATE_CHARACTER_SHEET = 3;
	
	public int currentState = STATE_WALK_AROUND;
		
	private static Core m_instance = null;
	private RenderView m_renderView;
	private Party m_playerParty;
	
	private Camera m_camera;
	private Level m_currentLevel;
	
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
	
}
