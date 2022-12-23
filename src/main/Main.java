package main;

import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
	
	public static JFrame window;

	public static void main(String[] args) {
		
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Fantastic World");
		//window.setUndecorated(true); //removes topbar
		ImageIcon icon = new ImageIcon("/player/character_03");
		window.setIconImage(icon.getImage());
		window.setCursor(window.getToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), null));
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		gamePanel.config.loadConfig();
		if(gamePanel.fullScreenOn == true) {
			window.setUndecorated(true);
		}
		
		window.pack(); // window gets sized to fit everything 
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.setupGame();
		
		gamePanel.startGameThread();
	}

}
