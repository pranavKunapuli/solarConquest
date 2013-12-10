package bodies;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Instructions extends JPanel {
	Image rules;
	
	public Instructions() {
		BufferedImage buff = null;
		try {
			buff = ImageIO.read(new File("Instructions.png"));
		} catch (IOException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		}
		rules = buff;
	}
	
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(rules,0,0,null);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(800,500);
	}
}
