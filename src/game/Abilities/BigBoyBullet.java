package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class BigBoyBullet implements Ability {
	
	boolean toggle;
	
	public BigBoyBullet() {
		
		this.toggle = false;
	}

	public void modifyTank(TankSkeleton tank){
		
		if (this.toggle) {
			
			return;
		}
		
		//increase damage and radius of bullet, decrease its speed
		
		tank.setBulletDamage((int) (tank.getBulletDamage() * 2));
		
		tank.setBulletSpeed(tank.getBulletSpeed() * .5f);
		
		tank.setBulletRadius(tank.getBulletRadius() * 3);
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
}
