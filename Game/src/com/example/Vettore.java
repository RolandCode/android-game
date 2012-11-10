package com.example;


public final class Vettore {

		  public int x;
		  public int y;		  
		  
		  public Vettore() {
			  this.x = 0;
			  this.y = 0;
		  }
		  
		  public Vettore(int x, int y) {
			  this.x = x;
			  this.y = y;
		  }
		  
		  public Vettore(Vettore altro) {
			  this.x = altro.x;
			  this.y = altro.y;
		  }


		  public Vettore somma(Vettore altro) {
		    Vettore ret = new Vettore(); // Creiamo un nuovo vettore. Questo sara` il risultato di questa funzione
		    ret.x = altro.x + x; // sommiamo le componenti x di questo vettore e dell'altro
		    ret.y = altro.y + y; // Stessa cosa, con la componente y.
		    return ret; // diamo il risultato. 
		  }

		  public Vettore differenza(Vettore altro) {
		    Vettore ret = new Vettore();
		    ret.x = altro.x - x;
		    ret.y = altro.y - y;
		    return ret;
		  }

		  public Vettore prodotto(Vettore altro) {
		    Vettore ret = new Vettore();
		    ret.x = altro.x * x;
		    ret.y = altro.y * y;
		    return ret;
		  }
		  
		  public Vettore prodotto(float fat) {
			    Vettore ret = new Vettore();
			    ret.x *= fat;
			    ret.y *= fat;
			    return ret;
		  }
		  
		  public Vettore divisione(Vettore altro) {
			Vettore ret = new Vettore();
			ret.x = altro.x / x;
		    ret.y = altro.y / y;
		    return ret;
		  }
		  
		  public Vettore divisione(float fat) {
			    Vettore ret = new Vettore();
			    ret.x /= fat;
			    ret.y /= fat;
			    return ret;
		  }
		  
		  public Vettore ProdottoPunto(Vettore altro) {
			Vettore ret = new Vettore();
			// TODO: implementare questo metodo.
			// usare la funzione Math.cos()
			//
			return ret; 
		  }
		  
		  public String toString() {
			  return "(" + x + "," + y + ")"; 
		  }

}
