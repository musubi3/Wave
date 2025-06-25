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
			hud.set_level(1, hud.get_level(1) + 1);

			if (Settings.difficulty == 0) {
				if (hud.get_level(1) == 2) {
					handler.add_object(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.get_level(1) == 3) {
					handler.add_object(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
					handler.add_object(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					handler.add_object(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
				}
				else if (hud.get_level(1) == 4) {
					handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.get_level(1) == 5) {
					handler.add_object(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player, Color.magenta, handler, hud));
					boolean player2Alive = false;
					for(int i = 0; i < handler.object.size() && !player2Alive; i++) {
						GameObject tempObject = handler.object.get(i);
						if (tempObject.getID() == ID.Player2) player2Alive = true;
					}
					if (player2Alive) handler.add_object(new SmartEnemy(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.SmartEnemy, ID.Player2, new Color(160,0,255), handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.Coin, handler, audio));
					handler.add_object(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
				}
				else if (hud.get_level(1) == 6) {
					handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.get_level(1) == 7) {
					handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					handler.add_object(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
				}
				else if (hud.get_level(1) == 8) {
					handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.get_level(1) == 9) {
					handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					handler.add_object(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
				}
				else if (hud.get_level(1) == 10) {
					handler.add_object(new BossEnemy(Game.WIDTH / 2 -48, -120, ID.BossEnemy, handler));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
			}
			else {
				if (hud.get_level(1) == 2) {
					handler.add_object(new HardEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.HardEnemy, handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.get_level(1) == 3) {
					handler.add_object(new HardEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.HardEnemy, handler, hud));
					handler.add_object(new HardEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.HardEnemy, handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.get_level(1) == 4) {
					handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.get_level(1) == 5) {
					handler.add_object(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player, Color.magenta, handler, hud));
					boolean player2Alive = false;
					for(int i = 0; i < handler.object.size() && !player2Alive; i++) {
						GameObject tempObject = handler.object.get(i);
						if (tempObject.getID() == ID.Player2) player2Alive = true;
					}
					if (player2Alive) handler.add_object(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player2, new Color(160,0,255), handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					handler.add_object(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
				}
				else if (hud.get_level(1) == 6) {
					handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.get_level(1) == 7) {
					handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.get_level(1) == 8) {
					handler.add_object(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player, Color.magenta, handler, hud));
					boolean player2Alive = false;
					for(int i = 0; i < handler.object.size() && !player2Alive; i++) {
						GameObject tempObject = handler.object.get(i);
						if (tempObject.getID() == ID.Player2) player2Alive = true;
					}
					if (player2Alive) handler.add_object(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player2, new Color(160,0,255), handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
				else if (hud.get_level(1) == 9) {
					handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					handler.add_object(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
				}
				else if (hud.get_level(1) == 10) {
					handler.add_object(new BossEnemy(Game.WIDTH / 2 -48, -120, ID.BossEnemy, handler));
					handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
				}
			}
		}
		if (scoreKeep2 >= nxtLevel) {
			scoreKeep2 = 0;
			hud.set_level(2, hud.get_level(2) + 1);
			if (Player.dead) {
				for(int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getID() == ID.SmartEnemy) {
						if (((SmartEnemy) tempObject).getTarget() == ID.Player) {
							handler.object.remove(i);
						}
					}
				}
				if (Settings.difficulty == 0) {
					if (hud.get_level(2) == 2 && hud.get_level(1) != 2) {
						handler.add_object(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.get_level(2) == 3 && hud.get_level(1) != 3) {
						handler.add_object(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
						handler.add_object(new BasicEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.add_object(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
					}
					else if (hud.get_level(2) == 4 && hud.get_level(1) != 4) {
						handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.get_level(2) == 5  && hud.get_level(1) != 2) {
						handler.add_object(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player2, new Color(160,0,255), handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.add_object(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
					}
					else if (hud.get_level(2) == 6  && hud.get_level(1) != 6) {
						handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.get_level(2) == 7 && hud.get_level(1) != 7) {
						handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.add_object(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
					}
					else if (hud.get_level(2) == 8  && hud.get_level(1) != 8) {
						handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.get_level(2) == 9  && hud.get_level(1) != 9) {
						handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.add_object(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
					}
					else if (hud.get_level(2) == 10 && hud.get_level(1) != 10) {
						handler.add_object(new BossEnemy(Game.WIDTH / 2 -48, -120, ID.BossEnemy, handler));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
				}
				else {
					if (hud.get_level(2) == 2 && hud.get_level(1) != 2) {
						handler.add_object(new HardEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.HardEnemy, handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.get_level(2) == 3 && hud.get_level(1) != 3) {
						handler.add_object(new HardEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.HardEnemy, handler, hud));
						handler.add_object(new HardEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.HardEnemy, handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.get_level(2) == 4 && hud.get_level(1) != 4) {
						handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.get_level(2) == 5  && hud.get_level(1) != 2) {
						handler.add_object(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player2, new Color(160,0,255), handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.add_object(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
					}
					else if (hud.get_level(2) == 6  && hud.get_level(1) != 6) {
						handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.get_level(2) == 7 && hud.get_level(1) != 7) {
						handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.get_level(2) == 8  && hud.get_level(1) != 8) {
						handler.add_object(new SmartEnemy(r.nextInt(Game.WIDTH-75), r.nextInt(Game.HEIGHT-75), ID.SmartEnemy, ID.Player2, new Color(160,0,255), handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
					}
					else if (hud.get_level(2) == 9  && hud.get_level(1) != 9) {
						handler.add_object(new FastEnemy(r.nextInt(Game.WIDTH-27), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler, hud));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
						handler.add_object(new Health(r.nextInt(Game.WIDTH-250), -20, ID.healthPack, handler, audio));
					}
					else if (hud.get_level(2) == 10 && hud.get_level(1) != 10) {
						handler.add_object(new BossEnemy(Game.WIDTH / 2 -48, -120, ID.BossEnemy, handler));
						handler.add_object(new Coin(r.nextInt(Game.WIDTH-100), r.nextInt(Game.HEIGHT-100), ID.Coin, handler, audio));
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

