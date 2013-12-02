package bodies;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Field extends JPanel {
	private Grid grid = new Grid(FIELD_WIDTH,FIELD_HEIGHT); // A grid for frame of reference
	ArrayList<Planet> planets = new ArrayList<Planet>();
	ArrayList<Starship> ships = new ArrayList<Starship>();
	
	public boolean playing = false; // Whether the game is playing or not
	private JLabel status; // Current status text
	
	// Game constants
	public static final int FIELD_HEIGHT = 500;
	public static final int FIELD_WIDTH = 800;
	// Time interval for planet battles
	public static final int INTERVAL = 200;
	
	public Field (JLabel status) {
		// Loads the current level
		load();
		
		// JComponent initialization
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e){
				ellapse();
			}
		});
		timer.start();
		setFocusable(true);
		this.status = status;
	}
	

	private void ellapse() {
		/* TODO 
		 * 1) Move ships that have been created (i.e. new attack requested)
		 * 2) Engage in battle over one timestep
		 * 3) Regenerate forces on each planet
		*/  
		for (int i = 0; i < ships.size(); i++) {
			ships.get(i).move();
		}
	}
	
	public void reset() {
		load();
		repaint();
	}
	
	private void load() {
		BufferedReader r = null;
		String level = "Level1.txt";
		try {
			r = new BufferedReader(new FileReader(level));
			String title = r.readLine().trim();
			status.setText(title);
			while(r.ready()) {
				String[] temp = r.readLine().split(",");
				int size = Integer.parseInt(temp[0]);
				int x = Integer.parseInt(temp[1]);
				int y = Integer.parseInt(temp[2]);
				int forces = Integer.parseInt(temp[3]);
				int enemy = Integer.parseInt(temp[4]);
				int health = Integer.parseInt(temp[5]);
				String t_hold = temp[6].toUpperCase();
				Territory t;
				if (t_hold == "USER") { t = Territory.USER; }
				else if (t_hold == "ENEMY") { t = Territory.ENEMY; }
				else { t = Territory.NEUTRAL; }
				planets.add(new Planet(size, x, y, forces, enemy, health, t));
			}
		} catch (IOException e) {
			playing = false;
			if (e instanceof FileNotFoundException) 
				System.out.println("File not found");
			e.printStackTrace();
		} finally {
			try {
				r.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		grid.draw(g);
		for (int i = 0; i < planets.size(); i++) {
			planets.get(i).draw(g);;
		}
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(FIELD_WIDTH,FIELD_HEIGHT);
	}
}
