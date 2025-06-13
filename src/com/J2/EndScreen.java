package com.J2;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.J2.Game.STATE;

public class EndScreen extends MouseAdapter {

	private Handler handler;
	private HUD hud;
	private AudioPlayer audio;
	private LoadGame load;
	private Random r = new Random();

	private Color endColor = Color.darkGray;
	private Color endColor2 = Color.darkGray;

	public boolean newHighScore = false;
	public static int newCoins = 0;

	public EndScreen(Handler handler, HUD hud, AudioPlayer audio, LoadGame load) {
		this.handler = handler;
		this.hud = hud;
		this.audio = audio;
		this.load = load;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		//Play Again
		if (Game.gameState == STATE.End) {
			if (mouseOver(mx, my, 430, 350, 200, 80)) {
				handler.clearEnemys();
				hud.isHighScore(hud.highScore);
				hud.setLevel(1);
				hud.score(0);
				newCoins = 0;
				Game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
				handler.clearEnemys();
				for (int i = 0; i < 1; i++)
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
				newHighScore = false;
				if (Settings.darkMode) endColor = Color.black;
				else endColor = Color.darkGray;
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				if (Settings.sound) audio.playMenuSound("app/res/button4.wav", 0.27);audio.playMenuSound("app/res/button.wav", 0.84);
			}
			//Main Menu
			if (mouseOver(mx, my, 170, 350, 200, 80)) {
				handler.object.clear();

				hud.setLevel(1);
				hud.score(0);
				newCoins = 0;
				Game.gameState = STATE.Menu;
				for (int i = 0; i < 20; i++) {
					handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH)-40, r.nextInt(Game.HEIGHT)-70, ID.MenuParticle, handler));
				}
				newHighScore = false;
				if (load.user > 0) {
					handler.addObject(new MenuPlayer(640,355,ID.MenuPlayer,handler)); 
					load.save(load.saveFiles.get(load.user));
				}
				if (Settings.darkMode) endColor2 = Color.black;
				else endColor2 = Color.darkGray;
				if (Settings.sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
		}
		else if (Game.gameState == STATE.End2) {
			//Play Again
			if (mouseOver(mx, my, 430, 350, 200, 80)) {
				handler.clearEnemys();
				hud.setLevel(1);
				hud.setLevel2(1);
				hud.score(0);
				hud.score2(0);
				newCoins = 0;
				Game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH/2-64, Game.HEIGHT/2-32, ID.Player, handler));
				handler.addObject(new Player(Game.WIDTH/2+64, Game.HEIGHT/2-32, ID.Player2, handler));
				handler.clearEnemys();
				for (int i = 0; i < 2; i++)
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-30), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
				newHighScore = false;
				if (Settings.darkMode) endColor = Color.black;
				else endColor = Color.darkGray;
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				if (Settings.sound) audio.playMenuSound("app/res/button.wav", 0.84);
			}
			//Main Menu
			else if (mouseOver(mx, my, 170, 350, 200, 80)) {
				handler.object.clear();
				Game.multiplayer = false;
				hud.setLevel(1);
				hud.setLevel2(1);
				hud.score(0);
				hud.score2(0);
				newCoins = 0;
				Game.gameState = STATE.Menu;
				for (int i = 0; i < 20; i++) {
					handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH)-40, r.nextInt(Game.HEIGHT)-70, ID.MenuParticle, handler));
				}
				newHighScore = false;
				if (load.user > 0) {
					handler.addObject(new MenuPlayer(640,355,ID.MenuPlayer,handler));
					load.save(load.saveFiles.get(load.user));
				}
				if (Settings.darkMode) endColor2 = Color.black;
				else endColor2 = Color.darkGray;
				if (Settings.sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (Game.gameState == STATE.End || Game.gameState == STATE.End2) {

			//Play Again
			if (mouseOver(mx, my, 430, 350, 200, 80)) {
				if (Settings.darkMode) endColor = Color.green;
				else endColor = new Color(0,170,0);
			} else {
				if (Settings.darkMode) endColor = Color.black;
				else endColor = Color.darkGray;
			}

			//Main Menu
			if (mouseOver(mx, my, 170, 350, 200, 80)) {
				if (Settings.darkMode) endColor2 = Color.green;
				else endColor2 = new Color(0,170,0);
			} else {
				if (Settings.darkMode) endColor2 = Color.black;
				else endColor2 = Color.darkGray;
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
		if (Game.gameState == STATE.End2) {

			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(100, 50, 600, 450);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawRect(100, 50, 600, 450);


			g.setFont(Game.titleFont);
			g.setColor(Color.red);
			g.drawString("Game Over", 230, 100);

			g.setFont(Game.menuFont);
			g.setColor(new Color(0, 140, 210));
			g.drawString("Player 1", 210, 210);
			g.setFont(Game.menuFont2);
			g.drawString("Score  -  " + hud.getScore(), 210, 260);
			g.drawString("Level  -  " + hud.getLevel(), 210, 290);

			g.setFont(Game.menuFont);
			g.setColor(new Color(0,170,0));
			g.drawString("Player 2", 470, 210);
			g.setFont(Game.menuFont2);
			g.drawString("Score  -  " + hud.getScore2(), 470, 260);
			g.drawString("Level  -  " + hud.getLevel2(), 470, 290); 

			g.setFont(Game.menuFont);
			if (!newHighScore) {
				if (Settings.darkMode) g.setColor(Color.white);
				else g.setColor(Color.black);
				g.drawString("HighScore  -  " + hud.highScore, 278, 150);
			} else {
				g.setColor(Color.magenta);
				g.drawString("New  HighScore  -  " + hud.highScore, 245, 150);
			}
			
			g.drawImage(Game.coin[6],360,162,null);
			if (Settings.darkMode) g.setColor(Color.white);
			else g.setColor(Color.black);
			g.setFont(Game.menuFont4);
			g.drawString(""+newCoins, 380, 177);
			
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(430, 350, 200, 80);
			g.setColor(endColor);
			g.drawRect(430, 350, 200, 80);
			g.setFont(Game.menuFont);
			g.drawString("Play Again", 455, 395);

			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(170, 350, 200, 80);
			g.setColor(endColor2);
			g.drawRect(170, 350, 200, 80);
			g.setFont(Game.menuFont);
			g.drawString("Main Menu", 200, 395);
		}
		else if (Game.gameState == STATE.End) {

			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(100, 50, 600, 450);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawRect(100, 50, 600, 450);

			g.setFont(Game.titleFont);
			g.setColor(Color.red);
			g.drawString("Game Over", 230, 100);

			g.setFont(Game.menuFont);
			g.setColor(new Color(0, 140, 210));
			g.drawString("Score  -  " + hud.getScore(), 185, 210);
			g.drawString("Level  -  " + hud.getLevel(), 445, 210);
			
			if (!newHighScore) {
				if (Settings.darkMode) g.setColor(Color.white);
				else g.setColor(Color.black);
				g.drawString("HighScore  -  " + hud.highScore, 278, 150);
			} else {
				g.setColor(Color.magenta);
				g.drawString("New  HighScore  -  " + hud.highScore, 245, 150);
			}
			
			g.drawImage(Game.coin[6],360,162,null);
			if (Settings.darkMode) g.setColor(Color.white);
			else g.setColor(Color.black);
			g.setFont(Game.menuFont4);
			g.drawString(""+newCoins, 380, 177);

			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(430, 350, 200, 80);
			g.setColor(endColor);
			g.drawRect(430, 350, 200, 80);
			g.setFont(Game.menuFont);
			g.drawString("Play Again", 455, 395);

			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(170, 350, 200, 80);
			g.setColor(endColor2);
			g.drawRect(170, 350, 200, 80);
			g.setFont(Game.menuFont);
			g.drawString("Main Menu", 200, 395);
		}
	}
}


