package bodies;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Starship {
	private int x; // X-Coordinate of the starship
	private int y; // Y-Coordinate of the starship
	private int velX; // X-Velocity of the starship
	private int velY; // Y-Velocity of the starship
	private Planet start;
	private Planet end;
	private Vector dir;
	private Image img;
	
	private enum Vector { UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT; }
	
	public Starship(Planet start, Planet end) {
		this.start = start;
		this.end = end;
		dir = setVector();
		setStartPoint();
		getImage();
	}

	private void getImage() {
		BufferedImage buff = null;
		try {
			buff = ImageIO.read(new File("starship.jpg"));
		} catch (NullPointerException n) {
			n.printStackTrace();
		} catch (IOException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		img = buff;
	}

	private void setStartPoint() {
		if (dir == Vector.UP_RIGHT) {
			this.x = start.getX() + start.getSize();
			this.y = start.getY() + start.getSize() + 40;
		}
		else if (dir == Vector.UP_LEFT) {
			this.x = start.getX() - start.getSize() - 40;
			this.y = start.getY() + start.getSize() + 40;
		}
		else if (dir == Vector.DOWN_RIGHT) {
			this.x = start.getX() + start.getSize();
			this.y = start.getY() - start.getSize();
		}
		else {
			this.x = start.getX() - start.getSize() - 40;
			this.y = start.getY() - start.getSize();
		}
		this.velX = (end.getX() - start.getX()) / 10;
		this.velY = (end.getY() - start.getX()) / 10;
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
				&& next_y >= (p_y - p_size)
				&& next_y <= (p_y + p_size));
	}

	public void draw(Graphics g) {
		img.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
		g.drawImage(img, x, y, null);
	}
}
