package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class PoisonBullets implements Ability {
	
	boolean toggle;
	
	public PoisonBullets() {
		
		this.toggle = false;
	}
	
	public PoisonBullets copy() {
		
		return new PoisonBullets();
	}


	public void modifyTank(TankSkeleton tank){
		
		if (this.toggle) {
			
			return;
		}
	}
	
	public void modifyBullet(BulletSkeleton bullet) {

		return;
	}
	
	public void collisionEffect(BulletSkeleton bullet, TankSkeleton tankhit) {
		
		// find a way to call this method over time
		
		return;
	}
	
	public void toggleFalse() {
		
		this.toggle = false;
	}
	
	public void toggleTrue() {
		
		this.toggle = true;
	}
	
	public void resetCD() {
		
		return;
	}
	
	public String getAbilityDescription() {
		
		return "Poison Bullets - ";
	}
}
