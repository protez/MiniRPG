package com.redapplecandy.minirpg;

import com.redapplecandy.minirpg.util.MathUtils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class StatusText {
	public static String statusText = "Im cool";
	
	public static void draw(Canvas canvas) {
		Paint textPaint = new Paint();
		textPaint.setARGB(255, 255, 255, 255);
		Typeface typeface = Typeface.SANS_SERIF;
		textPaint.setTypeface(typeface);
		canvas.drawText(statusText, 
			MathUtils.scaleX(Config.VIEW_CORNER_X + 4, canvas.getWidth()), 
			MathUtils.scaleY(Config.STATUS_BAR_POS, canvas.getHeight()), 
			textPaint);
	}
}
