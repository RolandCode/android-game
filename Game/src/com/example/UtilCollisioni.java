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

        float DistanzaX = Math.abs(SpriteA.getPosition().getX() - SpriteB.getPosition().getX() + SpriteA.getWidth()/2 - SpriteB.getWidth()/2);
        float DistanzaY = Math.abs(SpriteA.getPosition().getY() - SpriteB.getPosition().getY() + SpriteA.getHeight()/2 - SpriteB.getHeight()/2);

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
        return Oggetto.cloneBitmap().getPixel(X - (int)Oggetto.getPosition().getX(), Y - (int)Oggetto.getPosition().getY());
    }

    public static Rect getRectIntersezione(Rect A, Rect B) {
        return new Rect(
                Math.max(A.left,   B.left),
                Math.max(A.top,    B.top),
                Math.min(A.right,  B.right),
                Math.min(A.bottom, B.bottom)
        );
    }

    public static boolean circles(Circle C1, Circle C2){
        float Dx = C1.getX() - C2.getX();
        float Dy = C1.getY() - C2.getY();
        return Math.hypot(Dx, Dy) < C1.getRadius()+C2.getRadius();
    }
    public static boolean touched(float x, float y, Circle circle){
        Circle point = new Circle(x, y, 0.5f);
        return circles(point, circle);
    }
    public static void rilevaMuri(int Mx, int My, Circle C, float lose){
        if (C.getX()-C.getRadius()<=0){
            C.speed.setX(-lose-C.speed.getX());
            C.setX(C.getRadius());
        }

        if (C.getX()+C.getRadius()>=Mx){
            C.speed.setX(lose-C.speed.getX());
            C.setX(Mx - C.getRadius());
        }
        if (C.getY()-C.getRadius()<=0){
            C.speed.setY(-lose-C.speed.getY());
            C.setY(C.getRadius());
        }
        if (C.getY()+C.getRadius()>=My){
            C.speed.setY(lose-C.speed.getY());
            C.setY(My - C.getRadius());
        }

    }

    public static void elastic(Circle C1, Circle C2){
        float M1 = (float)Math.sqrt(C1.getRadius());
        float M2 = (float)Math.sqrt(C2.getRadius());

        double V1 = C1.speed.getModule();
        double V2 = C2.speed.getModule();

        double D1 = C1.speed.getArg();
        double D2 = C2.speed.getArg();

        float X1 = C1.getX();
        float X2 = C2.getX();
        float Y1 = C1.getY();
        float Y2 = C2.getY();

        float Dx = X1 - X2;
        float Dy = Y1 - Y2;

        double Alpha = Math.atan2(Dy, Dx);

        float moduloSv = (C1.getRadius()+C2.getRadius())-(float)Math.hypot(Dy, Dx);

        Vettore S1 = new Vettore(moduloSv,(float)Math.toDegrees(Alpha), 1);
        Vettore S2 = new Vettore(moduloSv,(float)Math.toDegrees(Alpha) + 180, 1);

        double Vi1x = V1*Math.cos(D1-Alpha);
        double Vi2x = V2*Math.cos(D2-Alpha);
        double Vi1y = V1*Math.sin(D1-Alpha);
        double Vi2y = V2*Math.sin(D2-Alpha);

        double Vf1x = (Vi1x*(M1-M2)+2*M2*Vi2x)/(M1+M2);
        double Vf2x = (Vi2x*(M2-M1)+2*M1*Vi1x)/(M1+M2);

        V1 = Math.hypot(Vf1x, Vi1y);
        V2 = Math.hypot(Vf2x, Vi2y);

        D1 = Math.toDegrees(Math.atan2(Vi1y, Vf1x)+Alpha);
        D2 = Math.toDegrees(Math.atan2(Vi2y, Vf2x)+Alpha);

        C1.speed.setModule((float)V1);
        C2.speed.setModule((float)V2);

        C1.speed.setArg((float)D1);
        C2.speed.setArg((float)D2);

        C1.setX(X1 + S1.getX()/2);
        C1.setY(Y1 + S1.getY()/2);
        C2.setX(X2 + S2.getX()/2);
        C2.setY(Y2 + S2.getY()/2);
    }

}