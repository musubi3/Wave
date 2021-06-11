package com.J2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.J2.Game.STATE;

public class LoadGame extends MouseAdapter {

	public int user = 0;
	public ArrayList<String> saves = new ArrayList<String>();
	public ArrayList<String> saveFiles = new ArrayList<String>();
	public int numUsers;
	public String username = "";
	public boolean newUser = false;
	public boolean confirmed = false;
	public int timer = 25;
	public int bounds;
	public int player;
	public boolean newName = false;

	private boolean full = false;
	private boolean rightClick = false;
	private boolean confirmation = false;
	private int timer2 = 25;
	private String prevName = "";

	private AudioPlayer audio;
	private Handler handler;
	private HUD hud;
	public Color[] saveColors = {Color.darkGray,Color.darkGray,Color.darkGray,Color.darkGray,Color.darkGray,Color.darkGray,
			Color.darkGray,Color.darkGray,Color.darkGray,Color.darkGray,Color.darkGray,Color.darkGray,
			Color.darkGray,Color.darkGray,Color.darkGray,Color.darkGray, Color.black, Color.black};

	public LoadGame(AudioPlayer audio, Handler handler, HUD hud) {
		this.audio = audio;
		this.handler = handler;
		this.hud = hud;
		for (int i = 0; i < 16; i++) saves.add(""); 
		saveFiles.add("sav/base.txt");
		saveFiles.add("sav/save1.txt");
		saveFiles.add("sav/save2.txt");
		saveFiles.add("sav/save3.txt");
		saveFiles.add("sav/save4.txt");
		saveFiles.add("sav/save5.txt");
		saveFiles.add("sav/save6.txt");
		saveFiles.add("sav/save7.txt");
		saveFiles.add("sav/save8.txt");
		saveFiles.add("sav/save9.txt");
		saveFiles.add("sav/save10.txt");
		saveFiles.add("sav/save11.txt");
		saveFiles.add("sav/save12.txt");
		saveFiles.add("sav/save13.txt");
		saveFiles.add("sav/save14.txt");
		saveFiles.add("sav/save15.txt");
		saveFiles.add("sav/save16.txt");
	}

	public void saveUser() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("sav/userNum.txt"));
			bw.write(""+user); bw.newLine();
			bw.write(""+numUsers); bw.newLine();
			bw.write(""+bounds); bw.newLine();
			for (int i = 0; i < saveFiles.size(); i++) {bw.write(saveFiles.get(i)); bw.newLine(); }
			for (int i = 0; i < numUsers; i++) {bw.write(saves.get(i)); bw.newLine(); }
			bw.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	public void loadUser() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("sav/userNum.txt"));
			user = Integer.parseInt(br.readLine());
			numUsers = Integer.parseInt(br.readLine());
			bounds = Integer.parseInt(br.readLine());
			for (int i = 0; i < saveFiles.size(); i++) saveFiles.set(i,br.readLine());
			for (int i = 0; i < numUsers; i++) saves.set(i,br.readLine());
			br.close();
		} catch (NumberFormatException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
	}

	public void save(String file) {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			for (int i = 0; i < Settings.songs.length; i++) {bw.write(Settings.songs[i]); bw.newLine(); }
			for (int i = 0; i < Settings.diff.length; i++) {bw.write(Settings.diff[i]); bw.newLine(); }
			bw.write(""+Game.difficulty);
			bw.newLine();
			if (Settings.music) bw.write("true");
			else bw.write("false");
			bw.newLine();
			if (Settings.sound) bw.write("true");
			else bw.write("false");
			bw.newLine();
			if (Settings.darkMode) bw.write("true");
			else bw.write("false");
			bw.newLine();
			bw.write(""+hud.highScore); bw.newLine();
			bw.write(""+Player.speed); bw.newLine();
			bw.write(""+Shop.healthLevel); bw.newLine();
			bw.write(""+Shop.coins); bw.newLine();
			bw.write(""+Shop.skin); bw.newLine();
			for (int i = 0; i < 3; i++) {
				if (Shop.unlocked[i]) bw.write("true");
				else bw.write("false");
				bw.newLine();
			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load(String file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			for (int i = 0; i < Settings.songs.length; i++) Settings.songs[i] = br.readLine();
			for (int i = 0; i < Settings.diff.length; i++) Settings.diff[i] = br.readLine();
			Game.difficulty = Integer.parseInt(br.readLine());
			if (br.readLine().equals("true")) Settings.music = true;
			else Settings.music = false;
			if (br.readLine().equals("true")) Settings.sound = true;
			else Settings.sound = false;
			if (br.readLine().equals("true")) Settings.darkMode = true;
			else Settings.darkMode = false;
			hud.highScore = Integer.parseInt(br.readLine());
			Player.speed = Integer.parseInt(br.readLine());
			Shop.healthLevel = Integer.parseInt(br.readLine());
			Shop.coins = Integer.parseInt(br.readLine());
			Shop.skin = Integer.parseInt(br.readLine());
			for (int i = 0; i < 3; i++) {
				if (br.readLine().equals("true")) Shop.unlocked[i] = true;
				else Shop.unlocked[i] = false;
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		audio.stopMusic();
		if (Settings.music) {
			if (Settings.songs[0].equals("Default")) audio.playGameSound("res/music.wav", 1.122);
			else if (Settings.songs[0].equals("Sketchers")) audio.playGameSound("res/sketchers.wav", 1.122);
			else if (Settings.songs[0].equals("Act My Age")) audio.playGameSound("res/1D.wav", 1.122);
		}
	}

	public void delete() {
		String temp = "";
		temp = saveFiles.get(numUsers-player+1);
		saveFiles.remove(numUsers-player+1);
		saveFiles.add(temp);
		if (user == numUsers-player+1 && user < numUsers) {load(saveFiles.get(user));}
		else {user--; load(saveFiles.get(user));}
		if (user == 0) {
			for(int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.getID() == ID.MenuPlayer) handler.object.remove(i);
			}
		}
	}

	public int getBounds(String name) {
		if (name.length() == 1) bounds = 0;
		if (name.length() == 2) bounds = 3;
		if (name.length() == 3) bounds = 10;
		if (name.length() == 4) bounds = 17;
		if (name.length() == 5) bounds = 20;
		if (name.length() == 6) bounds = 25;
		if (name.length() == 7) bounds = 28;
		if (name.length() == 8) bounds = 34;
		if (name.length() == 9) bounds = 41;
		if (name.length() == 10) bounds = 47;
		if (name.length() == 11) bounds = 55;
		if (name.length() == 12) bounds = 58;
		if (name.length() == 13) bounds = 65;
		if (name.length() == 14) bounds = 70;
		if (name.length() == 15) bounds = 76;
		if (name.length() == 16) bounds = 83;
		if (name.length() == 17) bounds = 86;
		if (name.length() == 18) bounds = 90;
		if (name.length() == 19) bounds = 105;
		return bounds;
	}

	public int getNameIndex() {
		int index = 0;
		for (int i = 1; i < saves.size(); i++) {
			if (user == i) index = numUsers - i;
		}
		return index;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		int mouse = e.getButton();

		if (Game.gameState == STATE.Load) {
			if (!newUser && !newName && !rightClick) {
				if (mouseOver(mx, my, 287, 490, 250, 20) && numUsers<16 && mouse == MouseEvent.BUTTON1) {
					newUser = true;
					saves.remove(15);
					saves.add(0,"Enter Name");
					if (numUsers >= 15) full = true;
					audio.playMenuSound("res/button4.wav", 0.27);
				}
				int down = 190;
				for (int i = 0; i < saves.size(); i++) {
					if (mouseOver(mx, my, 287, down, 250, 20) && numUsers>i && mouse == MouseEvent.BUTTON1) {
						user = numUsers-i;
						load(saveFiles.get(user));
						bounds = getBounds(saves.get(numUsers-user));
						saveUser();
						audio.stopMusic();
						if (Settings.songs[0].equals("Default")) audio.playGameSound("res/music.wav", 1.122);
						else if (Settings.songs[0].equals("Sketchers")) audio.playGameSound("res/sketchers.wav", 1.122);
						else if (Settings.songs[0].equals("Act My Age")) audio.playGameSound("res/1D.wav", 1.122);
						Game.gameState = STATE.Menu;
					}
					if (mouseOver(mx, my, 287, down, 250, 20) && numUsers>i && mouse == MouseEvent.BUTTON3) {
						player = i;
						rightClick = true;
					}
					down+=20;
				}
			}
			if (!mouseOver(mx, my, 287, 160, 250, 350) && newUser && mouse == MouseEvent.BUTTON1) {
				newUser = false;
				username = "";
				saves.remove(0);
				saves.add(15,"");
				if (numUsers < 15) saves.set(14, "");
				if (numUsers < 16) full = false;
				Game.gameState = STATE.Menu;
				if (Settings.sound) audio.playMenuSound("res/button4.wav", 0.27);
			}
			if (!mouseOver(mx, my, 287, 160, 250, 350) && timer <= 0 && !newUser && !newName && !rightClick && mouse == MouseEvent.BUTTON1) {
				Game.gameState = STATE.Menu;
				if (Settings.sound) audio.playMenuSound("res/button4.wav", 0.27);
			}
			if (rightClick) {
				if (mouseOver(mx, my, 362, 287, 94, 18) && !confirmation && mouse == MouseEvent.BUTTON1) {
					rightClick = false;
					newName = true;
					prevName = saves.get(player);
					saves.set(player, "Enter New Name");
					if (user == numUsers-player)bounds = getBounds(saves.get(player));
					if (Settings.sound) audio.playMenuSound("res/button4.wav", 0.27);
				}
				if (mouseOver(mx, my, 362, 317, 94, 18) && !confirmation && mouse == MouseEvent.BUTTON1) {
					confirmation = true;
					timer2 = 25;
					if (Settings.sound) audio.playMenuSound("res/button4.wav", 0.27);
				}
				if (!mouseOver(mx, my, 335, 270, 150, 75) && !confirmation && mouse == MouseEvent.BUTTON1) {
					rightClick = false;
					if (Settings.sound) audio.playMenuSound("res/button4.wav", 0.27);
				}
				if (confirmation) {
					if (mouseOver(mx, my, 362, 317, 94, 18) && timer2 <= 0 && mouse == MouseEvent.BUTTON1) {
						confirmation = false;
						if (Settings.sound) audio.playMenuSound("res/button4.wav", 0.27);
					}
					if (mouseOver(mx, my, 362, 292, 94, 18) && mouse == MouseEvent.BUTTON1) {
						rightClick = false;
						confirmation = false;
						saves.remove(player);
						if (full) full = false;
						numUsers--;
						saves.add(15,"");
						if (numUsers < 15) saves.set(14, "");
						delete();
						saveUser();
						if (Settings.sound) audio.playMenuSound("res/button4.wav", 0.27);
					}
				}
			}
			if (!mouseOver(mx, my, 287, 160, 250, 350) && newName && mouse == MouseEvent.BUTTON1) {
				newName = false;
				saves.set(player, prevName);
				bounds = getBounds(saves.get(numUsers-user));
				Game.gameState = STATE.Menu;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (Game.gameState == STATE.Load) {
			if (mouseOver(mx, my, 287, 190, 250, 20)) {
				if (Settings.darkMode) saveColors[0] = Color.green;
				else saveColors[0] = new Color(0,170,0);
				if (newUser) saveColors[0] = new Color(0,150,0);
				if (newName) saveColors[0] = Color.darkGray;
				if (newName && player == 0) saveColors[0] = new Color(0,150,0);
				if (rightClick) saveColors[0] = Color.darkGray; 
			} else {
				saveColors[0] = Color.darkGray;
				if (newUser) saveColors[0] = new Color(0,150,0);
				if (newName && player == 0) saveColors[0] = new Color(0,150,0);
			}
			int down = 210;
			for (int i = 1; i < saves.size(); i++) {
				if (mouseOver(mx, my, 287, down, 250, 20)) {
					if (Settings.darkMode) saveColors[i] = Color.green;
					else saveColors[i] = new Color(0,170,0);
					if (newUser) saveColors[i] = Color.darkGray;
					if (newName) saveColors[i] = Color.darkGray;
					if (newName && player == i) saveColors[i] = new Color(0,150,0); 
					if (rightClick) saveColors[i] = Color.darkGray;
				} else {
					saveColors[i] = Color.darkGray;
					if (newName && player == i) saveColors[i] = new Color(0,150,0); 
				}
				down+=20;
			}
			if (rightClick) {
				if (mouseOver(mx, my, 362, 287, 94, 18)) saveColors[16] = Color.green;
				else saveColors[16] = Color.black;

				if (mouseOver(mx, my, 362, 317, 94, 18)) saveColors[17] = Color.red;
				else saveColors[17] = Color.black;
				
				if (confirmation) { 
					saveColors[16] = Color.black;
					saveColors[17] = Color.green;
					if (mouseOver(mx, my, 362, 292, 94, 18)) saveColors[16] = Color.red;
					else saveColors[16] = Color.black;

					if (mouseOver(mx, my, 362, 317, 94, 18)) saveColors[17] = Color.green;
					else saveColors[17] = Color.black;
				}
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
		if (!full) saves.set(15, "New Game");
		Game.clamp(timer, 0, 25);
		Game.clamp(timer2, 0, 25);
		timer--;
		timer2--;
	}

	public void render(Graphics g) {
		if (Game.gameState == STATE.Load) {
			g.setFont(Game.titleFont);
			if (Settings.darkMode) g.setColor(Color.white);
			else g.setColor(Color.black);
			g.drawString("W A V E", 280, 170);

			g.setFont(Game.titleFont2);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawString("v1.0.0", 765, 583);
			g.drawString("JL33", 12, 583);

			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(258, 200, 145, 70);
			g.setFont(Game.menuFont2);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawRect(258, 200, 145, 70);
			g.drawString("Single Player", 265, 240);

			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(424, 200, 145, 70);
			g.setFont(Game.menuFont2);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawRect(424, 200, 145, 70);
			g.drawString("Multiplayer", 440, 240);

			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(258, 300, 310, 70);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawRect(258, 300, 310, 70);

			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(258, 400, 310, 70);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawRect(258, 400, 310, 70);

			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(60, 470, 80, 35);
			g.setFont(Game.menuFont3);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawRect(60, 470, 80, 35);
			g.drawString("Load Game", 64, 491);

			if (Settings.darkMode) g.setColor(Color.darkGray);
			else g.setColor(new Color(235,235,235));
			g.fillRect(690, 470, 50, 35);
			g.setFont(Game.menuFont3);
			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(Color.darkGray);
			g.drawRect(690, 470, 50, 35);
			g.drawString("Shop", 697, 491);

			if (Settings.darkMode) g.setColor(Color.white);
			else g.setColor(Color.black);
			g.setFont(Game.menuFont2);
			if (user > 0) g.drawRect(640, 280, 150, 150);
			if (!newUser) {
				for (int i = 1; i < saves.size(); i++) {
					if (user == i) g.drawString(saves.get(numUsers-i), 710-bounds, 250);
				}
			}
			if (user > 0 && newUser) {
				for (int i = 1; i < saves.size(); i++) {
					if (user == i)g.drawString(saves.get(numUsers-i+1), 710-bounds, 250);
				}
			}

			if (Settings.darkMode) g.setColor(Color.lightGray);
			else g.setColor(new Color(200,200,200));
			g.fillRect(287, 160, 250, 350);
			g.setFont(Game.titleFont2);
			g.setColor(new Color(150,150,150));
			g.drawRect(287, 160, 250, 350);
			g.fillRect(287, 160, 250, 30);
			g.setColor(Color.darkGray);
			g.drawString("Load Game", 362, 180);
			g.setColor(new Color(150,150,150));
			int down = 190; 
			for (int i = 0; i < 16; i++) {
				g.drawRect(287,down,250,20);
				down += 20;
			}
			down = 205;
			for (int i = 0; i < saves.size(); i++) {
				g.setColor(saveColors[i]);
				g.drawString(saves.get(i), 295, down);
				down += 20;
			}
			if (newUser && username.equals("")) saves.set(0, "Enter Name");
			if (confirmed) {
				bounds = getBounds(username);
				username = "";
				numUsers++;
				if (user != 0) {
					Settings.darkMode = true;
					Settings.music = true;
					Settings.sound = true;
					Settings.songs[0] = "Default";
					Settings.songs[1] = "Sketchers";
					Settings.songs[2] = "Act My Age";
					Settings.diff[0] = "Normal";
					Settings.diff[1] = "Hard";
					hud.highScore = 0;
					Shop.coins = 0;
					Shop.healthLevel = 0;
					Shop.skin = 0;
					Player.speed = 3;
					for (int i = 0; i < Shop.unlocked.length; i++) Shop.unlocked[i] = false;
				}
				user = numUsers;
				save(saveFiles.get(user));
				saveUser();
				audio.stopMusic();
				if (Settings.songs[0].equals("Default")) audio.playGameSound("res/music.wav", 1.122);
				else if (Settings.songs[0].equals("Sketchers")) audio.playGameSound("res/sketchers.wav", 1.122);
				else if (Settings.songs[0].equals("Act My Age")) audio.playGameSound("res/1D.wav", 1.122);
				Game.gameState = STATE.Menu;
				confirmed = false;
			}
			if (rightClick) {
				g.setColor(Color.darkGray);
				g.fillRect(335, 270, 150, 75);
				g.setColor(Color.white);
				g.drawRect(335, 270, 150, 75);
				g.setColor(Color.lightGray);
				g.fillRect(362, 287, 94, 18);
				g.fillRect(362, 317, 94, 18);
				g.setColor(saveColors[16]);
				g.setFont(Game.titleFont3);
				g.drawString("Change Name", 367, 300);
				g.drawRect(362, 287, 94, 18);
				g.setColor(saveColors[17]);
				g.drawString("Delete", 389, 330);
				g.drawRect(362, 317, 94, 18);
			}
			if (newName && user == numUsers-player) bounds = getBounds(username);
			if (confirmation) {
				g.setColor(Color.darkGray);
				g.fillRect(335, 270, 150, 75);
				g.setColor(Color.white);
				g.drawRect(335, 270, 150, 75);
				g.setColor(Color.lightGray);
				g.fillRect(362, 292, 94, 18);
				g.fillRect(362, 317, 94, 18);
				g.setFont(Game.titleFont3);
				g.setColor(Color.white);
				g.drawString("Are you sure?", 362, 284);
				g.setColor(saveColors[16]);
				g.drawString("YES", 398, 305);
				g.drawRect(362, 292, 94, 18);
				g.setColor(saveColors[17]);
				g.drawString("NO", 401, 330);
				g.drawRect(362, 317, 94, 18);
			}
		}
	}
}


