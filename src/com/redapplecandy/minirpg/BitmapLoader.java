package com.redapplecandy.minirpg;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class BitmapLoader {

	private static BitmapLoader m_instance = null;
	private Context m_context;
	
	private BitmapLoader() {
		
	}
	
	public void setContext(Context context) {
		m_context = context;
	}
	
	static public BitmapLoader instance() {
		if (m_instance == null) {
			m_instance = new BitmapLoader();
		}
		return m_instance;
	}
	
	public Bitmap loadById(int id) {
		InputStream is = m_context.getResources().openRawResource(id);
		Bitmap bmp = BitmapFactory.decodeStream(is);
		Bitmap bmp565 = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.RGB_565);
		
		bmp565 = bmp.copy(Bitmap.Config.RGB_565, true);
		
		return bmp565;
	}
	
}
