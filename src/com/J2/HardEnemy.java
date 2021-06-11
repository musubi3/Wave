package com.J2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class HardEnemy extends GameObject {

	private Handler handler;
	private HUD hud;
	
	private Random r = new Random();

	public HardEnemy(float x, float y, ID id, Handler handler, HUD hud) {
		super(x, y, id);

		this.hud = hud;
		this.handler = handler;
		velX = 5.5f;
		velY = 5.5f;
	}

	public void tick() {
		x += velX;
		y += velY;

//		if (y <= 0 || y >= Game.HEIGHT-60) { if(velY<0) velY=(r.nextInt(5)+3); else velY=-(r.nextInt(5)+3); }
//		if (x <= 0 || x >= Game.WIDTH-37) { if(velX<0) velX=(r.nextInt(5)+3); else velX=-(r.nextInt(5)+3); }
		
		if (y <= 0 || y >= Game.HEIGHT-50) velY *= -1.005;
		if (x <= 0 || x >= Game.WIDTH-27) velX *= -1.01;

		if (hud.getLevel() == 10 || hud.getLevel2() == 10) handler.removeObject(this);

		handler.addObject(new Trail(x, y, ID.Trail, new Color(50,r.nextInt(155)+100,50), 16, 16, 0.02f,handler));
	}

	public void render(Graphics g) {
		g.setColor(new Color(50,r.nextInt(155)+100,50));
		g.fillOval((int)x, (int)y, 16, 16);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

}
