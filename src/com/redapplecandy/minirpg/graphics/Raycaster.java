package com.redapplecandy.minirpg.graphics;

import com.redapplecandy.minirpg.BitmapLoader;
import com.redapplecandy.minirpg.Config;
import com.redapplecandy.minirpg.Pair;
import com.redapplecandy.minirpg.R;
import com.redapplecandy.minirpg.math.Vec2;
import com.redapplecandy.minirpg.util.ArrayUtils;
import com.redapplecandy.minirpg.util.MathUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Raycaster {
	
	private class RayInfo {
		float wallDist;
		int mapX, mapY;
		float floorXWall, floorYWall;
		int textureX;
		int tileId;
	}
	
	private int m_width, m_height;
	private int[][] m_tileMap;
	private Bitmap m_wallTexture, m_floorTexture;
	private Bitmap m_target;
	
	private Matrix m_drawMatrix = new Matrix();
	
	public Raycaster(int width, int height) {
		m_width = width;
		m_height = height;
		
		int[][] tileMap = {
			{1, 2, 1, 2, 1, 2, 1, 2},
			{2, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 4, 0, 0, 4, 0, 2},
			{2, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 2},
			{2, 0, 4, 0, 0, 4, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 2},
			{2, 1, 2, 1, 2, 1, 2, 1},
		};
		m_tileMap = tileMap;
		
		m_target = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		
		m_wallTexture = BitmapLoader.instance().loadById(R.drawable.wall);
		m_floorTexture = BitmapLoader.instance().loadById(R.drawable.floor64x64);
	}
	
	public void draw(Camera camera, Canvas canvas) {
		m_target.eraseColor(0);
		//raycast(camera);
		raycast(m_target, m_wallTexture, m_floorTexture, 
			ArrayUtils.flattenIntMatrix(m_tileMap), m_tileMap.length, m_tileMap[0].length,
			camera.pos.x, camera.pos.y, camera.dir.x, camera.dir.y, camera.plane.x, camera.plane.y);
		
		Pair<Float, Float> scale = MathUtils.scaleFactor(canvas.getWidth(), canvas.getHeight());
		
		m_drawMatrix.setTranslate(Config.VIEW_CORNER_X * scale.fst, Config.VIEW_CORNER_Y * scale.snd);
		m_drawMatrix.preScale(scale.fst, scale.snd);
		
		canvas.drawBitmap(m_target, m_drawMatrix, null);
		//canvas.drawBitmap(m_target, Config.VIEW_CORNER_X, Config.VIEW_CORNER_Y, null);
	}
	
	private static native void raycast(
		Bitmap bitmap, Bitmap wallTexture, Bitmap floorTexture, int[] _tileMap,
		int width, int height, float camX, float camY, float camDirX,
		float camDirY, float camPlaneX, float camPlaneY);
	
}
