package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	public Font maruMonica;
	BufferedImage heart_full, heart_half, heart_blank, coin, crystal_full, crystal_blank;
	public boolean messageOn = false;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDialogue;
	public int commandNum = 0;
	public int playerslotCol = 0;
	public int playerslotRow = 0;
	public int npcslotCol = 0;
	public int npcslotRow = 0;
	public int subState = 0;
	int counter = 0; 
	public Entity npc;
	int charIndex = 0;
	String combinedText = "";
	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf"); 
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} 
		catch (FontFormatException e) { e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		
		// CREATE A HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity crystal = new OBJ_Mana_Crystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
		coin = bronzeCoin.down1;
	}
	
	public void addMessage(String text) {
		
		message.add(text);
		messageCounter.add(0);
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 =g2;
		
		g2.setFont(maruMonica);
		g2.setColor(Color.white);
		
		//Title State
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		//Play State
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMonsterLife();
			drawMessage();
		}
		//Pause State
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		//Dialogue State
		if(gp.gameState == gp.dialogueState) {
			//drawPlayerLife();
			drawDialogueScreen();
		}
		//Character State
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory(gp.player, true);
		}
		//Options State
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		//Game Over State
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		//Transition State
		if(gp.gameState == gp.transitionState) {
			drawTransition();
		}
		//Transition State
		if(gp.gameState == gp.tradeState) {
			drawTradeScreen();
		}
	}
	
	public void drawPlayerLife() {
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int iconSize = 32;
		int manaStartX = (gp.tileSize/2)-5;
		int manaStartY = 0;
		int i = 0;
		// Draw Blank Hearts
		while(i<gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, iconSize, iconSize, null);
			i++;
			x += iconSize;
			manaStartY = y + 32;
			
			if(i%8 == 0) {
				x = gp.tileSize/2;
				y += iconSize;
			}
			
		}
		//Reset
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		// Draw Current LIFE
		while(i<gp.player.life) {
			g2.drawImage(heart_half, x, y, iconSize, iconSize, null);
			i++;
			if(i<gp.player.life) {g2.drawImage(heart_full, x, y, iconSize, iconSize, null);}
			i++;
			x+=iconSize;
			
			if(i%16 == 0) {
				x = gp.tileSize/2;
				y += iconSize;
			}
		}
		//Draw Max Mana
		x = manaStartX;
		y = manaStartY;
		i=0;
		while(i<gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, iconSize, iconSize, null);
			i++;
			x+=20;
			if(i%10 == 0) {
				x = manaStartX;
				y += iconSize;
			}
		}
		//Draw Mana
		x = manaStartX;
		y = manaStartY;
		i=0;
		while(i<gp.player.mana) {
			g2.drawImage(crystal_full, x, y, iconSize, iconSize, null);
			i++;
			x+=20;
			if(i%10 == 0) {
				x = manaStartX;
				y += iconSize;
			}
		}
	}
	public void drawMonsterLife() {
		
		for(int i =0; i<gp.monster[1].length; i++) {
			Entity monster = gp.monster[gp.currentMap][i];
			if(monster != null && monster.inCamera() == true) {
				if(monster.hpBarOn == true && monster.boss == false) {
					
					double oneScale = (double)gp.tileSize/monster.maxLife;
					double hpBarValue = oneScale*monster.life;
					
					g2.setColor(new Color(35,35,35));                      //Boarder
					g2.fillRect(monster.getScreenX()-1, monster.getScreenY()-16, gp.tileSize+2, 12);
					g2.setColor(new Color(255,0,30));					   //Meter
					g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int)hpBarValue, 10);
					
					monster.hpBarCounter++;
					if(monster.hpBarCounter > 300) { //5 sec
						monster.hpBarCounter = 0;
						monster.hpBarOn =false;
					}
				}
				else if(monster.boss == true) {
					//DRAW LARGE HEALTH BAR
					double oneScale = (double)gp.tileSize*8/monster.maxLife;
					double hpBarValue = oneScale*monster.life;
					
					int x = gp.screenWidth/2 - gp.tileSize*4;
					int y = gp.tileSize*10;
					
					g2.setColor(new Color(35,35,35));                      //Boarder
					g2.fillRect(x-1, y-1, gp.tileSize*8+2, 22);
					g2.setColor(new Color(255,0,30));					   //Meter
					g2.fillRect(x, y, (int)hpBarValue, 20);
					g2.setFont(gp.getFont().deriveFont(Font.BOLD, 24f)); //Name
					g2.setColor(Color.white);
					g2.drawString(monster.name, x+2, y-10);
				}
			}
		}

	}
	public void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		
		for(int i = 0; i < message.size(); i++) {
			
			if(message.get(i) != null) {
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2); //Shadow
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				int counter = messageCounter.get(i) + 1; //messageCounter ++
				messageCounter.set(i, counter); // set the counter to the array
				messageY+= 50;
				
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
				
			}
		}
		
	}
	public void drawTitleScreen(){
		// Background Color
		g2.setColor(new Color(50,50,50));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		//Title Name
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 94F));
		String text = "FANTASTIC WORLD";
		int x = getXforCenteredText(text);
		int y = gp.tileSize*3;
		
		//SHADOW
		g2.setColor(Color.red);
		g2.drawString(text, x+5, y+5);
		
		//Main Color
		g2.setColor(Color.yellow);
		g2.drawString(text, x, y);
		
		// Player Image
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2;
		g2.drawImage(gp.player.downIdle, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		// Menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize*3.5;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "LOAD GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}	
		
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize, y);
		}		
		
		
	}
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	public void drawDialogueScreen(){
		// Window
		int x = gp.tileSize*3;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*6);
		int height = gp.tileSize*4;
		drawSubWindow(x, y ,width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));  // Dialogue Font Size
		x += gp.tileSize;
		y += gp.tileSize;
		
		if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
//			currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];
			char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
			if(charIndex < characters.length) {
				gp.playSE(17);
				String string = String.valueOf(characters[charIndex]);
				combinedText = combinedText + string;
				currentDialogue = combinedText;
				charIndex++;
			}
			if(gp.keyH.enterPressed == true) {
				charIndex = 0;
				combinedText = "";
				if(gp.gameState == gp.dialogueState) {
					npc.dialogueIndex++;
					gp.keyH.enterPressed=false;
				}
			}
		}
		else {//If no text left in array
			//reset dialogue
			npc.dialogueIndex = 0;
			if(gp.gameState == gp.dialogueState) {
				gp.gameState = gp.playState;
			}
		}
		
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
		
		
	}
	public void drawCharacterScreen() {
		//CREATE FRAME
		final int frameX = gp.tileSize*2;  
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = frameX+20;
		int textY = frameY+gp.tileSize;
		final int lineHeight = 35;    //SAME AS FONT //edit +3
		
		//NAMES
		g2.drawString("LEVEL", textX, textY); textY+=lineHeight;
		g2.drawString("HP", textX, textY); textY+=lineHeight;
		g2.drawString("MANA", textX, textY); textY+=lineHeight;
		g2.drawString("STRENGTH", textX, textY); textY+=lineHeight;
		g2.drawString("DEXTERITY", textX, textY); textY+=lineHeight;
		g2.drawString("ATTACK", textX, textY); textY+=lineHeight;
		g2.drawString("DEFENSE", textX, textY); textY+=lineHeight;
		g2.drawString("EXP", textX, textY); textY+=lineHeight;
		g2.drawString("NEXT LEVEL", textX, textY); textY+=lineHeight;
		g2.drawString("COIN", textX, textY); textY+=lineHeight +10;
		g2.drawString("WEAPON", textX, textY); textY+=lineHeight +15;
		g2.drawString("SHEILD", textX, textY); textY+=lineHeight;		
		
		//VALUES
		int tailX = (frameX + frameWidth) - 30; //Align to right
		textY = frameY + gp.tileSize; //reset textY
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;		
		
		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;	
		
		value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;		
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;		
		
		value = String.valueOf(gp.player.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;		
		
		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;		
		
		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;		
		
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;		
		
		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;	//Make ROom for images	
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX-gp.tileSize, textY-24, null);
		textY+=gp.tileSize;
		
		g2.drawImage(gp.player.currentSheild.down1, tailX-gp.tileSize, textY-24, null);
		textY+=gp.tileSize;
		
	}
	
	public void drawTransition() {
		counter++;
		g2.setColor(new Color(0,0,0,counter*5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		if(counter == 50) {
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize*gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize*gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
			gp.changeArea();

		}
	}
	
	public void drawInventory(Entity entity, boolean cursor) {

		int frameX=0; 
		int frameY=0;
		int frameWidth=0;
		int frameHeight=0;
		int slotCol=0;
		int slotRow=0;
		
		if(entity == gp.player) {
			frameX = gp.tileSize*12; // or 13 and charscreen at 1
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = playerslotCol;
			slotRow = playerslotRow;
		}
		else {
			frameX = gp.tileSize*2; // or 13 and charscreen at 1
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = npcslotCol;
			slotRow = npcslotRow;
		}
		
		// FRAME
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize+3;
		
		//Draw Player's Items
		for(int i = 0; i < entity.inventory.size(); i++) {
			
			//EQUIP CURSOR
			if(entity.inventory.get(i)==entity.currentWeapon || entity.inventory.get(i) == entity.currentSheild || entity.inventory.get(i) == entity.currentLight) {
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
			
			// DRAW AMOUNT
			if(entity == gp.player && entity.inventory.get(i).amount > 1) {
				g2.setFont(g2.getFont().deriveFont(32f));
				int amountX;
				int amountY;
				
				String string = "" + entity.inventory.get(i).amount;
				amountX = getXforAlignToRightText(string, slotX+44);
				amountY = slotY + gp.tileSize;
				
				//Shadow
				g2.setColor(new Color(60,60,60));
				g2.drawString(string, amountX, amountY);
				//Number
				g2.setColor(Color.white);
				g2.drawString(string, amountX-3, amountY-3);
				
			}
			
			slotX+=slotSize;
			if( i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}
		
		//Cursor
		if(cursor == true) {
			int cursorX = slotXstart + (slotSize * slotCol);
			int cursorY = slotYstart + (slotSize * slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;
			
			//Draw Cursor
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX,cursorY, cursorWidth, cursorHeight, 10, 10);
			
			// DESCRIPTION FRAME
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize*3;
			
			
			// Draw Desiption text
			int textX = dFrameX+20;
			int textY = dFrameY+gp.tileSize;
			g2.setFont(g2.getFont().deriveFont(28F));
			
			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
			
			if(itemIndex < entity.inventory.size()) {
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
				for(String line: entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY += 32;
				}
			}
		}
	}
	
	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
	}
	
	public void drawTradeScreen(){
		switch(subState) {
		case 0: trade_select(); break;
		case 1: trade_buy(); break;
		case 2: trade_sell(); break;
		}
		gp.keyH.enterPressed = false;
	}
	public void trade_select() {
		npc.dialogueSet = 0;
		drawDialogueScreen();
		//DRAW WINDOW
		int x = gp.tileSize*15;
		int y = gp.tileSize*4;
		int width = gp.tileSize*3;
		int height = (int)(gp.tileSize * 3.5);
		drawSubWindow(x, y, width, height);
		//commandNum = 0;
		
		// DRAW TEXT
		x+=gp.tileSize;
		y+=gp.tileSize;
		g2.drawString("Buy", x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				subState = 1;
			}
		}
		y+=gp.tileSize;
		g2.drawString("Sell", x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
			}
		}
		y+=gp.tileSize;
		g2.drawString("Leave", x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				commandNum = 0;
				npc.startDialogue(npc, 1);
			}
		}
		
	}
	public void trade_buy() {
		//draw player inventory
		drawInventory(gp.player, false);
		//draw npc inventory
		drawInventory(npc, true);
		//draw hint window
		int x = gp.tileSize*2;
		int y = gp.tileSize*9;
		int width = gp.tileSize*6;
		int height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x+24, y+60);
		//draw player coin window
		 x = gp.tileSize*12;
		 y = gp.tileSize*9;
		 width = gp.tileSize*6;
		 height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawString("$"+gp.player.coin, x+24, y+60);
		//draw price window
		int itemIndex = getItemIndexOnSlot(npcslotCol,npcslotRow);
		if(itemIndex < npc.inventory.size()) {
			x = (int)(gp.tileSize*5.5);
			y = (int)(gp.tileSize*5.5);
			width = (int)(gp.tileSize*2.5);
			height = (int)(gp.tileSize);
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x+10, y+8, 32, 32, null);
			//Price
			int price = npc.inventory.get(itemIndex).price;
			String textString = ""+price;
			x = getXforAlignToRightText(textString, gp.tileSize*8-20);
			g2.drawString(textString, x, y+34);
			
			//BUY ITEM
			if(gp.keyH.enterPressed == true) {
				if(npc.inventory.get(itemIndex).price > gp.player.coin) {
					subState = 0;
					npc.startDialogue(npc, 2);
				}
				else {
					if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
						gp.player.coin -= npc.inventory.get(itemIndex).price;
					}
					else {
						subState = 0;
						npc.startDialogue(npc, 3);					
					}
				}
			}
		}
	}
	public void trade_sell() {
		//draw inventory
		drawInventory(gp.player, true);
		int x;
		int y;
		int width;
		int height;
		
		//draw hint window
		x = gp.tileSize*2;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x+24, y+60);
		//draw player coin window
		 x = gp.tileSize*12;
		 y = gp.tileSize*9;
		 width = gp.tileSize*6;
		 height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawString("$"+gp.player.coin, x+24, y+60);
		//draw price window
		int itemIndex = getItemIndexOnSlot(playerslotCol,playerslotRow);
		if(itemIndex < gp.player.inventory.size()) {
			x = (int)(gp.tileSize*15.5);
			y = (int)(gp.tileSize*5.5);
			width = (int)(gp.tileSize*2.5);
			height = (int)(gp.tileSize);
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x+10, y+8, 32, 32, null);
			//Price
			int price = gp.player.inventory.get(itemIndex).price/2;
			String textString = ""+price;
			x = getXforAlignToRightText(textString, gp.tileSize*18-20);
			g2.drawString(textString, x, y+34);
			
			//Sell ITEM
			if(gp.keyH.enterPressed == true) {
				if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon || 
						gp.player.inventory.get(itemIndex) == gp.player.currentSheild || 
						gp.player.inventory.get(itemIndex) == gp.player.currentLight) {
					commandNum = 0;
					subState = 0;
					npc.startDialogue(npc, 4);
				}
				else {
					if(gp.player.inventory.get(itemIndex).amount > 1) {
						gp.player.inventory.get(itemIndex).amount--;
					}
					else {
						gp.player.inventory.remove(itemIndex);
					}
					gp.playSE(1);
				}
			}
			
		}
		
	}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String textString;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
		
		textString = "Game Over";
		g2.setColor(Color.black);
		x = getXforCenteredText(textString);
		y = gp.tileSize*4;
		g2.drawString(textString, x, y);
		g2.setColor(Color.white);
		g2.drawString(textString, x-4, y-4);
		
		//Retry
		g2.setFont(g2.getFont().deriveFont(50f));
		textString = "Retry";
		x = getXforCenteredText(textString);
		y+= gp.tileSize*4;
		g2.drawString(textString, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		
		//Quit
		textString = "Quit";
		x = getXforCenteredText(textString);
		y+= 55;
		g2.drawString(textString, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}

	}
 	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		//Subwindow
		int frameX = gp.tileSize*6; 
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*8;
		int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0: options_top(frameX, frameY);break;
		case 1: options_fullScreenNotification(frameX, frameY);break;
		case 2: options_controls(frameX, frameY);break;
		case 3: options_confirmEndGame(frameX, frameY);break;
		}
		gp.keyH.enterPressed = false;
	}
	public void options_top(int frameX, int frameY) {
		int textX;
		int textY;
		
		//TITLE
		String textString = "Options";
		textX = getXforCenteredText(textString);
		textY = frameY + gp.tileSize;
		g2.drawString(textString, textX, textY);
		
		// FULL SCREEN ON/OFF
		textX = frameX+gp.tileSize;
		textY += gp.tileSize*2;
		g2.drawString("Full Screen", textX, textY);
		if(commandNum == 0 && subState == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				if(gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				}
				else if(gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		}
		//MUSIC
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		//SE
		textY += gp.tileSize;
		g2.drawString("SFX", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
		}
		//CONTROL
		textY += gp.tileSize;
		g2.drawString("Controls", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
		//END GAME
		textY += gp.tileSize;
		g2.drawString("Quit", textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}
		//BACK
		textY += gp.tileSize*2;
		g2.drawString("Back to Game", textX, textY);
		if(commandNum == 5) {
			g2.drawString(">", textX-25, textY);
		}
		
		//FULL SCREEN CHECK BOX
		textX = frameX + (int)(gp.tileSize*4.5);
		textY = frameY + gp.tileSize*2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if(gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}
		
		//Music Volume
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24); // 120/5 = 24px per scale
		int volumeWidth = 24*gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		//SE Volume
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24*gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		gp.config.saveConfig();
		
	}
	
	public void options_fullScreenNotification(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "This change will take \neffect after restarting \nthe game!";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY+=40;
		}
		// Back
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
			}
		}
	}
	
	public void options_controls(int frameX, int frameY) {
		int textX;
		int textY;
		//Title
		String textString = "Controls";
		textX = getXforCenteredText(textString);
		textY = frameY + gp.tileSize;
		g2.drawString(textString, textX, textY);
		
		textX = frameX + (gp.tileSize/2);
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY); textY+=gp.tileSize;
		g2.drawString("Confirm/Attack", textX, textY); textY+=gp.tileSize;
		g2.drawString("Shoot/Cast", textX, textY); textY+=gp.tileSize;
		g2.drawString("Inventory/Stats", textX, textY); textY+=gp.tileSize;
		g2.drawString("Pause", textX, textY); textY+=gp.tileSize;
		g2.drawString("Options", textX, textY); textY+=gp.tileSize;
		g2.drawString("Restart PC", textX, textY); textY+=gp.tileSize;
		
		textX = frameX + (int)(gp.tileSize*5.25);
		textY = frameY + gp.tileSize*2;
		g2.drawString("WASD", textX, textY); textY+=gp.tileSize;
		g2.drawString("ENTER", textX, textY); textY+=gp.tileSize;
		g2.drawString("F", textX, textY); textY+=gp.tileSize;
		g2.drawString("C", textX, textY); textY+=gp.tileSize;
		g2.drawString("P", textX, textY); textY+=gp.tileSize;
		g2.drawString("ESC", textX, textY); textY+=gp.tileSize;
		g2.drawString("Ctr+Alt+Del", textX, textY); textY+=gp.tileSize;
		
		
		// Back
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 3;
			}
		}
	}
	
	public void options_confirmEndGame(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "Are you sure you want to \nquit to the title screen?";
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY+=40;
		}
		// YES
		String text = "Yes";
		textX = getXforCenteredText(text);
		textY += gp.tileSize*3;
		g2.drawString(text, textX, textY);
		if(commandNum ==0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				gp.gameState = gp.titleState;
				gp.resetGame(true);
				gp.stopMusic();
			}
		}
		// NO
		text = "No";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum ==1) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}
		}
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0,0,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35); // change archwidth and archeight
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	public int getXforAlignToRightText(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
	
}
