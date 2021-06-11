package com.J2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SmartEnemy extends GameObject {
	
	private Handler handler;
	private GameObject player;
	private ID target;
	private HUD hud;
	private Color color;

	public SmartEnemy(float x, float y, ID id, ID target, Color color, Handler handler, HUD hud) {
		super(x, y, id);
		
		this.hud = hud;
		this.target = target;
		this.handler = handler;
		this.color = color;
		
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getID() == target) player = handler.object.get(i);
		}
		
		velX = 5;
		velY = 5;
	}
	
	public ID getTarget() {
		return target;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		float diffX = x - player.getX() - 8;
		float diffY = y - player.getY() - 8;
		float distance = (float) Math.sqrt((x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY()));
		
		velX = (float) ((-1.0/distance) * diffX);
		velY = (float) ((-1.0/distance) * diffY);
		
		
		if (y <= 0 || y >= Game.HEIGHT-50) velY *= -1;
		if (x <= 0 || x >= Game.WIDTH-27) velX *= -1;
		
		if (hud.getLevel() == 10 || hud.getLevel2() == 10) handler.removeObject(this);
		
		handler.addObject(new Trail(x, y, ID.Trail, color, 16, 16, 0.02f,handler));
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval((int)x, (int)y, 16, 16);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}
	
}
