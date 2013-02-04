package com.example;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Elastic extends View implements View.OnTouchListener{

    public float[] eventsInformation = {0, 0, 10};

    public ArrayList<Circle> circles = new ArrayList<Circle>();
    private PhysicsEngine phEngine;

    public Elastic(Context context){
        super(context);

        phEngine = new PhysicsEngine();
        for (int i = 0; i < 6; i++){
            circles.add(new Circle(phEngine, 50));
        }

    }

    public boolean onTouch(View p1, MotionEvent motionEvent) {
        eventsInformation[0] = motionEvent.getX();
        eventsInformation[1] = motionEvent.getY();
        eventsInformation[2] = motionEvent.getAction();
        invalidate();
        return true;
    }
    void updateObjects(){
        phEngine.update(10);

        for (int i = 0; i < circles.size(); i++){
            if(!circles.get(i).alive){
                for (Circle child: circles.get(i).childrens){
                    circles.add(child);
                } circles.remove(i);
            }
        }
    }
    void drawObjects(Canvas canvas){
        updateObjects();

        for(Circle Object: circles){
        	Object.processEvent(eventsInformation);
            Object.draw(canvas);
        }
    }

    public void onDraw(Canvas canvas){
        this.setOnTouchListener(this);
        Game.getInstance().setCanvas(canvas);
        drawObjects(canvas);
        invalidate();
    }
}
