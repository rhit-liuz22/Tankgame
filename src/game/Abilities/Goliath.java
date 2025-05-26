package game.Abilities;
import game.Ability;
import game.BulletSkeleton;
import game.TankSkeleton;

public class Goliath implements Ability{
	
	boolean toggle;
	int cd = 0;
	int resetCD = 60;// every 600 ticks will regenerate 2% of hp
	
	public Goliath() {
		
		this.toggle = false;
	}
	
	public Goliath copy() {
		
		return new Goliath();
	}

	public void modifyTank(TankSkeleton tank){
		
		//regenerate health
		
		if (cd <= 0) {
			
			tank.addHealth(tank.getMaxHealth() * .02f);
			this.cd = this.resetCD;
		}
		else {
			
			this.cd -= 1;
		}
		
		if (this.toggle) {
			
			return;
		}
		
		//increase health
		
		tank.setMaxHealth(tank.getMaxHealth() * 1.5f);
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
	
	public String getAbilityDescription() {
		
		return "Goliath - N/A, increased max health and health regeneration";
	}
}
