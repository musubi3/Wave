package com.J2;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.J2.Game.STATE;

public class Shop extends MouseAdapter{


	private AudioPlayer audio;
	private LoadGame load;

	private Color[] colors = {Color.darkGray, Color.black, Color.black, Color.black, Color.black};

	public static int healthLevel = 0;
	public static int coins = 0;
	public static int timer = 25;
	public static int skin = 0;
	public static boolean[] unlocked = new boolean[3];

	private boolean upgrades = true;
	private boolean maxHealth = false;
	private boolean maxSpeed = false;

	public Shop(AudioPlayer audio, LoadGame load) {
		this.audio = audio;
		this.load = load;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		//Back from shop
		if (Game.gameState == STATE.Shop) {
			if (mouseOver(mx, my, 75, 55, 60, 20)) {
				Game.gameState = STATE.Menu;
				colors[0] = Color.darkGray;
				upgrades = true;
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				if (Settings.sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			if (!mouseOver(mx, my, 65, 50, 700, 450) && timer <= 0) {
				Game.gameState = STATE.Menu;
				upgrades = true;
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				if (Settings.sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			//Colors
			if (mouseOver(mx, my, 80, 180, 269, 70)) {
				upgrades = false;
				if (Settings.sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			//Upgrades
			if (mouseOver(mx, my, 80, 95, 269, 70)) {
				upgrades = true;
				if (Settings.sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			if (upgrades) {
				if (mouseOver(mx, my, 400, 120, 150, 100)) {
					if (coins >= 2000) audio.playMenuSound("app/res/button4.wav", 0.27);
					else if (coins < 2000) audio.playMenuSound("app/res/stop.wav", 1.0);
					if (healthLevel <= 5 && coins >= 2000) {healthLevel++; coins-=2000;}
					if (healthLevel == 5) {maxHealth = true; colors[1] = Color.red;}
					if (load.user > 0) load.save(load.saveFiles.get(load.user));
				}
				if (mouseOver(mx, my, 580, 120, 150, 100)) {
					if (coins >= 2000) audio.playMenuSound("app/res/button4.wav", 0.27);
					else if (coins < 2000) audio.playMenuSound("app/res/stop.wav", 1.0);
					if (Player.speed <= 8 && coins >= 2000) {Player.speed++; coins-=2000;}
					if (Player.speed == 8) {maxSpeed = true; colors[2] = Color.red;}
					if (load.user > 0) load.save(load.saveFiles.get(load.user));
				}
			}
			else {
				if (mouseOver(mx, my, 400, 120, 150, 100)) {
					skin = 0;
					if (load.user > 0) load.save(load.saveFiles.get(load.user));
					if (Settings.sound) audio.playMenuSound("app/res/button4.wav", 0.27);
				}
				if (mouseOver(mx, my, 580, 120, 150, 100)) {
					if (!unlocked[0] && coins >= 5000) {
						audio.playMenuSound("app/res/button4.wav", 0.27);
						skin = 1;
						unlocked[0] = true;
						coins -= 5000;
						if (load.user > 0) load.save(load.saveFiles.get(load.user));
					}
					else if (unlocked[0]) {
						skin = 1;
						if (load.user > 0) load.save(load.saveFiles.get(load.user));
						if (Settings.sound) audio.playMenuSound("app/res/button4.wav", 0.27);
					}
				} if (mouseOver(mx, my, 580, 120, 150, 100) && !unlocked[0] && coins < 5000) audio.playMenuSound("app/res/stop.wav", 0.8);
				
				if (mouseOver(mx, my, 400, 280, 150, 100)) {
					if (!unlocked[1] && coins >= 7500) {
						skin = 2;
						unlocked[1] = true;
						coins -= 7500;
						if (load.user > 0) load.save(load.saveFiles.get(load.user));
						if (Settings.sound) audio.playMenuSound("app/res/button4.wav", 0.27);
					}
					else if (unlocked[1]) {
						skin = 2;
						if (load.user > 0) load.save(load.saveFiles.get(load.user));
						if (Settings.sound) audio.playMenuSound("app/res/button4.wav", 0.27);
					}
				} if (mouseOver(mx, my, 400, 280, 150, 100) && !unlocked[0] && coins < 5000) audio.playMenuSound("app/res/stop.wav", 0.8);
				
				if (mouseOver(mx, my, 580, 280, 150, 100)) {
					if (!unlocked[2] && coins >= 10000) {
						skin = 3;
						unlocked[2] = true;
						coins -= 10000;
						if (load.user > 0) load.save(load.saveFiles.get(load.user));
						if (Settings.sound) audio.playMenuSound("app/res/button4.wav", 0.27);
					}
					else if (unlocked[2]) {
						skin = 3;
						if (load.user > 0) load.save(load.saveFiles.get(load.user));
						if (Settings.sound) audio.playMenuSound("app/res/button4.wav", 0.27);
					}
				} if (mouseOver(mx, my, 580, 280, 150, 100) && !unlocked[0] && coins < 5000) audio.playMenuSound("app/res/stop.wav", 0.8);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (Game.gameState == STATE.Shop) {
			if (mouseOver(mx, my, 75, 55, 60, 20)) {
				if (Settings.darkMode) colors[0] = new Color(0,190,0);
				else colors[0] = new Color(0,170,0);
			}else {
				colors[0] = Color.darkGray;
			}
			if (upgrades) {
				if (mouseOver(mx, my, 400, 120, 150, 100)) {
					if (Settings.darkMode && coins >= 2000 && !maxHealth) colors[1] = new Color(0,190,0);
					else if (coins >= 2000 && !maxHealth) colors[1] = new Color(0,170,0);
					else colors[1] = Color.red;
				}else {
					colors[1] = Color.darkGray;
				}
				if (mouseOver(mx, my, 580, 120, 150, 100)) {
					if (Settings.darkMode && coins >= 2000 && !maxSpeed) colors[2] = new Color(0,190,0);
					else if (coins >= 2000 && !maxSpeed)colors[2] = new Color(0,170,0);
					else colors[2] = Color.red;
				}else {
					colors[2] = Color.darkGray;
				}
			}
			else {
				if (mouseOver(mx, my, 400, 120, 150, 100)) {
					if (Settings.darkMode) colors[1] = new Color(0,190,0);
					else colors[1] = new Color(0,170,0);
				}else {
					colors[1] = Color.darkGray;
				}
				if (mouseOver(mx, my, 580, 120, 150, 100)) {
					if (Settings.darkMode && coins >= 5000 || unlocked[0]) colors[2] = new Color(0,190,0);
					else if (coins >= 5000 || unlocked[0])colors[2] = new Color(0,170,0);
					else colors[2] = Color.red;
				}else {
					colors[2] = Color.darkGray;
				}
				if (mouseOver(mx, my, 400, 280, 150, 100)) {
					if (Settings.darkMode && coins >= 7500 || unlocked[1]) colors[3] = new Color(0,190,0);
					else if (coins >= 7500 || unlocked[1])colors[3] = new Color(0,170,0);
					else colors[3] = Color.red;
				}else {
					colors[3] = Color.darkGray;
				}
				if (mouseOver(mx, my, 580, 280, 150, 100)) {
					if (Settings.darkMode && coins >= 10000 || unlocked[2]) colors[4] = new Color(0,190,0);
					else if (coins >= 10000 || unlocked[2])colors[4] = new Color(0,170,0);
					else colors[4] = Color.red;
				}else {
					colors[4] = Color.darkGray;
				}
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
		Game.clamp(timer, 0, 25);
		timer--;
	}

	public void render(Graphics g) {
		if (Game.gameState == STATE.Shop && upgrades) {

			//Version
			g.setFont(Game.titleFont2);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawString("v1.0.0", 765, 583);
			g.drawString("MUSUBI", 12, 583);

			//Window left
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(200,200,200));
			g.fillRect(65, 50, 700, 450);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(new Color(230,230,230));
			g.drawRect(65, 50, 700, 450);
			g.fillRect(65, 50, 700, 30);

			//Text
			g.setFont(Game.menuFont2);
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawString("Shop", 545, 70);

			//Back
			g.setColor(colors[0]);
			g.setFont(Game.menuFont2);
			g.drawRect(75, 55, 60, 20);
			g.drawString("Back", 82, 69);

			//Window right
			if (Settings.darkMode) g.setColor(new Color(50,50,50));
			else g.setColor(Color.lightGray);
			g.fillRect(65, 80, 300, 420);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(new Color(230,230,230));
			g.drawRect(65, 80, 300, 420);

			//Upgrades
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(230,230,230));
			g.fillRect(80, 95, 269, 70);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.drawRect(80, 95, 269, 70);
			g.setFont(Game.menuFont);
			if (!Settings.darkMode) g.setColor(new Color(150,150,150));
			g.drawString("Upgrades", 145, 137);

			//Colors
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(80, 180, 269, 70);
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 180, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Skins", 174, 220);

			g.drawImage(Game.coin[6],375,87,null);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.setFont(Game.menuFont4);
			g.drawString(""+coins, 395, 101);
			if (!maxHealth)g.drawString("2000 Coins",427,240);
			if (!maxSpeed)g.drawString("2000 Coins",610,240);

			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(400, 120, 150, 100);
			g.fillRect(580, 120, 150, 100);
			if (Settings.darkMode) g.setColor(new Color(0,210,0));
			else g.setColor(new Color(0,190,0));
			g.fillRect(470, 143, 10, 30);
			g.fillRect(460, 153, 30, 10);
			g.setColor(colors[1]);
			g.setFont(Game.menuFont4);
			g.drawRect(470, 143, 10, 30);
			g.drawRect(460, 153, 30, 10);
			g.drawRect(400, 120, 150, 100);
			g.drawString("Increase", 432, 190);
			g.drawString("Health", 440, 210);
			g.setFont(Game.titleFont3);
			if (!maxHealth) g.drawString("Health Lvl - " + healthLevel, 428, 135);
			else g.drawString("Max Level", 440, 135);
			g.setColor(new Color(160,160,255)); g.fillOval(610, 142, 32, 32);
			g.setColor(new Color(120,120,255)); g.fillOval(625, 142, 32, 32);
			g.setColor(new Color(80,80,255)); g.fillOval(640, 142, 32, 32);
			g.setColor(new Color(40,40,255)); g.fillOval(655, 142, 32, 32);
			g.setColor(Color.blue); g.fillOval(670, 142, 32, 32);
			g.setColor(colors[2]);
			g.setFont(Game.menuFont4);
			g.drawRect(580, 120, 150, 100);
			g.drawString("Increase", 613, 190);
			g.drawString("Speed", 626, 210);
			g.setFont(Game.titleFont3);
			int speedLevel = Player.speed - 3;
			if (!maxSpeed) g.drawString("Speed Lvl - " + speedLevel, 609, 135);
			else g.drawString("Max Level", 620, 135);
		}
		else if (Game.gameState == STATE.Shop && !upgrades) {
			//Version
			g.setFont(Game.titleFont2);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawString("v1.0.0", 765, 583);
			g.drawString("MUSUBI", 12, 583);

			//Window left
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(200,200,200));
			g.fillRect(65, 50, 700, 450);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(new Color(230,230,230));
			g.drawRect(65, 50, 700, 450);
			g.fillRect(65, 50, 700, 30);

			//Text
			g.setFont(Game.menuFont2);
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawString("Shop", 545, 70);

			//Back
			g.setColor(colors[0]);
			g.setFont(Game.menuFont2);
			g.drawRect(75, 55, 60, 20);
			g.drawString("Back", 82, 69 );

			//Window right
			if (Settings.darkMode) g.setColor(new Color(50,50,50));
			else g.setColor(Color.lightGray);
			g.fillRect(65, 80, 300, 420);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(new Color(230,230,230));
			g.drawRect(65, 80, 300, 420);

			//Upgrades
			if (!Settings.darkMode) g.setColor(Color.white);
			g.fillRect(80, 95, 269, 70);
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 95, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Upgrades", 145, 137);

			//Colors
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(230,230,230));
			g.fillRect(80, 180, 269, 70);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.drawRect(80, 180, 269, 70);
			g.setFont(Game.menuFont);
			if (!Settings.darkMode) g.setColor(new Color(150,150,150));
			g.drawString("Skins", 174, 220);

			g.drawImage(Game.coin[6],375,87,null);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.setFont(Game.menuFont4);
			g.drawString(""+coins, 395, 101);
			if (skin == 0) {g.setColor(new Color(0,190,0)); g.drawString("Equipped",435,240);}
			else g.drawString("Owned",447,240);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			
			if (skin == 1) {g.setColor(new Color(0,190,0)); g.drawString("Equipped",617,240);}
			else if (unlocked[0]) g.drawString("Owned",629,240);
			else g.drawString("5000 Coins",610,240);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			
			if (skin == 2) {g.setColor(new Color(0,190,0)); g.drawString("Equipped",435,400);}
			else if (unlocked[1]) g.drawString("Owned",447,400);
			else g.drawString("7500 Coins",427,400);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			
			if (skin == 3) {g.setColor(new Color(0,190,0)); g.drawString("Equipped",617,400);}
			else if (unlocked[2]) g.drawString("Owned",629,400);
			else g.drawString("10000 Coins",602,400);

			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(400, 120, 150, 100);
			g.fillRect(580, 120, 150, 100);
			if (Settings.darkMode) g.setColor(new Color(0,210,0));
			else g.setColor(new Color(0,190,0));
			g.setColor(new Color(160,160,255)); g.fillOval(425, 142, 32, 32);
			g.setColor(new Color(120,120,255)); g.fillOval(440, 142, 32, 32);
			g.setColor(new Color(80,80,255)); g.fillOval(455, 142, 32, 32);
			g.setColor(new Color(40,40,255)); g.fillOval(470, 142, 32, 32);
			g.setColor(Color.blue); g.fillOval(485, 142, 32, 32);
			g.setColor(colors[1]);
			g.setFont(Game.menuFont4);
			g.drawRect(400, 120, 150, 100);
			g.drawString("Basic Blue", 428, 200);
			g.drawImage(Game.skins[0], 637, 142, null);
			g.setColor(colors[2]);
			g.setFont(Game.menuFont4);
			g.drawRect(580, 120, 150, 100);
			g.drawString("Energy Ball", 600, 200);

			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(400, 280, 150, 100);
			g.fillRect(580, 280, 150, 100);
			if (Settings.darkMode) g.setColor(new Color(0,210,0));
			else g.setColor(new Color(0,190,0));
			g.setColor(colors[3]);
			g.setFont(Game.menuFont4);
			g.drawRect(400, 280, 150, 100);
			g.drawString("Rasengan", 432, 360);
			g.drawImage(Game.skins[1], 460, 302, null);
			g.setColor(colors[4]);
			g.setFont(Game.menuFont4);
			g.drawRect(580, 280, 150, 100);
			g.drawString("Pyro Ball", 612, 360);
			g.drawImage(Game.pyroBall[5], 637, 302, null);
		}
	}
}

