package com.J2;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Player extends GameObject {

	Random r = new Random();
	private Handler handler;
	public static boolean dead = false;
	public static boolean dead2 = false;
	public static int speed = 3;
	public static Color playerColor = Color.blue;
	
	private Animation anim;
	private Animation anim2;

	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		anim = new Animation(4, Game.pyroBall[0], Game.pyroBall[1], Game.pyroBall[2], Game.pyroBall[3], Game.pyroBall[4], Game.pyroBall[5], Game.pyroBall[6],
				 Game.pyroBall[7], Game.pyroBall[8], Game.pyroBall[9], Game.pyroBall[10], Game.pyroBall[11], Game.pyroBall[12]);
		anim2 = new Animation(4, Game.greenBall[0], Game.greenBall[1], Game.greenBall[2], Game.greenBall[3], Game.greenBall[4], Game.greenBall[5], Game.greenBall[6],
				 Game.greenBall[7], Game.greenBall[8], Game.greenBall[9], Game.greenBall[10], Game.greenBall[11], Game.greenBall[12]);
	}

	public void tick() {
		x += velX;
		y += velY;
		x = Game.clamp((int)x, 0, Game.WIDTH-44);
		y = Game.clamp((int)y, 0, Game.HEIGHT-69);
		
		if (Shop.skin == 3) {anim.runAnimation(); anim2.runAnimation();}
		
		if (id == ID.Player2 && Shop.skin == 0) handler.addObject(new Trail(x, y, ID.Trail, Color.green, 32, 32, 0.1f,handler));
		else if (id == ID.Player2 && Shop.skin != 3) handler.addObject(new Trail2(x, y, ID.Trail, 0.1f, Game.skins[2], handler));
		else if (id == ID.Player2 && Shop.skin == 3)handler.addObject(new Trail2(x, y, ID.Trail, 0.1f, anim2.getCurrentImage(), handler));
		if (id == ID.Player && Shop.skin == 0) handler.addObject(new Trail(x, y, ID.Trail, playerColor, 32, 32, 0.1f,handler));
		else if (id == ID.Player && Shop.skin == 1)handler.addObject(new Trail2(x, y, ID.Trail, 0.1f, Game.skins[0], handler));
		else if (id == ID.Player && Shop.skin == 2)handler.addObject(new Trail2(x, y, ID.Trail, 0.1f, Game.skins[1], handler));
		else if (id == ID.Player && Shop.skin == 3)handler.addObject(new Trail2(x, y, ID.Trail, 0.1f, anim.getCurrentImage(), handler));
		
		collision();
		if (HUD.HEALTH <= 0) {
			for(int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.getID() == ID.Player) {
					handler.object.remove(i);
					dead = true;
				}
			}
		}
		if (HUD.HEALTH2 <= 0) {
			for(int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.getID() == ID.Player2) {
					handler.object.remove(i);
					dead2 = true;
				}
			}
		}
	}

	public void collision() {
		if (id == ID.Player)
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);

				if(tempObject.getID() == ID.BasicEnemy || tempObject.getID() == ID.FastEnemy 
						|| tempObject.getID() == ID.SmartEnemy || tempObject.getID() == ID.HardEnemy) {
					if (getBounds().intersects(tempObject.getBounds())) {
						if (Shop.healthLevel == 0) HUD.HEALTH -= 2;
						if (Shop.healthLevel == 1) HUD.HEALTH -= 1.8;
						if (Shop.healthLevel == 2) HUD.HEALTH -= 1.6;
						if (Shop.healthLevel == 3) HUD.HEALTH -= 1.4;
						if (Shop.healthLevel == 4) HUD.HEALTH -= 1.2;
						if (Shop.healthLevel == 5) HUD.HEALTH -= 1;
					}
				}
				else if (tempObject.getID() == ID.BossEnemy) {
					if (getBounds().intersects(tempObject.getBounds()))
						HUD.HEALTH -= 1000;
				}
				else if (tempObject.getID() == ID.Player2) {
					if (getBounds().intersects(tempObject.getBounds()))
						HUD.HEALTH += 0.05f;
				}
			}
		if (id == ID.Player2)
			for (int j = 0; j < handler.object.size(); j++) {
				GameObject tempObject2 = handler.object.get(j);

				if(tempObject2.getID() == ID.BasicEnemy || tempObject2.getID() == ID.FastEnemy 
						|| tempObject2.getID() == ID.SmartEnemy || tempObject2.getID() == ID.HardEnemy) {
					if (getBounds().intersects(tempObject2.getBounds())) {
						if (Shop.healthLevel == 0) HUD.HEALTH2 -= 2;
						if (Shop.healthLevel == 1) HUD.HEALTH2 -= 1.8;
						if (Shop.healthLevel == 2) HUD.HEALTH2 -= 1.6;
						if (Shop.healthLevel == 3) HUD.HEALTH2 -= 1.4;
						if (Shop.healthLevel == 4) HUD.HEALTH2 -= 1.2;
						if (Shop.healthLevel == 5) HUD.HEALTH2 -= 1;
					}
				}
				else if (tempObject2.getID() == ID.BossEnemy) {
					if (getBounds().intersects(tempObject2.getBounds()))
						HUD.HEALTH2 -= 1000; 
				}
				else if (tempObject2.getID() == ID.Player) {
					if (getBounds().intersects(tempObject2.getBounds()))
						HUD.HEALTH2 += 0.05f;
				}
			}
	}

	public void render(Graphics g) {

		if(id == ID.Player) { 
			g.setColor(playerColor);
			if (Shop.skin == 0) g.fillOval((int)x, (int)y, 32, 32);
			else if (Shop.skin == 1) g.drawImage(Game.skins[0],(int)x,(int)y,null);
			else if (Shop.skin == 2) g.drawImage(Game.skins[1],(int)x,(int)y,null);
			else if (Shop.skin == 3) anim.drawAnimation(g, (int)x, (int)y, 0);
		}
		if (id == ID.Player2) { 
			g.setColor(Color.green);
			if (Shop.skin == 0) g.fillOval((int)x, (int)y, 32, 32);
			else if (Shop.skin != 3) g.drawImage(Game.skins[2], (int)x, (int)y, null);
			else if (Shop.skin == 3) anim2.drawAnimation(g, (int)x, (int)y, 0);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
}
