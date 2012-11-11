package com.redapplecandy.minirpg.ui;

import com.redapplecandy.minirpg.Config;
import com.redapplecandy.minirpg.Core;
import com.redapplecandy.minirpg.util.MathUtils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class MessageBox {

	private String m_message = "";
	
	private String[] m_buffer;
	
	private int m_messageIndex = 0;
	private int m_bufferIndex = 0;
	
	private int m_x, m_y;
	
	private boolean m_visible = false;
	
	public MessageBox() {
		m_buffer = new String[4];
		clear();
	}
	
	public void show(int x, int y) {
		m_x = x;
		m_y = y;
		
		m_visible = true;
	}
	
	public void hide() {
		m_visible = false;
	}
	
	public boolean visible() {
		return m_visible;
	}
	
	public boolean done() {
		return m_messageIndex == m_message.length();
	}
	
	public void advance() {
		if (done()) return;
		
		if (m_message.charAt(m_messageIndex) == '\n') {
			m_bufferIndex++;
			m_messageIndex++;
			advance();
		} else {
			m_buffer[m_bufferIndex] += m_message.charAt(m_messageIndex);
			m_messageIndex++;
		}
	}
	
	public void setMessage(String message) {
		clear();
		m_message = message;
		/*
		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) == '\n') {
				m_bufferIndex++;
			} else {
				m_buffer[m_bufferIndex] += message.charAt(i);
			}
		}
		*/
	}
	
	public void clear() {
		for (int i = 0; i < m_buffer.length; i++) {
			m_buffer[i] = "";
		}
		m_message = "";
		m_messageIndex = 0;
		m_bufferIndex = 0;
	}
	
	public void draw(Canvas canvas) {
		int cw = canvas.getWidth(), ch = canvas.getHeight();
		
		Paint paint = new Paint();
		paint.setARGB(255, 255, 255, 255);
		canvas.drawRect(
			MathUtils.scaleX(m_x, cw), MathUtils.scaleY(m_y, ch), 
			MathUtils.scaleX(m_x + Config.MAIN_WINDOW_WIDTH, cw), 
			MathUtils.scaleY(m_y + Config.MESSAGE_BOX_HEIGHT, ch), 
			paint);
		
		Paint paint2 = new Paint();
		paint2.setARGB(255, 0, 0, 0);
		canvas.drawRect(
			MathUtils.scaleX(4 + m_x, cw), 
			MathUtils.scaleY(m_y + 4, ch), 
			MathUtils.scaleX(m_x + Config.MAIN_WINDOW_WIDTH - 4, cw), 
			MathUtils.scaleY(m_y + Config.MESSAGE_BOX_HEIGHT - 4, ch), 
			paint2);
		
		Paint textPaint = new Paint();
		textPaint.setARGB(255, 255, 255, 255);
		Typeface typeface = Typeface.SANS_SERIF;
		textPaint.setTypeface(typeface);
		for (int i = 0; i < m_buffer.length; i++) {
			canvas.drawText(m_buffer[i], 
				MathUtils.scaleX(m_x + 8, cw),
				MathUtils.scaleY(m_y + (i+1)*16, ch),
				textPaint);
//			Text.drawText(
//					canvas,
//					m_buffer[i], 
//					m_x + 8, m_y + (i+1)*16,
//					R.drawable.font_8x8);
		}
	}
}
