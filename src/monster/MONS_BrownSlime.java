package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Rock;

public class MONS_BrownSlime extends Entity{

	GamePanel gp;
	
	public MONS_BrownSlime(GamePanel gp) {
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
		
		getImage();
		
	}
	public void getImage() {
		up1 = setup("/monster/brownslime_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/brownslime_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/brownslime_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/brownslime_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/brownslime_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/brownslime_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/brownslime_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/brownslime_down_2", gp.tileSize, gp.tileSize);
	}
	

	
	public void setAction() {
		// unique overriding simple ai	
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
		int i = new Random().nextInt(100)+1; // pick a num form 1 to 100
		if(i>99 && projectile.alive == false && shotAvailableCOunter == 30) {
			projectile.set(worldX, worldY, direction, true, this);

			for(int j = 0; j < gp.projectile[1].length; j++) {
				if(gp.projectile[gp.currentMap][j] == null) {
					gp.projectile[gp.currentMap][j] = projectile;
					break;
				}
			}
			shotAvailableCOunter = 0;
		}
	}
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction;	
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
