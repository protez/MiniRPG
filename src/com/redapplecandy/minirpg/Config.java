package com.redapplecandy.minirpg;

/**
 * A class containing different values for UI placement and so
 * on.
 * @author tomasbrannstrom
 */
public class Config {
	/**
	 * Width and height of HVGA is 480x320 in landscape mode.
	 * HVGA is used as the base screen for this game.
	 */
	public static final int BASE_WIDTH = 480;
	public static final int BASE_HEIGHT = 320;
	
	public static final int MAIN_WINDOW_WIDTH = 320;
	public static final int MAIN_WINDOW_HEIGHT = 200;
	
	public static final int VIEW_CORNER_X = BASE_WIDTH / 2 - MAIN_WINDOW_WIDTH / 2;
	public static final int VIEW_CORNER_Y = 0;
	
	public static final int TILE_WIDTH = 96;
	public static final int TILE_HEIGHT = 192;
	
	public static final int FAR_TILE_WIDTH = 48;
	
	public static final int STATUS_BAR_POS = MAIN_WINDOW_HEIGHT + 16;
	
	public static final int MESSAGE_BOX_HEIGHT = 80;
	
	//public static final int MIDDLE_TILE_POS = 96;
	
	public static final int TEXTURE_SIZE = 64;
	
	public static final long FPS = 60;
}
