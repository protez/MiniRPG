package com.redapplecandy.minirpg.graphics;

import java.util.HashMap;

import com.redapplecandy.minirpg.BitmapLoader;
import com.redapplecandy.minirpg.Pair;
import com.redapplecandy.minirpg.util.MathUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Text {

	private Matrix m_drawMatrix = new Matrix();
	
	private Bitmap m_font;
	private String m_text = "";
	
	private int m_charW, m_charH;
	
	private HashMap<Character, Bitmap> m_subBitmaps = new HashMap<Character, Bitmap>();
	
	public Text(int fontId) {
		m_font = BitmapLoader.instance().loadById(fontId);
		
		// Assumes that the font is 32 characters wide and 8 characters tall.
		m_charW = m_font.getWidth() / 32;
		m_charH = m_font.getHeight() / 8;
	}
	
	public void setText(String text) {
		m_text = text;
	}
	
	public void draw(Canvas canvas, int x, int y) {
		int realY = y;
		
		Pair<Float, Float> scale = MathUtils.scaleFactor(canvas.getWidth(), canvas.getHeight());
		
		int offset = 0;
		for (int i = 0; i < m_text.length(); i++) {
			char chr = m_text.charAt(i);
			
			if (chr != '\n') {			
				int posX = chr % (m_font.getWidth() / m_charW);
				int posY = chr / (m_font.getWidth() / m_charW);
				
				Bitmap character = null;
				
				// Cache the individual characters in the font.
				if (m_subBitmaps.containsKey(chr)) {
					character = m_subBitmaps.get(chr);
				} else {
					character = Bitmap.createBitmap(m_font, posX * m_charW, posY * m_charH, m_charW, m_charH);
					m_subBitmaps.put(chr, character);
				}
				
				m_drawMatrix.setTranslate(
						MathUtils.scaleX(x + offset * m_charW, canvas.getWidth()),
						MathUtils.scaleY(realY, canvas.getHeight()));
				m_drawMatrix.postScale(scale.fst, scale.snd);
				
				canvas.drawBitmap(character, m_drawMatrix, null);
				
				offset++;
			} else {
				realY += m_charH;
				offset = 0;
			}
		}
	}
	
	public static void drawText(Canvas canvas, String str, int x, int y, int fontId) {
		Text text = new Text(fontId);
		text.setText(str);
		text.draw(canvas, x, y);
	}
}
