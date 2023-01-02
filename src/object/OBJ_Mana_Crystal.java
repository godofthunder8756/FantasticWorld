package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mana_Crystal extends Entity{
	public static final String OBJNAME = "Mana Crystal";
	GamePanel gp;

	public OBJ_Mana_Crystal(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = OBJNAME;
		type = type_pickupOnly;
		value = 1;
		down1 = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
		image = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
		
	}
	public boolean use(Entity entity) {
		gp.playSE(2);
		gp.ui.addMessage("+"+ value+"HP");
		entity.mana+=value;
		return true;
	}

}


