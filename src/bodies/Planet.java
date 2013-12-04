package bodies;

import java.awt.*;


public class Planet {
	private int size; // Planet's radius
	private int health; // Planet's health
	private int original; // Planet's original health (for resetting)
	private int x; // X coordinate
	private int y; // Y coordinate
	private int forces; // Amount of ships around planet
	private int enemy; // Rate of deterioration of user's ships
	private Territory t; // Whether user has conquered the planet or not
	private java.awt.Color planetColor; // Planet display color
	
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
		case USER: planetColor = Color.GREEN; break;
		case ENEMY: planetColor = Color.RED; break;
		case NEUTRAL: planetColor = Color.GRAY; break;
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
		g.setColor(planetColor);
		g.fillOval(this.x, this.y, size, size);
	}
}
