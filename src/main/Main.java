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
		new Main().setIcon();
		
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
	
	public void setIcon() {
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("monster/redslime_down_1.png"));
		window.setIconImage(icon.getImage());
	}

}
