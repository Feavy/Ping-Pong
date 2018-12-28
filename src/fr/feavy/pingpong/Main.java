package fr.feavy.pingpong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.feavy.pingpong.physics.CollisionListener;
import fr.feavy.pingpong.physics.PhysicWorld;

public class Main extends JPanel {

	public static void main(String[] args) {
		Main panel = new Main();
		JFrame frame = new JFrame("Ping Pong");
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.setResizable(true);
		frame.setVisible(true);
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				panel.onKeyReleased(e.getKeyCode());
			}

			@Override
			public void keyPressed(KeyEvent e) {
				panel.onKeyPressed(e.getKeyCode());
			}
		});
	}

	private boolean initialized;
	private List<Ball> balls = new ArrayList<>();
	private Racket racket;
	private Dimension lastSize;
	
	private int score;
	
	private PhysicWorld physicWorld;
	
	private Random r = new Random();

	public Main() {
		score = 0;
		physicWorld = new PhysicWorld();
		initialized = false;
		setBackground(Color.WHITE);
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e) {
				if (initialized)
					onResize(getSize());
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	// TODO : ajouter une balle lorsque le score atteint un certain point

	private void init() {
		initialized = true;
		Dimension defaultSize = getSize();
		lastSize = defaultSize;
		Drawable.setRestriction(defaultSize);
		racket = new Racket((defaultSize.width - 100) / 2, defaultSize.height - 20, 100, 20);
		racket.setCollisionListener(new CollisionListener() {
			
			@Override
			public boolean onCollision(Drawable other) {
				score++;
				if(score == 2){
					addNewBall();
				}
				return true;
			}
		});
		
		addNewBall();
		physicWorld.addPhysicObject(racket);
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						repaint();
						Thread.sleep(1000l / 60l);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	private void addNewBall(){
		int x = r.nextInt(getSize().width);
		Ball b = new Ball(x, 0, 30, 30);
		balls.add(b);
		physicWorld.addPhysicObject(b);
		
	}
	
	private void onResize(Dimension size) {
		Drawable.setRestriction(size);
		float xTransform = (float) size.width / lastSize.width;
		float yTransform = (float) size.height / lastSize.height;
		for (Ball b : balls) {
			b.resize(xTransform, yTransform);
		}
		
		racket.resize(xTransform, yTransform);

		lastSize = size;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(new Color(33, 150, 243));
		if (!initialized)
			init();
		else {
			racket.move();
			Graphics2D g2d = (Graphics2D) g;
			racket.draw(g2d);
			for (Ball b : balls) {
				b.draw(g2d);
				b.move();
			}
			physicWorld.calculate();
			g2d.setFont(new Font("Arial", 20, 20));
			g2d.setColor(new Color(0, 150, 136));
			g2d.drawString("Score : "+score, 5, 20);
		}
	}

	public void onKeyPressed(int keyCode) {
		if(racket.getDirection() == 0)
			racket.setDirection(keyCode);
	}

	public void onKeyReleased(int keyCode) {
		if (keyCode == racket.getDirection())
			racket.setDirection(0);
	}

}
