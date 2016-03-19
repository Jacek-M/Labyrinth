package GUI;

import Map.Maze;
import Misc.Tools;
import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.Timer;

public class LabyrinthGUI extends JFrame {

    public static final String TITLE = "MazeForm";
    public static final int WIDTH = 500;
    public static final int HEIGHT = 600;

    public static final int TILE_SIZE = 5;
    public static final int MAZE_SIZE = (WIDTH / TILE_SIZE) - 1;

    private JPanel panel;
    private DrawablePanel drawPanel;
    private JButton[] buttons;
    private Maze maze;
    private MouseHandler mouse;
    private JSpinner spinner;
    private Thread th = null;

    public LabyrinthGUI(ActionStatus action) {

        if (action.equals(ActionStatus.DRAW_MAZE)) {
            maze = new Maze(MAZE_SIZE);
        }

        this.panel = new JPanel();
        this.drawPanel = new DrawablePanel(WIDTH, WIDTH, maze.getTiles());
        this.buttons = new JButton[5];
        this.mouse = new MouseHandler(maze.getTiles());
        FileDialog fd = new FileDialog(this, "Wczytaj", FileDialog.LOAD);

        SpinnerListModel model = new SpinnerListModel(Tools.intToInteger(new int[]{20, 15, 10, 5, 0}));
        this.spinner = new JSpinner(model);
        this.spinner.setSize(410, 40);

        this.buttons[0] = new JButton("Zapisz");
        this.buttons[1] = new JButton("Rozwiąż");
        this.buttons[2] = new JButton("Wyczyść");
        this.buttons[3] = new JButton("Generuj");
        this.buttons[4] = new JButton("Wczytaj");

        this.panel.setSize(WIDTH, 100);

        this.panel.add(spinner);
        this.panel.add(buttons[3], BorderLayout.NORTH); // generuj
        this.panel.add(buttons[0], BorderLayout.SOUTH); // zapisz
        this.panel.add(buttons[1]); // rozwiaz
        this.panel.add(buttons[2], BorderLayout.SOUTH); // wyczysc
        this.panel.add(buttons[4], BorderLayout.SOUTH); // wczytaj

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
            public void actionPerformed(ActionEvent ae) {
                // implementacja algorytmu
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

        this.buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (maze == null) {
                    maze = new Maze(MAZE_SIZE);
                }
                th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        buttons[3].setEnabled(false);
                        maze.generateMaze(MAZE_SIZE, (Integer) spinner.getValue());
                        drawPanel.setTiles(maze.getTiles());
                        mouse.setTiles(maze.getTiles());
                    }
                });

                th.start();
            }
        });

        this.buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fd.setVisible(true);
                String dir = fd.getDirectory() + fd.getFile();
                maze = new Maze();

                if (dir != null && !dir.isEmpty() && dir.length() > 3) {
                    if (maze.loadMazeFromFile(dir) == false) {
                        maze = new Maze(MAZE_SIZE);
                        drawPanel.setTiles(maze.getTiles());
                    }
                }
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

        if (this.maze.isGenerationStatus() == false) {
            this.buttons[3].setEnabled(true);
        }

        this.drawPanel.repaint();
        this.panel.repaint();

    }
}
