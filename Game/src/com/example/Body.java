package com.example;
import android.graphics.*;
import com.example.AABB;

public interface Body {

  public AABB getAABB();
	
  public boolean collide(Body other);

  public Entity getParent();
  
  public Vector getCenter();
  public void setCenter(Vector center);
  
  public Vector getLocalCenter();
  
  public Vector getSpeed();
  public void setSpeed(Vector speed);
  
  public void update(int ms);
  public void debugDraw();
  public void wallCollision();
  
  public boolean collideImpl(CircleBody oth);
  public boolean collideImpl(AARBody oth);
  public boolean collideImpl(HollowAARBody oth);
  

}
