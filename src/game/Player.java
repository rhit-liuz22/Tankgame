package game;

import java.awt.Color;

public class Player {
    private int id;
    private Tank tank;
    private int score;
    private Controls controls;
    
    public Player(int id, Color color, Controls controls) {
        this.id = id;
        this.tank = new Tank(color);
        this.controls = controls;
        this.score = 0;
    }
    
    public void update(float deltaTime) {
        tank.update(deltaTime);
    }
    
    public void incrementScore() {
        score++;
    }
    
    // Getters
    public int getId() { return id; }
    public int getScore() { return score; }
    public Tank getTank() { return tank; }
    public Controls getControls() { return controls; }
}