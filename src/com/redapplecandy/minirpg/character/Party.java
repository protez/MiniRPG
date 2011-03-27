package com.redapplecandy.minirpg.character;

public class Party {

	private int m_gold;
	private Character[] m_members;
	
	private Party() {
		m_members = new Character[4];
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
