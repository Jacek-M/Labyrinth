package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {

    public static final String TITLE = "Labyrinth";
    public static final int WIDTH = 400;
    public static final int HEIGHT = 80;
    private JPanel panel;
    private JButton[] buttons;
    private LabyrinthGUI labiLabyrinthGUI;
    

    public GUI() {
        panel = new JPanel();
        buttons = new JButton[3];

        buttons[0] = new JButton("Generuj labirynt");
        buttons[1] = new JButton("Rysuj labirynt");
        buttons[2] = new JButton("Wczytaj labirynt");

        panel.add(buttons[0]);
        panel.add(buttons[1]);
        panel.add(buttons[2]);

        this.add(panel);

        this.setTitle(TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labiLabyrinthGUI = new LabyrinthGUI(ActionStatus.GENERATE_MAZE);
                labiLabyrinthGUI.setVisible(true);
                GUI.this.dispose();
            }
        });

        this.buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labiLabyrinthGUI = new LabyrinthGUI(ActionStatus.DRAW_MAZE);
                labiLabyrinthGUI.setVisible(true);
                GUI.this.dispose();
            }
        });

        this.buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labiLabyrinthGUI = new LabyrinthGUI(ActionStatus.LOAD_MAZE);
                labiLabyrinthGUI.setVisible(true);
                GUI.this.dispose();
            }
        });

    }
}
