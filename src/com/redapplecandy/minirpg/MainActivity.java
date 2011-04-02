package com.redapplecandy.minirpg;

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
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        m_renderView = (RenderView) findViewById(R.id.gameView);
        BitmapLoader.instance().setContext(this);
        TileManager.instance();
        
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
				
				if (Core.instance().currentState() == Core.STATE_WALK_AROUND) {
				
					if (Core.instance().camera().direction == Direction.RIGHT)
						Core.instance().camera().direction = Direction.UP;
					else if (Core.instance().camera().direction == Direction.LEFT)
						Core.instance().camera().direction = Direction.DOWN;
					else if (Core.instance().camera().direction == Direction.UP)
						Core.instance().camera().direction = Direction.LEFT;
					else if (Core.instance().camera().direction == Direction.DOWN)
						Core.instance().camera().direction = Direction.RIGHT;
					
					m_renderView.invalidate();
					
				}
			}
        	
        });
        m_turnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (Core.instance().currentState() == Core.STATE_WALK_AROUND) {
				
					if (Core.instance().camera().direction == Direction.RIGHT)
						Core.instance().camera().direction = Direction.DOWN;
					else if (Core.instance().camera().direction == Direction.LEFT)
						Core.instance().camera().direction = Direction.UP;
					else if (Core.instance().camera().direction == Direction.UP)
						Core.instance().camera().direction = Direction.RIGHT;
					else if (Core.instance().camera().direction == Direction.DOWN)
						Core.instance().camera().direction = Direction.LEFT;
					
					m_renderView.invalidate();
					
				}
			}
        	
        });
        
        m_up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Core core = Core.instance();
				
				if (core.currentState() == Core.STATE_WALK_AROUND) {
				
					int tempX = core.camera().x;
					int tempY = core.camera().y;
					
					if (core.camera().direction == Direction.RIGHT)
						core.camera().x++;
					if (core.camera().direction == Direction.LEFT)
						core.camera().x--;
					if (core.camera().direction == Direction.UP)
						core.camera().y--;
					if (core.camera().direction == Direction.DOWN)
						core.camera().y++;
					
					if (core.currentLevel().isSolid(core.camera().x, core.camera().y)) {
						core.camera().x = tempX;
						core.camera().y = tempY;
					}
					
					if (core.camera().x == 3 && core.camera().y == 2) {
						core.startMessage("A test message.\nTest!");
					}
					
					m_renderView.invalidate();
					
				}
			}
        	
        });

        m_down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Core core = Core.instance();
				
				if (core.currentState() == Core.STATE_WALK_AROUND) {
				
					int tempX = core.camera().x;
					int tempY = core.camera().y;
					
					if (core.camera().direction == Direction.RIGHT)
						core.camera().x--;
					if (core.camera().direction == Direction.LEFT)
						core.camera().x++;
					if (core.camera().direction == Direction.UP)
						core.camera().y++;
					if (core.camera().direction == Direction.DOWN)
						core.camera().y--;
					
					if (core.currentLevel().isSolid(core.camera().x, core.camera().y)) {
						core.camera().x = tempX;
						core.camera().y = tempY;
					}
					
					m_renderView.invalidate();
				
				}
			}
        	
        });
        
    }
}