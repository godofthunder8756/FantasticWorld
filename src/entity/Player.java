package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		worldX = gp.tileSize *23;
		worldY = gp.tileSize *21;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/character_08.png")); //
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/character_09.png")); //
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/character_00.png")); //
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/character_01.png")); //
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/character_05.png")); //
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/character_04.png")); //
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/character_07.png")); //
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/character_06.png")); //
			upIdle = ImageIO.read(getClass().getResourceAsStream("/player/character_10.png")); //
			downIdle = ImageIO.read(getClass().getResourceAsStream("/player/character_03.png")); //
			leftIdle = ImageIO.read(getClass().getResourceAsStream("/player/character_04.png")); //
			rightIdle = ImageIO.read(getClass().getResourceAsStream("/player/character_06.png")); //
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			if(keyH.upPressed == true) {
				direction = "up";
				worldY -= speed;
			}
			else if(keyH.downPressed == true) {
				direction = "down";
				worldY += speed;
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
				worldX -= speed;
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
				worldX += speed;
			}
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum ==2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}	
	}
	public void draw(Graphics2D g2 ){
		//g2.setColor(Color.red);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			if(spriteNum == 3) {
				image = upIdle;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			if(spriteNum == 3) {
				image = downIdle;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			if(spriteNum == 3) {
				image = leftIdle;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			if(spriteNum == 3) {
				image = rightIdle;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}

}
