package game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet {
    Vector2 position;
    Vector2 velocity;
    private Color color;
    private float lifetime;
    private float radius = 3f;
    private Tank owner;
    private int bounces = 0;
    private boolean canHitOwner = false;
    
    public Bullet(Vector2 position, Vector2 direction, Color color, Tank owner) {
        this.position = position;
        this.velocity = direction.mult(300f);
        this.color = color;
        this.lifetime = 2f;
        this.owner = owner;
    }
    
    public void update(float deltaTime) {
        position = position.add(velocity.mult(deltaTime));
        lifetime -= deltaTime;
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
    
    public void bounce(Vector2 normal) {
        velocity = velocity.sub(normal.mult(2 * velocity.dot(normal)));
        bounces++;
        canHitOwner = true;
    }
    
    // Getters
    public Tank getOwner() { return owner; }
    public boolean canHitOwner() { return canHitOwner; }
    public int getBounces() { return bounces; }
}