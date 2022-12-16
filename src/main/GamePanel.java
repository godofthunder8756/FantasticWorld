package main;

import java.awt.Color;
import java.awt.Dimension;

import javax.security.auth.x500.X500Principal;
import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tiles
	final int scale = 3; //scale everything up by 3 cuz shit's too small. 
	
	public final int tileSize = originalTileSize * scale; // Makes tiles 48x48 IRL
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;  //4x3 ratio
	public final int screenWidth = tileSize * maxScreenCol; //48*16 =  768px
	public final int screenHeight = tileSize * maxScreenRow; //48*12 = 576px
	
	// WORLD SETTING
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	final int FPS = 60; // 60 FPS
	
	// SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Sound se = new Sound();
	Sound music = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	Thread gameThread;
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10]; //Display up to ten objects at once in game (for performance. subject to change)
	
	
	// Set player's default location
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel () {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		// For Better rendering and performance:
		this.setDoubleBuffered(true); // all the drawing from this component will be done in an offscreen painting buffer
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		
		aSetter.setObject();
		playMusic(0);                      //STARTS MUSIC
	}
	
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
;	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; // 0.01666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
 		
		//Game Loop
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) /drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
					
		}
		
	}
	public void update(){
		player.update();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		//TILE
		tileM.draw(g2);
		
		//OBJECT
		for(int i = 0; i<obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		//PLAYER
		player.draw(g2);
		g2.dispose();
	}
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}

}
