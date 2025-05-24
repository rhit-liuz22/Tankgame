package game;

import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Color;

public class Wall implements Entity { // entity bc collision (and maybe moving walls)
    private int x, y, width, height;
    private float theta = 0; // 0 is vertical wall (default), 90 is horizontal, etc
    private float dtheta;
    public int dx = 0; // default is static (does not move)
    public int dy = 0;
    public Color color = Color.BLACK;
    
    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void update() {
    	
    	updatePosition();
    }
    
    public void updatePosition() {
    	
    	this.x += this.dx;
    	this.y += this.dy;
    	this.theta += this.dtheta;
    	
    	this.dx = 0;
    	this.dy = 0;
    	this.dtheta = 0;
    }
    
	public void render(JPanel panel, Graphics2D g2d) {
		
		java.awt.geom.AffineTransform originalTransform = g2d.getTransform();

        g2d.translate(this.x, this.y);
        g2d.rotate(this.theta * Math.PI / 180);
        
        // draw image
        g2d.setColor(this.color);
        g2d.fillRect(x, y, width, height);
        
        g2d.setTransform(originalTransform);
        
        panel.repaint();
	}
    
    // getters and setters
    
    public void setdx(int dx) {
    	
    	this.dx = dx;
    }
    
    public void setdy(int dy) {
    	
    	this.dy = dy;
    }
    
    public void setdtheta(float dtheta) {
    	
    	this.dtheta = dtheta;
    }

	public int getX() {
		
		return this.x;
	}
	
	public int getY() {
		
		return this.y;
	}
	
	public float getTheta() {
		
		return this.theta;
	}
	
	public int getNextX() {
		
		return this.x + this.dx;
	}
	
	public int getNextY() {
		
		return this.y + this.dy;
	}
	
	public int getWidth() {
		
		return this.width;
	}
	
	public int getHeight() {
		
		return this.height;
	}
}