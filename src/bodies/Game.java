package bodies;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Game implements Runnable {

	@Override
	public void run() {
		JFrame frame = new JFrame("Solar Conquest");
		frame.setLocation(200,200);
		
		final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Mission 1");
        status_panel.add(status);
        
        final Field field = new Field(status);
        frame.add(field, BorderLayout.CENTER);
        
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    field.reset();
                }
            });
        control_panel.add(reset);
        
        final JButton quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        control_panel.add(quit);
        
        final JFrame instructions = new JFrame("Instructions");
		instructions.setLocation(500,500);
		
		final Instructions instr = new Instructions();
		instructions.add(instr, BorderLayout.CENTER);
		
		instructions.pack();
        
        final JButton rules = new JButton("Instructions");
        rules.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		instructions.setVisible(true);
        		instructions.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	}
        });
        control_panel.add(rules);
        
		frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
