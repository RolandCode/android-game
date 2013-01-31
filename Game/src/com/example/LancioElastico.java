package com.example;

public class LancioElastico {
    public Vettore carica = new Vettore();
	private boolean charging = false;
	private Circle parent;
    private float x, y = 0;
	
	public LancioElastico(Circle parent){
		this.parent = parent;
	}
	
	private void rileva(float x, float y){
     	    this.carica.x = -(x - this.x)/10;
    	    this.carica.y = -(y - this.y)/10;
	}
	
	private void erase(){
		this.carica = new Vettore();
	}

	public void sayPressed(){
		this.charging = true;
        this.x = parent.position.x;
        this.y = parent.position.y;
        parent.speed.setModule(0);
	}

	public void sayCharging(float x, float y){
		this.rileva(x, y);
        parent.position.x = x;
        parent.position.y = y;
    }

	public void sayRelased(){
		this.parent.speed = this.carica;
		this.charging = false;
        this.erase();
	}

	public boolean isCharging(){
		return this.charging;
	}
	
}
