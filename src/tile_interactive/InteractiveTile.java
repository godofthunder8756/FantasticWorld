package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity{
	
	GamePanel gp;
	public boolean destructible = false;

	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
		// TODO Auto-generated constructor stub
	}
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		
		return isCorrectItem;	
	}
	
	public void update() {
		
	}

}
