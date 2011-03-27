package com.redapplecandy.minirpg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    
	RenderView m_renderView;
	Button m_up;
	Button m_down;
	
	Button m_turnLeft;
	Button m_turnRight;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        m_renderView = (RenderView) findViewById(R.id.gameView);
        BitmapLoader.instance().setContext(this);
        TileManager.instance();
        
        m_renderView.createFeature();
        
        m_up = (Button) this.findViewById(R.id.buttonUp);
        m_down = (Button) this.findViewById(R.id.buttonDown);
        
        m_turnLeft = (Button) findViewById(R.id.buttonUpLeft);
        m_turnRight = (Button) findViewById(R.id.buttonUpRight);
        
        m_up.setText("Forwards");
        m_down.setText("Backwards");
        m_turnLeft.setText("Left");
        m_turnRight.setText("Right");
        
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