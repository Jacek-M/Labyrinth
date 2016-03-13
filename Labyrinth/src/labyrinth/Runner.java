package labyrinth;

public class Runner {

    public static final String TITLE = "Labyrinth";
    public static final int WIDTH = 300;
    public static final int HEIGHT = 500;

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setTitle(TITLE);
        gui.setSize(WIDTH, HEIGHT);
        gui.setResizable(false);
        gui.setVisible(true);
    }
}
