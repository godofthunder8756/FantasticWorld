package data;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class DataStorage implements Serializable{
	//PLAYER STATES
	int level;
	int maxLife;
	int life;
	int maxMana;
	int mana;
	int strength;
	int dexterity;
	int exp;
	int nextLevelExp;
	int coin;
	
	//Player Inventory
	ArrayList<String> itemNames = new ArrayList<>();
	ArrayList<Integer> itemAmounts = new ArrayList<>();
	int currentWeaponSlot;
	int currentShieldSlot;
	
	//Objects On Map
	String mapObjectNames[][];
	int mapObjectWorldX[][];
	int mapObjectWorldY[][];
	String mapObjectLootNames[][] ;
	boolean mapObjectOpened[][];
	
	
}
