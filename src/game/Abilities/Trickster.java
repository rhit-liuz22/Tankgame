package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class Trickster implements Ability {
	
	boolean toggle;
	
	public Trickster() {
		
		this.toggle = false;
	}
	
	public Trickster copy() {
		
		return new Trickster();
	}


	public void modifyTank(TankSkeleton tank){
		
		if (this.toggle) {
			
			return;
		}
		
		// increase times bouncing
		tank.setBulletBounces(tank.getBulletBounces() + 2);
		
		// set max bounces
	}
	
	public void modifyBullet(BulletSkeleton bullet) {

		// increased bullet damage and speed upon each bounce
		
		if (bullet.isBouncing()) {
			
			bullet.addDamage(bullet.getDamage() * 0.2f);
			bullet.addVelocity(bullet.getVelocity() * 0.2f);
		}
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
		
		return "Trickster - N/A, increase bullet bounces and increase (bullet damage and speed; every bounce)";
	}
}
