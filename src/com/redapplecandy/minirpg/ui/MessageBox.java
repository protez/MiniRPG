package com.redapplecandy.minirpg.ui;

import com.redapplecandy.minirpg.Config;
import com.redapplecandy.minirpg.Core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class MessageBox {

	private String m_message = "";
	
	private String[] m_buffer;
	
	private int m_messageIndex = 0;
	private int m_bufferIndex = 0;
	
	public MessageBox() {
		m_buffer = new String[4];
		clear();
	}
	
	public void advance() {
		if (m_message.charAt(m_messageIndex) == '\n') {
			m_bufferIndex++;
			m_messageIndex++;
			// Recursively call this to add the character after the
			// newline.
			advance();
		} else {
			m_buffer[m_bufferIndex] += m_message.charAt(m_messageIndex);
			m_messageIndex++;
		}
		
		/*
		if (m_messageIndex == m_message.length()) {
			Core.instance().setState(Core.STATE_WAIT_MESSAGE);
		}
		*/
	}
	
	public boolean done() {
		return m_messageIndex == m_message.length();
	}
	
	public void setMessage(String message) {
		clear();
		m_message = message;
		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) == '\n') {
				m_bufferIndex++;
			} else {
				m_buffer[m_bufferIndex] += message.charAt(m_bufferIndex);
			}
		}
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
		
		Paint textPaint = new Paint();
		textPaint.setARGB(255, 255, 255, 255);
		Typeface typeface = Typeface.create("Helvetica", Typeface.NORMAL);
		textPaint.setTypeface(typeface);
		for (int i = 0; i < m_buffer.length; i++) {
			canvas.drawText(m_buffer[i], 8,
				Config.MAIN_WINDOW_HEIGHT + i*16,
				textPaint);
		}
	}
}
