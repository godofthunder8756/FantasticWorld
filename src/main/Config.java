package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
	GamePanel gp;
	
	public Config(GamePanel gp) {
		this.gp = gp;
	}
	public void saveConfig() {
		try {
			BufferedWriter bWriter = new BufferedWriter(new FileWriter("config.txt"));
			// Full screen
			if(gp.fullScreenOn == true) {
				bWriter.write("On");
			}
			if(gp.fullScreenOn == false) {
				bWriter.write("Off");
			}
			bWriter.newLine();
			
			// Music Volume
			bWriter.write(String.valueOf(gp.music.volumeScale));
			bWriter.newLine();
			
			//SE VOlume
			bWriter.write(String.valueOf(gp.se.volumeScale));
			bWriter.newLine();
			
			
			bWriter.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadConfig() {
		try {
			BufferedReader bReader = new BufferedReader(new FileReader("config.txt"));
			
			String s = bReader.readLine();
			
			// Full Screen
			if(s.equals("On")) {
				gp.fullScreenOn = true;
			}
			if(s.equals("Off")) {
				gp.fullScreenOn = false;
			}
			
			// Music volume
			s = bReader.readLine();
			gp.music.volumeScale = Integer.parseInt(s);
			
			// SE volume
			s = bReader.readLine();
			gp.se.volumeScale = Integer.parseInt(s);
			
			bReader.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
