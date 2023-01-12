package object;

import entity.Entity;
import main.GamePanel;

public class BLDG_House extends Entity{
	public static final String OBJNAME = "House1";
	GamePanel gp;
	
	public BLDG_House(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = OBJNAME;
		down1 = setup("/buildings/House1", gp.tileSize*5, gp.tileSize*6);
		//value = 5;
		//description = "["+ name +"]\nTastes like red Kool-Aid!\nHeals you by "+value+"HP!";
		//price = 75;
		//stackable = true;
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}