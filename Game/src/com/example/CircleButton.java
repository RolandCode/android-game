package com.example;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class CircleButton extends Circle {
    Sound suoni;
    private boolean soundFlag = false;
    private boolean callbackFlag = false;
    public int color = Color.argb(30, 255, 255, 255),
               originalColor = Color.argb(30, 255, 255, 255);
	public interface Callback {
		public void execute();
	}
	
	private String text;
	private Callback callback;
	
	CircleButton(PhysicsEngine engine, Vector center, float radius, String string) {
		super(engine, center, radius);
		this.text = string;

        suoni = new Sound();

        suoni.add("Click", R.raw.click);
    }
	
	@Override
    protected void gestisciEventi(float x, float y, int evento){
        switch (evento){
            case MotionEvent.ACTION_DOWN:
                if (this.isTouched(x, y)) {
                   giveFeedback();
                   callbackFlag = false;
                }
                break;

            case MotionEvent.ACTION_UP:
                soundFlag = false;
                this.color = this.originalColor;
                if (this.isTouched(x, y) && !callbackFlag)
                    executeCallback();
                callbackFlag = true;
                break;

            case MotionEvent.ACTION_MOVE:
                if (this.isTouched(x, y))
                    this.color = Color.argb(70, 255, 0, 0);
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
        paint.setARGB(100, 234, 234, 235);
        paint.setTextSize(14);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, getCenter().x, getCenter().y, paint);
    }

    protected void drawThis(Canvas canvas, Vector position) {
        CircleBody circleBody = (CircleBody) body;
        Paint paint = new Paint();
        paint.setColor(Color.argb(100, 0, 0, 0));
        canvas.drawCircle(position.x, position.y, circleBody.radius, paint);
        paint.setColor(this.color);
        canvas.drawCircle(position.x, position.y, circleBody.radius-1.5f, paint);
    }

    public void executeCallback() {
        if(callback != null)
        callback.execute();
    }

    public void giveFeedback() {
        this.color = Color.argb(70, 255, 0, 0);
        if(!soundFlag)suoni.playSound("Click");
        soundFlag = true;
    }

    public void setCallback(Callback callback) {
		this.callback = callback;
	}

}
