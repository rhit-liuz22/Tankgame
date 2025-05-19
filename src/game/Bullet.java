package game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet {
    Vector2 position;
    Vector2 velocity;
    float radius = 3f;
    
    Vector2 nextpos;
    
    private Color color;
    private float lifetime;
    private Tank owner;
    private int bounces = 0;
    private boolean canHitOwner = false;
    private boolean bounceEnabled = true;
    private int maxBounces = 0;
    
    private float deltaTime; // THIS FEELS UGLY
    
    public Bullet(Vector2 position, Vector2 direction, Color color, Tank owner) {
        this.position = position;
        this.velocity = direction.mult(300f);
        this.color = color;
        this.lifetime = 2f;
        this.owner = owner;
        
        // TODO: temporary
        this.maxBounces = 5;
    }
    
    public void runCalculations(float deltaTime) {
    	this.nextpos = position.add(velocity.mult(deltaTime));
    			
    }
    public void update(float deltaTime) {
    	this.deltaTime = deltaTime;
    	updateNextPosition(deltaTime);
    	
        this.position = this.nextpos;
        
        lifetime -= deltaTime;
    }
    
    public void updateNextPosition(float deltaTime) {
    	
    	this.nextpos = position.add(velocity.mult(deltaTime));
    }
    
    public void render(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillOval(
            (int)(position.x - radius),
            (int)(position.y - radius),
            (int)(radius * 2),
            (int)(radius * 2)
        );
    }
    
    public boolean isExpired() {
        return lifetime <= 0 || 
               position.x < 0 || position.x > 704 ||
               position.y < 0 || position.y > 640;
    }
    
    public boolean bounceCheck(Wall wall) {
    	
    	int x = (int) (this.nextpos.x - this.radius);
    	int y = (int) (this.nextpos.y - this.radius);
    	int diameter = (int) this.radius * 2;
    	
    	if (Collision.isCollidedX(x, diameter, wall.x, wall.width) &&
			Collision.isCollidedY(y,  diameter, wall.y,  wall.height)) {
    		
    		if (this.bounceEnabled && this.bounces < this.maxBounces) {
    			
	    		if ((int) this.position.x + diameter <= wall.x) {
	    			
	    			this.velocity.x = -1 * Math.abs(this.velocity.x);
	    			updateNextPosition(this.deltaTime);
	    		}
	    		else if ((int) this.position.x >= wall.x + wall.width) {
	    			
	    			this.velocity.x = Math.abs(this.velocity.x);
	    			updateNextPosition(this.deltaTime);
	    		}
				if ((int) this.position.y + diameter <= wall.y || (int) this.position.y >= wall.y + wall.height) {
	    			
	    			bounceY();
	    		} //TODO: 
    		}
			System.out.println((this.position.x - this.radius) + " " + (this.position.y - this.radius));
			System.out.println(x + " " + y + " " + wall.x + " " + wall.y);
			return true;
    	}
    	else {
    		this.position = this.nextpos;
    		return false;
    	}
    }
    
    public void bounceX() {
    	
    	System.out.println("workingx");
    	System.out.println(this.bounces);
    	System.out.println(this.maxBounces);
    	if (this.bounceEnabled && this.bounces < this.maxBounces) {
    		
            velocity.x *= -1;
            this.bounces++;
            this.canHitOwner = true;
    	}
    }

    public void bounceY() {
    	
    	System.out.println("workingy");
    	System.out.println(this.bounces);
    	System.out.println(this.maxBounces);
    	if (this.bounceEnabled && this.bounces < this.maxBounces) {
    		
            velocity.y *= -1;
            this.bounces++;
            this.canHitOwner = true;
    	}
    }
    
    // Getters
    public Tank getOwner() { return owner; }
    public boolean canHitOwner() { return canHitOwner; }
    public int getBounces() { return bounces; }
}