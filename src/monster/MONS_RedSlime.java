package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MONS_RedSlime extends Entity{

	public MONS_RedSlime(GamePanel gp) {
		super(gp);
		
		name = "Red Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
		
	}
	public void getImage() {
		up1 = setup("/monster/redslime_down_1");
		up2 = setup("/monster/redslime_down_2");
		down1 = setup("/monster/redslime_down_1");
		down2 = setup("/monster/redslime_down_2");
		left1 = setup("/monster/redslime_down_1");
		left2 = setup("/monster/redslime_down_2");
		right1 = setup("/monster/redslime_down_1");
		right2 = setup("/monster/redslime_down_2");
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
	}

}
