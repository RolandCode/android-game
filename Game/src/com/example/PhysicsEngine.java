package com.example;

import java.util.*;

public class PhysicsEngine {
  private LinkedList<Body> bodies;
  
  public PhysicsEngine() {
	  bodies = new LinkedList<Body>();
  }
  
  public void update(int ms) {
    processCollisions();
    simulate(ms);
  }

  private void processCollisions() {
    if(bodies.size() < 2) {
      return;
    }

    // use two iterators, the first is the caller, the second is the calle
    // if collision happens, call onCollision(CollsiionInfo ob both sprites)
    //

    ListIterator<Body> callerIt = bodies.listIterator(0); 
    ListIterator<Body> otherIt = bodies.listIterator(1);
    int nextOtherIndex = 2;

//     For each unique pair, test collision
//     Iterates from the first collider, then from the second etc.
    while (callerIt.hasNext()) {
      Body caller = callerIt.next();
      while (otherIt.hasNext()) {
    	Body other = otherIt.next();
        try {
          caller.collide(other);
        } catch(Exception e) {
          System.out.println("Unhandled Collision!");
        }
      }
      if(nextOtherIndex < bodies.size())
    	  otherIt = bodies.listIterator(nextOtherIndex);
      nextOtherIndex++;
    }
  }

  private void simulate(int ms) {
    for (Body body : bodies) {
      body.wallCollision();
      body.update(ms);
      body.debugDraw();
    }
  }

  protected  <T extends Body> void addBody(T collider) {
    bodies.add(collider);
  }

}
