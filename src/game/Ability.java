package game;

public interface Ability {
	
	Ability copy();

	void modifyTank(TankSkeleton tank);
	
	void modifyBullet(BulletSkeleton bullet);
	
	void toggleTrue();
	
	void toggleFalse();
	
	String getAbilityDescription();	
}
