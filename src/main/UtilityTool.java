package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class UtilityTool {
	public BufferedImage scaleImage(BufferedImage original,int width, int height) {
		
		Image tmp = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		  Graphics2D g2d = dimg.createGraphics();
		  g2d.drawImage(tmp, 0, 0, null);
		  g2d.dispose();
		
		
		
		
//		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
//		BufferedImage newimg = (BufferedImage) BufferedImage.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH ) ;  
//		try {
//			Graphics2D graphics2d = scaledImage.createGraphics();
//			graphics2d.drawImage(original, 0, 0, width, height, null);
//			graphics2d.dispose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
		
		return dimg;
	}

}
