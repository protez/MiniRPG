package com.redapplecandy.minirpg.math;

import com.redapplecandy.minirpg.util.MathUtils;

public class Vec2 implements Cloneable {

	public float x, y;
	
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vec2 clone() {
		return new Vec2(x, y);
	}
	
	public boolean equals(Vec2 rhs) {
		return Float.compare(rhs.x, x) == 0 && Float.compare(rhs.y, y) == 0;
	}
}
