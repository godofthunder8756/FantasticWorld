package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.tree.AbstractLayoutCache;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity{
	
	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	int standCounter = 0;
	public boolean attackCancelled = false;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20; //cap
	
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
		
//		attackArea.width = 38;          //Attack radius, MAYBE 48????
//		attackArea.height = 38;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}
	public void setDefaultValues() {
//		worldX = gp.tileSize *23;
//		worldY = gp.tileSize *21;
		worldX = gp.tileSize *10;
		worldY = gp.tileSize *13;
		speed = 4;
		direction = "down";
		
		// Player Status
		level = 1;
		maxLife = 6;
		life = maxLife;
		strength = 1;  // More strength = more damage given
		dexterity = 1; // More dexterity = less damage recieved
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentSheild = new OBJ_Shield_Wood(gp);
		projectile = new OBJ_Fireball(gp);
		attack = getAttack();
		defense = getDefense();
	}
	
	public void setItems() {
		inventory.add(currentWeapon);
		inventory.add(currentSheild);
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
	}
	
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		return attack = strength*currentWeapon.attackValue;
	}
	public int getDefense() {
		return defense = dexterity*currentSheild.defenseValue;
		
	}
	
	public void getPlayerImage() {	
		up1 = setup("/player/character_08", gp.tileSize, gp.tileSize);
		up2 = setup("/player/character_09", gp.tileSize, gp.tileSize);
		down1 = setup("/player/character_00", gp.tileSize, gp.tileSize);
		down2 = setup("/player/character_01", gp.tileSize, gp.tileSize);
		left1 = setup("/player/character_05", gp.tileSize, gp.tileSize);
		left2 = setup("/player/character_04", gp.tileSize, gp.tileSize);
		right1 = setup("/player/character_07", gp.tileSize, gp.tileSize);
		right2 = setup("/player/character_06", gp.tileSize, gp.tileSize);
		upIdle = setup("/player/character_10", gp.tileSize, gp.tileSize);
		downIdle = setup("/player/character_03", gp.tileSize, gp.tileSize);
		leftIdle = setup("/player/character_04", gp.tileSize, gp.tileSize);
		rightIdle = setup("/player/character_06", gp.tileSize, gp.tileSize);	
	}
	
	public void getPlayerAttackImage() {
		if(currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
		}
		if(currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
		}
		
	}
	
	public void update() {
		
		if(attacking == true) {
			attacking();
		}
		else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
			if(keyH.upPressed == true) {direction = "up";}
			else if(keyH.downPressed == true) {direction = "down";}
			else if(keyH.leftPressed == true) {direction = "left";}
			else if(keyH.rightPressed == true) {direction = "right";}
			
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
			if(collisionOn == false) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			if(keyH.enterPressed == true && attackCancelled == false) {
				gp.playSE(7);
				attacking = true;
				spriteCounter = 0;
			}
			attackCancelled = false;
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
		//Projectile Shooting
		if(gp.keyH.shootKeyPressed == true && projectile.alive == false && shotAvailableCOunter == 30) { //ONLY SHOOT ONE AT A TIME
			//Set Default data
			projectile.set(worldX, worldY, direction, true, this);
			
			//add to the list
			gp.projectileList.add(projectile);
			shotAvailableCOunter = 0;
			gp.playSE(10);
			
		}
		
		if(invincible==true) {
			invincibleCounter++;
			if(invincibleCounter >60) {
				invincible = false;
				invincibleCounter = 0;
				
			}
		}
		// Shoot cooldown timer
		if(shotAvailableCOunter < 30) {
			shotAvailableCOunter++;
		}
	}
	
	public void attacking() {
		
		spriteCounter++;
		
		if(spriteCounter <= 5) { //5 frames
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <= 25) { //20 frames
			spriteNum = 2;
			// Store current worldx and worldy
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			// Adjust worldx and world y of player
			switch (direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			// attackArea becomes solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			// Check monster collision with the updated worldX and worldY
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, attack);
			
			// After checking collision, restore world x and y
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter= 0;
			attacking = false;
		}
	}
	
	public void pickUpObject(int i) {
		if(i != 999) {
			String text;
			if(inventory.size() != maxInventorySize) {
				inventory.add(gp.obj[i]);
				gp.playSE(1);
				text = "Obtained: "+gp.obj[i].name+"!";
			}
			else {
				text = "Your inventory is full!";
			}
			gp.ui.addMessage(text);
			gp.obj[i] = null;
		}
	}
	
	public void interactNPC(int i) {
		if(gp.keyH.enterPressed == true) {
			if(i != 999) {
				attackCancelled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
	}
	
	public void contactMonster(int i){
		if(i != 999) {
			if(invincible == false && gp.monster[i].dying == false) {
				gp.playSE(6);
				
				int damage = gp.monster[i].attack - gp.player.defense;
				if(damage < 0) {
					damage = 0;
				}
				life -=damage;
				invincible = true;
			}					
		}
	}
	
	public void damageMonster(int i, int attack) {
		if(i != 999) {
			System.out.println("Hit");  //DEBUG
			if(gp.monster[i].invincible == false) {
				gp.playSE(5);
				
				int damage = attack - gp.monster[i].defense;
				if(damage < 0) {
					damage = 0;
				}
				
				gp.monster[i].life -= damage;
				gp.ui.addMessage(damage + " damage!");
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
				
				if(gp.monster[i].life <= 0) {
					gp.monster[i].dying = true;
					gp.ui.addMessage(gp.monster[i].name + " killed!");
					gp.ui.addMessage("+"+gp.monster[i].exp+" EXP");
					exp += gp.monster[i].exp;
					checkLevelUp();
				}
			}
		}
		else {
			System.out.println("Miss"); //DEBUG
		}
	}
	public void checkLevelUp() {
		if(exp >= nextLevelExp) {
			level++;
			nextLevelExp =  nextLevelExp*2; //-----------------------------------------------------ADJUST
			maxLife+=2; //----------------------------------------------------------------------/
			life+=2; //------------------------------------------------------------------------/
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			gp.playSE(8);
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "LEVEL " + level +"!\n" + "You feel stronger now!";
		}
	}
	
	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot();
		if(itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			if(selectedItem.type == type_shield) {
				currentSheild = selectedItem;
				defense = getDefense();
				
			}
			if(selectedItem.type == type_consumable) {

				selectedItem.use(this);
				inventory.remove(itemIndex);
				
			}
		}
	}
	
	public void draw(Graphics2D g2 ){
		//g2.setColor(Color.red);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		case "up":
			if(attacking == false) {
				if(spriteNum == 1) {image = up1;}
				if(spriteNum == 2) {image = up2;}
				//if(spriteNum == 3) {image = upIdle;}				
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) {image = attackUp1;}
				if(spriteNum == 2) {image = attackUp2;}
			}
			break;
		case "down":
			if(attacking == false) {
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
				//if(spriteNum == 3) {image = downIdle;}				
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = attackDown1;}
				if(spriteNum == 2) {image = attackDown2;}
			}
			break;
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) {image = left1;}
				if(spriteNum == 2) {image = left2;}
				//if(spriteNum == 3) {image = leftIdle;}				
			}
			if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum == 1) {image = attackLeft1;}
				if(spriteNum == 2) {image = attackLeft2;}				
			}
			break;
		case "right":
			if(attacking == false) {
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
				//if(spriteNum == 3) {image = rightIdle;}
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = attackRight1;}
				if(spriteNum == 2) {image = attackRight2;}				
			}
			break;
		}
		// Invincibility
		if(invincible==true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		//Reset Invincibility alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

}
