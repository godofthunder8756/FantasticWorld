package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity{
	public static final String OBJNAME = "Normal Sword";

	public OBJ_Sword_Normal(GamePanel gp){
		super(gp);
		
		type = type_sword;
		name = OBJNAME;
		down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		description = "["+ name +"]\nKinda rusty. Good for \nturning slimes into jelly.";
		price = 2;
		knockBackPower = 2;
		motion1_duration = 2;
		motion2_duration = 25;
	}

}
