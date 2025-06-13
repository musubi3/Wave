package com.J2;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.J2.Game.STATE;

public class Settings extends MouseAdapter{

	private AudioPlayer audio;
	private LoadGame load;

	private boolean open = false;
	private boolean open2 = false;

	public static int timer = 25;
	public static String[] songs = {"Default","Sketchers","Act My Age"};
	public static String[] diff = {"Normal","Hard"};
	public static boolean music = true;
	public static boolean sound = true;
	public static boolean darkMode = true;

	private Color color = Color.black;
	private Color color2 = Color.black;
	private Color color3 = Color.black;
	private Color color4 = Color.black;
	private Color color5 = Color.darkGray;
	private Color color6 = Color.darkGray;
	private Color color7 = Color.darkGray;
	private Color color8 = Color.darkGray;
	private Color color9 = Color.black;
	private Color color10 = Color.white;
	private Color color11 = Color.white;
	private Color color12 = Color.white;
	private Color color13 = Color.black;
	private Color color14 = Color.white;
	private Color color15 = Color.white;
	private Color color16 = Color.darkGray;

	public Settings(AudioPlayer audio, LoadGame load) {
		this.audio = audio;
		this.load = load;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		//Back from settings
		if (Game.gameState == STATE.Settings || Game.gameState == STATE.AudioSettings 
				|| Game.gameState == STATE.ApperanceSettings || Game.gameState == STATE.Difficulty) {
			if (mouseOver(mx, my, 75, 55, 60, 20)) {
				Game.gameState = STATE.Menu;
				color16 = Color.darkGray;
				open = false;
				open2 = false;
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			if (!mouseOver(mx, my,65, 50, 700, 450)) {
				Game.gameState = STATE.Menu;
				open = false;
				open2 = false;
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
		}
		//Appearance
		if ((Game.gameState == STATE.Settings || Game.gameState == STATE.AudioSettings || Game.gameState == STATE.Difficulty) && timer <= 0) {
			if (mouseOver(mx, my, 80, 265, 269, 70)) {
				Game.gameState = STATE.ApperanceSettings;
				open = false;
				open2 = false;
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
		}
		//Audio
		if (Game.gameState == STATE.Settings || Game.gameState == STATE.ApperanceSettings || Game.gameState == STATE.Difficulty) {
			if (mouseOver(mx, my, 80, 180, 269, 70)) {
				Game.gameState = STATE.AudioSettings;
				open = false;
				open2 = false;
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
		}
		//Settings
		if (Game.gameState == STATE.ApperanceSettings || Game.gameState == STATE.AudioSettings || Game.gameState == STATE.Difficulty) {
			if (mouseOver(mx, my, 80, 95, 269, 70)) {
				Game.gameState = STATE.Settings;
				open = false;
				open2 = false;
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
		}
		//Difficulty
		if ((Game.gameState == STATE.Settings || Game.gameState == STATE.AudioSettings || Game.gameState == STATE.ApperanceSettings) && timer <= 0) {
			if (mouseOver(mx, my, 80, 350, 269, 70)) {
				Game.gameState = STATE.Difficulty;
				open = false;
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
		}
		if (Game.gameState == STATE.AudioSettings) {
			//Music on/off
			if (mouseOver(mx, my, 655, 117, 75, 25)) {
				if (music) {
					music = false;
					audio.stopMusic();
				}
				else {
					music = true;
					if (songs[0].equals("Default")) audio.playGameSound("app/res/music.wav", 1.122);
					else if (songs[0].equals("Sketchers")) audio.playGameSound("app/res/sketchers.wav", 1.122);
					else if (songs[0].equals("Act My Age")) audio.playGameSound("app/res/1D.wav", 1.122);
				}
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			//Sfx on/off
			else if (mouseOver(mx, my, 655, 162, 75, 25)) {
				if (sound) sound = false;
				else sound = true;
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			//Expand songs
			else if (mouseOver(mx, my, 695, 207, 35, 25) && !open) {
				open = true;
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			//Collapse songs
			else if (mouseOver(mx, my, 695, 207, 35, 25) && open) {
				open = false;
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			//Collapse songs
			else if (!mouseOver(mx, my, 495, 232, 200, 50) && open) {
				open = false;
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			//New Song
			else if (mouseOver(mx, my, 495, 232, 200, 25) && open) {
				String temp = "";
				temp = songs[0];
				songs[0] = songs[1];
				songs[1] = temp;
				open = false;
				audio.stopMusic();
				if (songs[0].equals("Default")) audio.playGameSound("app/res/music.wav", 1.122);
				else if (songs[0].equals("Sketchers")) audio.playGameSound("app/res/sketchers.wav", 1.122);
				else if (songs[0].equals("Act My Age")) audio.playGameSound("app/res/1D.wav", 1.122);
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			//New Song 2
			else if (mouseOver(mx, my, 495, 257, 200, 25) && open) {
				String temp = "";
				temp = songs[0];
				songs[0] = songs[2];
				songs[2] = temp;
				open = false;
				audio.stopMusic();
				if (songs[0].equals("Default")) audio.playGameSound("app/res/music.wav", 1.122);
				else if (songs[0].equals("Sketchers")) audio.playGameSound("app/res/sketchers.wav", 1.122);
				else if (songs[0].equals("Act My Age")) audio.playGameSound("app/res/1D.wav", 1.122);
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
		}
		else if (Game.gameState == STATE.ApperanceSettings) {
			//DarkMode
			if (mouseOver(mx, my, 655, 117, 75, 25)) {
				if (darkMode) darkMode = false;
				else darkMode = true;
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
				Menu.menuColor = Color.lightGray;
				Menu.menuColor2 = Color.lightGray;
				Menu.menuColor3 = Color.lightGray;
				Menu.menuColor4 = Color.lightGray;
				Menu.menuColor5 = Color.lightGray;
				Menu.menuColor6 = Color.lightGray;
				Menu.about = Color.lightGray;
			}
			//LightMode
			else if (mouseOver(mx, my, 655, 162, 75, 25)) {
				if (darkMode) darkMode = false;
				else darkMode = true;
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
				Menu.menuColor = Color.darkGray;
				Menu.menuColor2 = Color.darkGray;
				Menu.menuColor3 = Color.darkGray;
				Menu.menuColor4 = Color.darkGray;
				Menu.menuColor5 = Color.darkGray;
				Menu.menuColor6 = Color.darkGray;
				Menu.about = Color.darkGray;
			}
		}
		else if (Game.gameState == STATE.Difficulty) {
			//Expand menu
			if (mouseOver(mx, my, 695, 117, 35, 25) && !open2) {
				open2 = true;
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			//Collapse
			else if (mouseOver(mx, my, 695, 117, 35, 25) && open2) {
				open2 = false;
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			//Collapse
			else if (!mouseOver(mx, my, 495, 132, 200, 25) && open2) {
				open2 = false;
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
			//Change Diff
			else if (mouseOver(mx, my, 495, 142, 200, 25) && open2) {
				String temp = "";
				temp = diff[0];
				diff[0] = diff[1];
				diff[1] = temp;
				open2 = false;
				if (diff[0].equals("Normal")) Game.difficulty = 0;
				else if (diff[0].equals("Hard")) Game.difficulty = 1;
				if (load.user > 0) load.save(load.saveFiles.get(load.user));
				if (sound) audio.playMenuSound("app/res/button4.wav", 0.27);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (Game.gameState == STATE.Settings || Game.gameState == STATE.AudioSettings || Game.gameState == STATE.ApperanceSettings || Game.gameState == STATE.Difficulty) {
			if (mouseOver(mx, my, 75, 55, 60, 20)) {
				if (Settings.darkMode) color16 = new Color(0,190,0);
				else color16 = new Color(0,170,0);
			}else {
				color16 = Color.darkGray;
			}
		}
		
		if (Game.gameState == STATE.AudioSettings) {
			//Music on/off
			if (mouseOver(mx, my, 655, 117, 75, 25)) {
				if (Settings.darkMode) color = Color.green;
				else color = new Color(0,170,0);
			}else {
				if (Settings.darkMode) color = Color.black;
				else color = Color.darkGray;
			}
			if (mouseOver(mx, my, 655, 117, 75, 25)) {
				if (Settings.darkMode) color5 = Color.green;
				else color5 = new Color(0,170,0);
			}else {
				color5 = Color.darkGray;
			}

			//Sfx on/off
			if (mouseOver(mx, my, 655, 162, 75, 25)) {
				if (Settings.darkMode) color2 = Color.green;
				else color2 = new Color(0,170,0);
			} else {
				if (Settings.darkMode) color2 = Color.black;
				else color2 = Color.darkGray;
			}
			if (mouseOver(mx, my, 655, 162, 75, 25)) {
				if (Settings.darkMode) color6 = Color.green;
				else color6 = new Color(0,170,0);
			} else {
				color6 = Color.darkGray;
			}

			//Open Songs
			if (mouseOver(mx, my, 695, 207, 35, 25)) {
				if (Settings.darkMode) color9 = Color.green;
				else color9 = new Color(0,170,0);
			} else {
				if (Settings.darkMode) color9 = Color.black;
				else color9 = Color.darkGray;
			}

			//Select Song
			if (mouseOver(mx, my, 495, 207, 200, 25) && open) {
				if (Settings.darkMode) color12 = Color.green;
				else color12 = new Color(0,170,0);
			} else {
				if (Settings.darkMode) color12 = Color.white;
				else color12 = Color.darkGray;
			}
			if (mouseOver(mx, my, 495, 232, 200, 25)) {
				if (Settings.darkMode) color10 = Color.green;
				else color10 = new Color(0,170,0);
			} else {
				if (Settings.darkMode) color10 = Color.white;
				else color10 = Color.darkGray;
			}
			if (mouseOver(mx, my, 495, 257, 200, 25)) {
				if (Settings.darkMode) color11 = Color.green;
				else color11 = new Color(0,170,0);
			} else {
				if (Settings.darkMode) color11 = Color.white;
				else color11 = Color.darkGray;
			}
		}
		else if (Game.gameState == STATE.ApperanceSettings) {
			//DarkMode
			if (mouseOver(mx, my, 655, 117, 75, 25)) {
				if (Settings.darkMode) color3 = Color.green;
				else color3 = new Color(0,170,0);
			} else {
				if (Settings.darkMode) color3 = Color.black;
				else color3 = Color.darkGray;
			}
			if (mouseOver(mx, my, 655, 117, 75, 25)) {
				if (Settings.darkMode) color7 = Color.green;
				else color7 = new Color(0,170,0);
			} else {
				color7 = Color.darkGray;
			}

			//LightMode
			if (mouseOver(mx, my, 655, 162, 75, 25)) {
				if (Settings.darkMode) color4 = Color.green;
				else color4 = new Color(0,170,0);
			} else {
				if (Settings.darkMode) color4 = Color.black;
				else color4 = Color.darkGray;
			}
			if (mouseOver(mx, my, 655, 162, 75, 25)) {
				if (Settings.darkMode) color8 = Color.green;
				else color8 = new Color(0,170,0);
			} else {
				color8 = Color.darkGray;
			}
		}
		else if (Game.gameState == STATE.Difficulty) {
			if (mouseOver(mx, my, 695, 117, 35, 25)) {
				if (Settings.darkMode) color13 = Color.green;
				else color13 = new Color(0,170,0);
			} else {
				if (Settings.darkMode) color13 = Color.black;
				else color13 = Color.darkGray;
			}
			
			if (mouseOver(mx, my, 495, 117, 200, 25) && open2) {
				if (Settings.darkMode) color14 = Color.green;
				else color14 = new Color(0,170,0);
			} else {
				if (Settings.darkMode) color14 = Color.white;
				else color14 = Color.darkGray;
			}
			
			if (mouseOver(mx, my, 495, 142, 200, 25)) {
				if (Settings.darkMode) color15 = Color.green;
				else color15 = new Color(0,170,0);
			} else {
				if (Settings.darkMode) color15 = Color.white;
				else color15 = Color.darkGray;
			}
		}
	}

	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			}
			else return false;
		}
		else return false;
	}

	public void tick() {
		Game.clamp(timer, 0, 25);
		timer--;
	}

	public void render(Graphics g) {
		if (Game.gameState == STATE.Settings) {

			//Version
			g.setFont(Game.titleFont2);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawString("v1.0.0", 765, 583);
			g.drawString("MUSUBI", 12, 583);

			//Window left
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(200,200,200));
			g.fillRect(65, 50, 700, 450);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(new Color(230,230,230));
			g.drawRect(65, 50, 700, 450);
			g.fillRect(65, 50, 700, 30);

			//Text
			g.setFont(Game.menuFont2);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawString("Settings", 522, 70);

			//Back
			g.setColor(color16);
			g.setFont(Game.menuFont2);
			g.drawRect(75, 55, 60, 20);
			g.drawString("Back", 82, 69);

			//Window right
			if (darkMode) g.setColor(new Color(50,50,50));
			else g.setColor(Color.lightGray);
			g.fillRect(65, 80, 300, 420);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(new Color(230,230,230));
			g.drawRect(65, 80, 300, 420);

			//Controls
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(230,230,230));
			g.fillRect(80, 95, 269, 70);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.drawRect(80, 95, 269, 70);
			g.setFont(Game.menuFont);
			if (!darkMode) g.setColor(new Color(150,150,150));
			g.drawString("Controls", 147, 137);

			//Audio
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(80, 180, 269, 70);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 180, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Audio", 175, 220);

			//Appearance
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(80, 265, 269, 70);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 265, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Appearance", 127, 305);

			//Difficulty
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(80, 350, 269, 70);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 350, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Difficulty", 143, 390);

			//Player 1
			g.setFont(Game.menuFont2);
			g.setColor(new Color(0, 130, 210));
			g.drawString("Player 1", 410, 150);
			g.setFont(Game.menuFont4);
			g.drawString("W   -  Up", 410, 200);
			g.drawRect(408, 188, 17, 17);
			g.drawString("A   -  Left", 410, 230);
			g.drawRect(408, 218, 17, 17);
			g.drawString("S   -  Down", 410, 260);
			g.drawRect(408, 248, 17, 17);
			g.drawString("D   -  Right", 410, 290);
			g.drawRect(408, 278, 17, 17);

			g.setFont(Game.menuFont2);
			g.setColor(new Color(0,170,0));
			g.drawString("Player 2", 560, 150);
			g.setFont(Game.menuFont4);
			g.drawString("Up Arrow   -  Up", 560, 200);
			g.drawRect(558, 188, 83, 17);
			g.drawString("Left Arrow   -  Left", 560, 230);
			g.drawRect(558, 218, 103, 17);
			g.drawString("Down Arrow   -  Down", 560, 260);
			g.drawRect(558, 248, 104, 17);
			g.drawString("Right Arrow   -  Right", 560, 290);
			g.drawRect(558, 278, 108, 17);
			
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.setFont(Game.menuFont2);
			g.drawString("P   -  Pause Game", 410, 350);
			g.drawRect(408, 338, 17, 17);
		}
		if (Game.gameState == STATE.AudioSettings) {
			//Version
			g.setFont(Game.titleFont2);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawString("v1.0.0", 765, 583);
			g.drawString("MUSUBI", 12, 583);

			//Window left
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(200,200,200));
			g.fillRect(65, 50, 700, 450);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(new Color(230,230,230));
			g.drawRect(65, 50, 700, 450);
			g.fillRect(65, 50, 700, 30);

			//Text
			g.setFont(Game.menuFont2);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawString("Settings", 522, 70);

			//Back
			g.setColor(color16);
			g.setFont(Game.menuFont2);
			g.drawRect(75, 55, 60, 20);
			g.drawString("Back", 82, 69);

			//Window right
			if (darkMode) g.setColor(new Color(50,50,50));
			else g.setColor(Color.lightGray);
			g.fillRect(65, 80, 300, 420);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(new Color(230,230,230));
			g.drawRect(65, 80, 300, 420);

			//Controls
			if (!darkMode) g.setColor(Color.white);
			g.fillRect(80, 95, 269, 70);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 95, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Controls", 147, 137);

			//Audio
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(230,230,230));
			g.fillRect(80, 180, 269, 70);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.drawRect(80, 180, 269, 70);
			g.setFont(Game.menuFont);
			if (!darkMode) g.setColor(new Color(150,150,150));
			g.drawString("Audio", 175, 220);

			//Appearance
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(80, 265, 269, 70);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 265, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Appearance", 127, 305);

			//Difficulty
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(80, 350, 269, 70);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 350, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Difficulty", 143, 390);

			g.setFont(Game.menuFont2);
			if (!darkMode) g.setColor(Color.white);
			else g.setColor(Color.lightGray);
			g.fillRect(655, 117, 75, 25);
			if (!darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.lightGray);
			g.drawString("Music", 390, 136);
			g.setColor(color);
			g.drawRect(655, 117, 75, 25);
			g.setColor(color5);
			g.setFont(Game.menuFont2);
			if (music) g.drawString("ON", 680, 133);
			else g.drawString("OFF", 674, 133);

			g.setFont(Game.menuFont2);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawString("Sfx", 390, 181);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(655, 162, 75, 25);
			g.setColor(color2);
			g.drawRect(655, 162, 75, 25);
			g.setColor(color6);
			g.setFont(Game.menuFont2);
			if (sound) g.drawString("ON", 680, 178);
			else g.drawString("OFF", 674, 178);

			if (!open) {
				g.setFont(Game.menuFont2);
				if (darkMode) g.setColor(Color.lightGray);
				else g.setColor(Color.darkGray);
				g.drawString("Song", 390, 226);
				if (!darkMode) g.setColor(Color.lightGray);
				g.fillRect(695, 207, 35, 25);
				g.setColor(color9);
				g.drawRect(695, 207, 35, 25);
				g.drawString("V",706,225);
				g.drawString("_",706,215);
				if (darkMode) g.setColor(new Color(50,50,50));
				else g.setColor(Color.white);
				g.fillRect(495, 207, 200, 25);
				if (darkMode) g.setColor(Color.black);
				else g.setColor(Color.darkGray);
				g.drawRect(495, 207, 200, 25);
				if (darkMode) g.setColor(Color.white);
				else g.setColor(Color.darkGray);
				g.setFont(Game.menuFont4);
				g.drawString(songs[0],500,223);
			}
			else {
				g.setFont(Game.menuFont2);
				if (darkMode) g.setColor(Color.lightGray);
				else g.setColor(Color.darkGray);
				g.drawString("Song", 390, 226);
				if (!darkMode) g.setColor(Color.lightGray);
				g.fillRect(695, 207, 35, 25);
				g.setColor(color9);
				g.drawRect(695, 207, 35, 25);
				g.drawString("V",706,225);
				g.drawString("_",706,215);
				if (darkMode) g.setColor(new Color(50,50,50));
				else g.setColor(Color.white);
				g.fillRect(495, 207, 200, 75);
				if (darkMode) g.setColor(Color.black);
				else g.setColor(Color.darkGray);
				g.drawRect(495, 207, 200, 25);
				g.drawRect(495, 232, 200, 25);
				g.drawRect(495, 257, 200, 25);
				g.setColor(color12);
				g.setFont(Game.menuFont4);
				g.drawString(songs[0],500,223);
				g.setColor(color10);
				g.drawString(songs[1],500,248);
				g.setColor(color11);
				g.drawString(songs[2],500,273);
			}
		}
		if (Game.gameState == STATE.ApperanceSettings) {
			//Version
			g.setFont(Game.titleFont2);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawString("v1.0.0", 765, 583);
			g.drawString("MUSUBI", 12, 583);

			//Window left
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(200,200,200));
			g.fillRect(65, 50, 700, 450);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(new Color(230,230,230));
			g.drawRect(65, 50, 700, 450);
			g.fillRect(65, 50, 700, 30);

			//Text
			g.setFont(Game.menuFont2);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawString("Settings", 522, 70);

			//Back
			g.setColor(color16);
			g.setFont(Game.menuFont2);
			g.drawRect(75, 55, 60, 20);
			g.drawString("Back", 82, 69);

			//Window right
			if (darkMode) g.setColor(new Color(50,50,50));
			else g.setColor(Color.lightGray);
			g.fillRect(65, 80, 300, 420);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(new Color(230,230,230));
			g.drawRect(65, 80, 300, 420);

			//Controls
			if (!darkMode) g.setColor(Color.white);
			g.fillRect(80, 95, 269, 70);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 95, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Controls", 147, 137);

			//Audio
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(80, 180, 269, 70);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 180, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Audio", 175, 220);

			//Appearance
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(230,230,230));
			g.fillRect(80, 265, 269, 70);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.drawRect(80, 265, 269, 70);
			g.setFont(Game.menuFont);
			if (!darkMode) g.setColor(new Color(150,150,150));
			g.drawString("Appearance", 127, 305);

			//Difficulty
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(80, 350, 269, 70);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 350, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Difficulty", 142, 390);

			//Dark Mode toggle
			g.setFont(Game.menuFont2);
			if (!darkMode) g.setColor(Color.white);
			else g.setColor(Color.lightGray);
			g.fillRect(655, 117, 75, 25);
			if (!darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.lightGray);
			g.drawString("Dark Mode", 390, 136);
			g.setColor(color3);
			g.drawRect(655, 117, 75, 25);
			g.setColor(color7);
			g.setFont(Game.menuFont2);
			if (darkMode) g.drawString("ON", 680, 133);
			else g.drawString("OFF", 674, 133);

			//Light Mode toggle
			g.setFont(Game.menuFont2);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawString("Light Mode", 390, 181);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(655, 162, 75, 25);
			g.setColor(color4);
			g.drawRect(655, 162, 75, 25);
			g.setColor(color8);
			g.setFont(Game.menuFont2);
			if (!darkMode) g.drawString("ON", 680, 178);
			else g.drawString("OFF", 674, 178);
		}
		if (Game.gameState == STATE.Difficulty) {
			//Version
			g.setFont(Game.titleFont2);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawString("v1.0.0", 765, 583);
			g.drawString("MUSUBI", 12, 583);

			//Window left
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(200,200,200));
			g.fillRect(65, 50, 700, 450);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(new Color(230,230,230));
			g.drawRect(65, 50, 700, 450);
			g.fillRect(65, 50, 700, 30);

			//Text
			g.setFont(Game.menuFont2);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawString("Settings", 522, 70);

			//Back
			g.setColor(color16);
			g.setFont(Game.menuFont2);
			g.drawRect(75, 55, 60, 20);
			g.drawString("Back", 82, 69);

			//Window right
			if (darkMode) g.setColor(new Color(50,50,50));
			else g.setColor(Color.lightGray);
			g.fillRect(65, 80, 300, 420);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(new Color(230,230,230));
			g.drawRect(65, 80, 300, 420);

			//Controls
			if (!darkMode) g.setColor(Color.white);
			g.fillRect(80, 95, 269, 70);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 95, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Controls", 147, 137);

			//Audio
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(80, 180, 269, 70);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 180, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Audio", 175, 220);

			//Appearance
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.fillRect(80, 265, 269, 70);
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(Color.darkGray);
			g.drawRect(80, 265, 269, 70);
			g.setFont(Game.menuFont);
			g.drawString("Appearance", 127, 305);

			//Difficulty
			if (darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(230,230,230));
			g.fillRect(80, 350, 269, 70);
			if (darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.white);
			g.drawRect(80, 350, 269, 70);
			g.setFont(Game.menuFont);
			if (!darkMode) g.setColor(new Color(150,150,150));
			g.drawString("Difficulty", 142, 390);

			if (!open2) {
				g.setFont(Game.menuFont2);
				if (darkMode) g.setColor(Color.lightGray);
				else g.setColor(Color.darkGray);
				g.drawString("Difficulty", 390, 136);
				if (!darkMode) g.setColor(Color.lightGray);
				g.fillRect(695, 117, 35, 25);
				g.setColor(color13);
				g.drawRect(695, 117, 35, 25);
				g.drawString("V",706,135);
				g.drawString("_",706,125);
				if (darkMode) g.setColor(new Color(50,50,50));
				else g.setColor(Color.white);
				g.fillRect(495, 117, 200, 25);
				if (darkMode) g.setColor(Color.black);
				else g.setColor(Color.darkGray);
				g.drawRect(495, 117, 200, 25);
				if (darkMode) g.setColor(Color.white);
				else g.setColor(Color.darkGray);
				g.setFont(Game.menuFont4);
				g.drawString(diff[0],500,133);
			}
			else {
				g.setFont(Game.menuFont2);
				if (darkMode) g.setColor(Color.lightGray);
				else g.setColor(Color.darkGray);
				g.drawString("Difficulty", 390, 136);
				if (!darkMode) g.setColor(Color.lightGray);
				g.fillRect(695, 117, 35, 25);
				g.setColor(color13);
				g.drawRect(695, 117, 35, 25);
				g.drawString("V",706,135);
				g.drawString("_",706,125);
				if (darkMode) g.setColor(new Color(50,50,50));
				else g.setColor(Color.white);
				g.fillRect(495, 117, 200, 50);
				if (darkMode) g.setColor(Color.black);
				else g.setColor(Color.darkGray);
				g.drawRect(495, 117, 200, 25);
				g.drawRect(495, 142, 200, 25);
				g.setColor(color14);
				g.setFont(Game.menuFont4);
				g.drawString(diff[0],500,133);
				g.setColor(color15);
				g.drawString(diff[1],500,158);
			}
		}
	}
}
