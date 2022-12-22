package tile_interactive;

import main.GamePanel;

public class IT_Trunk extends InteractiveTile{

	public IT_Trunk(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize *col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/tiles_interactive/trunk", gp.tileSize, gp.tileSize);
		
		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 0;
		solidArea.height = 0;
		solidAreaDefaultX =0;
		solidAreaDefaultY =0;
		
	}
}
