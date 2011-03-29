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
        
        m_renderView.createFeature();
        
        /*
        m_up = (Button) this.findViewById(R.id.buttonUp);
        m_down = (Button) this.findViewById(R.id.buttonDown);
        
        m_turnLeft = (Button) findViewById(R.id.buttonUpLeft);
        m_turnRight = (Button) findViewById(R.id.buttonUpRight);
        
        m_up.setText("Forwards");
        m_down.setText("Backwards");
        m_turnLeft.setText("Left");
        m_turnRight.setText("Right");
        */
        
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
				if (m_renderView.curDir == Direction.RIGHT)
					m_renderView.curDir = Direction.UP;
				else if (m_renderView.curDir == Direction.LEFT)
					m_renderView.curDir = Direction.DOWN;
				else if (m_renderView.curDir == Direction.UP)
					m_renderView.curDir = Direction.LEFT;
				else if (m_renderView.curDir == Direction.DOWN)
					m_renderView.curDir = Direction.RIGHT;
				
				m_renderView.invalidate();
			}
        	
        });
        m_turnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (m_renderView.curDir == Direction.RIGHT)
					m_renderView.curDir = Direction.DOWN;
				else if (m_renderView.curDir == Direction.LEFT)
					m_renderView.curDir = Direction.UP;
				else if (m_renderView.curDir == Direction.UP)
					m_renderView.curDir = Direction.RIGHT;
				else if (m_renderView.curDir == Direction.DOWN)
					m_renderView.curDir = Direction.LEFT;
				
				m_renderView.invalidate();
			}
        	
        });
        
        m_up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (m_renderView.curDir == 0)
					m_renderView.curX++;
				if (m_renderView.curDir == 1)
					m_renderView.curX--;
				if (m_renderView.curDir == 2)
					m_renderView.curY--;
				if (m_renderView.curDir == 3)
					m_renderView.curY++;
				m_renderView.invalidate();
			}
        	
        });

        m_down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (m_renderView.curDir == 0)
					m_renderView.curX--;
				if (m_renderView.curDir == 1)
					m_renderView.curX++;
				if (m_renderView.curDir == 2)
					m_renderView.curY++;
				if (m_renderView.curDir == 3)
					m_renderView.curY--;
				m_renderView.invalidate();
			}
        	
        });
        
    }
}