package game;

import javax.swing.*;
import java.awt.*;

public class Viewer extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Game game;
	private JLayeredPane mainPanel;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;
    
    public Viewer(Game game) {
        this.game = game;
        this.mainPanel = new JLayeredPane();
        initializeUI();
    }
    
    public JLayeredPane getMainPanel() {
    	
    	return this.mainPanel;
    }
    
    public GamePanel getGamePanel() {
        return this.gamePanel;
    }
    
    private void initializeUI() {
        setTitle("Tank Trouble x ROUNDS");
        setSize(704, 704);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        setLayout(new BorderLayout());
        
        mainPanel.setPreferredSize(new Dimension(704,704));
        mainPanel.setLayout(null);
        
        gamePanel = new GamePanel(game);
        gamePanel.setBounds(0, 60, 704, 704);
        mainPanel.add(gamePanel,Integer.valueOf(1));
        
        scorePanel = new ScorePanel(game);
        scorePanel.setBounds(0, 0, 704, 64);
        mainPanel.add(scorePanel,Integer.valueOf(2));
        
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        
        setContentPane(mainPanel);
        setVisible(true);
    }
    
    public void render() {
    	
        gamePanel.repaint();
        scorePanel.updateScores();
        mainPanel.repaint();
        
    }
    
    public void showGameOver(PlayerSkeleton winner) {
        String endGame = "player " + winner.getID() + " has won the game!";
        
        JOptionPane.showMessageDialog(this, endGame, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static class GamePanel extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Game game;
        
        public GamePanel(Game game) {
            this.game = game;
            setPreferredSize(new Dimension(704, 640));
            setBackground(new Color(20, 20, 20));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g;
            
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                RenderingHints.VALUE_ANTIALIAS_ON);
            
            game.getMap().render(g2d);
            
            for (PlayerSkeleton player : game.getPlayers()) {
                player.getTank().render(this, g2d);
                
                for (BulletSkeleton bullet : player.getTank().getBulletList()) {
                    bullet.render(this, g2d);
                }
            }
            
            drawHealthBars(g2d);
        }
        
        private void drawHealthBars(Graphics2D g2d) {
            int yOffset = 10;
            for (PlayerSkeleton player : game.getPlayers()) {
                TankSkeleton tank = player.getTank();
                int width = 100;
                int height = 10;
                int x = 20;
                int y = yOffset;

                g2d.setColor(Color.GRAY);
                g2d.fillRect(x, y, width, height);

                float healthPercent = tank.getHealth() / tank.getMaxHealth();
                g2d.setColor(player.getColor());
                g2d.fillRect(x, y, (int)(width * healthPercent), height);

                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, width, height);
                
                yOffset += 20;
            }
        }
    }
    
    private static class ScorePanel extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Game game;
        private JLabel[] playerScoreLabels;
        
        public ScorePanel(Game game) {
            this.game = game;
            setPreferredSize(new Dimension(704, 64));
            setBackground(new Color(40, 40, 40));
            setLayout(new GridLayout(1, game.getPlayers().size()));
            
            playerScoreLabels = new JLabel[game.getPlayers().size()];
            for (int i = 0; i < playerScoreLabels.length; i++) {
                PlayerSkeleton player = game.getPlayers().get(i);
                playerScoreLabels[i] = new JLabel("P" + player.getID() + ": 0", SwingConstants.CENTER);
                playerScoreLabels[i].setForeground(player.getColor());
                playerScoreLabels[i].setFont(new Font("Arial", Font.BOLD, 24));
                add(playerScoreLabels[i]);
            }
        }
        
        public void updateScores() {
            for (int i = 0; i < playerScoreLabels.length; i++) {
                PlayerSkeleton player = game.getPlayers().get(i);
                playerScoreLabels[i].setText("P" + player.getID() + ": " + player.getScore());
            }
        }
    }
}