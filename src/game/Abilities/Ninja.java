package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class Ninja implements Ability{
	
	boolean toggle;
	
	public Ninja() {
		
		this.toggle = false;
	}
	
	public Ninja copy() {
		
		return new Ninja();
	}

	public void modifyTank(TankSkeleton tank){
		
		if (this.toggle) {
			
			return;
		}
		
		// increase dash speed, dash reload speed, movement speed, reload speed, decrease max health
		
		tank.setDashCD(tank.getDashCD() * 0.3f);
		tank.setDashSpeed(tank.getDashSpeed() * 1.35f);
		tank.setVelocity(tank.getVelocity() * 1.2f, tank.getBackVelocity() * 1.2f);
		tank.setReloadCD(tank.getReloadCD() * 0.8f);
		tank.setMaxHealth(tank.getMaxHealth() * .75f);
		
		tank.setHealth(tank.getMaxHealth());
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
		
		return "Ninja - decrease max health, increase dash speed and bullet speed ";
	}
}
