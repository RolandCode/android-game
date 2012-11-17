package com.example;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GameClass extends View implements View.OnTouchListener
{
    public float X = 0;
    public float Y = 0;

    public GestioneMovimentiThread GestioneMovimenti = new GestioneMovimentiThread();
    public GestioneCollisioniThread GestioneCollisioni = new GestioneCollisioniThread();

    public Sprite PallaGrande = new Sprite();
    public Sprite PallaPiccola = new Sprite();

    public Sprite PareteDestra = new Sprite(true);
    public Sprite PareteSinistra = new Sprite(true);
    public Sprite PareteSopra = new Sprite(true);
    public Sprite PareteSotto = new Sprite(true);

    Resources Risorse;

    public int MaxX, MaxY;

    public double DelayDisegni = 0.00f;
    public double DelayMovimenti = 0.00f;
    public double DelayCollisioni = 0.00f;

    public long NowDisegni = 0;
    public long NowMovimenti = 0;
    public long NowCollisioni = 0;

    public boolean Toccato = false;
    public boolean FlagInizializzazioni = true;

    public Paint Disegna = new Paint();
    public Canvas Tavola;

    public boolean Collisione = false;
    
    public ArrayList<Sprite> Colliders = new ArrayList<Sprite>();

    public GameClass(Context Contesto){
        super(Contesto);
        Risorse = getResources();
    }

    void Inizializzazioni(){
        MaxX = Tavola.getWidth();
        MaxY = Tavola.getHeight();

        PallaGrande.createSprite(Risorse, R.drawable.img, 300, 300);
        PallaGrande.setPosition(MaxX/2, MaxY/2);
        PallaGrande.setCurrentDirection(Sprite.RIGHT_BOTTOM);
        PallaGrande.setMoveCoefficientHorizontal(0f);
        PallaGrande.setMoveCoefficientVertical(0f);

        PallaPiccola.createSprite(Risorse, R.drawable.ball, 150, 150);
        PallaPiccola.setPosition(MaxX/2, MaxY/2);
        PallaPiccola.setCurrentDirection(Sprite.LEFT_BOTTOM);
        PallaPiccola.setMoveCoefficientHorizontal(0f);
        PallaPiccola.setMoveCoefficientVertical(0f);

        PareteDestra.createSprite(5, MaxY);
        PareteDestra.setPosition(MaxX-5,0);

        PareteSinistra.createSprite(5, MaxY);
        PareteSinistra.setPosition(0, 0);

        PareteSopra.createSprite(MaxX, 5);
        PareteSopra.setPosition(0, 0);

        PareteSotto.createSprite(MaxX, 5);
        PareteSotto.setPosition(0, MaxY-5);
        
        Colliders.add(PareteDestra);
        Colliders.add(PareteSinistra);
        Colliders.add(PareteSopra);
        Colliders.add(PareteSotto);

        GestioneMovimenti.start();
        GestioneCollisioni.start();

    }
    void DisegnaTesti(){
        Disegna.setTextSize(20);
        Disegna.setTextAlign(Paint.Align.CENTER);
        Disegna.setColor(Color.argb(200, 0, 255, 0));
        Tavola.drawText("Delay onDraw: " + DelayDisegni + " ms", MaxX / 2, 40, Disegna);
        Tavola.drawText("Delay Movimenti: " + DelayMovimenti + " ms", MaxX / 2, 60, Disegna);
        Tavola.drawText("Delay Collisioni: " + DelayCollisioni + " ms", MaxX / 2, 80, Disegna);
        Tavola.drawText("PallaGrande -> PallaPiccola: " + Collisione, MaxX / 2, 110, Disegna);
        Tavola.drawText("PallaGrande speed, acceleration" + PallaGrande.getSpeed().toString() + " , " + PallaGrande.getAcceleration().toString(), MaxX / 2, 140, Disegna);
    }

    public void onDraw(Canvas C){

        Tavola = C; this.setOnTouchListener(this);

        DelayDisegni = (int)(System.currentTimeMillis() - NowDisegni);
        NowDisegni = System.currentTimeMillis();

        if(FlagInizializzazioni) Inizializzazioni(); FlagInizializzazioni = false;

        Disegna.setColor(Color.argb(255, 0, 0, 255));
        Tavola.drawPaint(Disegna);

        PallaGrande.draw(Tavola, Disegna);
        PallaPiccola.draw(Tavola, Disegna);

        if(PallaGrande.getShape().intersect(PallaPiccola.getShape())){
            Disegna.setColor(Color.argb(80, 255, 255, 255));
            Tavola.drawRect(UtilCollisioni.getRectIntersezione(PallaGrande.getShape(), PallaPiccola.getShape()), Disegna);
        }

        DisegnaTesti();

        invalidate();
    }

    public boolean onTouch(View p1, MotionEvent p2){
        switch (p2.getAction()){

            case MotionEvent.ACTION_DOWN:
                X = p2.getX();
                Y = p2.getY();
                Toccato = true;
                break;

            case MotionEvent.ACTION_MOVE:
                X = p2.getX();
                Y = p2.getY();
                break;

            case MotionEvent.ACTION_UP:
                Toccato = false;
                break;
        }
        invalidate();
        return true;
    }

    class GestioneMovimentiThread extends Thread{
        public void run(){
            while(true){

                DelayMovimenti = (System.currentTimeMillis() - NowMovimenti);
                NowMovimenti = System.currentTimeMillis();

//                PallaPiccola.setPosition((int)(X-PallaPiccola.getWidth()/2), (int)(Y-PallaPiccola.getHeight()/2));
//                PallaGrande.setPosition(MaxX/2-PallaGrande.getWidth()/2, MaxY/2-PallaGrande.getHeight()/2);

                try {
					sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                
                for(Sprite collider : Colliders) {
                	if(PallaGrande.collide(collider, UtilCollisioni.PERFECT_PIXEL)) PallaGrande.onCollision(collider);
                	if(PallaPiccola.collide(collider, UtilCollisioni.PERFECT_PIXEL)) PallaPiccola.onCollision(collider);
                }
                PallaGrande.move(1);
                PallaPiccola.move(1);

            }
        }
    }
    class GestioneCollisioniThread extends Thread{
        public void run(){
            while(true){

                DelayCollisioni = (int)(System.currentTimeMillis() - NowCollisioni);
                NowCollisioni = System.currentTimeMillis();

                Collisione = PallaGrande.collide(PallaPiccola, UtilCollisioni.PERFECT_PIXEL);


//                if(PallaGrande.collide(PareteSinistra, UtilCollisioni.PERFECT_PIXEL))PallaGrande.reverseDirection();
//                if(PallaGrande.collide(PareteDestra, UtilCollisioni.PERFECT_PIXEL))PallaGrande.reverseDirection();
//                if(PallaGrande.collide(PareteSopra, UtilCollisioni.PERFECT_PIXEL))PallaGrande.reverseDirection();
//                if(PallaGrande.collide(PareteSotto, UtilCollisioni.PERFECT_PIXEL))PallaGrande.reverseDirection();
//                if(PallaPiccola.collide(PareteSinistra, UtilCollisioni.PERFECT_PIXEL))PallaPiccola.reverseDirection();
//                if(PallaPiccola.collide(PareteDestra, UtilCollisioni.PERFECT_PIXEL))PallaPiccola.reverseDirection();
//                if(PallaPiccola.collide(PareteSopra, UtilCollisioni.PERFECT_PIXEL))PallaPiccola.reverseDirection();
//                if(PallaPiccola.collide(PareteSotto, UtilCollisioni.PERFECT_PIXEL))PallaPiccola.reverseDirection();
            }
        }
    }
}