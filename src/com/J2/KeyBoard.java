package com.J2;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyBoard extends KeyAdapter{

	private LoadGame load;
	private Handler handler;

	public KeyBoard(LoadGame load, Handler handler) {
		this.load = load;
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (load.newUser) {
			if (load.username.length() < 19) {
				if (key == KeyEvent.VK_A) {load.username += "A"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_B) {load.username += "B"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_C) {load.username += "C"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_D) {load.username += "D"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_E) {load.username += "E"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_F) {load.username += "F"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_G) {load.username += "G"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_H) {load.username += "H"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_I) {load.username += "I"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_J) {load.username += "J"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_K) {load.username += "K"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_L) {load.username += "L"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_M) {load.username += "M"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_N) {load.username += "N"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_O) {load.username += "O"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_P) {load.username += "P"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_Q) {load.username += "Q"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_R) {load.username += "R"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_S) {load.username += "S"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_T) {load.username += "T"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_U) {load.username += "U"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_V) {load.username += "V"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_W) {load.username += "W"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_X) {load.username += "X"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_Y) {load.username += "Y"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_Z) {load.username += "Z"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_1) {load.username += "1"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_2) {load.username += "2"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_3) {load.username += "3"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_4) {load.username += "4"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_5) {load.username += "5"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_6) {load.username += "6"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_7) {load.username += "7"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_8) {load.username += "8"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_9) {load.username += "9"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_0) {load.username += "0"; load.saves.set(0,load.username);}
				if (key == KeyEvent.VK_SPACE) {load.username += "  "; load.saves.set(0,load.username);}
			}
			if (key == KeyEvent.VK_BACK_SPACE) {
				String temp = "";
				for (int i = 0; i < load.username.length()-1; i++) temp += load.username.charAt(i);
				load.username = temp;
				load.saves.set(0,load.username);
			}
			if (key == KeyEvent.VK_ENTER) {
				load.newUser = false;
				load.confirmed = true;
				if (load.user == 0) handler.addObject(new MenuPlayer(640,355,ID.MenuPlayer,handler));
			}
		}
		if (load.newName) {
			if (load.username.length() < 19) {
				if (key == KeyEvent.VK_A) {load.username += "A"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_B) {load.username += "B"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_C) {load.username += "C"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_D) {load.username += "D"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_E) {load.username += "E"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_F) {load.username += "F"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_G) {load.username += "G"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_H) {load.username += "H"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_I) {load.username += "I"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_J) {load.username += "J"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_K) {load.username += "K"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_L) {load.username += "L"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_M) {load.username += "M"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_N) {load.username += "N"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_O) {load.username += "O"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_P) {load.username += "P"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_Q) {load.username += "Q"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_R) {load.username += "R"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_S) {load.username += "S"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_T) {load.username += "T"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_U) {load.username += "U"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_V) {load.username += "V"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_W) {load.username += "W"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_X) {load.username += "X"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_Y) {load.username += "Y"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_Z) {load.username += "Z"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_1) {load.username += "1"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_2) {load.username += "2"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_3) {load.username += "3"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_4) {load.username += "4"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_5) {load.username += "5"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_6) {load.username += "6"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_7) {load.username += "7"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_8) {load.username += "8"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_9) {load.username += "9"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_0) {load.username += "0"; load.saves.set(load.player,load.username);}
				if (key == KeyEvent.VK_SPACE) {load.username += "  "; load.saves.set(load.player,load.username);}
			}
			if (key == KeyEvent.VK_BACK_SPACE) {
				String temp = "";
				for (int i = 0; i < load.saves.get(load.player).length()-1; i++) temp += load.username.charAt(i);
				load.username = temp;
				load.saves.set(load.player, load.username);
			}
			if (key == KeyEvent.VK_ENTER) {
				if (load.user == load.numUsers-load.player)load.bounds = load.getBounds(load.saves.get(load.player));
				load.username = "";
				load.saveColors[load.player] = Color.darkGray;
				load.saveUser();
				load.newName = false;
			}
		}
	}
}
