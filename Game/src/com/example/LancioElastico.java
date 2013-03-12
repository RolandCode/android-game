package com.example;

import android.graphics.*;

public class LancioElastico
 {
    public Vector carica = new Vector();
    private Vector center = new Vector();
	private boolean charging = false;
	private Circle parent;
	Paint p = new Paint();
	
	public LancioElastico(Circle parent){
		this.parent = parent;
	}
	
	private void rileva(float x, float y){
     	    this.carica.x = -(x - center.x)/10;
    	    this.carica.y = -(y - center.y)/10;
	}
	
	private void erase(){
		this.carica = new Vector();
	}
	
	public void draw(Canvas c){
		if(charging){
			RectF oval = new RectF(center.x, 0, center.x + carica.x*(-10), 800);
			p.setColor(Color.argb(90, 255, 0, 0));
			c.drawLine(center.x + carica.x*(-10), center.y + carica.y*(-10), center.x, 0, p);
			c.drawLine(center.x + carica.x*(-10), center.y + carica.y*(-10), center.x, 800, p);
//	    	c.drawLine(center.x, 0, center.x, 800, p);
//          carica.mul(10000).draw(c, center, Color.argb(90, 255, 255, 255));
//	    	carica.mul(-10000).draw(c, center, Color.argb(90, 255, 255, 255));
//       	c.drawArc(oval, 0, 90, false, p);
			if(parent.color == Color.BLACK) carica.mul(-1).draw(c, center, Color.RED);	
            else carica.mul(-1).draw(c, center, parent.color);	
        }
	}

	public void sayPressed(){
		charging = true;
		parent.launching = true;
		center = new Vector(parent.body.getCenter().x, parent.body.getCenter().y);
        parent.body.setSpeed(new Vector());
	}

	public void sayCharging(float x, float y){
		this.rileva(x, y);
		parent.body.setSpeed(new Vector());
		parent.body.setCenter(new Vector(x, y));
    }

	public void sayRelased(){
		parent.launching = false;		
		this.parent.body.setSpeed(this.carica);
		this.charging = false;
        this.erase();
		parent.id = 8;
	}

	public boolean isCharging(){
		return this.charging;
	}
	
}
