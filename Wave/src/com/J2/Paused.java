package com.J2;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.J2.Game.STATE;

public class Paused extends MouseAdapter{
	private Handler handler;
	private HUD hud;
	private AudioPlayer audio;
	private LoadGame load;
	private Random r = new Random();

	private Color pauseColor = Color.black;
	private Color pauseColor2 = Color.black;
	private Color pauseColor3 = Color.black;
	private Color pauseColor4 = Color.darkGray;
	private Color pauseColor5 = Color.darkGray;
	private Color pauseColor6 = Color.darkGray;

	public Paused(Handler handler, HUD hud, AudioPlayer audio, LoadGame load) {
		this.handler = handler;
		this.hud = hud;
		this.audio = audio;
		this.load = load;
	}
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (Game.paused) {
			if (!mouseOver(mx, my, 100, 50, 600, 450)) {
				Game.paused = false;
				if (Settings.sound) audio.playMenuSound("res/button.wav", 0.84);
			}
		}
		//Play Again
		if (Game.paused && !Game.multiplayer) {
			if (mouseOver(mx, my, 430, 350, 200, 80)) {
				if (Settings.darkMode) pauseColor = Color.black;
				else pauseColor = Color.darkGray;
				pauseColor4 = Color.darkGray;
				handler.clearEnemys();
				Game.paused = false;
				hud.setLevel(1);
				hud.score(0);
				HUD.HEALTH = 100;
				Shop.coins -= EndScreen.newCoins;
				EndScreen.newCoins = 0;
				Game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
				handler.clearEnemys();
				for (int i = 0; i < 1; i++)
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
				if (Settings.sound) audio.playMenuSound("res/button.wav", 0.84);
			}
			//Main Menu
			else if (mouseOver(mx, my, 170, 350, 133, 80)) {
				if (Settings.darkMode) pauseColor2 = Color.black;
				else pauseColor2 = Color.darkGray;
				pauseColor5 = Color.darkGray;
				handler.object.clear();
				Game.paused = false;
				hud.setLevel(1);
				hud.score(0);
				HUD.HEALTH = 100;
				Shop.coins -= EndScreen.newCoins;
				EndScreen.newCoins = 0;
				Game.gameState = STATE.Menu;
				for (int i = 0; i < 20; i++) {
					handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH)-40, r.nextInt(Game.HEIGHT)-70, ID.MenuParticle, handler));
				}
				if (load.user > 0) handler.addObject(new MenuPlayer(640,355,ID.MenuPlayer,handler));
				if (Settings.sound) audio.playMenuSound("res/button4.wav", 0.27);
			}
			//Resume
			else if (mouseOver(mx, my, 334, 350, 133, 80)) {
				if (Settings.darkMode) pauseColor3 = Color.black;
				else pauseColor3 = Color.darkGray;
				pauseColor6 = Color.darkGray;
				Game.paused = false; 
				if (Settings.sound) audio.playMenuSound("res/button.wav", 0.84);
			}
		}
		else if (Game.paused && Game.multiplayer) {
			//Play Again
			if (mouseOver(mx, my, 430, 350, 200, 80)) {
				if (Settings.darkMode) pauseColor = Color.black;
				else pauseColor = Color.darkGray;
				pauseColor4 = Color.darkGray;
				handler.clearEnemys();
				Game.paused = false;
				hud.setLevel(1);
				hud.setLevel2(1);
				hud.score(0);
				hud.score2(0);
				HUD.HEALTH = 100;
				HUD.HEALTH2 = 100;
				Shop.coins -= EndScreen.newCoins;
				EndScreen.newCoins = 0;
				Game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH/2-64, Game.HEIGHT/2-32, ID.Player, handler));
				handler.addObject(new Player(Game.WIDTH/2+64, Game.HEIGHT/2-32, ID.Player2, handler));
				handler.clearEnemys();
				for (int i = 0; i < 2; i++)
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-30), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
				if (Settings.sound) audio.playMenuSound("res/button.wav", 0.84);
			}
			//Main Menu
			else if (mouseOver(mx, my, 170, 350, 133, 80)) {
				if (Settings.darkMode) pauseColor2 = Color.black;
				else pauseColor2 = Color.darkGray;
				pauseColor5 = Color.darkGray;
				handler.object.clear();
				Game.paused = false;
				Game.multiplayer = false;
				hud.setLevel(1);
				hud.setLevel2(1);
				hud.score(0);
				hud.score2(0);
				HUD.HEALTH = 100;
				HUD.HEALTH2 = 100;
				Shop.coins -= EndScreen.newCoins;
				EndScreen.newCoins = 0;
				Game.gameState = STATE.Menu;
				for (int i = 0; i < 20; i++) {
					handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH)-40, r.nextInt(Game.HEIGHT)-70, ID.MenuParticle, handler));
				}
				if (load.user > 0) handler.addObject(new MenuPlayer(640,355,ID.MenuPlayer,handler));
				if (Settings.sound) audio.playMenuSound("res/button4.wav", 0.27);
			}
			//Resume
			else if (mouseOver(mx, my, 334, 350, 133, 80)) {
				if (Settings.darkMode) pauseColor3 = Color.black;
				else pauseColor3 = Color.darkGray;
				pauseColor6 = Color.darkGray;
				Game.paused = false;
				if (Settings.sound) audio.playMenuSound("res/button.wav", 0.84);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (Game.paused) {
			//Restart
			if (mouseOver(mx, my, 497, 350, 133, 80)) {
				if (Settings.darkMode) pauseColor = Color.green;
				else pauseColor = new Color(0,170,0);
			} else {
				if (Settings.darkMode) pauseColor = Color.black;
				else pauseColor = Color.darkGray;
			}
			if (mouseOver(mx, my, 497, 350, 133, 80)) {
				if (Settings.darkMode) pauseColor4 = Color.green;
				else pauseColor4 = new Color(0,170,0);
			} else {
				pauseColor4 = Color.darkGray;
			}
			
			//Main Menu
			if (mouseOver(mx, my, 170, 350, 133, 80)) {
				if (Settings.darkMode) pauseColor2 = Color.green;
				else pauseColor2 = new Color(0,170,0);
			} else {
				if (Settings.darkMode) pauseColor2 = Color.black;
				else pauseColor2 = Color.darkGray;
			}
			if (mouseOver(mx, my, 170, 350, 133, 80)) {
				if (Settings.darkMode) pauseColor5 = Color.green;
				else pauseColor5 = new Color(0,170,0);
			} else {
				pauseColor5 = Color.darkGray;
			}

			//Resume
			if (mouseOver(mx, my, 334, 350, 133, 80)) {
				if (Settings.darkMode) pauseColor3 = Color.green;
				else pauseColor3 = new Color(0,170,0);
			} else {
				if (Settings.darkMode) pauseColor3 = Color.black;
				else pauseColor3 = Color.darkGray;
			}
			if (mouseOver(mx, my, 334, 350, 133, 80)) {
				if (Settings.darkMode) pauseColor6 = Color.green;
				else pauseColor6 = new Color(0,170,0);
			} else {
				pauseColor6 = Color.darkGray;
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

	public void render(Graphics g) {
		if (Game.multiplayer) {
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(100, 50, 600, 450);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawRect(100, 50, 600, 450);


			g.setFont(Game.titleFont);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawString("Paused", 295, 100);

			g.setFont(Game.menuFont);
			g.setColor(new Color(0, 140, 210));
			g.drawString("Player 1", 210, 150);
			g.setFont(Game.menuFont2);
			g.drawString("Score  -  " + hud.getScore(), 210, 200);
			g.drawString("Level  -  " + hud.getLevel(), 210, 230);

			g.setFont(Game.menuFont);
			g.setColor(new Color(0,170,0));
			g.drawString("Player 2", 470, 150);
			g.setFont(Game.menuFont2);
			g.drawString("Score  -  " + hud.getScore2(), 470, 200);
			g.drawString("Level  -  " + hud.getLevel2(), 470, 230); 
			
			g.drawImage(Game.coin[6],370,110,null);
			if (Settings.darkMode) g.setColor(Color.white);
			else g.setColor(Color.black);
			g.setFont(Game.menuFont4);
			g.drawString(""+EndScreen.newCoins, 390, 124);

			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(497, 350, 133, 80);
			g.setColor(pauseColor);
			g.drawRect(497, 350, 133, 80);
			g.setColor(pauseColor4);
			g.setFont(Game.menuFont2);
			g.drawString("Restart", 523, 396);

			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(170, 350, 133, 80);
			g.setColor(pauseColor2);
			g.drawRect(170, 350, 133, 80);
			g.setColor(pauseColor5);
			g.setFont(Game.menuFont2);
			g.drawString("Main Menu", 191, 396);

			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(334, 350, 133, 80);
			g.setColor(pauseColor3);
			g.drawRect(334, 350, 133, 80);
			g.setColor(pauseColor6);
			g.setFont(Game.menuFont2);
			g.drawString("Resume", 365, 396);
		}
		else {
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(100, 50, 600, 450);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawRect(100, 50, 600, 450);

			g.setFont(Game.titleFont);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawString("Paused", 295, 100);

			g.setFont(Game.menuFont);
			g.setColor(new Color(0, 140, 210));
			g.drawString("Score  -  " + hud.getScore(), 200, 200);
			g.drawString("Level  -  " + hud.getLevel(), 465, 200);
			
			g.drawImage(Game.coin[6],370,130,null);
			if (Settings.darkMode) g.setColor(Color.white);
			else g.setColor(Color.black);
			g.setFont(Game.menuFont4);
			g.drawString(""+EndScreen.newCoins, 390, 144);

			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(497, 350, 133, 80);
			g.setColor(pauseColor);
			g.drawRect(497, 350, 133, 80);
			g.setColor(pauseColor4);
			g.setFont(Game.menuFont2);
			g.drawString("Restart", 523, 396);

			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(170, 350, 133, 80);
			g.setColor(pauseColor2);
			g.drawRect(170, 350, 133, 80);
			g.setColor(pauseColor5);
			g.setFont(Game.menuFont2);
			g.drawString("Main Menu", 191, 396);

			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(334, 350, 133, 80);
			g.setColor(pauseColor3);
			g.drawRect(334, 350, 133, 80);
			g.setColor(pauseColor6);
			g.setFont(Game.menuFont2);
			g.drawString("Resume", 365, 396);
		}
	}
}
