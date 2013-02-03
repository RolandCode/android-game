package com.example;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class CircleButton extends Circle {
	
	public interface Callback {
		public void execute();
	}
	
	private String text;
	private Callback callback;
	
	CircleButton(PhysicsEngine engine, Vector center, float radius, String string) {
		super(engine, center, radius);
		this.text = string;
	}
	
	@Override
    protected void gestisciEventi(float x, float y, int evento){
        switch (evento){
            case MotionEvent.ACTION_DOWN:
                if (this.isTouched(x, y))
                	executeCallback();
                break;
            default:
            	return;
        }
    }
    
    @Override
    public void draw(Canvas canvas){
        this.drawThis(canvas, body.getCenter());
        Paint paint = new Paint();
        paint.setARGB(255, 234, 34, 35);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, getCenter().x, getCenter().y, paint);
    }
	
	public void executeCallback() {
		if(callback != null)
			callback.execute();
	}
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}

}
