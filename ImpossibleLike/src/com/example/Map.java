package com.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Map extends View implements View.OnTouchListener
{
    public float X = 0;
    public float Y = 0;

	public int Yline,
               X1line,
               X2line,
               X3line,
               X4line,
               Pause,
               MaxX,
               MaxY;

    public int MuoviMondo = 10;
    public int MuoviQuadrato = 10;

	public boolean Toccato = false;

	public int XQ = 50;
	public int YQ = 0;
	public int LQ = 50;
    public boolean Collisione = false;

    public Paint Disegna = new Paint();
    public Canvas Tavola;
    public int Max = 4;
    public int CoordLines[] = new int[Max];
    public boolean Collisioni[] = new boolean[Max];



    public boolean FlagInizializzazioni = true;

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
    void Inizializzazioni(){
        MaxX = Tavola.getWidth();
        MaxY = Tavola.getHeight();
        Yline = MaxY - (int)(MaxY*0.1f);
        Pause = 100;
        X1line = 0;
        X2line = 200;
        X3line = X2line + Pause;
        X4line = X3line + 300;
        for(int i = 0; i < Max; i++){
            CoordLines[i] = i*600;
            Collisioni[i] = false;
        }
    }
    public Map(Context Contesto){
	    super(Contesto);
	}
    boolean CollisioneQuadrato(int I, int F, int H){
        if(XQ > I - LQ && XQ < F && YQ >= H - LQ && YQ < H - LQ + 2){
            return true;
        }
        return false;
    }

    void DisegnaTerreno(){
        Disegna.setColor(Color.BLACK);
        Tavola.drawLine(X1line, Yline, X2line, Yline, Disegna);
        Tavola.drawLine(X3line, Yline, X4line, Yline, Disegna);
    }

    void DisegnaTesti(){
        Disegna.setTextSize(20);
        Disegna.setTextAlign(Paint.Align.CENTER);
        Disegna.setColor(Color.BLACK);
        Tavola.drawText("X: " + X, MaxX/2, MaxY/2 - 10, Disegna);
        Tavola.drawText("Y: " + Y, MaxX / 2, MaxY / 2 + 10, Disegna);
        Tavola.drawText("Collisione: " + Collisione, MaxX / 2, MaxY / 2 + 30, Disegna);

        Disegna.setTextAlign(Paint.Align.LEFT);
        Disegna.setColor(Color.argb(30,0,0,0));
        for(int i = 0; i < Max; i++){
            Tavola.drawText("Coordinata "+i+": "+CoordLines[i], 20, 30+i*60, Disegna);
            Tavola.drawText("Collisione "+i+": "+Collisioni[i],20, 60+i*60, Disegna);
        }

    }

    void DisegnaQuadrato(){
        Disegna.setColor(Color.BLACK);
        Tavola.drawRect(XQ - 2, YQ - 2, XQ + LQ + 2, YQ + LQ + 2, Disegna);
        Disegna.setColor(Color.WHITE);
        Tavola.drawRect(XQ, YQ, XQ + LQ, YQ + LQ, Disegna);
    }

    void MuoviMondo(){
        for(int i = 0; i < 4; i++){
            CoordLines[i] = CoordLines[i] - MuoviMondo;
        }

    }

    void MuoviQuadrato(){
       YQ = YQ + MuoviQuadrato;
    }

    public void onDraw(Canvas C){
	    Tavola = C; this.setOnTouchListener(this);

        if(FlagInizializzazioni) Inizializzazioni(); FlagInizializzazioni = false;

	    Disegna.setColor(Color.WHITE);
	    Tavola.drawPaint(Disegna);

        DisegnaQuadrato();
        DisegnaTesti();
        MuoviMondo();
        MuoviQuadrato();

        Disegna.setColor(Color.RED);

        for(int i = 0; i < Max; i++){

            Tavola.drawLine(CoordLines[i], Yline, CoordLines[i] + 500, Yline, Disegna);
            Collisioni[i] = CollisioneQuadrato(CoordLines[i], CoordLines[i] + 500, Yline);

            if(Collisioni[i]){
               Collisione=true;
            }
            else Collisione=false;
        }

        invalidate();
   }

}

