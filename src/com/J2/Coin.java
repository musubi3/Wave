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

		anim = new Animation(4, Game.coins);
	}

	public void tick() {
		collision();
		anim.run_animation();
	}

	public void render(Graphics g) {
		anim.draw_animation(g, x, y, 0);
	}

	public void collision() {
		if (id == ID.Coin) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.getID() == ID.Player || tempObject.getID() == ID.Player2) {
					if (getBounds().intersects(tempObject.getBounds())) {
						Shop.coins += 100;
						EndScreen.new_coins += 100;
						audio.playMenuSound("app/res/coinSFX.wav", 0.8);
						handler.remove_object(this);
					}
				}
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 10, 14);
	}

}
