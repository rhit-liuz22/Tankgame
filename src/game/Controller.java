package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

public class Controller implements KeyListener {
    private Game game;
    private Set<Integer> pressedKeys = new HashSet<>();
    
    public Controller(Game game) {
        this.game = game;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
        handleInput();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
        handleInput();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {} // Not used
    
    public void handleInput() {
        for (Player player : game.getPlayers()) {
            Controls controls = player.getControls();
            Tank tank = player.getTank();
            
            tank.setMovingForward(false);
            tank.setMovingBackward(false);
            tank.setRotatingLeft(false);
            tank.setRotatingRight(false);
            
            // Handle movement
            if (pressedKeys.contains(controls.up)) {
                tank.setMovingForward(true);
            }
            if (pressedKeys.contains(controls.down)) {
                tank.setMovingBackward(true);
            }
            
            // Handle rotation
            if (pressedKeys.contains(controls.left)) {
                tank.setRotatingLeft(true);
            }
            if (pressedKeys.contains(controls.right)) {
                tank.setRotatingRight(true);
            }
            
            if (pressedKeys.contains(controls.shoot)) {
                tank.shoot();
            }
        }
    }
    
    public void addToPanel(JPanel panel) {
        panel.addKeyListener(this);
    }
}