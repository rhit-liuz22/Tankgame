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
		
		if (cd <= 0) {
			
			tank.addHealth(tank.getMaxHealth() * -.025f);
			this.cd = this.resetCD;
		}
		else {
			
			this.cd -= 1;
		}
		
		if (this.toggle) {
			
			return;
		}
		
		// increase reload speed, bullet damage and speed, movement speed, dash distance, dash reload speed, maxhp
		
		tank.setReloadCD(tank.getReloadCD() * .75f);
		tank.setBulletDamage(tank.getBulletDamage() * 1.5f);
		tank.setBulletSpeed(tank.getBulletSpeed() * 1.5f);
		tank.setVelocity(tank.getVelocity() * 1.5f, tank.getBackVelocity() * 1.5f);
		tank.setMaxHealth(tank.getMaxHealth() * 1.5f);
		tank.setDashSpeed(tank.getDashSpeed() * 1.25f);
		tank.setDashCD(tank.getDashCD() * .75f);
		tank.setHealth(tank.getMaxHealth());
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
	
	public void resetCD() {
		
		this.cd = resetCD;
	}
	
	public String getAbilityDescription() {
		
		return "Overclock - lose health over time, decrease reload time and increase bullet damage"
				+ " and faster tank speed and faster bullet damage and increased max health";
	}
}
