package com.redapplecandy.minirpg.monster;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.redapplecandy.minirpg.battle.BattleEntity;

public class Monster extends BattleEntity {
	
	private Bitmap m_sprite;
	
	private Monster() {
		
	}
	
	public void draw(Canvas canvas, int x, int y) {
		canvas.drawBitmap(m_sprite, x, y, null);
	}

	
	public Bitmap getSprite() {
		return m_sprite;
	}
}
