package GUI;

import Map.Tile;
import Map.TileStatus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LabyrinthGUI extends JFrame {

    public static final String TITLE = "MazeForm";
    public static final int WIDTH = 400;
    public static final int HEIGHT = 500;

    public static final int TILE_SIZE = 20;

    public static final int MAZE_SIZE = WIDTH / TILE_SIZE;

    private JPanel panel;
    private DrawablePanel drawPanel;
    private JButton[] buttons;
    private Tile[][] tiles;

    public LabyrinthGUI(ActionStatus action) {
        tiles = new Tile[MAZE_SIZE][MAZE_SIZE];

        this.panel = new JPanel();
        this.drawPanel = new DrawablePanel(400, 400, tiles);
        this.buttons = new JButton[2];

        this.buttons[0] = new JButton("Zapisz");
        this.buttons[1] = new JButton("Rozwiąż");

        this.panel.setSize(400, 100);

        this.panel.add(buttons[0]);
        this.panel.add(buttons[1]);

        this.add(drawPanel, BorderLayout.CENTER);
        this.add(panel, BorderLayout.PAGE_END);

        this.setTitle(TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMaze(400, 400);

    }

    public void createMaze(int width, int height) {
        for (int i = 0; i < MAZE_SIZE; i++) {
            for (int j = 0; j < MAZE_SIZE; j++) {
                tiles[i][j] = new Tile(new Point(i, j), TileStatus.PATH);
            }
        }
    }

    public void drawMaze(Graphics g, int width, int height) {

    }

    public void ownMaze() {
        int a = 2; /* test upa na gita */

    }

    public void loadMaze() {
        //wczytanie
    }

    public void genetareMaze() {
        //algorytm
    }
}
