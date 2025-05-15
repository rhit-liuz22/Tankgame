package game;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            Viewer viewer = new Viewer(game);
            Controller controller = new Controller(game);
            
            game.setViewer(viewer);
            game.setController(controller);

            viewer.setVisible(true); 

            controller.addToPanel(viewer.getGamePanel());
            
            game.startGame();
        });
    }
}