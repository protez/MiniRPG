package com.redapplecandy.minirpg;

import java.util.Vector;

import com.redapplecandy.minirpg.dungeonfeatures.wallfeatures.WallFeature;
import com.redapplecandy.minirpg.maps.TileMap;
import com.redapplecandy.minirpg.ui.InvisibleButton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Renders the game. I'm using this as a testclass for everything atm.
 * @author tomas
 */
public class RenderView extends View implements OnTouchListener {

	Vector<InvisibleButton> m_buttons = new Vector<InvisibleButton>();
	
	TileMap m_tilemap = new TileMap();
	MessageBox m_messageBox = new MessageBox();
	
	public int curX = 2, curY = 2;
	public int curDir = 0;
	
	public RenderView(Context context) {
		super(context);
		
		setOnTouchListener(this);
	}

	public RenderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setOnTouchListener(this);
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawARGB(255, 0, 0, 0);
		
		Vector<Integer> visibleNear = m_tilemap.getVisibleNearTiles(curX, curY, curDir);
		
		TileManager tileManager = TileManager.instance();
		
		for (int i = 0; i < 3; i++) {
			int type = visibleNear.get(i);
			Bitmap bmp = tileManager.getTile(type);

			// First 6 will be wall tiles that we can safely draw over later.
			if (type == TileManager.FAR_WALL || type == TileManager.FAR_EMPTY) {
				canvas.drawBitmap(bmp, Config.VIEW_CORNER_X + i * Config.TILE_WIDTH, Config.VIEW_CORNER_Y, null);
			}
		}
		
		Vector<Integer> visibleFar = m_tilemap.getVisibleFarTiles(curX, curY, curDir);
		for (int i = 0; i < visibleFar.size(); i++) {
			int type = visibleFar.get(i);
			Bitmap bmp = tileManager.getTile(type);
			
			if (type == TileManager.FAR_CORRIDOR_LEFT 
				|| type == TileManager.FAR_INTERSECT_LEFT) 
			{
				canvas.drawBitmap(bmp, Config.VIEW_CORNER_X + Config.TILE_WIDTH, Config.VIEW_CORNER_Y, null);
			}
			if (type == TileManager.FAR_CORRIDOR_RIGHT 
				|| type == TileManager.FAR_INTERSECT_RIGHT) 
			{
				canvas.drawBitmap(bmp, Config.VIEW_CORNER_X + Config.TILE_WIDTH + Config.FAR_TILE_WIDTH, Config.VIEW_CORNER_Y, null);
			}
			
		}
		
		for (int i = 0; i < visibleNear.size(); i++) {
			int type = visibleNear.get(i);
			Bitmap bmp = tileManager.getTile(type);

			if (type >= TileManager.NEAR_WALL_LEFT 
				&& type <= TileManager.NEAR_WALL_RIGHT) 
			{
				canvas.drawBitmap(bmp, Config.VIEW_CORNER_X + (i - 3) * Config.TILE_WIDTH, Config.VIEW_CORNER_Y, null);
			}
			
			if (type == TileManager.NEAR_CORRIDOR_LEFT 
				|| type == TileManager.NEAR_INTERSECT_LEFT) 
			{
				canvas.drawBitmap(bmp, Config.VIEW_CORNER_X, Config.VIEW_CORNER_Y, null);
			}
			if (type == TileManager.NEAR_CORRIDOR_RIGHT
				|| type == TileManager.NEAR_INTERSECT_RIGHT) 
			{
				canvas.drawBitmap(bmp, Config.VIEW_CORNER_X + Config.TILE_WIDTH * 2, Config.VIEW_CORNER_Y, null);
			}
			
		}
		
		m_tilemap.drawFeatures(canvas, curX, curY, curDir);
		
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
		
		StatusText.draw(canvas);
		
		m_messageBox.draw(canvas);
		
		for (InvisibleButton b : m_buttons) {
			b.debugDraw(canvas);
		}
	}
	
	public void createFeature() {
		m_tilemap.addFeature(new WallFeature(R.drawable.wall_feature_cave_door, 5, 1, Direction.DOWN));
		m_tilemap.addFeature(new WallFeature(R.drawable.wall_feature_cave_door, 6, 2, Direction.LEFT));
		m_tilemap.addFeature(new WallFeature(R.drawable.wall_feature_cave_door, 5, 3, Direction.UP));
	}
	
	protected void onMeasure(int measureWidth, int measureHeight) {
		super.onMeasure(measureWidth, measureHeight);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
		
			Integer x = (int) event.getX();
			Integer y = (int) event.getY();
			StatusText.statusText = "X: " + x.toString() + "; Y: " + y.toString();
		
			for (InvisibleButton b : m_buttons) {
				if (b.isClicked(x, y)) {
					b.fireClick();
				}
			}
			
			this.invalidate();
		}
		
		return true;
	}
	
	public void addButton(InvisibleButton b) {
		m_buttons.add(b);
	}
	
}
