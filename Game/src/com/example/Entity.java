package com.example;

public class Entity implements EntityInterface {
	protected Body body;
	public boolean launching = false;
	public Vector getCenter() {
		if(body != null)
			return body.getCenter();
		else
			return new Vector();
	}

	public void setCenter(Vector center) {
		if(body != null)
			body.setCenter(center);
	}

	public void update(int ms) {}
	
	public void onCollision(CollisionInfo info) {};

}
