package bodies;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
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
	public static final int FIELD_HEIGHT = 800;
	public static final int FIELD_WIDTH = 1500;
	// Time interval for planet battles
	public static final int INTERVAL = 35;
	public static final int TIME_LIMIT = 1200000;
	
	public Field (JLabel status) {
		this.status = status;
		// Loads the current level
		load();
		System.out.println(planets.size());
		
		// JComponent initialization
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e){
				ellapse();
			}
		});
		
		Timer end = new Timer(TIME_LIMIT, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				endGame();
			}
		});
		
		addMouseListener (new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		timer.start();
		setFocusable(true);
	}

	private void endGame() {
		status.setText("Time's up! Your armada has been destroyed!");
		playing = false;
	}
	
	private void ellapse() {
		/* TODO 
		 * 1) Move ships that have been created (i.e. new attack requested)
		 * 2) Engage in battle over one timestep
		 * 3) Regenerate forces on each planet
		*/  
		
		// Checks to see if ship will collide, and removes the ship from the
		// ArrayList if it will collide with the next timestep
		for (int i = 0; i < ships.size(); i++) {
			Starship ship = ships.get(i);
			if (ship.willIntersect()) { ships.remove(i); }
		}
		
		// Moves the existing ships 
		for (int i = 0; i < ships.size(); i++) {
			ships.get(i).move();
		}
		
		for (int i = 1; i < planets.size(); i++) {
			Planet p = planets.get(i);
			if (p.getForces() != 0) {
				p.destroyShips();
			}
		}
	}
	
	public void reset() {
		load();
		repaint();
	}
	
	private void load() {
		BufferedReader r = null;
		String level = "Level1.txt"; 
		//TODO Make it so that multiple levels can be read
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
				if (t_hold.equals("USER")) { t = Territory.USER; }
				else if (t_hold.equals("ENEMY")) { t = Territory.ENEMY; }
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
				e.printStackTrace();
			}
		}
	}
	
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Image img = getSpace();
		Image background = img.getScaledInstance(FIELD_WIDTH, FIELD_HEIGHT, 
														Image.SCALE_DEFAULT);
		g.drawImage(background, 0, 0, null);
		grid.draw(g);
		for (int i = 0; i < planets.size(); i++) {
			Planet p = planets.get(i);
			p.draw(g);
			System.out.println("Here");
		}
	}

	private Image getSpace() {
		BufferedImage buff = null;
		try {
			buff = ImageIO.read(new File("space.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File Not Found");
		} catch (NullPointerException n) {
			System.out.println("Path argument is null");
			n.printStackTrace();
		}
		return buff;
	}


	@Override
	public Dimension getPreferredSize(){
		return new Dimension(FIELD_WIDTH,FIELD_HEIGHT);
	}
}
