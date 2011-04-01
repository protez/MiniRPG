package com.redapplecandy.minirpg.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.redapplecandy.minirpg.Config;
import com.redapplecandy.minirpg.character.PlayerCharacter;

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
	 * 			window and message box.
	 */
	public CharacterWidget(PlayerCharacter chr, int x, int y) {
		super(x, y + Config.MAIN_WINDOW_HEIGHT + Config.MESSAGE_BOX_HEIGHT + 4, 0, 72, 60);
		m_chr = chr;
	}
	
	public void draw(Canvas canvas) {
		Paint outline = new Paint();
		outline.setARGB(255, 255, 255, 255);
		canvas.drawRect(m_x, 
			m_y, 
			m_x + m_width, m_y + m_height, outline);
		
		Paint fill = new Paint();
		fill.setARGB(255, 0, 0, 0);
		canvas.drawRect(m_x + 4,
				m_y + 4,
				m_x + m_width - 4, m_y + m_height - 4, fill);
		
		Paint textPaint = new Paint();
		Typeface typeface = Typeface.create("Helvetica", Typeface.NORMAL);
		textPaint.setTypeface(typeface);
		textPaint.setARGB(255, 255, 255, 255);
		
		canvas.drawText(m_chr.name(), m_x + 6, m_y + 16, textPaint);
		
		Integer curHp = m_chr.currentHp();
		Integer maxHp = m_chr.maxHp();
		
		canvas.drawText("HP " + curHp.toString() + "/" + maxHp.toString(), m_x + 6, m_y + 32, textPaint);
		canvas.drawText("SP " + curHp.toString() + "/" + maxHp.toString(), m_x + 6, m_y + 48, textPaint);
		
	}
	
}
