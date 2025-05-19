package game;

public class Vector2 {
    public float x, y;
    
    public float dot(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }
    
    public Vector2 sub(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }
    
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public float distanceTo(Vector2 other) {
        float dx = this.x - other.x;
        float dy = this.y - other.y;
        return (float)Math.sqrt(dx*dx + dy*dy);
    }
    
    public Vector2 copy() {
        return new Vector2(x, y);
    }
    
    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }
    
    public Vector2 mult(float scalar) {
        return new Vector2(x * scalar, y * scalar);
    }
    
    public float length() {
        return (float)Math.sqrt(x*x + y*y);
    }
    
    public Vector2 normalize() {
        float len = length();
        return len > 0 ? new Vector2(x/len, y/len) : new Vector2(0, 0);
    }
    
    public float angle() {
        return (float)Math.atan2(y, x);
    }
    
    public Vector2 rotate(float radians) {
        float cos = (float)Math.cos(radians);
        float sin = (float)Math.sin(radians);
        return new Vector2(
            x * cos - y * sin,
            x * sin + y * cos
        );
    }
}