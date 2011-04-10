package com.redapplecandy.minirpg.ui;

import com.redapplecandy.minirpg.Pair;
import com.redapplecandy.minirpg.util.MathUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View.OnClickListener;

public class BitmapButton extends InvisibleButton {

	private Bitmap m_bitmap;
	private Matrix m_drawMatrix = new Matrix();
	
	public BitmapButton(Bitmap bmp, int x, int y) {
		super(x, y);
		m_bitmap = bmp;
		
		m_width = bmp.getWidth();
		m_height = bmp.getWidth();
	}
	
	public BitmapButton(Bitmap bmp, int x, int y, int padding) {
		super(x, y, padding);
		m_bitmap = bmp;
		
		m_width = bmp.getWidth();
		m_height = bmp.getWidth();
	}
	
	public void draw(Canvas canvas) {
		m_drawMatrix.setTranslate(
			MathUtils.scaleX(m_x, canvas.getWidth()),
			MathUtils.scaleY(m_y, canvas.getHeight()));
		
		Pair<Float, Float> scale = MathUtils.scaleFactor(canvas.getWidth(), canvas.getHeight());
		m_drawMatrix.postScale(scale.fst, scale.snd);
		canvas.drawBitmap(m_bitmap, m_drawMatrix, null);
		//canvas.drawBitmap(m_bitmap, m_x, m_y, null);
	}
	
}
