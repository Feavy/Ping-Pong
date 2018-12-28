package fr.feavy.pingpong.physics;

import java.util.LinkedList;
import java.util.List;

import fr.feavy.pingpong.Drawable;

public class PhysicWorld {

	private List<Drawable> objects;

	public PhysicWorld() {
		objects = new LinkedList<>();
	}

	public void addPhysicObject(Drawable object) {
		objects.add(object);
	}

	public void calculate() {
		for (int i = 0; i < objects.size() - 1; i++) {
			for (int j = i + 1; j < objects.size(); j++) {
				Drawable obj1 = objects.get(i);
				Drawable obj2 = objects.get(j);

				if (!((obj2.x() >= obj1.x() + obj1.width()) // trop à droite
						|| (obj2.x() + obj2.width() <= obj1.x()) // trop à gauche
						|| (obj2.y() >= obj1.y() + obj1.height()) // trop en bas
						|| (obj2.y() + obj2.height() <= obj1.y()))) // trop en haut
					{
						obj1.collide(obj2);
						obj2.collide(obj1);
					}

			}
		}
		// TODO ...
	}

}
