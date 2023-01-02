package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;
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
	public boolean lightUpdated = false;
	
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

	}
	public void getGuardImage() {
		guardUp = setup("/player/boy_guard_up", gp.tileSize, gp.tileSize);
		guardDown = setup("/player/boy_guard_down", gp.tileSize, gp.tileSize);
		guardLeft = setup("/player/boy_guard_left", gp.tileSize, gp.tileSize);
		guardRight = setup("/player/boy_guard_right", gp.tileSize, gp.tileSize);
	}
	public void setDefaultValues() {
//		worldX = gp.tileSize *23;  //FIRST TESTING
//		worldY = gp.tileSize *21;
//		worldX = gp.tileSize *12;
//		worldY = gp.tileSize *13;
		worldX = gp.tileSize *32;  
		worldY = gp.tileSize *21;
		defaultSpeed = 4;
		speed = defaultSpeed;
		direction = "down";
		
		// Player Status
		level = 1;
		maxLife = 6;
		life = maxLife;
		strength = 1;  // More strength = more damage given
		dexterity = 1; // More dexterity = less damage recieved
		exp = 0;
		nextLevelExp = 5;
		coin = 500; //----------------------------------------------------------DEBUG
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentSheild = new OBJ_Shield_Wood(gp);
		currentLight = null;
		projectile = new OBJ_Fireball(gp);
		attack = getAttack();
		defense = getDefense();
		getImage();
		getAttackImage();
		getGuardImage();
		setItems();
		setDialogue();
	}	
	public void setDefaultPositions() {
		worldX = gp.tileSize *32;
		worldY = gp.tileSize *21;
		direction = "down";
	}
	public void restoreStatus() {
		speed = defaultSpeed;
		life = maxLife;
		mana = maxMana;
		invincible = false;
		attacking = false;
		guarding = false;
		knockBack = false;
		lightUpdated = true;
	}
	public void setItems() {
		inventory.clear();
		inventory.add(currentWeapon); //Default
		inventory.add(currentSheild); //Default
		inventory.add(new OBJ_Key(gp)); //Default
		//inventory.add(new OBJ_Key(gp)); //Default
	}
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		motion1_duration = currentWeapon.motion1_duration;
		motion2_duration = currentWeapon.motion2_duration;
		return attack = strength*currentWeapon.attackValue;
	}
	public int getDefense() {
		return defense = dexterity*currentSheild.defenseValue;	
	}
	public void getImage() {	
//		up1 = setup("/player/man_up_1", gp.tileSize, gp.tileSize);
//		up2 = setup("/player/man_up_2", gp.tileSize, gp.tileSize);
//		down1 = setup("/player/man_down_1", gp.tileSize, gp.tileSize);
//		down2 = setup("/player/man_down_2", gp.tileSize, gp.tileSize);
//		left1 = setup("/player/man_left_1", gp.tileSize, gp.tileSize);
//		left2 = setup("/player/man_left_2", gp.tileSize, gp.tileSize);
//		right1 = setup("/player/man_right_1", gp.tileSize, gp.tileSize);
//		right2 = setup("/player/man_right_2", gp.tileSize, gp.tileSize);
//		downIdle = setup("/player/man_down_idle", gp.tileSize, gp.tileSize);
		
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
	public void getAttackImage() {
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
		else if (keyH.spacePressed == true) {
			guarding = true;
		}
		else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
			if(keyH.upPressed == true) {direction = "up";}
			else if(keyH.downPressed == true) {direction = "down";}
			else if(keyH.leftPressed == true) {direction = "left";}
			else if(keyH.rightPressed == true) {direction = "right";}
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this); //----------------------toggle me
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// CHECK NPC COLLISION
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// CHECK INTERACTIVE TILE COLLISION
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			
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
			guarding = false;
			
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
			guarding = false;
		}	
		// OUTSIDE of key if statement
		//Projectile Shooting
		if(gp.keyH.shootKeyPressed == true && projectile.alive == false && shotAvailableCOunter == 30) { //ONLY SHOOT ONE AT A TIME
			//Set Default data
			projectile.set(worldX, worldY, direction, true, this);
			
			//CHECK VACANCY
			for(int i = 0; i < gp.projectile[1].length; i++) {
				if(gp.projectile[gp.currentMap][i] == null) {
					gp.projectile[gp.currentMap][i] = projectile;
					break;
				}
			}
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
		if(life > maxLife) {
			life = maxLife;
		}
		if(mana > maxMana) {
			mana = maxMana;
		}
		if(life <= 0) {
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1;
			gp.stopMusic();
			//----------------------------------------------------------ADD GAME OVER MUSIC
			gp.playSE(12);
		}
	}
	public int getCurrentWeaponSlot() {
		int currentWeaponSlot = 0;
		for(int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i) == currentWeapon) {
				currentWeaponSlot = i;
			}
		}
		return currentWeaponSlot;
	}
	public int getCurrentShieldSlot() {
		int currentShieldSlot = 0;
		for(int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i) == currentSheild) {
				currentShieldSlot = i;
			}
		}
		return currentShieldSlot;
	}
	
	public void damageProjectile(int i) {
		if(i != 999) {
			Entity projectilEntity = gp.projectile[gp.currentMap][i];
			projectilEntity.alive = false;
			generateParticle(projectilEntity, projectilEntity);
		}
	}
	public void pickUpObject(int i) {
		if(i != 999) {
			// PICKUP ONLY
			if(gp.obj[gp.currentMap][i].type == type_pickupOnly){
				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i] = null;
			}
			// OBSTCLE
			else if(gp.obj[gp.currentMap][i].type == type_obstacle) {
				if(keyH.enterPressed == true) {
					attackCancelled = true;
					gp.obj[gp.currentMap][i].interact();
				}
			}
			// INVENTORY ITEMS
			else {
				String text;
				if(canObtainItem(gp.obj[gp.currentMap][i]) == true) {
					gp.playSE(1);
					text = "Obtained: "+gp.obj[gp.currentMap][i].name+"!";
				}
				else {
					text = "Your inventory is full!";
				}
				gp.ui.addMessage(text);
				gp.obj[gp.currentMap][i] = null;				
			}

		}
	}
	
	
	public void interactNPC(int i) {
		if(gp.keyH.enterPressed == true) {
			if(i != 999) {
				attackCancelled = true;
				gp.npc[gp.currentMap][i].speak();
			}
		}
	}
	
	public void contactMonster(int i){
		if(i != 999) {
			if(invincible == false && gp.monster[gp.currentMap][i].dying == false) {
				gp.playSE(6);
				
				int damage = gp.monster[gp.currentMap][i].attack - gp.player.defense;
				if(damage < 0) {
					damage = 0;
				}
				life -=damage;
				invincible = true;
			}					
		}
	}
	
	public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {
		if(i != 999) {
			System.out.println("Hit");  //DEBUG
			if(gp.monster[gp.currentMap][i].invincible == false) {
				gp.playSE(5);
				
				if(knockBackPower > 0) {
					setKnockBack(gp.monster[gp.currentMap][i], attacker, knockBackPower);
				}
				
				
				int damage = attack - gp.monster[gp.currentMap][i].defense;
				if(damage < 0) {
					damage = 0;
				}
				
				gp.monster[gp.currentMap][i].life -= damage;
				gp.ui.addMessage(damage + " damage!");
				gp.monster[gp.currentMap][i].invincible = true;
				gp.monster[gp.currentMap][i].damageReaction();
				
				if(gp.monster[gp.currentMap][i].life <= 0) {
					gp.monster[gp.currentMap][i].dying = true;
					gp.ui.addMessage(gp.monster[gp.currentMap][i].name + " killed!");
					gp.ui.addMessage("+"+gp.monster[gp.currentMap][i].exp+" EXP");
					exp += gp.monster[gp.currentMap][i].exp;
					checkLevelUp();
				}
			}
		}
		else {
			//Miss
		}
	}
	
	public void damageInteractiveTile(int i) {
		if(i != 999 && gp.iTile[gp.currentMap][i].destructible == true && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true && gp.iTile[gp.currentMap][i].invincible == false) {
			//Break tile
			gp.iTile[gp.currentMap][i].playSE();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
			
			generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
			
			if(gp.iTile[gp.currentMap][i].life == 0) {
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
			}	
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
			//gp.gameState = gp.dialogueState;
			dialogues[0][0] = "LEVEL " + level +"!\n" + "You feel stronger now!";
			startDialogue(this, 0);
		}
	}
	public void setDialogue() {
//		dialogues[0][0] = "LEVEL " + level +"!\n" + "You feel stronger now!";
	}
	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerslotCol, gp.ui.playerslotRow);
		if(itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getAttackImage();
			}
			if(selectedItem.type == type_shield) {
				currentSheild = selectedItem;
				defense = getDefense();
				
			}
			if(selectedItem.type == type_light) {
				if(currentLight == selectedItem) {
					currentLight = null;
				}
				else {
					currentLight = selectedItem;
				}
				lightUpdated = true;
				
			}
			if(selectedItem.type == type_consumable) {

				if(selectedItem.use(this) == true) {
					if(selectedItem.amount > 1) {
						selectedItem.amount--;
					}
					else {
						inventory.remove(itemIndex);
					}	
				}
			}
		}
	}
	
	public int searchItemInInventory(String itemName) {
		
		int itemIndex = 999;
		
		for(int i = 0; i<inventory.size(); i++) {
			if(inventory.get(i).name.equals(itemName)) {
				itemIndex = i;
				break; // just in case
			}
		}
		return itemIndex;
	}
	
	public boolean canObtainItem(Entity item) {
		
		boolean canObtain = false;
		// check stackablity
		if(item.stackable == true) {
			int index = searchItemInInventory(item.name);
			if(index != 999) {
				inventory.get(index).amount++;
				canObtain = true;
			}
			else { //new item so check vacancy
				if(inventory.size() != maxInventorySize) {
					inventory.add(item);
					canObtain = true;
				}
			}
		}
		else { //not stackable
			if(inventory.size() != maxInventorySize) {
				inventory.add(item);
				canObtain = true;
			}
		}
		return canObtain;
	}
	
	public void draw(Graphics2D g2 ){
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		case "up":
			if(attacking == false) {
				if(spriteNum == 1) {image = up1;}
				if(spriteNum == 2) {image = up2;}
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) {image = attackUp1;}
				if(spriteNum == 2) {image = attackUp2;}
			}
			if(guarding == true) {
				image = guardUp;
			}
			break;
		case "down":
			if(attacking == false) {
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = attackDown1;}
				if(spriteNum == 2) {image = attackDown2;}
			}
			if(guarding == true) {
				image = guardDown;
			}
			break;
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) {image = left1;}
				if(spriteNum == 2) {image = left2;}
			}
			if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum == 1) {image = attackLeft1;}
				if(spriteNum == 2) {image = attackLeft2;}				
			}
			if(guarding == true) {
				image = guardLeft;
			}
			break;
		case "right":
			if(attacking == false) {
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = attackRight1;}
				if(spriteNum == 2) {image = attackRight2;}				
			}
			if(guarding == true) {
				image = guardRight;
				
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
