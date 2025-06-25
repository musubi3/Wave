package com.J2;

import java.awt.Graphics;
import java.awt.Rectangle;

public class MenuPlayer extends GameObject {

	private Handler handler;
	private Animation pyro_ball;
	private Animation rasengan;
	private LoadGame load;

	private int X = (int) ((Game.WIDTH - 17) * 0.794);
	private int Y = (int) (Game.HEIGHT * 0.36);

	public MenuPlayer(float x, float y, ID id, Handler handler, LoadGame load) {
		super(x, y, id);

		this.load = load;
		this.handler = handler;
		velX = 3;
		velY = 3;

		pyro_ball = new Animation(5, Game.pyro_ball);
		rasengan = new Animation(5, Game.rasengan);
	}

	public void tick() {
		x += velX;
		y += velY;

		if (y <= Y || y >= Y + 200 - 32)
			velY *= -(0.99 + (Math.random() * 0.02));
		if (x <= X || x >= X + 200 - 32)
			velX *= -(0.99 + (Math.random() * 0.02));

		if (Shop.skin == 2)
			rasengan.run_animation();
		if (Shop.skin == 3)
			pyro_ball.run_animation();

		if (Shop.skin == 0)
			handler.add_object(new Trail(x, y, ID.Trail, Player.playerColor, 32, 32, 0.1f, handler));
		else if (Shop.skin == 1)
			handler.add_object(new Trail2(x, y, ID.Trail, 0.1f, Game.skins[0], handler));
		else if (Shop.skin == 2)
			handler.add_object(new Trail2(x, y, ID.Trail, 0.1f, rasengan.get_current_frame(), handler));
		else if (Shop.skin == 3)
			handler.add_object(new Trail2(x, y, ID.Trail, 0.1f, pyro_ball.get_current_frame(), handler));

		if (load.user == 0)
			handler.remove_object(this);
	}

	public void render(Graphics g) {
		g.setColor(Player.playerColor);
		if (Shop.skin == 0)
			g.fillOval((int) x, (int) y, 32, 32);
		else if (Shop.skin == 1)
			g.drawImage(Game.skins[0], (int) x, (int) y, null);
		else if (Shop.skin == 2)
			rasengan.draw_animation(g, (int) x, (int) y, 0);
		else if (Shop.skin == 3)
			pyro_ball.draw_animation(g, (int) x, (int) y, 0);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

}
