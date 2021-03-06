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
		System.err.println("*** LOADING LIBRARY raycaster");
		System.loadLibrary("raycaster");
		System.err.println("*** Success");
	}
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        m_renderView = (RenderView) findViewById(R.id.gameView);
        BitmapLoader.instance().setContext(this);
        
        m_renderView.raycaster = new Raycaster(Config.MAIN_WINDOW_WIDTH, 200);
        Core.instance().setRenderView(m_renderView);
        
        //m_renderView.createFeature();
        
    	m_up = new InvisibleButton(0, 0, Config.BASE_WIDTH, 64);
//    	m_down = new InvisibleButton(0, Config.BASE_HEIGHT - 64, Config.BASE_WIDTH, 64);
    	
    	m_turnLeft = new InvisibleButton(0, 64, 64, Config.BASE_HEIGHT - 128);
    	m_turnRight = new InvisibleButton(Config.BASE_WIDTH - 64, 64, 64, Config.BASE_HEIGHT - 128);
        
    	m_renderView.addButton(m_up);
    	m_renderView.addButton(m_down);
    	m_renderView.addButton(m_turnLeft);
    	m_renderView.addButton(m_turnRight);
    	
        m_turnLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Core core = Core.instance();				
				if (Core.instance().currentState() == Core.STATE_WALK_AROUND) {
					
					core.turn(90, 5);
				}
			}
        	
        });
        m_turnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Core core = Core.instance();				
				if (Core.instance().currentState() == Core.STATE_WALK_AROUND) {

					core.turn(-90, -5);
				}
			}
        	
        });
        
        m_up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Core core = Core.instance();
				
				if (core.currentState() == Core.STATE_WALK_AROUND) {

					core.step(0.1f);
					
				}
			}
        	
        });

//        m_down.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Core core = Core.instance();
//				
//				if (core.currentState() == Core.STATE_WALK_AROUND) {
//					
//					core.step(-0.1f);
//				
//				}
//			}
//        	
//        });
        
    }
}