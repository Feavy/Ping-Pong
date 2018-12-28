package fr.feavy.pingpong;

import java.awt.Graphics2D;

import fr.feavy.pingpong.physics.CollisionListener;

public class Ball extends Drawable{
	
	public Ball(int x, int y, int width, int height) {
		super(x, y, width, height, 0f, 0.4f);
		setCollisionListener(new CollisionListener() {
			
			@Override
			public boolean onCollision(Drawable other) {
				ySpeed*=-1;
				if(other instanceof Racket){
					float racketMid = other.x() + other.width()/2;
					float ballMid = x() + width/2;
					float xDiff = (ballMid-racketMid)/10;
					xSpeed = xDiff;
				}
				return true;
			}
		});
	}

	//TODO : augmenter la ySpeed au fur et à mesure que la balle tombe
	
	@Override
	public void draw(Graphics2D g) {
		g.fillOval((int)x(), (int)y(), (int)width(), (int)height());	
	}

	@Override
	public void move() {
		moveX(xSpeed);
		if(x() == 0 || x() == restriction.width-width())
			xSpeed*=-1;
		moveY(ySpeed);
		if(y() == restriction.height-height())
			System.exit(0);
		if(ySpeed > -0.1 && ySpeed < 0)
			ySpeed*=-1;
		ySpeed *= (ySpeed >= 0) ? 1.04 : 0.96;
	}

	public void bounce() {
		
	}
	
}
