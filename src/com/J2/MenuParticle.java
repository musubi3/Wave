package com.J2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class MenuParticle extends GameObject {

	private Handler handler;
	private Random r = new Random();
	private Color col;

	public MenuParticle(int x, int y, ID id, Handler handler) {
		super(x, y, id);

		this.handler = handler;

		velX = random_between(8, 2);
		velY = random_between(8, 2);

		col = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
	}

	private static int random_between(int max, int min) {
		Random rand = new Random();
		int sign = rand.nextBoolean() ? 1 : -1;
		return (rand.nextInt(max - min + 1) + min) * sign;
	}

	public void tick() {
		x += velX;
		y += velY;

		if (y <= 0 || y >= Game.HEIGHT - 50)
			velY *= -(0.99 + (Math.random() * 0.02));
		if (x <= 0 || x >= Game.WIDTH - 27)
			velX *= -(0.99 + (Math.random() * 0.02));

		handler.add_object(new Trail(x, y, ID.Trail, col, 16, 16, 0.03f, handler));
	}

	public void render(Graphics g) {
		g.setColor(col);
		g.fillOval((int) x, (int) y, 16, 16);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 16, 16);
	}

}
