package com.example;


import android.content.*;
import android.content.res.Resources;
import android.graphics.*;
import android.view.*;
import java.util.*;

public class Elastic extends View implements View.OnTouchListener{

    public float[] eventsInformation = {0, 0, 10};

    public ArrayList<TexturedCircle> circles = new ArrayList<TexturedCircle>();
    private PhysicsEngine phEngine;

    private boolean flagInit = true;

    Resources resource = getResources();

    public Elastic(Context context){ super(context); }

    void init(){
        if(flagInit){
            phEngine = new PhysicsEngine(); 
            for (int i = 0; i < 6; i++){
                circles.add(new TexturedCircle(phEngine, 60, R.drawable.ball, this.resource));
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

        for (int i = 0; i < circles.size(); i++){
            if(!circles.get(i).alive){
                for (Circle child: circles.get(i).childrens){
                    circles.add((TexturedCircle)child);
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
        init();
        Game.getInstance().setCanvas(canvas);
        drawObjects(canvas);
        invalidate();
    }
}
