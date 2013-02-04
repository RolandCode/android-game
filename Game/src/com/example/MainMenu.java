package com.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

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
            CircleButton gravityBall = new CircleButton(phEngine, new Vector(240, 480), 50, "Gravity Ball Debug");

            objects.add(elasticDebug);
            objects.add(gravityBall);

            CircleButton.Callback elasticCallback = new CircleButton.Callback() {
                public void execute() {
                    GameActivity activity = (GameActivity) Game.getInstance().getActivity();
                    activity.setContentView(new Elastic(activity));
                }
            };

            elasticDebug.setCallback(elasticCallback);

            CircleButton.Callback gravityBallCallback = new CircleButton.Callback() {
                public void execute() {
                    GameActivity activity = (GameActivity) Game.getInstance().getActivity();
                    activity.setContentView(new GravityBall(activity));
                }
            };

            gravityBall.setCallback(gravityBallCallback);

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
/*
        canvas.drawText("Orientation X:   " + GameActivity.orientationValues[0], 30, 30, paint);
        canvas.drawText("Orientation Y:   " + GameActivity.orientationValues[1], 30, 50, paint);
        canvas.drawText("Orientation Z:   " + GameActivity.orientationValues[2], 30, 70, paint);

        canvas.drawText("Accelerometer X: " + GameActivity.accelerometerValues[0], 30, 100, paint);
        canvas.drawText("Accelerometer Y: " + GameActivity.accelerometerValues[1], 30, 120, paint);
        canvas.drawText("Accelerometer Z: " + GameActivity.accelerometerValues[2], 30, 140, paint);
*/
        invalidate();
    }
    
}
