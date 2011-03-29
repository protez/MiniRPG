package com.redapplecandy.minirpg.maps;

import java.util.Vector;

import com.redapplecandy.minirpg.Direction;
import com.redapplecandy.minirpg.TileManager;
import com.redapplecandy.minirpg.dungeonfeatures.wallfeatures.WallFeature;

import android.graphics.Canvas;
import android.graphics.Paint;

public class TileMap {

	private int[][] m_tiles;
	private Vector<WallFeature> m_wallFeatures;
	
	
	public TileMap() {
		
		m_wallFeatures = new Vector<WallFeature>();
		m_tiles = new int[8][8];
		
		int[][] tiles = {
				{1, 1, 1, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 1, 1, 1, 1},
				{1, 1, 0, 0, 0, 0, 1, 1},
				{1, 1, 0, 1, 0, 1, 1, 1},
				{1, 1, 0, 1, 0, 1, 1, 1},
				{1, 1, 0, 0, 0, 0, 1, 1},
				{1, 1, 1, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 1, 1, 1, 1}
		};
		
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				m_tiles[i][j] = tiles[i][j];
			}
		}
		
	}
	
	public Vector<Integer> getVisibleNearTiles(int srcX, int srcY, int dir) {
		Vector<Integer> tileIndices = new Vector<Integer>();
		
		int xdir = 0, ydir = 0;
		int xmul = 0, ymul = 0;
		boolean flip = false;
		
		if (dir == Direction.RIGHT) {
			xdir = 1;
			xmul = 0;
			ymul = 1;
			ydir = 0;
			flip = false;
		} else if (dir == Direction.LEFT) {
			xdir = -1;
			xmul = 0;
			ymul = 1;
			ydir = 0;
			flip = true;
		} else if (dir == Direction.UP) {
			xdir = 0;
			xmul = 1;
			ymul = 0;
			ydir = -1;
			flip = false;
		} else if (dir == Direction.DOWN) {
			xdir = 0;
			xmul = 1;
			ymul = 0;
			ydir = 1;
			flip = true;
		}
		
		// assume looking right for now
		// Far walls (scan three walls two steps in front of player)
		for (int i = -1; i <= 1; i++) {
			if (m_tiles[srcY + i*ymul + 2*ydir][srcX + i*xmul + 2*xdir] == 1) {
				tileIndices.add(TileManager.FAR_WALL);
			} else if (m_tiles[srcY + i*ymul + 2*ydir][srcX + i*xmul + 2*xdir] == 0) {
				tileIndices.add(TileManager.FAR_EMPTY);
			}
		}
		
		// Near walls (scan three walls in front of player)
		if (m_tiles[srcY + 1*ydir][srcX + 1*xdir] == 1) {
			// TODO: MIGHT CHANGE THIS LATER SO THAT ONLY ONE WALL IS VISIBLE
			// IF WE ONLY STAND INFRONT OF ONE SOLID TILE ETC.
			tileIndices.add(!flip ? TileManager.NEAR_WALL_LEFT : TileManager.NEAR_WALL_RIGHT);
			tileIndices.add(TileManager.NEAR_WALL_MIDDLE);
			tileIndices.add(!flip ? TileManager.NEAR_WALL_RIGHT : TileManager.NEAR_WALL_LEFT);
		}
		
		if (m_tiles[srcY - 1*ymul][srcX - 1*xmul] == 1) {
			// LEFT corridor
			tileIndices.add(!flip ? TileManager.NEAR_CORRIDOR_LEFT : TileManager.NEAR_CORRIDOR_RIGHT);
		}
		if (m_tiles[srcY + 1*ymul][srcX + 1*xmul] == 1) {
			// RIGHT corridor
			tileIndices.add(!flip ? TileManager.NEAR_CORRIDOR_RIGHT : TileManager.NEAR_CORRIDOR_LEFT);
		}
		
		if (m_tiles[srcY - 1*ymul][srcX - 1*xmul] == 0 && m_tiles[srcY - 1*ymul + 1*ydir][srcX - 1*xmul + 1*xdir] == 1 && m_tiles[srcY + 1*ydir][srcX + 1*xdir] == 0) {
			// LEFT intersection
			tileIndices.add(!flip ? TileManager.NEAR_INTERSECT_LEFT : TileManager.NEAR_INTERSECT_RIGHT);
		}
		if (m_tiles[srcY + 1*ymul][srcX + 1*xmul] == 0 && m_tiles[srcY + 1*ymul + 1*ydir][srcX + 1*xmul + 1*xdir] == 1 && m_tiles[srcY + 1*ydir][srcX + 1*xdir] == 0) {
			// RIGHT intersection
			tileIndices.add(!flip ? TileManager.NEAR_INTERSECT_RIGHT : TileManager.NEAR_INTERSECT_LEFT);
		}
		
		return tileIndices;
	}
	
	public Vector<Integer> getVisibleFarTiles(int srcX, int srcY, int dir) {
		Vector<Integer> tileIndices = new Vector<Integer>();
		
		int xdir = 0, ydir = 0;
		int xmul = 0, ymul = 0;
		boolean flip = false;
		
		if (dir == Direction.RIGHT) {
			xdir = 1;
			xmul = 0;
			ymul = 1;
			ydir = 0;
			flip = false;
		} else if (dir == Direction.LEFT) {
			xdir = -1;
			xmul = 0;
			ymul = 1;
			ydir = 0;
			flip = true;
		} else if (dir == Direction.UP) {
			xdir = 0;
			xmul = 1;
			ymul = 0;
			ydir = -1;
			flip = false;
		} else if (dir == Direction.DOWN) {
			xdir = 0;
			xmul = 1;
			ymul = 0;
			ydir = 1;
			flip = true;
		}
		
		if (m_tiles[srcY - 1*ymul + 1*ydir][srcX - 1*xmul + 1*xdir] == 1) {
			tileIndices.add(!flip ? TileManager.FAR_CORRIDOR_LEFT : TileManager.FAR_CORRIDOR_RIGHT);
		}
		if (m_tiles[srcY + 1*ymul + 1*ydir][srcX + 1*xmul + 1*xdir] == 1) {
			tileIndices.add(!flip ? TileManager.FAR_CORRIDOR_RIGHT : TileManager.FAR_CORRIDOR_LEFT);
		}
		
		if (m_tiles[srcY - 1*ymul][srcX - 1*xmul] == 1 && m_tiles[srcY - 1*ymul + 1*ydir][srcX - 1*xmul + 1*xdir] == 0 && m_tiles[srcY + 2*ydir][srcX + 2*xdir] == 0) {
			tileIndices.add(!flip ? TileManager.FAR_INTERSECT_LEFT : TileManager.FAR_INTERSECT_RIGHT);
		}
		if (m_tiles[srcY + 1*ymul][srcX + 1*xmul] == 1 && m_tiles[srcY + 1*ymul + 1*ydir][srcX + 1*xmul + 1*xdir] == 0 && m_tiles[srcY + 2*ydir][srcX + 2*xdir] == 0) {
			tileIndices.add(!flip ? TileManager.FAR_INTERSECT_RIGHT : TileManager.FAR_INTERSECT_LEFT);
		}
		
		return tileIndices;
	}
	
	public void drawFeatures(Canvas canvas, int srcX, int srcY, int dir) {
		int xdir = 0, ydir = 0;
		int xmul = 0, ymul = 0;
		boolean flip = false;
		
		if (dir == Direction.RIGHT) {
			xdir = 1;
			xmul = 0;
			ymul = 1;
			ydir = 0;
			flip = false;
		} else if (dir == Direction.LEFT) {
			xdir = -1;
			xmul = 0;
			ymul = 1;
			ydir = 0;
			flip = true;
		} else if (dir == Direction.UP) {
			xdir = 0;
			xmul = 1;
			ymul = 0;
			ydir = -1;
			flip = false;
		} else if (dir == Direction.DOWN) {
			xdir = 0;
			xmul = 1;
			ymul = 0;
			ydir = 1;
			flip = true;
		}
		
		for (WallFeature wf : m_wallFeatures) {
			
			// First check if the player is on the correct side of the wall
			// to see this feature...
			if (wf.side() == Direction.DOWN) {
				if (!(srcX == wf.x() && srcY > wf.y())) {
					// Player must be below.
					continue;
				}
			} else if (wf.side() == Direction.UP) {
				if (!(srcX == wf.x() && srcY < wf.y())) {
					// Player must be above.
					continue;
				}
			} else if (wf.side() == Direction.RIGHT){
				if (!(srcX > wf.x() && srcY == wf.y())) {
					// Player must stand on the right side.
					continue;
				}
			} else if (wf.side() == Direction.LEFT) {
				if (!(srcX < wf.x() && srcY == wf.y())) {
					// Player must stand on left side.
					continue;
				}
			}
			
			if (srcX + xdir == wf.x() && srcY + ydir == wf.y()) {
				// Front
				wf.draw(canvas, WallFeature.NEAR_FRONT);
			} else if (m_tiles[srcY + ydir][srcX + xdir] == 0 && srcX + 2*xdir == wf.x() && srcY + 2*ydir == wf.y()) {
				// Far front
				wf.draw(canvas, WallFeature.FAR_FRONT);
			} else if (srcX - xmul == wf.x() && srcY - ymul == wf.y()) {
				// Left
				wf.draw(canvas, !flip ? WallFeature.LEFT : WallFeature.RIGHT);
			} else if (srcX + xmul == wf.x() && srcY + ymul == wf.y()) {
				// Right
				wf.draw(canvas, !flip ? WallFeature.RIGHT : WallFeature.LEFT);
			}
			
		}
	}
	
	public void addFeature(WallFeature wf) {
		m_wallFeatures.add(wf);
	}
	
	public void debugDraw(Canvas canvas) {
		for (int y = 0; y < m_tiles.length; y++) {
			for (int x = 0; x < m_tiles[y].length; x++) {
				if (m_tiles[y][x] == 1) {
					Paint paint = new Paint();
					paint.setARGB(255, 255, 255, 255);
					canvas.drawRect(x*8, y*8, x*8+8, y*8+8, paint);
				}
			}
		}
	}
}
