package game;

public interface Ability {
	
	Ability copy();

	void modifyTank(TankSkeleton tank);
	
	void modifyBullet(BulletSkeleton bullet);
	
	void collisionEffect(BulletSkeleton bullet, TankSkeleton hittank);
	
	void toggleTrue();
	
	void toggleFalse();
	
	void resetCD();
	
	String getAbilityDescription();	
}
