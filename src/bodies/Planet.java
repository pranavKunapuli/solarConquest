package bodies;

import java.awt.*;


public class Planet {
	private int health; // Planet's health
	private int original; // Planet's original health (for resetting)
	private int x; // X coordinate
	private int y; // Y coordinate
	private int forces; // Amount of ships around planet
	private int enemy; // Amount of enemy forces around planet
	private Territory t; // Whether user has conquered the planet or not
	private java.awt.Color planetColor; // Planet display color
	
	public Planet(int health, int x, int y, int forces, int enemy, 
			Territory t) {
		this.health = health;
		this.original = health;
		this.x = x;
		this.y = y;
		this.forces = forces;
		this.enemy = enemy;
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
	
	public int getForces() { return this.forces; }
	
	public int getEnemy() { return this.enemy; }
	
	public int attack() {
		forces = Math.round(forces / 2);
		return forces;
	}
	
	private void battle() {
		if(enemy != 0 || forces != 0) {
			forces -= 2;
			enemy -= 2;
			if (forces < 0) { forces = 0; }
			if (enemy < 0) { enemy = 0; }
		}
	}
	
	/** Initially sorts out the conflict between user forces and enemy forces.
	 *  Reduces the health of the planet if there are no remaining enemy
	 *  forces. Health stays unchanged if enemy forces remain. Program then
	 *  pauses for 1 second.
	 */
	public void deltaHealth() {
		battle();
		if (enemy == 0 || forces == 0) { health -= 2; }
		if (health < 0) {
			health = original;
			switch(t) {
			case USER: 
				t = Territory.ENEMY;
				break;
			case ENEMY: 
				t = Territory.USER;
				break;
			case NEUTRAL: 
				if(enemy == 0) {
					t = Territory.USER;
				}
				else if (forces == 0) {
					t = Territory.ENEMY;
				}
				break;
			}
			setColor();
		}
		else {
			try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}
	
	public void draw (Graphics g) {
		g.setColor(planetColor);
		g.fillOval(this.x, this.y, health, health);
	}
}
