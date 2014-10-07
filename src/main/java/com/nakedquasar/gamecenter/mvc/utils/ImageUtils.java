package com.nakedquasar.gamecenter.mvc.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.tomcat.util.codec.binary.Base64;

public class ImageUtils {
	public static String resize(String img, int newW, int newH) {
		BufferedImage image = resize(decodeToImage(img), newW, newH);

		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, "png", bos);
			byte[] imageBytes = bos.toByteArray();
			imageString = Base64.encodeBase64String(imageBytes);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageString;
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		BufferedImage newImage = null;
		try {
			newImage = Thumbnails.of(img)
			        .size(newW, newH)
			        .asBufferedImage();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newImage;
	}

	public static BufferedImage decodeToImage(String imageString) {

		BufferedImage image = null;
		byte[] imageByte;
		try {
			imageByte = Base64.decodeBase64(imageString);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
}
