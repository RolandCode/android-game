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

    public static boolean circles(Circle C1, Circle C2){
        float Dx = C1.position.x - C2.position.x;
        float Dy = C1.position.y - C2.position.y;
        return Math.hypot(Dx, Dy) < C1.radius+C2.radius;
    }

    public static boolean touched(float x, float y, Circle circle){
        Circle point = new Circle(x, y, 0.5f);
        return circles(point, circle);
    }

    public static void rilevaMuri(int Mx, int My, Circle circle){
        if (circle.position.x - circle.radius <= 0){
            circle.speed.x = -circle.speed.x;
            circle.position.x = circle.radius;
        }

        if (circle.position.x + circle.radius >= Mx){
            circle.speed.x = -circle.speed.x;
            circle.position.x = Mx - circle.radius;
        }
        if (circle.position.y - circle.radius <= 0){
            circle.speed.y = -circle.speed.y;
            circle.position.y = circle.radius;
        }
        if (circle.position.y + circle.radius >= My){
            circle.speed.y = -circle.speed.y;
            circle.position.y = My - circle.radius;
        }

    }

    public static void elastic(Circle C1, Circle C2){
        float M1 = (float)Math.sqrt(C1.radius);
        float M2 = (float)Math.sqrt(C2.radius);

        double V1 = C1.speed.getModule();
        double V2 = C2.speed.getModule();

        double D1 = C1.speed.getArg();
        double D2 = C2.speed.getArg();

        float X1 = C1.position.x;
        float X2 = C2.position.x;
        float Y1 = C1.position.y;
        float Y2 = C2.position.y;

        float Dx = X1 - X2;
        float Dy = Y1 - Y2;

        double Alpha = Math.atan2(Dy, Dx);

        float moduloSv = (C1.radius+C2.radius)-(float)Math.hypot(Dy, Dx);

        Vettore S1 = new Vettore(moduloSv,(float)Math.toDegrees(Alpha), true);
        Vettore S2 = new Vettore(moduloSv,(float)Math.toDegrees(Alpha) + 180, true);

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

        if(!C1.locked){
            C1.position.x = X1 + S1.x/2;
            C1.position.y = Y1 + S1.y/2;
        }
        if(!C2.locked){
            C2.position.x = X2 + S2.x/2;
            C2.position.y = Y2 + S2.y/2;
        }
    }

    public static void rilevaLinea(Circle circle, float x1, float x2, float y1, float y2){
        float xL = circle.position.x - circle.radius;
        float xR = circle.position.x + circle.radius;
        float yT = circle.position.y - circle.radius;
        float yB = circle.position.y + circle.radius;

        if(y1 == y2){ // Orizzontale
            if ((y1 >= yT && y1 <= yB) && ((xL >= x1 && xR <= x2) || (xL <= x1 && xR >= x2))){
                circle.speed.y = -circle.speed.y;

                if(Math.abs(yT - y1) <= Math.abs(yB - y1))
                    circle.position.y = y1 + circle.radius;
                else circle.position.y = y1 - circle.radius;
            }
        }

        if(x1 == x2){   // Verticale
            if ((x1 >= xL && x1 <= xR) && ((yT <= y1 && yB >= y2) || (yT >= y1 && yB <= y2))){
                circle.speed.x = -circle.speed.x;

                if(Math.abs(xL - x1) <=  Math.abs(xR - x1))
                    circle.position.x = x1 + circle.radius;
                else circle.position.x = x1 - circle.radius;
            }
        }
    }
}