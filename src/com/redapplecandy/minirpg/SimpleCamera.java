package com.redapplecandy.minirpg;

/**
 * A "camera" class. Just keeps track of a grid coordinate
 * and a direction.
 * @author tomas
 */
public class SimpleCamera {

	public int x, y, direction;
	
	public SimpleCamera() {
		this(0, 0, Direction.RIGHT);
	}
	
	public SimpleCamera(int x, int y, int direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
}
