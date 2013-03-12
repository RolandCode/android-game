package com.example;

import android.graphics.*;
import android.view.*;

public class SwitchButton extends Button
{

	public interface Callback{
		public void on();
		public void off();
	}

	Callback callback;
    public int color = Color.argb(255, 255, 255, 255);
    public	boolean pressed, longPressed = false;
    boolean done = false;
	boolean[] timersFlag = {false, false};
	boolean loopFlag = false;
	boolean manual = false;
	public int[] timers = {0, 0};
	
    public SwitchButton(Vector position, float width, float height, String text){
		super(position, width, height, text);
		pressed = false;
	}
	
    void actUp(int id){
		if(id == this.id && done){
			done = false;
			if(isTouched(events.x[id], events.y[id]) || manual){
				if(!pressed){
					pressed = true;
					color = Color.argb(170, 0, 255, 0);
					if(callback != null) callback.on();
					if(duringCallback != null && !loopFlag) callbackLoop();
					if(!timersFlag[1]) startTimer(1);
				}
				else{
					pressed = false;
					if (callback != null) callback.off();
				}
			}
			
			else{
				if(color == originalColor) color = Color.argb(150, 0, 255, 0);
				else color = originalColor;
			}
		}
		manual = false;
	}

	void actDown(int index){
		if((isTouched(events.x[index], events.y[index]) || manual)){
		if(!done){
			id = index;
			if(!pressed){
				color = Color.argb(125, 255, 0, 0);
			}
			else{
				color = originalColor;
			}
			giveFeedback();
			done = true;
		}
		}
	}
	
    public void	manualSwitch(){
		manual = true;
		actDown(0);
		actUp(0);
	}

	public void setCallback(Callback callback){
		this.callback = callback;
	}
	
    @Override
    public void draw(Canvas canvas, MotionEvent e){
		events.turnON(e);
		if(pressed && !events.aliveID(id))actUp(id);
		Paint paint = new Paint();
		if(!active)paint.setARGB(200, 150, 150, 150);
		else paint.setARGB(120, 0, 0, 0);
		canvas.drawRoundRect(new RectF(position.x, position.y, position.x + width, position.y + height), 15, 15, paint);
		paint.setColor(color);
		canvas.drawRoundRect(new RectF(position.x + 0.7f, position.y + 0.7f, position.x + width - 0.7f, position.y + height - 0.7f), 17, 17, paint);
		if(!active)paint.setARGB(200, 150, 150, 150);
		else paint.setARGB(120, 0, 0, 0);
		paint.setTextSize(18);
		paint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(text, this.position.x+this.width/2, this.position.y+this.height/2, paint);
		if(time >= 2) longPressed = true;
	//	text = ""+longPressed;
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
	
	void startTimer(final int index){
        	new Thread(new Runnable(){
    			public void run(){
					switch (index){
						case 1:
							while(pressed){
								timersFlag[index] = true;
								try
								{
									Thread.sleep(1000);
									timers[index]++;
								}
								catch (InterruptedException e){}
							}
							timersFlag[index] = false;
							timers[index] = 0;
							break;

						case 0:
							while(isTouched(events.x[id], events.y[id])){
								timersFlag[index] = true;
								try
								{
									Thread.sleep(1000);
									timers[index]++;
								}
								catch (InterruptedException e){}
							}
							timersFlag[index] = false;
							timers[index] = 0;
							break;
						}
					}
				}).start();
	}
}
