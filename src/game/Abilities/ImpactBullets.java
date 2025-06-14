package game.Abilities;

import java.util.ArrayList;

import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class ImpactBullets implements Ability {
	
	boolean toggle;
	ArrayList<BulletSkeleton> toexplode;
	
	public ImpactBullets() {
		
		this.toggle = false;
		this.toexplode = new ArrayList<>();
	}
	
	public ImpactBullets copy() {
		
		return new ImpactBullets();
	}


	public void modifyTank(TankSkeleton tank){
		
		// spawn bullets at bullet bounce location
		for (BulletSkeleton explode : toexplode) {
			
			for (int i = 0; i < 8; i++) {
				
				float rngangle = (float) Math.random() * 90 - 45;
				BulletSkeleton shrapnel = new BulletSkeleton(tank,
						explode.getX() + explode.getRadius(), explode.getY() + explode.getRadius(),
						(float) (i * 45) + rngangle, explode.getVelocity(), 0,
						false, 0, 2, explode.getDamage() * 0.15f, tank.getColor());
				shrapnel.setCanHitOwner(true);
				tank.getBulletList().add(shrapnel);
			}
		}
		toexplode.removeAll(toexplode);

		if (this.toggle) {
			
			return;
		}
		
		// decrease bounce count
		tank.setBulletBounces(tank.getBulletBounces() - 2);
	}
	
	public void modifyBullet(BulletSkeleton bullet) {

		// increased bullet damage and speed upon each bounce
		
		if (bullet.isBouncing()) {
			
			this.toexplode.add(bullet);
		}
		
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
		
		return "Impact Bullets - lose 2 bullet bounces, spawns shrapnel on each bounce";
	}
}
