package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Blue_Slimeball;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Rock;

public class MONS_Orc extends Entity{
	GamePanel gp;
	
	public MONS_Orc(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = type_monster;
		name = "Orc";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 10;
		life = maxLife;
		attack = 8;
		defense = 2;
		exp = 10;
		
		solidArea.x = 4;
		solidArea.y = 4;
		solidArea.width = 40;
		solidArea.height = 44;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		projectile = new OBJ_Blue_Slimeball(gp);
		attackArea.width = 48;
		attackArea.height = 48;

		
		getImage();
		getAttackImage();
	}
	public void getImage() {
		up1 = setup("/monster/orc_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/orc_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/orc_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/orc_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/orc_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/orc_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/orc_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/orc_right_2", gp.tileSize, gp.tileSize);
	}
	public void getAttackImage() {
		attackUp1 = setup("/monster/orc_attack_up_1", gp.tileSize, gp.tileSize*2);
		attackUp2 = setup("/monster/orc_attack_up_2", gp.tileSize, gp.tileSize*2);
		attackDown1 = setup("/monster/orc_attack_down_1", gp.tileSize, gp.tileSize*2);
		attackDown2 = setup("/monster/orc_attack_down_2", gp.tileSize, gp.tileSize*2);
		attackLeft1 = setup("/monster/orc_attack_left_1", gp.tileSize*2, gp.tileSize);
		attackLeft2 = setup("/monster/orc_attack_left_2", gp.tileSize*2, gp.tileSize);
		attackRight1 = setup("/monster/orc_attack_right_1", gp.tileSize*2, gp.tileSize);
		attackRight2 = setup("/monster/orc_attack_right_2", gp.tileSize*2, gp.tileSize);
	}
	
//	public void update() {
//		super.update();
//	}
	public void setAction() {
		
		if(onPath == true) {
			// Check if it stops chasing
			checkStopChasingOrNot(gp.player, 15, 100);
			// Search the direction to go
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
		}
		else {
			//Check if start chasing
			checkStartChasingOrNot(gp.player, 5, 100);
			// Get Random Direction
			getRandomDirection();
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
