package com.redapplecandy.minirpg;

import java.util.Vector;

import com.redapplecandy.minirpg.character.PlayerCharacter;
import com.redapplecandy.minirpg.ui.CharacterWidget;
import com.redapplecandy.minirpg.ui.InvisibleButton;
import com.redapplecandy.minirpg.ui.MessageBox;

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
	
	PlayerCharacter testCharacter = PlayerCharacter.createTestCharacter();
	CharacterWidget characterWidget = new CharacterWidget(testCharacter, 0, 0);
	CharacterWidget characterWidget2 = new CharacterWidget(testCharacter, 76, 0);
	CharacterWidget characterWidget3 = new CharacterWidget(testCharacter, 152, 0);
	CharacterWidget characterWidget4 = new CharacterWidget(testCharacter, 228, 0);
	
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
		
		Core core = Core.instance();
		Camera camera = core.camera();
		core.currentLevel().draw(canvas, camera);
		
		Paint paint = new Paint();
		paint.setARGB(255, 0, 255, 255);
		canvas.drawRect(camera.x*8, camera.y*8, camera.x*8+8, camera.y*8+8, paint);
		Paint paint2 = new Paint();
		paint.setARGB(255, 0, 0, 0);
		if (camera.direction == Direction.RIGHT)
			canvas.drawLine(camera.x*8+4, camera.y*8+4, camera.x*8+4+4, camera.y*8+4, paint2);
		if (camera.direction == Direction.LEFT)
			canvas.drawLine(camera.x*8+4, camera.y*8+4, camera.x*8+4-4, camera.y*8+4, paint2);
		if (camera.direction == Direction.UP)
			canvas.drawLine(camera.x*8+4, camera.y*8+4, camera.x*8+4, camera.y*8+4-4, paint2);
		if (camera.direction == Direction.DOWN)
			canvas.drawLine(camera.x*8+4, camera.y*8+4, camera.x*8+4, camera.y*8+4+4, paint2);
		
		core.currentLevel().drawMiniMap(canvas);
		
		StatusText.draw(canvas);
		
		for (InvisibleButton b : m_buttons) {
		//	b.debugDraw(canvas);
		}
		
		characterWidget.draw(canvas);
		characterWidget2.draw(canvas);
		characterWidget3.draw(canvas);
		characterWidget4.draw(canvas);
		
		if (core.getMessageBox().visible()) {
			core.getMessageBox().draw(canvas);
		}
	}
	
	/*
	public void createFeature() {
		m_tilemap.addFeature(new WallFeature(R.drawable.wall_feature_cave_door, 5, 1, Direction.DOWN));
		m_tilemap.addFeature(new WallFeature(R.drawable.wall_feature_cave_door, 6, 2, Direction.LEFT));
		m_tilemap.addFeature(new WallFeature(R.drawable.wall_feature_cave_door, 5, 3, Direction.UP));
	}
	*/
	
	protected void onMeasure(int measureWidth, int measureHeight) {
		super.onMeasure(measureWidth, measureHeight);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
		
			Integer x = (int) event.getX();
			Integer y = (int) event.getY();
			StatusText.statusText = "X: " + x.toString() + "; Y: " + y.toString();
		
			if (Core.instance().currentState() == Core.STATE_WALK_AROUND) {
				// If STATE == WALK_AROUND then fire movement events.
				
				for (InvisibleButton b : m_buttons) {
					if (b.isClicked(x, y)) {
						b.fireClick();
					}
				}
			
				this.invalidate();
			
			} else if (Core.instance().currentState() == Core.STATE_WAIT_MESSAGE) {
				// If STATE == WAIT_MESSAGE, then pressing will escape from the
				// currently displayed message.
				
				// TODO: The state to change to will depend on a number of things
				// later (a saved state? We'll see...).
				Core.instance().setState(Core.STATE_WALK_AROUND);
				Core.instance().getMessageBox().hide();
				
				this.invalidate();
			}
		}
		
		return true;
	}
	
	public void addButton(InvisibleButton b) {
		m_buttons.add(b);
	}
	
}
