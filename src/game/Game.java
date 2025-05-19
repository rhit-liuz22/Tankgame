package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class Game {
    private List<Player> players;
    private GameMap map;
    private Viewer viewer;
    private Timer gameTimer;
    private boolean gameRunning;
    
    public Game() {
        players = new ArrayList<>();
        map = new GameMap(704, 640);
        initializePlayers();
    }
    
    private void initializePlayers() {
        players.add(new Player(1, new Color(255, 50, 50), 
            new Controls(87, 65, 83, 68, 32))); // WASD + Space
        players.add(new Player(2, new Color(50, 50, 255),
            new Controls(38, 37, 40, 39, 10))); // Arrows + Enter
    }
    
    public void startGame() {
        gameRunning = true;
        map.generate();
        spawnPlayers();
        gameTimer = new Timer(16, new ActionListener() {
            float deltaTime = 16/1000f;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameRunning) return;
                update(deltaTime);
                viewer.render();
            }
        });
        gameTimer.start();
    }
    
    private void spawnPlayers() {
        for (Player player : players) {
            Vector2 spawn = map.getRandomSpawnPoint();
            player.getTank().setPosition(spawn.copy());
        }
    }
    
    private void update(float deltaTime) {
        for (Player player : players) {
            player.update(deltaTime);
            
            for (Wall wall : map.getWalls()) {
                if (Collision.tankWall(player.getTank(), wall)) {
                    player.getTank().resolveWallCollision(wall);
                }
            }
        }
        checkCollisions();        
        if (checkGameOver()) {
            endGame();
        }
    }
    
    private void checkCollisions() {
    	
    	for (Player player : players) {
    		
    		bulletCollisions(player.getTank());
    	}
//        for (Player player : players) {
//            List<Bullet> bullets = player.getTank().getBullets();
//            for (int i = bullets.size() - 1; i >= 0; i--) {
//                Bullet bullet = bullets.get(i);
//                
//                for (Wall wall : map.getWalls()) {
//                    if (Collision.bulletWall(bullet, wall)) {
//                        Vector2 normal = Collision.getWallNormal(bullet, wall);
//                        bullet.bounce(normal);
//                        break;
//                    }
//                }
//                
//                for (Player otherPlayer : players) {
//                    Tank otherTank = otherPlayer.getTank();
//                    
//                    boolean shouldHit = (bullet.getOwner() != otherTank) || 
//                                      (bullet.canHitOwner());
//                    
//                    if (shouldHit && Collision.bulletTank(bullet, otherTank)) {
//                        bullets.remove(i);
//                        otherTank.takeDamage(25);
//                        if (bullet.getOwner() != otherTank) {
//                            player.incrementScore();
//                        }
//                        break;
//                    }
//                }
//            }
//        }
    }
    
    private void bulletCollisions(Tank tank) {
		for (Bullet bullet : tank.getBullets()) {
			
			int bulletx = (int) bullet.position.x;
			int bullety = (int) bullet.position.y;
			int bulletradius = (int) bullet.radius;
			
			boolean hitwall = false;
			
			for (Wall wall : map.getWalls()) {
				
				if (hitwall) {
					System.out.print("break");
					break;
				}
				else {
					
					boolean collideX = Collision.isCollidedX(bulletx - bulletradius, bulletradius * 2,
							wall.x, wall.width);
					boolean collideY = Collision.isCollidedY(bullety - bulletradius, bulletradius * 2,
							wall.y, wall.height);
					
					if (collideX && collideY) {
						
						System.out.println("hitwall");
						hitwall = true;
						
						if (bulletx + bulletradius < wall.x || bulletx > wall.x + wall.width) {
							
							bullet.bounceX();
						}
						if (bullety + bulletradius < wall.y || bullety > wall.y + wall.height) {
							
							bullet.bounceY();
						}
					}
				}
			}
		}
    }
    
    private boolean checkGameOver() {
        for (Player player : players) {
            if (player.getTank().getHealth() <= 0) {
                return true;
            }
        }
        return false;
    }
    
    private void endGame() {
        gameRunning = false;
        gameTimer.stop();
        viewer.showGameOver();
    }
    
    // Getters and setters
    public List<Player> getPlayers() { return players; }
    public GameMap getMap() { return map; }
    public void setViewer(Viewer viewer) { this.viewer = viewer; }
    public void setController(Controller controller) { }
}