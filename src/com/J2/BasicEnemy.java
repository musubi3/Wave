package com.J2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BasicEnemy extends GameObject {

	private Handler handler;
	private HUD hud;

	public BasicEnemy(float x, float y, ID id, Handler handler, HUD hud) {
		super(x, y, id);

		this.hud = hud;
		this.handler = handler;
		velX = 5 * (Math.random() < 0.5 ? -1 : 1);
		velY = 5 * (Math.random() < 0.5 ? -1 : 1);
	}

	public void tick() {
		x += velX;
		y += velY;

		if (y <= 0 || y >= Game.HEIGHT - 50) {
			velY *= -1;
			velX += Utils.rand_float(-1f, 1f);
		} 
		
		if (x <= 0 || x >= Game.WIDTH - 27) {
			velX *= -1;
			velY += Utils.rand_float(-1f, 1f);
		}

		if (hud.get_level(1) == 10 || hud.get_level(2) == 10)
			handler.remove_object(this);

		handler.add_object(new Trail(x, y, ID.Trail, Color.orange, 16, 16, 0.02f, handler));
	}

	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillOval((int) x, (int) y, 16, 16);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 16, 16);
	}

}
