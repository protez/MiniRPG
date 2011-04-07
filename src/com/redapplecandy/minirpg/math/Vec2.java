package com.redapplecandy.minirpg.math;

public class Vec2 implements Cloneable {

	public float x, y;
	
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	
	public Vec2 clone() {
		return new Vec2(x, y);
	}
}
