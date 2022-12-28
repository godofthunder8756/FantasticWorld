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
//	public void update() {
//		super.update();
//	}
	public void setAction() {
		
		if(onPath == true) {
			// Check if it stops chasing
			checkStopChasingOrNot(gp.player, 15, 100);
			// Search the direction to go
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
			// Check if shoot projectile
			checkShootOrNot(200, 30);

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
