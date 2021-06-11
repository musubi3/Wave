package com.J2;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Coin extends GameObject {

	private Handler handler;
	private AudioPlayer audio;
	private Animation anim;

	public Coin(float x, float y, ID id, Handler handler, AudioPlayer audio) {
		super(x, y, id);
		this.handler = handler;
		this.audio = audio;
		
		anim = new Animation(4,Game.coin[0],Game.coin[1],Game.coin[2],Game.coin[3],Game.coin[4],Game.coin[5]); 
	}

	public void tick() {
		collision();
		anim.runAnimation();
	}

	public void render(Graphics g) {
		anim.drawAnimation(g, x, y, 0);
	}
	
	public void collision() {
		if (id == ID.Coin) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if(tempObject.getID() == ID.Player || tempObject.getID() == ID.Player2) {
					if (getBounds().intersects(tempObject.getBounds())) {
						Shop.coins += 100;
						EndScreen.newCoins += 100;
						audio.playMenuSound("res/coinSFX.wav", 0.8);
						handler.removeObject(this);
					}
				}
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 10, 14);
	}

}
