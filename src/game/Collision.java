package game;

public class Collision {
	
	// better collision detection
	
	public static boolean isCollidedX(int x1, int width1, int x2, int width2) {	
		if (x1 <= x2 && x1 + width1 >= x2) {
			
			return true;
		}
		else if (x2 >= x1 && x1 <= x2 + width2) {
			
			return true;
		}
		return false;
	}
	
	public static boolean isCollidedY(int y1, int width1, int y2, int width2) {	
		if (y1 <= y2 && y1 + width1 >= y2) {
			
			return true;
		}
		else if (y2 >= y1 && y1 <= y2 + width2) {
			
			return true;
		}
		return false;
	}
	//
	
	public static boolean tankWall(Tank tank, Wall wall) {
	    float tankWidth = 30;
	    float tankHeight = 20;
	    float tankLeft = tank.getPosition().x - tankWidth/2;
	    float tankRight = tank.getPosition().x + tankWidth/2;
	    float tankTop = tank.getPosition().y - tankHeight/2;
	    float tankBottom = tank.getPosition().y + tankHeight/2;
	    
	    // Wall bounds
	    float wallLeft = wall.x;
	    float wallRight = wall.x + wall.width;
	    float wallTop = wall.y;
	    float wallBottom = wall.y + wall.height;
	    
	    return tankRight > wallLeft &&
	           tankLeft < wallRight &&
	           tankBottom > wallTop &&
	           tankTop < wallBottom;
	}
    
    public static Vector2 getWallNormal(Bullet bullet, Wall wall) {
        float closestX = Math.max(wall.x, Math.min(bullet.position.x, wall.x + wall.width));
        float closestY = Math.max(wall.y, Math.min(bullet.position.y, wall.y + wall.height));
        
        Vector2 normal = new Vector2(bullet.position.x - closestX, 
                                    bullet.position.y - closestY);
        return normal.length() > 0 ? normal.normalize() : new Vector2(0, 1);
    }

    public static boolean bulletWall(Bullet bullet, Wall wall) {
        Vector2 nextPos = bullet.position.add(bullet.velocity.mult(0.016f));
        
        return nextPos.x + 3 > wall.x &&
               nextPos.x - 3 < wall.x + wall.width &&
               nextPos.y + 3 > wall.y &&
               nextPos.y - 3 < wall.y + wall.height;
    }
    
    public static boolean bulletTank(Bullet bullet, Tank tank) {
        float distance = bullet.position.distanceTo(tank.getPosition());
        return distance < 20;
    }
   
    public static float distance(Vector2 a, Vector2 b) {
        float dx = a.x - b.x;
        float dy = a.y - b.y;
        return (float)Math.sqrt(dx*dx + dy*dy);
    }
}