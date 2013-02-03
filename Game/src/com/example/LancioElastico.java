package com.example;

public class LancioElastico {
    public Vector carica = new Vector();
    private Vector center = new Vector();
	private boolean charging = false;
	private Circle parent;
	
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

	public void sayPressed(){
		this.charging = true;
		center = parent.body.getCenter();
        parent.body.setSpeed(new Vector());
	}

	public void sayCharging(float x, float y){
		this.rileva(x, y);
		parent.body.setCenter(new Vector(x, y));
    }

	public void sayRelased(){
		this.parent.body.setSpeed(this.carica);
		this.charging = false;
        this.erase();
	}

	public boolean isCharging(){
		return this.charging;
	}
	
}
