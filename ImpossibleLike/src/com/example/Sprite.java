public class Vettore {
  public int x;
  public int y;

  public Vettore Somma() {
    Vettore ret = new Vettore();
    return ret;
  }

  public Vettore Differenza() {
    Vettore ret = new Vettore();
    return ret;
  }

  public Vettore Prodotto() {
    Vettore ret = new Vettore();
    return ret;
  }
}

public class Sprite {
  public Vettore pos;
  private Vettore vel;

  public void ImpostaVel(Vettore vel) {
    this.vel = vel; 
  }

}
