package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity{
	public static final String OBJNAME = "Boots";
	
		
	public OBJ_Boots(GamePanel gp) {
		super(gp);
		
		name = OBJNAME;
		type = type_boots;
		down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
		description = "["+ name +"]\nOne size fits all!\n+2 Speed";
		price = 4;
		
	}

}
