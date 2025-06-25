package com.J2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private AudioPlayer audio;
	private boolean[] keyDown = new boolean[4];
	private boolean[] keyDown2 = new boolean[4];

	public KeyInput(Handler handler, AudioPlayer audio) {
		this.handler = handler;
		this.audio = audio;

		for (int i = 0; i < keyDown.length; i++) {
			keyDown[i] = false;
		}
		for (int j = 0; j < keyDown2.length; j++) {
			keyDown2[j] = false;
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (Game.gameState == STATE.Game) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.getID() == ID.Player) {
					if (key == KeyEvent.VK_W) {
						tempObject.setVelY(-Player.speed);
						keyDown[0] = true;
					}
					if (key == KeyEvent.VK_S) {
						tempObject.setVelY(Player.speed);
						keyDown[1] = true;
					}
					if (key == KeyEvent.VK_D) {
						tempObject.setVelX(Player.speed);
						keyDown[2] = true;
					}
					if (key == KeyEvent.VK_A) {
						tempObject.setVelX(-Player.speed);
						keyDown[3] = true;
					}
					// if(key == KeyEvent.VK_SPACE) {
					// tempObject.setVelX(tempObject.getVelX() * 2.0f);
					// tempObject.setVelY(tempObject.getVelY() * 2.0f);
					// }
				}
				if (tempObject.getID() == ID.Player2) {
					if (key == KeyEvent.VK_UP) {
						tempObject.setVelY(-Player.speed);
						keyDown2[0] = true;
					}
					if (key == KeyEvent.VK_DOWN) {
						tempObject.setVelY(Player.speed);
						keyDown2[1] = true;
					}
					if (key == KeyEvent.VK_RIGHT) {
						tempObject.setVelX(Player.speed);
						keyDown2[2] = true;
					}
					if (key == KeyEvent.VK_LEFT) {
						tempObject.setVelX(-Player.speed);
						keyDown2[3] = true;
					}
					// if(key == KeyEvent.VK_CONTROL) {
					// tempObject.setVelX(tempObject.getVelX() * 2.0f);
					// tempObject.setVelY(tempObject.getVelY() * 2.0f);
					// }
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getID() == ID.Player) {
				if (key == KeyEvent.VK_W)
					keyDown[0] = false;
				if (key == KeyEvent.VK_S)
					keyDown[1] = false;
				if (key == KeyEvent.VK_D)
					keyDown[2] = false;
				if (key == KeyEvent.VK_A)
					keyDown[3] = false;
				// if(key == KeyEvent.VK_SPACE) {
				// tempObject.setVelX(tempObject.getVelX() / 2.0f);
				// tempObject.setVelY(tempObject.getVelY() / 2.0f);
				// }

				if (!keyDown[0] && !keyDown[1])
					tempObject.setVelY(0);
				if (!keyDown[2] && !keyDown[3])
					tempObject.setVelX(0);
			}
			if (tempObject.getID() == ID.Player2) {
				if (key == KeyEvent.VK_UP)
					keyDown2[0] = false;
				if (key == KeyEvent.VK_DOWN)
					keyDown2[1] = false;
				if (key == KeyEvent.VK_RIGHT)
					keyDown2[2] = false;
				if (key == KeyEvent.VK_LEFT)
					keyDown2[3] = false;
				// if(key == KeyEvent.VK_CONTROL) {
				// tempObject.setVelX(tempObject.getVelX() / 2.0f);
				// tempObject.setVelY(tempObject.getVelY() / 2.0f);
				// }

				if (!keyDown2[0] && !keyDown2[1])
					tempObject.setVelY(0);
				if (!keyDown2[2] && !keyDown2[3])
					tempObject.setVelX(0);
			}
		}
	}
}
