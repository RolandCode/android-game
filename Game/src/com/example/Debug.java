package com.example;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Debug {
	
	public static void drawLine(Vector start, Vector end, Paint paint) {
		Canvas canvas = Game.getInstance().getCanvas();
		canvas.drawLine(start.x, start.y, end.x, end.y, paint);
	}
	
	public static void drawLine(Vector start, Vector end) {
		Paint paint = new Paint();
		paint.setARGB(1, 255, 255, 255);
		drawLine(start, end, paint);
	}
	
	public static void drawRectangle(Vector corners[], Paint paint) {
		drawLine(corners[0], corners[1], paint);
		drawLine(corners[1], corners[2], paint);
		drawLine(corners[2], corners[3], paint);
		drawLine(corners[3], corners[0], paint);
	}
	
	public static void drawRectangle(Vector corners[]) {
		Paint paint = new Paint();
		paint.setARGB(255, 255, 0, 255);
		drawRectangle(corners, paint);
	}

}
