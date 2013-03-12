package com.example;

import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.view.*;
import java.util.*;

public class AnalogControlledView extends View implements View.OnTouchListener{

	public MotionEvent e = null;
	
	Paint p = new Paint();

    public ArrayList<Circle> circles = new ArrayList<Circle>();

    private PhysicsEngine phEngine;

    private Random random = new Random();
	int[] col = {Color.BLUE, Color.GREEN, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK};
	AnalogStick analog;
	AnalogStick analog1;

	Resources risorse = getResources();
	
	Bitmap background;

	
   public AnalogControlledView(Context context){
        super(context);
        phEngine = new PhysicsEngine();

	   background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(risorse, R.drawable.sfondo), 480, 800, true);
	   p = new Paint();
        for (int i = 0; i < 4; i++){
			if(i < 6)
            circles.add(new Circle(phEngine, 40, col[i]));
		   else           circles.add(new Circle(phEngine, 40));
            circles.get(i).body.setCenter(new Vector(random.nextInt(480), random.nextInt(800)));
        }
		analog = new AnalogStick(new Vector(240, 700), 70);
	   analog1 = new AnalogStick(new Vector(240, 100), 70);
	//	analog.manual = false;
	}

    public boolean onTouch(View p1, MotionEvent e) {
		this.e = e;
        return true;
    }
    void updateObjects(){
        phEngine.update(10);
        for (int i = 0; i < circles.size(); i++){
            if(!circles.get(i).alive){
                for (Circle child: circles.get(i).childrens){
                    circles.add(child);
                } circles.remove(i);
            }
        }
    }
    void drawObjects(Canvas canvas){
        updateObjects();
        for(Circle Object: circles){
            Object.draw(canvas, e);
			if(Object.getCenter().y > canvas.getHeight() - 200 - Object.r){
				Object.body.setSpeed(new Vector(Object.body.getSpeed().x, 0));
				Object.setCenter(new Vector(Object.getCenter().x, canvas.getHeight() - 200 - Object.r));
			}
			
			else if(Object.getCenter().y < 200 + Object.r){
				Object.body.setSpeed(new Vector(Object.body.getSpeed().x, 0));
				Object.setCenter(new Vector(Object.getCenter().x, 200 + Object.r));
			}
			
			if(Object.getCenter().x > canvas.getWidth() - Object.r || Object.getCenter().x > Object.r){
				Object.body.setSpeed(new Vector(0, Object.body.getSpeed().y));
			}

        }
    }

    public void onDraw(Canvas canvas){
        this.setOnTouchListener(this);
        Game.getInstance().setCanvas(canvas);
    	canvas.drawBitmap(background, 0, 0, p);
        drawObjects(canvas);
		
		analog.draw(canvas, e);

		analog1.draw(canvas, e);
		
		if(analog.pressed) circles.get(0).body.setSpeed(analog.axis.mul(0.1f));
		if(analog1.pressed) circles.get(1).body.setSpeed(analog1.axis.mul(0.1f));
		analog.initposition = new Vector(240, 700);
		p.setColor(Color.WHITE);
		canvas.drawLine(0, 200, canvas.getWidth(), 200, p);
		canvas.drawLine(0, canvas.getHeight()-200, canvas.getWidth(), canvas.getHeight()-200, p);
        invalidate();
    }
}
	
