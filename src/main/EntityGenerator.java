package main;

import entity.Entity;
import object.OBJ_Axe;
import object.OBJ_Blue_Slimeball;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Door_Iron;
import object.OBJ_Fireball;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_Mana_Crystal;
import object.OBJ_Pickaxe;
import object.OBJ_Potion_Red;
import object.OBJ_Rock;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class EntityGenerator {
	GamePanel gp;
	public EntityGenerator(GamePanel gp) {
		this.gp = gp;
		
	}
	public Entity getObject(String itemName) {
		Entity obj = null;
		switch (itemName) {
		
		case OBJ_Axe.OBJNAME: obj = new OBJ_Axe(gp); break;
		case OBJ_Blue_Slimeball.OBJNAME: obj = new OBJ_Blue_Slimeball(gp); break;
		case OBJ_Boots.OBJNAME: obj = new OBJ_Boots(gp); break;
		case OBJ_Chest.OBJNAME: obj = new OBJ_Chest(gp); break;
		case OBJ_Door_Iron.OBJNAME: obj = new OBJ_Door_Iron(gp); break;
		case OBJ_Door.OBJNAME: obj = new OBJ_Door(gp); break;
		case OBJ_Fireball.OBJNAME: obj = new OBJ_Fireball(gp); break;
		case OBJ_Heart.OBJNAME: obj = new OBJ_Heart(gp); break;
		case OBJ_Key.OBJNAME: obj = new OBJ_Key(gp); break;
		case OBJ_Lantern.OBJNAME: obj = new OBJ_Lantern(gp); break;
		case OBJ_Mana_Crystal.OBJNAME: obj = new OBJ_Mana_Crystal(gp); break;
		case OBJ_Pickaxe.OBJNAME: obj = new OBJ_Pickaxe(gp); break;
		case OBJ_Potion_Red.OBJNAME: obj = new OBJ_Potion_Red(gp); break;
		case OBJ_Rock.OBJNAME: obj = new OBJ_Rock(gp); break;
		case OBJ_Shield_Blue.OBJNAME: obj = new OBJ_Shield_Blue(gp); break;
		case OBJ_Shield_Wood.OBJNAME: obj = new OBJ_Shield_Wood(gp); break;
		case OBJ_Sword_Normal.OBJNAME: obj = new OBJ_Sword_Normal(gp); break;
		
		
		
		
		
		}
		return obj;
	}
}
