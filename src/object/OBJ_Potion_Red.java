package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity{
	
	GamePanel gp;
	
	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = "Red Potion";
		down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
		value = 5;
		description = "["+ name +"]\nTastes like red Kool-Aid!\nHeals you by "+value+"HP!";
		price = 75;
		stackable = true;
		setDialogue();
	}
	public void setDialogue() {
		dialogues[0][0] = "You drink the "+name+"!\n +"+value+"HP";
	}
	public boolean use(Entity entity) {
		startDialogue(this, 0);
		entity.life += value;
		gp.playSE(2);
		return true;
	}

}