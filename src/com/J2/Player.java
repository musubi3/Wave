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

	private Animation pyro_ball;
	private Animation green_ball;
	private Animation rasengan;

	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		pyro_ball = new Animation(5, Game.pyro_ball);
		green_ball = new Animation(5, Game.green_ball);
		rasengan = new Animation(5, Game.rasengan);
	}

	public void tick() {
		x += velX;
		y += velY;
		x = Utils.clamp((int) x, 0, Game.WIDTH - 44);
		y = Utils.clamp((int) y, 0, Game.HEIGHT - 69);

		if (Shop.skin == 2)
			rasengan.run_animation();
		else if (Shop.skin == 3)
			pyro_ball.run_animation();

		if (Shop.skin == 2 || Shop.skin == 3)
			green_ball.run_animation(); 

		if (id == ID.Player2) {
			if (Shop.skin == 0)
				handler.add_object(new Trail(x, y, ID.Trail, new Color(0, 180, 0), 32, 32, 0.1f, handler));
			else if (Shop.skin == 1)
				handler.add_object(new Trail2(x, y, ID.Trail, 0.1f, Game.skins[1], handler));
			else if (Shop.skin == 2 || Shop.skin == 3)
				handler.add_object(new Trail2(x, y, ID.Trail, 0.1f, green_ball.get_current_frame(), handler));
		}

		else if (id == ID.Player) {
			if (Shop.skin == 0)
				handler.add_object(new Trail(x, y, ID.Trail, playerColor, 32, 32, 0.1f, handler));
			else if (Shop.skin == 1)
				handler.add_object(new Trail2(x, y, ID.Trail, 0.1f, Game.skins[0], handler));
			else if (Shop.skin == 2)
				handler.add_object(new Trail2(x, y, ID.Trail, 0.1f, rasengan.get_current_frame(), handler));
			else if (Shop.skin == 3)
				handler.add_object(new Trail2(x, y, ID.Trail, 0.1f, pyro_ball.get_current_frame(), handler));

		}
		collision();
		if (HUD.HEALTH <= 0) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.getID() == ID.Player) {
					handler.object.remove(i);
					dead = true;
				}
			}
		}
		if (HUD.HEALTH2 <= 0) {
			for (int i = 0; i < handler.object.size(); i++) {
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

				if (tempObject.getID() == ID.BasicEnemy || tempObject.getID() == ID.FastEnemy
						|| tempObject.getID() == ID.SmartEnemy || tempObject.getID() == ID.HardEnemy) {
					if (getBounds().intersects(tempObject.getBounds())) {
						if (Shop.health_level == 0)
							HUD.HEALTH -= 2;
						if (Shop.health_level == 1)
							HUD.HEALTH -= 1.8;
						if (Shop.health_level == 2)
							HUD.HEALTH -= 1.6;
						if (Shop.health_level == 3)
							HUD.HEALTH -= 1.4;
						if (Shop.health_level == 4)
							HUD.HEALTH -= 1.2;
						if (Shop.health_level == 5)
							HUD.HEALTH -= 1;
					}
				} else if (tempObject.getID() == ID.BossEnemy) {
					if (getBounds().intersects(tempObject.getBounds()))
						HUD.HEALTH -= 1000;
				} else if (tempObject.getID() == ID.Player2) {
					if (getBounds().intersects(tempObject.getBounds()))
						HUD.HEALTH += 0.05f;
				}
			}
		if (id == ID.Player2)
			for (int j = 0; j < handler.object.size(); j++) {
				GameObject tempObject2 = handler.object.get(j);

				if (tempObject2.getID() == ID.BasicEnemy || tempObject2.getID() == ID.FastEnemy
						|| tempObject2.getID() == ID.SmartEnemy || tempObject2.getID() == ID.HardEnemy) {
					if (getBounds().intersects(tempObject2.getBounds())) {
						if (Shop.health_level == 0)
							HUD.HEALTH2 -= 2;
						if (Shop.health_level == 1)
							HUD.HEALTH2 -= 1.8;
						if (Shop.health_level == 2)
							HUD.HEALTH2 -= 1.6;
						if (Shop.health_level == 3)
							HUD.HEALTH2 -= 1.4;
						if (Shop.health_level == 4)
							HUD.HEALTH2 -= 1.2;
						if (Shop.health_level == 5)
							HUD.HEALTH2 -= 1;
					}
				} else if (tempObject2.getID() == ID.BossEnemy) {
					if (getBounds().intersects(tempObject2.getBounds()))
						HUD.HEALTH2 -= 1000;
				} else if (tempObject2.getID() == ID.Player) {
					if (getBounds().intersects(tempObject2.getBounds()))
						HUD.HEALTH2 += 0.05f;
				}
			}
	}

	public void render(Graphics g) {

		if (id == ID.Player) {
			g.setColor(playerColor);
			if (Shop.skin == 0)
				g.fillOval((int) x, (int) y, 32, 32);
			else if (Shop.skin == 1)
				g.drawImage(Game.skins[0], (int) x, (int) y, null);
			else if (Shop.skin == 2)
				rasengan.draw_animation(g, (int) x, (int) y, 0);
			else if (Shop.skin == 3)
				pyro_ball.draw_animation(g, (int) x, (int) y, 0);
		}
		if (id == ID.Player2) {
			g.setColor(new Color(0, 180, 0));
			if (Shop.skin == 0)
				g.fillOval((int) x, (int) y, 32, 32);
			else if (Shop.skin == 1)
				g.drawImage(Game.skins[1], (int) x, (int) y, null);
			else if (Shop.skin == 2 || Shop.skin == 3)
				green_ball.draw_animation(g, (int) x, (int) y, 0);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}
}
