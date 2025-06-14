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
		
		int stack = 0;
		for (Ability ability : tank.getAbilities()) {
			
			if (ability instanceof Goliath) {
				
				stack++;
			}
		}
		
		
		if (cd <= 0) {
			
			tank.addHealth(tank.getMaxHealth() * .015f * stack);
			this.cd = this.resetCD;
		}
		else {
			
			this.cd -= 1;
		}
		
		if (this.toggle) {
			
			return;
		}
		
		//increase health
		
		tank.setMaxHealth(tank.getMaxHealth() * 1.75f);
		tank.setHealth(tank.getMaxHealth());
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
		
		return "Goliath - N/A, increased max health and health regeneration";
	}
}
