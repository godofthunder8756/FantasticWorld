package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity{
	public static final String OBJNAME = "Wood Shield";

	public OBJ_Shield_Wood(GamePanel gp) {
		super(gp);
		
		type = type_shield;
		name = OBJNAME;
		down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
		defenseValue = 1;
		description = "["+ name +"]\nIt can't defend you from \nit's splinters.";
		price = 1;
	}

}
