package object;

import entity.Entity;
import main.GamePanel;

public class BLDG_House extends Entity{
	public static final String OBJNAME = "House1";
	GamePanel gp;
		
	public BLDG_House(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_obstacle;
		name = OBJNAME;
		down1 = setup("/buildings/House1", gp.tileSize*5, gp.tileSize*6);
		collision = true;
		
		solidArea.x = 32;
		solidArea.y = 48;
		solidArea.width = 192;
		solidArea.height = 192;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		//setDialogue();
	}
	


}