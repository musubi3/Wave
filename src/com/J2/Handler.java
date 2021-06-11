package com.J2;

import java.awt.Graphics;
import java.util.ArrayList;

public class Handler {

	ArrayList<GameObject> object = new ArrayList<GameObject>();
	
	public void tick() {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);

			tempObject.tick();
		}
	}

	public void render(Graphics g) {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}

	public void addObject(GameObject object) {
		this.object.add(object);
	}

	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

	public void clearEnemys() {
		float x = 0;
		float y = 0;
		float x2 = 0;
		float y2 = 0;
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			if (tempObject.getID() == ID.Player) {
				x = tempObject.getX();
				y = tempObject.getY();
			}  
			if (tempObject.getID() == ID.Player2) {
				x2 = tempObject.getX();
				y2 = tempObject.getY();
			}
		}
			object.clear();
				addObject(new Player((int)x, (int)y, ID.Player, this));
				if (Game.multiplayer) addObject(new Player((int)x2, (int)y2, ID.Player2, this));
		}
	}

