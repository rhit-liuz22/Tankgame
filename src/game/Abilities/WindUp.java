package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class WindUp implements Ability{
	
	boolean toggle;
	
	public WindUp() {
		
		this.toggle = false;
	}
	
	public WindUp copy() {
		
		return new WindUp();
	}

	public void modifyTank(TankSkeleton tank){
		
		if (this.toggle) {
			
			return;
		}
		
		//reduce movespeed and reload; increase damage and bullet speed
		tank.setVelocity(tank.getVelocity() * .8f, tank.getBackVelocity() * .8f);
		
		tank.setReloadCD((int) (tank.getReloadCD() * 1.2));
		
		tank.setBulletDamage((int) (tank.getBulletDamage() * 1.5));
		
		tank.setBulletSpeed(tank.getBulletSpeed() * 1.5f);
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
		
		return "Wind-Up";
	}
}
