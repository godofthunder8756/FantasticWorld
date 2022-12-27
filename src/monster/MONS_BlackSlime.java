package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;

public class MONS_BlackSlime extends Entity{

	GamePanel gp;
	
	public MONS_BlackSlime(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = type_monster;
		name = "Black Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp = 4;
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
		
	}
	public void getImage() {
		up1 = setup("/monster/blackslime_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/blackslime_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/blackslime_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/blackslime_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/blackslime_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/blackslime_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/blackslime_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/blackslime_down_2", gp.tileSize, gp.tileSize);
	}
	public void setAction() {
		if(onPath == true) {
			
//			int goalCol =12;
//			int goalRow =9; 
			
			speed = 2;
			int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
			
			
			searchPath(goalCol, goalRow);
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
