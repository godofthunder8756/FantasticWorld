package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import main.GamePanel;


public class SaveLoad {
	GamePanel gp;
	
	public SaveLoad(GamePanel gp) {
		this.gp = gp;
	}

	public void save() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
			DataStorage ds = new DataStorage();
			//Player Stats
			ds.level = gp.player.level;
			ds.maxLife = gp.player.maxLife;
			ds.life = gp.player.life;
			ds.maxMana = gp.player.maxMana;
			ds.strength = gp.player.strength;
			ds.dexterity = gp.player.dexterity;
			ds.exp = gp.player.exp;
			ds.nextLevelExp = gp.player.nextLevelExp;
			ds.coin = gp.player.coin;
			
			ds.currentMap = gp.currentMap;
			ds.currentArea = gp.currentArea;
			ds.worldX = gp.player.worldX;
			ds.worldY = gp.player.worldY;
			ds.direction = gp.player.direction;
			ds.currentMusic = gp.currentMusic;
			
			//Player Inventory
			for(int i = 0; i< gp.player.inventory.size(); i++) {
				ds.itemNames.add(gp.player.inventory.get(i).name);
				ds.itemAmounts.add(gp.player.inventory.get(i).amount);
			}
			//Player Equipment
			ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
			ds.currentShieldSlot = gp.player.getCurrentShieldSlot();
			// Objects on Map
			ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
			ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
			ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];
			for(int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				for(int i = 0; i<gp.obj[1].length; i++) {
					if(gp.obj[mapNum][i] == null) {
						ds.mapObjectNames[mapNum][i] = "NA";
					}
					else {
						ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
						ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
						ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
						if(gp.obj[mapNum][i].loot != null) {
							ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
						}
						ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
					}
				}
			}
			
			//Write Data
			System.out.println("Writing Data...");
			oos.writeObject(ds);
			System.out.println("Data Saved!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void load() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
			// Read data storage object
			DataStorage ds = (DataStorage)ois.readObject();
			//Player Stats
			gp.player.level = ds.level;
			gp.player.maxLife = ds.maxLife;
			gp.player.life = ds.life;
			gp.player.maxMana = ds.maxMana;
			gp.player.strength = ds.strength;
			gp.player.dexterity = ds.dexterity;
			gp.player.exp = ds.exp;
			gp.player.nextLevelExp = ds.nextLevelExp;
			gp.player.coin = ds.coin;
			
			gp.currentMap = ds.currentMap;
			gp.currentArea = ds.currentArea;
			gp.player.worldX = ds.worldX;
			gp.player.worldY = ds.worldY;
			gp.player.direction = ds.direction;
			//gp.currentMusic = ds.currentMusic;
			if(gp.currentArea == gp.outside) {gp.currentMusic = 0;}
			if(gp.currentArea == gp.indoor) {gp.currentMusic = 18;}
			if(gp.currentArea == gp.dungeon) {gp.currentMusic = 19;}
			//Player Inventory
			gp.player.inventory.clear();
			for(int i = 0; i< ds.itemNames.size(); i++) {
				gp.player.inventory.add(gp.eGenerator.getObject(ds.itemNames.get(i)));
				gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
			}
			//Player Equipment
			gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
			gp.player.currentSheild = gp.player.inventory.get(ds.currentShieldSlot);
			gp.player.getAttack();
			gp.player.getDefense();
			gp.player.getAttackImage();
			// Objects on map
			for(int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				for(int i = 0; i<gp.obj[1].length; i++) {
					if(ds.mapObjectNames[mapNum][i].equals("NA")) {
						gp.obj[mapNum][i] = null;
					}
					else {
						gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
						if(gp.obj[mapNum][i] != null) {
							gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
							gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
						}
						
						if(ds.mapObjectLootNames[mapNum][i] != null) {
							gp.obj[mapNum][i].setLoot(gp.eGenerator.getObject(ds.mapObjectLootNames[mapNum][i]));
						}
						if(gp.obj[mapNum][i] != null) {
							gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
							
							if(gp.obj[mapNum][i].opened == true) {
								gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Load Error!");
		}
	}
}
