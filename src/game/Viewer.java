package game;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Viewer {

	
	public static void createGUI() {
		JFrame frame = new JFrame("Viewer");
		frame.setLayout(new java.awt.BorderLayout());
		frame.setSize(704, 704);
		frame.setLocationRelativeTo(null);
		
		frame.add(new Game());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.requestFocusInWindow();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> createGUI());
	}
}
