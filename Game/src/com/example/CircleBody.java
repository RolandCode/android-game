
package com.example;
import com.example.HollowAARBody.Side;

public class CircleBody extends AbstractCircle implements Body {
  
  public float radius;
  public double mass;
  public boolean locked;
  public Vector speed = new Vector();
  public Vector acceleration = new Vector();
  public Entity parent;
  protected PhysicsEngine phEngine;
  

 public CircleBody(PhysicsEngine engine, Entity parent, Vector center, float radius) {
   super(center, radius);
   parent.body = this;
   phEngine = engine;
   phEngine.addBody(this);
   this.parent = parent;
 
 }

  public AABB getAABB() {
    return new AABB(center, new Vector(radius*2, radius*2));	
  }
  
  // Double dispath, first call
  public boolean collide(Body body) {
    return body.collideImpl(this);
  }

  public void update(int ms) {
    this.speed = this.speed.add(this.acceleration);
	 // this.speed = new Vector((float)(this.speed.getModule()*Math.cos(this.speed.x)), (float)(this.speed.getModule()*Math.sin(this.speed.y)));
    this.center = this.center.add(this.speed);
    
    applyFriction(0.07f);

  }

    private void applyFriction(float decelerazione){
        if (this.speed.getModule() <= 2*decelerazione){
            this.acceleration.setModule(0);
            this.speed.setModule(0);
        }

        else this.acceleration.setModule(decelerazione);
        this.acceleration.setArg((float)Math.toDegrees(this.speed.getArg())+180);
    }
  
  public boolean collideImpl(CircleBody oth) {
    if( (this.center.sub(oth.center).sqrMagnitute()) < Math.pow(this.radius + oth.radius, 2) ) {
      if(!this.parent.launching && !oth.parent.launching)elastic(this, oth);
      return true;
    } else {
      return false;
    }
  }
  
  public boolean collideImpl(AARBody oth) {
	 return false;
  }
  
  public boolean collideImpl(HollowAARBody other) {
	  Vector distanceVec = other.center.sub(center);
	  boolean inside = false;
	  Vector otherHalfSize = other.size.div(2);
	  inside = Math.abs(distanceVec.x) < otherHalfSize.x - other.margin.x;
	  inside &= Math.abs(distanceVec.y) < otherHalfSize.y - other.margin.y;
	  boolean collision = false;
	  if (inside) {
		  if(Math.abs(distanceVec.x) + radius > otherHalfSize.x - other.margin.x) {
			  collision = true;
			  if (center.x < other.center.x) {
				  speed.x = -speed.x;
				  center.x = other.side(Side.IL) + radius;
			  } else {
				  speed.x = -speed.x;
				  center.x = other.side(Side.IR) - radius;
			  }
		  }
		  if(Math.abs(distanceVec.y) + radius > otherHalfSize.y - other.margin.y) {
			  collision = true;
			  if (center.y < other.center.y) {
				  speed.y = -speed.y;
				  center.y = other.side(Side.IT) + radius;
			  } else {
				  speed.y = -speed.y;
				  center.y = other.side(Side.IB) - radius;
			  }
		  }
	  } else {
	  	  boolean intersectionX = Math.abs(distanceVec.x) < otherHalfSize.x + radius;
	  	  boolean intersectionY = Math.abs(distanceVec.y) < otherHalfSize.y + radius;
	  	  boolean centerInX = Math.abs(distanceVec.x) < otherHalfSize.x;
	  	  boolean centerInY = Math.abs(distanceVec.y) < otherHalfSize.y;
	  	  if(intersectionX && intersectionY) {
				  collision = true;
				  if(centerInX == false) {
					  if (center.x < other.center.x) { 
						  speed.x = -speed.x;
						  center.x = other.side(Side.OL) - radius;
					  } else {
						  speed.x = -speed.x;
						  center.x = other.side(Side.OR) + radius;
					  }
				  }
				  if(centerInY == false) {
					  if (center.y < other.center.y) {
						  speed.y = -speed.y;
						  center.y = other.side(Side.OT) - radius;
					  } else {
						  speed.y = -speed.y;
						  center.y = other.side(Side.OB) + radius;
					  }
				  }
	  	  }
	  }
	 return collision; 
  }

  public void setLock(boolean lock){
    if(lock) {
      this.mass = Math.pow(1000000000, 1000000000);
    }
    this.locked = lock;
  }

  public void elastic(CircleBody C1, CircleBody C2){
        float M1 = (float)Math.sqrt(C1.radius);
        float M2 = (float)Math.sqrt(C2.radius);

        double V1 = C1.speed.getModule();
        double V2 = C2.speed.getModule();

        double D1 = C1.speed.getArg();
        double D2 = C2.speed.getArg();

        float X1 = C1.center.x;
        float X2 = C2.center.x;
        float Y1 = C1.center.y;
        float Y2 = C2.center.y;

        float Dx = X1 - X2;
        float Dy = Y1 - Y2;

        double Alpha = Math.atan2(Dy, Dx);

        float moduloSv = (C1.radius+C2.radius)-(float)Math.hypot(Dy, Dx);

        Vector S1 = new Vector(moduloSv,(float)Math.toDegrees(Alpha), true);
        Vector S2 = new Vector(moduloSv,(float)Math.toDegrees(Alpha) + 180, true);

        double Vi1x = V1*Math.cos(D1-Alpha);
        double Vi2x = V2*Math.cos(D2-Alpha);
        double Vi1y = V1*Math.sin(D1-Alpha);
        double Vi2y = V2*Math.sin(D2-Alpha);

        double Vf1x = (Vi1x*(M1-M2)+2*M2*Vi2x)/(M1+M2);
        double Vf2x = (Vi2x*(M2-M1)+2*M1*Vi1x)/(M1+M2);

        V1 = Math.hypot(Vf1x, Vi1y);
        V2 = Math.hypot(Vf2x, Vi2y);

        D1 = Math.toDegrees(Math.atan2(Vi1y, Vf1x)+Alpha);
        D2 = Math.toDegrees(Math.atan2(Vi2y, Vf2x)+Alpha);

        C1.speed.setModule((float)V1);
        C2.speed.setModule((float)V2);

        C1.speed.setArg((float)D1);
        C2.speed.setArg((float)D2);

        if(!C1.locked && !C1.parent.launching){
            C1.center.x = X1 + S1.x/2;
            C1.center.y = Y1 + S1.y/2;
        }
        if(!C2.locked && !C2.parent.launching){
            C2.center.x = X2 + S2.x/2;
            C2.center.y = Y2 + S2.y/2;
        }
    }

public Entity getParent() {
	return parent;
}

public Vector getCenter() {
	return center;
}

public Vector getLocalCenter() {
	return new Vector();
}


public void setCenter(Vector center) {
	this.center = center;
}

public Vector getSpeed() {
	return speed;
}

	public Vector getAcceleration() {
		return acceleration;
	}
	
public void setSpeed(Vector speed) {
	this.speed = speed;
}

public void debugDraw() {
	;
}


public void wallCollision() {
	Vector screenSize = Game.getInstance().getScreenSize();
	if(center.x + radius > screenSize.x) {
		if(!parent.launching)center.x = screenSize.x - radius;
		speed.x = -speed.x;
	} else if(center.x - radius < 0) {
		if(!parent.launching)center.x = radius;
		speed.x = -speed.x;
	}
	if(center.y + radius > screenSize.y) {
		if(!parent.launching)center.y = screenSize.y - radius;
		speed.y = -speed.y;
	} else if(center.y- radius < 0) {
		if(!parent.launching)center.y = radius;
		speed.y = -speed.y;
	}
}

}
