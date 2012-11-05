#include <jni.h>
#include <android/bitmap.h>
#include <android/log.h>

#include <math.h>
#include <stdlib.h>

#define  LOG_TAG    "libraycast"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)


// width * y + x

JNIEnv* currentEnv = NULL;

typedef struct _Texture {
	AndroidBitmapInfo info;
	jobject bitmap;
} Texture;

static Texture* textureTable;

static const int TEXTURE_SIZE = 64;

int mapWidth, mapHeight;

typedef struct _Vec2 {
	float x, y;
} Vec2;

typedef struct _Camera {
	Vec2 pos, dir, plane;
} Camera;

typedef struct _RayInfo {
	float wallDist;
	int mapX, mapY;
	float floorXWall, floorYWall;
	int textureX;
	int tileId;
} RayInfo;

void extractRgb565(uint16_t color, uint8_t* r, uint8_t* g, uint8_t* b)
{
    static uint16_t red_mask = 0xF800;
    static uint16_t green_mask = 0x7E0;
    static uint16_t blue_mask = 0x1F;

    *r = (color & red_mask) >> 11;
    *g = (color & green_mask) >> 5;
    *b = (color & blue_mask);
}

uint16_t composeRgb565(uint16_t r, uint16_t g, uint16_t b)
{
    return (r << 11) | (g << 5) | b;
}

uint16_t computeIntensity(uint16_t pixel, float objectIntensity, float multiplier, float distance) 
{
    float intensity = objectIntensity / distance * multiplier;

    uint8_t r, g, b;
    extractRgb565(pixel, &r, &g, &b);

    float fr = (float) r;
    float fg = (float) g;
    float fb = (float) b;

    fr *= intensity;
    fg *= intensity;
    fb *= intensity;

    r = (uint8_t) fr;
    g = (uint8_t) fg;
    b = (uint8_t) fb;

    return composeRgb565(r, g, b);
}

RayInfo castRay(Camera, int, jint*, int, int);

void raycast(Camera camera, 
	AndroidBitmapInfo* bmpInfo, void* bmpPixels,
	jint* tileMap, jint* floorMap, jint* ceilMap) 
{
	int x, y;
	
	Texture* wallTexture;
	Texture* floorTexture;
	Texture* ceilTexture;

	void* wallPixels;
	void* floorPixels;
	void* ceilPixels;

	for (x = 0; x < bmpInfo->width; x++) {
		// Point to start of bmpPixels
		void* currentPixels = bmpPixels;

		int lineHeight;
		float wallDist, cameraDist = 0.0f, currentDist;
		int wallStart, wallEnd;
		
		RayInfo info = castRay(camera, x, tileMap, bmpInfo->width, bmpInfo->height);
		int textureId = info.tileId - 1;	// 0 is "emtpy"

		// TODO: Shall depend on textur ID in tilemap.
		wallTexture = &textureTable[textureId];

		AndroidBitmap_lockPixels(currentEnv, wallTexture->bitmap, &wallPixels);

		// m_zbuffer[x] = info.wallDist;
		
		lineHeight = (int)fabs((float)bmpInfo->height / info.wallDist);
		wallStart = -lineHeight / 2 + bmpInfo->height / 2;
		wallEnd = lineHeight / 2 + bmpInfo->height / 2;
		if  (wallStart < 0) wallStart = 0;
		if (wallEnd >= bmpInfo->height) wallEnd = bmpInfo->height - 1;
		
		// Set pointer to current line
		currentPixels = (char*) currentPixels + bmpInfo->stride * wallStart;
		for (y = wallStart; y < wallEnd; y++) {
			uint16_t* line = (uint16_t *) currentPixels;
			
			int d, textureY;
			
			d = y * 256 - bmpInfo->height * 128 + lineHeight * 128;
			textureY = ((d * TEXTURE_SIZE) / lineHeight) / 256;

			void* texturePointer = wallPixels;
			texturePointer = (char*)texturePointer + wallTexture->info.stride * textureY;
			uint16_t* textureLine = (uint16_t*) texturePointer;
			
			uint16_t color = textureLine[info.textureX];
			
			line[x] = computeIntensity(color, 0.5, 1.0, info.wallDist);
			
			// Advance pointer.
			currentPixels = (char*)currentPixels + bmpInfo->stride;
		}
	
		
		if (wallEnd < 0) {
			wallEnd = bmpInfo->height;
		}

		// Reset pointer
		currentPixels = bmpPixels;
		// Point to the line where the wall ends.
		currentPixels = (char*) currentPixels + bmpInfo->stride * (wallEnd + 1);		
		for (y = wallEnd + 1; y < bmpInfo->height; y++) {
			uint16_t* line = (uint16_t *) currentPixels;

			
			int floorTextureX, floorTextureY;
			float weight;
			float currentFloorX, currentFloorY;
			int textureIndex;

			currentDist = (float)bmpInfo->height / (2.0f * y - bmpInfo->height);
			
			weight = (currentDist - cameraDist) / (info.wallDist - cameraDist);
			currentFloorX = weight * info.floorXWall + (1.0f - weight) * camera.pos.x;
			currentFloorY = weight * info.floorYWall + (1.0f - weight) * camera.pos.y;
			floorTextureX = (int)(currentFloorX * TEXTURE_SIZE) % TEXTURE_SIZE;
			floorTextureY = (int)(currentFloorY * TEXTURE_SIZE) % TEXTURE_SIZE;
			
			textureIndex = (int)currentFloorY * mapWidth + (int)currentFloorX;

			floorTexture = &textureTable[floorMap[textureIndex] - 1];
			ceilTexture = &textureTable[ceilMap[textureIndex] - 1];
			AndroidBitmap_lockPixels(currentEnv, floorTexture->bitmap, &floorPixels);
			AndroidBitmap_lockPixels(currentEnv, ceilTexture->bitmap, &ceilPixels);

			// Floor
			void* texturePointer = floorPixels;
			texturePointer = (char*)texturePointer + floorTexture->info.stride * floorTextureY;
			uint16_t* textureLine = (uint16_t*) texturePointer;
			
			uint16_t color = textureLine[floorTextureX];

			line[x] = computeIntensity(color, 0.5, 1.0, currentDist);

			// Ceiling
			texturePointer = ceilPixels;
			texturePointer = (char*)texturePointer + ceilTexture->info.stride * floorTextureY;
			textureLine = (uint16_t*)texturePointer;

			color = textureLine[floorTextureX];

			void* ceilLoc = bmpPixels;
			ceilLoc = (char*)ceilLoc + bmpInfo->stride * (bmpInfo->height - y);
			line = (uint16_t *) ceilLoc;
			line[x] = computeIntensity(color, 0.5, 1.0, currentDist);
	
			currentPixels = (char*)currentPixels + bmpInfo->stride;

			AndroidBitmap_unlockPixels(currentEnv, floorTexture->bitmap);
			AndroidBitmap_unlockPixels(currentEnv, ceilTexture->bitmap);
		}
	
		AndroidBitmap_unlockPixels(currentEnv, wallTexture->bitmap);

	}
	
}

RayInfo castRay(Camera camera, int x, jint* tileMap, int width, int height) {
	int mapX, mapY;
	float sideDistX, sideDistY;
	float ddx, ddy;
	float wallDist;
	int stepX, stepY;
	int side;
	float wallX;
	int textureX;
	float floorXWall, floorYWall;
	
	float camX = 2.0f * (float)x / (float)width - 1.0f;
	Vec2 ray = camera.pos;
	Vec2 rayDir = { camera.dir.x + camera.plane.x * camX,
					camera.dir.y + camera.plane.y * camX };
	
	mapX = (int) ray.x;
	mapY = (int) ray.y;
	
	ddx = sqrt(1.0f + (rayDir.y * rayDir.y) / (rayDir.x * rayDir.x));
	ddy = sqrt(1.0f + (rayDir.x * rayDir.x) / (rayDir.y * rayDir.y));
	
	if (rayDir.x < 0) {
		stepX = -1;
		sideDistX = (ray.x - mapX) * ddx;
	} else {
		stepX = 1;
		sideDistX = (mapX + 1.0f - ray.x) * ddx; 
	}
	
	if (rayDir.y < 0) {
		stepY = -1;
		sideDistY = (ray.y - mapY) * ddy;
	} else {
		stepY = 1;
		sideDistY = (mapY + 1.0f - ray.y) * ddy;
	}
	
	while (1) {
		if (sideDistX < sideDistY) {
			sideDistX += ddx;
			mapX += stepX;
			side = 0;
		} else {
			sideDistY += ddy;
			mapY += stepY;
			side = 1;
		}
		
		if (tileMap[mapY * mapWidth + mapX] != 0) {
			break;
		}
	}
	
	if (side == 0) {
		wallDist = fabs((mapX - ray.x + (1.0f - stepX) / 2.0f) / rayDir.x);
		wallX = ray.y + ((mapX - ray.x + (1.0f - stepX) / 2.0f) / rayDir.x) * rayDir.y;
		wallX -= floor(wallX);
		
		textureX = (int)(wallX * (float)TEXTURE_SIZE);
		if (rayDir.x > 0) {
			textureX = TEXTURE_SIZE - textureX - 1;
		}
	} else {
		wallDist = fabs((mapY - ray.y + (1.0f - stepY) / 2.0f) / rayDir.y);
		wallX = ray.x + ((mapY - ray.y + (1.0f - stepY) / 2.0f) / rayDir.y) * rayDir.x;
		wallX -= floor(wallX);
		
		textureX = (int)(wallX * (float)TEXTURE_SIZE);
		if (rayDir.y < 0) {
			textureX = TEXTURE_SIZE - textureX - 1;
		}			
	}
	
	if (side == 0 && rayDir.x > 0) {
		floorXWall = mapX;
		floorYWall = mapY + wallX;
	} else if (side == 0 && rayDir.x < 0) {
		floorXWall = mapX + 1.0f;
		floorYWall = mapY + wallX;
	} else if (side == 1 && rayDir.y > 0) {
		floorXWall = mapX + wallX;
		floorYWall = mapY;
	} else {
		floorXWall = mapX + wallX;
		floorYWall = mapY + 1.0f;
	}
	
	RayInfo info = {
		wallDist,
		mapX,
		mapY,
		floorXWall,
		floorYWall,
		textureX,
		tileMap[mapY * mapWidth + mapX]
	};
	
	return info;
}



/**
 * JNI Export Function
 */
JNIEXPORT void JNICALL Java_com_redapplecandy_minirpg_graphics_Raycaster_raycast
	(JNIEnv* env, jobject obj, jobject bitmap,
	 jintArray _tileMap, jintArray _floorMap, jintArray _ceilMap, jint width, jint height,
	 jfloat camX, jfloat camY, jfloat camDirX, jfloat camDirY, jfloat camPlaneX, jfloat camPlaneY)
{
	currentEnv = env;

//	LOGE("*** RAYCAST ***");
	AndroidBitmapInfo bitmapInfo;

	void* pixels;


//	LOGE("*** GETTING BITMAP INFO ***");	
	AndroidBitmap_getInfo(env, bitmap, &bitmapInfo);

	if (bitmapInfo.format != ANDROID_BITMAP_FORMAT_RGB_565) {
		int fmt = bitmapInfo.format;
		LOGE("*** BITMAP WRONG FORMAT ***: %s", 
			fmt == 0 ? 
				"NONE" : 
			fmt == 1 ? 
				"RGBA_8888" : 
			fmt == 4 ? 
				"RGB_565" : 
			fmt == 7 ? 
				"RGBA_4444" : 
			fmt == 8 ? 
				"A_8" : 
			"ERROR" );
		return;
	}
	
//	LOGE("*** LOCKING PIXELS .... ***");	
	AndroidBitmap_lockPixels(env, bitmap, &pixels);

//	LOGE("*** ... PIXELS LOCKED ***");	
	
	Camera camera = { 
		{camX, camY}, 
		{camDirX, camDirY}, 
		{camPlaneX, camPlaneY} 
	};
	
	mapWidth = width;
	mapHeight = height;
	
//	LOGE("***  GETTING INT ARRAY ELEMENTS ***");
	jint* tileMap = (*env)->GetIntArrayElements(env, _tileMap, NULL);
	jint* floorMap = (*env)->GetIntArrayElements(env, _floorMap, NULL);
	jint* ceilMap = (*env)->GetIntArrayElements(env, _ceilMap, NULL);

//	LOGE("***  CALLING RAYCAST ***");	
	raycast(camera, 
		&bitmapInfo, pixels, 
		tileMap, floorMap, ceilMap);

//	LOGE("***  UNLOCKING PIXELS ***");

	AndroidBitmap_unlockPixels(env, bitmap);
	
//	LOGE("*** RELEASING ARRAY ***");
	(*env)->ReleaseIntArrayElements(env, _tileMap, tileMap, 0);
	(*env)->ReleaseIntArrayElements(env, _floorMap, floorMap, 0);
	(*env)->ReleaseIntArrayElements(env, _ceilMap, ceilMap, 0);
	
//	LOGE("*** ALL DONE ***");
}

JNIEXPORT void JNICALL Java_com_redapplecandy_minirpg_graphics_Raycaster_reserveTextureSpace
	(JNIEnv* env, jobject obj, jint numberOfTextures)
{
	LOGI("Reserving texture space for %d textures", (int)numberOfTextures);

	textureTable = calloc((int)numberOfTextures, sizeof(Texture));
}

JNIEXPORT void JNICALL Java_com_redapplecandy_minirpg_graphics_Raycaster_registerTexture
	(JNIEnv* env, jobject obj, jint textureNumber, jobject texture)
{
	AndroidBitmapInfo textureInfo;

	AndroidBitmap_getInfo(env, texture, &textureInfo);

	if (textureInfo.format != ANDROID_BITMAP_FORMAT_RGB_565) {
		int fmt = textureInfo.format;
		LOGE("*** BITMAP WRONG FORMAT ***: %s", 
			fmt == 0 ? 
				"NONE" : 
			fmt == 1 ? 
				"RGBA_8888" : 
			fmt == 4 ? 
				"RGB_565" : 
			fmt == 7 ? 
				"RGBA_4444" : 
			fmt == 8 ? 
				"A_8" : 
			"ERROR" );
		return;
	}

	LOGI("Storing texture at position %d in texture table.", textureNumber);

	Texture _texture;
	_texture.info = textureInfo;
	_texture.bitmap = (*env)->NewGlobalRef(env, texture);	// TODO: Must be freed later with DeletGlobalRef!

	textureTable[textureNumber] = _texture;
}
