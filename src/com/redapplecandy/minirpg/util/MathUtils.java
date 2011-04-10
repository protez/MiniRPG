package com.redapplecandy.minirpg.util;

import com.redapplecandy.minirpg.Config;
import com.redapplecandy.minirpg.Pair;

public class MathUtils {

	private static Pair<Float, Float> m_scaleFactor = null;
	
	public static float deg2rad(float deg) {
		return (float) (deg * Math.PI / 180.0f);
	}
	
	/**
	 * Returns a value with which every graphical object must be
	 * scaled by, depending on the screen resolution.
	 * @param w	The <i>current</i> width of the screen.
	 * @param h The <i>current</i> height of the screen.
	 * @return	A Pair<Float, Float> containing the values that
	 * 			the x and y values must be scaled with.
	 */
	public static Pair<Float, Float> scaleFactor(int w, int h) {
		if (m_scaleFactor == null) {
			float scaleW = (float)w / (float)Config.BASE_WIDTH;
			float scaleH = (float)h / (float)Config.BASE_HEIGHT;
			
			m_scaleFactor = new Pair<Float, Float>(scaleW, scaleH);
			
		}
		return m_scaleFactor;
	}
	
	public static int scale(int value, float factor) {
		return (int)(value * factor);
	}
	
	public static int scaleX(int value, int realW) {
		float scaleW = (float)realW / (float)Config.BASE_WIDTH;
		return (int)(value * scaleW);
	}
	
	public static int scaleY(int value, int realH) {
		float scaleH = (float)realH / (float)Config.BASE_HEIGHT;
		return (int)(value * scaleH);
	}
	
}
