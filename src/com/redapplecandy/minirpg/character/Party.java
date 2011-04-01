package com.redapplecandy.minirpg.character;

import com.redapplecandy.minirpg.battle.BattleEntity;

public class Party {

	private int m_gold;
	private BattleEntity[] m_members;
	
	private Party() {
		m_members = new BattleEntity[4];
	}
	
	public BattleEntity getMember(int index) throws IndexOutOfBoundsException {
		if (index > m_members.length) {
			throw new IndexOutOfBoundsException();
		}
		
		return m_members[index];
	}
	
	public int size() {
		return m_members.length;
	}
	
	public static Party createTestPart() {
		Party testParty = new Party();
		for (int i = 0; i < 4; i++) {
			testParty.m_members[i] = PlayerCharacter.createTestCharacter();
		}
		testParty.m_gold = 200;
		return testParty;
	}
	
}
