package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{
	public static final String OBJNAME = "Chest";
	GamePanel gp;
		
	public OBJ_Chest(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_obstacle;
		name = OBJNAME;
		image = setup("/objects/chest", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
		down1 = image;
		collision = true;
		
		solidArea.x = 4;
		solidArea.y = 16;
		solidArea.width = 40;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
	}
	public void setLoot(Entity loot) {
		this.loot = loot;
	}
	public void interact() {
		if(opened == false) {
			gp.playSE(3);
			
			StringBuilder sb = new StringBuilder();
			sb.append("You open the chest to find a "+ loot.name + "!");
			
			if(gp.player.canObtainItem(loot) == false) {
				sb.append("\n...But your inventory is full");
			}
			else {
				sb.append("\nYou obtained the " + loot.name + "!");
				down1 = image2;
				opened = true;
			}
			dialogues[0][0] = sb.toString();
			startDialogue(this, 0);
		}
		else {
			dialogues[1][0] = "It's empty...";
			startDialogue(this, 1);
		}
	}

}