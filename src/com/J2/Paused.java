package com.J2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Paused extends MouseAdapter implements KeyListener {
	private Game game;
	private Handler handler;
	private HUD hud;
	private AudioPlayer audio;
	private LoadGame load;

	private static int x_offset = 17;
	private static int y_offset = 47;

	// Window
	private static int window_width = (int) (Game.WIDTH * 0.4);
	private static int window_height = (int) (Game.HEIGHT * 0.7);
	private static int window_X = (Game.WIDTH - x_offset - window_width) / 2;
	private static int window_Y = (Game.HEIGHT - y_offset - window_height) / 2;

	// Sections
	private static int section_X = window_X + 15;
	private static int[] section_Y = new int[3];
	private static int section_width = (int) (window_width / 2.5 - 30);
	private static int section_height = (window_height - 30) / 4 - 40;

	// Content Window
	private static int content_X = (int) (window_X + window_width / 2.5);

	private static Color[] colors = new Color[3];

	public Paused(Game game, Handler handler, HUD hud, AudioPlayer audio, LoadGame load) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.audio = audio;
		this.load = load;

		for (int i = 0; i < section_Y.length; i++)
			section_Y[i] = window_Y + 30 + 15 * (i + 1) + section_height * i;

		reset_colors();
	}

	public static void reset_colors() {
		Arrays.fill(colors, Settings.darkMode ? new Color(30, 30, 30) : new Color(230, 230, 230));
	}

	public Map<String, Map<String, Integer>> setupTextDimensions() {
		return new HashMap<>() {
			{
				put("Paused", Utils.get_text_dimensions("Paused", Game.menuFont5));
				put("Resume", Utils.get_text_dimensions("Resume", Game.menuFont3));
				put("Restart", Utils.get_text_dimensions("Restart", Game.menuFont3));
				put("Main Menu", Utils.get_text_dimensions("Main Menu", Game.menuFont3));
				put("Coins", Utils.get_text_dimensions(String.valueOf(EndScreen.new_coins), Game.menuFont4));
			}
		};
	}

	private void reset_stats(boolean multiplayer) {
		this.audio.playMenuSound("app/res/button4.wav", 0.27);
		reset_colors();
		this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		handler.object.clear();
		Game.paused = false;

		if (multiplayer)
			Game.multiplayer = false;

		hud.set_level(1, 1);
		hud.set_level(2, 1);
		hud.set_score(1, 0);
		hud.set_score(2, 0);
		HUD.HEALTH = 100;
		HUD.HEALTH2 = 100;
		Shop.coins -= EndScreen.new_coins;
		EndScreen.new_coins = 0;
	}

	public void mousePressed(MouseEvent e) {
		if (!Game.paused)
			return;

		int mx = e.getX();
		int my = e.getY();
		int mouse = e.getButton();
		Boolean left_click = mouse == MouseEvent.BUTTON1;

		if (!left_click)
			return;

		// Resume
		if (!Utils.mouse_over(mx, my, window_X, window_Y, window_width, window_height)
				|| Utils.mouse_over(mx, my, section_X, section_Y[0], section_width, section_height)) {
			audio.playMenuSound("app/res/button.wav", 0.84);
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			reset_colors();
			Game.paused = false;
		}

		// Restart
		else if (Utils.mouse_over(mx, my, section_X, section_Y[1], section_width, section_height)) {
			reset_stats(false);

			int p1_X = Game.multiplayer ? Game.WIDTH / 2 - 64 : Game.WIDTH / 2 - 32;
			int p1_Y = Game.HEIGHT / 2 - 32;

			handler.add_object(new Player(p1_X, p1_Y, ID.Player, handler));

			if (Game.multiplayer)
				handler.add_object(new Player(Game.WIDTH / 2 + 64, Game.HEIGHT / 2 - 32, ID.Player2, handler));

			for (int i = 0; i < (Game.multiplayer ? 4 : 2); i++) {
				BasicEnemy basic = new BasicEnemy(Utils.rand_int(50, Game.WIDTH - 50),
						Utils.rand_int(50, Game.HEIGHT - 50), ID.BasicEnemy, handler, hud);

				HardEnemy hard = new HardEnemy(Utils.rand_int(50, Game.WIDTH - 50),
						Utils.rand_int(50, Game.HEIGHT - 50), ID.HardEnemy, handler, hud);

				handler.add_object(Settings.difficulty == 0 ? basic : hard);
			}
		}

		// Main Menu
		else if (Utils.mouse_over(mx, my, section_X, section_Y[2], section_width, section_height)) {
			reset_stats(true);

			Game.gameState = STATE.Menu;
			Utils.render_menu_particles(handler);

			if (load.user > 0)
				handler.add_object(new MenuPlayer((int) ((Game.WIDTH - 17) * 0.794),
						(int) (Game.HEIGHT * 0.36) + 82, ID.MenuPlayer, handler, load));
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		if (!Game.paused)
			return;

		int mx = e.getX();
		int my = e.getY();

		this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		// Resume
		if (Utils.mouse_over(mx, my, section_X, section_Y[0], section_width, section_height)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[0] = Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		// Restart
		else if (Utils.mouse_over(mx, my, section_X, section_Y[1], section_width, section_height)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[1] = Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		// Main Menu
		else if (Utils.mouse_over(mx, my, section_X, section_Y[2], section_width, section_height)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[2] = Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		else
			reset_colors();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (Game.gameState != STATE.Game)
			return;

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			audio.playMenuSound("app/res/button4.wav", 0.27);
			Game.paused = !Game.paused;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public void render(Graphics2D g) {
		if (!Game.paused)
			return;

		Map<String, Map<String, Integer>> dims = setupTextDimensions();

		// Darken Background
		g.setColor(new Color(0, 0, 0, (int) (255 * 0.8)));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

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
		g.setFont(Game.menuFont5);
		g.setColor(Settings.darkMode ? Color.lightGray : Color.darkGray);
		Map<String, Integer> pause_dims = dims.get("Paused");
		g.drawString("Paused", window_X + (window_width - pause_dims.get("width")) / 2,
				window_Y + (30 - pause_dims.get("height")) / 2 + pause_dims.get("ascent"));

		// Divider
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g.setColor(Settings.darkMode ? new Color(35, 35, 35) : new Color(230, 230, 230));
		g.fillRect(content_X, window_Y + 45, 3, window_height - 65);

		// Sections
		g.setFont(Game.menuFont3);
		String[] sections = new String[] { "Resume", "Restart", "Main Menu" };
		for (int i = 0; i < section_Y.length; i++) {
			g.setColor(colors[i]);
			g.fillRoundRect(section_X, section_Y[i], section_width, section_height, 20, 20);
			g.setColor(Settings.darkMode ? new Color(25, 25, 25) : new Color(225, 225, 225));
			g.drawRoundRect(section_X, section_Y[i], section_width, section_height, 20, 20);

			g.setColor(Settings.darkMode ? Color.lightGray : Color.darkGray);
			Map<String, Integer> section_dims = dims.get(sections[i]);
			g.drawString(sections[i], section_X + (section_width - section_dims.get("width")) / 2,
					section_Y[i] + (section_height - section_dims.get("height")) / 2 + section_dims.get("ascent"));
		}

		Map<String, Integer> coin_dims = dims.get("Coins");
		int coin_icon_X = content_X + 30;
		int coin_icon_Y = window_Y + 60;
		int coins_X = coin_icon_X + Game.coin.getWidth() + 10;
		int coins_Y = coin_icon_Y + Game.coin.getHeight() - (Game.coin.getHeight() - coin_dims.get("height")) / 2;
		g.setFont(Game.menuFont4);
		g.drawImage(Game.coin, coin_icon_X, coin_icon_Y, null);
		g.drawString(String.valueOf(EndScreen.new_coins), coins_X, coins_Y);

		if (Game.multiplayer) {
			int p1_Y = coin_icon_Y + Game.coin.getHeight() + coin_dims.get("height") + 30;
			g.drawString(load.user == 0 ? "Player 1" : load.saves.get(load.user - 1), coin_icon_X, p1_Y);
			g.setFont(Game.menuFont6);
			g.drawString("Score: " + hud.get_score(1), coin_icon_X, p1_Y + coin_dims.get("height") + 15);
			g.drawString("Level: " + hud.get_level(1), coin_icon_X, p1_Y + (coin_dims.get("height") + 15) * 2);

			g.setFont(Game.menuFont4);
			int p2_Y = p1_Y + (coin_dims.get("height") + 15) * 3 + 15;
			g.drawString("Player 2", coin_icon_X, p2_Y);
			g.setFont(Game.menuFont6);
			g.drawString("Score: " + hud.get_score(2), coin_icon_X, p2_Y + coin_dims.get("height") + 15);
			g.drawString("Level: " + hud.get_level(2), coin_icon_X, p2_Y + (coin_dims.get("height") + 15) * 2);
		} else {
			int p1_Y = coin_icon_Y + Game.coin.getHeight() + coin_dims.get("height") + 30;
			g.setFont(Game.menuFont4);
			g.drawString("Score: " + hud.get_score(1), coin_icon_X, p1_Y);
			g.drawString("Level: " + hud.get_level(1), coin_icon_X, p1_Y + coin_dims.get("height") + 15);
		}
	}
}
