package bodies;

import java.awt.Color;
import java.awt.Graphics;

public class Grid {
	private int height;
	private int width;
	
	Grid(int xDivide, int yDivide) {
		this.width = xDivide;
		this.height = yDivide;
	}
	
	public void draw (Graphics g) {
		g.setColor(Color.WHITE);
		for (int i = 0; i < width; i = i + 100) {
			g.drawLine(i, 0, i, height);
		}
		for (int j = 0; j < height; j = j + 100) {
			g.drawLine(0, j, width, j);
		}
	}
}
