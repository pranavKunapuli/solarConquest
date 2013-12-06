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
		setColor();
	}
	
	private void setColor() {
		switch (t) {
		case USER: 
			src = "greenPlanet.gif"; 
			break;
		case ENEMY: 
			src = "redPlanet.gif"; 
			break;
		case NEUTRAL: 
			src = "grayPlanet.gif"; 
			break;
		}
	}
	
	public int getHealth() { return this.health; }
	public int getX() { return this.x; }
	public int getForces() { return this.forces; }
	public int getY() { return this.y; }
	public int getSize() { return this.size	; }
	
	public void destroyShips() {
		if (forces != 0) { 
			forces = forces - enemy; 
			if (forces <= 0) {
				forces = 0;
				health = original;
				t = Territory.ENEMY;
				setColor();
			}
			else {
				health = health - (enemy/2);
				if (health <= 0) {
					health = 0; 
					t = Territory.USER;
					setColor();
				}
			}
		}
		else {
			health = original;
		}
	}
	
	public void draw (Graphics g) {
		Image img = getImage();
		Image show = img.getScaledInstance(size, size, Image.SCALE_DEFAULT);
		g.drawImage(show, x - (size/2), y - (size/2), null);
		String display = forces + " : " + health;
		System.out.println(display);
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
}
