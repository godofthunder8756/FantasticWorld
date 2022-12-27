package main;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MONS_BlackSlime;
import monster.MONS_BlueSlime;
import monster.MONS_BrownSlime;
import monster.MONS_PurpleSlime;
import monster.MONS_RedSlime;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import tile_interactive.IT_DryTree;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	public void setObject() {
		int mapNum = 0;
		int i = 0;
		
//		gp.obj[i] = new OBJ_Door(gp);
//		gp.obj[i].worldX = gp.tileSize*21;
//		gp.obj[i].worldY = gp.tileSize*22;
//		i++;
		
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*25;
		gp.obj[mapNum][i].worldY = gp.tileSize*23;
		i++;
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*21;
		gp.obj[mapNum][i].worldY = gp.tileSize*19;
		i++;
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*26;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Heart(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*25;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Axe(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*33;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*35;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*22;
		gp.obj[mapNum][i].worldY = gp.tileSize*27;
		i++;
		
		
	}
	public void setNPC() {
		int mapNum = 0;
		int i = 0;
		// MAP 0
		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*24;
		gp.npc[mapNum][i].worldY = gp.tileSize*21;
		i++;
		
		// MAP 1
		mapNum = 1;
		i = 0;
		gp.npc[mapNum][i] = new NPC_Merchant(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*12;
		gp.npc[mapNum][i].worldY = gp.tileSize*7;
		i++;
		
	}
	public void setMonster() {
		int mapNum = 0;
		int i = 0;
		
		gp.monster[mapNum][i] = new MONS_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*36;
		i++;
		gp.monster[mapNum][i] = new MONS_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*37;
		i++;
		gp.monster[mapNum][i] = new MONS_BlackSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*22;
		gp.monster[mapNum][i].worldY = gp.tileSize*37;
		i++;
		gp.monster[mapNum][i] = new MONS_PurpleSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*22;
		gp.monster[mapNum][i].worldY = gp.tileSize*38;
		i++;
		gp.monster[mapNum][i] = new MONS_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*11;
		gp.monster[mapNum][i].worldY = gp.tileSize*10;
		i++;
		gp.monster[mapNum][i] = new MONS_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*11;
		gp.monster[mapNum][i].worldY = gp.tileSize*11;
		i++;
		gp.monster[mapNum][i] = new MONS_BrownSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*10;
		gp.monster[mapNum][i].worldY = gp.tileSize*30;
		i++;
		gp.monster[mapNum][i] = new MONS_BlueSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*11;
		gp.monster[mapNum][i].worldY = gp.tileSize*30;
		i++;
		
	}
public void setInteractiveTile() {
		int mapNum = 0;
		int i = 0;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 27, 12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 28, 12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 29, 12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 30, 12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 31, 12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 32, 12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 33, 12);i++;
		
}

}
