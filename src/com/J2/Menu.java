package com.J2;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class Menu extends MouseAdapter {

	private Game game;
	private Handler handler;
	private HUD hud;
	private AudioPlayer audio;
	private LoadGame load;

	private int x_offset = 17;

	// Single Player
	private int button_width1 = 220;
	private int button_height1 = 80;
	private int button_X1 = (Game.WIDTH - x_offset) / 2 - button_width1 - 15;
	private int button_Y1 = (int) (Game.HEIGHT * 0.27);

	// Multiplayer
	private int button_width2 = button_width1;
	private int button_height2 = button_height1;
	private int button_X2 = button_X1 + button_width1 + 30;;
	private int button_Y2 = button_Y1;

	// Settings
	private int button_width3 = button_width1 * 2 + 30;
	private int button_height3 = button_height1;
	private int button_X3 = button_X1;
	private int button_Y3 = button_Y1 + button_height1 + 30;

	// Quit
	private int button_width4 = button_width3;
	private int button_height4 = button_height1;
	private int button_X4 = button_X1;
	private int button_Y4 = button_Y3 + button_height1 + 30;

	// Load Game
	private int button_width5 = 110;
	private int button_height5 = 35;
	private int button_X5 = 60;
	private int button_Y5 = (int) (Game.HEIGHT * 0.75);

	// Shop
	private int button_width6 = button_width5;
	private int button_height6 = button_height5;
	private int button_X6 = Game.WIDTH - x_offset - button_width6 - button_X5;
	private int button_Y6 = button_Y5;

	private static Color menuColor;
	private static Color menuColor2;
	private static Color menuColor3;
	private static Color menuColor4;
	private static Color menuColor5;
	private static Color menuColor6;
	private static Color about;

	public Menu(Game game, Handler handler, HUD hud, AudioPlayer audio, LoadGame load) {
		this.game = game;
		this.hud = hud;
		this.handler = handler;
		this.audio = audio;
		this.load = load;

		reset_colors();
	}

	public static void reset_colors() {
		menuColor = Settings.darkMode ? Color.lightGray : Color.darkGray;
		menuColor2 = Settings.darkMode ? Color.lightGray : Color.darkGray;
		menuColor3 = Settings.darkMode ? Color.lightGray : Color.darkGray;
		menuColor4 = Settings.darkMode ? Color.lightGray : Color.darkGray;
		menuColor5 = Settings.darkMode ? Color.lightGray : Color.darkGray;
		menuColor6 = Settings.darkMode ? Color.lightGray : Color.darkGray;
		about = Settings.darkMode ? Color.lightGray : Color.darkGray;
	}

	public Map<String, Map<String, Integer>> setupTextDimensions() {
		return new HashMap<>() {
			{
				put("W A V E", Utils.get_text_dimensions("W A V E", Game.titleFont));
				put("MUSUBI", Utils.get_text_dimensions("MUSUBI", Game.titleFont2));
				put(Game.VERSION, Utils.get_text_dimensions(Game.VERSION, Game.titleFont2));
				put("Single Player", Utils.get_text_dimensions("Single Player", Game.menuFont5));
				put("Multiplayer", Utils.get_text_dimensions("Multiplayer", Game.menuFont5));
				put("Settings", Utils.get_text_dimensions("Settings", Game.menuFont));
				put("Quit", Utils.get_text_dimensions("Quit", Game.menuFont));
				put("Load", Utils.get_text_dimensions("Load Game", Game.menuFont6));
				put("Shop", Utils.get_text_dimensions("Shop", Game.menuFont6));
			}
		};
	}

	private void start_game(boolean multiplayer) {
		audio.playMenuSound("app/res/button.wav", 0.84);
		this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		Game.multiplayer = multiplayer;
		Game.gameState = STATE.Game;
		menuColor2 = Settings.darkMode ? Color.lightGray : Color.darkGray;
		this.handler.object.clear();

		int p1_X = Game.multiplayer ? Game.WIDTH / 2 - 64 : Game.WIDTH / 2 - 32;
		int p1_Y = Game.HEIGHT / 2 - 32;

		handler.add_object(new Player(p1_X, p1_Y, ID.Player, this.handler));

		if (multiplayer)
			handler.add_object(new Player(Game.WIDTH / 2 + 64, Game.HEIGHT / 2 - 32, ID.Player2, this.handler));

		for (int i = 0; i < (multiplayer ? 4 : 2); i++) {
			BasicEnemy basic = new BasicEnemy(Utils.rand_int(50, Game.WIDTH - 50),
					Utils.rand_int(50, Game.HEIGHT - 50), ID.BasicEnemy, handler, hud);

			HardEnemy hard = new HardEnemy(Utils.rand_int(50, Game.WIDTH - 50),
					Utils.rand_int(50, Game.HEIGHT - 50), ID.HardEnemy, handler, hud);

			handler.add_object(Settings.difficulty == 0 ? basic : hard);
		}
	}

	public void mousePressed(MouseEvent e) {
		if (Game.gameState == STATE.Menu) {
			int mx = e.getX();
			int my = e.getY();
			int mouse = e.getButton();
			boolean left_click = mouse == MouseEvent.BUTTON1;

			Map<String, Map<String, Integer>> dims = setupTextDimensions();
			Map<String, Integer> about_dims = dims.get("MUSUBI");

			if (!left_click)
				return;

			// Single Player
			if (Utils.mouse_over(mx, my, button_X1, button_Y1, button_width1, button_height1)) {
				start_game(false);
			}

			// Multiplayer
			else if (Utils.mouse_over(mx, my, button_X2, button_Y2, button_width2, button_height2)) {
				start_game(true);
			}

			// Settings
			else if (Utils.mouse_over(mx, my, button_X3, button_Y3, button_width3, button_height3)) {
				audio.playMenuSound("app/res/button.wav", 0.84);
				this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				Settings.timer = 15;
				Game.gameState = STATE.Settings;
				menuColor3 = Settings.darkMode ? Color.lightGray : Color.darkGray;
			}

			// Quit
			else if (Utils.mouse_over(mx, my, button_X4, button_Y4, button_width4, button_height4)) {
				if (load.user > 0)
					load.save(load.save_files.get(load.user), null);
				System.exit(1);
			}

			// Load
			else if (Utils.mouse_over(mx, my, button_X5, button_Y5, button_width5, button_height5)) {
				audio.playMenuSound("app/res/button.wav", 0.84);
				load.load_user();
				load.timer = 15;
				Game.gameState = STATE.Load;
				menuColor5 = Settings.darkMode ? Color.lightGray : Color.darkGray;
			}

			// Shop
			else if (Utils.mouse_over(mx, my, button_X6, button_Y6, button_width6, button_height6)) {
				audio.playMenuSound("app/res/button.wav", 0.84);
				this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				Game.gameState = STATE.Shop;
				Shop.timer = 15;
				menuColor6 = Settings.darkMode ? Color.lightGray : Color.darkGray;
			}

			// About
			else if (Utils.mouse_over(mx, my, 15, (int) (Game.HEIGHT * 0.92) - about_dims.get("ascent"),
					about_dims.get("width"), about_dims.get("height"))) {
				audio.playMenuSound("app/res/button.wav", 0.84);
				this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				About.timer = 15;
				Game.gameState = STATE.About;
				about = Settings.darkMode ? Color.lightGray : Color.darkGray;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (Game.gameState == STATE.Menu) {
			Map<String, Map<String, Integer>> dims = setupTextDimensions();
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			// Single Player
			if (Utils.mouse_over(mx, my, button_X1, button_Y1, button_width1, button_height1)) {
				menuColor = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
				this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else
				menuColor = Settings.darkMode ? Color.lightGray : Color.darkGray;

			// Multiplayer
			if (Utils.mouse_over(mx, my, button_X2, button_Y2, button_width2, button_height2)) {
				menuColor2 = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
				this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else
				menuColor2 = Settings.darkMode ? Color.lightGray : Color.darkGray;

			// Settings
			if (Utils.mouse_over(mx, my, button_X3, button_Y3, button_width3, button_height3)) {
				menuColor3 = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
				this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else
				menuColor3 = Settings.darkMode ? Color.lightGray : Color.darkGray;

			// Quit
			if (Utils.mouse_over(mx, my, button_X4, button_Y4, button_width4, button_height4)) {
				menuColor4 = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
				this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else
				menuColor4 = Settings.darkMode ? Color.lightGray : Color.darkGray;

			// Load
			if (Utils.mouse_over(mx, my, button_X5, button_Y5, button_width5, button_height5)) {
				menuColor5 = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
				this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else
				menuColor5 = Settings.darkMode ? Color.lightGray : Color.darkGray;

			// Shop
			if (Utils.mouse_over(mx, my, button_X6, button_Y6, button_width6, button_height6)) {
				menuColor6 = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
				this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else
				menuColor6 = Settings.darkMode ? Color.lightGray : Color.darkGray;

			// About
			Map<String, Integer> about_dims = dims.get("MUSUBI");
			if (Utils.mouse_over(mx, my, 15, (int) (Game.HEIGHT * 0.92) - about_dims.get("ascent"),
					about_dims.get("width"),
					about_dims.get("height"))) {
				about = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
				this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else
				about = Settings.darkMode ? Color.lightGray : Color.darkGray;
		}
	}

	public void tick() {
	}

	public void render(Graphics2D g) {
		if (Game.gameState == STATE.Menu) {
			Map<String, Map<String, Integer>> dims = setupTextDimensions();
			Composite original = g.getComposite();
			Color color;

			// W A V E
			g.setFont(Game.titleFont);
			color = Settings.darkMode ? Color.white : Color.black;
			g.setColor(color);
			Map<String, Integer> wave_dims = dims.get("W A V E");
			g.drawString("W A V E", (Game.WIDTH - x_offset - wave_dims.get("width")) / 2, (int) (Game.HEIGHT * 0.2));

			// About
			g.setFont(Game.titleFont2);
			color = Settings.darkMode ? Color.lightGray : Color.darkGray;
			g.setColor(color);
			Map<String, Integer> version_dims = dims.get(Game.VERSION);
			g.drawString(Game.VERSION, (Game.WIDTH - x_offset - version_dims.get("width") - 30),
					(int) (Game.HEIGHT * 0.92));
			g.setColor(about);
			g.drawString("MUSUBI", 15, (int) (Game.HEIGHT * 0.92));

			// Single Player
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
			color = Settings.darkMode ? Color.darkGray : new Color(235, 235, 235);
			g.setColor(color);
			g.fillRoundRect(button_X1, button_Y1, button_width1, button_height1, 20, 20);
			g.setComposite(original);
			g.setFont(Game.menuFont5);
			g.setColor(menuColor);
			g.drawRoundRect(button_X1, button_Y1, button_width1, button_height1, 20, 20);
			Map<String, Integer> single_dims = dims.get("Single Player");
			g.drawString("Single Player", button_X1 + (button_width1 - single_dims.get("width")) / 2,
					button_Y1 + (button_height1 - single_dims.get("height")) / 2 + single_dims.get("ascent"));

			// Multiplayer
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
			color = Settings.darkMode ? Color.darkGray : new Color(235, 235, 235);
			g.setColor(color);
			g.fillRoundRect(button_X2, button_Y2, button_width2, button_height2, 20, 20);
			g.setComposite(original);
			g.setFont(Game.menuFont5);
			g.setColor(menuColor2);
			g.drawRoundRect(button_X2, button_Y2, button_width2, button_height2, 20, 20);
			Map<String, Integer> multi_dims = dims.get("Multiplayer");
			g.drawString("Multiplayer", button_X2 + (button_width2 - multi_dims.get("width")) / 2,
					button_Y2 + (button_height2 - multi_dims.get("height")) / 2 + multi_dims.get("ascent"));

			// Settings
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
			color = Settings.darkMode ? Color.darkGray : new Color(235, 235, 235);
			g.setColor(color);
			g.fillRoundRect(button_X3, button_Y3, button_width3, button_height3, 20, 20);
			g.setComposite(original);
			g.setFont(Game.menuFont);
			g.setColor(menuColor3);
			g.drawRoundRect(button_X3, button_Y3, button_width3, button_height3, 20, 20);
			Map<String, Integer> settings_dims = dims.get("Settings");
			g.drawString("Settings", button_X3 + (button_width3 - settings_dims.get("width")) / 2,
					button_Y3 + (button_height3 - settings_dims.get("height")) / 2 + settings_dims.get("ascent"));

			// Quit
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
			color = Settings.darkMode ? Color.darkGray : new Color(235, 235, 235);
			g.setColor(color);
			g.fillRoundRect(button_X4, button_Y4, button_width4, button_height4, 20, 20);
			g.setComposite(original);
			g.setFont(Game.menuFont);
			g.setColor(menuColor4);
			g.drawRoundRect(button_X4, button_Y4, button_width4, button_height4, 20, 20);
			Map<String, Integer> quit_dims = dims.get("Quit");
			g.drawString("Quit", button_X4 + (button_width4 - quit_dims.get("width")) / 2,
					button_Y4 + (button_height4 - quit_dims.get("height")) / 2 + quit_dims.get("ascent"));

			// Load Game
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
			color = Settings.darkMode ? Color.darkGray : new Color(235, 235, 235);
			g.setColor(color);
			g.fillRoundRect(button_X5, button_Y5, button_width5, button_height5, 20, 20);
			g.setComposite(original);
			g.setFont(Game.menuFont6);
			g.setColor(menuColor5);
			g.drawRoundRect(button_X5, button_Y5, button_width5, button_height5, 20, 20);
			Map<String, Integer> load_dims = dims.get("Load");
			g.drawString("Load Game", button_X5 + (button_width5 - load_dims.get("width")) / 2,
					button_Y5 + (button_height5 - load_dims.get("height")) / 2 + load_dims.get("ascent"));

			// Shop
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
			color = Settings.darkMode ? Color.darkGray : new Color(235, 235, 235);
			g.setColor(color);
			g.fillRoundRect(button_X6, button_Y6, button_width6, button_height6, 20, 20);
			g.setComposite(original);
			g.setFont(Game.menuFont6);
			g.setColor(menuColor6);
			g.drawRoundRect(button_X6, button_Y6, button_width6, button_height6, 20, 20);
			Map<String, Integer> shop_dims = dims.get("Shop");
			g.drawString("Shop", button_X6 + (button_width6 - shop_dims.get("width")) / 2,
					button_Y6 + (button_height6 - shop_dims.get("height")) / 2 + shop_dims.get("ascent"));

			// Player
			if (load.user > 0) {
				color = Settings.darkMode ? new Color(210, 210, 210, (int) (255 * 0.1))
						: new Color(70, 70, 70, (int) (255 * 0.2));
				g.setColor(color);
				g.setFont(Game.menuFont5);

				int box_X = (int) ((Game.WIDTH - x_offset) * 0.794);
				int box_Y = (int) (Game.HEIGHT * 0.36);
				int box_width = 200;

				g.fillRoundRect(box_X, box_Y, box_width, box_width, 20, 20);
				color = Settings.darkMode ? Color.WHITE : Color.BLACK;
				g.setColor(color);
				g.drawRoundRect(box_X, box_Y, box_width, box_width, 20, 20);

				for (int i = 1; i <= load.saves.size(); i++) {
					if (load.user == i) {
						Map<String, Integer> name_dims = Utils.get_text_dimensions(load.saves.get(i - 1),
								Game.menuFont5);
						g.drawString(load.saves.get(i - 1), box_X + (box_width - name_dims.get("width")) / 2,
								box_Y - 15);
					}
				}
			}
		}
	}
}