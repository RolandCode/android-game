package com.example;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class Button{
    public boolean active    = true,
                   pressable = true,
				   loopFlag  = false,
				   timerFlag = false,
	               pressed   = false,
				   soundFlag = false;

	public int id = 0, time = 0,
	           color = Color.argb(255, 255, 255, 255),
               originalColor = color;
		   
	public float width, height;	
	
	public Vector position;

    Sound suoni;
	String text;
	Callback callback;
	DuringCallback duringCallback;
	EventsEngine events = new EventsEngine();
	
    public interface Callback {
		public void execute();
	}
	
	public interface DuringCallback{
		public void loop();
		public void stop();
	}
	
	public Button(Vector position, float width, float height, String text) {
		this.text = text;
		this.width = width;
		this.height = height;
		this.position = new Vector(position.x - width/2, position.y - height/2);
        defineEvents();
        suoni = new Sound();
        suoni.add("Click", R.raw.click);
    }

	
	void defineEvents(){
		events.setFingersActions(new EventsEngine.FingersAct(){
				public void onActUp(int id){ actUp(id);	}
				public void onActDown(int index){ actDown(index); }	
			});
	}
	
	void actUp(int id){
		if(id == this.id){
			executeCallback();
			pressed = false;
		}
	}
	
	void actDown(int index){
		if(isTouched(events.x[index], events.y[index])){
			id = index;
			pressed = true;
			playClick();
			if(duringCallback != null && !loopFlag) callbackLoop();
			if(!timerFlag) timer();
			giveFeedback();
		}
	}
	boolean isTouched(float x, float y)
	{
		return (new RectF(x, y, x+1, y+1)).intersect(this.position.x, this.position.y, this.position.x + this.width, this.position.y + this.height);
	}

    public void draw(Canvas canvas, MotionEvent e){
		events.turnON(e);
		if(pressed && !events.aliveID(id))actUp(id);
        Paint paint = new Paint();
		if(!active)paint.setARGB(200, 150, 150, 150);
		else paint.setARGB(120, 0, 0, 0);
		canvas.drawRoundRect(new RectF(position.x, position.y, position.x + width, position.y + height), 15, 15, paint);
		paint.setColor(color);
		canvas.drawRoundRect(new RectF(position.x + 0.7f, position.y + 0.7f, position.x + width - 0.7f, position.y + height - 0.7f), 17, 17, paint);
		paint.setARGB(130, 0, 0, 0);
        paint.setTextSize(18);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, this.position.x+this.width/2, this.position.y+this.height/2, paint);
    }
	
    public void executeCallback() {
		color = originalColor;
		if(isTouched(events.x[id], events.y[id]))
	    	if (callback != null) callback.execute();
    }

    public void giveFeedback() {
			this.color = Color.argb(125, 255, 0, 0);
    }
	
	void playClick(){
		if(!soundFlag)suoni.playSound("Click");
            soundFlag = true;		
	}

    public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public void setDuringCallback(DuringCallback duringCallback){
		this.duringCallback = duringCallback;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	void callbackLoop(){
		new Thread(new Runnable(){
				public void run(){
					while(pressed){
						loopFlag = true;
						duringCallback.loop();
					}
					duringCallback.stop();
					loopFlag = false;
				}
			}).start();
	}
	
	void timer(){
		new Thread(new Runnable(){
				public void run(){
					while(pressed){
						timerFlag = true;
						try
						{
							Thread.sleep(1000);
							time++;
						}
						catch (InterruptedException e){}
					}
					timerFlag = false;
					time = 0;
				}
			}).start();
	}

}
