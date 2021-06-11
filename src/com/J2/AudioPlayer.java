package com.J2;

import java.io.File;
import java.io.IOException;
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

		try {

			AudioInputStream menuSound = AudioSystem.getAudioInputStream(new File(filePath));
			play = AudioSystem.getClip();
			play.open(menuSound);
			FloatControl volume = (FloatControl) play.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(vol) / Math.log(10) * 20);
			volume.setValue(dB);
			if (Settings.sound) play.start();

		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e){

			e.printStackTrace();
		}
	}

	public void playGameSound(String filePath, double vol) {

		try {
			AudioInputStream gameSound = AudioSystem.getAudioInputStream(new File(filePath));
			play2 = AudioSystem.getClip();
			play2.open(gameSound);
			FloatControl volume = (FloatControl) play2.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(vol) / Math.log(10) * 20);
			volume.setValue(dB);
			if (Settings.music) play2.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e){

			e.printStackTrace();
		}
	}

	public void stopMusic() {
		play2.stop();
	}
}