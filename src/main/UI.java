package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.RemoteStub;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue;
	public int commandNum = 0;
	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf"); 
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMessage(String text) {
		message= text;
		messageOn = true;
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
			// Do playState stuff
		}
		//Pause State
		if(gp.gameState == gp.pauseState) {
			// Do pauseState stuff
			drawPauseScreen();
		}
		if(gp.gameState == gp.dialogueState) {
			// Do dialogueState stuff
			drawDialogueScreen();
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
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen(){
		// Window
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		drawSubWindow(x, y ,width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));  // Dialogue Font Size
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
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
}
