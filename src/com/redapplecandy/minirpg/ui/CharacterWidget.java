package com.redapplecandy.minirpg.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.redapplecandy.minirpg.Config;
import com.redapplecandy.minirpg.character.PlayerCharacter;
import com.redapplecandy.minirpg.util.MathUtils;

/**
 * A small rectangular widget displaying the name, hp
 * and sp of a character in the party. Should also be
 * clickable for targeting purposes etc etc.
 * @author tomas
 */
public class CharacterWidget extends InvisibleButton {

	private PlayerCharacter m_chr;
	
	/**
	 * Constructor
	 * @param chr
	 * @param x
	 * @param y	This will automatically be scaled to be placed under the main
	 * 			window.
	 */
	public CharacterWidget(PlayerCharacter chr, int x, int y) {
		super(x, y + Config.STATUS_BAR_POS + 4, 80, 60);
		m_chr = chr;
	}
	
	public void draw(Canvas canvas) {
		int cw = canvas.getWidth();
		int ch = canvas.getHeight();
		
		Paint outline = new Paint();
		outline.setARGB(255, 255, 255, 255);
		canvas.drawRect(MathUtils.scaleX(m_x, cw), 
			MathUtils.scaleY(m_y, ch), 
			MathUtils.scaleX(m_x + m_width, cw), 
			MathUtils.scaleY(m_y + m_height, ch), outline);
		
		Paint fill = new Paint();
		fill.setARGB(255, 0, 0, 0);
		canvas.drawRect(MathUtils.scaleX(m_x + 4, cw),
				MathUtils.scaleY(m_y + 4, ch),
				MathUtils.scaleX(m_x + m_width - 4, cw), 
				MathUtils.scaleY(m_y + m_height - 4, ch), fill);
		
		Paint textPaint = new Paint();
		Typeface typeface = Typeface.create("Helvetica", Typeface.NORMAL);
		textPaint.setTypeface(typeface);
		textPaint.setARGB(255, 255, 255, 255);
		
		canvas.drawText(m_chr.name(), 
			MathUtils.scaleX(m_x + 6, cw), 
			MathUtils.scaleY(m_y + 16, ch), textPaint);
		
		Integer curHp = m_chr.currentHp();
		Integer maxHp = m_chr.maxHp();
		
		canvas.drawText("HP " + curHp.toString() + "/" + maxHp.toString(), 
			MathUtils.scaleX(m_x + 6, cw), 
			MathUtils.scaleY(m_y + 32, ch), textPaint);
		canvas.drawText("SP " + curHp.toString() + "/" + maxHp.toString(), 
			MathUtils.scaleX(m_x + 6, cw), 
			MathUtils.scaleY(m_y + 48, ch), textPaint);
		
	}
	
}
