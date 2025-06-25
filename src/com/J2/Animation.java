package com.J2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

	private int speed;
	private BufferedImage[] frames;
	private int index = 0;
	private int count = 0;

	private BufferedImage current_frame;

	public Animation(int speed, BufferedImage[] frames) {
		this.speed = speed;
		this.frames = frames;
	}

	public void run_animation() {
		index++;
		if (index > speed) {
			index = 0;
			next_frame();
		}
	}

	private void next_frame() {
		if (count == frames.length)
			count = 0;
		current_frame = frames[count];
		count++;
	}

	public void draw_animation(Graphics g, double x, double y, int offset) {
		g.drawImage(current_frame, (int) x - offset, (int) y, null);
	}

	public BufferedImage get_current_frame() {
		return current_frame;
	}

	public void set_count(int count) {
		this.count = count;
	}

	public int get_count() {
		return count;
	}

	public int get_speed() {
		return speed;
	}

	public void set_speed(int speed) {
		this.speed = speed;
	}

}