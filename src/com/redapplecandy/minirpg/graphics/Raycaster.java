package com.redapplecandy.minirpg.graphics;

import com.redapplecandy.minirpg.BitmapLoader;
import com.redapplecandy.minirpg.Config;
import com.redapplecandy.minirpg.R;
import com.redapplecandy.minirpg.math.Vec2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

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
		raycast(camera);
		canvas.drawBitmap(m_target, 0, 0, null);
	}
	
	public void raycast(Camera camera) {
		
		for (int x = 0; x < m_width; x++) {
			int lineHeight;
			float wallDist, cameraDist = 0.0f, currentDist;
			int wallStart, wallEnd;
			
			RayInfo info = castRay(camera, x);
			
			// m_zbuffer[x] = info.wallDist;
			
			lineHeight = (int)Math.abs((float)m_height / info.wallDist);
			wallStart = -lineHeight / 2 + m_height / 2;
			wallEnd = lineHeight / 2 + m_height / 2;
			if  (wallStart < 0) wallStart = 0;
			if (wallEnd >= m_height) wallEnd = m_height - 1;
			
			for (int y = wallStart; y < wallEnd; y++) {
				int d, textureY;
				
				d = y * 256 - m_height * 128 + lineHeight * 128;
				textureY = ((d * Config.TEXTURE_SIZE) / lineHeight) / 256;
				
				int color = m_wallTexture.getPixel(info.textureX, textureY);
				
				m_target.setPixel(x, y, color);
			}
		
			/*
			if (wallEnd < 0) {
				wallEnd = m_height;
			}
			
			for (int y = wallEnd + 1; y < m_height; y++) {
				int floorTextureX, floorTextureY;
				float weight;
				float currentFloorX, currentFloorY;
				
				currentDist = (float)m_height / (2.0f * y - m_height);
				
				weight = (currentDist - cameraDist) / (info.wallDist - cameraDist);
				currentFloorX = weight * info.floorXWall + (1.0f - weight) * camera.pos.x;
				currentFloorY = weight * info.floorYWall + (1.0f - weight) * camera.pos.y;
				floorTextureX = (int)(currentFloorX * Config.TEXTURE_SIZE) % Config.TEXTURE_SIZE;
				floorTextureY = (int)(currentFloorY * Config.TEXTURE_SIZE) % Config.TEXTURE_SIZE;
				
				int color = m_floorTexture.getPixel(floorTextureX, floorTextureY);
				// Floor
				m_target.setPixel(x, y, color);
				
				// Ceiling
				m_target.setPixel(x, m_height - y, color);
			}
			*/
		}
		
	}
	
	private RayInfo castRay(Camera camera, int x) {
		int mapX, mapY;
		float sideDistX, sideDistY;
		float ddx, ddy;
		float wallDist;
		int stepX, stepY;
		int side;
		float wallX;
		int textureX;
		float floorXWall, floorYWall;
		
		float camX = 2.0f * (float)x / (float)m_width - 1.0f;
		Vec2 ray = camera.pos;
		Vec2 rayDir = new Vec2(camera.dir.x + camera.plane.x * camX,
							   camera.dir.y + camera.plane.y * camX);
		
		mapX = (int) ray.x;
		mapY = (int) ray.y;
		
		ddx = (float) Math.sqrt(1.0f + (rayDir.y * rayDir.y) / (rayDir.x * rayDir.x));
		ddy = (float) Math.sqrt(1.0f + (rayDir.x * rayDir.x) / (rayDir.y * rayDir.y));
		
		if (rayDir.x < 0) {
			stepX = -1;
			sideDistX = (ray.x - mapX) * ddx;
		} else {
			stepX = 1;
			sideDistX = (mapX + 1.0f - ray.x) * ddx; 
		}
		
		if (rayDir.y < 0) {
			stepY = -1;
			sideDistY = (ray.y - mapY) * ddy;
		} else {
			stepY = 1;
			sideDistY = (mapY + 1.0f - ray.y) * ddy;
		}
		
		while (true) {
			if (sideDistX < sideDistY) {
				sideDistX += ddx;
				mapX += stepX;
				side = 0;
			} else {
				sideDistY += ddy;
				mapY += stepY;
				side = 1;
			}
			
			if (m_tileMap[mapY][mapX] != 0) {
				break;
			}
		}
		
		if (side == 0) {
			wallDist = Math.abs((mapX - ray.x + (1.0f - stepX) / 2.0f) / rayDir.x);
			wallX = ray.y + ((mapX - ray.x + (1.0f - stepX) / 2.0f) / rayDir.x) * rayDir.y;
			wallX -= Math.floor(wallX);
			
			textureX = (int)(wallX * (float)Config.TEXTURE_SIZE);
			if (rayDir.x > 0) {
				textureX = Config.TEXTURE_SIZE - textureX - 1;
			}
		} else {
			wallDist = Math.abs((mapY - ray.y + (1.0f - stepY) / 2.0f) / rayDir.y);
			wallX = ray.x + ((mapY - ray.y + (1.0f - stepY) / 2.0f) / rayDir.y) * rayDir.x;
			wallX -= Math.floor(wallX);
			
			textureX = (int)(wallX * (float)Config.TEXTURE_SIZE);
			if (rayDir.y < 0) {
				textureX = Config.TEXTURE_SIZE - textureX - 1;
			}			
		}
		
		if (side == 0 && rayDir.x > 0) {
			floorXWall = mapX;
			floorYWall = mapY + wallX;
		} else if (side == 0 && rayDir.x < 0) {
			floorXWall = mapX + 1.0f;
			floorYWall = mapY + wallX;
		} else if (side == 1 && rayDir.y > 0) {
			floorXWall = mapX + wallX;
			floorYWall = mapY;
		} else {
			floorXWall = mapX + wallX;
			floorYWall = mapY + 1.0f;
		}
		
		RayInfo info = new RayInfo();
		info.wallDist = wallDist;
		info.mapX = mapX;
		info.mapY = mapY;
		info.floorXWall = floorXWall;
		info.floorYWall = floorYWall;
		info.textureX = textureX;
		info.tileId = m_tileMap[mapY][mapX];
		
		return info;
	}
	
}
