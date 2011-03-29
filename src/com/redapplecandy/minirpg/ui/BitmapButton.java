package com.redapplecandy.minirpg.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View.OnClickListener;

public class BitmapButton extends InvisibleButton {

	private Bitmap m_bitmap;
	
	public BitmapButton(Bitmap bmp, int x, int y) {
		super(x, y);
		m_bitmap = bmp;
		
		m_width = bmp.getWidth();
		m_height = bmp.getWidth();
	}
	
	public BitmapButton(Bitmap bmp, int x, int y, int padding) {
		super(x, y, padding);
		m_bitmap = bmp;
		
		m_width = bmp.getWidth();
		m_height = bmp.getWidth();
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(m_bitmap, m_x, m_y, null);
	}
	
}
