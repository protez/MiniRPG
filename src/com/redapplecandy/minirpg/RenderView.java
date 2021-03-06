package com.redapplecandy.minirpg;

import java.util.Vector;

import com.redapplecandy.minirpg.character.PlayerCharacter;
import com.redapplecandy.minirpg.graphics.Raycaster;
import com.redapplecandy.minirpg.ui.CharacterWidget;
import com.redapplecandy.minirpg.ui.InvisibleButton;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Renders the game. I'm using this as a testclass for everything atm.
 * @author tomas
 */
public class RenderView extends SurfaceView implements OnTouchListener, SurfaceHolder.Callback {

	private class GameThread extends Thread {
		private SurfaceHolder m_surfaceHolder;
		private boolean m_running = true;
		private Core m_core;
		
		private long m_timerThen;
		
		public GameThread(SurfaceHolder surfaceHolder) {
			m_surfaceHolder = surfaceHolder;
			m_core = Core.instance();
			
			m_timerThen = SystemClock.currentThreadTimeMillis();
		}
		
		public void run() {
			while (m_running) {
				Canvas canvas = null;
				
				long timerNow = SystemClock.currentThreadTimeMillis();
				
				while (m_timerThen < timerNow) {
					update();
					m_timerThen += 1000 / Config.FPS;
				}
				
				try {
					canvas = m_surfaceHolder.lockCanvas(null);
					synchronized(m_surfaceHolder) {
						draw(canvas);
					}
				} finally {
					if (canvas != null) {
						m_surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
				
				SystemClock.sleep(m_timerThen - timerNow);
			}
		}
		
		public void update() {
			// Maybe put this if-statement in core ;-)
			if (m_core.currentState() == Core.STATE_TURNING) {
				m_core.incAngle();
			} else if (m_core.currentState() == Core.STATE_MOVING) {
				m_core.incStep();
			} else if (m_core.currentState() == Core.STATE_SHOW_MESSAGE) {
				m_core.advanceMessage();
			}
		}
		
		public void end() {
			m_running = false;
		}
		
		public void draw(Canvas canvas) {
			canvas.drawARGB(255, 0, 0, 0);
			
			Core core = Core.instance();
			/*
			SimpleCamera camera = core.getSimpleCamera();
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
			*/
			
			raycaster.draw(core.camera(), canvas);
			
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
	}
	
	Vector<InvisibleButton> m_buttons = new Vector<InvisibleButton>();
	
	PlayerCharacter testCharacter = PlayerCharacter.createTestCharacter();
	CharacterWidget characterWidget = new CharacterWidget(testCharacter, Config.VIEW_CORNER_X + 0, 0);
	CharacterWidget characterWidget2 = new CharacterWidget(testCharacter, Config.VIEW_CORNER_X + 80, 0);
	CharacterWidget characterWidget3 = new CharacterWidget(testCharacter, Config.VIEW_CORNER_X + 160, 0);
	CharacterWidget characterWidget4 = new CharacterWidget(testCharacter, Config.VIEW_CORNER_X + 240, 0);
	
	public Raycaster raycaster;
	
	
	private GameThread m_gameThread;
	
	public RenderView(Context context) {
		super(context);
		
		setOnTouchListener(this);
	}

	public RenderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		SurfaceHolder holder = this.getHolder();
		holder.addCallback(this);
		m_gameThread = new GameThread(holder);
		
		addButton(characterWidget);
		characterWidget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Core.instance().startMessage("hurr durr");
			}
		});
		
		setOnTouchListener(this);
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
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
					if (b != null && b.isClicked(x, y)) {
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

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Config.REAL_WIDTH = this.getWidth();
		Config.REAL_HEIGHT = this.getHeight();
		
		m_gameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		m_gameThread.end();
		try {
			m_gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
