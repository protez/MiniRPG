package com.redapplecandy.minirpg.battle;

import java.util.PriorityQueue;

import com.redapplecandy.minirpg.BattleEntity;
import com.redapplecandy.minirpg.character.Party;
import com.redapplecandy.minirpg.util.BattlerComparator;

public class Battle {

	private PriorityQueue<BattleEntity> m_battleQueue;
	
	public Battle(Party playerParty, Party monsterParty) {
		m_battleQueue = new PriorityQueue<BattleEntity>(4, new BattlerComparator());
	}
	
}
