package main;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import ai.Pathfinder;
import entity.Entity;
import entity.Player;
import enviorment.EnviormentManager;
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
	public final int maxMap = 10;
	public int currentMap = 0;
	
	// FOR FULLSCREEN
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreenOn = false;
	
	// FPS
	final int FPS = 60; // 60 FPS
	
	// SYSTEM
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound se = new Sound();
	Sound music = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Config config = new Config(this);
	public Pathfinder pFinder = new Pathfinder(this);
	EnviormentManager eManager = new EnviormentManager(this); 
	Thread gameThread;
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public Entity obj[][] = new Entity[maxMap][30]; //Display up to 30 objects at once in game (for performance. subject to change)
	public Entity npc[][] = new Entity[maxMap][10]; 
	public Entity monster[][] = new Entity[maxMap][20];
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
//	public ArrayList<Entity> projectileList = new ArrayList<>(); // OBSOLETE
	public Entity projectile[][] = new Entity[maxMap][20];
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
	public final int transitionState = 7;
	public final int tradeState = 8;
	
	
	// Set player's default location
//	int playerX = 100;
//	int playerY = 100;
//	int playerSpeed = 4;
	
	public GamePanel () {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		// For Better rendering and performance:
		this.setDoubleBuffered(true); // all the drawing from this component will be done in an offscreen painting buffer
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		currentMap = 0;
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
		eManager.setup();
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
			for(int i=0; i<npc[1].length; i++) {
				if(npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}
			//Monster
			for(int i=0; i<monster[1].length; i++) {
				if(monster[currentMap][i] != null) {
					if(monster[currentMap][i].alive ==true && monster[currentMap][i].dying == false) {
						monster[currentMap][i].update();
					}
					if(monster[currentMap][i].alive ==false) {
						monster[currentMap][i].checkDrop();
						monster[currentMap][i] = null;
					}	
				}
			}
			//Projectile
			for(int i=0; i<projectile[1].length; i++) {
				if(projectile[currentMap][i] != null) {
					if(projectile[currentMap][i].alive ==true) {
						projectile[currentMap][i].update();
					}
					if(projectile[currentMap][i].alive ==false) {
						projectile[currentMap][i] = null;
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
			for(int i=0; i<iTile[1].length; i++) {
				if(iTile[currentMap][i] != null) {
					iTile[currentMap][i].update();
				}
			}
			eManager.update();
			
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
					for(int i = 0; i<iTile[1].length; i++) {
						if(iTile[currentMap][i] != null) {
							iTile[currentMap][i].draw(g2);
						}
					}
					// ADD ENTITIES TO THE LIST
					entityList.add(player);
					//NPCs
					for(int i=0; i<npc[1].length; i++) {
						if(npc[currentMap][i] != null) {
							entityList.add(npc[currentMap][i]);
						}
					}
					//Objects
					for(int i=0; i<obj[1].length; i++) {
						if(obj[currentMap][i] != null) {
							entityList.add(obj[currentMap][i]);
						}
					}
					//Monsters
					for(int i=0; i<monster[1].length; i++) {
						if(monster[currentMap][i] != null) {
							entityList.add(monster[currentMap][i]);
						}
					}
					//Projectiles
					for(int i=0; i<projectile[1].length; i++) {
						if(projectile[currentMap][i] != null) {
							entityList.add(projectile[currentMap][i]);
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
					
					//ENVIORMENT
					eManager.draw(g2);
					
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
