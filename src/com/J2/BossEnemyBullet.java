package com.J2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class BossEnemyBullet extends GameObject {
	
	private Handler handler;
	Random r = new Random();

	public BossEnemyBullet(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		if (Game.difficulty == 0)  {
			velX = r.nextInt(5 - -5) + -5;
			velY = 5;
		} else {
			velX = r.nextInt(6 - -6) + -6;
			velY = 6;
		}
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if (y >= Game.HEIGHT) handler.removeObject(this);
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.getHSBColor(1f * r.nextInt(40)/360, 1f, 1f), 16, 16, 0.05f,handler));
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillOval((int)x, (int)y, 16, 16);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}
	
}
