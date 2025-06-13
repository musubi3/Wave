package com.J2;

import java.awt.Color;


import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.J2.Game.STATE;

public class Menu extends MouseAdapter {

	private Handler handler;
	private HUD hud;
	private AudioPlayer audio;
	private LoadGame load;
	private int[] button = {0,0,0,0,0,0};
	private Random r = new Random();

	public static Color menuColor = Color.lightGray;
	public static Color menuColor2 = Color.lightGray;
	public static Color menuColor3 = Color.lightGray;
	public static Color menuColor4 = Color.lightGray;
	public static Color menuColor5 = Color.lightGray;
	public static Color menuColor6 = Color.lightGray;
	public static Color about = Color.lightGray;

	public Menu(Handler handler, HUD hud, AudioPlayer audio, LoadGame load) {
		this.hud = hud;
		this.handler = handler;
		this.audio = audio;
		this.load = load;
	}
	
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		int mouse = e.getButton();

		if (Game.gameState == STATE.Menu) {
			//Single Player
			if (mouseOver(mx, my, 258, 200, 145, 70) && mouse == MouseEvent.BUTTON1) {
				Game.gameState = STATE.Game;
				if (Settings.darkMode) menuColor = Color.lightGray;
				else menuColor = Color.darkGray;
				handler.object.clear();
				handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
				for (int i = 0; i < 1; i++)
					if (Game.difficulty == 0) handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
					else handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH-35), r.nextInt(Game.HEIGHT-55), ID.HardEnemy, handler, hud));
				for (int i = 0; i < button.length; i++) button[i] = 0;
				audio.playMenuSound("app/res/button.wav", 0.84);
			}

			//Multiplayer
			if (mouseOver(mx, my, 423, 200, 145, 70) && mouse == MouseEvent.BUTTON1) {
				Game.multiplayer = true;
				Game.gameState = STATE.Game;
				if (Settings.darkMode) menuColor2 = Color.lightGray;
				else menuColor2 = Color.darkGray;
				handler.object.clear(); 
				handler.addObject(new Player(Game.WIDTH/2-64, Game.HEIGHT/2-32, ID.Player, handler));
				handler.addObject(new Player(Game.WIDTH/2+64, Game.HEIGHT/2-32, ID.Player2, handler));
				for (int i = 0; i < 2; i++)
					if (Game.difficulty == 0) handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
					else handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH-35), r.nextInt(Game.HEIGHT-55), ID.HardEnemy, handler, hud));
				for (int i = 0; i < button.length; i++) button[i] = 0;
				audio.playMenuSound("app/res/button.wav", 0.84);
			}

			//Settings
			if (mouseOver(mx, my, 258, 300, 310, 70) && mouse == MouseEvent.BUTTON1) {
				Settings.timer = 15;
				Game.gameState = STATE.Settings;
				if (Settings.darkMode) menuColor3 = Color.lightGray;
				else menuColor3 = Color.darkGray;
				for (int i = 0; i < button.length; i++) button[i] = 0;
				audio.playMenuSound("app/res/button.wav", 0.84);
			}

			//Quit
			if (mouseOver(mx, my, 258, 400, 310, 70) && mouse == MouseEvent.BUTTON1) {
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				System.exit(1);
			}
			
			//Load
			if (mouseOver(mx, my, 60, 470, 80, 35) && mouse == MouseEvent.BUTTON1) {
				load.timer = 15;
				Game.gameState = STATE.Load;
				if (Settings.darkMode) menuColor5 = Color.lightGray;
				else menuColor5 = Color.darkGray;
				for (int i = 0; i < button.length; i++) button[i] = 0;
				audio.playMenuSound("app/res/button.wav", 0.84);
			}
			
			//Shop
			if (mouseOver(mx, my, 690, 470, 50, 35) && mouse == MouseEvent.BUTTON1) {
				Game.gameState = STATE.Shop;
				Shop.timer = 15;
				if (Settings.darkMode) menuColor6 = Color.lightGray;
				else menuColor6 = Color.darkGray;
				for (int i = 0; i < button.length; i++) button[i] = 0;
				audio.playMenuSound("app/res/button.wav", 0.84);
			}
			
			//About
			if (mouseOver(mx, my, 11, 565, 44, 19) && mouse == MouseEvent.BUTTON1) {
				About.timer = 15;
				Game.gameState = STATE.About;
				if (Settings.darkMode) about = Color.lightGray;
				else about = Color.darkGray;
				audio.playMenuSound("app/res/button.wav", 0.84);
			}
			
			//???
			if (mouseOver(mx, my, 11, 565, 44, 19) && mouse == MouseEvent.BUTTON3 && Settings.music) {
				audio.stopMusic();
				handler.object.clear();
				if (Settings.darkMode) {Easter.col = 0; Easter.esc = 0;}
				else {Easter.col = 247; Easter.esc = 247;}
				Game.gameState = STATE.Egg;
				if (Settings.darkMode) about = Color.lightGray;
				else about = Color.darkGray;
				audio.playMenuSound("app/res/button.wav", 0.84);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (Game.gameState == STATE.Menu) {
			//Single Player
			if (mouseOver(mx, my, 258, 200, 145, 70)) {
				if (Settings.darkMode) menuColor = Color.green;
				else menuColor = new Color(0,170,0);
				button[0] = 3;
			} else {
				if (Settings.darkMode) menuColor = Color.lightGray;
				else menuColor = Color.darkGray;
				button[0] = 0;
			}

			//Multiplayer
			if (mouseOver(mx, my, 423, 200, 145, 70)) {
				if (Settings.darkMode) menuColor2 = Color.green;
				else menuColor2 = new Color(0,170,0);
				button[1] = 3;
			} else {
				if (Settings.darkMode) menuColor2 = Color.lightGray;
				else menuColor2 = Color.darkGray;
				button[1] = 0;
			}

			//Settings
			if (mouseOver(mx, my, 258, 300, 310, 70)) {
				if (Settings.darkMode) menuColor3 = Color.green;
				else menuColor3 = new Color(0,170,0);
				button[2] = 3;
			} else {
				if (Settings.darkMode) menuColor3 = Color.lightGray;
				else menuColor3 = Color.darkGray;
				button[2] = 0;
			}

			//Quit
			if (mouseOver(mx, my, 258, 400, 310, 70)) {
				if (Settings.darkMode) menuColor4 = Color.green;
				else menuColor4 = new Color(0,170,0);
				button[3] = 3;
			} else {
				if (Settings.darkMode) menuColor4 = Color.lightGray;
				else menuColor4 = Color.darkGray;
				button[3] = 0;
			}
			
			//Load
			if (mouseOver(mx, my, 60, 470, 80, 35)) {
				if (Settings.darkMode) menuColor5 = Color.green;
				else menuColor5 = new Color(0,170,0);
				button[4] = 2;
			} else {
				if (Settings.darkMode) menuColor5 = Color.lightGray;
				else menuColor5 = Color.darkGray;
				button[4] = 0;
			}
			
			//Shop
			if (mouseOver(mx, my, 690, 470, 50, 35)) {
				if (Settings.darkMode) menuColor6 = Color.green;
				else menuColor6 = new Color(0,170,0);
				button[5] = 2;
			} else {
				if (Settings.darkMode) menuColor6 = Color.lightGray;
				else menuColor6 = Color.darkGray;
				button[5] = 0;
			}
			
			//About
			if (mouseOver(mx, my, 11, 565, 44, 19)) {
				if (Settings.darkMode) about = Color.green;
				else about = new Color(0,170,0);
			} else {
				if (Settings.darkMode) about = Color.lightGray;
				else about = Color.darkGray;
			}
		}
	}

	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			}
			else return false;
		}
		else return false;
	}

	public void tick() {
	}

	public void render(Graphics g) {
		if (Game.gameState == STATE.Menu) {

			g.setFont(Game.titleFont);
			if (Settings.darkMode) g.setColor(Color.white);
			else g.setColor(Color.black);
			g.drawString("W A V E", 280, 170);

			g.setFont(Game.titleFont2);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawString("v1.0.0", 765, 583);
			g.setColor(about);
			g.drawString("MUSUBI", 12, 583);
			
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(258-button[0]/2, 200-button[0]/2, 145+button[0], 70+button[0]);
			g.setFont(Game.menuFont2);
			g.setColor(menuColor);
			g.drawRect(258-button[0]/2, 200-button[0]/2, 145+button[0], 70+button[0]);
			g.drawString("Single Player", 265, 240);

			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(424-button[1]/2, 200-button[1]/2, 145+button[1], 70+button[1]);
			g.setFont(Game.menuFont2);
			g.setColor(menuColor2);
			g.drawRect(424-button[1]/2, 200-button[1]/2, 145+button[1], 70+button[1]);
			g.drawString("Multiplayer", 440, 240);

			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(258-button[2]/2, 300-button[2]/2, 310+button[2], 70+button[2]);
			g.setFont(Game.menuFont);
			g.setColor(menuColor3);
			g.drawRect(258-button[2]/2, 300-button[2]/2, 310+button[2], 70+button[2]);
			g.drawString("Settings", 348, 343);

			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(258-button[3]/2, 400-button[3]/2, 310+button[3], 70+button[3]);
			g.setFont(Game.menuFont);
			g.setColor(menuColor4);
			g.drawRect(258-button[3]/2, 400-button[3]/2, 310+button[3], 70+button[3]);
			g.drawString("Quit", 381, 443);
			
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(60-button[4]/2, 470-button[4]/2, 80+button[4], 35+button[4]);
			g.setFont(Game.menuFont3);
			g.setColor(menuColor5);
			g.drawRect(60-button[4]/2, 470-button[4]/2, 80+button[4], 35+button[4]);
			g.drawString("Load Game", 64, 491);
			
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(690-button[5]/2, 470-button[5]/2, 50+button[5], 35+button[5]);
			g.setFont(Game.menuFont3);
			g.setColor(menuColor6);
			g.drawRect(690-button[5]/2, 470-button[5]/2, 50+button[5], 35+button[5]);
			g.drawString("Shop", 697, 491);
			
			if (Settings.darkMode) g.setColor(Color.white);
			else g.setColor(Color.black);
			g.setFont(Game.menuFont2);
			if (load.user > 0) g.drawRect(640, 280, 150, 150);
			for (int i = 1; i < load.saves.size(); i++) {
				if (load.user == i)g.drawString(load.saves.get(load.numUsers-i), 710-load.bounds, 250);
			}
		}
	}
}


