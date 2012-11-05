package com.example;

import android.content.res.Resources;
import android.graphics.*;

public class Sprite {

    private float Position[] = {0, 0};
    private Rect Sagoma = new Rect();
    private int Width = 0;
    private int Height = 0;
    private Bitmap Texture;
    public static int X = 0;
    public static int Y = 1;
    private int Colore = Color.BLACK;


    public void setPosition(float X, float Y){
        this.Position[0] = X;
        this.Position[1] = Y;
    }
    public float[] getPosition(){
        return this.Position;
    }
    public float getPosition(int XorY){
        if(XorY == 0){
            return this.Position[0];
        }
        else if(XorY == 1){
            return this.Position[1];
        }
        return 0;
    }
    public void MoveLeft(int MoveCoefficient, int FrameDelay){
       Position[0] -= MoveCoefficient * FrameDelay;
    }
    public void MoveRight(int MoveCoefficient, int FrameDelay){
       Position[0] += MoveCoefficient * FrameDelay;
    }
    public void MoveTop(int MoveCoefficient, int FrameDelay){
        Position[1] -= MoveCoefficient * FrameDelay;
    }
    public void MoveBottom(int MoveCoefficient, int FrameDelay){
        Position[1] += MoveCoefficient * FrameDelay;
    }
    private void updateSagoma(){
        Sagoma.left = (int)Position[0];
        Sagoma.top = (int)Position[1];
        Sagoma.bottom = (int)Position[1] + Height;
        Sagoma.right = (int)Position[0] + Width;
    }
    private Rect getSagoma(){
        return Sagoma;
    }
    public void createSprite(Resources R, int Foto, int W, int H){
        Width = W; Height = H;
        Texture = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(R, Foto), Width, Height, true);
        updateSagoma();
    }
    public void createSprite(int W, int H, int C){
        Width = W; Height = H;
        Colore = C;
        Texture = null;
    }

    public void draw(Canvas C, Paint P){
       P.setColor(Colore);
       if(Texture==null)C.drawRect(Position[0], Position[1], Position[0]+Width, Position[1]+Height, P);
       else C.drawBitmap(Texture, Position[0], Position[1], P);
       updateSagoma();
    }

    public boolean collide(Sprite Oggetto){
        return Sagoma.intersect(Oggetto.getSagoma());
    }
}
