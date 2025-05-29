package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

public class Controller implements KeyListener {
    private Game game;
    private Set<Integer> pressedKeys = new HashSet<>();
    private int selection;
    
    public Controller(Game game) {
        this.game = game;
        this.selection = -1;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
    
    @Override
    public void keyTyped(KeyEvent e) {} // Not used
    
    public void update(float deltaTime) {
    	
    	handleInput(deltaTime);
    }
    
    public void handleInput(float deltaTime) {
        for (PlayerSkeleton player : game.getPlayers()) {
            Controls controls = player.getControls();
            TankSkeleton tank = player.getTank();
            
            // Handle movement
            if (pressedKeys.contains(controls.up)) {
                tank.moveForward(deltaTime);
            }
            if (pressedKeys.contains(controls.down)) {
                tank.moveBackward(deltaTime);
            }
            
            // Handle rotation
            if (pressedKeys.contains(controls.left)) {
                tank.rotateCCW(deltaTime);
            }
            if (pressedKeys.contains(controls.right)) {
                tank.rotateCW(deltaTime);
            }
            
            if (pressedKeys.contains(controls.shoot)) {
                player.getTank().shootBullet();
            }

            if (pressedKeys.contains(controls.dash)) {
                player.getTank().dash();
            }
        }
    }
    
    public void runSelection() {
    	
    	if (pressedKeys.contains(KeyEvent.VK_1)) {
    		this.selection = 1;
    	}
    	if (pressedKeys.contains(KeyEvent.VK_2)) {
    		
    		this.selection = 2;
    	}
    	if (pressedKeys.contains(KeyEvent.VK_3)) {
    		
    		this.selection = 3;
    	}
    	if (pressedKeys.contains(KeyEvent.VK_4)) {
    		
    		this.selection = 4;
    	}
    	if (pressedKeys.contains(KeyEvent.VK_5)) {
    		
    		this.selection = 5;
    	}
    }
    
    public int getSelection() {
    	
    	return this.selection;
    }
    
    public void addToPanel(JPanel panel) {
        panel.addKeyListener(this);
    }
}