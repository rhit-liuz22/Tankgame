package game;

import javax.swing.JPanel;
import java.awt.Graphics2D;

public interface Entity {
	
	void update();
	
	void render(JPanel panel, Graphics2D g2d);

	
	float getX();
	
	float getY();
	
	float getNextX();
	
	float getNextY();
	
	int getWidth();
	
	int getHeight();
}
