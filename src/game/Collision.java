package game;

public class Collision {
	
	// for each entity: run calculations -> collision check -> update
	
	// better better collision detections
	public static boolean isCollidedX(Entity entity1, Entity entity2) {
		
		// next frame rectangle
		
		if (entity1.getNextX() + entity1.getWidth() > entity2.getNextX()
			&& entity1.getNextX() < entity2.getNextX() + entity2.getWidth()) {
			
			return true;
		}
		
		return false;
	}
	
	public static boolean isCollidedY(Entity entity1, Entity entity2) {
		
		// next frame rectangle
		
		if (entity1.getNextY() + entity1.getHeight() > entity2.getNextY()
			&& entity1.getNextY() < entity2.getNextY() + entity2.getHeight()) {
			
			return true;
		}
		
		return false;
	}
}