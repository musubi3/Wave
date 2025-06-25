package com.J2;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Main Game class for Wave.
 */
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -2029340714966263948L;

	// Game version
	public static final String VERSION = "v1.1.0";

	// Game dimensions
	public static final int WIDTH = 1280;
	public static final int HEIGHT = WIDTH / 16 * 9;

	// Game state
	public static boolean paused = false;
	public static boolean multiplayer = false;

	// Fonts
	public static Font titleFont, titleFont2, titleFont3;
	public static Font menuFont, menuFont2, menuFont3, menuFont4, menuFont5, menuFont6, menuFont7;

	// Core game components
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

	// Listeners
	private KeyInput key_input;

	// Assets
	public static BufferedImage[] coins = new BufferedImage[6];
	public static BufferedImage[] skins = new BufferedImage[2];
	public static BufferedImage[] pyro_ball = new BufferedImage[13];
	public static BufferedImage[] green_ball = new BufferedImage[13];
	public static BufferedImage[] rasengan = new BufferedImage[10];
	public static BufferedImage healthPack;
	public static BufferedImage coin;

	public static STATE gameState = STATE.Menu;

	public Game() {
		// Load fonts
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			titleFont = loadFont("app/fonts/title.ttf", 70f, ge);
			titleFont2 = loadFont("app/fonts/title.ttf", 20f, ge);
			titleFont3 = loadFont("app/fonts/title.ttf", 15f, ge);

			menuFont = loadFont("app/fonts/PressStart2P.ttf", 20f, ge);
			menuFont2 = loadFont("app/fonts/PressStart2P.ttf", 18f, ge);
			menuFont3 = loadFont("app/fonts/PressStart2P.ttf", 16f, ge);
			menuFont4 = loadFont("app/fonts/PressStart2P.ttf", 14f, ge);
			menuFont5 = loadFont("app/fonts/PressStart2P.ttf", 12f, ge);
			menuFont6 = loadFont("app/fonts/PressStart2P.ttf", 10f, ge);
			menuFont7 = loadFont("app/fonts/PressStart2P.ttf", 8f, ge);

		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		// Load images
		ImageLoader img_loader = new ImageLoader();

		for (int i = 0; i < 6; i++)
			coins[i] = img_loader.loadImage("app/res/Coin/coin" + (i + 1) + ".png");
		coin = img_loader.loadImage("app/res/Coin/coin.png");

		healthPack = img_loader.loadImage("app/res/health.png");
		skins[0] = img_loader.loadImage("app/res/energyBall.png");
		skins[1] = img_loader.loadImage("app/res/energy2.png");

		for (int i = 0; i < 13; i++) {
			pyro_ball[i] = img_loader.loadImage("app/res/pyroBall/pyroBall" + (i + 1) + ".png");
			green_ball[i] = img_loader.loadImage("app/res/energyBall/e" + (i + 1) + ".png");

			if (i < 10)
				rasengan[i] = img_loader.loadImage("app/res/rasengan/rasengan" + (i + 1) + ".png");
		}

		// Initialize core components
		audio = new AudioPlayer();
		handler = new Handler();
		load = new LoadGame(this, audio, handler);
		hud = new HUD(load);
		menu = new Menu(this, handler, hud, audio, load);
		settings = new Settings(this, audio, load);
		end = new EndScreen(this, handler, hud, audio, load);
		pause = new Paused(this, handler, hud, audio, load);
		shop = new Shop(this, audio, load);
		about = new About(this, audio, load);

		key_input = new KeyInput(handler, audio);

		// Add input listeners
		this.addKeyListener(about);
		this.addKeyListener(load);
		this.addKeyListener(settings);
		this.addKeyListener(shop);
		this.addKeyListener(pause);
		this.addKeyListener(key_input);

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

		spawner = new Spawn(handler, hud, audio);
		new Window(WIDTH, HEIGHT, "WAVE", this);

		// Add animated menu particles
		Utils.render_menu_particles(handler);

		// Add menu player if user is loaded
		if (load.user > 0)
			handler.add_object(new MenuPlayer((int) ((Game.WIDTH - 17) * 0.794), (int) (Game.HEIGHT * 0.36) + 82,
					ID.MenuPlayer, handler, load));
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
		} catch (Exception e) {
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
		// int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			// frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames);
				// frames = 0;
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

				if (!multiplayer && Player.dead) {
					EndScreen.new_high_score = hud.is_high_score(hud.get_score(1));

					if (EndScreen.new_high_score)
						HUD.high_score = hud.get_score(1);

					gameState = STATE.End;
					audio.playMenuSound("app/res/gameOver.wav", 1.5);
					HUD.HEALTH = 100;
					Player.dead = false;
				} else if (multiplayer && Player.dead && Player.dead2) {
					EndScreen.new_high_score = hud.is_high_score(hud.get_score(1)) || hud.is_high_score(hud.get_score(2));

					if (EndScreen.new_high_score)
						HUD.high_score = Math.max(hud.get_score(1), hud.get_score(2));

					gameState = STATE.End;
					audio.playMenuSound("app/res/gameOver.wav", 1.5);
					HUD.HEALTH = 100;
					HUD.HEALTH2 = 100;
					Player.dead = false;
					Player.dead2 = false;
				}
			}
		} else if (gameState == STATE.Menu) {
			menu.tick();
			handler.tick();
		} else if (gameState == STATE.End) {
			end.tick();
			handler.tick();
		} else if (gameState == STATE.Load) {
			load.tick();
			handler.tick();
		} else if (gameState == STATE.Settings) {
			settings.tick();
			handler.tick();
		} else if (gameState == STATE.Shop) {
			shop.tick();
			handler.tick();
		} else if (gameState == STATE.About) {
			about.tick();
			handler.tick();
		} else
			handler.tick();

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		// g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		// RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

		g.setColor(Settings.darkMode ? Color.black : new Color(247, 247, 247));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);

		switch (gameState) {
			case Game:
				hud.render(g);
				break;
			case Menu:
				menu.render(g);
				break;
			case Load:
				load.render(g);
				break;
			case Settings:
				settings.render(g);
				break;
			case End:
				end.render(g);
				break;
			case Shop:
				shop.render(g);
				break;
			case About:
				about.render(g);
				break;
		}

		if (paused)
			pause.render(g);

		g.dispose();
		bs.show();
	}

	private Font loadFont(String path, float size, GraphicsEnvironment ge) throws IOException, FontFormatException {
		InputStream file = getClass().getResourceAsStream(path);
		Font font = Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(size);
		ge.registerFont(font);
		return font;
	}

	public static void main(String args[]) {
		new Game();
	}
}