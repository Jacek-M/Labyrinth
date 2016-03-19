package labyrinth;

import GUI.ActionStatus;
import GUI.LabyrinthGUI;

public class Runner {
    
    public static void main(String[] args) {
        LabyrinthGUI gui = new LabyrinthGUI(ActionStatus.DRAW_MAZE);
        gui.setVisible(true);
    }
}
