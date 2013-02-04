package com.example;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;

public class GravityBall extends View {

    public ArrayList<TexturedCircle> circles = new ArrayList<TexturedCircle>();
    private PhysicsEngine phEngine;

    private boolean flagInit = true;

    Resources resource = getResources();
    TexturedCircle ball;

    public GravityBall(Context context){ super(context); }

    void init(){
        if(flagInit){
            phEngine = new PhysicsEngine();
            ball = new TexturedCircle(phEngine, 60, R.drawable.ball, this.resource);
        } flagInit = false;
    }

    void updateObjects(){
        phEngine.update(10);
        ball.body.setSpeed(new Vector(-GameActivity.accelerometerValues[0], GameActivity.accelerometerValues[1]));
    }

    void drawObjects(Canvas canvas){
        updateObjects();
        ball.draw(canvas);
    }

    public void onDraw(Canvas canvas){
        init();
        Game.getInstance().setCanvas(canvas);
        drawObjects(canvas);
        invalidate();
    }
}
