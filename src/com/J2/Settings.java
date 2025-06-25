package com.J2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Settings extends MouseAdapter implements KeyListener {
	private enum SETTING {
		Controls, Audio, Theme, Difficulty
	}

	public static int timer = 15;
	public static String[] songs = { "Default", "Sketchers", "Act My Age" };
	public static String[] song_files = { "app/res/music.wav", "app/res/sketchers.wav", "app/res/1D.wav" };
	public static String[] difficulties = { "Normal", "Hard" };
	public static boolean music = true;
	public static boolean sound = true;
	public static int song_index = 0;
	public static boolean darkMode = true;
	public static int difficulty = 0;

	private Game game;
	private AudioPlayer audio;
	private LoadGame load;
	private static SETTING setting;
	private static ArrayList<SETTING> all_settings = new ArrayList<>(
			Arrays.asList(new SETTING[] { SETTING.Controls, SETTING.Audio, SETTING.Theme, SETTING.Difficulty }));
	private static boolean drop_down = false;

	private int x_offset = 17;
	private int y_offset = 47;

	// Window
	private int window_width = (int) (Game.WIDTH * 0.6);
	private int window_height = (int) (Game.HEIGHT * 0.7);
	private int window_X = (Game.WIDTH - x_offset - window_width) / 2;
	private int window_Y = (Game.HEIGHT - y_offset - window_height) / 2;

	// Sections
	private int section_X = window_X + 15;
	private int[] section_Y = new int[4];
	private int section_width = window_width / 3 - 30;
	private int section_height = (window_height - 30) / 4 - 40;

	// Horizontal Carousel Toggle
	private int carousel_width;
	private int carousel_height;
	private int carousel_Y;

	private int carousel_X1;
	private int carousel_X2;

	// Toggle Switch
	int toggle_width = 50;
	int toggle_height;
	int toggle_X = window_X + window_width - toggle_width - 30;

	int toggle_Y1;
	int toggle_Y2;

	// Drop Down Button
	private int drop_down_width;
	private int drop_down_X;
	private int drop_down_Y;

	// Song Buttons
	private int song_width;
	private int song_height;
	private int song_X;

	private int[] song_Y = new int[songs.length];

	private static Color[] colors = new Color[5];
	private static Color[] theme_colors = new Color[2];
	private static Color[] diff_colors = new Color[2];
	private static Color[] toggle_colors = new Color[2];
	private static Color[] song_colors = new Color[songs.length];
	private static Color drop_down_color;

	public Settings(Game game, AudioPlayer audio, LoadGame load) {
		this.game = game;
		this.audio = audio;
		this.load = load;
		setting = SETTING.Controls;

		for (int i = 0; i < section_Y.length; i++)
			section_Y[i] = window_Y + 30 + 15 * (i + 1) + section_height * i;

		reset_colors();
	}

	public static void reset_colors() {
		Arrays.fill(theme_colors, darkMode ? Color.lightGray : Color.darkGray);
		Arrays.fill(diff_colors, darkMode ? Color.lightGray : Color.darkGray);
		Arrays.fill(song_colors, darkMode ? Color.lightGray : Color.darkGray);

		drop_down_color = darkMode ? drop_down ? new Color(80, 80, 80) : new Color(30, 30, 30)
				: drop_down ? new Color(140, 140, 140) : new Color(230, 230, 230);

		toggle_colors = new Color[] {
				darkMode ? music ? new Color(0, 180, 0) : new Color(80, 80, 80)
						: music ? new Color(0, 170, 0) : new Color(150, 150, 150),

				darkMode ? sound ? new Color(0, 180, 0) : new Color(80, 80, 80)
						: sound ? new Color(0, 170, 0) : new Color(150, 150, 150)
		};

		colors[0] = darkMode ? new Color(160, 160, 160) : new Color(130, 130, 130);
		for (int i = 1; i <= 4; i++) {
			if (i == all_settings.indexOf(setting) + 1)
				colors[i] = darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
			else
				colors[i] = darkMode ? new Color(30, 30, 30) : new Color(230, 230, 230);
		}
	}

	private Map<String, Map<String, Integer>> setupTextDimensions() {
		return new HashMap<>() {
			{
				put("Settings", Utils.get_text_dimensions("Settings", Game.menuFont5));
				put("Back", Utils.get_text_dimensions("Back", Game.menuFont5));
				put("Controls", Utils.get_text_dimensions("Controls", Game.menuFont3));
				put("Audio", Utils.get_text_dimensions("Audio", Game.menuFont3));
				put("Theme", Utils.get_text_dimensions("Theme", Game.menuFont3));
				put("Difficulty", Utils.get_text_dimensions("Difficulty", Game.menuFont3));
				put("Player", Utils.get_text_dimensions("Player 1", Game.menuFont4));
				put("Move", Utils.get_text_dimensions("Move", Game.menuFont5));
				put("W", Utils.get_text_dimensions("W", Game.menuFont5));
				put("A", Utils.get_text_dimensions("A", Game.menuFont5));
				put("S", Utils.get_text_dimensions("S", Game.menuFont5));
				put("D", Utils.get_text_dimensions("D", Game.menuFont5));
				put("Up", Utils.get_text_dimensions("↑", Game.menuFont5));
				put("Down", Utils.get_text_dimensions("↓", Game.menuFont5));
				put("Left", Utils.get_text_dimensions("←", Game.menuFont5));
				put("Right", Utils.get_text_dimensions("→", Game.menuFont5));
				put("ESC", Utils.get_text_dimensions("ESC", Game.menuFont5));
				put("Theme", Utils.get_text_dimensions("Theme", Game.menuFont4));
				put("Difficulty", Utils.get_text_dimensions("Difficulty", Game.menuFont4));
				put("Music", Utils.get_text_dimensions("Music", Game.menuFont4));
				put("▶", Utils.get_text_dimensions("▶", Game.menuFont4));
				put("◀", Utils.get_text_dimensions("◀", Game.menuFont4));
				put("▼", Utils.get_text_dimensions("▼", Game.menuFont5));
				put("Dark", Utils.get_text_dimensions("Dark", Game.menuFont4));
				put("Light", Utils.get_text_dimensions("Light", Game.menuFont4));
				put("Normal", Utils.get_text_dimensions("Normal", Game.menuFont4));
				put("Hard", Utils.get_text_dimensions("Hard", Game.menuFont4));
				put(songs[0], Utils.get_text_dimensions(songs[0], Game.menuFont4));
				put(songs[1], Utils.get_text_dimensions(songs[1], Game.menuFont4));
				put(songs[2], Utils.get_text_dimensions(songs[2], Game.menuFont4));
			}
		};
	}

	private void exit_helper() {
		this.audio.playMenuSound("app/res/button4.wav", 0.27);
		colors = new Color[] {
				Settings.darkMode ? new Color(160, 160, 160) : new Color(130, 130, 130),
				Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140),
				Settings.darkMode ? new Color(30, 30, 30) : new Color(230, 230, 230),
				Settings.darkMode ? new Color(30, 30, 30) : new Color(230, 230, 230),
				Settings.darkMode ? new Color(30, 30, 30) : new Color(230, 230, 230),
		};

		this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setting = SETTING.Controls;
		Game.gameState = STATE.Menu;
	}

	private void section_helper(SETTING new_setting, int color_num) {
		this.audio.playMenuSound("app/res/button4.wav", 0.27);
		colors[all_settings.indexOf(setting) + 1] = Settings.darkMode ? new Color(30, 30, 30)
				: new Color(230, 230, 230);
		setting = new_setting;
		colors[color_num] = Settings.darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
	}

	private void theme_helper(boolean theme) {
		this.audio.playMenuSound("app/res/button4.wav", 0.27);
		this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		darkMode = theme;

		Settings.reset_colors();
		Menu.reset_colors();
		LoadGame.reset_colors();
		About.reset_colors();
		Shop.reset_colors();
		Paused.reset_colors();
		EndScreen.reset_colors();

		if (load.user > 0)
			load.save(load.save_files.get(load.user), null);
	}

	private void difficulty_helper(int difficulty) {
		this.audio.playMenuSound("app/res/button4.wav", 0.27);
		this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		Settings.difficulty = difficulty;
		Arrays.fill(diff_colors, darkMode ? Color.lightGray : Color.darkGray);

		if (load.user > 0)
			load.save(load.save_files.get(load.user), null);
	}

	private void audio_helper(boolean is_music) {
		if (is_music) {
			music = !music;
			toggle_colors[0] = darkMode ? music ? new Color(0, 180, 0) : new Color(80, 80, 80)
					: music ? new Color(0, 170, 0) : new Color(150, 150, 150);
			if (!music)
				audio.stopMusic();
			else
				audio.playGameSound(song_files[song_index], 1.122);

		} else {
			sound = !sound;
			toggle_colors[1] = darkMode ? sound ? new Color(0, 180, 0) : new Color(80, 80, 80)
					: sound ? new Color(0, 170, 0) : new Color(150, 150, 150);
		}

		this.audio.playMenuSound("app/res/button4.wav", 0.27);

		if (load.user > 0)
			load.save(load.save_files.get(load.user), null);
	}

	private void song_helper(int new_index) {
		this.audio.playMenuSound("app/res/button4.wav", 0.27);
		drop_down = false;
		this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		Arrays.fill(song_colors, Settings.darkMode ? Color.lightGray : Color.darkGray);

		if (song_index != new_index) {
			audio.stopMusic();
			audio.playGameSound(song_files[new_index], 1.122);
		}

		song_index = new_index;

		if (load.user > 0)
			load.save(load.save_files.get(load.user), null);
	}

	public void mousePressed(MouseEvent e) {
		if (Game.gameState != STATE.Settings)
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

			// Controls
			else if (Utils.mouse_over(mx, my, section_X, section_Y[0], section_width, section_height)) {
				section_helper(SETTING.Controls, 1);
			}

			// Audio
			else if (Utils.mouse_over(mx, my, section_X, section_Y[1], section_width, section_height)) {
				section_helper(SETTING.Audio, 2);
			}

			// Theme
			else if (Utils.mouse_over(mx, my, section_X, section_Y[2], section_width, section_height)) {
				section_helper(SETTING.Theme, 3);
			}

			// Difficulty
			else if (Utils.mouse_over(mx, my, section_X, section_Y[3], section_width, section_height)) {
				section_helper(SETTING.Difficulty, 4);
			}

			// Light Mode
			else if (setting == SETTING.Theme && darkMode
					&& Utils.mouse_over(mx, my, carousel_X1, carousel_Y, carousel_width, carousel_height)) {
				theme_helper(false);
			}

			// Dark Mode
			else if (setting == SETTING.Theme && !darkMode
					&& Utils.mouse_over(mx, my, carousel_X2, carousel_Y, carousel_width, carousel_height)) {
				theme_helper(true);
			}

			// Hard
			else if (setting == SETTING.Difficulty && Settings.difficulty == 0
					&& Utils.mouse_over(mx, my, carousel_X1, carousel_Y, carousel_width, carousel_height)) {
				difficulty_helper(1);
			}

			// Normal
			else if (setting == SETTING.Difficulty && Settings.difficulty == 1
					&& Utils.mouse_over(mx, my, carousel_X2, carousel_Y, carousel_width, carousel_height)) {
				difficulty_helper(0);
			}

			// Music
			else if (setting == SETTING.Audio
					&& Utils.mouse_over(mx, my, toggle_X, toggle_Y1, toggle_width, toggle_height)) {
				audio_helper(true);
			}

			// SFX
			else if (setting == SETTING.Audio
					&& Utils.mouse_over(mx, my, toggle_X, toggle_Y2, toggle_width, toggle_height)) {
				audio_helper(false);
			}

			// Open drop down menu
			else if (!drop_down && setting == SETTING.Audio
					&& Utils.mouse_over(mx, my, drop_down_X, drop_down_Y, drop_down_width, drop_down_width)) {
				drop_down_color = darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
				drop_down = true;
			}

			// Close drop down menu
			else if (drop_down && setting == SETTING.Audio
					&& Utils.mouse_over(mx, my, drop_down_X, drop_down_Y, drop_down_width, drop_down_width)) {
				drop_down = false;
			}

			else if (setting == SETTING.Audio && drop_down
					&& !Utils.mouse_over(mx, my, song_X, song_Y[0], song_width, song_height)
					&& !Utils.mouse_over(mx, my, song_X, song_Y[1], song_width, song_height)
					&& !Utils.mouse_over(mx, my, song_X, song_Y[2], song_width, song_height)) {
				drop_down = false;
				drop_down_color = darkMode ? new Color(30, 30, 30) : new Color(230, 230, 230);
			}

			// Select song
			else if (setting == SETTING.Audio && drop_down
					&& Utils.mouse_over(mx, my, song_X, song_Y[0], song_width, song_height)) {
				song_helper(0);
			}

			else if (setting == SETTING.Audio && drop_down
					&& Utils.mouse_over(mx, my, song_X, song_Y[1], song_width, song_height)) {
				song_helper(1);
			}

			else if (setting == SETTING.Audio && drop_down
					&& Utils.mouse_over(mx, my, song_X, song_Y[2], song_width, song_height)) {
				song_helper(2);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		if (Game.gameState != STATE.Settings)
			return;

		int mx = e.getX();
		int my = e.getY();

		this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		for (int i = 0; i < 4; i++)
			if (Utils.mouse_over(mx, my, section_X, section_Y[i], section_width, section_height))
				colors[i + 1] = darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);

		// Back
		if (Utils.mouse_over(mx, my, window_X + 15, window_Y + 5, 58, 18)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[0] = darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
		}

		// Controls
		else if (Utils.mouse_over(mx, my, section_X, section_Y[0], section_width, section_height)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[1] = darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		// Audio
		else if (Utils.mouse_over(mx, my, section_X, section_Y[1], section_width, section_height)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[2] = darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		// Theme
		else if (Utils.mouse_over(mx, my, section_X, section_Y[2], section_width, section_height)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[3] = darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		// Difficulty
		else if (Utils.mouse_over(mx, my, section_X, section_Y[3], section_width, section_height)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[4] = darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		// Dark/Light Mode
		else if (Utils.mouse_over(mx, my, carousel_X1, carousel_Y, carousel_width, carousel_height) && darkMode
				&& setting == SETTING.Theme) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			theme_colors[0] = darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
		}

		else if (Utils.mouse_over(mx, my, carousel_X2, carousel_Y, carousel_width, carousel_height) && !darkMode
				&& setting == SETTING.Theme) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			theme_colors[1] = darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
		}

		// Normal/Hard Mode
		else if (Utils.mouse_over(mx, my, carousel_X1, carousel_Y, carousel_width, carousel_height)
				&& Settings.difficulty == 0 && setting == SETTING.Difficulty) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			diff_colors[0] = darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
		}

		else if (Utils.mouse_over(mx, my, carousel_X2, carousel_Y, carousel_width, carousel_height)
				&& Settings.difficulty == 1 && setting == SETTING.Difficulty) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			diff_colors[1] = darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
		}

		// Music/SFX
		else if (Utils.mouse_over(mx, my, toggle_X, toggle_Y1, toggle_width, toggle_height)
				&& setting == SETTING.Audio) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			toggle_colors[0] = darkMode ? music ? new Color(0, 210, 0) : new Color(110, 110, 110)
					: music ? new Color(0, 150, 0) : new Color(130, 130, 130);
		}

		else if (Utils.mouse_over(mx, my, toggle_X, toggle_Y2, toggle_width, toggle_height)
				&& setting == SETTING.Audio) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			toggle_colors[1] = darkMode ? sound ? new Color(0, 210, 0) : new Color(110, 110, 110)
					: sound ? new Color(0, 140, 0) : new Color(120, 120, 120);
		}

		else if (Utils.mouse_over(mx, my, drop_down_X, drop_down_Y, drop_down_width, drop_down_width)
				&& setting == SETTING.Audio) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			drop_down_color = darkMode ? new Color(80, 80, 80) : new Color(140, 140, 140);
		}

		else if (Utils.mouse_over(mx, my, song_X, song_Y[0], song_width, song_height) && drop_down
				&& setting == SETTING.Audio) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			Arrays.fill(song_colors, Settings.darkMode ? Color.lightGray : Color.darkGray);
			song_colors[0] = darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
		}

		else if (Utils.mouse_over(mx, my, song_X, song_Y[1], song_width, song_height) && drop_down
				&& setting == SETTING.Audio) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			Arrays.fill(song_colors, Settings.darkMode ? Color.lightGray : Color.darkGray);
			song_colors[1] = darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
		}

		else if (Utils.mouse_over(mx, my, song_X, song_Y[2], song_width, song_height) && drop_down
				&& setting == SETTING.Audio) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			Arrays.fill(song_colors, Settings.darkMode ? Color.lightGray : Color.darkGray);
			song_colors[2] = darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
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
		if (Game.gameState != STATE.Settings)
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
		if (setting != SETTING.Audio)
			drop_down = false;

		Utils.clamp(timer, 0, 15);
		timer--;
	}

	public void render(Graphics2D g) {
		if (Game.gameState != STATE.Settings)
			return;

		Map<String, Map<String, Integer>> dims = setupTextDimensions();
		Composite original = g.getComposite();

		Utils.render_menu(g, load.user, load.saves, true);

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
		g.setComposite(original);
		g.setFont(Game.menuFont5);
		g.setColor(Settings.darkMode ? Color.lightGray : Color.darkGray);
		Map<String, Integer> about_dims = dims.get("Settings");
		g.drawString("Settings", window_X + (window_width - about_dims.get("width")) / 2,
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
		String[] sections = new String[] { "Controls", "Audio", "Theme", "Difficulty" };
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

		int content_X = window_X + window_width / 3;
		switch (setting) {
			// Controls
			case Controls:
				// Players
				g.setFont(Game.menuFont4);
				g.setColor(Settings.darkMode ? Color.lightGray : Color.darkGray);
				Map<String, Integer> player_dims = dims.get("Player");
				int player_Y = window_Y + 45 + player_dims.get("height");
				int p1_X = content_X + content_X / 3 - player_dims.get("width") / 2;
				int p2_X = content_X + 2 * content_X / 3 - player_dims.get("width") / 2;
				g.drawString("Player 1", p1_X, player_Y);
				g.drawString("Player 2", p2_X, player_Y);

				// Buttons
				int button_width = 40;
				int arc = 10;
				Function<Integer, Integer> get_button_X = x -> x + (player_dims.get("width") - button_width) / 2;
				Function<Integer, Integer> get_button_Y = i -> player_Y + 25 * i + button_width * (i - 1);
				BiFunction<Integer, String, Integer> center_X = (x, word) -> get_button_X.apply(x)
						+ (button_width - dims.get(word).get("width")) / 2;
				BiFunction<Integer, String, Integer> center_Y = (i, word) -> get_button_Y.apply(i)
						+ (button_width - dims.get(word).get("height")) / 2 + dims.get(word).get("ascent");

				for (int i = 1; i <= 5; i++) {
					g.setColor(Settings.darkMode ? Color.lightGray : Color.darkGray);
					g.fillRoundRect(get_button_X.apply(p1_X), get_button_Y.apply(i), button_width, button_width,
							arc, arc);

					if (i != 5)
						g.fillRoundRect(get_button_X.apply(p2_X), get_button_Y.apply(i), button_width, button_width,
								arc, arc);

					g.setColor(Color.black);
					g.drawRoundRect(get_button_X.apply(p1_X), get_button_Y.apply(i), button_width, button_width,
							arc, arc);
					if (i != 5)
						g.drawRoundRect(get_button_X.apply(p2_X), get_button_Y.apply(i), button_width, button_width,
								arc, arc);
				}

				g.setColor(Settings.darkMode ? Color.darkGray : Color.lightGray);
				g.setFont(Game.menuFont5);
				g.drawString("W", center_X.apply(p1_X, "W"), center_Y.apply(1, "W"));
				g.drawString("A", center_X.apply(p1_X, "A"), center_Y.apply(2, "A"));
				g.drawString("S", center_X.apply(p1_X, "S"), center_Y.apply(3, "S"));
				g.drawString("D", center_X.apply(p1_X, "D"), center_Y.apply(4, "D"));
				g.drawString("ESC", center_X.apply(p1_X, "ESC"), center_Y.apply(5, "ESC"));

				g.drawString("↑", center_X.apply(p2_X, "Up"), center_Y.apply(1, "Up"));
				g.drawString("↓", center_X.apply(p2_X, "Down"), center_Y.apply(2, "Down"));
				g.drawString("←", center_X.apply(p2_X, "Left"), center_Y.apply(3, "Left"));
				g.drawString("→", center_X.apply(p2_X, "Right"), center_Y.apply(4, "Right"));

				// Actions
				g.setColor(Settings.darkMode ? Color.lightGray : Color.darkGray);
				Map<String, Integer> move_dims = dims.get("Move");
				Function<Integer, Integer> get_action_Y = i -> get_button_Y.apply(i)
						+ (button_width - move_dims.get("height")) / 2 + move_dims.get("ascent");
				g.drawString("Up", content_X + 30, get_action_Y.apply(1));
				g.drawString("Down", content_X + 30, get_action_Y.apply(2));
				g.drawString("Left", content_X + 30, get_action_Y.apply(3));
				g.drawString("Right", content_X + 30, get_action_Y.apply(4));
				g.drawString("Pause", content_X + 30, get_action_Y.apply(5));
				break;

			// Audio
			case Audio:
				// Options
				g.setColor(Settings.darkMode ? Color.lightGray : Color.darkGray);
				g.setFont(Game.menuFont4);
				Map<String, Integer> music_dims = dims.get("Music");
				int padding = 50;
				int music_Y = window_Y + 79 + music_dims.get("height");
				g.drawString("Music", content_X + 30, music_Y);
				g.drawString("SFX", content_X + 30, music_Y + music_dims.get("height") + padding);
				g.drawString("Song", content_X + 30, music_Y + (music_dims.get("height") + padding) * 2);

				// Music
				g.setColor(toggle_colors[0]);
				toggle_height = music_dims.get("height") + 6;
				toggle_Y1 = music_Y - toggle_height;
				g.fillRoundRect(toggle_X, toggle_Y1, toggle_width, toggle_height, 10, 10);
				g.setColor(darkMode ? new Color(100, 100, 100) : new Color(120, 120, 120));
				g.drawRoundRect(toggle_X, toggle_Y1, toggle_width, toggle_height, 10, 10);
				g.setComposite(original);
				g.setColor(new Color(220, 220, 220));
				if (music) {
					g.fillRoundRect(toggle_X + toggle_width - toggle_height + 3, toggle_Y1 + 3, toggle_height - 6,
							toggle_height - 6, 5, 5);
					g.setColor(Color.lightGray);
					g.drawRoundRect(toggle_X + toggle_width - toggle_height + 3, toggle_Y1 + 3, toggle_height - 6,
							toggle_height - 6, 5, 5);
				} else {
					g.fillRoundRect(toggle_X + 3, toggle_Y1 + 3, toggle_height - 6,
							toggle_height - 6, 5, 5);
					g.setColor(Color.lightGray);
					g.drawRoundRect(toggle_X + 3, toggle_Y1 + 3, toggle_height - 6,
							toggle_height - 6, 5, 5);
				}

				// SFX
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
				g.setColor(toggle_colors[1]);
				toggle_Y2 = music_Y + music_dims.get("height") + padding - toggle_height;
				g.fillRoundRect(toggle_X, toggle_Y2, toggle_width, toggle_height, 10, 10);
				g.setColor(darkMode ? new Color(100, 100, 100) : new Color(120, 120, 120));
				g.drawRoundRect(toggle_X, toggle_Y2, toggle_width, toggle_height, 10, 10);
				g.setComposite(original);
				g.setColor(new Color(220, 220, 220));
				if (sound) {
					g.fillRoundRect(toggle_X + toggle_width - toggle_height + 3, toggle_Y2 + 3, toggle_height - 6,
							toggle_height - 6, 5, 5);
					g.setColor(Color.lightGray);
					g.drawRoundRect(toggle_X + toggle_width - toggle_height + 3, toggle_Y2 + 3, toggle_height - 6,
							toggle_height - 6, 5, 5);
				} else {
					g.fillRoundRect(toggle_X + 3, toggle_Y2 + 3, toggle_height - 6,
							toggle_height - 6, 5, 5);
					g.setColor(Color.lightGray);
					g.drawRoundRect(toggle_X + 3, toggle_Y2 + 3, toggle_height - 6,
							toggle_height - 6, 5, 5);
				}

				// Song
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
				g.setColor(drop_down_color);
				drop_down_width = music_dims.get("height") + 14;
				drop_down_X = window_X + window_width - drop_down_width - 30;
				drop_down_Y = music_Y + (music_dims.get("height") + padding) * 2 - drop_down_width + 2;
				g.fillRoundRect(drop_down_X, drop_down_Y, drop_down_width, drop_down_width, 10, 10);
				g.setColor(Settings.darkMode ? new Color(25, 25, 25) : new Color(225, 225, 225));
				g.drawRoundRect(drop_down_X, drop_down_Y, drop_down_width, drop_down_width, 10, 10);

				int display_song_width = 250;
				int display_song_height = drop_down_width;
				int display_song_X = drop_down_X - display_song_width - 10;
				int display_song_Y = drop_down_Y;
				g.setColor(darkMode ? new Color(30, 30, 30) : new Color(230, 230, 230));
				g.fillRoundRect(display_song_X, display_song_Y, display_song_width, display_song_height, 10, 10);
				g.setColor(Settings.darkMode ? new Color(25, 25, 25) : new Color(225, 225, 225));
				g.drawRoundRect(display_song_X, display_song_Y, display_song_width, display_song_height, 10, 10);
				g.setColor(darkMode ? Color.lightGray : Color.darkGray);
				g.drawString(songs[song_index], display_song_X + 15,
						display_song_Y + (display_song_height - dims.get(songs[song_index]).get("height")) / 2
								+ dims.get(songs[song_index]).get("ascent"));

				g.setFont(Game.menuFont5);
				g.drawString("▼", drop_down_X + (drop_down_width - dims.get("▼").get("width")) / 2, drop_down_Y
						+ (drop_down_width - dims.get("▼").get("height")) / 2 + dims.get("▼").get("ascent"));

				if (drop_down) {
					int song_option_height = 32;
					g.setColor(darkMode ? new Color(30, 30, 30) : new Color(230, 230, 230));
					g.fillRoundRect(display_song_X, display_song_Y + display_song_height + 10, display_song_width,
							song_option_height * songs.length, 10, 10);
					g.setColor(Settings.darkMode ? new Color(25, 25, 25) : new Color(225, 225, 225));
					g.drawRoundRect(display_song_X, display_song_Y + display_song_height + 10, display_song_width,
							song_option_height * songs.length, 10, 10);

					for (int i = 1; i <= 2; i++) {
						g.setColor(darkMode ? new Color(70, 70, 70) : new Color(190, 190, 190));
						g.drawLine(display_song_X + 1,
								(display_song_Y + display_song_height + 10) + song_option_height * i,
								display_song_X + display_song_width - 2,
								(display_song_Y + display_song_height + 10) + song_option_height * i);
					}

					g.setFont(Game.menuFont4);
					for (int i = 0; i < songs.length; i++) {
						song_width = display_song_width;
						song_height = song_option_height;
						song_X = display_song_X;
						song_Y[i] = (display_song_Y + display_song_height + 10) + song_height * i;

						g.setColor(song_colors[i]);
						int song_button_y = (display_song_Y + display_song_height + 10) + song_option_height * i;
						song_button_y = song_button_y + (display_song_height - dims.get(songs[i]).get("height")) / 2
								+ dims.get(songs[i]).get("ascent");
						g.drawString(songs[i], display_song_X + 15, song_button_y + 2);
					}
				}
				break;

			// Theme
			case Theme:
				// Options
				g.setColor(darkMode ? Color.lightGray : Color.darkGray);
				g.setFont(Game.menuFont4);
				Map<String, Integer> theme_dims = dims.get("Theme");
				int theme_Y = window_Y + 79 + theme_dims.get("height");
				g.drawString("Theme", content_X + 30, theme_Y);

				// Toggles
				int theme_menu_width = 140;
				int theme_menu_height = theme_dims.get("height") + 14;
				int theme_menu_X = content_X + 2 * window_width / 3 - theme_menu_width - 30;
				int theme_menu_Y = window_Y + 71;
				g.setColor(darkMode ? new Color(30, 30, 30) : new Color(230, 230, 230));
				g.fillRoundRect(theme_menu_X, theme_menu_Y, theme_menu_width, theme_menu_height, 10, 10);
				g.setColor(Settings.darkMode ? new Color(25, 25, 25) : new Color(225, 225, 225));
				g.drawRoundRect(theme_menu_X, theme_menu_Y, theme_menu_width, theme_menu_height, 10, 10);

				carousel_width = 28;
				carousel_height = theme_menu_height;
				carousel_Y = theme_menu_Y;

				carousel_X1 = theme_menu_X + theme_menu_width - 28;
				carousel_X2 = theme_menu_X;

				int right_icon_X = theme_menu_X + theme_menu_width - dims.get("▶").get("width") - 7;
				int left_icon_X = theme_menu_X + 7;
				if (darkMode) {
					g.setColor(theme_colors[0]);
					g.drawString("▶", right_icon_X, theme_Y);
					g.setColor(new Color(80, 80, 80));
					g.drawString("◀", left_icon_X, theme_Y);
					g.setColor(darkMode ? Color.lightGray : Color.darkGray);
					g.drawString("Dark", theme_menu_X + (theme_menu_width - dims.get("Dark").get("width")) / 2,
							theme_Y);
				} else {
					g.setColor(new Color(170, 170, 170));
					g.drawString("▶", right_icon_X, theme_Y);
					g.setColor(theme_colors[1]);
					g.drawString("◀", left_icon_X, theme_Y);
					g.setColor(darkMode ? Color.lightGray : Color.darkGray);
					g.drawString("Light", theme_menu_X + (theme_menu_width - dims.get("Light").get("width")) / 2,
							theme_Y);
				}
				break;

			// Difficulty
			case Difficulty:
				// Options
				g.setColor(darkMode ? Color.lightGray : Color.darkGray);
				g.setFont(Game.menuFont4);
				Map<String, Integer> diff_dims = dims.get("Difficulty");
				int diff_Y = window_Y + 79 + diff_dims.get("height");
				g.drawString("Difficulty", content_X + 30, diff_Y);

				// Toggles
				int diff_menu_width = 140;
				int diff_menu_height = diff_dims.get("height") + 14;
				int diff_menu_X = content_X + 2 * window_width / 3 - diff_menu_width - 30;
				int diff_menu_Y = window_Y + 71;
				g.setColor(darkMode ? new Color(30, 30, 30) : new Color(230, 230, 230));
				g.fillRoundRect(diff_menu_X, diff_menu_Y, diff_menu_width, diff_menu_height, 10, 10);
				g.setColor(Settings.darkMode ? new Color(25, 25, 25) : new Color(225, 225, 225));
				g.drawRoundRect(diff_menu_X, diff_menu_Y, diff_menu_width, diff_menu_height, 10, 10);

				carousel_width = 28;
				carousel_height = diff_menu_height;
				carousel_Y = diff_menu_Y;

				carousel_X1 = diff_menu_X + diff_menu_width - 28;
				carousel_X2 = diff_menu_X;

				right_icon_X = diff_menu_X + diff_menu_width - dims.get("▶").get("width") - 7;
				left_icon_X = diff_menu_X + 7;
				if (difficulty == 0) {
					g.setColor(diff_colors[0]);
					g.drawString("▶", right_icon_X, diff_Y);
					g.setColor(darkMode ? new Color(80, 80, 80) : new Color(170, 170, 170));
					g.drawString("◀", left_icon_X, diff_Y);
					g.setColor(darkMode ? Color.lightGray : Color.darkGray);
					g.drawString("Normal", diff_menu_X + (diff_menu_width - dims.get("Normal").get("width")) / 2,
							diff_Y);
				} else {
					g.setColor(darkMode ? new Color(80, 80, 80) : new Color(170, 170, 170));
					g.drawString("▶", right_icon_X, diff_Y);
					g.setColor(diff_colors[1]);
					g.drawString("◀", left_icon_X, diff_Y);
					g.setColor(darkMode ? Color.lightGray : Color.darkGray);
					g.drawString("Hard", diff_menu_X + (diff_menu_width - dims.get("Hard").get("width")) / 2,
							diff_Y);
				}
				break;
		}
	}
}
