package com.J2;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EndScreen extends MouseAdapter {
	private Game game;
	private Handler handler;
	private HUD hud;
	private AudioPlayer audio;
	private LoadGame load;

	private static int x_offset = 17;
	private static int y_offset = 47;

	// Window
	private static int window_width = (int) (Game.WIDTH * 0.5);
	private static int window_height = (int) (Game.HEIGHT * 0.7);
	private static int window_X = (Game.WIDTH - x_offset - window_width) / 2;
	private static int window_Y = (Game.HEIGHT - y_offset - window_height) / 2;

	// Sections
	private static int section_X = window_X + 15;
	private static int[] section_Y = new int[2];
	private static int section_width = (int) (window_width / 2.5 - 30);
	private static int section_height = (window_height - 30) / 4 - 40;

	// Content Window
	private static int content_X = (int) (window_X + window_width / 2.5);

	private static Color[] colors = new Color[2];

	public static boolean new_high_score = false;
	public static int new_coins = 0;

	public EndScreen(Game game, Handler handler, HUD hud, AudioPlayer audio, LoadGame load) {
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
				put("Game Over", Utils.get_text_dimensions("Game Over", Game.menuFont5));
				put("Play Again", Utils.get_text_dimensions("Play Again", Game.menuFont3));
				put("Main Menu", Utils.get_text_dimensions("Main Menu", Game.menuFont3));
				put("Player", Utils.get_text_dimensions("Player 1", Game.menuFont4));
			}
		};
	}

	private void reset_stats(boolean multiplayer) {
		audio.playMenuSound("app/res/button4.wav", 0.27);
		reset_colors();
		handler.object.clear();
		hud.set_level(1, 1);
		hud.set_level(2, 1);
		hud.set_score(1, 0);
		hud.set_score(2, 0);
		new_coins = 0;
		new_high_score = false;

		if (multiplayer)
			Game.multiplayer = false;

		if (load.user > 0)
			load.save(load.save_files.get(load.user), null);
	}

	public void mousePressed(MouseEvent e) {
		if (Game.gameState != STATE.End)
			return;

		int mx = e.getX();
		int my = e.getY();
		int mouse = e.getButton();
		Boolean left_click = mouse == MouseEvent.BUTTON1;

		if (!left_click)
			return;

		// Play Again
		if (Utils.mouse_over(mx, my, section_X, section_Y[0], section_width, section_height)) {
			reset_stats(false);

			Game.gameState = STATE.Game;

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
		else if (Utils.mouse_over(mx, my, section_X, section_Y[1], section_width, section_height)) {
			reset_stats(Game.multiplayer);

			Game.gameState = STATE.Menu;
			Utils.render_menu_particles(handler);

			if (load.user > 0)
				handler.add_object(new MenuPlayer((int) ((Game.WIDTH - 17) * 0.794), (int) (Game.HEIGHT * 0.36) + 82,
						ID.MenuPlayer, handler, load));
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		if (Game.gameState != STATE.End)
			return;

		int mx = e.getX();
		int my = e.getY();

		this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		// Play Again
		if (Utils.mouse_over(mx, my, section_X, section_Y[0], section_width, section_height)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[0] = Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		// Main Menu
		else if (Utils.mouse_over(mx, my, section_X, section_Y[1], section_width, section_height)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[1] = Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		else
			reset_colors();
	}

	public void tick() {
	}

	public void render(Graphics2D g) {
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
		Map<String, Integer> game_over_dims = dims.get("Game Over");
		g.drawString("Game Over", window_X + (window_width - game_over_dims.get("width")) / 2,
				window_Y + (30 - game_over_dims.get("height")) / 2 + game_over_dims.get("ascent"));

		// Divider
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g.setColor(Settings.darkMode ? new Color(35, 35, 35) : new Color(230, 230, 230));
		g.fillRect(content_X, window_Y + 45, 3, window_height - 65);

		// Sections
		g.setFont(Game.menuFont3);
		String[] sections = new String[] { "Play Again", "Main Menu" };
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

		g.setFont(Game.menuFont4);
		Map<String, Integer> p_dims = dims.get("Player");
		int start_X = content_X + 30;
		int start_Y = window_Y + 79 + p_dims.get("height");
		String high_score = new_high_score ? "New High Score: " + HUD.high_score : "High Score: " + HUD.high_score;
		g.drawString(high_score, start_X, start_Y);

		int coins_X = start_X + Game.coin.getWidth() + 10;
		int coins_Y = start_Y + 15 + Game.coin.getHeight() - (Game.coin.getHeight() - p_dims.get("height")) / 2;
		g.drawImage(Game.coin, start_X, start_Y + 15, null);
		g.drawString(String.valueOf(EndScreen.new_coins), coins_X, coins_Y);
		
		if (Game.multiplayer) {
			int p1_Y = start_Y + 15 + Game.coin.getHeight() + p_dims.get("height") + 30;
			g.drawString(load.user == 0 ? "Player 1" : load.saves.get(load.user - 1), start_X, p1_Y);
			g.setFont(Game.menuFont6);
			g.drawString("Score: " + hud.get_score(1), start_X, p1_Y + p_dims.get("height") + 15);
			g.drawString("Level: " + hud.get_level(1), start_X, p1_Y + (p_dims.get("height") + 15) * 2);

			g.setFont(Game.menuFont4);
			int p2_Y = p1_Y + (p_dims.get("height") + 15) * 3 + 15;
			g.drawString("Player 2", start_X, p2_Y);
			g.setFont(Game.menuFont6);
			g.drawString("Score: " + hud.get_score(2), start_X, p2_Y + p_dims.get("height") + 15);
			g.drawString("Level: " + hud.get_level(2), start_X, p2_Y + (p_dims.get("height") + 15) * 2);
		} else {
			int p1_Y = start_Y + 15 + Game.coin.getHeight() + p_dims.get("height") + 30;
			g.setFont(Game.menuFont4);
			g.drawString("Score: " + hud.get_score(1), start_X, p1_Y);
			g.drawString("Level: " + hud.get_level(1), start_X, p1_Y + p_dims.get("height") + 15);
		}
	}
}
