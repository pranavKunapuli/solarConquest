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
	private ArrayList<Planet> planets = new ArrayList<Planet>();
	private ArrayList<Starship> ships = new ArrayList<Starship>();
	private Planet start = null;
	private Planet finish = null;
	private Integer time_counter = 0;
	private int level_count = 1;
	private String level = "Level" + level_count + ".txt";
	
	public boolean playing = false; // Whether the game is playing or not
	private JLabel status; // Current status text
	
	// Game constants
	public static final int FIELD_HEIGHT = 800;
	public static final int FIELD_WIDTH = 1500;
	// Time interval for planet battles
	public static final int INTERVAL = 5;
	public static final int TIME_LIMIT = 500;
	
	public Field (JLabel status) {
		this.status = status;
		// Loads the current level
		load();
		
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
		
		addMouseListener (new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Mouse clicked");
				for (int i = 0; i < planets.size(); i++) {
					Planet p = planets.get(i);
					Point mouse = e.getPoint();
					System.out.println(mouse.x);
					System.out.println(mouse.y);
					if (p.isWithin(mouse)) {
						System.out.println("Mouse detected");
						// Tests to see if start has been set or not
						if (start == null) {
							start = p; // Sets the starting planet to the current planet in the ArrayList
							start.select();
							System.out.println("Start set");
							repaint();
							break;
						}
						else { 
							finish = p;
							start.attack(finish);
							System.out.println("End set");
							repaint();
							break;
						}
					}
					else {
						System.out.println("nowhere");
					}
				}
			}

		});
		
		timer.start();
		end.start();
		setFocusable(true);
	}

	private void endGame() {
		if(playing) {
			time_counter++;
			if(time_counter >= 120) {	
				status.setText("Time's up! Your armada has been destroyed!");
				playing = false;
				System.out.println("Time up");
			}
			
			for (int i = 0; i < planets.size(); i++) {
				Planet p = planets.get(i);
				if (p.getForces() != 0) {
					p.destroyShips();
				}
			}
			repaint();
		}
	}
	
	private void ellapse() {
		/* TODO 
		 * 1) Move ships that have been created (i.e. new attack requested)
		 * 2) Engage in battle over one timestep
		 * 3) Regenerate forces on each planet
		*/  
		
		// Tests for victory
		if(playing) {
			int win_count = 0;
			for (int i = 0; i < planets.size(); i++) {
				Planet p = planets.get(i);
				if (p.control) {
					win_count++;
				}
			}
			
			if(win_count == planets.size()) {
				status.setText("You have conquered the planet field!");
				//level_count++;
				//load();
				repaint();
				playing = false;
			}
			repaint();
		}
	}
	
	public void reset() {
		planets.clear();
		playing = true;
		start = null;
		finish = null;
		time_counter = 0;
		load();
		repaint();
	}
	
	private void load() {
		BufferedReader r = null;
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
		grid.draw(g); // Draws a white grid for effect
		g.setFont(new Font("default", Font.BOLD, 16)); // Boldens the font
		g.setColor(new Color(255,255,255)); // Makes text white
		Integer display_time = time_counter/2;
		g.drawString("Time: " + display_time.toString(), 1400, 50); // Shows time
		// Draws planets, including selected planets
		for (int i = 0; i < planets.size(); i++) {
			Planet p = planets.get(i);
			if (p.isSelected()) {
				int x = p.getX();
				int y = p.getY();
				int size = p.getSize();
				g.setColor(new Color (255,255,0));
				g.fillOval(x - (size/2) - 10, y - (size/2) - 10, size + 20, size + 20);
				if(finish != null) {
					g.drawLine(start.getX(), start.getY(), finish.getX(), finish.getY());
					start.deselect();
					start = null;
					finish = null;
				}
			}
			p.draw(g);
		}
		
		// Draws any ships for the field
		for (int j = 0; j < ships.size(); j++) {
			Starship ship = ships.get(j);
			ship.draw(g);
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
