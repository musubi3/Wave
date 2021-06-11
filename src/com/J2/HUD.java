package com.J2;

import java.awt.Color;

import java.awt.Graphics;

public class HUD {
	
	public static float HEALTH = 100;
	public static float HEALTH2 = 100;
	public int highScore = 0;
	
	private int score, score2 = 0;
	private int level = 1;
	private int level2 = 1;
	
	public int isHighScore(int num) {
		highScore = Math.max(highScore, num);
		return highScore;
	}
	
	public void tick() {
		
		HEALTH = Game.clamp(HEALTH, 0, 100);
		HEALTH2 = Game.clamp(HEALTH2, 0, 100);
		
		if (HEALTH > 0) score++;
		if (HEALTH2 > 0 && Game.multiplayer) score2++;
		
		level = (int)Game.clamp(level, 0, 10);
		level2 = (int)Game.clamp(level2, 0, 10);
	}
	
	public void render(Graphics g) {
		
		g.setFont(Game.titleFont3);
		g.setColor(Color.gray);
		g.fillRect(15, 17, 200, 8);
		g.setColor(Color.getHSBColor((HEALTH)/360, 1f, 1f));
		g.fillRect(15, 17, (int)HEALTH*2, 8);
		if (Settings.darkMode) g.setColor(Color.white);
		else g.setColor(Color.black);
		g.drawRect(15, 17, 200, 8);
		
		if (Game.multiplayer) {
			g.setColor(Color.gray);
			g.fillRect(610, 17, 200, 8);
			g.setColor(Color.getHSBColor((HEALTH2)/360, 1f, 1f));
			g.fillRect(610, 17, (int)HEALTH2*2, 8);
			if (Settings.darkMode) g.setColor(Color.white);
			else g.setColor(Color.black);
			g.drawRect(610, 17, 200, 8);
			g.drawString("Player 1", 15, 13);
			g.drawString("Player 2", 755, 13);
			g.drawString("Score: " + score2, 737, 40);
			g.drawString("Level: " + level2, 737, 55);
		}
		
		g.drawString("Score: " + score, 15, 40);
		g.drawString("Level: " + level, 15, 55);
	}
	
	public void score(int score) {
		this.score = score;
	}
	public int getScore() {
		return score;
	}
	public void score2(int score) {
		this.score2 = score;
	}
	public int getScore2() {
		return score2;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setLevel2(int level) {
		this.level2 = level;
	}
	public int getLevel2() {
		return level2;
	}
}
