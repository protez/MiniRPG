package com.redapplecandy.minirpg;

import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RenderView extends View {

	TileMap m_tilemap = new TileMap();
	
	public int curX = 2, curY = 2;
	public int curDir = 0;
	
	public RenderView(Context context) {
		super(context);
	}

	public RenderView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawARGB(255, 255, 0, 0);
		
		/*
		Bitmap bmp1 = TileManager.instance().getTile(TileManager.NEAR_CORRIDOR_LEFT);
		Bitmap bmp2 = TileManager.instance().getTile(TileManager.FAR_WALL);
		Bitmap bmp3 = TileManager.instance().getTile(TileManager.NEAR_INTERSECT_RIGHT);
		
		Bitmap bmp4 = TileManager.instance().getTile(TileManager.FAR_CORRIDOR_LEFT);
		Bitmap bmp5 = TileManager.instance().getTile(TileManager.FAR_INTERSECT_RIGHT);
		
		canvas.drawBitmap(bmp1, 0, 24, null);
		canvas.drawBitmap(bmp2, 96, 24, null);
		canvas.drawBitmap(bmp3, 192, 24, null);
		
		canvas.drawBitmap(bmp4, 96, 24, null);
		canvas.drawBitmap(bmp5, 144, 24, null);
		*/
		
		Vector<Integer> visibleNear = m_tilemap.getVisibleNearTiles(curX, curY, curDir);
		
		TileManager tileManager = TileManager.instance();
		
		for (int i = 0; i < 3; i++) {
			int type = visibleNear.get(i);
			Bitmap bmp = tileManager.getTile(type);

			// First 6 will be wall tiles that we can safely draw over later.
			if (type == TileManager.FAR_WALL || type == TileManager.FAR_EMPTY) {
				canvas.drawBitmap(bmp, i * 96, 24, null);
			}
		}
		
		Vector<Integer> visibleFar = m_tilemap.getVisibleFarTiles(curX, curY, curDir);
		for (int i = 0; i < visibleFar.size(); i++) {
			int type = visibleFar.get(i);
			Bitmap bmp = tileManager.getTile(type);
			
			if (type == TileManager.FAR_CORRIDOR_LEFT 
				|| type == TileManager.FAR_INTERSECT_LEFT) 
			{
				canvas.drawBitmap(bmp, 96, 24, null);
			}
			if (type == TileManager.FAR_CORRIDOR_RIGHT 
				|| type == TileManager.FAR_INTERSECT_RIGHT) 
			{
				canvas.drawBitmap(bmp, 144, 24, null);
			}
			
		}
		
		for (int i = 0; i < visibleNear.size(); i++) {
			int type = visibleNear.get(i);
			Bitmap bmp = tileManager.getTile(type);

			if (type >= TileManager.NEAR_WALL_LEFT 
				&& type <= TileManager.NEAR_WALL_RIGHT) 
			{
				canvas.drawBitmap(bmp, (i - 3) * 96, 24, null);
			}
			
			if (type == TileManager.NEAR_CORRIDOR_LEFT 
				|| type == TileManager.NEAR_INTERSECT_LEFT) 
			{
				canvas.drawBitmap(bmp, 0, 24, null);
			}
			if (type == TileManager.NEAR_CORRIDOR_RIGHT
				|| type == TileManager.NEAR_INTERSECT_RIGHT) 
			{
				canvas.drawBitmap(bmp, 192, 24, null);
			}
			
		}
		
		Paint paint = new Paint();
		paint.setARGB(255, 0, 255, 255);
		canvas.drawRect(curX*8, curY*8, curX*8+8, curY*8+8, paint);
		Paint paint2 = new Paint();
		paint.setARGB(255, 0, 0, 0);
		if (curDir == Direction.RIGHT)
			canvas.drawLine(curX*8+4, curY*8+4, curX*8+4+4, curY*8+4, paint2);
		if (curDir == Direction.LEFT)
			canvas.drawLine(curX*8+4, curY*8+4, curX*8+4-4, curY*8+4, paint2);
		if (curDir == Direction.UP)
			canvas.drawLine(curX*8+4, curY*8+4, curX*8+4, curY*8+4-4, paint2);
		if (curDir == Direction.DOWN)
			canvas.drawLine(curX*8+4, curY*8+4, curX*8+4, curY*8+4+4, paint2);
		
		m_tilemap.debugDraw(canvas);
	}
	
	protected void onMeasure(int measureWidth, int measureHeight) {
		super.onMeasure(measureWidth, measureHeight);
	}
	
}
