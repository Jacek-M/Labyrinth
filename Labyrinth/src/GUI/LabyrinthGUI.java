package GUI;

import Map.Maze;
import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class LabyrinthGUI extends JFrame {

    public static final String TITLE = "MazeForm";
    public static final int WIDTH = 400;
    public static final int HEIGHT = 500;

    public static final int TILE_SIZE = 5;
    public static final int MAZE_SIZE = (WIDTH / TILE_SIZE) - 1;

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

            if (dir != null && !dir.isEmpty() && dir.length() > 3) {
                if (maze.loadMazeFromFile(dir) == false) {
                    maze = new Maze(MAZE_SIZE);
                    this.drawPanel.setTiles(maze.getTiles());
                }
            }
        }

        this.panel = new JPanel();
        this.drawPanel = new DrawablePanel(WIDTH, WIDTH, maze.getTiles());
        this.buttons = new JButton[3];
        this.mouse = new MouseHandler(maze.getTiles());

        this.buttons[0] = new JButton("Zapisz");
        this.buttons[1] = new JButton("Rozwiąż");
        this.buttons[2] = new JButton("Wyczyść");

        this.panel.setSize(400, 100);

        this.panel.add(buttons[0]);
        this.panel.add(buttons[1]);
        this.panel.add(buttons[2]);

        this.buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(LabyrinthGUI.this, "Zapisz", FileDialog.SAVE);
                fd.setVisible(true);
                String dir = fd.getDirectory() + fd.getFile();
                if (fd.getFile() != null && !fd.getFile().isEmpty() && fd.getFile().length() > 3) {
                    maze.saveMazeToFile(dir);
                } else {
                    JOptionPane.showMessageDialog(null, "Nie wybrano pliku");
                }
            }
        });

        this.buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maze = new Maze(MAZE_SIZE);

                Thread th = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        maze.generateMaze(MAZE_SIZE);
                        drawPanel.setTiles(maze.getTiles());
                        mouse.setTiles(maze.getTiles());
                    }
                });
                
                th.start();
            }
        });

        this.buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maze = new Maze(MAZE_SIZE);
                drawPanel.setTiles(maze.getTiles());
                mouse.setTiles(maze.getTiles());
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
        this.drawPanel.setTiles(maze.getTiles());
        this.drawPanel.repaint();
    }
}
