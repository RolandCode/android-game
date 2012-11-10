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
    public static final int INVERT        = 9;

    public static final int HORIZONTAL_COLLISION  = 0;
    public static final int VERTICAL_COLLISION    = 1;


    private int TypeOfCollision = HORIZONTAL_COLLISION;

    private int CurrentDirection = STOP;
    private float MoveCoefficientHorizontal = 0;
    private float MoveCoefficientVertical = 0;

    private float Position[] = {0, 0};
    private Rect Sagoma = new Rect();
    private int Width = 0;
    private int Height = 0;
    private Bitmap Texture;
    private int Colore = Color.WHITE;

    public void setCurrentDirection(int CurrentDirection){
        this.CurrentDirection = CurrentDirection;
    }
    public void setMoveCoefficientHorizontal(float MoveCoefficientHorizontal){
        this.MoveCoefficientHorizontal = MoveCoefficientHorizontal;
    }
    public void setMoveCoefficientVertical(float MoveCoefficientVertical){
        this.MoveCoefficientVertical = MoveCoefficientVertical;
    }

    public int getCurrentDirection(){
        return this.CurrentDirection;
    }
    public float getMoveCoefficientHorizontal(){
        return this.MoveCoefficientHorizontal;
    }
    public float getMoveCoefficientVertical(){
        return this.MoveCoefficientVertical;
    }

    public void reverseDirection(){
        switch (TypeOfCollision){
            case HORIZONTAL_COLLISION:
                switch(CurrentDirection){
                    case LEFT:
                        CurrentDirection = RIGHT;
                        break;
                    case RIGHT:
                        CurrentDirection = LEFT;
                        break;
                    case TOP:
                        CurrentDirection = BOTTOM;
                        break;
                    case BOTTOM:
                        CurrentDirection = TOP;
                        break;
                    case LEFT_TOP:
                        CurrentDirection = RIGHT_TOP;
                        break;
                    case RIGHT_TOP:
                        CurrentDirection = LEFT_TOP;
                        break;
                    case LEFT_BOTTOM:
                        CurrentDirection = RIGHT_BOTTOM;
                        break;
                    case RIGHT_BOTTOM:
                        CurrentDirection = LEFT_BOTTOM;
                        break;
                    case STOP:
                        CurrentDirection = STOP;
                        break;
                }
                break;

            case VERTICAL_COLLISION:
                switch(CurrentDirection){
                    case LEFT:
                        CurrentDirection = RIGHT;
                        break;
                    case RIGHT:
                        CurrentDirection = LEFT;
                        break;
                    case TOP:
                        CurrentDirection = BOTTOM;
                        break;
                    case BOTTOM:
                        CurrentDirection = TOP;
                        break;
                    case LEFT_TOP:
                        CurrentDirection = LEFT_BOTTOM;
                        break;
                    case RIGHT_TOP:
                        CurrentDirection = RIGHT_BOTTOM;
                        break;
                    case LEFT_BOTTOM:
                        CurrentDirection = LEFT_TOP;
                        break;
                    case RIGHT_BOTTOM:
                        CurrentDirection = RIGHT_TOP;
                        break;
                    case STOP:
                        CurrentDirection = STOP;
                        break;
                }
                break;
        }
    }

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
    public void move(float FrameDelay){
        switch(CurrentDirection){
            case LEFT:
                moveLeft(this.getMoveCoefficientHorizontal(), FrameDelay);
                break;
            case RIGHT:
                moveRight(this.getMoveCoefficientHorizontal(), FrameDelay);
                break;
            case TOP:
                moveTop(this.getMoveCoefficientVertical(), FrameDelay);
                break;
            case BOTTOM:
                moveBottom(this.getMoveCoefficientVertical(), FrameDelay);
                break;
            case LEFT_TOP:
                moveLeft(this.getMoveCoefficientHorizontal(), FrameDelay);
                moveTop(this.getMoveCoefficientVertical(), FrameDelay);
                break;
            case RIGHT_TOP:
                moveRight(this.getMoveCoefficientHorizontal(), FrameDelay);
                moveTop(this.getMoveCoefficientVertical(), FrameDelay);
                break;
            case LEFT_BOTTOM:
                moveLeft(this.getMoveCoefficientHorizontal(), FrameDelay);
                moveBottom(this.getMoveCoefficientVertical(), FrameDelay);
                break;
            case RIGHT_BOTTOM:
                moveRight(this.getMoveCoefficientHorizontal(), FrameDelay);
                moveBottom(this.getMoveCoefficientVertical(), FrameDelay);
                break;
            case STOP:
                moveBottom(0, 0);
                moveTop(0, 0);
                moveLeft(0, 0);
                moveRight(0, 0);
                break;
        } updateSagoma();
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
    public boolean collide(Sprite Oggetto, int TypeOfCollision){
        this.TypeOfCollision = TypeOfCollision;
        return Sagoma.intersect(Oggetto.getSagoma());
    }
}
