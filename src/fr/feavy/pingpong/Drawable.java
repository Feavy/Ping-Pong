package fr.feavy.pingpong;

import java.awt.Dimension;
import java.awt.Graphics2D;

import fr.feavy.pingpong.physics.CollisionListener;

public abstract class Drawable {

	private float x, y, width, height;
	protected float xSpeed, ySpeed;
	protected static Dimension restriction;
	private CollisionListener listener;

	public Drawable(float x, float y, float width, float height, float xSpeed, float ySpeed) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	public void setCollisionListener(CollisionListener listener) {
		this.listener = listener;
	}

	public void collide(Drawable other){
		if(listener != null)
			listener.onCollision(other);
	}
	
	public abstract void draw(Graphics2D g);

	public abstract void move();

	public void resize(float xTransform, float yTransform) {
		x *= xTransform;
		width *= xTransform;
		xSpeed *= xTransform;
		y *= yTransform;
		height *= yTransform;
		ySpeed *= yTransform;
	}

	protected void moveX(float xOffset) {
		x += xOffset;
		if (x < 0)
			x = 0;
		else if (x + width > restriction.width)
			x = restriction.width - width;
	}

	protected void moveY(float yOffset) {
		y += yOffset;
		if (y < 0)
			y = 0;
		else if (y + height > restriction.height)
			y = restriction.height - height;
	}

	public int x() {
		return (int) x;
	}

	public int y() {
		return (int) y;
	}

	public int width() {
		return (int) width;
	}

	public int height() {
		return (int) height;
	}

	public static void setRestriction(Dimension d) {
		restriction = d;
	}

}
