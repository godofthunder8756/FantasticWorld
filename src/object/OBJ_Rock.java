package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {

	GamePanel gp;
	
	public OBJ_Rock(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Rock";
		speed = 8; 
		maxLife= 40; // 60 frames it burns out
		life = maxLife;
		attack = 2;
		useCost = 1; // spend 1 mana to cast
		alive = false;
		getImage();
	}

	private void getImage() {
		up1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		
	}
	

}