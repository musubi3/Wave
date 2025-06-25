package com.J2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Shop extends MouseAdapter implements KeyListener {
	private enum SHOP {
		Upgrades, Skins
	}

	private Game game;
	private AudioPlayer audio;
	private LoadGame load;

	public static int health_level = 0;
	public static int coins = 0;
	public static int timer = 25;
	public static int skin = 0;
	public static boolean[] unlocked = new boolean[3];
	private static SHOP shop;

	private static int x_offset = 17;
	private static int y_offset = 47;

	// Window
	private static int window_width = (int) (Game.WIDTH * 0.6);
	private static int window_height = (int) (Game.HEIGHT * 0.7);
	private static int window_X = (Game.WIDTH - x_offset - window_width) / 2;
	private static int window_Y = (Game.HEIGHT - y_offset - window_height) / 2;

	// Sections
	private static int section_X = window_X + 15;
	private static int[] section_Y = new int[2];
	private static int section_width = window_width / 3 - 30;
	private static int section_height = (window_height - 30) / 4 - 40;

	// Content Window
	private static int content_X = window_X + window_width / 3;
	private static int content_width = 2 * window_width / 3;

	// Shop Items
	private static int item_padding = 26;
	private static int item_width = (int) (content_width / 2 - item_padding * 1.5);
	private static int item_height = (int) (item_width * 0.65);

	private static int item_X1 = content_X + item_padding;
	private static int item_X2 = item_X1 + item_width + item_padding;

	private static int item_Y1 = window_Y + 45;
	private static int item_Y2 = item_Y1 + item_height + item_padding;

	// Images
	private static BufferedImage coin_img = Utils.scale_image(Game.coin, 0.7f);
	private static Animation pyro_ball = new Animation(5, Game.pyro_ball);
	private static Animation rasengan = new Animation(5, Game.rasengan);

	private static Color[] colors = new Color[3];
	private static Color[] item_colors = new Color[4];

	private boolean max_health = false;
	private boolean max_speed = false;

	public Shop(Game game, AudioPlayer audio, LoadGame load) {
		this.game = game;
		this.audio = audio;
		this.load = load;
		shop = SHOP.Upgrades;

		for (int i = 0; i < section_Y.length; i++)
			section_Y[i] = window_Y + 30 + 15 * (i + 1) + section_height * i;

		max_health = health_level == 5;
		max_speed = Player.speed == 8;

		reset_colors();
	}

	public static void reset_colors() {
		Arrays.fill(item_colors, Settings.darkMode ? new Color(30, 30, 30) : new Color(230, 230, 230));

		colors[0] = Settings.darkMode ? new Color(160, 160, 160) : new Color(130, 130, 130);
		for (int i = 1; i <= section_Y.length; i++) {
			colors[i] = switch (shop) {
				case Upgrades -> Settings.darkMode ? i == 1 ? new Color(80, 80, 80) : new Color(30, 30, 30)
						: i == 1 ? new Color(140, 140, 140) : new Color(230, 230, 230);
				case Skins -> Settings.darkMode ? i == 2 ? new Color(80, 80, 80) : new Color(30, 30, 30)
						: i == 2 ? new Color(140, 140, 140) : new Color(230, 230, 230);
			};
		}
	}

	public Map<String, Map<String, Integer>> setupTextDimensions() {
		return new HashMap<>() {
			{
				put("Shop", Utils.get_text_dimensions("Shop", Game.menuFont5));
				put("Back", Utils.get_text_dimensions("Back", Game.menuFont5));
				put("Upgrades", Utils.get_text_dimensions("Upgrades", Game.menuFont3));
				put("Skins", Utils.get_text_dimensions("Skins", Game.menuFont3));
				put("Player Coins", Utils.get_text_dimensions(String.valueOf(coins), Game.menuFont5));
				put("Price", Utils.get_text_dimensions(String.valueOf(2000 + 1000 * health_level), Game.menuFont5));
			}
		};
	}

	private void exit_helper() {
		this.audio.playMenuSound("app/res/button4.wav", 0.27);
		colors = new Color[] {
				Settings.darkMode ? new Color(160, 160, 160) : new Color(130, 130, 130),
				Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140),
				Settings.darkMode ? new Color(30, 30, 30) : new Color(230, 230, 230),
		};

		this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		shop = SHOP.Upgrades;
		Game.gameState = STATE.Menu;
	}

	private void section_helper(SHOP new_shop) {
		this.audio.playMenuSound("app/res/button4.wav", 0.27);
		shop = new_shop;
		reset_colors();
	}

	public void mousePressed(MouseEvent e) {
		if (Game.gameState != STATE.Shop)
			return;
		int mx = e.getX();
		int my = e.getY();
		int mouse = e.getButton();
		Boolean left_click = mouse == MouseEvent.BUTTON1;

		if (left_click && timer <= 0) {
			// Back
			if (Utils.mouse_over(mx, my, window_X + 15, window_Y + 5, 58, 18)
					|| !Utils.mouse_over(mx, my, window_X, window_Y, window_width, window_height)) {
				exit_helper();
			}

			// Upgrades
			else if (Utils.mouse_over(mx, my, section_X, section_Y[0], section_width, section_height)) {
				section_helper(SHOP.Upgrades);
			}

			// Skins
			else if (Utils.mouse_over(mx, my, section_X, section_Y[1], section_width, section_height)) {
				section_helper(SHOP.Skins);
			}

			// Increase Health
			else if (Utils.mouse_over(mx, my, item_X1, item_Y1, item_width, item_height) && shop == SHOP.Upgrades) {
				if (coins >= 2000)
					audio.playMenuSound("app/res/button4.wav", 0.27);
				else if (coins < 2000)
					audio.playMenuSound("app/res/stop.wav", 1.0);

				if (health_level < 5 && coins >= 2000) {
					health_level++;
					coins -= 2000;

					max_health = health_level == 5;
				}

				if (load.user > 0)
					load.save(load.save_files.get(load.user), null);
			}

			// Increase Speed
			else if (Utils.mouse_over(mx, my, item_X2, item_Y1, item_width, item_height) && shop == SHOP.Upgrades) {
				if (coins >= 2000)
					audio.playMenuSound("app/res/button4.wav", 0.27);
				else if (coins < 2000)
					audio.playMenuSound("app/res/stop.wav", 1.0);

				if (Player.speed < 8 && coins >= 2000) {
					Player.speed++;
					coins -= 2000;

					max_speed = Player.speed == 8;
				}

				if (load.user > 0)
					load.save(load.save_files.get(load.user), null);

			}

			// Basic Blue
			else if (Utils.mouse_over(mx, my, item_X1, item_Y1, item_width, item_height) && shop == SHOP.Skins) {
				audio.playMenuSound("app/res/button4.wav", 0.27);
				if (skin != 0) {
					skin = 0;
					if (load.user > 0)
						load.save(load.save_files.get(load.user), null);
				}
			}

			// Energy Ball
			else if (Utils.mouse_over(mx, my, item_X2, item_Y1, item_width, item_height) && shop == SHOP.Skins) {
				if (!unlocked[0]) {
					if (coins >= 5000) {
						audio.playMenuSound("app/res/button4.wav", 0.27);
						unlocked[0] = true;
						skin = 1;
						coins -= 5000;
						if (load.user > 0)
							load.save(load.save_files.get(load.user), null);
					} else {
						audio.playMenuSound("app/res/stop.wav", 1.0);
					}
				} else {
					audio.playMenuSound("app/res/button4.wav", 0.27);
					if (skin != 1) {
						skin = 1;
						if (load.user > 0)
							load.save(load.save_files.get(load.user), null);
					}
				}
			}

			// Rasengan
			else if (Utils.mouse_over(mx, my, item_X1, item_Y2, item_width, item_height) && shop == SHOP.Skins) {
				if (!unlocked[1]) {
					if (coins >= 7500) {
						audio.playMenuSound("app/res/button4.wav", 0.27);
						unlocked[1] = true;
						skin = 2;
						coins -= 7500;
						if (load.user > 0)
							load.save(load.save_files.get(load.user), null);
					} else {
						audio.playMenuSound("app/res/stop.wav", 1.0);
					}
				} else {
					audio.playMenuSound("app/res/button4.wav", 0.27);
					if (skin != 2) {
						skin = 2;
						if (load.user > 0)
							load.save(load.save_files.get(load.user), null);
					}
				}
			}

			// Pyro Ball
			else if (Utils.mouse_over(mx, my, item_X2, item_Y2, item_width, item_height) && shop == SHOP.Skins) {
				if (!unlocked[2]) {
					if (coins >= 10000) {
						audio.playMenuSound("app/res/button4.wav", 0.27);
						unlocked[2] = true;
						skin = 3;
						coins -= 10000;
						if (load.user > 0)
							load.save(load.save_files.get(load.user), null);
					} else {
						audio.playMenuSound("app/res/stop.wav", 1.0);
					}
				} else {
					audio.playMenuSound("app/res/button4.wav", 0.27);
					if (skin != 3) {
						skin = 3;
						if (load.user > 0)
							load.save(load.save_files.get(load.user), null);
					}
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		if (Game.gameState != STATE.Shop)
			return;

		int mx = e.getX();
		int my = e.getY();

		this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		// Back
		if (Utils.mouse_over(mx, my, window_X + 15, window_Y + 5, 58, 18)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[0] = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
		}

		// Upgrades
		else if (Utils.mouse_over(mx, my, section_X, section_Y[0], section_width, section_height)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[1] = Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		// Skins
		else if (Utils.mouse_over(mx, my, section_X, section_Y[1], section_width, section_height)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[2] = Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		// Item 1
		else if (Utils.mouse_over(mx, my, item_X1, item_Y1, item_width, item_height)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			item_colors[0] = Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		// Item 2
		else if (Utils.mouse_over(mx, my, item_X2, item_Y1, item_width, item_height)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			item_colors[1] = Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		// Item 3
		else if (Utils.mouse_over(mx, my, item_X1, item_Y2, item_width, item_height) && shop == SHOP.Skins) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			item_colors[2] = Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		// Item 4
		else if (Utils.mouse_over(mx, my, item_X2, item_Y2, item_width, item_height) && shop == SHOP.Skins) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			item_colors[3] = Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		else {
			reset_colors();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (Game.gameState != STATE.Shop)
			return;

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			audio.playMenuSound("app/res/button4.wav", 0.27);
			if (load.user > 0)
				load.save(load.save_files.get(load.user), null);
			Game.gameState = STATE.Menu;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public void tick() {
		pyro_ball.run_animation();
		rasengan.run_animation();
		Utils.clamp(coins, 0, 999999999);
		Utils.clamp(timer, 0, 25);
		timer--;
	}

	private static void render_trail(Graphics2D g, int ball_width, int x, int y, int height, boolean is_skin,
			BufferedImage img) {
		int overlap = ball_width / 2 - 1;
		int n_balls = 10;
		int total_width = ball_width + (n_balls - 1) * (ball_width - overlap);
		int start_x = x + (item_width - total_width) / 2;
		y = y + (height - ball_width) / 2;
		for (int i = 0; i < n_balls; i++) {
			int ball_X = start_x + i * (ball_width - overlap);
			if (!is_skin) {
				g.setColor(new Color(0, 64, 255, (int) (255 * (0.1 * (i + 1)))));
				g.fillOval(ball_X, y, ball_width, ball_width);
			} else {
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (0.1 * (i + 1))));
				g.drawImage(img, ball_X, y, null);
			}
		}
	}

	public void render(Graphics2D g) {
		if (Game.gameState != STATE.Shop)
			return;

		Map<String, Map<String, Integer>> dims = setupTextDimensions();
		Composite original = g.getComposite();

		Utils.render_menu(g, load.user, load.saves, true);

		// Darken Background
		g.setColor(new Color(0, 0, 0, (int) (255 * 0.8)));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		// Coins
		Map<String, Integer> coin_dims = dims.get("Player Coins");
		int padding_x = 10;
		int padding_y = 5;
		int spacing = 20;
		int coin_box_X = Game.WIDTH - x_offset - coin_dims.get("width") - 10 - Game.coin.getWidth()
				- padding_x * 2 - spacing;
		int coin_box_width = coin_dims.get("width") + 10 + Game.coin.getWidth() + padding_x * 2;

		g.setFont(Game.menuFont5);
		g.setColor(new Color(50, 50, 50, (int) (255 * 0.7)));
		g.fillRoundRect(coin_box_X, spacing, coin_box_width, Game.coin.getHeight() + padding_y * 2, 20, 20);
		g.drawImage(Game.coin, coin_box_X + padding_x, spacing + padding_y, null);
		g.setColor(Color.lightGray);
		g.drawString(String.valueOf(coins), coin_box_X + padding_x + Game.coin.getWidth() + 10,
				spacing + padding_y * 2 + coin_dims.get("ascent"));

		// Window
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g.setColor(Settings.darkMode ? Color.darkGray : new Color(200, 200, 200));
		g.fillRoundRect(window_X, window_Y, window_width, window_height, 20, 20);
		g.setColor(Settings.darkMode ? new Color(30, 30, 30) : new Color(230, 230, 230));
		g.drawRoundRect(window_X, window_Y, window_width, window_height, 20, 20);

		Path2D path = new Path2D.Double();
		int radius = 10;
		path.moveTo(window_X + radius, window_Y);
		path.lineTo(window_X + window_width - radius, window_Y);
		path.quadTo(window_X + window_width, window_Y, window_X + window_width, window_Y + radius);
		path.lineTo(window_X + window_width, window_Y + 30);
		path.lineTo(window_X, window_Y + 30);
		path.lineTo(window_X, window_Y + radius);
		path.quadTo(window_X, window_Y, window_X + radius, window_Y);
		path.closePath();
		g.fill(path);

		// Text
		g.setComposite(original);
		g.setFont(Game.menuFont5);
		g.setColor(Settings.darkMode ? Color.lightGray : Color.darkGray);
		Map<String, Integer> about_dims = dims.get("Shop");
		g.drawString("Shop", window_X + (window_width - about_dims.get("width")) / 2,
				window_Y + (30 - about_dims.get("height")) / 2 + about_dims.get("ascent"));

		// Back
		g.setColor(colors[0]);
		g.setFont(Game.menuFont5);
		g.drawRoundRect(window_X + 15, window_Y + 5, 58, 18, 10, 10);
		Map<String, Integer> back_dims = dims.get("Back");
		g.drawString("Back", window_X + 15 + (60 - back_dims.get("width")) / 2,
				window_Y + (30 - back_dims.get("height")) / 2 + back_dims.get("ascent"));

		// Divider
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g.setColor(Settings.darkMode ? new Color(35, 35, 35) : new Color(230, 230, 230));
		g.fillRect(window_X + window_width / 3, window_Y + 45, 3, window_height - 65);

		// Sections
		g.setFont(Game.menuFont3);
		String[] sections = new String[] { "Upgrades", "Skins" };
		for (int i = 0; i < section_Y.length; i++) {
			g.setColor(colors[i + 1]);
			g.fillRoundRect(section_X, section_Y[i], section_width, section_height, 20, 20);
			g.setColor(Settings.darkMode ? new Color(25, 25, 25) : new Color(225, 225, 225));
			g.drawRoundRect(section_X, section_Y[i], section_width, section_height, 20, 20);

			g.setColor(Settings.darkMode ? Color.lightGray : Color.darkGray);
			Map<String, Integer> section_dims = dims.get(sections[i]);
			g.drawString(sections[i], section_X + (section_width - section_dims.get("width")) / 2,
					section_Y[i] + (section_height - section_dims.get("height")) / 2 + section_dims.get("ascent"));
		}

		Map<String, Integer> price_dims = dims.get("Price");
		int coin_X1 = item_X1 + 10;
		int coin_X2 = item_X2 + 10;

		int price_X1 = coin_X1 + coin_img.getWidth() + 10;
		int price_X2 = coin_X2 + coin_img.getWidth() + 10
				+ (coin_img.getHeight() - price_dims.get("height")) / 2;

		int coin_Y1 = item_Y1 + item_height - coin_img.getHeight() - 10;
		int coin_Y2 = item_Y2 + item_height - coin_img.getHeight() - 10;

		int price_Y1 = coin_Y1 + price_dims.get("height")
				+ (coin_img.getHeight() - price_dims.get("height")) / 2;
		int price_Y2 = coin_Y2 + price_dims.get("height")
				+ (coin_img.getHeight() - price_dims.get("height")) / 2;
		switch (shop) {
			case Upgrades:
				// Item Boxes
				g.setColor(item_colors[0]);
				g.fillRoundRect(item_X1, item_Y1, item_width, item_height, 20, 20);
				g.setColor(Settings.darkMode ? new Color(25, 25, 25) : new Color(225, 225, 225));
				g.drawRoundRect(item_X1, item_Y1, item_width, item_height, 20, 20);
				g.setColor(item_colors[1]);
				g.fillRoundRect(item_X2, item_Y1, item_width, item_height, 20, 20);

				g.setColor(Settings.darkMode ? new Color(25, 25, 25) : new Color(225, 225, 225));
				g.drawRoundRect(item_X1, item_Y1, item_width, item_height, 20, 20);
				g.drawRoundRect(item_X2, item_Y1, item_width, item_height, 20, 20);

				// Item Info
				g.setColor(Settings.darkMode ? Color.lightGray : Color.darkGray);
				g.setFont(Game.menuFont5);

				// Increase Health
				if (!max_health) {
					g.drawImage(coin_img, coin_X1, coin_Y1, null);
					g.drawString(String.valueOf(2000 + 1000 * health_level), price_X1, price_Y1);
				} else
					g.drawString("Max Level", coin_X1, price_Y1);
				g.drawString("Increase Health", item_X1 + 10, coin_Y1 - 10);

				int level_X1 = item_X1 + 10;
				int level_width = (item_width - 6 * 10) / 5;
				for (int i = 0; i < health_level; i++)
					g.fillRoundRect(level_X1 + (level_width + 10) * i, item_Y1 + 10, level_width, 5, 5, 5);
				for (int i = 0; i < 5; i++)
					g.drawRoundRect(level_X1 + (level_width + 10) * i, item_Y1 + 10, level_width, 5, 5, 5);

				g.setColor(Color.green);
				int icon_Y = item_Y1 + 15;
				int icon_height = (coin_Y1 - 10 - price_dims.get("height")) - icon_Y;
				int health_width = 15;
				int health_height = 45;
				Function<Integer, Integer> get_health_X = width -> item_X1 + (item_width - width) / 2;
				Function<Integer, Integer> get_health_Y = height -> icon_Y + (icon_height - height) / 2;
				g.fillRoundRect(get_health_X.apply(health_width), get_health_Y.apply(health_height), health_width,
						health_height, 5, 5);
				g.fillRoundRect(get_health_X.apply(health_height), get_health_Y.apply(health_width), health_height,
						health_width, 5, 5);
				g.drawRoundRect(get_health_X.apply(health_width), get_health_Y.apply(health_height), health_width,
						health_height, 5, 5);
				g.drawRoundRect(get_health_X.apply(health_height), get_health_Y.apply(health_width), health_height,
						health_width, 5, 5);

				// Increase Speed
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
				g.setColor(Settings.darkMode ? Color.lightGray : Color.darkGray);
				if (!max_speed) {
					g.drawImage(coin_img, coin_X2, coin_Y1, null);
					g.drawString(String.valueOf(2000 + 1000 * (Player.speed - 3)), price_X2, price_Y1);
				} else
					g.drawString("Max Level", coin_X2, price_Y1);
				g.drawString("Increase Speed", item_X2 + 10, coin_Y1 - 10);

				int level_X2 = item_X2 + 10;
				for (int i = 0; i < Player.speed - 3; i++)
					g.fillRoundRect(level_X2 + (level_width + 10) * i, item_Y1 + 10, level_width, 5, 5, 5);
				for (int i = 0; i < 5; i++)
					g.drawRoundRect(level_X2 + (level_width + 10) * i, item_Y1 + 10, level_width, 5, 5, 5);

				g.setComposite(original);
				render_trail(g, 28, item_X2, icon_Y, icon_height, false, null);
				break;

			case Skins:
				g.setColor(item_colors[0]);
				g.fillRoundRect(item_X1, item_Y1, item_width, item_height, 20, 20);
				g.setColor(item_colors[1]);
				g.fillRoundRect(item_X2, item_Y1, item_width, item_height, 20, 20);
				g.setColor(item_colors[2]);
				g.fillRoundRect(item_X1, item_Y2, item_width, item_height, 20, 20);
				g.setColor(item_colors[3]);
				g.fillRoundRect(item_X2, item_Y2, item_width, item_height, 20, 20);

				g.setColor(Settings.darkMode ? new Color(25, 25, 25) : new Color(225, 225, 225));
				g.drawRoundRect(item_X1, item_Y1, item_width, item_height, 20, 20);
				g.drawRoundRect(item_X2, item_Y1, item_width, item_height, 20, 20);
				g.drawRoundRect(item_X1, item_Y2, item_width, item_height, 20, 20);
				g.drawRoundRect(item_X2, item_Y2, item_width, item_height, 20, 20);

				g.setColor(Settings.darkMode ? Color.lightGray : Color.darkGray);
				g.setFont(Game.menuFont5);
				int skin_height = (coin_Y1 - 10 - price_dims.get("height")) - item_Y1;

				g.drawString("Basic Blue", item_X1 + 10, coin_Y1 - 10);
				g.drawString(skin == 0 ? "Equipped" : "Owned", coin_X1, price_Y1);

				g.drawString("Energy Ball", item_X2 + 10, coin_Y1 - 10);
				if (!unlocked[0]) {
					g.drawImage(coin_img, coin_X2, coin_Y1, null);
					g.drawString(String.valueOf(5000), price_X2, price_Y1);
				} else
					g.drawString(skin == 1 ? "Equipped" : "Owned", coin_X2, price_Y1);

				g.drawString("Rasengan", item_X1 + 10, coin_Y2 - 10);
				if (!unlocked[1]) {
					g.drawImage(coin_img, coin_X1, coin_Y2, null);
					g.drawString(String.valueOf(7500), price_X1, price_Y2);
				} else
					g.drawString(skin == 2 ? "Equipped" : "Owned", coin_X1, price_Y2);

				g.drawString("Pyro Ball", item_X2 + 10, coin_Y2 - 10);
				if (!unlocked[2]) {
					g.drawImage(coin_img, coin_X2, coin_Y2, null);
					g.drawString(String.valueOf(10000), price_X2, price_Y2);
				} else
					g.drawString(skin == 3 ? "Equipped" : "Owned", coin_X2, price_Y2);

				g.setComposite(original);
				render_trail(g, 32, item_X1, item_Y1, skin_height, false, null);
				render_trail(g, 32, item_X2, item_Y1, skin_height, true, Game.skins[0]);
				render_trail(g, 32, item_X1, item_Y2, skin_height, true, rasengan.get_current_frame());
				render_trail(g, 32, item_X2, item_Y2, skin_height, true, pyro_ball.get_current_frame());
				break;
		}
	}
}
