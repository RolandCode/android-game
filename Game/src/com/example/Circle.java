package com.example;

import android.graphics.*;
import android.view.*;
import java.util.*;

public class Circle extends Sprite {
	
    public int color = Color.BLACK, originalColor = Color.BLACK;
    
    private Paint paint = new Paint();
    public ArrayList<Circle> childrens = new ArrayList<Circle>();
 
    public boolean alive = true;
	protected boolean threadFlag = false;
	public float r = 50;
	public LancioElastico lancio = new LancioElastico(this);
	public int id = 8;
	int[] T = new int[10];
	
	EventsEngine events = new EventsEngine();
	

    public Circle(PhysicsEngine engine) {
        CircleBody body = new CircleBody(engine, this, new Vector(), 50);
        body.radius = 50;
		r = 50;
        this.body = body;
		defineTouchEvents();
    }
    public Circle(PhysicsEngine engine,  float radius) {
        CircleBody body = new CircleBody(engine, this, new Vector(), radius);
        body.radius = radius;
		r = radius;
        this.body = body;
		defineTouchEvents();
    }

    public Circle(PhysicsEngine engine,  float radius, int color) {
        CircleBody body = new CircleBody(engine, this, new Vector(), radius); 
        body.radius = radius;
        this.color = color;
		defineTouchEvents();
		r = radius;
    }

    public Circle(PhysicsEngine engine, Vector center,  float radius, int color) {
        CircleBody body = new CircleBody(engine, this, center, radius); 
        body.radius = radius;
        this.color = color;
		defineTouchEvents();
		r = radius;
    }

    public Circle(PhysicsEngine engine, Vector center,  float radius) {
        CircleBody body = new CircleBody(engine, this, center, radius); 
        body.radius = radius;
		defineTouchEvents();
		r = radius;
    }

    public Circle(PhysicsEngine engine, Vector center, float radius, int color, Vector speed) {
        CircleBody body = new CircleBody(engine, this, center, radius); 
        body.radius = radius;
        body.speed = speed;
        this.color = color;
        originalColor = color;
		defineTouchEvents();
		r = radius;
    }
	
	void defineTouchEvents(){
		events.setFingersActions(new EventsEngine.FingersAct(){
				public void onActUp(int id){ actUp(id);	}
				public void onActDown(int index){ actDown(index); }	
			});
			
	//	for (int i = 0
	}
	
	void actUp(int id){
		if(id == this.id){
			if(lancio.isCharging()) lancio.sayRelased();
		}
	}

	void actDown(int index){
		if(isTouched(events.x[index], events.y[index])){
			id = index;
			lancio.sayPressed();
		}
	}

    public void draw(Canvas canvas, MotionEvent e){
		lancio.draw(canvas);
        drawThis(canvas, body.getCenter());
		events.turnON(e);
		if(launching){		
			lancio.sayCharging(events.x[id], events.y[id]);
			if(!events.aliveID(id))lancio.sayRelased();
		}
    }
    
    @Override
    public void processEvent(float[] eventInformation) {
    	this.gestisciEventi(eventInformation[0], eventInformation[1], (int)eventInformation[2]);
    }

    protected void drawThis(Canvas canvas, Vector position) {
    	CircleBody circleBody = (CircleBody) body;
//		(new Vector(body.getAcceleration().getModule()*1000000, (float)Math.toDegrees(body.getAcceleration().getArg()), true)).draw(canvas, getCenter(), Color.argb(100, 255, 255, 255));
	//	(new Vector(body.getAcceleration().getModule()*(-1000000), (float)Math.toDegrees(body.getAcceleration().getArg()), true)).draw(canvas, getCenter(), Color.argb(100, 255, 255, 255));
		paint.setColor(Color.argb(90, 0, 255, 0));
/*		canvas.drawLine(0, getCenter().y, 1000, getCenter().y, paint);
		canvas.drawLine(getCenter().x, 0, getCenter().x, 1000, paint);
		canvas.drawLine(getCenter().x, getCenter().y, 0, 0, paint);
		canvas.drawLine(getCenter().x, getCenter().y, 0, 800, paint);
		canvas.drawLine(getCenter().x, getCenter().y, 480, 800, paint);
		canvas.drawLine(getCenter().x, getCenter().y, 480, 0, paint);
*/		paint.setColor(Color.WHITE);
        canvas.drawCircle(position.x, position.y, circleBody.radius, paint);
        paint.setColor(this.color);
        canvas.drawCircle(position.x, position.y, circleBody.radius-1.5f, paint);
		paint.setColor(Color.GREEN);
		paint.setTextSize((int)(r*0.4));
		paint.setTextAlign(Paint.Align.CENTER);
	//	body.getSpeed().draw(canvas, new Vector(getCenter().getModule(), (float)Math.toDegrees(getCenter().getArg()), true), Color.argb(150, 0, 0, 255));

//		(new Vector(body.getAcceleration().getModule()*100, (float)Math.toDegrees(body.getAcceleration().getArg()), true)).draw(canvas,  new Vector(getCenter().getModule(), (float)Math.toDegrees(getCenter().getArg()), true), Color.argb(150, 255, 0, 0));
		
	//	canvas.drawText("("+(int)getCenter().x+", "+(int)getCenter().y+")", position.x, position.y, paint);
    }

    private void drawLancio(Canvas canvas){

    }

    public void createChildrens(int number){
        for(int i = 0; i < number; i++){
            this.childrens.add(new Circle(
                    ((CircleBody)body).phEngine,
                    getCenter(),
                    ((CircleBody)body).radius/number,
                    this.color,
                    this.body.getSpeed().div(number)));
        }
    }

    public void destroy(int child){
        this.createChildrens(child);
        this.alive = false;
    }

    protected void gestisciEventi(float x, float y, int evento){
        switch (evento){
            case MotionEvent.ACTION_DOWN:
                if (this.isTouched(x, y)) this.gestisciLancio(x, y, 0);
                break;
            case MotionEvent.ACTION_MOVE:
                if (this.lancio.isCharging()) this.gestisciLancio(x, y, 1);
                break;
            case MotionEvent.ACTION_UP:
                if (this.lancio.isCharging()) this.gestisciLancio(x, y, 2);
                break;
        }
    }

    private void ripristinaColore(final int t, final Circle other){
        new Thread(
                new Runnable(){
                    public void run(){
                        try {
                            threadFlag  = true;
                            Thread.sleep(t);
                            color = originalColor;
                            other.color = other.originalColor;
                            threadFlag = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }

    public void gestisciLancio(float x, float y, int fase){
        switch (fase){
            case 0:
                this.lancio.sayPressed();
                break;
            case 1:
                this.lancio.sayCharging(x, y);
                break;
            case 2:
                this.lancio.sayRelased();
                break;
        }
    }

    public boolean isTouched(float x, float y){
       return UtilCollisioni.isTouched(new Vector(x, y), this);
    }

}
