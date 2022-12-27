package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Blue_Slimeball;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Rock;

public class MONS_BlueSlime extends Entity{

	GamePanel gp;
	
	public MONS_BlueSlime(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = type_monster;
		name = "Brown Slime";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 4;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp = 3;
		projectile = new OBJ_Rock(gp);
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		projectile = new OBJ_Blue_Slimeball(gp);

		
		getImage();
		
	}
	public void getImage() {
		up1 = setup("/monster/blueslime_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/blueslime_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/blueslime_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/blueslime_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/blueslime_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/blueslime_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/blueslime_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/blueslime_down_2", gp.tileSize, gp.tileSize);
	}
	public void update() {
		super.update();
		
		int xDistance = Math.abs(worldX - gp.player.worldX);
		int yDistance = Math.abs(worldY - gp.player.worldY);
		int tileDistance = (xDistance + yDistance)/gp.tileSize;
		
		if(onPath == false && tileDistance < 5) {
			int i = new Random().nextInt(100)+1;
			if(i>50) {
				onPath = true;
			}
		}
		if(onPath == true && tileDistance > 15) {
			onPath = false;
		}
	}
	public void setAction() {
		if(onPath == true) {
			
			speed = 1;
			int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
			
			searchPath(goalCol, goalRow);
			
			int j = new Random().nextInt(100)+1; // pick a num form 1 to 100
			if(j>99 && projectile.alive == false && shotAvailableCOunter == 30) {
				projectile.set(worldX, worldY, direction, true, this);

				for(int i = 0; i < gp.projectile[1].length; i++) {
					if(gp.projectile[gp.currentMap][i] == null) {
						gp.projectile[gp.currentMap][i] = projectile;
						break;
					}
				}
				
				shotAvailableCOunter = 0;
			}
		}
		else {
			actionLockCounter++;
			if(actionLockCounter == 120) {
				Random random = new Random();
				int i = random.nextInt(100)+1; // pick a num form 1 to 100
				if(i<=25) {
					direction = "up";
				}
				if(i>25 && i <= 50) {
					direction = "down";
				}
				if(i>50 && i <= 75) {
					direction = "left";
				}
				if(i>75 && i <= 100) {
					direction = "right";
				}
				actionLockCounter = 0;
			}	
		}
	}
	public void damageReaction() {
		
		actionLockCounter = 0;
//		direction = gp.player.direction; // you pissed it off..
		onPath = true;
		
	}
	public void checkDrop() {
		// Roll dice
		int i = new Random().nextInt(100)+1;
		
		// Set drop
		if(i <50) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if(i >=50 && i < 75) {
			dropItem(new OBJ_Heart(gp));
		}
		if(i >=50 && i < 75) {
			// you get nuthin
		}
		
	}

}
