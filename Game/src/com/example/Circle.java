package com.example;

import android.graphics.*;
import android.view.*;
import java.util.*;

public class Circle{
    public int color = Color.BLACK;
    public int originalColor= Color.BLACK;
    private float radius;
    private Paint paint = new Paint();
    private ArrayList<Circle> childrens = new ArrayList<Circle>();
    public LancioElastico lancio = new LancioElastico(this);
    private boolean alive = true;

    public Vettore speed = new Vettore(0, 0, 0);
    public Vettore acceleration = new Vettore(0, 0, 0);
    private Vettore position = new Vettore(0, 0, 0);

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
        this.position.setX(x);
        this.position.setY(y);
        this.radius = radius;
    }

    public Circle(float x, float y, float radius, int color){
        this.position.setX(x);
        this.position.setY(y);
        this.radius = radius;
        this.color = color;
        this.originalColor = color;
    }
    public Circle(float x, float y, float radius, int color, Vettore speed){
        this.position.setX(x);
        this.position.setY(y);
        this.speed = speed;
        this.radius = radius;
        this.color = color;
        this.originalColor = color;
    }

    public void draw(Canvas canvas, ArrayList<Circle> collidableObjects, float[] eventInformation){
        if(!this.lancio.isCharging()){
            paint.setColor(Color.WHITE);
            canvas.drawCircle(this.position.getX(), this.position.getY(), this.radius, paint);
            paint.setColor(this.color);
            canvas.drawCircle(this.position.getX(), this.position.getY(), this.radius-1.5f, paint);
            this.move();
            for (Circle Collider: collidableObjects){
                if(!Collider.lancio.isCharging() && Collider != this){
                    this.gestisciCollisioneCerchi(Collider);
                }
            }
            this.gestisciCollisioneMuri(canvas, 0);
            this.consideraAttritoTerreno(0.07f);
        }

        else {
            paint.setColor(Color.WHITE);
            canvas.drawCircle(this.getX()+this.lancio.getDrawable(0).getX()*10, this.getY()+this.lancio.getDrawable(0).getY()*10, this.radius, paint);
            paint.setColor(this.color);
            canvas.drawCircle(this.getX()+this.lancio.getDrawable(0).getX()*10, this.getY()+this.lancio.getDrawable(0).getY()*10, this.radius-1.5f, paint);
            paint.setColor(Color.argb(20, 0, 255, 0));
            canvas.drawCircle(this.position.getX(), this.position.getY(), this.radius, paint);
            this.lancio.getDrawable(1).draw(canvas, this, Color.argb(70, 0, 255, 0));
            this.lancio.getDrawable(0).draw(canvas, this, Color.argb(255, 0, 255, 0));
            this.speed.setX(0); this.speed.setY(0);
        }

        this.gestisciEventi(eventInformation[0], eventInformation[1],(int)eventInformation[2]);
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
                this.setX(this.getX()+this.lancio.getDrawable(0).getX()*10);
                this.setY(this.getY()+this.lancio.getDrawable(0).getY()*10);
                this.lancio.sayRelased();
//	 this.destroy(2);
                break;
        }
    }

    public boolean isTouched(float x, float y){
        return UtilCollisioni.touched(x, y, this);
    }

    public float getX(){
        return this.position.getX();
    }

    public float getY(){
        return this.position.getY();
    }

    public void setX(float x){
        this.position.setX(x);
    }

    public void setY(float y){
        this.position.setY(y);
    }

    public float getRadius(){
        return this.radius;
    }

    public void setPosition(float x, float y){
        this.setX(x);
        this.setY(y);
    }

    private void move(){
        this.speed.setX(this.speed.getX()+this.acceleration.getX());
        this.speed.setY(this.speed.getY()+this.acceleration.getY());
        this.position.setX(this.position.getX()+this.speed.getX());
        this.position.setY(this.position.getY()+this.speed.getY());
    }

    private void gestisciCollisioneMuri(Canvas screen, float c){
        UtilCollisioni.rilevaMuri(screen.getWidth(), screen.getHeight(), this, c);
    }

    private void gestisciCollisioneCerchi(Circle other){
        if(UtilCollisioni.circles(this, other)){
            UtilCollisioni.elastic(this, other);
/*            this.color = Color.RED;
            other.color = Color.RED;
            if(!threadFlag) this.ripristinaColore(30, other);
*/        }
    }

    private void consideraAttritoTerreno(float c){
        if (this.speed.getModule() <= 2*this.acceleration.getModule()) this.acceleration.setModule(0);
        else this.acceleration.setModule(c);
        this.acceleration.setArg((float)Math.toDegrees(this.speed.getArg())+180);
    }

    public void createChildrens(int number){
        for(int i = 0; i < number; i++){
            this.childrens.add(new Circle(this.getX(), this.getY(), this.getRadius()/number, this.color, new Vettore(this.speed.getModule()/number, (float)Math.toDegrees(this.speed.getArg()), 1)));
        }
    }

    public ArrayList<Circle> getChildrens(){
        return this.childrens;
    }

    public void destroy(int child){
        this.createChildrens(child);
        this.alive = false;
    }

    public boolean isAlive(){
        return this.alive;
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
}