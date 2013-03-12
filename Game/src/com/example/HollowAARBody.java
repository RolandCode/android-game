package com.example;

import com.example.AARBody.Corner;

import android.R.integer;

public class HollowAARBody extends AARBody
{

	public Vector getAcceleration()
	{
		// TODO: Implement this method
		return acceleration;
	}

	
	protected Vector margin;
	public enum Side {IL, IT, IR, IB, OL, OT, OR, OB};
	public enum Corner {ILB, ILT, IRT, IRB, OLB, OLT, ORT, ORB}

	public HollowAARBody(PhysicsEngine engine, Entity parent, Vector center,
			Vector size, Vector marginSize) {
		super(engine, parent, center, size);
		this.margin = marginSize;
	}
	
	public float side(Side side) {
		Vector halfSize = size.div(2);
		switch(side) {
			case IL:
				return center.x - halfSize.x + margin.x;
			case IT:
				return center.y - halfSize.y + margin.y;
			case IR:
				return center.x + halfSize.x - margin.x;
			case IB:
				return center.y + halfSize.y - margin.y;
			case OL:
				return center.x - halfSize.x;
			case OT:
				return center.y - halfSize.y;
			case OR:
				return center.x + halfSize.x;
			case OB:
				return center.y + halfSize.y;
			default:
				return 0;
		}
	}
	
	public Vector[] insideCorners() {
		Vector[] insideCorners = new Vector[4];
		insideCorners[0] = corner(Corner.ILB);
		insideCorners[1] = corner(Corner.ILT);
		insideCorners[2] = corner(Corner.IRT);
		insideCorners[3] = corner(Corner.IRB);
		return insideCorners;
	}
	
	public Vector corner(Corner corner) {
		Vector halfSize = size.div(2);
		switch(corner) {
		case OLB:
			return new Vector(center.x - halfSize.x, center.y + halfSize.y);
		case ORB:
			return new Vector(center.x + halfSize.x, center.y + halfSize.y);
		case OLT:
			return new Vector(center.x - halfSize.x, center.y - halfSize.y);
		case ORT:
			return new Vector(center.x + halfSize.x, center.y - halfSize.y);
		case ILB:
			return new Vector(center.x - halfSize.x + margin.x, center.y + halfSize.y - margin.y);
		case IRB:
			return new Vector(center.x + halfSize.x - margin.x, center.y + halfSize.y - margin.y);
		case ILT:
			return new Vector(center.x - halfSize.x + margin.x, center.y - halfSize.y + margin.y);
		case IRT:
			return new Vector(center.x + halfSize.x - margin.x, center.y - halfSize.y + margin.y);
		default:
			return new Vector();
		}
		
	}
	
	public boolean collideImpl(CircleBody oth) {
		System.out.println("hollow imp");
		return oth.collideImpl(this);
	}

	public boolean collideImpl(AARBody oth) {
		return false;
	}

	public boolean collideImpl(HollowAARBody oth) {
		return false;
	}

	public void debugDraw() {
		Debug.drawRectangle(insideCorners());
		Debug.drawRectangle(corners());
	}

}
