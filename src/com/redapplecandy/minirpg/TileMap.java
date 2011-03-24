package com.redapplecandy.minirpg;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;

public class TileMap {

	int[][] m_tiles;
	
	public TileMap() {
		
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
