package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.Timer;

import game.Abilities.*;

public class Game {
    
    private ArrayList<PlayerSkeleton> players;
    private PlayerSkeleton loser;
    
    private GameMap map;
    private Viewer viewer;
    private Timer gameTimer;
    private boolean gameRunning;
    private Controller controller;
    private ArrayList<Ability> allAbilities;
    private boolean waitingForSelection = false;
    private ArrayList<Ability> abilitySelections;
    
    public Game() {
        players = new ArrayList<>();
        map = new GameMap(720, 640);
        initializePlayers();
        
        abilitySelections = new ArrayList<>();
        
        // all existing abilities
        allAbilities = new ArrayList<>();
        allAbilities.add(new WindUp());
        allAbilities.add(new BigBoyBullet());
        allAbilities.add(new Speedy());
        allAbilities.add(new Goliath());
        allAbilities.add(new Overclock());
        allAbilities.add(new Trickster());
        allAbilities.add(new ImpactBullets());
        allAbilities.add(new Grow());
        allAbilities.add(new Ninja());
    }
    
    private void initializePlayers() {
        players.add(new PlayerSkeleton(1, new Color(255, 50, 50), 
            new Controls(87, 65, 83, 68, 32, 69))); // WASD + Space + E
        players.add(new PlayerSkeleton(2, new Color(50, 50, 255),
            new Controls(38, 37, 40, 39, 10, 16))); // Arrows + Enter + Shift
    }
    
    public void startGame() {
        gameRunning = true;
        map.generate();
        spawnPlayers();
        gameTimer = new Timer(8, new ActionListener() {
            float deltaTime = 8/1000f;
            
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
    	
    	if (waitingForSelection) {
    		controller.runSelection();
    		if (controller.getSelection() != -1) {
    			
    			waitingForSelection = false;
    			resolveSelection(controller.getSelection());
    		}
    		return;
    	}
    	else {
    		
    		controller.resetSelection();
    	}

        if (checkRoundOver()) {
        	
        	for (PlayerSkeleton player : players) {
        		
        		if (player.getHalf() == 2) {
        			
        			nextRound();
        		}
        	}
        	resetRound(players.get(0).getTank(), players.get(1).getTank());
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
    
    private void nextRound() {
    	
    	this.map.generate();
    	
    	int numAbilities = 5;
    	
    	this.abilitySelections = createAbilitySelection(numAbilities);
    	
    	this.waitingForSelection = true;
	}
    
    private ArrayList<Ability> createAbilitySelection(int n) {
    	
    	ArrayList<Ability> toShow = new ArrayList<>();
    	for (int i = 0; i < n; i++) {
    		
    		int index = (int) (Math.random() * allAbilities.size());
    		while (toShow.contains(allAbilities.get(index))) {
    			
    			index = (int) (Math.random() * allAbilities.size());
    		}
    		toShow.add(allAbilities.get(index));
    	}
    	for (Ability ability : toShow) {
    		
    		JLabel power = new JLabel(ability.getAbilityDescription());
    		viewer.getMainPanel().add(power, Integer.valueOf(3));
    		System.out.println("#" + String.valueOf(toShow.indexOf(ability) + 1) + " : " + ability.getAbilityDescription());
    	}
    	
    	System.out.println("player " + this.loser.getID() + " choose a Power-Up (Numbers 1-" + n + ")");
    	
    	return toShow;
    }
    
    private void resolveSelection(int index) {
    	
    	TankSkeleton tank1 = players.get(0).getTank();
    	TankSkeleton tank2 = players.get(1).getTank();
    	
		System.out.println("You chose Power-Up #"+ index + " : " + abilitySelections.get(index - 1).getAbilityDescription());
		
		for (PlayerSkeleton player : players) {
            if (player.getHalf() != 2) {
                player.getTank().addAbility(abilitySelections.get(index - 1).copy());
            }
            
            player.resetHalf();
            player.getTank().getBulletList().removeAll(player.getTank().getBulletList());
        }
		
		resetRound(tank1, tank2);
    }
    
    private void resetRound(TankSkeleton tank1, TankSkeleton tank2) {
		
		players.get(0).spawnTank(50, 50, 0);
    	players.get(1).spawnTank(500, 500, 180);
    	
    	tank1.resetAllCooldowns();
    	tank2.resetAllCooldowns();
    	tank1.setHealth(tank1.getMaxHealth());
    	tank2.setHealth(tank2.getMaxHealth());
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
    					
    					if (bullet.getX() < wall.getX() || bullet.getX() > wall.getX() + wall.getWidth()) {
    						
    						bullet.bounce(wall.getTheta());
    					}
    					if (bullet.getY() < wall.getY() || bullet.getY() > wall.getY() + wall.getHeight()) {
    						
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
    
    private boolean checkRoundOver() {
        ArrayList<PlayerSkeleton> alivePlayers = new ArrayList<>();
        ArrayList<PlayerSkeleton> deadPlayers = new ArrayList<>();
        
        for (PlayerSkeleton player : players) {
        	
        	if (player.getTank().getHealth() > 0) {
        		
        		alivePlayers.add(player);
        	}
        	else {
        		
        		deadPlayers.add(player);
        	}
        }
        
        if (deadPlayers.size() > 0) {
        	
        	PlayerSkeleton loser = deadPlayers.get((int) (Math.random() * deadPlayers.size()));
        	this.loser = loser;
        	for (PlayerSkeleton player : players) {
        		
        		if (! (player == loser)) {
        			
        			player.incrementHalf();
        			System.out.println("player " + player.getID() + " has won the round!");
        			
        			if (player.getScore() >= 5) {
        				
        				endGame(player);
        				viewer.dispose();
        			}
        			return true;
        		}
        	}
        }
        return false;
    }
    
    private void endGame(PlayerSkeleton winner) {
        gameRunning = false;
        gameTimer.stop();
        viewer.showGameOver(winner);
    }
    
    // Getters and setters
    public ArrayList<PlayerSkeleton> getPlayers() { return players; }
    public GameMap getMap() { return map; }
    public void setViewer(Viewer viewer) { this.viewer = viewer; }
    public void setController(Controller controller) { this.controller = controller;}
}