package com.example;


public final class Vettore {

    public int x;
    public int y;

    public Vettore() {
        this.x = 0;
        this.y = 0;
    }

    public Vettore(int x, int y) {
        this.x = x;
        this.y = y;
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

}