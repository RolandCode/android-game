package com.example;


import android.graphics.Canvas;
import android.graphics.Paint;

public final class Vector {

    public float x;
    public float y;

    private Paint paint = new Paint();


    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector(float module, float arg, boolean modarg){
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

    public Vector(Vector altro) {
        this.x = altro.x;
        this.y = altro.y;
    }

    public Vector add(Vector altro) {
        Vector ret = new Vector(); // Creiamo un nuovo vettore. Questo sara` il risultato di questa funzione
        ret.x = altro.x + x; // sommiamo le componenti x di questo vettore e dell'altro
        ret.y = altro.y + y; // Stessa cosa, con la componente y.
        return ret; // diamo il risultato.
    }

    public Vector sub(Vector altro) {
        Vector ret = new Vector();
        ret.x = x - altro.x;
        ret.y = y - altro.y;
        return ret;
    }

    public Vector mul(Vector altro) {
        Vector ret = new Vector();
        ret.x = altro.x * x;
        ret.y = altro.y * y;
        return ret;
    }

    public Vector mul(float fat) {
        Vector ret = new Vector(this);
        ret.x *= fat;
        ret.y *= fat;
        return ret;
    }

    public Vector div(Vector altro) {
        Vector ret = new Vector();
        ret.x = x / altro.x;
        ret.y = y / altro.y;
        return ret;
    }

    public Vector div(float fat) {
        Vector ret = new Vector(this);
        ret.x /= fat;
        ret.y /= fat;
        return ret;
    }

    public Vector dotProduct(Vector altro) {
        Vector ret = new Vector();
        // TODO: implementare questo metodo.
        // usare la funzione Math.cos()
        //
        return ret;
    }

    public Vector normalize() {
        int magn = magnitude();
        Vector ret = new Vector();
        ret.x = x / magn;
        ret.y = y / magn;
        return ret;
    }

    public int magnitude() {
        return (int) Math.sqrt(x*x + y*y);
    }
    
    public float sqrMagnitute() {
    	return x*x + y*y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }


    public void draw(Canvas canvas, Vector pos, int c){
        this.paint.setColor(c);
        float xi = pos.x;
        float yi = pos.y;
        float xf = xi + this.x*10;
        float yf = yi + this.y*10;

        canvas.drawLine(xi, yi, xf, yf, this.paint);
        canvas.drawCircle(xi, yi, 5, this.paint);
        canvas.drawCircle(xf, yf, 5, this.paint);
    }

}
