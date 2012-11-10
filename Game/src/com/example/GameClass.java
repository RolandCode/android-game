package com.example;

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

    public Sprite PallaGrande = new Sprite();
    public Sprite PallaPiccola = new Sprite();

    public Sprite PareteDestra = new Sprite();
    public Sprite PareteSinistra = new Sprite();
    public Sprite PareteSopra = new Sprite();
    public Sprite PareteSotto = new Sprite();

    Resources Risorse;

    public int MaxX, MaxY;

    public long T = 0;
    public int DeltaT = 0;

    public boolean Toccato = false;
    public boolean FlagInizializzazioni = true;

    public Paint Disegna = new Paint();
    public Canvas Tavola;

    public GameClass(Context Contesto){
        super(Contesto);
        Risorse = getResources();
    }

    void Inizializzazioni(){
        MaxX = Tavola.getWidth();
        MaxY = Tavola.getHeight();

        PallaGrande.createSprite(Risorse, R.drawable.ball, 100, 100);
        PallaGrande.setPosition(50, 50);
        PallaGrande.setCurrentDirection(Sprite.RIGHT_BOTTOM);
        PallaGrande.setMoveCoefficientHorizontal(0.05f);
        PallaGrande.setMoveCoefficientVertical(0.05f);

        PallaPiccola.createSprite(Risorse, R.drawable.ball, 50, 50);
        PallaPiccola.setPosition(MaxX-100, 50);
        PallaPiccola.setCurrentDirection(Sprite.LEFT_BOTTOM);
        PallaPiccola.setMoveCoefficientHorizontal(0.2f);
        PallaPiccola.setMoveCoefficientVertical(0.2f);

        PareteDestra.createSprite(5, MaxY);
        PareteDestra.setPosition(MaxX-5,0);

        PareteSinistra.createSprite(5, MaxY);
        PareteSinistra.setPosition(0, 0);

        PareteSopra.createSprite(MaxX, 5);
        PareteSopra.setPosition(0, 0);

        PareteSotto.createSprite(MaxX, 5);
        PareteSotto.setPosition(0, MaxY-5);

        GestioneMovimenti.start();

    }
    void DisegnaTesti(){
        Disegna.setTextSize(20);
        Disegna.setTextAlign(Paint.Align.CENTER);
        Disegna.setColor(Color.BLACK);
        Tavola.drawText("X: " + X, MaxX / 2, MaxY / 2 - 10, Disegna);
        Tavola.drawText("Y: " + Y, MaxX / 2, MaxY / 2 + 10, Disegna);
        Tavola.drawText("Next frame delay: " + DeltaT/1000.0f + " s", MaxX / 2, MaxY / 2 - 30, Disegna);
        if(DeltaT != 0)Tavola.drawText("FPS ≈ " + (1000/DeltaT) +" f/s", MaxX / 2, MaxY / 2 - 50, Disegna);
        Tavola.drawText("DirezionePallaGrande: " + PallaGrande.getCurrentDirection(), MaxX / 2, MaxY / 2 + 50, Disegna);
        Tavola.drawText("DirezionePallaPiccola: " + PallaPiccola.getCurrentDirection(), MaxX / 2, MaxY / 2 + 70, Disegna);
    }

    public void onDraw(Canvas C){

        Tavola = C; this.setOnTouchListener(this);

        DeltaT = (int)(System.currentTimeMillis() - T);
        T = System.currentTimeMillis();

        if(FlagInizializzazioni) Inizializzazioni(); FlagInizializzazioni = false;

        Disegna.setColor(Color.WHITE);
        Tavola.drawPaint(Disegna);

        PallaGrande.draw(Tavola, Disegna);
        PallaPiccola.draw(Tavola, Disegna);
        PareteDestra.draw(Tavola, Disegna);
        PareteSinistra.draw(Tavola, Disegna);

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

                if(PallaGrande.collide(PallaPiccola, Sprite.HORIZONTAL_COLLISION))PallaGrande.reverseDirection();
                if(PallaGrande.collide(PareteSinistra, Sprite.HORIZONTAL_COLLISION))PallaGrande.reverseDirection();
                if(PallaGrande.collide(PareteDestra, Sprite.HORIZONTAL_COLLISION))PallaGrande.reverseDirection();
                if(PallaGrande.collide(PareteSopra, Sprite.VERTICAL_COLLISION))PallaGrande.reverseDirection();
                if(PallaGrande.collide(PareteSotto, Sprite.VERTICAL_COLLISION))PallaGrande.reverseDirection();

                if(PallaPiccola.collide(PallaGrande, Sprite.HORIZONTAL_COLLISION))PallaPiccola.reverseDirection();
                if(PallaPiccola.collide(PareteSinistra, Sprite.HORIZONTAL_COLLISION))PallaPiccola.reverseDirection();
                if(PallaPiccola.collide(PareteDestra, Sprite.HORIZONTAL_COLLISION))PallaPiccola.reverseDirection();
                if(PallaPiccola.collide(PareteSopra, Sprite.VERTICAL_COLLISION))PallaPiccola.reverseDirection();
                if(PallaPiccola.collide(PareteSotto, Sprite.VERTICAL_COLLISION))PallaPiccola.reverseDirection();

                PallaGrande.move(1);
                PallaPiccola.move(1);

            }
        }
    }
}