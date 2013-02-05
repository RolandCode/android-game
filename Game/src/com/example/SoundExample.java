package com.example;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class SoundExample extends View implements View.OnTouchListener{

    public float[] eventsInformation = {0, 0, 10};

    private PhysicsEngine phEngine;

    private boolean flagInit = true;

    Resources risorse = getResources();
    CircleButton play;
    Bitmap background;

    Paint paint = new Paint();

    Sound suoni = new Sound();

    public SoundExample(Context context){  super(context);  }

    void init(){
        if(flagInit){
            suoni.add("Exp", R.raw.explosion);

            phEngine = new PhysicsEngine();
            play = new CircleButton(phEngine, new Vector(240,400), 50, "Play");

            play.setCallback(new CircleButton.Callback() {
                public void execute() {
                    suoni.playSound("Exp");
                }
            });

            background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(risorse, R.drawable.sfondo), getWidth(), getHeight(), true);
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
        play.processEvent(eventsInformation);
        play.draw(canvas);
    }

    public void onDraw(Canvas canvas){
        this.setOnTouchListener(this);

        init();
        canvas.drawBitmap(background, 0, 0, paint);
        Game.getInstance().setCanvas(canvas);
        drawObjects(canvas);
        invalidate();
    }
}
