package com.redapplecandy.minirpg.ui;

import com.redapplecandy.minirpg.Config;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View.OnClickListener;

public class InvisibleButton {
	protected int m_x, m_y;
	protected int m_padding = 0;
	protected OnClickListener m_clickListener;
	protected int m_width, m_height;
	
	public InvisibleButton() {
		
	}
	
	public InvisibleButton(int x, int y) {
		m_x = x;
		m_y = y;
	}
	
	public InvisibleButton(int x, int y, int padding) {
		this(x, y);
		m_padding = padding;
	}
	
	public InvisibleButton(int x, int y, int padding, int w, int h) {
		this(x, y, padding);
		m_width = w;
		m_height = h;
		/*
		debugColor = debugColors[debugColorIndex];
		debugColorIndex++;
		if (debugColorIndex > debugColors.length) {
			debugColorIndex = 0;
		}
		*/
	}
	
	public int x() {
		return m_x;
	}
	
	public int y() {
		return m_y;
	}
	
	public int width() {
		return m_width;
	}
	
	public int height() {
		return m_height;
	}
	
	public boolean isClicked(int x, int y) {
		if (x >= m_x - m_padding && x <= m_x + width() + m_padding
			&& y >= m_y - m_padding && y <= m_y + height() + m_padding)
		{
			return true;
		}
		return false;
	}
	
	public void fireClick() {
		m_clickListener.onClick(null);
	}
	
	public void setOnClickListener(OnClickListener listener) {
		m_clickListener = listener;
	}
	/*
	private static int[] debugColors = {0xffff0000, 0xff00ff00, 0xff0000ff, 0xffffff00 };
	private static int debugColorIndex = 0;
	private int debugColor;
	*/
	public void debugDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(0xffff0000);
		canvas.drawRect(
			m_x, m_y, 
			m_x + m_width, m_y + m_height, 
			paint);
	}
}
