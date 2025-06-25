package com.J2;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class About extends MouseAdapter implements KeyListener {

	private Desktop desktop;
	private AudioPlayer audio;
	private Game game;
	private LoadGame load;

	private int x_offset = 17;
	private int y_offset = 47;

	// Window
	private int window_width = (int) (Game.WIDTH * 0.6);
	private int window_height = (int) (Game.HEIGHT * 0.7);
	private int window_X = (Game.WIDTH - x_offset - window_width) / 2;
	private int window_Y = (Game.HEIGHT - y_offset - window_height) / 2;

	public static int timer = 15;

	private static Color[] colors = new Color[3];

	public About(Game game, AudioPlayer audio, LoadGame load) {
		this.game = game;
		this.desktop = Desktop.getDesktop();
		this.audio = audio;
		this.load = load;

		reset_colors();
	}

	public static void reset_colors() {
		colors = new Color[] {
				Settings.darkMode ? new Color(160, 160, 160) : new Color(130, 130, 130),
				Settings.darkMode ? new Color(170, 210, 170) : new Color(95, 135, 95),
				Settings.darkMode ? new Color(170, 210, 170) : new Color(95, 135, 95)
		};
	}

	public Map<String, Map<String, Integer>> setupTextDimensions() {
		return new HashMap<>() {
			{
				put("About", Utils.get_text_dimensions("About", Game.menuFont5));
				put("Back", Utils.get_text_dimensions("Back", Game.menuFont5));
				put("Version", Utils.get_text_dimensions(Game.VERSION.substring(1), Game.menuFont));
				put("Dev", Utils.get_text_dimensions("Justin Lee", Game.menuFont));
				put("Email", Utils.get_text_dimensions("ju3tinlee@gmail.com", Game.titleFont2));
				put("GitHub", Utils.get_text_dimensions("musubi3", Game.titleFont2));
			}
		};
	}

	public void mousePressed(MouseEvent e) {
		if (Game.gameState != STATE.About)
			return;

		int mx = e.getX();
		int my = e.getY();
		int mouse = e.getButton();
		Boolean left_click = mouse == MouseEvent.BUTTON1;

		Map<String, Map<String, Integer>> dims = setupTextDimensions();
		Map<String, Integer> email_dims = dims.get("Email");
		Map<String, Integer> git_dims = dims.get("GitHub");

		if (left_click) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			// Back
			if (Utils.mouse_over(mx, my, window_X + 15, window_Y + 5, 58, 18)) {
				Game.gameState = STATE.Menu;
				colors[0] = Settings.darkMode ? new Color(160, 160, 160) : new Color(130, 130, 130);
				this.audio.playMenuSound("app/res/button4.wav", 0.27);
			}

			else if (!Utils.mouse_over(mx, my, window_X, window_Y, window_width, window_height) && timer <= 0) {
				Game.gameState = STATE.Menu;
				this.audio.playMenuSound("app/res/button4.wav", 0.27);
			}

			// Email
			else if (Utils.mouse_over(mx, my, window_X + window_width - email_dims.get("width") - 15,
					window_Y + 120 + email_dims.get("ascent") * 2, email_dims.get("width"),
					email_dims.get("height"))) {
				this.audio.playMenuSound("app/res/button4.wav", 0.27);
				try {
					this.desktop.browse(new URI("mailto:ju3tinlee@gmail.com"));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
				colors[1] = Settings.darkMode ? new Color(170, 170, 170) : new Color(95, 95, 95);
			}

			// GitHub
			else if (Utils.mouse_over(mx, my, window_X + window_width - git_dims.get("width") - 15,
					window_Y + 150 + email_dims.get("ascent") * 3, git_dims.get("width"), git_dims.get("height"))) {
				this.audio.playMenuSound("app/res/button4.wav", 0.27);
				try {
					this.desktop.browse(new URI("https://github.com/musubi3"));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
				colors[2] = Settings.darkMode ? new Color(170, 170, 170) : new Color(95, 95, 95);
			}
		}

	}

	public void mouseMoved(MouseEvent e) {
		if (Game.gameState != STATE.About)
			return;

		int mx = e.getX();
		int my = e.getY();

		Map<String, Map<String, Integer>> dims = setupTextDimensions();
		Map<String, Integer> email_dims = dims.get("Email");
		Map<String, Integer> git_dims = dims.get("GitHub");

		this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		// Back
		if (Utils.mouse_over(mx, my, window_X + 15, window_Y + 5, 58, 18)) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[0] = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
		}

		// Email
		else if (Utils.mouse_over(mx, my, window_X + window_width - email_dims.get("width") - 15,
				window_Y + 120 + email_dims.get("ascent") * 2, email_dims.get("width"), email_dims.get("height"))) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[1] = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
		}

		// GitHub
		else if (Utils.mouse_over(mx, my, window_X + window_width - git_dims.get("width") - 15,
				window_Y + 150 + git_dims.get("ascent") * 3, git_dims.get("width"), git_dims.get("height"))) {
			this.game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			colors[2] = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
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
		if (Game.gameState != STATE.About)
			return;

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Game.gameState = STATE.Menu;
			this.audio.playMenuSound("app/res/button4.wav", 0.27);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public void tick() {
		Utils.clamp(timer, 0, 15);
		timer--;
	}

	public void render(Graphics2D g) {
		if (Game.gameState != STATE.About)
			return;

		Map<String, Map<String, Integer>> dims = setupTextDimensions();
		Composite original = g.getComposite();

		Utils.render_menu(g, this.load.user, this.load.saves, true);

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
		Map<String, Integer> about_dims = dims.get("About");
		g.drawString("About", window_X + (window_width - about_dims.get("width")) / 2,
				window_Y + (30 - about_dims.get("height")) / 2 + about_dims.get("ascent"));

		// Back
		g.setColor(colors[0]);
		g.setFont(Game.menuFont5);
		g.drawRoundRect(window_X + 15, window_Y + 5, 58, 18, 10, 10);
		Map<String, Integer> back_dims = dims.get("Back");
		g.drawString("Back", window_X + 15 + (60 - back_dims.get("width")) / 2,
				window_Y + (30 - back_dims.get("height")) / 2 + back_dims.get("ascent"));

		// Text
		g.setFont(Game.menuFont);
		g.setColor(Settings.darkMode ? Color.lightGray : Color.darkGray);

		Map<String, Integer> ver_dims = dims.get("Version");
		g.drawString("Version", window_X + 15, window_Y + 60 + ver_dims.get("ascent"));
		g.drawString(Game.VERSION.substring(1), window_X + window_width - ver_dims.get("width") - 15,
				window_Y + 60 + ver_dims.get("ascent"));

		Map<String, Integer> dev_dims = dims.get("Dev");
		g.drawString("Developer", window_X + 15, window_Y + 90 + ver_dims.get("ascent") * 2);
		g.drawString("Justin Lee", window_X + window_width - dev_dims.get("width") - 15,
				window_Y + 90 + ver_dims.get("ascent") * 2);

		g.setFont(Game.titleFont2);
		g.setColor(Settings.darkMode ? new Color(170, 170, 170) : new Color(95, 95, 95));
		Map<String, Integer> email_dims = dims.get("Email");
		g.drawString("Email", window_X + 15, window_Y + 120 + email_dims.get("ascent") * 3);
		g.setColor(colors[1]);
		g.drawString("ju3tinlee@gmail.com", window_X + window_width - email_dims.get("width") - 15,
				window_Y + 120 + email_dims.get("ascent") * 3);

		g.setColor(Settings.darkMode ? new Color(170, 170, 170) : new Color(95, 95, 95));
		Map<String, Integer> git_dims = dims.get("GitHub");
		g.drawString("GitHub", window_X + 15, window_Y + 150 + email_dims.get("ascent") * 4);
		g.setColor(colors[2]);
		g.drawString("musubi3", window_X + window_width - git_dims.get("width") - 15,
				window_Y + 150 + email_dims.get("ascent") * 4);
	}
}
