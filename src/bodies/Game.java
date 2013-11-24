package bodies;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Game implements Runnable {

	@Override
	public void run() {
		JFrame frame = new JFrame("Solar Conquest");
		frame.setLocation(450,200);
		frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
