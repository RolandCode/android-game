// Modified

package com.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GameClass extends View implements View.OnTouchListener
{
    public float X = 0;
    public float Y = 0;

    public int         // --> void Inizializzazioni()
            InizioLinea,
            FineLinea,
            YLinea,
            MuoviMondo,
            MuoviQuadrato,
            XQuadrato,
            YQuadrato,
            LatoQuadrato,
            MaxX,
            MaxY;

    public boolean Toccato = false;
    public boolean Collisione = false;
    public boolean FlagInizializzazioni = true;

    public Paint Disegna = new Paint();
    public Canvas Tavola;

    public GameClass(Context Contesto){
        super(Contesto);
    }

    void Inizializzazioni(){
        MaxX = Tavola.getWidth();
        MaxY = Tavola.getHeight();

        InizioLinea = MaxX;
        FineLinea = 2000;
        MuoviMondo = -10;
        MuoviQuadrato = 10;
        XQuadrato = 50;
        YQuadrato = 50;
        LatoQuadrato = 50;
        YLinea = (int)(MaxY*0.8);


    }

    boolean CollisioneQuadrato(int I, int F, int H){
        if(
           XQuadrato > (I - LatoQuadrato)  &&
           XQuadrato < F                   &&
           YQuadrato >= (H - LatoQuadrato) &&
           YQuadrato < (H - LatoQuadrato + 2)
        ){ return true; } return false;
    }

    void DisegnaTerreno(){
        Disegna.setColor(Color.BLACK);
        Tavola.drawLine(InizioLinea, YLinea, FineLinea, YLinea, Disegna);
    }

    void DisegnaTesti(){
        Disegna.setTextSize(20);
        Disegna.setTextAlign(Paint.Align.CENTER);
        Disegna.setColor(Color.BLACK);
        Tavola.drawText("X: " + X, MaxX/2, MaxY/2 - 10, Disegna);
        Tavola.drawText("Y: " + Y, MaxX / 2, MaxY / 2 + 10, Disegna);
        Tavola.drawText("Collisione: " + Collisione, MaxX / 2, MaxY / 2 + 30, Disegna);
    }

    void DisegnaQuadrato(){
        Disegna.setColor(Color.RED);
        Tavola.drawRect(XQuadrato - 2, YQuadrato - 2, XQuadrato + LatoQuadrato + 2, YQuadrato + LatoQuadrato + 2, Disegna);
        Disegna.setColor(Color.YELLOW);
        Tavola.drawRect(XQuadrato, YQuadrato, XQuadrato + LatoQuadrato, YQuadrato + LatoQuadrato, Disegna);
    }

    void MuoviMondo(){
        InizioLinea = InizioLinea + MuoviMondo;
        FineLinea = FineLinea + MuoviMondo;
    }

    void MuoviQuadrato(){
        if(!Collisione)YQuadrato = YQuadrato + MuoviQuadrato;
    }

/////////////////////////////////////////////////////////////

    public void onDraw(Canvas C){
        Tavola = C; this.setOnTouchListener(this);

        if(FlagInizializzazioni) Inizializzazioni(); FlagInizializzazioni = false;

        Disegna.setColor(Color.WHITE);
        Tavola.drawPaint(Disegna);

        DisegnaQuadrato();
        DisegnaTerreno();
        DisegnaTesti();
        MuoviMondo();
        MuoviQuadrato();

        Collisione = CollisioneQuadrato(InizioLinea, FineLinea, YLinea);
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

}