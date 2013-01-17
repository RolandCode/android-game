package com.example;

public class LancioElastico {
    private Vettore carica = new Vettore();
	private boolean charging = false;
	private Circle parent;
	
	public LancioElastico(Circle parent){
		this.parent = parent;
	}
	
	private void rileva(float x, float y){
     	    this.carica.setX(-(x - this.parent.getX())/10);
    	    this.carica.setY(-(y - this.parent.getY())/10);
	}
	
	private void erase(){
		this.carica = new Vettore();
	}
	
	public Vettore getCarica(){
		return this.carica;
	}
	
	public Vettore getDrawable(int type){
		if (type == 0) return new Vettore(-this.carica.getX(), -this.carica.getY(), 0);
		else return new Vettore(this.carica.getX()*10000, this.carica.getY()*10000, 0);
	}
	
	public void sayPressed(){
		this.charging = true;
	}

	public void sayCharging(float x, float y){
		this.rileva(x, y);
	}

	public void sayRelased(){
		this.parent.speed = this.getCarica();
		this.charging = false;
		this.erase();		
	}
	public boolean isCharging(){
		return this.charging;
	}
	
}
