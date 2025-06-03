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

		// increase bullet damage and speed and radius over time
		
		bullet.setRadius(bullet.getRadius() * 1.001f);
		bullet.addDamage((float) Math.log(bullet.getDamage()) / 20f);
		bullet.addVelocity((float) Math.log(bullet.getVelocity()) / 5f);
		
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
		
		this.cd = resetCD;
	}
	
	public String getAbilityDescription() {
		
		return "Grow - N/A, Increase Tank HP and "
				+ "(increase bullet damage and increase bullet speed and incresae bullet radius)[over time]";
	}
}
