package com.redapplecandy.minirpg.ui;

import com.redapplecandy.minirpg.Config;
import com.redapplecandy.minirpg.util.MathUtils;

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
	}
	
	public InvisibleButton(int x, int y, int w, int h) {
		this(x, y, 0);
		m_width = w;
		m_height = h;
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
		int cw = Config.REAL_WIDTH, ch = Config.REAL_HEIGHT;
		if (x >= MathUtils.scaleX(m_x - m_padding, cw) 
			&& x <= MathUtils.scaleX(m_x + width() + m_padding, cw)
			&& y >= MathUtils.scaleY(m_y - m_padding, ch) 
			&& y <= MathUtils.scaleY(m_y + height() + m_padding, ch))
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

	public void debugDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(0xffff0000);
		canvas.drawRect(
			m_x, m_y, 
			m_x + m_width, m_y + m_height, 
			paint);
	}
}
