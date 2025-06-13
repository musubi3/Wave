package com.J2;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.J2.Game.STATE;

public class Easter extends KeyAdapter{
	
	private Handler handler;
	private AudioPlayer audio;
	Random r = new Random();
	private BufferedImage sparkles;
	private int timer = 670;
	public static double col = 0;
	public static int esc = 0;
	private int load = 375;
	private float spark = 0.0f;
	private boolean song = true;
	private boolean replay = false;
	private boolean effects = false;
	
	public Easter(AudioPlayer audio, Handler handler) {
		this.audio = audio;
		this.handler = handler;
		ImageLoader loader = new ImageLoader();
		sparkles = loader.loadImage("app/res/sparkles.png");
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ESCAPE && timer == -1) {
			audio.stopMusic();
			Game.gameState = STATE.Menu;
			if (Settings.songs[0].equals("Default")) audio.playGameSound("app/res/music.wav", 1.122);
			else if (Settings.songs[0].equals("Sketchers")) audio.playGameSound("app/res/sketchers.wav", 1.122);
			else if (Settings.songs[0].equals("Act My Age")) audio.playGameSound("app/res/1D.wav", 1.122);
			timer = 670;
			if (Settings.darkMode) {col = 0; esc = 0;}
			else {col = 247; esc = 247;}
			spark = 0.0f;
			load = 375;
			song = true;
			replay = false;
			effects = false;
		}
	}
	
	private void song() {
		if (song) {
			audio.playGameSound("app/res/replay.wav", 1.0);
			song = false;
		}
	}
	
	private void effects() {
		if (effects) {
			for (int i = 0; i < 20; i++) {
				handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH)-40, r.nextInt(Game.HEIGHT)-70, ID.MenuParticle, handler));
			}
			timer = -1;
			effects = false;
		}
	}
	
	public void tick() {
		if (load > 0) load--;
		if (load == 0) replay = true;
		if (timer > 0 && replay) timer--;
		if (timer == 0) effects = true;
	}
	
	public void render(Graphics g) {
		if (!replay) {
			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.lightGray);
			g.fillRect(0,0,Game.WIDTH,Game.HEIGHT);
			g.setFont(Game.menuFont);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.fillOval(195, 250, 40, 40);
			g.fillRect(213,250,400,40);
			g.fillOval(595, 250, 40, 40);
			g.drawString("Loading ...", 345, 230);
			g.setColor(new Color(0,170,0));
			g.fillOval(198, 253, 40, 34);
			g.fillRect(216, 253, 397-load, 34);
			g.fillOval(592-load, 253, 40, 34);
			
		}
		else if (replay) {
			song();
			effects();
			Graphics2D g2d = (Graphics2D)g;
			g.setFont(Game.titleFont);
			if (Settings.darkMode && col < 255) col+=0.25;
			else if (!Settings.darkMode && col > 0) col-=0.25;
			if (spark < 0.999f) spark+=0.001f;
			g.setColor(new Color((int)col,(int)col,(int)col));
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, spark));
			g.drawImage(sparkles,207,190,null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.999f));
			g.drawString("SUPRISE", 290, 250);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, spark));
			g.drawImage(sparkles,540,190,null);
			if (Settings.darkMode && esc < 200 && timer == -1) esc++;
			else if (!Settings.darkMode && esc > 60 && timer == -1) esc--;
			g.setColor(new Color(esc,esc,esc));
			g.setFont(Game.titleFont2);
			g.drawString("Press ESC to exit", 20, 30);
		}
	}
	
}
