package com.J2;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class HUD {
	private LoadGame load;

	public static float HEALTH = 100;
	public static float HEALTH2 = 100;
	public static int high_score = 0;

	private int score, score2 = 0;
	private int level = 1;
	private int level2 = 1;

	public HUD(LoadGame load) {
		this.load = load;
	}

	public boolean is_high_score(int num) {
		return num > high_score;
	}

	public void tick() {

		HEALTH = Utils.clamp(HEALTH, 0, 100);
		HEALTH2 = Utils.clamp(HEALTH2, 0, 100);
		
		score = (int) (Utils.clamp(score, 0, 99999999));
		score2 = (int) (Utils.clamp(score2, 0, 99999999));

		if (HEALTH > 0)
			score++;
		if (HEALTH2 > 0 && Game.multiplayer)
			score2++;

		level = (int) Utils.clamp(level, 0, 10);
		level2 = (int) Utils.clamp(level2, 0, 10);
	}

	public void render(Graphics g) {
		int bar_width = 300;
		float multiplyer = bar_width / 100;

		g.setFont(Game.titleFont3);
		g.setColor(Color.gray);
		g.fillRect(15, 17, bar_width, 8);
		g.setColor(Color.getHSBColor((HEALTH) / 360, 1f, 1f));
		g.fillRect(15, 17, (int) (HEALTH * multiplyer), 8);
		g.setColor(Settings.darkMode ? Color.white : Color.black);
		g.drawRect(15, 17, bar_width, 8);

		g.drawString("Score: " + score, 15, 40);
		g.drawString("Level: " + level, 15, 55);

		if (Game.multiplayer) {
			int bar_X = Game.WIDTH - bar_width - 32;

			g.setColor(Color.gray);
			g.fillRect(bar_X, 17, bar_width, 8);
			g.setColor(Color.getHSBColor((HEALTH2) / 360, 1f, 1f));
			g.fillRect(bar_X, 17, (int) (HEALTH2 * multiplyer), 8);
			g.setColor(Settings.darkMode ? Color.white : Color.black);
			g.drawRect(bar_X, 17, bar_width, 8);

			FontMetrics fm = g.getFontMetrics();
			int p2_width = fm.stringWidth("Player 2");
			int score_width = fm.stringWidth("Score: " + score2);
			int lvl_width = fm.stringWidth("Level: " + level2);

			g.drawString(load.user == 0 ? "Player 1" : load.saves.get(load.user - 1), 15, 13);
			g.drawString("Player 2", Game.WIDTH - p2_width - 32, 13);
			g.drawString("Score: " + score2, Game.WIDTH - score_width - 32, 40);
			g.drawString("Level: " + level2, Game.WIDTH - lvl_width - 32, 55);
		}
	}

	public int get_score(int player) {
		return player == 1 ? score : score2;
	}

	public void set_score(int player, int score) {
		if (player == 1)
			this.score = score;
		else
			this.score2 = score;
	}

	public int get_level(int level_type) {
		return level_type == 1 ? level : level2;
	}

	public void set_level(int level_type, int level) {
		if (level_type == 1)
			this.level = level;
		else
			this.level2 = level;
	}
}
