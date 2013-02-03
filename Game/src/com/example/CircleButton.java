package com.example;

import android.graphics.Canvas;
import android.graphics.Color;
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
                    giveFeedback();
                break;

            case MotionEvent.ACTION_UP:
                this.color = this.originalColor;
                if (this.isTouched(x, y))
                    executeCallback();
                break;

            case MotionEvent.ACTION_MOVE:
                if (this.isTouched(x, y))
                    this.color = Color.BLUE;
                else this.color = this.originalColor;
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

    public void giveFeedback() {
        this.color = Color.BLUE;
    }

    public void setCallback(Callback callback) {
		this.callback = callback;
	}

}
