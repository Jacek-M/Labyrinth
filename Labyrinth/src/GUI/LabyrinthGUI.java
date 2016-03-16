package GUI;

import Map.Maze;
import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class LabyrinthGUI extends JFrame {

    public static final String TITLE = "MazeForm";
    public static final int WIDTH = 400;
    public static final int HEIGHT = 500;

    public static final int TILE_SIZE = 5;
    public static final int MAZE_SIZE = WIDTH / TILE_SIZE;

    private JPanel panel;
    private DrawablePanel drawPanel;
    private JButton[] buttons;
    private Maze maze;
    private MouseHandler mouse;

    public LabyrinthGUI(ActionStatus action) {
        if (action.equals(ActionStatus.DRAW_MAZE)) {
            maze = new Maze(MAZE_SIZE);

        } else if (action.equals(ActionStatus.GENERATE_MAZE)) {
            maze = new Maze(MAZE_SIZE);

        } else if (action.equals(ActionStatus.LOAD_MAZE)) {
            FileDialog fd = new FileDialog(this, "Wczytaj", FileDialog.LOAD);
            fd.setVisible(true);
            String dir = fd.getDirectory() + fd.getFile();
            maze = new Maze();
            maze.loadMazeFromFile(dir);
        }

        this.panel = new JPanel();
        this.drawPanel = new DrawablePanel(WIDTH, WIDTH, maze.getTiles());
        this.buttons = new JButton[2];
        this.mouse = new MouseHandler(maze.getTiles());

        this.buttons[0] = new JButton("Zapisz");
        this.buttons[1] = new JButton("Rozwiąż");

        this.panel.setSize(400, 100);

        this.panel.add(buttons[0]);
        this.panel.add(buttons[1]);

        this.buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(LabyrinthGUI.this, "Zapisz", FileDialog.SAVE);
                fd.setVisible(true);
                String dir = fd.getDirectory() + fd.getFile();
                maze.saveMazeToFile(dir);
            }
        });

        this.add(drawPanel, BorderLayout.CENTER);
        this.add(panel, BorderLayout.PAGE_END);

        this.setTitle(TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.drawPanel.addMouseMotionListener(this.mouse);
        this.drawPanel.addMouseListener(this.mouse);

        ActionListener timerAction = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                refresh();
            }
        };
        Timer timer = new Timer(10, timerAction);
        timer.setRepeats(true);
        timer.start();

    }

    private void refresh() {
        this.drawPanel.repaint();
    }

    public void drawMaze(Graphics g, int width, int height) {

    }

    public void ownMaze() {
        int a = 2;
        /* test upa na gita */

    }

    public void loadMaze() {
        //wczytanie
    }

    public void genetareMaze() {
        //algorytm
    }
}
