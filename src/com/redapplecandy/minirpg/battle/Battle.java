package com.redapplecandy.minirpg.battle;

import java.util.PriorityQueue;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.redapplecandy.minirpg.Config;
import com.redapplecandy.minirpg.character.Party;
import com.redapplecandy.minirpg.monster.Monster;
import com.redapplecandy.minirpg.util.BattlerComparator;

public class Battle {

	private Bitmap m_background;
	private PriorityQueue<BattleEntity> m_battleQueue;
	
	private Party m_playerParty, m_monsterParty;
	
	public Battle(Party playerParty, Party monsterParty) {
		m_battleQueue = new PriorityQueue<BattleEntity>(4, new BattlerComparator());
		
		m_playerParty = playerParty;
		m_monsterParty = monsterParty;
	}
	
	/**
	 * Draw the battle scene.
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		canvas.drawBitmap(m_background, Config.VIEW_CORNER_X, Config.VIEW_CORNER_Y, null);
		
		int lastMonsterLength = 0;
		for (int i = 0; i < m_monsterParty.size(); i++) {
			Monster monster = (Monster) m_monsterParty.getMember(i);
			monster.draw(canvas, 
				4 + Config.VIEW_CORNER_X + lastMonsterLength, 
				Config.VIEW_CORNER_Y + Config.TILE_HEIGHT / 2);
			lastMonsterLength = monster.getSprite().getWidth();
		}
		
	}
	
}
