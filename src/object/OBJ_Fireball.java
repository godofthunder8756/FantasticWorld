package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {
	public static final String OBJNAME = "Fireball";
	GamePanel gp;
	
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = OBJNAME;
		speed = 10; 
		maxLife= 40; // 60 frames it burns out
		life = maxLife;
		attack = 1;
		knockBackPower = 5; // unneccisary code
		useCost = 1; // spend 1 mana to cast
		alive = false;
		getImage();
	}

	private void getImage() {
		up1 = setup("/projectile/fireball_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/fireball_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/fireball_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/fireball_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/fireball_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/fireball_right_2", gp.tileSize, gp.tileSize);
	}
	
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		if(user.mana >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	
	public void subtractResource(Entity user) { user.mana -= useCost;}
	
	public Color getParticleColor() {
		Color color = new Color(240,50,0);
		return color;
	}
	public int getParticleSize() {
		int size = 10;
		return size; //10px
	}
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
	

}
