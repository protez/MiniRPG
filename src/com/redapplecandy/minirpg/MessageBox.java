package com.redapplecandy.minirpg;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class MessageBox {

	private String m_message = "Lorem ipsum";
	
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setARGB(255, 255, 255, 255);
		canvas.drawRect(
			0, Config.MAIN_WINDOW_HEIGHT, 
			Config.MAIN_WINDOW_WIDTH, Config.MAIN_WINDOW_HEIGHT + Config.MESSAGE_BOX_HEIGHT, 
			paint);
		
		Paint paint2 = new Paint();
		paint2.setARGB(255, 0, 0, 0);
		canvas.drawRect(
			4, Config.MAIN_WINDOW_HEIGHT + 4, 
			Config.MAIN_WINDOW_WIDTH - 4, Config.MAIN_WINDOW_HEIGHT + Config.MESSAGE_BOX_HEIGHT - 4, 
			paint2);
		
		// TODO: More "advanced" text rendering.
		Paint textPaint = new Paint();
		textPaint.setARGB(255, 255, 255, 255);
		Typeface typeface = Typeface.create("Helvetica", Typeface.NORMAL);
		textPaint.setTypeface(typeface);
		canvas.drawText(m_message, 8, Config.MAIN_WINDOW_HEIGHT + 16, textPaint);
	}
}
