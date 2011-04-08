package com.redapplecandy.minirpg.util;

public class MathUtils {

	public static float EPS = 1e-6f;
	
	public static float deg2rad(float deg) {
		return (float) (deg * Math.PI / 180.0f);
	}
	
	public static boolean eqFloats(float f1, float f2) {
		if (Math.abs(f1 - f2) <= EPS) {
			return true;
		}
		return false;
	}
	
}
