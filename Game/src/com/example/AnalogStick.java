package com.example;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class AnalogStick{
     boolean active = true;
     Vector position;
	 public Vector initposition;
	 float radius;
	 Paint paint;
	 
	 public Vector axis = new Vector();
	 
	 public boolean manual = true;
	 
	 public Vector move = new Vector();
	 
	 int id = 0;
	 
	 boolean pressed = false;
     
	 EventsEngine events;

	 public AnalogStick(Vector position, float radius){
		 paint = new Paint();
		 events = new EventsEngine();
		 this.radius = radius;
		 this.position = position;
		 this.initposition = position.clone();

		 events.setFingersActions(new EventsEngine.FingersAct(){
				 public void onActUp(int id){ actUp(id); }
				 public void onActDown(int index){ actDown(index); }	
			 });
	 }
	 
	 void actUp(int id){
		 if(id == this.id && manual){
			 pressed = false;
			 move.setModule(0);
		 }
	 }
	 
	 void actDown(int index){
		 if(isTouched(events.x[index], events.y[index]) && manual){
			 id = index;
			 pressed = true;
		 }
	 }
	 
	 boolean isTouched(float x, float y){
		 float Dx = position.x - x;
		 float Dy = position.y - y;
		 return Math.hypot(Dx, Dy) < radius;
	 }
	 
     public void draw(Canvas canvas, MotionEvent e){
		 events.turnON(e);
		 if(!active)paint.setColor(Color.argb(100, 0, 0, 0));
		 else paint.setColor(Color.argb(130, 0, 0, 0));
		 canvas.drawCircle(initposition.x, initposition.y, radius, paint);
		 paint.setColor(Color.argb(150, 255, 255, 255));
    	 canvas.drawCircle(initposition.x, initposition.y, radius-2, paint);
//       move.draw(canvas, initposition, Color.WHITE);
		 if(!active)paint.setColor(Color.argb(200, 150, 150, 150));
		 else paint.setColor(Color.argb(130, 0, 0, 0));
    	 canvas.drawCircle(position.x, position.y, radius*0.8f, paint);
		 if(!pressed)paint.setColor(Color.argb(255, 255, 255, 255));
		 else paint.setColor(Color.argb(130, 255, 0, 0));
		 canvas.drawCircle(position.x, position.y, radius*0.8f-2, paint);
         gestioneMovimento();
	 }
	 
	 void gestioneMovimento(){
		 if(manual){
	    	 if(pressed){			
		     	 move.x = events.x[id] - initposition.x;
		    	 move.y = events.y[id] - initposition.y;
		    	 if(move.getModule() >= radius*0.7f)
			    	 move.setModule(radius*0.7f);
		    	 if(!events.aliveID(id)) actUp(id);
	    	 }	 
	     }
		 
		 else{
			 pressed = true;
			 move = new Vector(/*-MainActivity.accelerometerValues[0], MainActivity.accelerometerValues[1]*/);
			 move.setModule((radius/10)*move.getModule());
			 if(move.getModule() >= radius)
				 move.setModule(radius);
		 }
		 
		 position.x = initposition.x + move.x;
		 position.y = initposition.y + move.y;
		 
		 axis.x = (move.x / (radius * 0.7f)) * 100;
		 axis.y = -(move.y / (radius * 0.7f)) * 100;
		 
	 }
}
