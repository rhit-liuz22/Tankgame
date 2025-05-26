package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class WindUp implements Ability {
	
	boolean toggle;
	
	public WindUp() {
		
		this.toggle = false;
	}

	public void modifyTank(TankSkeleton tank){
		
		if (this.toggle) {
			
			return;
		}
		
		//reduce movespeed
		tank.setVelocity(tank.getVelocity() * .8f);
		
		//reduce reload
		tank.setReloadCD((int) (tank.getReloadCD() * 1.2));
	}
	
	public void modifyBullet(BulletSkeleton bullet) {
		
		if (this.toggle) {
			
			return;
		}
		
		//increase damage and bullet speed
		bullet.addDamage((int) (bullet.getDamage() * 1.5));
		bullet.setVelocity(bullet.getVelocity() * 1.5f);
	}
	
	public void toggleFalse() {
		
		this.toggle = false;
	}
	
	public void toggleTrue() {
		
		this.toggle = true;
	}
}
