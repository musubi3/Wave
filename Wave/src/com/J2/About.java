package com.J2;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.J2.Game.STATE;

public class About extends MouseAdapter{

	private Desktop d;
	private AudioPlayer audio;
	
	public static int timer = 15;

	private Color[] colors = {Color.darkGray, new Color(170,210,170), new Color(170,210,170)};

	public About(AudioPlayer audio) {
		this.d = Desktop.getDesktop();
		this.audio = audio;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (Game.gameState == STATE.About) {
			if (mouseOver(mx, my, 176, 66, 58, 18)) {
				Game.gameState = STATE.Menu;
				colors[0] = Color.darkGray;
				audio.playMenuSound("res/button4.wav", 0.27);
			}
			
			if (!mouseOver(mx, my, 165, 60, 500, 450) && timer <= 0) {
				Game.gameState = STATE.Menu;
				audio.playMenuSound("res/button4.wav", 0.27);
			}
			
			if (mouseOver(mx, my, 452, 212, 184, 21)) {
				audio.playMenuSound("res/button4.wav", 0.27);
				try {d.browse(new URI("mailto:ju3tinlee@gmail.com"));} 
				catch (IOException e1) {e1.printStackTrace();} 
				catch (URISyntaxException e1) {e1.printStackTrace();}
				if (Settings.darkMode) colors[1] = new Color(170,170,170);
				else colors[1] = new Color(95,95,95);
			}

			if (mouseOver(mx, my, 537, 237, 99, 21)) {
				audio.playMenuSound("res/button4.wav", 0.27);
				try {d.browse(new URI("https://instagram.com/ju3tin.lee/"));} 
				catch (IOException e1) {e1.printStackTrace();} 
				catch (URISyntaxException e1) {e1.printStackTrace();}
				if (Settings.darkMode) colors[2] = new Color(170,170,170);
				else colors[2] = new Color(95,95,95);
			}
		}

	}

	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (mouseOver(mx, my, 176, 66, 58, 18)) {
			if (Settings.darkMode) colors[0] = new Color(0,190,0);
			else colors[0] = new Color(0,170,0);
		} else colors[0] = Color.darkGray;
		
		if (mouseOver(mx, my, 452, 212, 184, 21)) {
			if (Settings.darkMode) colors[1] = Color.green;
			else colors[1] = new Color(0,170,0);
		} else {
			if (Settings.darkMode) colors[1] = new Color(170,210,170);
			else colors[1] = new Color(95,135,95);
		}

		if (mouseOver(mx, my, 537, 237, 99, 21)) {
			if (Settings.darkMode) colors[2] = Color.green;
			else colors[2] = new Color(0,170,0);
		} else {
			if (Settings.darkMode) colors[2] = new Color(170,210,170);
			else colors[2] = new Color(95,135,95);
		}
	}

	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) return true;
			else return false;
		} else return false;
	}

	public void tick() {
		Game.clamp(timer, 0, 15);
		timer--;
	}

	public void render(Graphics g) {
		//Version
		g.setFont(Game.titleFont2);
		if (Settings.darkMode) g.setColor(Color.lightGray);
		else g.setColor(Color.darkGray);
		g.drawString("v1.0.0", 765, 583);
		g.setColor(Color.lightGray);
		g.drawString("JL33", 12, 583);

		//Window
		if (Settings.darkMode) g.setColor(Color.darkGray);
		else g.setColor(new Color(200,200,200));
		g.fillRect(165, 60, 500, 450);
		if (Settings.darkMode) g.setColor(Color.lightGray);
		else g.setColor(new Color(230,230,230));
		g.drawRect(165, 60, 500, 450);
		g.fillRect(165, 60, 500, 30);

		//Text
		g.setFont(Game.menuFont2);
		if (Settings.darkMode) g.setColor(Color.darkGray);
		else g.setColor(Color.darkGray);
		g.drawString("About", 400, 80);

		//Back
		g.setColor(colors[0]);
		g.setFont(Game.menuFont2);
		g.drawRect(176, 66, 58, 18);
		g.drawString("Back", 182, 79);

		//Text
		g.setFont(Game.menuFont);
		if (Settings.darkMode) g.setColor(Color.lightGray);
		else g.setColor(Color.darkGray);
		g.drawString("Version", 182, 140);
		g.drawString("1.0.0",560, 140);
		g.drawString("Developer", 182, 200);
		g.drawString("Justin Lee",485, 200);
		g.setFont(Game.titleFont2);
		if (Settings.darkMode)g.setColor(new Color(170,170,170));
		else g.setColor(new Color(95,95,95));
		g.drawString("Email", 182, 230);
		g.setColor(colors[1]);
		g.drawString("ju3tinlee@gmail.com", 453, 230);
		if (Settings.darkMode)g.setColor(new Color(170,170,170));
		else g.setColor(new Color(95,95,95));
		g.drawString("Instagram", 182, 255);
		g.setColor(colors[2]);
		g.drawString("@ju3tin.lee", 538, 255);
	}
}
