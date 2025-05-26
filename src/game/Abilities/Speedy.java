package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class Speedy implements Ability {
	
	boolean toggle;
	
	public Speedy() {
		
		this.toggle = false;
	}
	
	public Speedy copy() {
		
		return new Speedy();
	}


	public void modifyTank(TankSkeleton tank){
		
		if (this.toggle) {
			
			return;
		}
		
		//increase tank speed, bullet speed, and reload speed; decrease bullet size and damage
		
		tank.setVelocity(tank.getVelocity() * 1.2f, tank.getBackVelocity() * 1.2f);
		
		tank.setReloadCD(tank.getReloadCD() * .8f);
		
		tank.setBulletSpeed(tank.getBulletSpeed() * 1.25f);
		
		tank.setBulletRadius(tank.getBulletRadius() * .75f);
		
		tank.setBulletDamage(tank.getBulletDamage() * .75f);
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
	
	public void resetCD() {
		
		return;
	}
	
	public String getAbilityDescription() {
		
		return "Speedy - smaller bullet size and decreased damage, faster tank speed and bullet speed and reload time";
	}
}
