package bodies;

import java.awt.Graphics;

public class Starship {
	private int x; // X-Coordinate of the starship
	private int y; // Y-Coordinate of the starship
	private int velX; // X-Velocity of the starship
	private int velY; // Y-Velocity of the starship
	private Planet start;
	private Planet end;
	private Vector dir;
	
	public Starship(Planet start, Planet end) {
		this.start = start;
		this.end = end;
		dir = setVector();
		setStartPoint();
	}

	private void setStartPoint() {
		if (dir == Vector.UP_RIGHT) {
			this.x = start.getX() + start.getSize();
			this.y = start.getY() + start.getSize();
			this.velX = 50;
			this.velY = 50;
		}
		else if (dir == Vector.UP_LEFT) {
			this.x = start.getX() - start.getSize();
			this.y = start.getY() + start.getSize();
			this.velX = -50;
			this.velY = 50;
		}
		else if (dir == Vector.DOWN_RIGHT) {
			this.x = start.getX() + start.getSize();
			this.y = start.getY() - start.getSize();
			this.velX = 50;
			this.velY = -50;
		}
		else {
			this.x = start.getX() - start.getSize();
			this.y = start.getY() - start.getSize();
			this.velX = -50;
			this.velY = -50;
		}
	}

	private Vector setVector() {
		if (start.getX() < end.getX()) {
			if (start.getY() < end.getY()) { return Vector.DOWN_RIGHT; }
			else { return Vector.UP_RIGHT; }
		}
		else {
			if (start.getY() < end.getY()) { return Vector.DOWN_LEFT; }
			else { return Vector.UP_LEFT; }
		}
	}
	
	private enum Vector { UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT; }
	
	public void move() {
		x += velX;
		y += velY;
	}
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	
	public boolean willIntersect () {
		int next_x = x + velX;
		int next_y = y + velY;
		int p_x = end.getX();
		int p_y = end.getY();
		int p_size = end.getSize();
		return (next_x >= (p_x - p_size) 
				&& next_x <= (p_x + p_size)
				&& next_y >= (p_x - p_size)
				&& next_y <= (p_x + p_size));
	}

	public void draw(Graphics g) {
		
	}
}
