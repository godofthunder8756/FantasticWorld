package main;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tiles
	final int scale = 3; //scale everything up by 3 cuz shit's too small. 
	
	public final int tileSize = originalTileSize * scale; // Makes tiles 48x48 IRL
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;  //4x3 ratio
	public final int screenWidth = tileSize * maxScreenCol; //48*20 =  960px
	public final int screenHeight = tileSize * maxScreenRow; //48*12 = 576px
	
	// WORLD SETTING
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	// FOR FULLSCREEN
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreenOn = false;
	
	// FPS
	final int FPS = 60; // 60 FPS
	
	// SYSTEM
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound se = new Sound();
	Sound music = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Config config = new Config(this);
	Thread gameThread;
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public Entity obj[] = new Entity[30]; //Display up to 30 objects at once in game (for performance. subject to change)
	public Entity npc[] = new Entity[10]; 
	public Entity monster[] = new Entity[20];
	public InteractiveTile iTile[] = new InteractiveTile[50];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();
	
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int optionsState = 5;
	public final int gameOverState = 6;
	
	
	// Set player's default location
//	int playerX = 100;
//	int playerY = 100;
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
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
//		playMusic(0);                      //STARTS MUSIC
		gameState = titleState;
		//FULLSCREEN
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)tempScreen.getGraphics();
		
		if(fullScreenOn == true) {
			setFullscreen();
		}
	}
	
	public void retry() {
		player.setDefaultPositions();
		player.restoreLifeAndMana();
		aSetter.setNPC();
		aSetter.setMonster();
	}
	public void restart() {
		stopMusic();
		player.setDefaultValues();
		player.setDefaultPositions();
		player.restoreLifeAndMana();
		player.setItems();
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
	}
	
	public void setFullscreen() {
		// GET MONITOR SPECS
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		// GET FULL SCREEN WIDTH AND HEIGHT
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
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
				drawToTempScreen();
				drawToScreen();
				delta--;
				drawCount++;
			}
			if(timer >= 1000000000) {
				//System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
					
		}
		
	}
	public void update(){
		if(gameState == playState) {
			//Player
			player.update();
			//NPC
			for(int i=0; i<npc.length; i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
			//Monster
			for(int i=0; i<monster.length; i++) {
				if(monster[i] != null) {
					if(monster[i].alive ==true && monster[i].dying == false) {
						monster[i].update();
					}
					if(monster[i].alive ==false) {
						monster[i].checkDrop();
						monster[i] = null;
					}	
				}
			}
			//Projectile
			for(int i=0; i<projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					if(projectileList.get(i).alive ==true) {
						projectileList.get(i).update();
					}
					if(projectileList.get(i).alive ==false) {
						projectileList.remove(i);
					}	
				}
			}
			//Particle
			for(int i=0; i<particleList.size(); i++) {
				if(particleList.get(i) != null) {
					if(particleList.get(i).alive ==true) {
						particleList.get(i).update();
					}
					if(particleList.get(i).alive ==false) {
						particleList.remove(i);
					}	
				}
			}
			// Interactive Tiles (ie drytrees)
			for(int i=0; i<iTile.length; i++) {
				if(iTile[i] != null) {
					iTile[i].update();
				}
			}
			
		}
		if(gameState == pauseState) {
			// fucking keel over and die
		}
		
		
	}
	
	public void drawToTempScreen() {
		// TITLE SCREEN
				if(gameState == titleState) {
					ui.draw(g2);
					
				}
				//OTHERS
				else {
					//TILE
					tileM.draw(g2);
					//  Interactive TIles
					for(int i = 0; i<iTile.length; i++) {
						if(iTile[i] != null) {
							iTile[i].draw(g2);
						}
					}
					// ADD ENTITIES TO THE LIST
					entityList.add(player);
					//NPCs
					for(int i=0; i<npc.length; i++) {
						if(npc[i] != null) {
							entityList.add(npc[i]);
						}
					}
					//Objects
					for(int i=0; i<obj.length; i++) {
						if(obj[i] != null) {
							entityList.add(obj[i]);
						}
					}
					//Monsters
					for(int i=0; i<monster.length; i++) {
						if(monster[i] != null) {
							entityList.add(monster[i]);
						}
					}
					//Projectiles
					for(int i=0; i<projectileList.size(); i++) {
						if(projectileList.get(i) != null) {
							entityList.add(projectileList.get(i));
						}
					}
					//Particles
					for(int i=0; i<particleList.size(); i++) {
						if(particleList.get(i) != null) {
							entityList.add(particleList.get(i));
						}
					}
					
					// SORT
					Collections.sort(entityList, new Comparator<Entity>() {
						@Override
						public int compare(Entity e1, Entity e2) {
							int result = Integer.compare(e1.worldY, e2.worldY);
							return result;
						}		
					}
					);
					
					// Draw Entity List
					for(int i=0; i<entityList.size(); i++) { entityList.get(i).draw(g2);}
					// Empty Entity List
					entityList.clear();
					
					//UI
					ui.draw(g2);
				}
	}
	public void drawToScreen() {
		
		Graphics g =getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
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
