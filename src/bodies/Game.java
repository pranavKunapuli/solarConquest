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
		/*
		final Instructions instr = new Instructions();
		instructions.add(instr, BorderLayout.CENTER);
		*/
		String rule = "Introductions:"
				+ "\nAttention recruit! Your home planet of earth is under "
				+ "\nthreat of invasion from neighboring planets. Their species "
				+ "\nare very hostile to humans, and they come to earth in "
				+ "\nsearch of our most valuable resource: water. High command "
				+ "\nhas issued an order from Admiral Benjamin Pierce to launch "
				+ "\na preemptive strike against the enemy combatants. You, "
				+ "\nrecruit, will be presented with a series of missions, "
				+ "\nthe goal being to conquer every neighboring planet in the "
				+ "\nspace field. Good luck, and may the odds be ever in your "
				+ "\nfavor."
				+ "\nGameplay:"
				+ "\nEach planet on the screen will be one of three colors: "
				+ "\ngreen (under your control with regeneration of forces), "
				+ "\ngray (no opposition to conquest), and red (opposition to "
				+ "\nconquest and degeneration of forces once conquered). "
				+ "\nGreen planets will always stay under your control, gray "
				+ "\nplanets once conquered will stay under your control and "
				+ "\nbecome green planets, but red planets will always oppose "
				+ "\nconquest. Each level will begin with at least one green "
				+ "\nplanet under your control. Each planet has health that "
				+ "\ndecreases when your forces attack (except for the green "
				+ "\nplanets), but attacking a planet also expends forces. To "
				+ "\nattack a planet, simply click on a green planet and then "
				+ "\nclick on a gray or red planet you wish to attack. In the "
				+ "\ncenter of each planet there are two numbers separated by a "
				+ "\ncolon; the first number represents the number of forces "
				+ "\nyou have at the planet, and the second number represents "
				+ "\nthe health of the planet. Hence for green and gray "
				+ "\nplanets, your forces will constantly increase, whereas "
				+ "\nfor red planets, your forces will constantly decrease. "
				+ "\nNote that a red planet is considered under your control "
				+ "\nif its health is zero. But remember to keep up the forces "
				+ "\non that planet, or the health will reset to the original "
				+ "\nand you will have to reconquer it. Also remember that the "
				+ "\nonly forces at your discretion are those based in green "
				+ "\nplanets. The forces already allocated to red planets "
				+ "\ncannot be reallocated."
				+ "\nTrick:"
				+ "\nThe trick to this game is that you must beat each level "
				+ "\nwithin a certain time limit. The earth's space technology "
				+ "\nis still not extremely advanced, so staying in space "
				+ "\nbeyond that time limit causes all forces to deteriorate "
				+ "\nand perish. So when playing, time is of the essence. To "
				+ "\nwin any level, all planets must be under your control. "
				+ "\nHow and when you attack is up to you.";
		final JLabel view_rules = new JLabel(rule);
		instructions.add(view_rules, BorderLayout.CENTER);
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
