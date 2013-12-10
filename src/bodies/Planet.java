package bodies;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Planet {
	private int size; // Planet's radius
	private int health; // Planet's health
	private int original; // Planet's original health (for resetting)
	private int x; // X coordinate
	private int y; // Y coordinate
	private int forces; // Amount of ships around planet
	private int enemy; // Rate of deterioration of user's ships
	private Territory t; // Whether user has conquered the planet or not
	private String src; // Planet image source
	private boolean selected; // Whether planet has been clicked or not
	public boolean control; // Whether user has control of the planet
	
	public Planet(int size, int x, int y, int forces, int enemy, int health,
			Territory t) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.forces = forces;
		this.enemy = enemy;
		this.health = health;
		this.original = health;
		this.t = t;
		selected = false;
		setColor();
	}
	
	private void setColor() {
		switch (t) {
		case USER: 
			src = "greenPlanet.gif"; 
			control = true;
			break;
		case ENEMY: 
			src = "redPlanet.gif"; 
			control = false;
			break;
		case NEUTRAL: 
			src = "grayPlanet.gif"; 
			control = false;
			break;
		}
	}
	
	public int getHealth() { return this.health; }
	public int getX() { return this.x; }
	public int getForces() { return this.forces; }
	public void setForces(int f) { forces += f; }
	public int getY() { return this.y; }
	public int getSize() { return this.size	; }
	public boolean isSelected() { return selected; }
	public void select() { selected = true; }
	public void deselect() { selected = false; }
	
	public void destroyShips() {
		switch(t) {
		case USER:
			forces += enemy; // Regenerates forces
			control = true;
			break;
		case NEUTRAL:
			if (forces != 0) {
				forces -= enemy; // Reduces forces;
				health -= enemy; // Reduces health;
				if (health <= 0) {
					t = Territory.USER;
					setColor();
					health = original;
					control = true;
				}
				else if (forces <= 0) { 
					health = original;
					t = Territory.NEUTRAL;
					setColor();
					control = false;
				}
			}
		case ENEMY:
			if (forces != 0) {
				forces -= enemy; // Reduces forces
				health -= enemy; // Reduces health
				if (health <= 0) {
					health = 0;
					control = true;
				}
				if (forces <= 0) {
					forces = 0;
					control = false;
					health = original;
				}
			}
		}
	}

	public void draw (Graphics g) {
		Image img = getImage();
		Image show = img.getScaledInstance(size, size, Image.SCALE_DEFAULT);
		g.drawImage(show, x - (size/2), y - (size/2), null);
		String display = forces + " : " + health;
		if (t == Territory.USER) { g.setColor(new Color(0,0,0)); }
		else { g.setColor(new Color(255,255,255)); }
		g.setFont(new Font("default", Font.BOLD, 16));
		g.drawString(display, x - (size / 4), y);
	}
	
	private Image getImage() {
		BufferedImage buff = null;
		try {
			buff = ImageIO.read(new File(src));
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("Source file invalid");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File could not be read");
		}
		return buff;
	}

	public boolean isWithin(Point location) {
		return (location.x >= x - size
				&& location.x <= x + size
				&& location.y >= y - size
				&& location.y <= y + size);
	}
	
	public void attack(Planet dest) {
		dest.setForces(this.forces / 2);
		forces /= 2;
	}
}
