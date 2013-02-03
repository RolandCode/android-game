package com.example;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.Display;

public class Game {
	Activity activity;
	Canvas canvas;
	
	private Game() {}
	
	private static class Singleton {
		private final static Game instance = new Game();
	}
	
	public static void initialize(Activity activity) {
		Singleton.instance.activity = activity;
	}
	
	public static Game getInstance() {
		return Singleton.instance;
	}
	
	public Vector getScreenSize() {
		Display display = activity.getWindowManager().getDefaultDisplay();
		return new Vector(display.getWidth(), display.getHeight());
	}
	
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public Activity getActivity() {
		return activity;
	}
	
}