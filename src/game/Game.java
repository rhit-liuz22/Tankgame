package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import game.Abilities.*;

public class Game {
    
    private ArrayList<PlayerSkeleton> players;
    
    private GameMap map;
    private Viewer viewer;
    private Timer gameTimer;
    private boolean gameRunning;
    private Controller controller;
    
    public Game() {
        players = new ArrayList<>();
        map = new GameMap(704, 640);
        initializePlayers();
    }
    
    private void initializePlayers() {
        players.add(new PlayerSkeleton(1, new Color(255, 50, 50), 
            new Controls(87, 65, 83, 68, 32))); // WASD + Space
        players.add(new PlayerSkeleton(2, new Color(50, 50, 255),
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
    	
    	players.get(0).spawnTank(50, 50, 0);
    	players.get(1).spawnTank(500, 500, 180);
    }
    
    private void update(float deltaTime) {

        if (checkGameOver()) {
            endGame();
        }
    	
        controller.update(deltaTime);
        for (PlayerSkeleton player : players) {
        	
            player.getTank().runCalculations(deltaTime);
        }
        
        handleCollisions(); 
        
        for (PlayerSkeleton player : players) {
        	
        	player.update();
        }
    }
    
    // deal with collisions using new collision
    public void handleCollisions() {
    	
    	ArrayList<BulletSkeleton> allBullets = new ArrayList<>();
    	for (PlayerSkeleton player : players) {
    		
    		// bullet-wall
    		for (BulletSkeleton bullet : player.getTank().getBulletList()) {
    			
    			allBullets.add(bullet);
    			
    			for (Wall wall : map.getWalls()) {
    				
    				boolean withinX = Collision.isCollidedX(bullet, wall);
    				boolean withinY = Collision.isCollidedY(bullet, wall);
    				if (withinX && withinY) {
    					
    					if (bullet.getX() + bullet.getWidth() < wall.getX() || bullet.getX() > wall.getX() + wall.getWidth()) {
    						
    						bullet.bounce(wall.getTheta());
    					}
    					else {
    						
    						bullet.bounce(wall.getTheta() + 90);
    					}
    				}
    			}
    		} // bullet-wall
    		
    		//tank-wall
    		for (Wall wall : map.getWalls()) {
    			
    			TankSkeleton tank = player.getTank();
    			boolean withinX = Collision.isCollidedX(tank, wall);
				boolean withinY = Collision.isCollidedY(tank, wall);
				
				if (withinX && withinY) {
					
					if (tank.getX() + tank.getWidth() < wall.getX() || tank.getX() > wall.getX() + wall.getWidth()) {
						
						tank.setdx(0);
					}
					else {
						
						tank.setdy(0);
					}
				}
    		} // tank-wall#
    	}
    	
    	//tank-bullet
    	for (BulletSkeleton bullet : allBullets) {
    		
    		for (PlayerSkeleton player : players) {
    			
    			TankSkeleton tank = player.getTank();
    			boolean withinX = Collision.isCollidedX(tank, bullet);
				boolean withinY = Collision.isCollidedY(tank, bullet);
				
				if (withinX && withinY) {
					
					if (tank == bullet.getOwner()) {
						if (!bullet.getCanHitOwner()) {
							
							break;
						}
					}
					tank.addHealth(-bullet.getDamage());
					bullet.setExpired();
				}
    		}
    	}
    }
    
    private boolean checkGameOver() {
        for (PlayerSkeleton player : players) {
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
    public ArrayList<PlayerSkeleton> getPlayers() { return players; }
    public GameMap getMap() { return map; }
    public void setViewer(Viewer viewer) { this.viewer = viewer; }
    public void setController(Controller controller) { this.controller = controller;}
}