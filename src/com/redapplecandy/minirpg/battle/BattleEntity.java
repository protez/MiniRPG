package com.redapplecandy.minirpg.battle;

import com.redapplecandy.minirpg.Pair;

public abstract class BattleEntity {
	protected String m_name;
	protected Pair<Integer, Integer> 
		m_hp, m_sp, m_strength, m_endurance, m_intellect, m_luck, m_speed;
	protected int m_level, m_experience;
	
	
	public int currentSpeed() {
		return m_speed.fst;
	}
	
	public int currentHp() {
		return m_hp.fst;
	}
	
	public int maxHp() {
		return m_hp.snd;
	}
	
	public int currentSp() {
		return m_sp.fst;
	}
	
	public int maxSp() {
		return m_sp.snd;
	}
	
	public String name() {
		return m_name;
	}
	
}
