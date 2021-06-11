package com.J2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class BossEnemy extends GameObject {
	
	private Handler handler;
	Random r = new Random();
	
	private int timer = 70;
	private int timer2 = 50;

	public BossEnemy(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		velX = 0;
		velY = 2;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if (timer <= 0) velY = 0;
		else timer--;
		
		if (timer <= 0) timer2--;
		if (timer2 <= 0) {
			if (velX == 0) velX = 2;
			if (velX > 0) velX += 0.005f;
			else if (velX < 0) velX -= 0.005f;
			
			velX = Game.clamp(velX, -10, 10);
			
			int spawn = r.nextInt(6);
			if (spawn == 0) handler.addObject(new BossEnemyBullet(x+48, y+48, ID.BasicEnemy, handler));
		}
		
//		if (y <= -101 || y >= Game.HEIGHT-132) velY *= -1;
		if (x <= 0 || x >= Game.WIDTH-108) velX *= -1;
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.red, 96, 96, 0.1f,handler));
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillOval((int)x, (int)y, 96, 96);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 96, 96);
	}
	
}
