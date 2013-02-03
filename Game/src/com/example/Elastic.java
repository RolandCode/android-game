package com.example;


import android.content.*;
import android.graphics.*;
import android.view.*;
import java.util.*;

public class Elastic extends View implements View.OnTouchListener{

    public float[] eventsInformation = {0, 0, 10};

    public ArrayList<Circle> objects = new ArrayList<Circle>();
    private PhysicsEngine phEngine;

    private boolean flagInit = true;

    public Elastic(Context context){ super(context); }

    void init(){
        if(flagInit){
            phEngine = new PhysicsEngine(); 
            for (int i = 0; i < 6; i++){
                objects.add(new Circle(phEngine, new Vector(90+i*90, 50+i*70), 40));
            }
//            Entity frame = new Entity();
//            Vector screenSize = Game.getInstance().getScreenSize();
//            new HollowAARBody(phEngine, frame, screenSize.div(2), screenSize, new Vector(5, 5));
            
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
        phEngine.update(10);

        for (int i = 0; i < objects.size(); i++){
            if(!objects.get(i).alive){
                for (Circle child: objects.get(i).childrens){
                    objects.add(child);
                } objects.remove(i);
            }
        }
    }
    void drawObjects(Canvas canvas){
        updateObjects();

        for(Circle Object: objects){
        	Object.processEvent(eventsInformation);
            Object.draw(canvas);
        }
    }

    public void onDraw(Canvas canvas){
        this.setOnTouchListener(this);
        init();
        Game.getInstance().setCanvas(canvas);
        drawObjects(canvas);
        invalidate();
    }
    
}
