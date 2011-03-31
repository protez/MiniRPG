package com.redapplecandy.minirpg;

/**
 * A "camera" class. Just keeps track of a grid coordinate
 * and a direction.
 * @author tomas
 */
public class Camera {

	public int x, y, direction;
	
	public Camera() {
		this(0, 0, Direction.RIGHT);
	}
	
	public Camera(int x, int y, int direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
}
