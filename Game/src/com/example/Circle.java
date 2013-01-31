package com.example;

import android.graphics.*;
import android.view.*;
import java.util.*;

public class Circle{
    public int color = Color.BLACK, originalColor = Color.BLACK;
    public float radius;
    public double mass;
    private Paint paint = new Paint();
    public ArrayList<Circle> childrens = new ArrayList<Circle>();
    public LancioElastico lancio = new LancioElastico(this);
    public boolean alive = true;
    public boolean locked = false;

    public Vettore speed = new Vettore();
    public Vettore acceleration = new Vettore();
    public Vettore position = new Vettore();

    private boolean threadFlag = false;

    public Circle(float radius){
        this.radius = radius;
    }
    public Circle(float radius, int color){
        this.radius = radius;
        this.color = color;
        this.originalColor = color;
    }
    public Circle(float x, float y, float radius){
        this.position.x = x;
        this.position.y = y;
        this.radius = radius;
    }
    public Circle(float x, float y, float radius, int color){
        this.position.x = x;
        this.position.y = y;
        this.radius = radius;
        this.color = color;
        this.originalColor = color;
    }
    public Circle(float x, float y, float radius, int color, Vettore speed){
        this.position.x = x;
        this.position.y = y;
        this.speed = speed;
        this.radius = radius;
        this.color = color;
        this.originalColor = color;
    }

    public void draw(Canvas canvas, ArrayList<Circle> collidableObjects, float[] eventInformation){
        this.drawThis(canvas, this.position.x, this.position.y);
        this.drawLancio(canvas);
        this.move();
        this.gestisciCollisioneCerchi(collidableObjects);
        this.gestisciCollisioneMuri(canvas);
        this.consideraAttritoTerreno(0.07f);

        this.gestisciEventi(eventInformation[0], eventInformation[1], (int)eventInformation[2]);
    }

    private void drawThis(Canvas canvas, float x, float y){
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, this.radius, paint);
        paint.setColor(this.color);
        canvas.drawCircle(x, y, this.radius-1.5f, paint);
    }

    private void drawLancio(Canvas canvas){
        if(this.lancio.isCharging()){
            this.lancio.carica.mul(1000).draw(canvas, this, Color.argb(50, 0, 255, 0));
            this.lancio.carica.draw(canvas, this, Color.argb(100, 0, 255, 0));
        }
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
        return UtilCollisioni.touched(x, y, this);
    }

    private void move(){
        this.speed = this.speed.add(this.acceleration);
        this.position = this.position.add(this.speed);
    }

    private void gestisciCollisioneMuri(Canvas screen){
        UtilCollisioni.rilevaMuri(screen.getWidth(), screen.getHeight(), this);
    }

    private void gestisciCollisioneCerchi(ArrayList<Circle> collidableObjects){
        for (Circle Collider: collidableObjects){
            if(!this.lancio.isCharging() && !Collider.lancio.isCharging() && Collider != this){
                if(UtilCollisioni.circles(this, Collider)){
                    UtilCollisioni.elastic(this, Collider);
                }
            }
        }
    }

    private void consideraAttritoTerreno(float c){
        if (this.speed.getModule() <= 2*this.acceleration.getModule()) this.acceleration.setModule(0);
        else this.acceleration.setModule(c);
        this.acceleration.setArg((float)Math.toDegrees(this.speed.getArg())+180);
    }

    public void createChildrens(int number){
        for(int i = 0; i < number; i++){
            this.childrens.add(new Circle(
                    this.position.x,
                    this.position.y,
                    this.radius/number,
                    this.color,
                    new Vettore(this.speed.x/number, this.speed.y/number)));
        }
    }

    public void destroy(int child){
        this.createChildrens(child);
        this.alive = false;
    }

    private void gestisciEventi(float x, float y, int evento){
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
                            threadFlag = true;
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

    public void locked(){
        this.mass = Math.pow(1000000000, 1000000000);
        this.locked = true;
    }
}