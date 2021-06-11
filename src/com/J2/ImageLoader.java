package com.J2;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageLoader {
	private BufferedImage image;
	
	public BufferedImage loadImage(String path) {
		try {image = ImageIO.read(new File(path));}
		catch(Exception e) {e.printStackTrace();}
		return image;
	}
}
