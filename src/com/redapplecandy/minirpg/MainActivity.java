package com.redapplecandy.minirpg;

import com.redapplecandy.minirpg.graphics.Raycaster;
import com.redapplecandy.minirpg.math.Vec2;
import com.redapplecandy.minirpg.ui.InvisibleButton;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity {
    
	RenderView m_renderView;
	InvisibleButton m_up;
	InvisibleButton m_down;
	
	InvisibleButton m_turnLeft;
	InvisibleButton m_turnRight;
	
	static {
		System.err.println("*** LOADING LIBRARY!!!");
		System.loadLibrary("raycaster");
		System.err.println("*** LIBRARY LOADED!!!!!");
	}
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        m_renderView = (RenderView) findViewById(R.id.gameView);
        BitmapLoader.instance().setContext(this);
        TileManager.instance();
        
        m_renderView.raycaster = new Raycaster(Config.MAIN_WINDOW_WIDTH, 200);
        Core.instance().setRenderView(m_renderView);
        
        //m_renderView.createFeature();
        
    	m_up = new InvisibleButton(64, 0, 0, Config.MAIN_WINDOW_WIDTH - 128, 64);
    	m_down = new InvisibleButton(64, Config.MAIN_WINDOW_HEIGHT - 64, 0, Config.MAIN_WINDOW_WIDTH - 128, 64);
    	
    	m_turnLeft = new InvisibleButton(0, 64, 0, 64, Config.MAIN_WINDOW_HEIGHT - 128);
    	m_turnRight = new InvisibleButton(Config.MAIN_WINDOW_WIDTH - 64, 64, 0, 64, Config.MAIN_WINDOW_HEIGHT - 128);
        
    	m_renderView.addButton(m_up);
    	m_renderView.addButton(m_down);
    	m_renderView.addButton(m_turnLeft);
    	m_renderView.addButton(m_turnRight);
    	
        m_turnLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Core core = Core.instance();				
				if (Core.instance().currentState() == Core.STATE_WALK_AROUND) {
				/*
					if (Core.instance().getSimpleCamera().direction == Direction.RIGHT)
						Core.instance().getSimpleCamera().direction = Direction.UP;
					else if (Core.instance().getSimpleCamera().direction == Direction.LEFT)
						Core.instance().getSimpleCamera().direction = Direction.DOWN;
					else if (Core.instance().getSimpleCamera().direction == Direction.UP)
						Core.instance().getSimpleCamera().direction = Direction.LEFT;
					else if (Core.instance().getSimpleCamera().direction == Direction.DOWN)
						Core.instance().getSimpleCamera().direction = Direction.RIGHT;
				*/	
					Vec2 dir = core.camera().dir.clone();
					Vec2 plane = core.camera().plane.clone();
					
					core.camera().dir.x = (float) (dir.x * Math.cos(0.087) - dir.y * Math.sin(0.087));
					core.camera().dir.y = (float) (dir.x * Math.sin(0.087) + dir.y * Math.cos(0.087));
					core.camera().plane.x = (float) (plane.x * Math.cos(0.087) - plane.y * Math.sin(0.087));
					core.camera().plane.y = (float) (plane.x * Math.sin(0.087) + plane.y * Math.cos(0.087));
					
					m_renderView.invalidate();
					
				}
			}
        	
        });
        m_turnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Core core = Core.instance();				
				if (Core.instance().currentState() == Core.STATE_WALK_AROUND) {
				/*
					if (Core.instance().getSimpleCamera().direction == Direction.RIGHT)
						Core.instance().getSimpleCamera().direction = Direction.DOWN;
					else if (Core.instance().getSimpleCamera().direction == Direction.LEFT)
						Core.instance().getSimpleCamera().direction = Direction.UP;
					else if (Core.instance().getSimpleCamera().direction == Direction.UP)
						Core.instance().getSimpleCamera().direction = Direction.RIGHT;
					else if (Core.instance().getSimpleCamera().direction == Direction.DOWN)
						Core.instance().getSimpleCamera().direction = Direction.LEFT;
				*/	
					Vec2 dir = core.camera().dir.clone();
					Vec2 plane = core.camera().plane.clone();
					
					core.camera().dir.x = (float) (dir.x * Math.cos(-0.087) - dir.y * Math.sin(-0.087));
					core.camera().dir.y = (float) (dir.x * Math.sin(-0.087) + dir.y * Math.cos(-0.087));
					core.camera().plane.x = (float) (plane.x * Math.cos(-0.087) - plane.y * Math.sin(-0.087));
					core.camera().plane.y = (float) (plane.x * Math.sin(-0.087) + plane.y * Math.cos(-0.087));
					
					m_renderView.invalidate();
					
				}
			}
        	
        });
        
        m_up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Core core = Core.instance();
				
				if (core.currentState() == Core.STATE_WALK_AROUND) {
					/*
					int tempX = core.getSimpleCamera().x;
					int tempY = core.getSimpleCamera().y;
					
					if (core.getSimpleCamera().direction == Direction.RIGHT)
						core.getSimpleCamera().x++;
					if (core.getSimpleCamera().direction == Direction.LEFT)
						core.getSimpleCamera().x--;
					if (core.getSimpleCamera().direction == Direction.UP)
						core.getSimpleCamera().y--;
					if (core.getSimpleCamera().direction == Direction.DOWN)
						core.getSimpleCamera().y++;
					
					if (core.currentLevel().isSolid(core.getSimpleCamera().x, core.getSimpleCamera().y)) {
						core.getSimpleCamera().x = tempX;
						core.getSimpleCamera().y = tempY;
					}
					
					if (core.getSimpleCamera().x == 3 && core.getSimpleCamera().y == 2) {
						core.startMessage("A test message.\nTest!");
					}
					*/
					
					core.camera().pos.x += core.camera().dir.x * 0.05;
					core.camera().pos.y += core.camera().dir.y * 0.05;
					
					m_renderView.invalidate();
					
				}
			}
        	
        });

        m_down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Core core = Core.instance();
				
				if (core.currentState() == Core.STATE_WALK_AROUND) {
					/*
					int tempX = core.getSimpleCamera().x;
					int tempY = core.getSimpleCamera().y;
					
					if (core.getSimpleCamera().direction == Direction.RIGHT)
						core.getSimpleCamera().x--;
					if (core.getSimpleCamera().direction == Direction.LEFT)
						core.getSimpleCamera().x++;
					if (core.getSimpleCamera().direction == Direction.UP)
						core.getSimpleCamera().y++;
					if (core.getSimpleCamera().direction == Direction.DOWN)
						core.getSimpleCamera().y--;
					
					if (core.currentLevel().isSolid(core.getSimpleCamera().x, core.getSimpleCamera().y)) {
						core.getSimpleCamera().x = tempX;
						core.getSimpleCamera().y = tempY;
					}
					*/
					
					core.camera().pos.x -= core.camera().dir.x * 0.05;
					core.camera().pos.y -= core.camera().dir.y * 0.05;
					
					m_renderView.invalidate();
				
				}
			}
        	
        });
        
    }
}