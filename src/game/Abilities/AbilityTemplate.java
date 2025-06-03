package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class AbilityTemplate implements Ability {
	
	boolean toggle;
	
	public AbilityTemplate() {
		
		this.toggle = false;
	}
	
	public AbilityTemplate copy() {
		
		return new AbilityTemplate();
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
		
		return "Ability Template - <insert description>";
	}
}
