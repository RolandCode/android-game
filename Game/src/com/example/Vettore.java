package com.example;


import android.graphics.Canvas;
import android.graphics.Paint;

public final class Vettore {

    public float x;
    public float y;

    public static final int X_Y = 0;
    public static final int MOD_ARG = 1;

    private Paint paint = new Paint();


    public Vettore() {
        this.x = 0;
        this.y = 0;
    }

    public Vettore(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vettore(float module, float arg, boolean modarg){
        this.update(module, arg, true);
    }

    public void setModule(float module){
        this.update(module, (float)Math.toDegrees(this.getArg()), true);
    }

    public void setArg(float argG){
        this.update(this.getModule(), argG, true);
    }

    public float getModule(){
        return (float)Math.hypot(this.x, this.y);
    }

    public float getArg(){
        return (float)Math.atan2(this.y, this.x);
    }

    public float getArgG(){
        return (float)Math.toDegrees(Math.atan2(this.y, this.x));
    }

    private void update(float module, float arg, boolean modarg){
            this.x = (float)(module*Math.cos(Math.toRadians(arg)));
            this.y = (float)(module*Math.sin(Math.toRadians(arg)));
    }

    public Vettore(Vettore altro) {
        this.x = altro.x;
        this.y = altro.y;
    }

    public Vettore add(Vettore altro) {
        Vettore ret = new Vettore(); // Creiamo un nuovo vettore. Questo sara` il risultato di questa funzione
        ret.x = altro.x + x; // sommiamo le componenti x di questo vettore e dell'altro
        ret.y = altro.y + y; // Stessa cosa, con la componente y.
        return ret; // diamo il risultato.
    }

    public Vettore sub(Vettore altro) {
        Vettore ret = new Vettore();
        ret.x = x - altro.x ;
        ret.y = y - altro.y;
        return ret;
    }

    public Vettore mul(Vettore altro) {
        Vettore ret = new Vettore();
        ret.x = altro.x * x;
        ret.y = altro.y * y;
        return ret;
    }

    public Vettore mul(float fat) {
        Vettore ret = new Vettore(this);
        ret.x *= fat;
        ret.y *= fat;
        return ret;
    }

    public Vettore div(Vettore altro) {
        Vettore ret = new Vettore();
        ret.x = x / altro.x;
        ret.y = y / altro.y;
        return ret;
    }

    public Vettore div(float fat) {
        Vettore ret = new Vettore(this);
        ret.x /= fat;
        ret.y /= fat;
        return ret;
    }

    public Vettore dotProduct(Vettore altro) {
        Vettore ret = new Vettore();
        // TODO: implementare questo metodo.
        // usare la funzione Math.cos()
        //
        return ret;
    }

    public Vettore normalize() {
        int magn = magnitude();
        Vettore ret = new Vettore();
        ret.x = x / magn;
        ret.y = y / magn;
        return ret;
    }

    public int magnitude() {
        return (int) Math.sqrt(x*x + y*y);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }


    public void draw(Canvas canvas, Circle circle, int c){
        this.paint.setColor(c);
        float xi = circle.position.x;
        float yi = circle.position.y;
        float xf = xi + this.x*10;
        float yf = yi + this.y*10;

        canvas.drawLine(xi, yi, xf, yf, this.paint);
        canvas.drawCircle(xi, yi, 5, this.paint);
        canvas.drawCircle(xf, yf, 5, this.paint);
    }

}