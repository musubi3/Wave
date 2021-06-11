package com.J2;

import java.awt.Graphics;
import java.awt.Rectangle;

public class MenuPlayer extends GameObject {
	
	private Handler handler;
	private Animation anim;

	public MenuPlayer(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		velX = 3;
		velY = 3;
		
		anim = new Animation(4, Game.pyroBall[0], Game.pyroBall[1], Game.pyroBall[2], Game.pyroBall[3], Game.pyroBall[4], Game.pyroBall[5], Game.pyroBall[6],
				 Game.pyroBall[7], Game.pyroBall[8], Game.pyroBall[9], Game.pyroBall[10], Game.pyroBall[11], Game.pyroBall[12]);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if (y <= 280 || y >= 400) velY *= -1;
		if (x <= 640 || x >= 760) velX *= -1;
		
		if (Shop.skin == 3) anim.runAnimation();

		if (Shop.skin == 0) handler.addObject(new Trail(x, y, ID.Trail, Player.playerColor, 32, 32, 0.1f,handler));
		else if (Shop.skin == 1)handler.addObject(new Trail2(x, y, ID.Trail, 0.1f, Game.skins[0], handler));
		else if (Shop.skin == 2)handler.addObject(new Trail2(x, y, ID.Trail, 0.1f, Game.skins[1], handler));
		else if (Shop.skin == 3)handler.addObject(new Trail2(x, y, ID.Trail, 0.1f, anim.getCurrentImage(), handler));
	}

	public void render(Graphics g) {
		g.setColor(Player.playerColor);
		if (Shop.skin == 0) g.fillOval((int)x, (int)y, 32, 32);
		else if (Shop.skin == 1) g.drawImage(Game.skins[0],(int)x,(int)y,null);
		else if (Shop.skin == 2) g.drawImage(Game.skins[1],(int)x,(int)y,null);
		else if (Shop.skin == 3) anim.drawAnimation(g, (int)x, (int)y, 0);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}

}
