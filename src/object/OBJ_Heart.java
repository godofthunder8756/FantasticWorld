package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{
	public static final String OBJNAME = "Heart";
	GamePanel gp;
	
		
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_pickupOnly;
		name = OBJNAME;
		value = 2;
		down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
		image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);
		
	}
	public boolean use(Entity entity) {
		gp.playSE(2);
		gp.ui.addMessage("+"+ value+"HP");
		gp.player.life+=value;
		return true;
	}
}