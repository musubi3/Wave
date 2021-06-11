package com.J2;

import java.awt.Color;
import java.util.Random;

public class Spawn{

	private Handler handler;
	private HUD hud;
	private AudioPlayer audio;
	private Random r = new Random();

	private int scoreKeep = 0;
	private int scoreKeep2 = 0; 

	public Spawn(Handler handler, HUD hud, AudioPlayer audio) {
		this.handler = handler;
		this.hud = hud;
		this.audio = audio;
	}

	public void tick() {
		int nxtLevel = 500;

		if (HUD.HEALTH > 0) scoreKeep++;
		if (HUD.HEALTH2 > 0 && Game.multiplayer) scoreKeep2++;

		if (scoreKeep >= nxtLevel) {
			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);

			if (Game.difficulty == 0) {
				if (hud.getLevel() == 2) {
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.getLevel() == 3) {
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					handler.addObject(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
				}
				else if (hud.getLevel() == 4) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.getLevel() == 5) {
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player, Color.magenta, handler, hud));
					boolean player2Alive = false;
					for(int i = 0; i < handler.object.size() && !player2Alive; i++) {
						GameObject tempObject = handler.object.get(i);
						if (tempObject.getID() == ID.Player2) player2Alive = true;
					}
					if (player2Alive) handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.SmartEnemy, ID.Player2, new Color(160,0,255), handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.Coin, handler, audio));
					handler.addObject(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
				}
				else if (hud.getLevel() == 6) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.getLevel() == 7) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					handler.addObject(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
				}
				else if (hud.getLevel() == 8) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.getLevel() == 9) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					handler.addObject(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
				}
				else if (hud.getLevel() == 10) {
					handler.addObject(new BossEnemy(Game.WIDTH / 2 -48, -120, ID.BossEnemy, handler));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
			}
			else {
				if (hud.getLevel() == 2) {
					handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.HardEnemy, handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.getLevel() == 3) {
					handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.HardEnemy, handler, hud));
					handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.HardEnemy, handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.getLevel() == 4) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.getLevel() == 5) {
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player, Color.magenta, handler, hud));
					boolean player2Alive = false;
					for(int i = 0; i < handler.object.size() && !player2Alive; i++) {
						GameObject tempObject = handler.object.get(i);
						if (tempObject.getID() == ID.Player2) player2Alive = true;
					}
					if (player2Alive) handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player2, new Color(160,0,255), handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					handler.addObject(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
				}
				else if (hud.getLevel() == 6) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.getLevel() == 7) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.getLevel() == 8) {
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player, Color.magenta, handler, hud));
					boolean player2Alive = false;
					for(int i = 0; i < handler.object.size() && !player2Alive; i++) {
						GameObject tempObject = handler.object.get(i);
						if (tempObject.getID() == ID.Player2) player2Alive = true;
					}
					if (player2Alive) handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player2, new Color(160,0,255), handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.getLevel() == 9) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					handler.addObject(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
				}
				else if (hud.getLevel() == 10) {
					handler.addObject(new BossEnemy(Game.WIDTH / 2 -48, -120, ID.BossEnemy, handler));
					handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
			}
		}
		if (scoreKeep2 >= nxtLevel) {
			scoreKeep2 = 0;
			hud.setLevel2(hud.getLevel2() + 1);
			if (Player.dead) {
				for(int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getID() == ID.SmartEnemy) {
						if (((SmartEnemy) tempObject).getTarget() == ID.Player) {
							handler.object.remove(i);
						}
					}
				}
				if (Game.difficulty == 0) {
					if (hud.getLevel2() == 2 && hud.getLevel() != 2) {
						handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.getLevel2() == 3 && hud.getLevel() != 3) {
						handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
						handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.addObject(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
					}
					else if (hud.getLevel2() == 4 && hud.getLevel() != 4) {
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.getLevel2() == 5  && hud.getLevel() != 2) {
						handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player2, new Color(160,0,255), handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.addObject(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
					}
					else if (hud.getLevel2() == 6  && hud.getLevel() != 6) {
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.getLevel2() == 7 && hud.getLevel() != 7) {
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.addObject(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
					}
					else if (hud.getLevel2() == 8  && hud.getLevel() != 8) {
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.getLevel2() == 9  && hud.getLevel() != 9) {
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.addObject(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
					}
					else if (hud.getLevel2() == 10 && hud.getLevel() != 10) {
						handler.addObject(new BossEnemy(Game.WIDTH / 2 -48, -120, ID.BossEnemy, handler));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
				}
				else {
					if (hud.getLevel2() == 2 && hud.getLevel() != 2) {
						handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.HardEnemy, handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.getLevel2() == 3 && hud.getLevel() != 3) {
						handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.HardEnemy, handler, hud));
						handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.HardEnemy, handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.getLevel2() == 4 && hud.getLevel() != 4) {
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.getLevel2() == 5  && hud.getLevel() != 2) {
						handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player2, new Color(160,0,255), handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.addObject(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
					}
					else if (hud.getLevel2() == 6  && hud.getLevel() != 6) {
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.getLevel2() == 7 && hud.getLevel() != 7) {
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.getLevel2() == 8  && hud.getLevel() != 8) {
						handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player2, new Color(160,0,255), handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.getLevel2() == 9  && hud.getLevel() != 9) {
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.addObject(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
					}
					else if (hud.getLevel2() == 10 && hud.getLevel() != 10) {
						handler.addObject(new BossEnemy(Game.WIDTH / 2 -48, -120, ID.BossEnemy, handler));
						handler.addObject(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
				}
			}
		}
		if (Player.dead2) {
			for(int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.getID() == ID.SmartEnemy) {
					if (((SmartEnemy) tempObject).getTarget() == ID.Player2) {
						handler.object.remove(i);
					}
				}
			}
		}
	}
}

