package bodies;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Instructions extends JPanel {
	String filename;
	BufferedReader buff = null;
	String rules;
	
	public Instructions() {
		filename = "Instructions.txt";
		rules = "";
		try {
			buff = new BufferedReader(new FileReader(filename));
			while(buff.ready()) {
				rules += buff.readLine() + "\n";
			}
			buff.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawString(rules, 0, 0);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(600,500);
	}
}
