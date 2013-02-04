package com.example;


import android.content.res.Resources;
import android.graphics.*;

public class TexturedCircle extends Circle {
    protected Bitmap texture;
    private Paint paint = new Paint();
    public float radius;

    public TexturedCircle(PhysicsEngine engine, float radius, int imageResource, Resources risorse) {
        super(engine, radius);
        this.radius = radius;
        this.texture = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(risorse, imageResource), 2*(int)this.radius, 2*(int)this.radius, true);
    }

    protected void drawThis(Canvas canvas, Vector position) {
        canvas.drawBitmap(texture, position.x-this.radius, position.y-this.radius, paint);

    }
}
