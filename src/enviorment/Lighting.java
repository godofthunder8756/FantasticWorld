package enviorment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {
	
	GamePanel gp;
	BufferedImage darknessFilter;
	int dayCounter;
	float filterAlpha = 0f;
	
	//Day State
	final int day = 0;
	final int dusk = 1;
	final int night = 2;
	final int dawn = 3;
	int dayState = day;
	
	public Lighting(GamePanel gp) {
		this.gp = gp;
		setLightSource();

	}
	public void setLightSource() {
		darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
		
		if(gp.player.currentLight == null) {
			g2.setColor(new Color(0,0,0,0.98f));
		}
		else {
		
			// Make rectangle screen sized
			// Make circle
			int centerX = gp.player.screenX + (gp.tileSize)/2;
			int centerY = gp.player.screenY + (gp.tileSize)/2;
			//Gradient
			Color color[] = new Color[12];//--------------------Must be the same
			float fraction[] = new float[12];//---------------/
			color[0] = new Color(0,0,0,0.1f);
			color[1] = new Color(0,0,0,0.42f);
			color[2] = new Color(0,0,0,0.52f);
			color[3] = new Color(0,0,0,0.61f);
			color[4] = new Color(0,0,0,0.69f);
			color[5] = new Color(0,0,0,0.76f);
			color[6] = new Color(0,0,0,0.82f);
			color[7] = new Color(0,0,0,0.87f);
			color[8] = new Color(0,0,0,0.9f);
			color[9] = new Color(0,0,0,0.9f);
			color[10] = new Color(0,0,0,0.9f);
			color[11] = new Color(0,0,0,0.9f);
	
			fraction[0] = 0f;
			fraction[1] = 0.4f;
			fraction[2] = 0.5f;
			fraction[3] = 0.6f;
			fraction[4] = 0.65f;
			fraction[5] = 0.7f;
			fraction[6] = 0.75f;
			fraction[7] = 0.8f;
			fraction[8] = 0.85f;
			fraction[9] = 0.9f;
			fraction[10] = 0.91f;
			fraction[11] = 0.98f;
	
			RadialGradientPaint gradientPaint= new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius, fraction, color);
			// Set the gradient data on g2
			g2.setPaint(gradientPaint);
		}
		//Draw light circle
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.dispose();
		
	}
	public void resetDay() {
		dayState = day;
		filterAlpha = 0f;
	}
	public void update() {
		if(gp.player.lightUpdated == true) {
			setLightSource();
			gp.player.lightUpdated = false;
		}
		// Check the state of the day
		if(dayState == day) {
			dayCounter++;
			
			if(dayCounter > 6000) { // 6000 = 100 seconds
				dayState = dusk;
				dayCounter = 0;
			}
		}
		if(dayState == dusk) {
			filterAlpha += 0.001f;
			if(filterAlpha>1f) {
				filterAlpha = 1f; //cap at 1
				dayState = night;
			}
		}
		if(dayState == night) {
			dayCounter++;
			
			if(dayCounter > 6000) { // 6000 = 100 seconds
				dayState = dawn;
				dayCounter = 0;
			}
		}
		if(dayState == dawn) {
			filterAlpha -= 0.001f;
			if(filterAlpha<0f) {
				filterAlpha = 0f; //cap at 0
				dayState = day;
			}
		}
	}
	public void draw(Graphics2D g2) {
		if(gp.currentArea == gp.outside) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
		}
		if(gp.currentArea == gp.outside || gp.currentArea == gp.dungeon) {
			g2.drawImage(darknessFilter, 0,0,null);
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		//Debug
		String situationString = "";
		switch (dayState) {
		case day: situationString = "DAY"; break;
		case dusk: situationString = "DUSK"; break;
		case night: situationString = "NIGHT"; break;
		case dawn: situationString = "DAWN"; break;
		}
		
			g2.setColor(Color.black);
			g2.setFont(g2.getFont().deriveFont(50F));
			g2.drawString(situationString, 800, 500);
			g2.setColor(Color.white);
			g2.drawString(situationString, 803, 503);
		
	}
}
