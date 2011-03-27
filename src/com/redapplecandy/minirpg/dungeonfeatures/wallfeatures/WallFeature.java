package com.redapplecandy.minirpg.dungeonfeatures.wallfeatures;

import com.redapplecandy.minirpg.BitmapLoader;
import com.redapplecandy.minirpg.Config;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Features that are set in walls, like doors
 * and torches. These features will have a
 * near and a close graphic, as well as a left
 * and a right graphic when they appear in walls
 * to the left and right of the player. They won't
 * be visible from far away if they are to the
 * right or left.
 * @author tomas
 */
public class WallFeature {

	public static final int
		LEFT = 0,
		NEAR_FRONT = 1,
		FAR_FRONT = 2,
		RIGHT = 3;
	
	protected Bitmap[] m_bitmaps;
	
	protected int m_x, m_y;
	
	/**
	 * The side of the wall this feature is on.
	 */
	protected int m_side;
	
	public WallFeature(int bitmapId) {
		m_bitmaps = new Bitmap[4];
		Bitmap featureBitmap = BitmapLoader.instance().loadById(bitmapId);
		for (int i = 0; i < 4; i++) {
			m_bitmaps[i] = Bitmap.createBitmap(featureBitmap, 
				i * Config.TILE_WIDTH, 0, 
				Config.TILE_WIDTH, Config.TILE_HEIGHT);
		}
	}
	
	public WallFeature(int bitmapId, int x, int y, int side) {
		this(bitmapId);
		m_x = x;
		m_y = y;
		m_side = side;
	}
	
	public void draw(Canvas canvas, int which) {
		if (which == NEAR_FRONT) {
			canvas.drawBitmap(m_bitmaps[NEAR_FRONT], 
				Config.VIEW_CORNER_X + Config.TILE_WIDTH, 
				Config.VIEW_CORNER_Y, null);
		} else if (which == FAR_FRONT) {
			canvas.drawBitmap(m_bitmaps[FAR_FRONT],
				Config.VIEW_CORNER_X + Config.TILE_WIDTH,
				Config.VIEW_CORNER_Y, null);
		} else if (which == LEFT) {
			canvas.drawBitmap(m_bitmaps[LEFT], Config.VIEW_CORNER_X, Config.VIEW_CORNER_Y, null);
		} else if (which == RIGHT) {
			canvas.drawBitmap(m_bitmaps[RIGHT], 
				Config.VIEW_CORNER_X + Config.TILE_WIDTH * 2, 
				Config.VIEW_CORNER_Y, null);
		}
	}
	
	public int x() {
		return m_x;
	}
	
	public int y() {
		return m_y;
	}
	
	public int side() {
		return m_side;
	}
}
