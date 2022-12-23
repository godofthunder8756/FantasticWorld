package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;


public class Entity {
	
	GamePanel gp;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, upIdle, downIdle, leftIdle, rightIdle;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public BufferedImage image, image2 , image3;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public Rectangle attackArea = new Rectangle();
	public int solidAreaDefaultX, solidAreaDefaultY;
	String dialogue[] = new String[50];
	public boolean collision = false;
	
	//COUNTERS
	public int spriteCounter = 0;
	public int actionLockCounter = 0;
	public int invincibleCounter = 0;
	int dyingCounter = 0;
	int hpBarCounter = 0;
	public int shotAvailableCOunter;

	// STATE
	public int worldX, worldY;
	public String direction = "down";
	public int spriteNum = 1;
	int dialogueIndex = 0;
	public boolean invincible = false;
	public boolean collisionOn = false;
	boolean attacking = false;
	public boolean dying = false;
	public boolean alive = true;
	boolean hpBarOn = false;
	
	// CHARACTER ATTRIBUTES
	public String name;
	public int speed;
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public Entity currentWeapon; //Instantiated in player.setDefault()
	public Entity currentSheild; //^
	public Projectile projectile;//^ 
	
	// ITEM ATTRIBUTES
	public int value;
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int useCost;
	
	//TYPE
	public int type; //0 = player, 1 = npc, 2 = monster
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_pickupOnly = 7;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAction() {} //override
	
	public void damageReaction() {} //override
	
	public void speak() {
		if(dialogue[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogue[dialogueIndex];
		dialogueIndex++;
		
		switch(gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
	}
	public void use(Entity entity) {} //gets overrided
	
	public void checkDrop() {} 
	
	public void dropItem(Entity droppedItem) {
		for(int i = 0; i<gp.obj[1].length; i++) {
			if(gp.obj[gp.currentMap][i] == null) { //see if there's room in the array
				gp.obj[gp.currentMap][i] = droppedItem;
				gp.obj[gp.currentMap][i].worldX = worldX; //where monster was
				gp.obj[gp.currentMap][i].worldY = worldY;
				break; //the two hour bug
			}
		}
	}
	//PARTICLE STUFF ----OVERRIDE IN SUBCLASS
	public Color getParticleColor() {
		Color color = null;
		return color;
	}
	public int getParticleSize() {
		int size = 0;
		return size; //6px
	}
	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}
	public void generateParticle(Entity generator, Entity target) {
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
		
		Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
		gp.particleList.add(p1);
		Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
		gp.particleList.add(p2);
		Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
		gp.particleList.add(p3);
		Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
		gp.particleList.add(p4);
	}
	
	public void update() {
		setAction();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.iTile);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		//When Monster touches Player
		if(this.type == type_monster && contactPlayer == true) {
			damagePlayer(attack);
		}
		
		if(collisionOn == false) {
			switch(direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
			}
		}
		
		spriteCounter++;
		if(spriteCounter > 12) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum ==2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		// Invincibility
		if(invincible==true) {
			invincibleCounter++;
			if(invincibleCounter > 40) {
				invincible = false;
				invincibleCounter = 0;
				
			}
		}
		// Shoot cooldown timer
		if(shotAvailableCOunter < 30) {
			shotAvailableCOunter++;
		}
		
	}
	
	public void damagePlayer(int attack) {
		if(gp.player.invincible == false) {
			//damage
			gp.playSE(6);
			int damage = attack - gp.player.defense;
			if(damage < 0) {
				damage = 0;
			}
			gp.player.life -=damage;
			gp.player.invincible= true;
		}
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX && 
			worldX - gp.tileSize< gp.player.worldX + gp.player.screenX && 
			worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize< gp.player.worldY + gp.player.screenY) {
			
			switch(direction) {
			case "up":
				if(spriteNum == 1) { image = up1;}
				if(spriteNum == 2) { image = up2;}
				break;
			case "down":
				if(spriteNum == 1) { image = down1;}
				if(spriteNum == 2) { image = down2;}
				break;
			case "left":
				if(spriteNum == 1) { image = left1;}
				if(spriteNum == 2) { image = left2;}
				break;
			case "right":
				if(spriteNum == 1) { image = right1;}
				if(spriteNum == 2) { image = right2;}
				break;
			}	
			
			// Monster HP Bar
			if(type == 2 && hpBarOn == true) {
				
				double oneScale = (double)gp.tileSize/maxLife;
				double hpBarValue = oneScale*life;
				
				g2.setColor(new Color(35,35,35));                      //Boarder
				g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);
				g2.setColor(new Color(255,0,30));					   //Meter
				g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
				
				hpBarCounter++;
				if(hpBarCounter > 300) { //5 sec
					hpBarCounter = 0;
					hpBarOn =false;
				}
				
			}
			
			// Invincibility
			if(invincible==true) { hpBarOn = true; hpBarCounter = 0; changeAlpha(g2, 0.4F);}
			if(dying==true) { dyingAnimation(g2);}
			g2.drawImage(image, screenX, screenY, null);
			//Reset Invincibility alpha
			changeAlpha(g2, 1F);
		}
	}
	
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		
		int i = 5; //speed
		
		if(dyingCounter <= 5) { changeAlpha(g2, 0f);}
		if(dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2, 1f);}
		if(dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2, 0f);}
		if(dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2, 1f);}
		if(dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2, 0f);}
		if(dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2, 1f);}
		if(dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2, 0f);}
		if(dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2, 1f);}
		if(dyingCounter > i*8) { alive = false;}
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	
	public BufferedImage setup(String imagePath, int width, int height) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
