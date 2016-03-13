package labyrinth;

import javax.swing.JFrame;

public class GUI extends JFrame {
    
    public static final String TITLE = "Labyrinth";
    public static final int WIDTH = 300;
    public static final int HEIGHT = 500;
    
    
    public GUI() {
        this.setTitle(TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
    }
}
