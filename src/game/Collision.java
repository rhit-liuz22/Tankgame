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
			x2 = entity2.getNextX() - Math.abs(dx1);
			width2 = entity2.getWidth() + Math.abs(dx1);
		}
		
		// next frame rectangle
		if (entity1.getNextX() + entity1.getWidth() > x2
			&& entity1.getNextX() < entity2.getNextX() + width2) {
			
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
			y2 = entity2.getNextY() - Math.abs(dy1);
			height2 = entity2.getHeight() + Math.abs(dy1);
		}
		
		// next frame rectangle
		if (entity1.getNextY() + entity1.getHeight() > y2
			&& entity1.getNextY() < entity2.getNextY() + height2) {
			
			return true;
		}
		
		return false;
	}
}