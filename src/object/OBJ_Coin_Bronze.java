package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity{
	public static final String OBJNAME = "Bronze Coin";
	GamePanel gp;
	
	public OBJ_Coin_Bronze(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_pickupOnly;
		name = OBJNAME;
		down1 = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);
		value = 1;
	
	}
	public boolean use(Entity entity) {
		gp.playSE(1);
		gp.ui.addMessage("+$"+ value);
		gp.player.coin+=value;
		return true;
	}

}
