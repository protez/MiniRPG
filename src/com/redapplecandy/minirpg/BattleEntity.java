package com.redapplecandy.minirpg;

public abstract class BattleEntity {
	protected String m_name;
	protected Pair<Integer, Integer> 
		m_hp, m_sp, m_strength, m_endurance, m_intellect, m_luck, m_speed;
	protected int m_level, m_experience;
	
	
	public int currentSpeed() {
		return m_speed.fst;
	}
}
