package com.example;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MainMenu extends View implements View.OnTouchListener{

    public float[] eventsInformation = {0, 0, 10};

    public ArrayList<Sprite> objects = new ArrayList<Sprite>();
    private PhysicsEngine phEngine;

    private boolean flagInit = true;

    private Paint paint = new Paint();

    Resources risorse = getResources();

    Bitmap background;

    public MainMenu(Context context){
        super(context);
    }

    void init(){
        if(flagInit){

            background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(risorse, R.drawable.sfondo), getWidth(), getHeight(), true);
            phEngine = new PhysicsEngine();
            CircleButton elasticDebug = new CircleButton(phEngine, new Vector(240, 400), 50, "Elastic Debug");
            CircleButton gravityBall = new CircleButton(phEngine, new Vector(240, 400), 50, "Gravity Ball");
            CircleButton reset = new CircleButton(phEngine, new Vector(240, 500), 50, "Reset");
            CircleButton b0 = new CircleButton(phEngine, new Vector(240, 300), 50, "Button");

            objects.add(elasticDebug);
            objects.add(gravityBall);
            objects.add(reset);
            objects.add(b0);  b0.body.setSpeed(new Vector(3, 270, true));

            CircleButton.Callback resetCallback = new CircleButton.Callback() {
                public void execute() {
                    GameActivity activity = (GameActivity) Game.getInstance().getActivity();
                    activity.setContentView(new MainMenu(activity));
                }
            }; reset.setCallback(resetCallback); reset.body.setSpeed(new Vector(3, 90, true));

            CircleButton.Callback elasticCallback = new CircleButton.Callback() {
                public void execute() {
                    GameActivity activity = (GameActivity) Game.getInstance().getActivity();
                    activity.setContentView(new Elastic(activity));
                }
            }; elasticDebug.setCallback(elasticCallback); elasticDebug.body.setSpeed(new Vector(3, 0, true));

            CircleButton.Callback gravityBallCallback = new CircleButton.Callback() {
                public void execute() {
                    GameActivity activity = (GameActivity) Game.getInstance().getActivity();
                    activity.setContentView(new GravityBall(activity));
                }
            }; gravityBall.setCallback(gravityBallCallback); gravityBall.body.setSpeed(new Vector(3, 180, true));

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
        canvas.drawBitmap(background, 0, 0, paint);
        Game.getInstance().setCanvas(canvas);
        drawObjects(canvas);
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        invalidate();
    }
    
}
