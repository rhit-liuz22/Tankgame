package game;

import java.awt.Graphics2D;

import javax.swing.JPanel;
import java.awt.Color;

public class BulletSkeleton implements Entity {

	private float x, y;
	private float theta;
	
	private float velocity; 
	
	// next frame
	
	private float dx, dy;
	private float dtheta;
	
	private boolean bounceEnabled;
	private int maxBounces;
	private int numBounces;
	
	private TankSkeleton owner;
	private boolean canHitOwner;
	private int framesAlive = 0;
	private boolean expired = false;
	
	private int damage;
	private int radius; 
	private int width;
	private int height;
	private Color color;
	
	public BulletSkeleton(TankSkeleton owner, int x, int y, float theta, float velocity,
			boolean bounceEnabled, int maxBounces, int radius, int damage, Color color) {
		
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.theta = theta;
		this.velocity = velocity;
		
		this.bounceEnabled = bounceEnabled;
		this.maxBounces = maxBounces;
		
		if (radius < 1) {
			
			this.radius = 1;
		}
		else {

			this.radius = radius;	
		}
		this.width = this.radius * 2;
		this.height = this.radius * 2;
		this.damage = damage;
		this.color = color;
		
		this.canHitOwner = false;
	}
		
	public void update() {
		
		this.framesAlive += 1;
		preventSelfHit();
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
	
	public void preventSelfHit() {
		
		if (this.framesAlive > 60) {
			
			this.canHitOwner = true;
		}
	}
	
	public void move(float deltaTime) {
		
		this.dx += this.velocity * Math.cos(this.theta * Math.PI / 180f) * deltaTime;
		this.dy += this.velocity * Math.sin(this.theta * Math.PI / 180f) * deltaTime;
	}
	
	public void addVelocity(float dv) { // for changing speed while alive
		
		this.velocity += dv;
	}
	
	public void addDamage(int dmg) { // for interactions that increase damage while alive
		
		this.damage += dmg;
	}
	
	public void bounce(float surfaceAngle) {
		
		if (this.bounceEnabled && this.numBounces < this.maxBounces) {
			
			this.dtheta = 540 + 2 * surfaceAngle - 2 * this.theta;
			
			this.numBounces++;
			
			this.canHitOwner = true;
		}
		else {
			
			this.expired = true;
		}
	}
	
	public void render(JPanel panel, Graphics2D g2d) {
		
		java.awt.geom.AffineTransform originalTransform = g2d.getTransform();
		
		g2d.translate(this.x + this.width/2, this.y + this.height/2);
        g2d.rotate(this.theta * Math.PI / 180f);
        
        g2d.setColor(color);
        g2d.fillOval(-this.width/2, -this.height/2, this.width, this.height);
        
        g2d.setTransform(originalTransform);
        
        panel.repaint();
	}
	
	
	
	// Getters and Setters
	
	public float getX() {
		
		return this.x;
	}
	
	public float getY() {
		
		return this.y;
	}
	
	public float getNextX() {
		
		return this.x + this.dx;
	}
	
	public float getNextY() {
		
		return this.y + this.dy;
	}
	
	public int getWidth() {
		
		return this.width;
	}
	
	public int getHeight() {
		
		return this.height;
	}
	
	public void setWidth(int width) {
		
		this.width = width;
	}
	
	public void setHeight(int height) {
		
		this.height = height;
	}
	
	public float getTheta() {
		
		return this.theta;
	}
	
	public void setTheta(float theta) {
		
		this.theta = theta;
	}
	
	public boolean isExpired() {
		
		return this.expired;
	}
	
	public void setExpired() {
		
		this.expired = true;
	}
	
	public int framesAlive() {
		
		return this.framesAlive;
	}
	
	public void setCanHitOwner(boolean state) {
		
		this.canHitOwner = state;
	}
	
	public boolean getCanHitOwner() {
		
		return this.canHitOwner;
	}
	
	public TankSkeleton getOwner() {
		
		return this.owner;
	}
	
	public void setRadius(int radius) {
		
		if (radius < 1) {
			return;
		}
		
		this.radius = radius;
	}
	
	public int getDamage() {
		
		return this.damage;
	}
	
	public void setDamage(int dmg) {
		
		this.damage = dmg;
	}
	
	public float getVelocity() {
		
		return this.velocity;
	}
	
	public void setVelocity(float vel) {
		
		this.velocity = vel;
	}
}
