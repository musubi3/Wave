package com.J2;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Health extends GameObject {

	private Handler handler;
	private AudioPlayer audio;

	public Health(float x, float y, ID id, Handler handler, AudioPlayer audio) {
		super(x, y, id);
		this.handler = handler;
		this.audio = audio;
		
		velX = 0;
		velY = 3;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if (y >= Game.HEIGHT) handler.removeObject(this);
		handler.addObject(new Trail2(x, y, ID.Trail, 0.1f, Game.healthPack, handler));
		
		collision();
	}

	public void render(Graphics g) {
		g.drawImage(Game.healthPack, (int)x, (int)y, null);
	}
	
	public void collision() {
		if (id == ID.healthPack) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if(tempObject.getID() == ID.Player) {
					if (getBounds().intersects(tempObject.getBounds())) {
						handler.removeObject(this);
						HUD.HEALTH += 10;
						audio.playMenuSound("res/healthSFX.wav", 1.5);
					}
				}
				else if(tempObject.getID() == ID.Player2) {
					if (getBounds().intersects(tempObject.getBounds())) {
						handler.removeObject(this);
						HUD.HEALTH2 += 10;
						audio.playMenuSound("res/healthSFX.wav", 1.5);
					}
				}
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

}
