package game;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;

public class TankSkeleton implements Entity {
	
	// loading files
	private java.awt.image.BufferedImage tankIdleSprite;
	private Color color;

	// current state
	private float x, y; // position
	private float theta; // angle in degrees
	private String action; // idle, moving, etc
	
	private final int WIDTH = 48;
	private final int HEIGHT = 40;
	
	private int maxHP = 100;
	
	private int hp;
	
	private java.awt.image.BufferedImage tankSprite; // no calculations so only update per state
	
	// next frame state (calculations)
	private float  dx, dy; // change in position
	private float dtheta; // change in angle
	private int dhp; // change in hit points
	
	// movement
	private float tvel = 200; // translational velocity (movement speed)
	private float backwardtvel = 150; // backwards movement speed
	private float rvel = 180; // rotational velocity (rotation speed)
	
	private ArrayList<Ability> abilities;
	
	private ArrayList<BulletSkeleton> bullets;
	private int shootCD = 0; // shooting cooldown (how many frames left until shoot enabled)
	private int reloadCD = 60; // once per second
	private boolean canShoot = true; // what if we add stuns so shootCD isn't the only thing stopping shooting
	private float bulletspeed = 200;
	private int bulletradius = 3; // default
	private int bulletdamage = 30;
	private int bulletBounces = 3; 
	
	private int dashCD = 0;
	private int dashFrame = 0;
	private int dashReloadCD = 300; // once per 5 seconds
	private float dashspeed = 10;
	private float dashvel;
	private float dashtheta;
	private boolean dashBackward;
	
	private boolean canRender;
	private boolean canControl;
	
	
	public TankSkeleton(int x, int y, float theta, Color color) {
		
		this.x = x;
		this.y = y;
		this.theta = theta;
		
		this.hp = this.maxHP;
		
		System.out.println(color.getRed() + " " + color.getBlue());
		try {
			
			if (color.getRed() == 255) {

				this.tankIdleSprite = ImageIO.read(new File("src/images/tankSprites/TankIdleRed.png"));
				this.tankSprite = this.tankIdleSprite;
			}
			else if (color.getBlue() == 255) {
				
				this.tankIdleSprite = ImageIO.read(new File("src/images/tankSprites/TankIdleBlue.png"));
				this.tankSprite = this.tankIdleSprite;
			}
			else {
				this.tankIdleSprite = ImageIO.read(new File("src/images/tankSprites/TankIdle.png"));
				this.tankSprite = this.tankIdleSprite;
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		this.color = color;
		this.bullets = new ArrayList<>();
		this.abilities = new ArrayList<>();
	}
	
	public void runCalculations(float deltaTime) {
		
		if (this.shootCD <= 0) { // ADD CONDITIONS AS NECESSARY (ex: stunning ability)
			
			this.canShoot = true;
		}
		else {
			
			this.canShoot = false;
		}
		
		runAbilities();
		
		calculateBullets(deltaTime);
	}
	
	public void update() {
		
//		printState();
		updateMovement();
		updateHealth();
		updateCooldown();
		updateSprite(this.action);
		
		// bullets
    	for (BulletSkeleton bullet : bullets) {
    		
    		bullet.update();
    	}
	}
	
	private void updateMovement() {
		
		this.x += this.dx;
		this.y += this.dy;
		this.theta += this.dtheta;
		this.dx = 0;
		this.dy = 0;
		this.dtheta = 0;
		
		dashing();
	}
	
	private void updateHealth() {
		
		this.hp += this.dhp;
		this.dhp = 0;
	}
	
	private void updateCooldown() {
		
		if (this.shootCD > 0) {
			
			this.shootCD -= 1;
		}
		
		if (this.dashCD > 0) {
			
			this.dashCD -= 1;
		}
	}
	
	private void updateSprite(String action) {
		
		if (action == null) {
			
			this.tankSprite = this.tankIdleSprite;
		}
	}
	
	public void runAbilities() {
		
		for (Ability ability : abilities) {
			
			ability.modifyTank(this);
			
			for (BulletSkeleton bullet : bullets) {
				
				ability.toggleFalse();
				ability.modifyBullet(bullet);
			}
			
			ability.toggleTrue();
		}
	}
	
	public void moveForward(float deltaTime) {
		
		this.dx += this.tvel * Math.cos(this.theta * Math.PI / 180f) * deltaTime;
		this.dy += this.tvel * Math.sin(this.theta * Math.PI / 180f) * deltaTime;
		
		this.dashBackward = false;
	}
	
	public void moveBackward(float deltaTime) {
		
		this.dx -= this.backwardtvel * Math.cos(this.theta * Math.PI / 180f) * deltaTime;
		this.dy -= this.backwardtvel * Math.sin(this.theta * Math.PI / 180f) * deltaTime;
		
		this.dashBackward = true;
	}
	
	// POSITIVE ANGLE IS CLOCKWISE BECAUSE POSITIVE Y IS DOWNWARDS
	public void rotateCCW(float deltaTime) {
		
		this.dtheta -= this.rvel * deltaTime;
	}
	
	public void rotateCW(float deltaTime) {
		
		this.dtheta += this.rvel * deltaTime;
	}
	
	public void addHealth(int dhp) {
		
		if (this.hp + dhp <= this.maxHP) {
			
			this.dhp = dhp;
		}
		else {
			
			this.dhp = this.maxHP - this.hp;
		}
	}
	
	public void render(JPanel panel, Graphics2D g2d) {
		
		g2d.setColor(this.color);
		
		java.awt.geom.AffineTransform originalTransform = g2d.getTransform();

        g2d.drawRect((int) this.x, (int) this.y, this.WIDTH, this.HEIGHT); // hitbox
		
		g2d.translate(getMiddleX(), getMiddleY());
        g2d.rotate(this.theta * Math.PI / 180f);
        
        // draw image
        g2d.drawImage(this.tankSprite, -this.WIDTH / 2, -this.HEIGHT / 2,
    		this.tankSprite.getWidth(), this.tankSprite.getHeight(), panel);

        g2d.setTransform(originalTransform);
        
        panel.repaint();
	}
	
	public void printState() {
		
		System.out.println("position: (" + x + ", " + y + ") " + "  angle: " + theta + " degrees");
		System.out.println("update: " + dx + " " + dy + " " + dtheta);
	}
	
    public void calculateBullets(float deltaTime) {
    	
    	ArrayList<BulletSkeleton> toExpire = new ArrayList<>();
    	
    	for (BulletSkeleton bullet : bullets) {
    		
        	bullet.move(deltaTime);
        	if (bullet.isExpired()) {
        		
        		toExpire.add(bullet);
        	}
    	}
    	
    	for (BulletSkeleton bullet : toExpire) {
    		
    		expireBullet(bullet);
    	}
    }
    
    private void expireBullet(BulletSkeleton bullet) {
    	
    	this.bullets.remove(bullet);
    }
    
    public void shootBullet() {
    	
    	if (this.canShoot) {
    		this.shootCD = this.reloadCD;
    		
        	//TODO update when done with abilities
        	BulletSkeleton bullet = new BulletSkeleton(this, getBulletX(), getBulletY(), this.theta,
        			this.bulletspeed + this.tvel, true, bulletBounces, bulletradius, bulletdamage, color);
        	
        	this.bullets.add(bullet);
    	}
    }
    
    public void dash() {
    	
    	if (dashCD <= 0) {
    		
    		this.dashvel = this.dashspeed;
    		this.dashtheta = this.theta;
    		if (this.dashBackward) {
    			this.dashtheta += 180;
    		}
    		this.dashCD = this.dashReloadCD;
    		this.dashFrame = 0;
    	}
    	else {
    		
    		return;
    	}
    }
    
    private void dashing() {
    	
    	if (this.dashFrame <= 15) {
    		
    		this.dx += this.dashvel * Math.cos(this.dashtheta * Math.PI / 180f);
    		this.dy += this.dashvel * Math.sin(this.dashtheta * Math.PI / 180f);
    		this.dashvel *= .9f;
    		this.dashFrame += 1;
    	}
    }
    
	
	
	// Getters & Setters
	
	public float getX() {
		
		return this.x;
	}
	
	public float getY() {
		
		return this.y;
	}
	
	public float getTheta() {
		
		return this.theta;
	}
	
	public float getNextX() {
		
		return this.x + this.dx;
	}
	
	public float getNextY() {
		
		return this.y + this.dy;
	}
	
	public int getWidth() {
		
		return this.WIDTH;
	}
	
	public int getHeight() {
		
		return this.HEIGHT;
	}
	
	public int getMiddleX() {
		
		return (int) this.x + this.WIDTH/2;
	}
	
	public int getMiddleY() {
		
		return (int) this.y + this.HEIGHT/2;
	}
	
	public float getVelocity() {
		
		return this.tvel;
	}
	
	public float getBackVelocity() {
		
		return this.backwardtvel;
	}
	
	public void setVelocity(float newvel, float newbackvel) {
		
		this.tvel = newvel;
		this.backwardtvel = newbackvel;
	}
	
	public int getHealth() {
		
		return this.hp;
	}
	
    public ArrayList<BulletSkeleton> getBulletList(){
    	
    	return this.bullets;
    }
	
	public void setX(int x) {
		
		this.x = x;
	}
	
	public void setY(int y) {
		
		this.y = y;
	}
	
	public void setTheta(float theta) {
		
		this.theta = theta;
	}
	
	public float getdx() {
		
		return this.dx;
	}
	
	public float getdy() {
		
		return this.dy;
	}
	
	public void setdx(int dx) {
		
		this.dx = dx;
	}
	
	public void setdy(int dy) {
		
		this.dy = dy;
	}
	
	public int getBulletX() {
		
		return getMiddleX() + (int) (16*Math.cos(this.theta * Math.PI / 180f)) - bulletradius;
	}
	
	public int getBulletY() {
		
		return getMiddleY() + (int) (16*Math.sin(this.theta * Math.PI / 180f)) - bulletradius;
	}
	
	public int getBulletBounces() {
		
		return this.bulletBounces;
	}
	
	public void setBulletBounces(int bounces) {
		
		this.bulletBounces = bounces;
	}
	
	public int getBulletDamage() {
		
		return this.bulletdamage;
	}
	
	public void setBulletDamage(int damage) {
		
		this.bulletdamage = damage;
	}
	
	public float getBulletSpeed() {
		
		return this.bulletspeed;
	}
	
	public void setBulletSpeed(float speed) {
		
		this.bulletspeed = speed;
	}
	
	public int getBulletRadius() {
		
		return this.bulletradius;
	}
	
	public void setBulletRadius(int rad) {
		
		if (rad < 1) {
			return;
		}
		this.bulletradius = rad;
	}
	
	public Color getColor() {
		
		return this.color;
	}
	
	public void addAbility(Ability ability) {
		
		this.abilities.add(ability);
	}
	
	public int getReloadCD() {
		
		return this.reloadCD;
	}
	
	public void setReloadCD(int cd) {
		
		this.reloadCD = cd;
	}
	
	public boolean getRender() {
		return this.canRender;
	}
	
	public void setRender(boolean render) {
		this.canRender = render;
	}
	
	public boolean getControl() {
		return this.canControl;
	}
	
	public void setControl(boolean control) {
		this.canControl = control;
	}
	
	public int getMaxHealth() {
		
		return this.maxHP;
	}
	
	public void setMaxHealth(int health) {
		
		this.maxHP = health;
	}
	
	public void setHealth(int health) {
		this.hp = health;
	}
}
