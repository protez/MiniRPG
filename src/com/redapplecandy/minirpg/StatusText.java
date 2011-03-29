package com.redapplecandy.minirpg;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class StatusText {
	public static String statusText = "Im cool";
	
	public static void draw(Canvas canvas) {
		Paint textPaint = new Paint();
		textPaint.setARGB(255, 255, 255, 255);
		Typeface typeface = Typeface.create("Helvetica", Typeface.NORMAL);
		textPaint.setTypeface(typeface);
		canvas.drawText(statusText, 4, Config.STATUS_BAR_POS, textPaint);
		//
	}
}
