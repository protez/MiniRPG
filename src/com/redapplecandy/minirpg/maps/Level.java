package com.redapplecandy.minirpg.maps;

import java.util.Vector;

import com.redapplecandy.minirpg.SimpleCamera;
import com.redapplecandy.minirpg.dungeonfeatures.wallfeatures.WallFeature;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Level {

	private int[][] m_tiles;
	private Vector<WallFeature> m_wallFeatures;
	
	
	public Level() {
		
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
	
	public boolean isSolid(int x, int y) {
		return m_tiles[y][x] == 1;
	}
	
	public void addFeature(WallFeature wf) {
		m_wallFeatures.add(wf);
	}
	
	public void draw(Canvas canvas, SimpleCamera src) {
	}
	
	public void drawMiniMap(Canvas canvas) {
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
