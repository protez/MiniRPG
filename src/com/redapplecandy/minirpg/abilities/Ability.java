package com.redapplecandy.minirpg.abilities;

import com.redapplecandy.minirpg.battle.Battle;
import com.redapplecandy.minirpg.battle.BattleEntity;

/**
 * An abstract <i>ability</i> class. Each ability should inherit
 * from this class. Some abilities can be used both in battles and
 * outside, some only in battles and some only in the field. Abilities
 * can target either entire groups or a single target. Each ability can
 * target both enemies and allies.
 * @author tomas
 */
public abstract class Ability {

	protected int m_spCost;
	
	public Ability() {
		this(0);
	}
	
	public Ability(int spCost) {
		m_spCost = spCost;
	}
	
	/**
	 * "Execute" this ability, causing its effect to be applied in a battle or
	 * in the field, depending on the arguments.
	 * @param battle	The battle this ability is used in. null if used in field.
	 * @param target	The target of the ability. Null to target entire group
	 * 					of allies or enemies, depending on the ability.
	 */
	public abstract void execute(Battle battle, BattleEntity target);
	
	public int getSpCost() {
		return m_spCost;
	}
}
