package com.J2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -2029340714966263948L;

	public static final int WIDTH = 840, HEIGHT = WIDTH / 12 * 9;
	public static boolean paused = false;
	public static boolean multiplayer = false;
	public static int difficulty = 0;
	public static Font titleFont;
	public static Font titleFont2;
	public static Font titleFont3;
	public static Font menuFont;
	public static Font menuFont2;
	public static Font menuFont3;
	public static Font menuFont4;
	
	private Thread thread;
	private boolean running = false;

	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	private EndScreen end;
	private Settings settings;
	private AudioPlayer audio;
	private Paused pause;
	private Shop shop;
	private LoadGame load;
	private About about;
	private Easter egg;
	
	private Random r = new Random();
	public static BufferedImage[] coin = new BufferedImage[7];
	public static BufferedImage[] skins = new BufferedImage[4];
	public static BufferedImage[] pyroBall = new BufferedImage[13];
	public static BufferedImage[] greenBall = new BufferedImage[13];
	public static BufferedImage healthPack;
	
	public enum STATE {
		Menu,
		Settings,
		AudioSettings,
		ApperanceSettings,
		Game,
		Difficulty,
		Shop,
		Load,
		End,
		End2,
		About,
		Egg
	}

	public static STATE gameState = STATE.Menu;

	public Game() {
		audio = new AudioPlayer();
		handler = new Handler();
		hud = new HUD();
		load = new LoadGame(audio, handler, hud);
		menu = new Menu(handler, hud, audio, load);
		settings = new Settings(audio, load);
		end = new EndScreen(handler, hud, audio, load);
		pause = new Paused(handler, hud, audio, load);
		shop = new Shop(audio, load);
		about = new About(audio);
		egg = new Easter(audio, handler);
		this.addKeyListener(new KeyBoard(load, handler));
		this.addKeyListener(new KeyInput(handler, load, audio));
		this.addKeyListener(egg);
		this.addMouseListener(menu);
		this.addMouseListener(settings);
		this.addMouseListener(end);
		this.addMouseListener(pause);
		this.addMouseListener(shop);
		this.addMouseListener(load);
		this.addMouseListener(about);
		this.addMouseMotionListener(menu);
		this.addMouseMotionListener(end);
		this.addMouseMotionListener(settings);
		this.addMouseMotionListener(pause);
		this.addMouseMotionListener(shop);
		this.addMouseMotionListener(load);
		this.addMouseMotionListener(about);
		
		try {
			titleFont = Font.createFont(Font.TRUETYPE_FONT, new File("fnt/title.ttf")).deriveFont(70f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fnt/title.ttf")));
			titleFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("fnt/title.ttf")).deriveFont(20f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fnt/title.ttf")));
			titleFont3 = Font.createFont(Font.TRUETYPE_FONT, new File("fnt/title.ttf")).deriveFont(15f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fnt/title.ttf")));
			menuFont = Font.createFont(Font.TRUETYPE_FONT, new File("fnt/menu.ttf")).deriveFont(20f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fnt/menu.ttf")));
			menuFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("fnt/menu.ttf")).deriveFont(13f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fnt/menu.ttf")));
			menuFont3 = Font.createFont(Font.TRUETYPE_FONT, new File("fnt/menu.ttf")).deriveFont(10f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fnt/menu.ttf")));
			menuFont4 = Font.createFont(Font.TRUETYPE_FONT, new File("fnt/menu.ttf")).deriveFont(12.5f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fnt/menu.ttf")));
		}catch (FontFormatException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		
		
		if (Settings.music) audio.playGameSound("res/music.wav", 1.122);
		load.loadUser();
		load.load(load.saveFiles.get(load.user));

		spawner = new Spawn(handler, hud, audio);

		new Window(WIDTH, HEIGHT, "WAVE", this);
		
		ImageLoader imgLoad = new ImageLoader();
		for (int i = 0; i < 6; i++) {int file = i+1; coin[i] = imgLoad.loadImage("res/Coin/coin"+file+".png");}
		coin[6] = imgLoad.loadImage("res/Coin/coin.png");
		healthPack = imgLoad.loadImage("res/health.png");
		skins[0] = imgLoad.loadImage("res/energyBall.png");
		skins[1] = imgLoad.loadImage("res/rasengan.png");
		skins[2] = imgLoad.loadImage("res/energy2.png");
		for (int i = 0; i < 13; i++) {int file = i+1; pyroBall[i] = imgLoad.loadImage("res/pyroBall/pyroBall"+file+".png");}
		for (int i = 0; i < 13; i++) {int file = i+1; greenBall[i] = imgLoad.loadImage("res/energyBall/e"+file+".png");}
		
			
		for (int i = 0; i < 20; i++) {
				if (gameState == STATE.Menu) handler.addObject(new MenuParticle(r.nextInt(WIDTH)-40, r.nextInt(HEIGHT)-70, ID.MenuParticle, handler));
		}
		if (load.user > 0) handler.addObject(new MenuPlayer(640,355,ID.MenuPlayer,handler));
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
//		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
//			frames++;

			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
//				System.out.println("FPS: " + frames);
//				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		if (gameState == STATE.Game) {
			if (!paused) {
				handler.tick();
				hud.tick();
				spawner.tick();
				
				if (Player.dead && !multiplayer) {
					hud.isHighScore(hud.getScore());
					if (hud.highScore == hud.getScore()) end.newHighScore = true;
					gameState = STATE.End;
					audio.playMenuSound("res/gameOver.wav", 1.5);
					HUD.HEALTH = 100;
					Player.dead = false;
				} else if (multiplayer && Player.dead && Player.dead2) {
					hud.isHighScore(hud.getScore());
					hud.isHighScore(hud.getScore2());
					if (hud.highScore == hud.getScore() || hud.highScore == hud.getScore2()) end.newHighScore = true;
					gameState = STATE.End2;
					audio.playMenuSound("res/gameOver.wav", 1.5);
					HUD.HEALTH = 100;
					HUD.HEALTH2 = 100;
					Player.dead = false;
					Player.dead2 = false;
				}
			}
		}
		else if (gameState == STATE.Menu) {
			menu.tick();
			handler.tick();
		}
		else if (gameState == STATE.End || gameState == STATE.End2) {
			end.tick();
			handler.tick();
		}
		else if (gameState == STATE.Load) {
			load.tick();
			handler.tick();
		}
		else if (gameState == STATE.Settings) {
			settings.tick();
			handler.tick();
		}
		else if (gameState == STATE.Shop) {
			shop.tick();
			handler.tick();
		}
		else if (gameState == STATE.About) {
			about.tick();
			handler.tick();
		}
		else if (gameState == STATE.Egg) {
			egg.tick();
			handler.tick();
		}
		else handler.tick();
		
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		if (Settings.darkMode) g.setColor(Color.black);
		else g.setColor(new Color(247,247,247));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);
		
		if (paused) {
			pause.render(g);
		}
		if (gameState == STATE.Game) {
			hud.render(g);
		}
		else if (gameState == STATE.Menu) {
			menu.render(g);
		}
		else if (gameState == STATE.Load) {
			load.render(g);
		}
		else if (gameState == STATE.Settings || gameState == STATE.AudioSettings 
				 || gameState == STATE.ApperanceSettings || gameState == STATE.Difficulty) {
			settings.render(g);
		}
		else if (gameState == STATE.End || gameState == STATE.End2 ) {
			end.render(g);
		}
		else if (gameState == STATE.Shop) {
			shop.render(g);
		}
		else if (gameState == STATE.About) {
			about.render(g);
		}
		else if (gameState == STATE.Egg) {
			egg.render(g);
		}
		g.dispose();
		bs.show();
	}

	public static float clamp(float x, float min, float max) {
		if (x >= max) return x = max;
		else if (x <= min) return x = min;
		else return x;

	}

	public static void main(String args[]) {
		new Game();
	}
}
