package com.example;


import android.content.*;
import android.graphics.*;
import android.view.*;
import java.util.*;

public class Elastic extends View implements View.OnTouchListener{

    public float[] eventsInformation = {0, 0, 10};

    public ArrayList<Circle> objects = new ArrayList<Circle>();

    boolean flagInit = true;

    public Elastic(Context context){ super(context); }

    void init(){
        if(flagInit){
            for (int i = 0; i < 4; i++){
                objects.add(new Circle(40));
            }
        } flagInit = false;
    }

    public boolean onTouch(View p1, MotionEvent motionEvent) {
        eventsInformation[0] = motionEvent.getX();
        eventsInformation[1] = motionEvent.getY();
        eventsInformation[2] = motionEvent.getAction();
        invalidate();
        return true;
    }
    void updateObjects(){
        for (int i = 0; i < objects.size(); i++){
            if(!objects.get(i).alive){
                for (Circle Child: objects.get(i).childrens){
                    objects.add(Child);
                } objects.remove(i);
            }
        }
    }
    void drawObjects(Canvas canvas){
        for(Circle Object: objects){
            Object.draw(canvas, objects, eventsInformation);
        }
        updateObjects();
    }

    public void onDraw(Canvas canvas){
        this.setOnTouchListener(this);
        init();
        drawObjects(canvas);
        invalidate();
    }
}
