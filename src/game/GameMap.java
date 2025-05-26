package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMap {
    private int width, height;
    private List<Wall> walls;
    private List<Vector2> spawnPoints;
    private ArrayList<ArrayList<Wall>> maps;
    
    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = new ArrayList<>();
        this.spawnPoints = new ArrayList<>();
        this.maps = new ArrayList<>();
        
        // Map 1
        ArrayList<Wall> map1 = new ArrayList<>();      
        map1.add(new Wall(332, 100, 32, 400)); // Central pillar
        map1.add(new Wall(100, 100, 150, 32)); // Left top
        map1.add(new Wall(450, 100, 150, 32)); // Right top
        map1.add(new Wall(100, 400, 150, 32)); // Left bottom
        map1.add(new Wall(450, 400, 150, 32)); // Right bottom
        map1.add(new Wall(200, 250, 32, 100)); // Left middle
        map1.add(new Wall(500, 250, 32, 100)); // Right middle
        this.maps.add(map1);
        
     // Map 2
        ArrayList<Wall> map2 = new ArrayList<>();
        map2.add(new Wall(200, 150, 32, 340)); // Left wall
        map2.add(new Wall(470, 150, 32, 340)); // Right wall
        map2.add(new Wall(250, 150, 200, 32)); // Top bridge
        map2.add(new Wall(250, 450, 200, 32)); // Bottom bridge
        map2.add(new Wall(340, 250, 32, 140)); // Central divider
        this.maps.add(map2);

        // Map 3
        ArrayList<Wall> map3 = new ArrayList<>();
        map3.add(new Wall(150, 150, 400, 32)); // Top platform
        map3.add(new Wall(150, 450, 400, 32)); // Bottom platform
        map3.add(new Wall(150, 182, 32, 100)); // Left pillar 1
        map3.add(new Wall(150, 350, 32, 100)); // Left pillar 2
        map3.add(new Wall(520, 182, 32, 100)); // Right pillar 1
        map3.add(new Wall(520, 350, 32, 100)); // Right pillar 2
        this.maps.add(map3);

        // Map 4
        ArrayList<Wall> map4 = new ArrayList<>();
        map4.add(new Wall(336, 100, 32, 440)); // Central vertical
        map4.add(new Wall(200, 300, 136, 32)); // Left horizontal
        map4.add(new Wall(368, 300, 136, 32)); // Right horizontal
        map4.add(new Wall(250, 150, 32, 100)); // Left top
        map4.add(new Wall(420, 150, 32, 100)); // Right top
        map4.add(new Wall(250, 400, 32, 100)); // Left bottom
        map4.add(new Wall(420, 400, 32, 100)); // Right bottom
        this.maps.add(map4);

        // Map 5
        ArrayList<Wall> map5 = new ArrayList<>();
        map5.add(new Wall(100, 100, 200, 200)); // Top-left square
        map5.add(new Wall(400, 100, 200, 200)); // Top-right square
        map5.add(new Wall(100, 340, 200, 200)); // Bottom-left square
        map5.add(new Wall(400, 340, 200, 200)); // Bottom-right square
        map5.add(new Wall(300, 250, 100, 32)); // Central connector
        this.maps.add(map5);

        // Map 6
        ArrayList<Wall> map6 = new ArrayList<>();
        map6.add(new Wall(200, 200, 300, 32)); // Top platform
        map6.add(new Wall(200, 400, 300, 32)); // Bottom platform
        map6.add(new Wall(200, 232, 32, 168)); // Left wall
        map6.add(new Wall(470, 232, 32, 168)); // Right wall
        map6.add(new Wall(300, 300, 32, 40)); // Left obstacle
        map6.add(new Wall(370, 300, 32, 40)); // Right obstacle
        this.maps.add(map6);
    }
    
    public void generate() {
        walls.clear();
        spawnPoints.clear();

        // Border walls
        walls.add(new Wall(0, 0, width, 32)); // Top
        walls.add(new Wall(0, height-64, width, 32)); // Bottom
        walls.add(new Wall(0, 0, 32, height)); // Left
        walls.add(new Wall(width-64, 0, 32, height)); // Right
        
        int index = (int) (Math.random() * walls.size());
        
        walls.addAll(this.maps.get(index));
    }
    
    public void render(Graphics2D g2d) {
        g2d.setColor(new Color(100, 100, 100));
        for (Wall wall : walls) {
            g2d.fillRect((int) wall.getX(), (int) wall.getY(), wall.getWidth(), wall.getHeight());
        }
    }
    
    public Vector2 getRandomSpawnPoint() {
        return spawnPoints.get(new Random().nextInt(spawnPoints.size()));
    }
    
    public List<Wall> getWalls() {
        return walls;
    }
}