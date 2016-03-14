package GUI;

import Map.Tile;
import Map.TileStatus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LabyrinthGUI extends JFrame implements MouseListener, MouseMotionListener {

    public static final String TITLE = "MazeForm";
    public static final int WIDTH = 400;
    public static final int HEIGHT = 525;

    public static final int TILE_SIZE = 20;

    public static final int MAZE_SIZE = WIDTH / TILE_SIZE;

    private JPanel panel;
    private JPanel drawPanel;
    private JButton[] buttons;
    private Tile[][] tiles;

    public LabyrinthGUI(ActionStatus action) {
        tiles = new Tile[MAZE_SIZE][MAZE_SIZE];

        this.panel = new JPanel();
        this.drawPanel = new JPanel();
        this.buttons = new JButton[2];

        this.buttons[0] = new JButton("Zapisz");
        this.buttons[1] = new JButton("Rozwiąż");

        this.drawPanel.setSize(400, 425);
        this.panel.setSize(400, 100);

        this.panel.add(buttons[0]);
        this.panel.add(buttons[1]);

        this.add(drawPanel, BorderLayout.CENTER);
        this.add(panel, BorderLayout.PAGE_END);

        this.setTitle(TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMaze(400, 425);

        addMouseListener(this);
    }

    public void createMaze(int width, int height) {
        for (int i = 0; i < MAZE_SIZE; i++) {
            for (int j = 0; j < MAZE_SIZE; j++) {
                tiles[i][j] = new Tile(new Point(i, j), TileStatus.PATH);
            }
        }
    }

    public void drawMaze(Graphics g, int width, int height) {

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);

        for (int i = 0; i < MAZE_SIZE; i++) {
            for (int j = 0; j < MAZE_SIZE; j++) {
                if (tiles[i][j].getTileStatus() == TileStatus.PATH) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(i * TILE_SIZE, j * TILE_SIZE + 25, TILE_SIZE - 1, TILE_SIZE - 1);
            }
        }

    }

    @Override
    public void paint(Graphics g) {
        drawMaze(g, 400, 425);
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (x > 0 && x < 400 && y > 25 && y < 425) {
            tiles[x / TILE_SIZE][(y - 25) / TILE_SIZE].setTileStatus(TileStatus.WALL);
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       int x = e.getX();
        int y = e.getY();
        if (x > 0 && x < 400 && y > 25 && y < 425) {
            tiles[x / TILE_SIZE][(y - 25) / TILE_SIZE].setTileStatus(TileStatus.WALL);
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (x > 0 && x < 400 && y > 25 && y < 425) {
            tiles[x / TILE_SIZE][(y - 25) / TILE_SIZE].setTileStatus(TileStatus.WALL);
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (x > 0 && x < 400 && y > 25 && y < 425) {
            tiles[x / TILE_SIZE][(y - 25) / TILE_SIZE].setTileStatus(TileStatus.WALL);
            repaint();
        }
    }

}
