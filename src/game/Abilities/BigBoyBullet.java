package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class BigBoyBullet implements Ability {
	
	boolean toggle;
	
	public BigBoyBullet() {
		
		this.toggle = false;
	}
	
	public BigBoyBullet copy() {
		
		return new BigBoyBullet();
	}


	public void modifyTank(TankSkeleton tank){
		
		if (this.toggle) {
			
			return;
		}
		
		//increase damage and radius of bullet, decrease its speed
		
		tank.setBulletDamage(tank.getBulletDamage() * 2);
		
		tank.setBulletSpeed(tank.getBulletSpeed() * .5f);
		
		tank.setBulletRadius(tank.getBulletRadius() * 1.75f);
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
		
		return "Big Boy Bullet - slower bullet speed, increased bullet damage and size";
	}
}
