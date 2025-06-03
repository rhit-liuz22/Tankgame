package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class Overclock implements Ability{
	
	boolean toggle;
	int cd = 0;
	int resetCD = 60; // every 600 ticks will lose 2.5% of hp
	
	public Overclock() {
		
		this.toggle = false;
	}
	
	public Overclock copy() {
		
		return new Overclock();
	}

	public void modifyTank(TankSkeleton tank){
		
		// reduce health over time
		
		int stack = 0;
		for (Ability ability : tank.getAbilities()) {
			
			if (ability instanceof Overclock) {
				
				stack++;
			}
		}
		
		if (cd <= 0) {

			tank.addHealth(tank.getMaxHealth() * -.025f * stack);
			this.cd = this.resetCD;			
		}
		else {
			
			this.cd -= 1;
		}
		
		if (this.toggle) {
			
			return;
		}
		
		// increase reload speed, bullet damage, movement speed, dash distance, dash reload speed
		
		tank.setReloadCD(tank.getReloadCD() * .6f);
		tank.setBulletDamage(tank.getBulletDamage() * 1.4f);
		tank.setVelocity(tank.getVelocity() * 1.35f, tank.getBackVelocity() * 1.35f);
		tank.setDashSpeed(tank.getDashSpeed() * 1.25f);
		tank.setDashCD(tank.getDashCD() * .8f);
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
		
		this.cd = resetCD;
	}
	
	public String getAbilityDescription() {
		
		return "Overclock - lose health over time, decrease reload time and increase bullet damage"
				+ " and faster tank speed";
	}
}
