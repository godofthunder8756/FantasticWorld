package entity;

import main.GamePanel;

public class PlayerDummy extends Entity{

	public static final String NPCNAME_STRING = "Dummy";
	
	public PlayerDummy(GamePanel gp) {
		super(gp);
		name = NPCNAME_STRING;
		getImage();
		
	}
	public void getImage() {	
		
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

}
