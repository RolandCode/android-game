package com.example;


import android.graphics.Canvas;
import android.graphics.Paint;

public final class Vettore {

//    public int x;
//    public int y;

    public static final int X_Y = 0;
    public static final int MOD_ARG = 1;

    private float x, y, module, arg;
    private Paint paint = new Paint();


/*    public Vettore() {
        this.x = 0;
        this.y = 0;
    }

    public Vettore(int x, int y) {
        this.x = x;
        this.y = y;
    }
*/


    public Vettore(){
        this.update(0, 0, 0);
    }

    public Vettore(float f, float s, int type){
        this.update(f, s, type);
    }

    public void setX(float x){
        this.update(x, this.y, X_Y);
    }

    public void setY(float y){
        this.update(this.x, y, X_Y);
    }

    public void setModule(float module){
        this.update(module, (float)Math.toDegrees(this.arg), MOD_ARG);
    }

    public void setArg(float argG){
        this.update(this.module, argG, MOD_ARG);
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public float getModule(){
        return this.module;
    }

    public float getArg(){
        return this.arg;
    }

    public float getArgG(){
        return (float)Math.toDegrees(this.arg);
    }

    private void update(float f, float s, int type){

        if (type == X_Y){
            this.x = f;
            this.y = s;
            this.module = (float)Math.sqrt(this.x*this.x+this.y*this.y);
            this.arg = (float)Math.atan2(this.y, this.x);
        }

        if (type == MOD_ARG){
            this.module = f;
            this.arg = (float)Math.toRadians(s);
            this.x = (float)(this.module*Math.cos(this.arg));
            this.y = (float)(this.module*Math.sin(this.arg));
        }
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
        float xi = circle.getX();
        float yi = circle.getY();
        float xf = xi + this.getX()*10;
        float yf = yi + this.getY()*10;

        canvas.drawLine(xi, yi, xf, yf, this.paint);
        canvas.drawCircle(xi, yi, 5, this.paint);
        canvas.drawCircle(xf, yf, 5, this.paint);
    }

}