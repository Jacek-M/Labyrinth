package GUI;

import Map.Maze;
import Map.TileStatus;
import Misc.Tools;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LabyrinthGUI extends JFrame {

    public static final String TITLE = "MazeForm";
    public static final int WIDTH = 500;
    public static final int HEIGHT = 600;

    public static final int TILE_SIZE = 10;
    public static final int MAZE_SIZE = (WIDTH / TILE_SIZE) - 1;

    private JPanel panel;
    private DrawablePanel drawPanel;
    private JButton[] buttons;
    private Maze maze;
    private MouseHandler mouse;
    private JSpinner spinner;
    private Thread th = null;
    private JRadioButton[] radio;

    public LabyrinthGUI(ActionStatus action) {
        guiSettings();
        maze = new Maze(MAZE_SIZE);

        this.panel = new JPanel();
        this.drawPanel = new DrawablePanel(WIDTH, WIDTH, maze.getTiles());
        this.buttons = new JButton[6];
        this.radio = new JRadioButton[4];
        this.mouse = new MouseHandler(maze);
        this.panel.setSize(WIDTH, 200);

        this.panel.setLayout(new GridLayout(2, 6));

        radioButton();
        createButtons();

        this.add(drawPanel, BorderLayout.CENTER);
        this.add(panel, BorderLayout.PAGE_END);

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

    private void guiSettings() {
        this.setTitle(TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createButtons() {
        SpinnerListModel model = new SpinnerListModel(Tools.intToInteger(new int[]{0, 5, 10, 15, 20}));
        this.spinner = new JSpinner(model);

        this.buttons[0] = new JButton("Zapisz");
        this.buttons[1] = new JButton("Rozwiąż");
        this.buttons[2] = new JButton("Wyczyść");
        this.buttons[3] = new JButton("Generuj");
        this.buttons[4] = new JButton("Wczytaj");
        this.buttons[5] = new JButton("Zapisz ścieżke");

        
        this.panel.add(spinner);
        this.panel.add(buttons[1]); // rozwiaz
        this.panel.add(buttons[0]); // zapisz     
        this.panel.add(buttons[4]); // wczytaj
        this.panel.add(buttons[2]); // wyczysc
        this.panel.add(buttons[3]); // generuj
        this.panel.add(buttons[5]); // export


        this.buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fd = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Pliki tekstowe", "txt");
                fd.setFileFilter(filter);
                if (fd.showSaveDialog(panel) == JFileChooser.APPROVE_OPTION) {
                    maze.saveMazeToFile(fd.getSelectedFile().getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(null, "Nie wybrano pliku");
                }
            }
        });

        this.buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (maze.mazeReady()) {
                    maze.mazeSolver();
                    drawPanel.setTiles(maze.getTiles());
                    mouse.setMaze(maze);
                } else {
                    JOptionPane.showMessageDialog(null, "Błąd ustawienia punktu startu i końca");
                }
            }
        });

        this.buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maze = new Maze(MAZE_SIZE);
                drawPanel.setTiles(maze.getTiles());
                mouse.setMaze(maze);
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
                        mouse.setMaze(maze);
                    }
                });

                th.start();
            }
        });

        this.buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fd = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Pliki tekstowe", "txt");
                fd.setFileFilter(filter);
                if (fd.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION) {
                    if (maze.loadMazeFromFile(fd.getSelectedFile().getAbsolutePath()) == false) {
                        maze = new Maze(MAZE_SIZE);
                        drawPanel.setTiles(maze.getTiles());
                        mouse.setMaze(maze);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nie wybrano pliku");
                }
            }
        });

        this.buttons[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fd = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Pliki tekstowe", "txt");
                fd.setFileFilter(filter);
                if (fd.showSaveDialog(panel) == JFileChooser.APPROVE_OPTION) {
                    maze.exportPath(fd.getSelectedFile().getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(null, "Nie wybrano pliku");
                }
            }
        });
    }

    private void radioButton() {

        radio[0] = new JRadioButton("Path");
        radio[1] = new JRadioButton("Wall");
        radio[1].setSelected(true);
        mouse.setCurrentTileStatus(TileStatus.WALL);
        radio[2] = new JRadioButton("Start");
        radio[3] = new JRadioButton("Exit");

        ButtonGroup group = new ButtonGroup();
        group.add(radio[0]);
        group.add(radio[1]);
        group.add(radio[2]);
        group.add(radio[3]);

        radio[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mouse.setCurrentTileStatus(TileStatus.PATH);
            }
        });
        radio[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mouse.setCurrentTileStatus(TileStatus.WALL);
            }
        });
        radio[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mouse.setCurrentTileStatus(TileStatus.START);
            }
        });
        radio[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mouse.setCurrentTileStatus(TileStatus.EXIT);
            }
        });

        this.panel.add(radio[0]);
        this.panel.add(radio[1]);
        this.panel.add(radio[2]);
        this.panel.add(radio[3]);
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
