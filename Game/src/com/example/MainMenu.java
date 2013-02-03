package com.example;

import android.content.*;
import android.graphics.*;
import android.view.*;
import java.util.*;

public class MainMenu extends View implements View.OnTouchListener{

    public float[] eventsInformation = {0, 0, 10};

    public ArrayList<Sprite> objects = new ArrayList<Sprite>();
    private PhysicsEngine phEngine;

    private boolean flagInit = true;

    private Paint paint = new Paint();

    public MainMenu(Context context){
        super(context);
    }

    void init(){
        if(flagInit){
            phEngine = new PhysicsEngine();
            CircleButton elasticDebug = new CircleButton(phEngine, new Vector(240, 480), 50, "Elastic Debug");
            CircleButton button0 = new CircleButton(phEngine, new Vector(240, 480), 50, "Button 0");
            CircleButton button1 = new CircleButton(phEngine, new Vector(240, 480), 50, "Button 1");
            CircleButton button2 = new CircleButton(phEngine, new Vector(240, 480), 50, "Button 2");

            objects.add(elasticDebug);
            objects.add(button0);
            objects.add(button1);
            objects.add(button2);

            CircleButton.Callback elasticCallback = new CircleButton.Callback() {

                public void execute() {
                    GameActivity activity = (GameActivity) Game.getInstance().getActivity();
                    activity.setContentView(new Elastic(activity));
                }

            };

            elasticDebug.setCallback(elasticCallback);
            elasticDebug.body.setSpeed(new Vector(10, 10));
            
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
    }
    
    void drawObjects(Canvas canvas){
        updateObjects();

        for(Sprite Object: objects){
        	Object.processEvent(eventsInformation);
            Object.draw(canvas);
        }
    }

    public void onDraw(Canvas canvas){
        this.setOnTouchListener(this);
        init();
        Game.getInstance().setCanvas(canvas);
        drawObjects(canvas);
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        canvas.drawText("Yaw: " + GameActivity.orientationValues[0], 30, 30, paint);
        canvas.drawText("Pitch: " + GameActivity.orientationValues[1], 30, 50, paint);
        canvas.drawText("Roll: " + GameActivity.orientationValues[2], 30, 70, paint);

        canvas.drawText("Sway: " + GameActivity.accelerometerValues[0], 30, 90, paint);
        canvas.drawText("Surge: " + GameActivity.accelerometerValues[1], 30, 110, paint);
        canvas.drawText("Heave: " + GameActivity.accelerometerValues[2], 30, 130, paint);
        invalidate();
    }
    
}
