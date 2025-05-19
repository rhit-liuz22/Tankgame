package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Tank {
    private Vector2 position;
    private Vector2 direction;
    private Color color;
    private List<Bullet> bullets;
    private float shootCooldown;
    private boolean isMovingForward;
    private boolean isMovingBackward;
    private boolean isRotatingLeft;
    private boolean isRotatingRight;
    private float speed = 200f;
    private float rotationSpeed = 3f; // radians per second
    private int health = 100;
    
    public Tank(Color color) {
        this.color = color;
        this.position = new Vector2(0, 0);
        this.direction = new Vector2(1, 0);
        this.bullets = new ArrayList<>();
        this.shootCooldown = 0;
    }
    
    public void update(float deltaTime) {
        // Handle rotation
        if (isRotatingLeft) {
            direction = direction.rotate(-rotationSpeed * deltaTime);
        }
        if (isRotatingRight) {
            direction = direction.rotate(rotationSpeed * deltaTime);
        }
        
        // Handle movement
        if (isMovingForward) {
            position = position.add(direction.mult(speed * deltaTime));
        }
        if (isMovingBackward) {
            position = position.add(direction.mult(-speed * deltaTime * 0.7f)); // Slower when moving backward
        }
        
        // Update cooldown and bullets
        if (shootCooldown > 0) {
            shootCooldown -= deltaTime;
        }
        
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(deltaTime);
            
            if (bullet.isExpired()) {
                bullets.remove(i);
            }
        }
    }
    
    public void render(Graphics2D g2d) {
        // Save original transform
        java.awt.geom.AffineTransform originalTransform = g2d.getTransform();

        g2d.translate(position.x, position.y);
        g2d.rotate(direction.angle());

        g2d.setColor(color);
        g2d.fillRoundRect(-20, -15, 40, 30, 10, 10);

        g2d.setColor(color.darker());
        g2d.fillRect(15, -5, 25, 10);

        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillOval(-15, -10, 20, 20);

        g2d.setTransform(originalTransform);
    }
    
    public void shoot() {
        if (shootCooldown <= 0) {
            bullets.add(new Bullet(
                position.copy().add(direction.mult(25)),
                direction.copy(),
                color,
                this
            ));
            shootCooldown = 0.5f;
        }
    }
    
    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }
    
    public void resolveWallCollision(Wall wall) {
        float tankWidth = 30;
        float tankHeight = 20;
        float tankLeft = position.x - tankWidth/2;
        float tankRight = position.x + tankWidth/2;
        float tankTop = position.y - tankHeight/2;
        float tankBottom = position.y + tankHeight/2;

        float wallLeft = wall.x;
        float wallRight = wall.x + wall.width;
        float wallTop = wall.y;
        float wallBottom = wall.y + wall.height;
        
        float xPenetration = Math.min(
            Math.abs(tankRight - wallLeft),
            Math.abs(wallRight - tankLeft)
        );
        
        float yPenetration = Math.min(
            Math.abs(tankBottom - wallTop),
            Math.abs(wallBottom - tankTop)
        );

        if (xPenetration < yPenetration) {
            if (direction.x > 0) {
                position.x = wallLeft - tankWidth/2;
            } else {
                position.x = wallRight + tankWidth/2;
            }
        } else {
            if (direction.y > 0) {
                position.y = wallTop - tankHeight/2;
            } else {
                position.y = wallBottom + tankHeight/2;
            }
        }
        
        float buffer = 0.5f;
        if (direction.x > 0) position.x -= buffer;
        if (direction.x < 0) position.x += buffer;
        if (direction.y > 0) position.y -= buffer;
        if (direction.y < 0) position.y += buffer;
    }
    
    // Getters and setters
    public Vector2 getPosition() { return position; }
    public List<Bullet> getBullets() { return bullets; }
    public Color getColor() { return color; }
    public int getHealth() { return health; }
    public void setPosition(Vector2 position) { this.position = position; }
    public void setMovingForward(boolean moving) { isMovingForward = moving; }
    public void setMovingBackward(boolean moving) { isMovingBackward = moving; }
    public void setRotatingLeft(boolean rotating) { isRotatingLeft = rotating; }
    public void setRotatingRight(boolean rotating) { isRotatingRight = rotating; }

}