package com.example;

import android.graphics.*;
import android.view.*;
import java.util.*;

public class Circle extends Sprite {
	
    public int color = Color.BLACK, originalColor = Color.BLACK;
    
    private Paint paint = new Paint();
    public ArrayList<Circle> childrens = new ArrayList<Circle>();
    public LancioElastico lancio = new LancioElastico(this);
    public boolean alive = true;
	protected boolean threadFlag = false;

    public Circle(PhysicsEngine engine,  float radius) {
        CircleBody body = new CircleBody(engine, this, new Vector(), radius); 
        body.radius = radius;
        this.body = body;
    }

    public Circle(PhysicsEngine engine,  float radius, int color) {
        CircleBody body = new CircleBody(engine, this, new Vector(), radius); 
        body.radius = radius;
        this.color = color;
    }

    public Circle(PhysicsEngine engine, Vector center,  float radius, int color) {
        CircleBody body = new CircleBody(engine, this, center, radius); 
        body.radius = radius;
        this.color = color;
    }

    public Circle(PhysicsEngine engine, Vector center,  float radius) {
        CircleBody body = new CircleBody(engine, this, center, radius); 
        body.radius = radius;
    }

    public Circle(PhysicsEngine engine, Vector center, float radius, int color, Vector speed) {
        CircleBody body = new CircleBody(engine, this, center, radius); 
        body.radius = radius;
        body.speed = speed;
        this.color = color;
        this.originalColor = color;
    }

    @Override
    public void draw(Canvas canvas){
        this.drawThis(canvas, body.getCenter());
        this.drawLancio(canvas);
    }
    
    @Override
    public void processEvent(float[] eventInformation) {
    	this.gestisciEventi(eventInformation[0], eventInformation[1], (int)eventInformation[2]);
    }

    protected void drawThis(Canvas canvas, Vector position) {
    	CircleBody circleBody = (CircleBody) body;
        paint.setColor(Color.WHITE);
        canvas.drawCircle(position.x, position.y, circleBody.radius, paint);
        paint.setColor(this.color);
        canvas.drawCircle(position.x, position.y, circleBody.radius-1.5f, paint);
    }

    private void drawLancio(Canvas canvas){
        if(this.lancio.isCharging()){
            this.lancio.carica.mul(1000).draw(canvas, getCenter(), Color.argb(50, 0, 255, 0));
            this.lancio.carica.draw(canvas, getCenter(), Color.argb(100, 0, 255, 0));
        }
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
