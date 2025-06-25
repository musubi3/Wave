package com.J2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoadGame extends MouseAdapter implements KeyListener {

	public int user = 0;
	public ArrayList<String> saves = new ArrayList<>();
	public ArrayList<String> times = new ArrayList<>();
	public ArrayList<String> save_files = new ArrayList<>();
	public ArrayList<Integer> high_scores = new ArrayList<>();
	public ArrayList<Integer> coins = new ArrayList<>();
	public int timer = 25;
	public int player;
	public boolean newName = false;

	private boolean new_user = false;
	private boolean name_menu = false;
	private boolean show_cursor = true;
	private long last_toggle_time = System.currentTimeMillis();
	private int cursor_blink_interval = 500;
	private String input_name = "";
	private Integer input_user = null;
	private boolean[] right_click_menu = { false, false, false };
	private String[] save_paths = {
			System.getenv("APPDATA") + "\\Wave\\saves\\save1.txt",
			System.getenv("APPDATA") + "\\Wave\\saves\\save2.txt",
			System.getenv("APPDATA") + "\\Wave\\saves\\save3.txt"
	};
	private static Color[] save_colors = new Color[3];
	private static Color[] name_colors = new Color[3];
	private static Color[] del_colors = new Color[3];
	private static Color[] change_name_colors = new Color[2];
	private static Color load_color;

	private int x_offset = 17;
	private int y_offset = 47;

	// Load Game
	private int button_width5 = 110;
	private int button_height5 = 35;
	private int button_X5 = 60;
	private int button_Y5 = (int) (Game.HEIGHT * 0.75);

	// Save 3
	private int save_width3 = 280;
	private int save_height3 = 100;
	private int save_X3 = button_X5;
	private int save_Y3 = button_Y5 - save_height3 - 30;

	// Save 2
	private int save_width2 = save_width3;
	private int save_height2 = save_height3;
	private int save_X2 = save_X3;
	private int save_Y2 = save_Y3 - save_height2 - 30;

	// Save 1
	private int save_width1 = save_width3;
	private int save_height1 = save_height3;
	private int save_X1 = save_X3;
	private int save_Y1 = save_Y2 - save_height1 - 30;

	// Right Click Menu
	private int rc_width = 100;
	private int rc_height = 30;

	private int name_X = save_X1 + 25;
	private int del_X = save_X1 + save_width1 - rc_width - 25;

	private int rc_Y1 = save_Y1 + (save_height1 - 30) / 2;
	private int rc_Y2 = save_Y2 + (save_height2 - 30) / 2;
	private int rc_Y3 = save_Y3 + (save_height3 - 30) / 2;

	// Change Name Window
	private int window_width = (int) (Game.WIDTH * 0.5);
	private int window_height = (int) (Game.HEIGHT * 0.3);
	private int window_X = (Game.WIDTH - x_offset - window_width) / 2;
	private int window_Y = (Game.HEIGHT - y_offset - window_height) / 2;

	// Change Name Text Box
	private int tb_X = window_X + 30;
	private int tb_Y = window_Y + Utils.get_text_dimensions("New Name", Game.menuFont).get("height") + 30;
	private int tb_width = window_width - 60;
	private int tb_height = 45;

	// Change Name Buttons
	private int cn_width = (int) (window_width * 0.43);
	private int cn_height = (int) (window_height * 0.3);

	private int save_X = window_X + 30;
	private int cancel_X = window_X + window_width - cn_width - 30;

	private int cn_Y = window_Y + window_height - cn_height - 30;

	private AudioPlayer audio;
	private Handler handler;
	private Game game;

	public LoadGame(Game game, AudioPlayer audio, Handler handler) {
		this.game = game;
		this.audio = audio;
		this.handler = handler;
		for (int i = 0; i < 3; i++) {
			saves.add("");
			times.add("");
			high_scores.add(0);
			coins.add(0);
		}

		save_files.add("app/saves/base.txt");
		for (int i = 0; i < 3; i++)
			save_files.add(save_paths[i]);

		Path save_dir = Paths.get(System.getenv("APPDATA"), "Wave", "saves");
		if (!Files.isDirectory(save_dir)) {
			try {
				Files.createDirectories(save_dir);
				Path save_file = save_dir.resolve("last_user.txt");
				try (BufferedWriter bw = Files.newBufferedWriter(save_file)) {
					bw.write("last_user=0");
				}

				try (BufferedReader br = new BufferedReader(new FileReader(save_files.get(0)))) {
					StringBuilder base_save = new StringBuilder();
					String line;
					while ((line = br.readLine()) != null)
						base_save.append(line).append("\n");

					for (int i = 0; i < 3; i++) {
						save_file = save_dir.resolve("save" + (i + 1) + ".txt");
						try (BufferedWriter bw = Files.newBufferedWriter(save_file)) {
							bw.write(base_save.toString());
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.load_user();
		this.load(this.save_files.get(this.user), true);

		reset_colors();
	}

	public static void reset_colors() {
		Arrays.fill(save_colors, Settings.darkMode ? Color.lightGray : Color.darkGray);
		Arrays.fill(name_colors, Settings.darkMode ? Color.darkGray : Color.lightGray);
		Arrays.fill(del_colors, Settings.darkMode ? Color.darkGray : Color.lightGray);
		Arrays.fill(change_name_colors, Settings.darkMode ? Color.darkGray : Color.lightGray);
		load_color = Settings.darkMode ? Color.lightGray : Color.darkGray;
	}

	public Map<String, Map<String, Integer>> setupTextDimensions() {
		return new HashMap<>() {
			{
				put("Load", Utils.get_text_dimensions("Load Game", Game.menuFont6));
				put("Save1", Utils.get_text_dimensions(saves.get(2), Game.menuFont4));
				put("Save2", Utils.get_text_dimensions(saves.get(1), Game.menuFont4));
				put("Save3", Utils.get_text_dimensions(saves.get(0), Game.menuFont4));
				put("Empty", Utils.get_text_dimensions("EMPTY", Game.menuFont));
				put("Score", Utils.get_text_dimensions("High Score - ", Game.menuFont7));
				put("Coins", Utils.get_text_dimensions("Coins - ", Game.menuFont7));
				put("Change Name", Utils.get_text_dimensions("Change Name", Game.menuFont7));
				put("Del", Utils.get_text_dimensions("Delete", Game.menuFont7));
				put("New Name", Utils.get_text_dimensions("New Name", Game.menuFont));
				put("Name", Utils.get_text_dimensions("Name", Game.menuFont));
				put("Save", Utils.get_text_dimensions("Save", Game.menuFont));
				put("Cancel", Utils.get_text_dimensions("Cancel", Game.menuFont));
				put("Input", Utils.get_text_dimensions(input_name, Game.menuFont));
			}
		};
	}

	public void save_user() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(System.getenv("APPDATA") + "\\Wave\\saves\\last_user.txt"))) {
			bw.write("last_user=" + user);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load_user() {
		try (BufferedReader br = new BufferedReader(new FileReader(System.getenv("APPDATA") + "\\Wave\\saves\\last_user.txt"))) {
			String[] last_user = br.readLine().split("=");
			if (last_user.length > 1)
				user = Integer.parseInt(last_user[1]);
			br.close();

			int i = 0;
			for (String path : save_paths) {
				BufferedReader temp_br = new BufferedReader(new FileReader(path));
				String line;
				String name = "";
				String time = "";
				int high_score = 0;
				int money = 0;

				while ((line = temp_br.readLine()) != null) {
					if (line.startsWith("name=")) {
						String[] name_line = line.split("=");
						name = name_line.length > 1 ? name_line[1] : "";
					} else if (line.startsWith("time=")) {
						String[] time_line = line.split("=");
						time = time_line.length > 1 ? time_line[1] : "";
					} else if (line.startsWith("high_score="))
						high_score = Integer.parseInt(line.split("=")[1]);
					else if (line.startsWith("coins="))
						money = Integer.parseInt(line.split("=")[1]);
				}

				saves.set(i, name);
				times.set(i, time);
				high_scores.set(i, high_score);
				coins.set(i, money);

				temp_br.close();
				i++;
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public void save(String file, String name) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
			name = name == null ? saves.get(user - 1) : name;

			bw.write("name=" + name + "\n");
			bw.write("time=" + now.format(formatter) + "\n");
			bw.write("difficulty=" + Settings.difficulty + "\n");
			bw.write("music=" + Settings.music + "\n");
			bw.write("sound=" + Settings.sound + "\n");
			bw.write("dark_mode=" + Settings.darkMode + "\n");
			bw.write("high_score=" + HUD.high_score + "\n");
			bw.write("speed=" + Player.speed + "\n");
			bw.write("health=" + Shop.health_level + "\n");
			bw.write("coins=" + Shop.coins + "\n");
			bw.write("skin=" + Shop.skin);

			for (int i = 0; i < Shop.unlocked.length; i++)
				bw.write("\nskin" + (i + 1) + "_unlocked=" + Shop.unlocked[i]);

			bw.write("\nsong_index=" + Settings.song_index);

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load(String file, boolean changed_user) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			br.readLine(); // Name
			br.readLine(); // Time
			Settings.difficulty = Integer.parseInt(br.readLine().split("=")[1]);
			Settings.music = br.readLine().split("=")[1].equals("true");
			Settings.sound = br.readLine().split("=")[1].equals("true");
			Settings.darkMode = br.readLine().split("=")[1].equals("true");
			HUD.high_score = Integer.parseInt(br.readLine().split("=")[1]);
			Player.speed = Integer.parseInt(br.readLine().split("=")[1]);
			Shop.health_level = Integer.parseInt(br.readLine().split("=")[1]);
			Shop.coins = Integer.parseInt(br.readLine().split("=")[1]);
			Shop.skin = Integer.parseInt(br.readLine().split("=")[1]);

			for (int i = 0; i < 3; i++)
				Shop.unlocked[i] = br.readLine().split("=")[1].equals("true");

			Settings.song_index = Integer.parseInt(br.readLine().split("=")[1]);

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (changed_user) {
			audio.stopMusic();
			this.audio.playGameSound(Settings.song_files[Settings.song_index], 1.122);
		}
	}

	private boolean is_reset(int user_num) {
		try (BufferedReader br = new BufferedReader(new FileReader(save_files.get(user_num)))) {
			String[] user_name = br.readLine().split("=");
			br.close();
			return user_name.length == 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	private void reset_file(int user_num) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(save_files.get(user_num)))) {
			StringBuilder base_save = new StringBuilder();
			BufferedReader br = new BufferedReader(new FileReader(save_files.get(0)));
			String line;
			while ((line = br.readLine()) != null)
				base_save.append(line).append("\n");
			br.close();

			bw.write(base_save.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void shift_up() {
		try {
			for (int i = 1; i < save_files.size() - 1; i++) {
				if (is_reset(i)) {
					if (!is_reset(i + 1)) {
						Files.copy(Paths.get(save_files.get(i + 1)), Paths.get(save_files.get(i)),
								StandardCopyOption.REPLACE_EXISTING);
						reset_file(i + 1);

						if (i != 1)
							i--;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete_user(int user_num) {
		try (BufferedReader br = new BufferedReader(new FileReader(save_files.get(0)))) {
			StringBuilder base_save = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null)
				base_save.append(line).append("\n");
			br.close();

			BufferedWriter bw = new BufferedWriter(new FileWriter(save_files.get(user_num)));
			bw.write(base_save.toString());
			bw.close();

			shift_up();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void load_user_helper(int user_num, int prev_user) {
		user = user_num;
		if (prev_user == 0)
			handler.add_object(new MenuPlayer((int) ((Game.WIDTH - 17) * 0.794), (int) (Game.HEIGHT * 0.36) + 82,
					ID.MenuPlayer, handler, this));

		audio.playMenuSound("app/res/button4.wav", 0.27);
		load(save_files.get(user_num), true);
		save_user();
		Arrays.fill(save_colors, Settings.darkMode ? Color.lightGray : Color.darkGray);
		Game.gameState = STATE.Menu;
	}

	private void name_helper(int user_num, boolean is_new) {

		audio.playMenuSound("app/res/button4.wav", 0.27);

		new_user = is_new;
		if (new_user)
			user_num = saves.indexOf("");

		name_menu = true;
		show_cursor = true;
		input_user = user_num + 1;
		right_click_menu[user_num] = false;
	}

	private void save_name_helper() {
		audio.playMenuSound("app/res/button4.wav", 0.27);

		if (user != 0)
			load(save_files.get(input_user), false);
		save(save_files.get(input_user), input_name);

		saves.set(input_user - 1, input_name);
		saves.removeIf(String::isEmpty);
		while (saves.size() < 3)
			saves.add("");

		if (this.user == 0 || new_user) {
			if (this.user == 0)
				handler.add_object(new MenuPlayer((int) ((Game.WIDTH - 17) * 0.794), (int) (Game.HEIGHT * 0.36) + 82,
						ID.MenuPlayer, handler, this));

			this.user = input_user;
			save_user();

			load(save_files.get(this.user), true);

			Game.gameState = STATE.Menu;
		} else
			load(save_files.get(this.user), false);

		input_name = "";
		input_user = null;
		name_menu = false;
		show_cursor = false;
		this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		load_user();
	}

	private void delete_user_helper(int user_num) {
		audio.playMenuSound("app/res/button4.wav", 0.27);
		if (user_num == this.user) {
			this.user = 0;
			load(save_files.get(this.user), true);
			save_user();
		}

		delete_user(user_num);
		saves.set(user_num - 1, "");
		saves.removeIf(String::isEmpty);
		while (saves.size() < 3)
			saves.add("");
		high_scores.set(user_num - 1, 0);
		high_scores.removeIf(x -> x == 0);
		while (high_scores.size() < 3)
			high_scores.add(0);
		coins.set(user_num - 1, 0);
		coins.removeIf(x -> x == 0);
		while (coins.size() < 3)
			coins.add(0);
		right_click_menu[user_num - 1] = false;
		Arrays.fill(del_colors, Settings.darkMode ? Color.darkGray : Color.lightGray);

		Settings.reset_colors();
		Menu.reset_colors();
		LoadGame.reset_colors();
		About.reset_colors();
		Shop.reset_colors();
		Paused.reset_colors();
		EndScreen.reset_colors();
	}

	public void mousePressed(MouseEvent e) {
		if (Game.gameState != STATE.Load)
			return;

		int mx = e.getX();
		int my = e.getY();
		int mouse = e.getButton();
		Boolean left_click = mouse == MouseEvent.BUTTON1;
		Boolean right_click = mouse == MouseEvent.BUTTON3;

		if (left_click && timer <= 0) {
			if (!name_menu) {
				this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

				if (Utils.mouse_over(mx, my, save_X1, save_Y1, save_width1, save_height1) && !right_click_menu[0]) {
					if (!saves.get(0).isBlank()) {
						load_user_helper(1, user);
					} else {
						name_helper(0, true);
					}
				} else if (Utils.mouse_over(mx, my, save_X2, save_Y2, save_width2, save_height2)
						&& !right_click_menu[1]) {
					if (!saves.get(1).isBlank()) {
						load_user_helper(2, user);
					} else {
						name_helper(0, true);
					}
				} else if (Utils.mouse_over(mx, my, save_X3, save_Y3, save_width3, save_height3)
						&& !right_click_menu[2]) {
					if (!saves.get(2).isBlank()) {
						load_user_helper(3, user);
					} else {
						name_helper(0, true);
					}
				} else if (right_click_menu[0]) {
					if (Utils.mouse_over(mx, my, name_X, rc_Y1, rc_width, rc_height)) {
						name_helper(0, false);
					} else if (Utils.mouse_over(mx, my, del_X, rc_Y1, rc_width, rc_height)) {
						delete_user_helper(1);
					}
				} else if (right_click_menu[1]) {
					if (Utils.mouse_over(mx, my, name_X, rc_Y2, rc_width, rc_height)) {
						name_helper(1, false);
					} else if (Utils.mouse_over(mx, my, del_X, rc_Y2, rc_width, rc_height)) {
						delete_user_helper(2);
					}
				} else if (right_click_menu[2]) {
					if (Utils.mouse_over(mx, my, name_X, rc_Y3, rc_width, rc_height)) {
						name_helper(2, false);
					} else if (Utils.mouse_over(mx, my, del_X, rc_Y3, rc_width, rc_height)) {
						delete_user_helper(3);
					}
				}

				boolean not_over = !Utils.mouse_over(mx, my, save_X1, save_Y1, save_width1, save_height1)
						&& !Utils.mouse_over(mx, my, save_X2, save_Y2, save_width2, save_height2)
						&& !Utils.mouse_over(mx, my, save_X3, save_Y3, save_width3, save_height3);

				boolean not_over_rc = !Utils.mouse_over(mx, my, name_X, rc_Y1, rc_width, rc_height)
						&& !Utils.mouse_over(mx, my, del_X, rc_Y1, rc_width, rc_height)
						&& !Utils.mouse_over(mx, my, name_X, rc_Y2, rc_width, rc_height)
						&& !Utils.mouse_over(mx, my, del_X, rc_Y2, rc_width, rc_height)
						&& !Utils.mouse_over(mx, my, name_X, rc_Y3, rc_width, rc_height)
						&& !Utils.mouse_over(mx, my, del_X, rc_Y3, rc_width, rc_height);

				if (not_over && !right_click_menu[0] && !right_click_menu[1]
						&& !right_click_menu[2]) {

					audio.playMenuSound("app/res/button4.wav", 0.27);
					Arrays.fill(right_click_menu, false);
					Game.gameState = STATE.Menu;
				} else if (not_over_rc && (right_click_menu[0] || right_click_menu[1] || right_click_menu[2]))
					Arrays.fill(right_click_menu, false);
			} else {
				if (!Utils.mouse_over(mx, my, window_X, window_Y, window_width, window_height)) {

					audio.playMenuSound("app/res/button4.wav", 0.27);
					this.game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					name_menu = false;
					show_cursor = false;
				} else if (Utils.mouse_over(mx, my, save_X, cn_Y, cn_width, cn_height) && input_name.length() > 0) {
					save_name_helper();
				} else if (Utils.mouse_over(mx, my, cancel_X, cn_Y, cn_width, cn_height)) {

					audio.playMenuSound("app/res/button4.wav", 0.27);
					input_name = "";
					input_user = null;
					name_menu = false;
					show_cursor = false;
				}
			}
		} else if (right_click) {
			if (Utils.mouse_over(mx, my, save_X1, save_Y1, save_width1, save_height1)) {
				if (!saves.get(0).isBlank()) {
					right_click_menu = new boolean[] { true, false, false };
					save_colors[0] = Settings.darkMode ? Color.lightGray : Color.darkGray;
					game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			} else if (Utils.mouse_over(mx, my, save_X2, save_Y2, save_width2, save_height2)) {
				if (!saves.get(1).isBlank()) {
					right_click_menu = new boolean[] { false, true, false };
					save_colors[1] = Settings.darkMode ? Color.lightGray : Color.darkGray;
					game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			} else if (Utils.mouse_over(mx, my, save_X3, save_Y3, save_width3, save_height3)) {
				if (!saves.get(2).isBlank()) {
					right_click_menu = new boolean[] { false, false, true };
					save_colors[2] = Settings.darkMode ? Color.lightGray : Color.darkGray;
					game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		if (Game.gameState != STATE.Load)
			return;

		int mx = e.getX();
		int my = e.getY();

		game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		if (Utils.mouse_over(mx, my, save_X1, save_Y1, save_width1, save_height1) && !right_click_menu[0]
				&& !name_menu) {
			save_colors[0] = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (Utils.mouse_over(mx, my, save_X2, save_Y2, save_width2, save_height2) && !right_click_menu[1]
				&& !name_menu) {
			save_colors[1] = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (Utils.mouse_over(mx, my, save_X3, save_Y3, save_width3, save_height3) && !right_click_menu[2]
				&& !name_menu) {
			save_colors[2] = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (Utils.mouse_over(mx, my, button_X5, button_Y5, button_width5, button_height5) && !name_menu) {
			load_color = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (Utils.mouse_over(mx, my, name_X, rc_Y1, rc_width, rc_height) && right_click_menu[0]) {
			name_colors[0] = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (Utils.mouse_over(mx, my, del_X, rc_Y1, rc_width, rc_height) && right_click_menu[0]) {
			del_colors[0] = Settings.darkMode ? new Color(180, 0, 0) : new Color(170, 0, 0);
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (Utils.mouse_over(mx, my, name_X, rc_Y2, rc_width, rc_height) && right_click_menu[1]) {
			name_colors[1] = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (Utils.mouse_over(mx, my, del_X, rc_Y2, rc_width, rc_height) && right_click_menu[1]) {
			del_colors[1] = Settings.darkMode ? new Color(180, 0, 0) : new Color(170, 0, 0);
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (Utils.mouse_over(mx, my, name_X, rc_Y3, rc_width, rc_height) && right_click_menu[2]) {
			name_colors[2] = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (Utils.mouse_over(mx, my, del_X, rc_Y3, rc_width, rc_height) && right_click_menu[2]) {
			del_colors[2] = Settings.darkMode ? new Color(180, 0, 0) : new Color(170, 0, 0);
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (Utils.mouse_over(mx, my, save_X, cn_Y, cn_width, cn_height) && name_menu
				&& input_name.length() == 0) {
			change_name_colors[0] = Settings.darkMode ? new Color(180, 0, 0) : new Color(170, 0, 0);
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (Utils.mouse_over(mx, my, save_X, cn_Y, cn_width, cn_height) && name_menu) {
			change_name_colors[0] = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (Utils.mouse_over(mx, my, cancel_X, cn_Y, cn_width, cn_height) && name_menu) {
			change_name_colors[1] = Settings.darkMode ? new Color(180, 0, 0) : new Color(170, 0, 0);
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
			reset_colors();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (Game.gameState != STATE.Load || !name_menu)
			return;

		char c = e.getKeyChar();
		if (Character.isLetterOrDigit(c) || c == ' ') {
			if (input_name.length() < 16) { // max length
				input_name += c;
				if (change_name_colors[0].getRed() >= 170 && change_name_colors[0].getGreen() == 0)
					change_name_colors[0] = Settings.darkMode ? new Color(0, 180, 0) : new Color(0, 170, 0);
			}
		} else if (c == '\b' && input_name.length() > 0) { // backspace
			input_name = input_name.substring(0, input_name.length() - 1);
			if (input_name.length() == 0 && change_name_colors[0].getGreen() >= 170
					&& change_name_colors[0].getRed() == 0)
				change_name_colors[0] = Settings.darkMode ? new Color(180, 0, 0) : new Color(170, 0, 0);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (Game.gameState != STATE.Load)
			return;

		if (!name_menu) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				Arrays.fill(right_click_menu, false);
				Game.gameState = STATE.Menu;

				audio.playMenuSound("app/res/button4.wav", 0.27);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER && input_name.length() > 0 && input_user != null) {
			save_name_helper();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

			audio.playMenuSound("app/res/button4.wav", 0.27);
			name_menu = false;
			show_cursor = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public void tick() {
		if (name_menu) {
			if (System.currentTimeMillis() - last_toggle_time > cursor_blink_interval) {
				show_cursor = !show_cursor;
				last_toggle_time = System.currentTimeMillis();
			}
		}

		Utils.clamp(timer, 0, 25);
		timer--;
	}

	public void render(Graphics2D g) {
		if (Game.gameState != STATE.Load)
			return;

		Map<String, Map<String, Integer>> dims = setupTextDimensions();
		Composite original = g.getComposite();
		Color color;
		int Y;

		Utils.render_menu(g, user, saves, false);

		// Darken Background
		if (!name_menu) {
			g.setColor(new Color(0, 0, 0, (int) (255 * 0.8)));
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		}

		// Load Game
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
		color = Settings.darkMode ? new Color(30, 30, 30) : new Color(235, 235, 235);
		g.setColor(color);
		g.fillRoundRect(button_X5, button_Y5, button_width5, button_height5, 20, 20);
		g.setComposite(original);
		g.setFont(Game.menuFont6);
		g.setColor(load_color);
		g.drawRoundRect(button_X5, button_Y5, button_width5, button_height5, 20, 20);
		Map<String, Integer> load_dims = dims.get("Load");
		g.drawString("Load Game", button_X5 + (button_width5 - load_dims.get("width")) / 2,
				button_Y5 + (button_height5 - load_dims.get("height")) / 2 + load_dims.get("ascent"));

		Map<String, Integer> score_dims = dims.get("Score");
		Map<String, Integer> coin_dims = dims.get("Coins");
		Map<String, Integer> name_dims = dims.get("Change Name");
		Map<String, Integer> del_dims = dims.get("Del");

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		color = Settings.darkMode ? Color.darkGray : new Color(235, 235, 235);
		g.setColor(color);
		g.fillRoundRect(save_X1, save_Y1, save_width1, save_height1, 20, 20);
		g.setColor(save_colors[0]);
		g.drawRoundRect(save_X1, save_Y1, save_width1, save_height1, 20, 20);
		if (!right_click_menu[0]) {
			Map<String, Integer> sav1_dims;
			g.setComposite(original);
			if (saves.get(0).isBlank()) {
				sav1_dims = dims.get("Empty");
				g.setFont(Game.menuFont);
				g.drawString("EMPTY", save_X1 + 15,
						save_Y1 + (save_height1 - sav1_dims.get("height")) / 2 + sav1_dims.get("ascent"));
			} else {
				g.setFont(Game.menuFont4);
				sav1_dims = dims.get("Save1");
				Y = save_Y1 + sav1_dims.get("height") + 15;
				g.drawString(saves.get(0), save_X1 + 15, Y);
				g.setFont(Game.menuFont7);
				g.drawString("High Score - " + high_scores.get(0), save_X1 + 15, Y + score_dims.get("height") + 10);
				g.drawString("Coins - " + coins.get(0), save_X1 + 15, Y + coin_dims.get("height") * 2 + 20);
				g.drawString(times.get(0), save_X1 + 15, Y + score_dims.get("height") * 3 + 30);
			}
		} else {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			color = Settings.darkMode ? Color.lightGray : Color.darkGray;
			g.setColor(color);
			g.fillRoundRect(name_X, rc_Y1, rc_width, rc_height, 15, 15);
			g.fillRoundRect(del_X, rc_Y1, rc_width, rc_height, 15, 15);
			g.setFont(Game.menuFont7);

			g.setColor(name_colors[0]);
			g.drawRoundRect(name_X, rc_Y1, rc_width, rc_height, 15, 15);
			g.drawString("Change Name", name_X + (rc_width - name_dims.get("width")) / 2,
					rc_Y1 + (rc_height - name_dims.get("height")) / 2 + name_dims.get("ascent"));

			g.setColor(del_colors[0]);
			g.drawRoundRect(del_X, rc_Y1, rc_width, rc_height, 15, 15);
			g.drawString("Delete", del_X + (rc_width - del_dims.get("width")) / 2,
					rc_Y1 + (rc_height - del_dims.get("height")) / 2 + del_dims.get("ascent"));
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		color = Settings.darkMode ? Color.darkGray : new Color(235, 235, 235);
		g.setColor(color);
		g.fillRoundRect(save_X2, save_Y2, save_width2, save_height2, 20, 20);
		g.setColor(save_colors[1]);
		g.drawRoundRect(save_X2, save_Y2, save_width2, save_height2, 20, 20);
		if (!right_click_menu[1]) {
			Map<String, Integer> sav2_dims;
			g.setComposite(original);
			if (saves.get(1).isBlank()) {
				sav2_dims = dims.get("Empty");
				g.setFont(Game.menuFont);
				g.drawString("EMPTY", save_X2 + 15,
						save_Y2 + (save_height2 - sav2_dims.get("height")) / 2 + sav2_dims.get("ascent"));
			} else {
				g.setFont(Game.menuFont4);
				sav2_dims = dims.get("Save2");
				Y = save_Y2 + sav2_dims.get("height") + 15;
				g.drawString(saves.get(1), save_X2 + 15, Y);
				g.setFont(Game.menuFont7);
				g.drawString("High Score - " + high_scores.get(1), save_X2 + 15, Y + score_dims.get("height") + 10);
				g.drawString("Coins - " + coins.get(1), save_X2 + 15, Y + coin_dims.get("height") * 2 + 20);
				g.drawString(times.get(1), save_X2 + 15, Y + score_dims.get("height") * 3 + 30);
			}
		} else {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			color = Settings.darkMode ? Color.lightGray : Color.darkGray;
			g.setColor(color);
			g.fillRoundRect(name_X, rc_Y2, rc_width, rc_height, 15, 15);
			g.fillRoundRect(del_X, rc_Y2, rc_width, rc_height, 15, 15);
			g.setFont(Game.menuFont7);

			g.setColor(name_colors[1]);
			g.drawRoundRect(name_X, rc_Y2, rc_width, rc_height, 15, 15);
			g.drawString("Change Name", name_X + (rc_width - name_dims.get("width")) / 2,
					rc_Y2 + (rc_height - name_dims.get("height")) / 2 + name_dims.get("ascent"));

			g.setColor(del_colors[1]);
			g.drawRoundRect(del_X, rc_Y2, rc_width, rc_height, 15, 15);
			g.drawString("Delete", del_X + (rc_width - del_dims.get("width")) / 2,
					rc_Y2 + (rc_height - del_dims.get("height")) / 2 + del_dims.get("ascent"));
		}

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		color = Settings.darkMode ? Color.darkGray : new Color(235, 235, 235);
		g.setColor(color);
		g.fillRoundRect(save_X3, save_Y3, save_width3, save_height3, 20, 20);
		g.setColor(save_colors[2]);
		g.drawRoundRect(save_X3, save_Y3, save_width3, save_height3, 20, 20);
		if (!right_click_menu[2]) {
			Map<String, Integer> sav3_dims;
			g.setComposite(original);
			if (saves.get(2).isBlank()) {
				sav3_dims = dims.get("Empty");
				g.setFont(Game.menuFont);
				g.drawString("EMPTY", save_X3 + 15,
						save_Y3 + (save_height3 - sav3_dims.get("height")) / 2 + sav3_dims.get("ascent"));
			} else {
				g.setFont(Game.menuFont4);
				sav3_dims = dims.get("Save3");
				Y = save_Y3 + sav3_dims.get("height") + 15;
				g.drawString(saves.get(2), save_X3 + 15, Y);
				g.setFont(Game.menuFont7);
				g.drawString("High Score - " + high_scores.get(2), save_X3 + 15, Y + score_dims.get("height") + 10);
				g.drawString("Coins - " + coins.get(2), save_X3 + 15, Y + coin_dims.get("height") * 2 + 20);
				g.drawString(times.get(2), save_X3 + 15, Y + score_dims.get("height") * 3 + 30);
			}
		} else {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			color = Settings.darkMode ? Color.lightGray : Color.darkGray;
			g.setColor(color);
			g.fillRoundRect(name_X, rc_Y3, rc_width, rc_height, 15, 15);
			g.fillRoundRect(del_X, rc_Y3, rc_width, rc_height, 15, 15);
			g.setFont(Game.menuFont7);

			g.setColor(name_colors[2]);
			g.drawRoundRect(name_X, rc_Y3, rc_width, rc_height, 15, 15);
			g.drawString("Change Name", name_X + (rc_width - name_dims.get("width")) / 2,
					rc_Y3 + (rc_height - name_dims.get("height")) / 2 + name_dims.get("ascent"));

			g.setColor(del_colors[2]);
			g.drawRoundRect(del_X, rc_Y3, rc_width, rc_height, 15, 15);
			g.drawString("Delete", del_X + (rc_width - del_dims.get("width")) / 2,
					rc_Y3 + (rc_height - del_dims.get("height")) / 2 + del_dims.get("ascent"));
		}

		if (name_menu) {
			// Darken Background
			g.setColor(new Color(0, 0, 0, (int) (255 * 0.8)));
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

			// Window
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			color = Settings.darkMode ? Color.darkGray : new Color(200, 200, 200);
			g.setColor(color);
			g.fillRoundRect(window_X, window_Y, window_width, window_height, 20, 20);
			color = Settings.darkMode ? Color.lightGray : new Color(230, 230, 230);
			g.setColor(color);
			g.drawRoundRect(window_X, window_Y, window_width, window_height, 20, 20);

			// Name
			g.setComposite(original);
			color = Settings.darkMode ? Color.lightGray : Color.darkGray;
			g.setColor(color);
			g.setFont(Game.menuFont);
			String name = new_user ? "Name" : "New Name";
			Map<String, Integer> title_dims = dims.get(name);
			g.drawString(name, window_X + (window_width - title_dims.get("width")) / 2,
					window_Y + title_dims.get("height") + 15);

			// Text Box
			color = Settings.darkMode ? new Color(30, 30, 30) : new Color(160, 160, 160);
			g.setColor(color);
			g.fillRoundRect(tb_X, tb_Y, tb_width, tb_height, 20, 20);

			color = Settings.darkMode
					? input_name.length() != 16 ? new Color(100, 100, 100) : new Color(170, 170, 170)
					: input_name.length() != 16 ? new Color(90, 90, 90) : new Color(20, 20, 20);
			g.setColor(color);
			g.drawRoundRect(tb_X, tb_Y, tb_width, tb_height, 20, 20);

			// Text
			color = Settings.darkMode ? Color.lightGray : Color.darkGray;
			g.setColor(color);
			Map<String, Integer> input_dims = dims.get("Input");
			g.drawString(input_name, tb_X + 15,
					tb_Y + (tb_height - input_dims.get("height")) / 2 + input_dims.get("ascent"));

			if (show_cursor) {
				int cursor_X = tb_X + input_dims.get("width") + 20;
				g.fillRect(cursor_X, tb_Y + 7, 4, tb_height - 17);
			}

			// Save
			g.fillRoundRect(save_X, cn_Y, cn_width, cn_height, 20, 20);
			g.setColor(change_name_colors[0]);
			g.drawRoundRect(save_X, cn_Y, cn_width, cn_height, 20, 20);
			Map<String, Integer> save_dims = dims.get("Save");
			g.drawString("Save", save_X + (cn_width - save_dims.get("width")) / 2,
					cn_Y + (cn_height - save_dims.get("height")) / 2 + save_dims.get("ascent"));

			// Cancel
			color = Settings.darkMode ? Color.lightGray : Color.darkGray;
			g.setColor(color);
			g.fillRoundRect(cancel_X, cn_Y, cn_width, cn_height, 20, 20);
			g.setColor(change_name_colors[1]);
			g.drawRoundRect(cancel_X, cn_Y, cn_width, cn_height, 20, 20);
			Map<String, Integer> cancel_dims = dims.get("Cancel");
			g.drawString("Cancel", cancel_X + (cn_width - cancel_dims.get("width")) / 2,
					cn_Y + (cn_height - cancel_dims.get("height")) / 2 + cancel_dims.get("ascent"));
		}
	}
}
