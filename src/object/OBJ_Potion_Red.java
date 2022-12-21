package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity{
	
	GamePanel gp;
	int value = 5;
	
	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = "Red Potion";
		down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
		description = "["+ name +"]\nTastes like red Kool-Aid!\nHeals you by "+value+"HP!";
		
	}
	public void use(Entity entity) {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You drink the "+name+"!\n +"+value+"HP";
		entity.life += value;
		if(gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		gp.playSE(2);
	}

}