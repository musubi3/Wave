package com.J2;

import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

	private Clip play;
	private Clip play2;

	public void playMenuSound(String filePath, double vol) {
		if (!Settings.sound)
			return;

		try {
			InputStream file = getClass().getResourceAsStream(filePath);
			AudioInputStream menuSound = AudioSystem.getAudioInputStream(file);
			play = AudioSystem.getClip();
			play.open(menuSound);
			FloatControl volume = (FloatControl) play.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(vol) / Math.log(10) * 20);
			volume.setValue(dB);
			play.start();

		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {

			e.printStackTrace();
		}
	}

	public void playGameSound(String filePath, double vol) {
		if (!Settings.music)
			return;

		try {
			InputStream file = getClass().getResourceAsStream(filePath);
			AudioInputStream gameSound = AudioSystem.getAudioInputStream(file);
			play2 = AudioSystem.getClip();
			play2.open(gameSound);
			FloatControl volume = (FloatControl) play2.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(vol) / Math.log(10) * 20);
			volume.setValue(dB);
			play2.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {

			e.printStackTrace();
		}
	}

	public void stopMusic() {
		if (play2 == null)
			return;

		play2.stop();
	}
}