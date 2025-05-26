package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class PlayerSkeleton {
	
	private int id;
	private TankSkeleton tank;
	
	private Color color;
	
	private int score;
	private Controls controls;
	
	//TODO make abilities class and create fields for collected modifications like bullet damage, size, etc
	// add ability list field
	
    public PlayerSkeleton(int id, Color color, Controls controls) {
    	
        this.id = id;
        this.tank = new TankSkeleton(50, 50, 0, color);
        
        this.color = color;
        
        this.score = 0;
        this.controls = controls;
    }
    
    //TODO make addAbility(Ability ability){ add ability to abilityList} 
    //TODO make runAbilities() that goes thru all abilities
    //TODO abilities will have modifyTank() and modifyBullet() that takes tank/bullet input
    
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
    
    public Controls getControls() {
    	
    	return this.controls;
    }
    
    
}
