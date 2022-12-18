package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
	
	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	int standCounter = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;           //MAYBE change both to 28 
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
//		worldX = gp.tileSize *23;
//		worldY = gp.tileSize *21;
		worldX = gp.tileSize *10;
		worldY = gp.tileSize *13;
		speed = 4;
		direction = "down";
		
		// Player Status
		maxLife = 6;
		life = maxLife;
	}
	
	public void getPlayerImage() {	
		up1 = setup("/player/character_08");
		up2 = setup("/player/character_09");
		down1 = setup("/player/character_00");
		down2 = setup("/player/character_01");
		left1 = setup("/player/character_05");
		left2 = setup("/player/character_04");
		right1 = setup("/player/character_07");
		right2 = setup("/player/character_06");
		upIdle = setup("/player/character_10");
		downIdle = setup("/player/character_03");
		leftIdle = setup("/player/character_04");
		rightIdle = setup("/player/character_06");
		
	}
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
			if(keyH.upPressed == true) {
				direction = "up";
			}
			else if(keyH.downPressed == true) {
				direction = "down";		
			}
			else if(keyH.leftPressed == true) {
				direction = "left";			
			}
			else if(keyH.rightPressed == true) {
				direction = "right";			
			}
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// CHECK NPC COLLISION
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// CHECK EVENT
			gp.eHandler.checkEvent();
			
			
			
			// IF COLLISION IS FLASE< PLAYER CAN MOVE
			if(collisionOn == false || keyH.enterPressed == false) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			gp.keyH.enterPressed = false;
			
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
		}	
		// OUTSIDE of key if statement
		if(invincible==true) {
			invincibleCounter++;
			if(invincibleCounter >60) {
				invincible = false;
				invincibleCounter = 0;
				
			}
		}
	}
	
	public void pickUpObject(int i) {
		if(i != 999) {
			// empty
		}
	}
	
	public void interactNPC(int i) {
		if(i != 999) {
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
	}
	
	public void contactMonster(int i){
		if(i != 999) {
			if(invincible == false) {
				life -=1;
				invincible = true;
			}					
		}
	}
	
	public void draw(Graphics2D g2 ){
		//g2.setColor(Color.red);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			if(spriteNum == 3) {
				image = upIdle;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			if(spriteNum == 3) {
				image = downIdle;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			if(spriteNum == 3) {
				image = leftIdle;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			if(spriteNum == 3) {
				image = rightIdle;
			}
			break;
		}
		// Invincibility
		if(invincible==true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		g2.drawImage(image, screenX, screenY, null);
		
		//Reset Invincibility alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

}
