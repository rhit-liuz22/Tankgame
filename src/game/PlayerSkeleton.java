package game;

import java.awt.Color;

public class PlayerSkeleton {
	
	private int id;
	private TankSkeleton tank;
	
	private Color color;
	
	private int score;
	private int half;
	private Controls controls;
	
	//TODO make abilities class and create fields for collected modifications like bullet damage, size, etc
	// add ability list field
	
    public PlayerSkeleton(int id, Color color, Controls controls) {
    	
        this.id = id;
        this.tank = new TankSkeleton(50, 50, 0, color);
        
        this.color = color;
        
        this.score = 0;
        this.half = 0;
        this.controls = controls;
    }
    
    public void spawnTank(int x, int y, float theta) {
    	
    	this.tank.setX(x);
    	this.tank.setY(y);
    	this.tank.setTheta(theta);
    }
    
    public void update() {
    	
    	this.tank.update();
    }
    
    public void incrementScore() {
        score++;
    }
    
    public void incrementHalf() {
    	
    	this.half += 1;
    }
    
    public void resetHalf() {
    	
    	this.half = 0;
    }
	
    // Getters and Setters
    
    public int getID() {
    	
    	return this.id;
    }
    
    public Color getColor() {
    	
    	return this.color;
    }
    
    public TankSkeleton getTank() {
    	
    	return this.tank;
    }
    
    public int getScore() {
    	
    	return this.score;
    }
    
    public int getHalf() {
    	
    	return this.half;
    }
    
    public Controls getControls() {
    	
    	return this.controls;
    }
    
    
}
