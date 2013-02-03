package com.example;

import com.example.HollowAARBody.Side;

import android.graphics.Canvas;

public class AARBody extends AABB implements Body {
	
	public enum Side {L, T, R, B};
	public enum Corner {LB, LT, RB, RT};
	
	private Entity parent;
	
	protected Vector speed = new Vector();
	protected Vector acceleration = new Vector();
	
	public AARBody(PhysicsEngine engine, Entity parent, Vector center, Vector size) {
		super(center, size);
		this.parent = parent;
		engine.addBody(this);	
	}
	
	public AABB getAABB() {
		return this;
	}

	public boolean collide(Body other) {
		return other.collide(this);
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

	public void update(int ms) {
		this.speed = this.speed.add(this.acceleration);
		this.center = this.center.add(this.speed);
	}
	

	public boolean collideImpl(CircleBody oth) {
		return oth.collideImpl(this);
	}

	public boolean collideImpl(AARBody oth) {
		return false;
	}

	public boolean collideImpl(HollowAARBody oth) {
		return false;
	}

	public void setCenter(Vector center) {
		this.center = center;
	}

	public void setSpeed(Vector speed) {
		this.speed = speed;
	}
	
	public float side(Side side) {
		Vector halfSize = size.div(2);
		switch(side) {
			case L:
				return center.x - halfSize.x;
			case T:
				return center.y - halfSize.y;
			case R:
				return center.x + halfSize.x;
			case B:
				return center.y + halfSize.y;
			default:
				return 0;
		}
	}
	
	public Vector corner(Corner corner) {
		Vector halfSize = size.div(2);
		switch(corner) {
		case LB:
			return new Vector(center.x - halfSize.x, center.y + halfSize.y);
		case RB:
			return new Vector(center.x + halfSize.x, center.y + halfSize.y);
		case LT:
			return new Vector(center.x - halfSize.x, center.y - halfSize.y);
		case RT:
			return new Vector(center.x + halfSize.x, center.y - halfSize.y);
		default:
			return new Vector();
		}
		
	}
	
	public Vector[] corners() {
		Vector[] corners = new Vector[4];
		corners[0] = corner(Corner.LB);
		corners[1] = corner(Corner.LT);
		corners[2] = corner(Corner.RT);
		corners[3] = corner(Corner.RB);
		return corners;
	}
	
	public void debugDraw() {
		Debug.drawRectangle(corners());
	}

	public void wallCollision() {
		// TODO Auto-generated method stub
		
	}

	public Vector getSpeed() {
		return speed;
	}
	

}
