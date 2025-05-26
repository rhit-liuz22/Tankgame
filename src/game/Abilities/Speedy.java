package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class Speedy implements Ability {
	
	boolean toggle;
	
	public Speedy() {
		
		this.toggle = false;
	}

	public void modifyTank(TankSkeleton tank){
		
		if (this.toggle) {
			
			return;
		}
		
		//increase tank speed, bullet speed, and reload speed; decrease bullet size and damage
		
		tank.setVelocity(tank.getVelocity() * 1.2f);
		
		tank.setBulletSpeed(tank.getBulletSpeed() * 1.25f);
		
		tank.setBulletRadius((int) (tank.getBulletRadius() * .75));
		
		tank.setBulletDamage((int) (tank.getBulletDamage() * .75));
	}
	
	public void modifyBullet(BulletSkeleton bullet) {

		return;
	}
	
	public void toggleFalse() {
		
		this.toggle = false;
	}
	
	public void toggleTrue() {
		
		this.toggle = true;
	}
	
	public String getAbilityDescription() {
		
		return "";
	}
}
