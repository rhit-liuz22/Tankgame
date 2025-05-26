package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class WindUp implements Ability {

	public void modifyTank(TankSkeleton tank){
		
		//reduce movespeed
		tank.setVelocity(tank.getVelocity() * .8f);
		
		//reduce reload
		tank.setReloadCD((int) (tank.getReloadCD() * 1.2));
	}
	
	public void modifyBullet(BulletSkeleton bullet) {
		
		//increase damage and bullet speed
		bullet.addDamage((int) (bullet.getDamage() * 1.5));
		bullet.addVelocity(bullet.getVelocity() * .5f);
	}
}
