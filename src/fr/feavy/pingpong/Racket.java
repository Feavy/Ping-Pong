package fr.feavy.pingpong;

import java.awt.Graphics2D;

public class Racket extends Drawable{

	private int direction;
	
	public Racket(int x, int y, int width, int height){
		super(x, y, width, height, 16, 0);
		direction = 0;
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillRect(x(), y(), width(), height());
	}

	public void setDirection(int direction) {this.direction = direction;}
	public int getDirection() {return direction;}
	
	@Override
	public void move() {
		switch (direction) {
		case 37:
			moveX(-xSpeed);
			break;
		case 39:
			moveX(xSpeed);
			break;
		default:
		}
		
	}
	
}
