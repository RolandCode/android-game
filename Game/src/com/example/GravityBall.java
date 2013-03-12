package com.example;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;

public class GravityBall extends View {

    public ArrayList<TexturedCircle> circles = new ArrayList<TexturedCircle>();
    private PhysicsEngine phEngine;

    private boolean flagInit = true;

    Resources risorse = getResources();
    TexturedCircle ball;
    Bitmap background;
	
    Paint paint = new Paint();

    public GravityBall(Context context){ super(context); }

    void init(){
        if(flagInit){
            phEngine = new PhysicsEngine();
            ball = new TexturedCircle(phEngine, 60, R.drawable.ball, this.risorse);
           // ball.body.setCenter(new Vector(240, 400));
			
            background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(risorse, R.drawable.sfondo), getWidth(), getHeight(), true);
        } flagInit = false;
    }

    void updateObjects(){
        phEngine.update(10);
     //   ball.body.setSpeed(new Vector(-GameActivity.accelerometerValues[0]*3, GameActivity.accelerometerValues[1]*3));
    }

    void drawObjects(Canvas canvas){
        updateObjects();
        ball.draw(canvas);
    }

    public void onDraw(Canvas canvas){
        init();
        canvas.drawBitmap(background, 0, 0, paint);
        Game.getInstance().setCanvas(canvas);
        drawObjects(canvas);
        invalidate();
    }
}
