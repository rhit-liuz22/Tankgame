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
            
            tank.setMoving(false);
            
            Vector2 direction = new Vector2(0, 0);
            if (pressedKeys.contains(controls.up)) direction.y -= 1;
            if (pressedKeys.contains(controls.down)) direction.y += 1;
            if (pressedKeys.contains(controls.left)) direction.x -= 1;
            if (pressedKeys.contains(controls.right)) direction.x += 1;
            
            if (direction.length() > 0) {
                tank.setDirection(direction.normalize());
                tank.setMoving(true);
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