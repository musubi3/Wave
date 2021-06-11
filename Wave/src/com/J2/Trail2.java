package com.J2;

import java.awt.AlphaComposite;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Trail2 extends GameObject {

	private float alpha = 1;
	private Handler handler;
	private float life;
	private BufferedImage image;

	public Trail2(float x, float y, ID id, float life, BufferedImage image, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.life = life;
		this.image = image;
	}

	public void tick() {
		if (alpha > life) alpha -= (life - 0.0001f);
		else handler.removeObject(this);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		g.drawImage(image, (int)x, (int)y, null);
		g2d.setComposite(makeTransparent(1));
	}

	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return AlphaComposite.getInstance(type, alpha);
	}

	public Rectangle getBounds() {
		return null;
	}

}
