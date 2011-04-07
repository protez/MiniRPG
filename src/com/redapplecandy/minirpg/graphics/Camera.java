package com.redapplecandy.minirpg.graphics;

import com.redapplecandy.minirpg.math.Vec2;

public class Camera implements Cloneable {

	public Vec2 pos, dir, plane;
	
	public Camera(Vec2 pos, Vec2 dir, Vec2 plane) {
		this.pos = pos;
		this.dir = dir;
		this.plane = plane;
	}
	
	public Camera clone() {
		return new Camera(pos.clone(), dir.clone(), plane.clone());
	}
	
}
