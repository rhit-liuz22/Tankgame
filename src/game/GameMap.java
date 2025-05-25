package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameMap {
    private int width, height;
    private List<Wall> walls;
    private List<Vector2> spawnPoints;
    
    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = new ArrayList<>();
        this.spawnPoints = new ArrayList<>();
    }
    
    public void generate() {
        walls.clear();
        spawnPoints.clear();

        // Border walls
        walls.add(new Wall(0, 0, width, 25)); // Top
        walls.add(new Wall(0, height-25, width, 25)); // Bottom
        walls.add(new Wall(0, 0, 25, height)); // Left
        walls.add(new Wall(width-25, 0, 25, height)); // Right

        // Define protected spawn areas
        int safeRadius = 100;
        List<Vector2> protectedAreas = Arrays.asList(
            new Vector2(60, 60),
            new Vector2(width-60, 60),
            new Vector2(60, height-60),
            new Vector2(width-60, height-60)
        );
        spawnPoints.addAll(protectedAreas);

        // Maze parameters
        int cellSize = 120;
        int wallThickness = 20;
        int rows = (height-50)/cellSize;
        int cols = (width-50)/cellSize;
        float wallSpawnChance = 0.4f;
        Random random = new Random();

        // Generate vertical walls
        for (int row = 0; row <= rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row > 0 && col == cols-1) continue; // Skip last column except first row
                
                Vector2 wallPos = new Vector2(
                    25 + col*cellSize + cellSize/2 - wallThickness/2,
                    25 + row*cellSize
                );

                if (isPositionSafe(wallPos, protectedAreas, safeRadius) && 
                    random.nextFloat() < wallSpawnChance) {
                    walls.add(new Wall((int)wallPos.x, (int)wallPos.y, wallThickness, cellSize));
                }
            }
        }

        // Generate horizontal walls
        for (int row = 0; row < rows; row++) {
            int skipCol = random.nextInt(cols);
            
            for (int col = 0; col <= cols; col++) {
                if (col == skipCol) continue;
                
                Vector2 wallPos = new Vector2(
                    25 + col*cellSize,
                    25 + row*cellSize + cellSize/2 - wallThickness/2
                );

                if (isPositionSafe(wallPos, protectedAreas, safeRadius) && 
                    random.nextFloat() < wallSpawnChance) {
                    walls.add(new Wall((int)wallPos.x, (int)wallPos.y, cellSize, wallThickness));
                }
            }
        }
    }

    private boolean isPositionSafe(Vector2 position, List<Vector2> protectedAreas, int radius) {
        for (Vector2 safeZone : protectedAreas) {
            if (position.distanceTo(safeZone) < radius) {
                return false;
            }
        }
        return true;
    }
    
    public void render(Graphics2D g2d) {
        g2d.setColor(new Color(100, 100, 100));
        for (Wall wall : walls) {
            g2d.fillRect(wall.x, wall.y, wall.width, wall.height);
        }
    }
    
    public Vector2 getRandomSpawnPoint() {
        return spawnPoints.get(new Random().nextInt(spawnPoints.size()));
    }
    
    public List<Wall> getWalls() {
        return walls;
    }
}