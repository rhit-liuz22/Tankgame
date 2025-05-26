package game;

public interface Ability {

	void modifyTank(TankSkeleton tank);
	
	void modifyBullet(BulletSkeleton bullet);
	
	void toggleTrue();
	
	void toggleFalse();
}
