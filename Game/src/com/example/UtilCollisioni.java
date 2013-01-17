package com.example;

import android.graphics.Rect;

public class UtilCollisioni {

    public static final int CIRCLE_CICLE = 0;
    public static final int PERFECT_PIXEL = 1;


    public static boolean getCollision(final int type, Sprite SpriteA, Sprite SpriteB){
        switch (type){
            case CIRCLE_CICLE:
                return collisionCircleCircle(SpriteA, SpriteB);
            case PERFECT_PIXEL:
                return collisionPerfectPixel(SpriteA, SpriteB);
            default: break;
        }
        return false;
    }

    private static boolean collisionCircleCircle(Sprite SpriteA, Sprite SpriteB){

        float DistanzaX = Math.abs(SpriteA.getPosition().x - SpriteB.getPosition().x + SpriteA.getWidth()/2 - SpriteB.getWidth()/2);
        float DistanzaY = Math.abs(SpriteA.getPosition().y - SpriteB.getPosition().y + SpriteA.getHeight()/2 - SpriteB.getHeight()/2);

        return Math.sqrt(Math.pow(DistanzaX, 2)+ Math.pow(DistanzaY, 2)) <= SpriteA.getWidth()/2 + SpriteB.getWidth()/2;
    }

    private static boolean collisionPerfectPixel(Sprite SpriteA, Sprite SpriteB){

        if(Rect.intersects(SpriteA.getShape(), SpriteB.getShape())){

            Rect RectIntersezione = getRectIntersezione(SpriteA.getShape(), SpriteB.getShape());
            for (int X = RectIntersezione.left; X < RectIntersezione.right; X+=10) {
                for (int Y = RectIntersezione.top; Y < RectIntersezione.bottom; Y+=10) {
                    try {
                        if(TrovaPixel(SpriteA, X, Y) != 0 && TrovaPixel(SpriteB, X, Y) != 0) { return true; }
                    } catch (Exception e) {}
                }
            }
        } return false;
    }

    private static int TrovaPixel(Sprite Oggetto, int X, int Y) {
        return Oggetto.cloneBitmap().getPixel(X - (int)Oggetto.getPosition().x, Y - (int)Oggetto.getPosition().y);
    }

    public static Rect getRectIntersezione(Rect A, Rect B) {
        return new Rect(
                Math.max(A.left,   B.left),
                Math.max(A.top,    B.top),
                Math.min(A.right,  B.right),
                Math.min(A.bottom, B.bottom)
        );
    }
}