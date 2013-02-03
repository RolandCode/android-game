package com.example;

import android.graphics.Rect;

public class UtilCollisioni {

    public static final int CIRCLE_CICLE = 0;
    public static final int PERFECT_PIXEL = 1;

//    public static boolean getCollision(final int type, Sprite SpriteA, Sprite SpriteB){
//        switch (type){
//            case CIRCLE_CICLE:
//                return collisionCircleCircle(SpriteA, SpriteB);
//            case PERFECT_PIXEL:
//                return collisionPerfectPixel(SpriteA, SpriteB);
//            default: break;
//        }
//        return false;
//    }

//    private static boolean collisionCircleCircle(Sprite SpriteA, Sprite SpriteB){
//
//        float DistanzaX = Math.abs(SpriteA.getPosition().x - SpriteB.getPosition().x + SpriteA.getWidth()/2 - SpriteB.getWidth()/2);
//        float DistanzaY = Math.abs(SpriteA.getPosition().y - SpriteB.getPosition().y + SpriteA.getHeight()/2 - SpriteB.getHeight()/2);
//
//        return Math.sqrt(Math.pow(DistanzaX, 2)+ Math.pow(DistanzaY, 2)) <= SpriteA.getWidth()/2 + SpriteB.getWidth()/2;
//    }

//    private static boolean collisionPerfectPixel(Sprite SpriteA, Sprite SpriteB){
//
//        if(Rect.intersects(SpriteA.getShape(), SpriteB.getShape())){
//
//            Rect RectIntersezione = getRectIntersezione(SpriteA.getShape(), SpriteB.getShape());
//            for (int X = RectIntersezione.left; X < RectIntersezione.right; X+=10) {
//                for (int Y = RectIntersezione.top; Y < RectIntersezione.bottom; Y+=10) {
//                    try {
//                        if(TrovaPixel(SpriteA, X, Y) != 0 && TrovaPixel(SpriteB, X, Y) != 0) { return true; }
//                    } catch (Exception e) {}
//                }
//            }
//        } return false;
//    }

//    private static int TrovaPixel(Sprite Oggetto, int X, int Y) {
//        return Oggetto.cloneBitmap().getPixel(X - (int)Oggetto.getPosition().x, Y - (int)Oggetto.getPosition().y);
//    }

    public static Rect getRectIntersezione(Rect A, Rect B) {
        return new Rect(
                Math.max(A.left,   B.left),
                Math.max(A.top,    B.top),
                Math.min(A.right,  B.right),
                Math.min(A.bottom, B.bottom)
        );
    }


    public static boolean isTouched(Vector position, Circle circle){
        AbstractCircle point = new AbstractCircle(position, 0.5f);
        return CircleCircleCollision(point, (CircleBody) circle.body);
    }
    
    public static boolean CircleCircleCollision(AbstractCircle C1, AbstractCircle C2) {
    	float Dx = C1.center.x - C2.center.x;
    	float Dy = C1.center.y - C2.center.y;
    	return Math.hypot(Dx, Dy) < (C1.radius + C2.radius);
    }

//    public static void rilevaMuri(int Mx, int My, Circle circle){
//        if (circle.position.x - circle.radius <= 0){
//            circle.speed.x = -circle.speed.x;
//            circle.position.x = circle.radius;
//        }
//
//        if (circle.position.x + circle.radius >= Mx){
//            circle.speed.x = -circle.speed.x;
//            circle.position.x = Mx - circle.radius;
//        }
//        if (circle.position.y - circle.radius <= 0){
//            circle.speed.y = -circle.speed.y;
//            circle.position.y = circle.radius;
//        }
//        if (circle.position.y + circle.radius >= My){
//            circle.speed.y = -circle.speed.y;
//            circle.position.y = My - circle.radius;
//        }
//
//    }


//    public static void rilevaLinea(Circle circle, float x1, float x2, float y1, float y2){
//        float xL = circle.position.x - circle.radius;
//        float xR = circle.position.x + circle.radius;
//        float yT = circle.position.y - circle.radius;
//        float yB = circle.position.y + circle.radius;
//
//        if(y1 == y2){ // Orizzontale
//            if ((y1 >= yT && y1 <= yB) && ((xL >= x1 && xR <= x2) || (xL <= x1 && xR >= x2))){
//                circle.speed.y = -circle.speed.y;
//
//                if(Math.abs(yT - y1) <= Math.abs(yB - y1))
//                    circle.position.y = y1 + circle.radius;
//                else circle.position.y = y1 - circle.radius;
//            }
//        }

//        if(x1 == x2){   // Verticale
//            if ((x1 >= xL && x1 <= xR) && ((yT <= y1 && yB >= y2) || (yT >= y1 && yB <= y2))){
//                circle.speed.x = -circle.speed.x;
//
//                if(Math.abs(xL - x1) <=  Math.abs(xR - x1))
//                    circle.position.x = x1 + circle.radius;
//                else circle.position.x = x1 - circle.radius;
//            }
//        }
//    }
}
