package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class OwnerRecognition implements Ability{
	
	boolean toggle;
	
	public OwnerRecognition() {
		
		this.toggle = false;
	}
	
	public OwnerRecognition copy() {
		
		return new OwnerRecognition();
	}

	public void modifyTank(TankSkeleton tank){
		
		if (this.toggle) {
			
			return;
		}
	}
	
	public void modifyBullet(BulletSkeleton bullet) {

		bullet.setCanHitOwner(false);
		
		return;
	}
	
	public void collisionEffect(BulletSkeleton bullet, TankSkeleton tankhit) {
		
		return;
	}
	
	public void toggleFalse() {
		
		this.toggle = false;
	}
	
	public void toggleTrue() {
		
		this.toggle = true;
	}
	
	public void resetCD() {
		
		
	}
	
	public String getAbilityDescription() {
		
		return "Owner Recognition - N/A, your own bullets will phase through you";
	}
}
