package tile;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Map extends TileManager{

	GamePanel gp;
	BufferedImage worldMap[];
	public boolean miniMapOn = false;
	
	public Map(GamePanel gp) {
		super(gp);
		this.gp = gp;
		createWorldMap();
	}
	public void createWorldMap() {
		worldMap = new BufferedImage[gp.maxMap];
		int worldMapWidth = gp.tileSize * gp.maxWorldCol;
		int worldMapHeight = gp.tileSize * gp.maxWorldRow;
		
		for(int i =0; i < gp.maxMap; i++) {
			worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D)worldMap[i].createGraphics();
			System.out.println("Rendering Map: "+ i); // ------------------------------------------------------------DEBUG
			int col = 0;
			int row = 0;
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				int tileNum = mapTileNum[i][col][row];
				int x = gp.tileSize * col;
				int y = gp.tileSize * row;
				g2.drawImage(tile[tileNum].image, x, y, null);
				
				col++;
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
		}
	}
	public void drawFullMapScreen(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		//Draw Map
		int width = 576;
		int height = 576;
		int x = gp.screenHeight/2 - width/2;
		int y = gp.screenHeight/2 - height/2;
		g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);
		// Draw Player
		double scale = (double)(gp.tileSize * gp.maxWorldCol)/width;
		int playerX = (int)(x + gp.player.worldX/scale);
		int playerY = (int)(y + gp.player.worldY/scale);
		int playerSize = (int)(gp.tileSize/scale);
		g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);
		//Hint
		g2.setFont(gp.ui.maruMonica.deriveFont(32f));
		g2.setColor(Color.white);
		x = gp.tileSize*15;
		y = gp.tileSize*10;
		width = gp.tileSize*5;
		height = gp.tileSize*2;
		gp.ui.drawSubWindow(x, y, width, height);
		g2.drawString("Press M to close", x+24, y+60);
	}
	public void drawMiniMap(Graphics2D g2) {
		if(miniMapOn == true){
			int width = 200;
			int height = 200;
			int x = gp.screenWidth - width - 50;
			int y = 50;
			//gp.ui.drawSubWindow(x-5, y-5, width+10, height+10);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);
			g2.setStroke(new BasicStroke(5));
			g2.drawRoundRect(x-3, y-3, width+6, height+3, 35, 35);
			// Draw Player
			double scale = (double)(gp.tileSize * gp.maxWorldCol)/width;
			int playerX = (int)(x + gp.player.worldX/scale);
			int playerY = (int)(y + gp.player.worldY/scale);
			int playerSize = (int)(gp.tileSize/3);
			g2.drawImage(gp.player.down1, playerX-6, playerY-6, playerSize, playerSize, null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}

}
