package com.example;

import android.content.res.Resources;
import android.graphics.*;

public class Sprite {

    public static int X = 0;
    public static int Y = 1;

    public static final int STOP          = 0;
    public static final int LEFT          = 1;
    public static final int RIGHT         = 2;
    public static final int TOP           = 3;
    public static final int BOTTOM        = 4;
    public static final int LEFT_BOTTOM   = 5;
    public static final int LEFT_TOP      = 6;
    public static final int RIGHT_BOTTOM  = 7;
    public static final int RIGHT_TOP     = 8;

    private float Position[] = {0, 0};
    private Rect Sagoma = new Rect();
    private int Width = 0;
    private int Height = 0;
    private Bitmap Texture;
    private int Colore = Color.WHITE;

    public void setPosition(float X, float Y){
        this.Position[0] = X;
        this.Position[1] = Y;
        updateSagoma();
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
    private void moveLeft(float MoveCoefficient, float FrameDelay){
       Position[0] -= MoveCoefficient * FrameDelay;
    }
    private void moveRight(float MoveCoefficient, float FrameDelay){
       Position[0] += MoveCoefficient * FrameDelay;
    }
    private void moveTop(float MoveCoefficient, float FrameDelay){
        Position[1] -= MoveCoefficient * FrameDelay;
    }
    private void moveBottom(float MoveCoefficient, float FrameDelay){
        Position[1] += MoveCoefficient * FrameDelay;
    }
    public void move(final int Type, float MoveCoefficient, float FrameDelay){
        switch(Type){
            case LEFT:
                moveLeft(MoveCoefficient, FrameDelay);
                break;
            case RIGHT:
                moveRight(MoveCoefficient, FrameDelay);
                break;
            case TOP:
                moveTop(MoveCoefficient, FrameDelay);
                break;
            case BOTTOM:
                moveBottom(MoveCoefficient, FrameDelay);
                break;
            case LEFT_TOP:
                moveLeft(MoveCoefficient, FrameDelay);
                moveTop(MoveCoefficient, FrameDelay);
                break;
            case RIGHT_TOP:
                moveRight(MoveCoefficient, FrameDelay);
                moveTop(MoveCoefficient, FrameDelay);
                break;
            case LEFT_BOTTOM:
                moveLeft(MoveCoefficient, FrameDelay);
                moveBottom(MoveCoefficient, FrameDelay);
                break;
            case RIGHT_BOTTOM:
                moveRight(MoveCoefficient, FrameDelay);
                moveBottom(MoveCoefficient, FrameDelay);
                break;
        } updateSagoma();
    }
    public void move(final int Type, float MoveCoefficient1, float MoveCoefficient2, float FrameDelay){
        switch(Type){
            case LEFT:
                moveLeft(MoveCoefficient1, FrameDelay);
                break;
            case RIGHT:
                moveRight(MoveCoefficient1, FrameDelay);
                break;
            case TOP:
                moveTop(MoveCoefficient1, FrameDelay);
                break;
            case BOTTOM:
                moveBottom(MoveCoefficient1, FrameDelay);
                break;
            case LEFT_TOP:
                moveLeft(MoveCoefficient1, FrameDelay);
                moveTop(MoveCoefficient2, FrameDelay);
                break;
            case RIGHT_TOP:
                moveRight(MoveCoefficient1, FrameDelay);
                moveTop(MoveCoefficient2, FrameDelay);
                break;
            case  LEFT_BOTTOM:
                moveLeft(MoveCoefficient1, FrameDelay);
                moveBottom(MoveCoefficient2, FrameDelay);
                break;
            case RIGHT_BOTTOM:
                moveRight(MoveCoefficient1, FrameDelay);
                moveBottom(MoveCoefficient2, FrameDelay);
                break;
        }
        updateSagoma();
    }
    public void move(int Type){
            moveLeft(0, 0);
            moveRight(0, 0);
            moveTop(0, 0);
            moveBottom(0, 0);
            updateSagoma();
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
        updateSagoma();
    }
    public void createSprite(int W, int H){
        Width = W; Height = H;
        Texture = null;
        updateSagoma();
    }
    public void draw(Canvas C, Paint P){
       P.setColor(Colore);
       if(Texture==null)C.drawRect(Position[0], Position[1], Position[0]+Width, Position[1]+Height, P);
       else C.drawBitmap(Texture, Position[0], Position[1], P);
    }
    public boolean collide(Sprite Oggetto){
        return Sagoma.intersect(Oggetto.getSagoma());
    }
}
