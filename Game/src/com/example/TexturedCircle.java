package com.example;


import android.content.res.Resources;
import android.graphics.*;

public class TexturedCircle extends Circle {
    protected Bitmap texture;
    private Paint paint = new Paint();
    public int radius;

    public TexturedCircle(PhysicsEngine engine, int radius, int imageResource, Resources risorse) {
        super(engine);
        this.radius = radius;
        this.texture = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(risorse, imageResource), 2*this.radius, 2*this.radius, true);
    }

    protected void drawThis(Canvas canvas, Vector position) {
/*        CircleBody circleBody = (CircleBody) body;
        paint.setColor(Color.WHITE);
        canvas.drawCircle(position.x, position.y, circleBody.radius, paint);
        paint.setColor(this.color);
        canvas.drawCircle(position.x, position.y, circleBody.radius-1.5f, paint);
*/
        canvas.drawBitmap(texture, position.x-this.radius, position.y-this.radius, paint);

    }
}
