package com.J2;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader {
	private BufferedImage image;

	public BufferedImage loadImage(String path) {
		try {
			InputStream file = getClass().getResourceAsStream(path);
			image = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return image;
	}
}
