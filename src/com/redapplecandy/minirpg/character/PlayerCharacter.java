package com.redapplecandy.minirpg.character;

import com.redapplecandy.minirpg.Pair;
import com.redapplecandy.minirpg.battle.BattleEntity;
import com.redapplecandy.minirpg.items.Item;

public class PlayerCharacter extends BattleEntity {

	public static final int
		WEAPON_SLOT = 0,
		HELM_SLOT = 1,
		SHIELD_SLOT = 2,
		ARMOR_SLOT = 3,
		RING_SLOT = 4,
		MISC_SLOT = 5;
	

	private PlayerClass m_class;
	private Item[] m_inventory;
	private Item[] m_equipment;
	
	private PlayerCharacter() {
		m_inventory = new Item[6];
		m_equipment = new Item[6];
	}
	
	
	public static PlayerCharacter createTestCharacter() {
		PlayerCharacter c = new PlayerCharacter();
		
		c.m_name = "TestChar";
		c.m_hp = new Pair<Integer, Integer>(999, 999);
		c.m_sp = new Pair<Integer, Integer>(15, 15);
		
		c.m_strength = new Pair<Integer, Integer>(18, 18);
		c.m_endurance = new Pair<Integer, Integer>(15, 15);
		c.m_intellect = new Pair<Integer, Integer>(10, 10);
		c.m_luck = new Pair<Integer, Integer>(5, 5);
		c.m_speed = new Pair<Integer, Integer>(10, 10);
		
		c.m_level = 1;
		c.m_experience = 0;
		
		return c;
	}
}
