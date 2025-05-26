package game;

public class Collision {
	
	// for each entity: run calculations -> collision check -> update
	
	// better better collision detections
	
	// if checking collision with wall, wall should be entity2
	public static boolean isCollidedX(Entity entity1, Entity entity2) {
		
		double x2 = entity2.getNextX();
		double width2 = entity2.getWidth();
		
		//artificial extending wall hitbox
		if (entity2.getClass().isInstance(Wall.class)) {
			
			double dx1 = entity1.getVelocity() * Math.cos(entity1.getTheta());
			if (dx1 > 0) {
				
				x2 = entity2.getNextX() - dx1;
			}
			else {
				
				width2 = entity2.getWidth() - dx1;
			}
		}
		
		// next frame rectangle
		if (entity1.getNextX() + entity1.getWidth() > x2
			&& entity1.getNextX() < x2 + width2) {
			
			return true;
		}
		
		return false;
	}
	
	public static boolean isCollidedY(Entity entity1, Entity entity2) {
		
		double y2 = entity2.getNextY();
		double height2 = entity2.getHeight();
		
		//artificial extending wall hitbox
		if (entity2.getClass().isInstance(Wall.class)) {
			
			double dy1 = entity1.getVelocity() * Math.sin(entity1.getTheta());
			if (dy1 > 0) {
				
				y2 = entity2.getNextY() - dy1;
			}
			else {
				
				height2 = entity2.getHeight() - dy1;
			}
		}
		
		// next frame rectangle
		if (entity1.getNextY() + entity1.getHeight() > y2
			&& entity1.getNextY() < y2 + height2) {
			
			return true;
		}
		
		return false;
	}
}