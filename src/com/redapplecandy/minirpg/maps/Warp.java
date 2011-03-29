package com.redapplecandy.minirpg.maps;

public class Warp {

	private TileMap m_targetMap;
	private int m_targetX, m_targetY;
	
	public Warp(TileMap targetMap, int targetX, int targetY) {
		m_targetMap = targetMap;
		m_targetX = targetX;
		m_targetY = targetY;
	}
	
	
	
}
