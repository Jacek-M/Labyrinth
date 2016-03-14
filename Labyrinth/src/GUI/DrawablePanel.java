package GUI;

import static GUI.LabyrinthGUI.MAZE_SIZE;
import static GUI.LabyrinthGUI.TILE_SIZE;
import Map.TileStatus;
import Map.Tile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class DrawablePanel extends JPanel {
    
    private int width;
    private int height;
    
    private Tile[][] tiles;
    
    public DrawablePanel(int width, int height, Tile[][] tiles) {
        super();
        this.setSize(width, height);
        this.width = width;
        this.height = height;
        this.tiles = tiles;
    }
    
    @Override
    public int getWidth() {
        return super.getWidth();
    }
    
    @Override
    public int getHeight() {
        return super.getHeight(); 
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, width, height);

        for (int i = 0; i < MAZE_SIZE; i++) {
            for (int j = 0; j < MAZE_SIZE; j++) {
                if (tiles[i][j].getTileStatus() == TileStatus.PATH) {
                    g2d.setColor(Color.WHITE);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                g2d.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
            }
        }
    }
}