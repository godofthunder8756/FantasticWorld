package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity{
	public static final String OBJNAME = "Lantern";
	public OBJ_Lantern(GamePanel gp) {
		super(gp);
		
		type = type_light;
		name = OBJNAME;
		down1 = setup("/objects/lantern", gp.tileSize, gp.tileSize);
		description = "["+name+"]\nDarkness repellant.";
		price = 200;
		lightRadius = 250;
	}

}
