package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mana_Crystal extends Entity{
	
	GamePanel gp;

	public OBJ_Mana_Crystal(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Mana Crystal";
		type = type_pickupOnly;
		value = 1;
//		down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
//		image = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
//		image2 = setup("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
		
	}
	public void use(Entity entity) {
		gp.playSE(2);
		gp.ui.addMessage("+"+ value+"HP");
		entity.mana+=value;
	}

}


