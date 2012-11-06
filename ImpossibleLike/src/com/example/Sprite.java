package com.example;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.Vettore;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Sprite {
  public Vettore pos;
  protected Vettore vel;
  protected float vel_fat; // fattore velocita`

  public void impostaVel(Vettore vel) {
    this.vel = vel; 
  }
  
  public void update(int deltaMS) {
	  aggiornaPosizione();
	  // Disegna questo sprite
	  // disegna();
	  
	  
  }
  
  private void aggiornaPosizione() {
	  // Muovi questo sprite.
	  pos = pos.somma(vel.prodotto(vel_fat * deltaMS));
  }

}

public class PlayerSprite extends Sprite {
	  
	private Paint disegna;
	private Canvas tavola;
	
    private int XQuadrato,
                YQuadrato,
                LatoQuadrato;
	 
	public PlayerSprite(Paint disegna, Canvas tavola) {
	   // Iniziallizza questo sprite
       this.disegna = disegna;
       this.tavola = tavola;
       XQuadrato = 50;
       YQuadrato = 50;
       LatoQuadrato = 50;
	}
	
	  public void update(int deltaMS) {
		  super.update(deltaMS);
		  // Disegna questo sprite
		  DisegnaQuadrato();
	  }
	
   private void DisegnaQuadrato(){
       disegna.setColor(Color.RED);
       tavola.drawRect(XQuadrato - 2, YQuadrato - 2, XQuadrato + LatoQuadrato + 2, YQuadrato + LatoQuadrato + 2, disegna);
       disegna.setColor(Color.YELLOW);
       tavola.drawRect(XQuadrato, YQuadrato, XQuadrato + LatoQuadrato, YQuadrato + LatoQuadrato, disegna);
   }
}
