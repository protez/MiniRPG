package com.redapplecandy.minirpg.character;

import com.redapplecandy.minirpg.BattleEntity;

public class Party {

	private int m_gold;
	private BattleEntity[] m_members;
	
	private Party() {
		m_members = new BattleEntity[4];
	}
	
	static Party createTestPart() {
		Party testParty = new Party();
		for (int i = 0; i < 4; i++) {
			testParty.m_members[i] = Character.createTestCharacter();
		}
		testParty.m_gold = 200;
		return testParty;
	}
	
}
