package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class Grow implements Ability{
	
	boolean toggle;
	int cd = 0;
	int resetCD = 60; // grow
	
	public Grow() {
		
		this.toggle = false;
	}
	
	public Grow copy() {
		
		return new Grow();
	}

	public void modifyTank(TankSkeleton tank){
		
		if (this.toggle) {
			
			return;
		}
		
		// increase tank hp
		tank.setMaxHealth(tank.getMaxHealth() * 1.2f);
		tank.setHealth(tank.getMaxHealth());
	}
	
	public void modifyBullet(BulletSkeleton bullet) {

		// increase bullet damage and speed over time
		
		bullet.addDamage(bullet.getDamage() * 0.0035f);
		bullet.addVelocity(bullet.getVelocity() * 0.01f);
		
		return;
	}
	
	public void toggleFalse() {
		
		this.toggle = false;
	}
	
	public void toggleTrue() {
		
		this.toggle = true;
	}
	
	public void resetCD() {
		
		this.cd = resetCD;
	}
	
	public String getAbilityDescription() {
		
		return "Grow - ";
	}
}
