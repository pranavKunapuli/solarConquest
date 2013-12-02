package bodies;

public class Starship {
	private int x;
	private int y;
	private int velX;
	private int velY;
	
	public Starship(int x, int y, int velX, int velY) {
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
	}
	
	public void move() {
		x += velX;
		y += velY;
	}
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
}
