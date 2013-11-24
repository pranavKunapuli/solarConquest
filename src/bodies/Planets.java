package bodies;

import java.awt.Graphics;
import java.awt.*;


public class Planets {
	private int size; // Planet's size
	private int regen; // How fast forces regenerate
	private int x; // X coordinate
	private int y; // Y coordinate
	private int forces; // Amount of ships around planet
	private int enemy; // Amount of enemy forces around planet
	private java.awt.Color planetColor; // Planet display color
	
	Planets(int size, int regen, int x, int y, int forces, int enemy, Color c) {
		this.size = size;
		this.regen = regen;
		this.x = x;
		this.y = y;
		this.forces = forces;
		this.enemy = forces;
		this.planetColor = c;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int getForces() {
		return this.forces;
	}
	
	public int attack() {
		forces = Math.round(forces / 2);
		return 0;
	}
	
	public void draw (Graphics g) {
		g.setColor(planetColor);
		g.fillOval(this.x, this.y, size, size);
	}
}
