package com.redapplecandy.minirpg;

import java.util.Vector;

import android.graphics.Bitmap;

/**
 * Loads the dungeon tiles and breaks them up into individual pieces.
 * @author Tomas Brännström
 */
public class TileManager {

	public static final int 
		NEAR_CORRIDOR_LEFT = 0,
		NEAR_INTERSECT_LEFT = 1,
		NEAR_WALL_LEFT = 2,
		NEAR_WALL_MIDDLE = 3,
		NEAR_WALL_RIGHT = 4,
		NEAR_INTERSECT_RIGHT = 5,
		NEAR_CORRIDOR_RIGHT = 6,
		FAR_EMPTY = 7, // ???
		FAR_WALL = 8,	// ???
		FAR_FAR_EMPTY = 9,	// ???
		FAR_FAR_WALL = 10,	// ???
		FAR_CORRIDOR_LEFT = 11,
		FAR_CORRIDOR_RIGHT = 12,
		FAR_CORRIDOR_WALL_LEFT = 13,
		FAR_CORRIDOR_WALL_RIGHT = 14,
		FAR_INTERSECT_LEFT = 15,
		FAR_INTERSECT_RIGHT = 16,
		FAR_INTERSECT_WALL_LEFT = 17,
		FAR_INTERSECT_WALL_RIGHT = 18;
	
	static private TileManager m_instance = null;
	
	private Bitmap m_dungeonTiles_far;
	private Bitmap m_dungeonTiles_near;
	
	private Vector<Bitmap> m_dungeonTiles;
	
	private TileManager() {
		
		m_dungeonTiles_far = BitmapLoader.instance().loadById(R.drawable.dungeon_far_hires);
		m_dungeonTiles_near = BitmapLoader.instance().loadById(R.drawable.dungeon_close_hires);
		
		m_dungeonTiles = new Vector<Bitmap>();
		
		for (int i = 0; i < 9; i++) {
			m_dungeonTiles.add(Bitmap.createBitmap(
				m_dungeonTiles_near, i * 96, 0, 96, 192
				));
		}
		for (int i = 0; i < 10; i++) {
			m_dungeonTiles.add(Bitmap.createBitmap(
				m_dungeonTiles_far, i * 48, 0, 48, 192
				));
		}
	}
	
	public Bitmap getTile(int index) {
		return m_dungeonTiles.get(index);
	}
	
	static public TileManager instance() {
		if (m_instance == null) {
			m_instance = new TileManager();
		}
		return m_instance;
	}
	
}
