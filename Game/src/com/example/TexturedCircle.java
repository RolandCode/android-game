package com.example;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class TexturedCircle extends Circle {
    protected Bitmap texture;
    protected int imageResource;
    protected Resources risorse;
    private boolean flagInit = true;
    private Paint paint = new Paint();
    public int radius;

    public TexturedCircle(PhysicsEngine engine, int radius, int imageResource, Resources risorse) {
        super(engine);
        this.imageResource = imageResource;
        this.risorse = risorse;
        this.radius = radius;
        init();
    }

    protected void drawThis(Canvas canvas, Vector position) {
       canvas.drawBitmap(texture, position.x-this.radius, position.y-this.radius, paint);
    }

    private void init(){
         if (flagInit){
             this.texture = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(risorse, this.imageResource), 2*this.radius,2*this.radius, true);
         } flagInit = false;
    }
}
