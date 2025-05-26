package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class WindUp implements Ability {

	public void modifyTank(TankSkeleton tank){
		
		//reduce movespeed
		tank.setVelocity(tank.getVelocity() * .8f);
	}
	
	public void modifyBullet(BulletSkeleton tank) {
		
		//reduce reload
		//increase damage and bullet speed
	}
}
