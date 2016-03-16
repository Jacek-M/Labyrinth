package Map;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Maze {

    private Tile[][] tiles;
    private Point endPoint;
    private Point startPoint;

    public Maze(Tile[][] tiles, Point endPoint, Point startPoint) {
        this.tiles = tiles;
        this.endPoint = endPoint;
        this.startPoint = startPoint;
    }

    public Maze(int size) {
        tiles = new Tile[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.tiles[i][j] = new Tile(new Point(i, j), TileStatus.PATH);
            }
        }
    }

    public Maze() {
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public void loadMazeFromFile(String path) {
        FileReader fr = null;
        int size;
        int value;

        try {
            fr = new FileReader(path);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Serio??!!!?? Pliku nie umiesz wybrac??? :P");
        }

        Scanner scanner = new Scanner(fr);
        size = scanner.nextInt();
        this.tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                value = scanner.nextInt();
                switch (value) {
                    case 0:
                        tiles[i][j] = new Tile(new Point(i, j), TileStatus.PATH);
                        break;
                    case 1:
                        tiles[i][j] = new Tile(new Point(i, j), TileStatus.WALL);
                        break;
                    case 2:
                        tiles[i][j] = new Tile(new Point(i, j), TileStatus.START);
                        break;
                    case 3:
                        tiles[i][j] = new Tile(new Point(i, j), TileStatus.EXIT);
                        break;
                }
            }
        }
        try {
            fr.close();
            JOptionPane.showMessageDialog(null, "Gitara");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Serio??!!!?? Pliku nie umiesz wybrac??? :P");
        }

    }

    public void saveMazeToFile(String path) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(path);
        } catch (FileNotFoundException e ) {
            JOptionPane.showMessageDialog(null, "Serio??!!!?? Pliku nie umiesz wybrac??? :P");
        } catch (IOException ex) {
            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
        }

        PrintWriter printW = new PrintWriter(fw);
        printW.println(this.tiles.length);

        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                switch (tiles[i][j].getTileStatus().toString()) {
                    case "PATH":
                        printW.println(0);
                        break;
                    case "WALL":
                        printW.println(1);
                        break;
                    case "START":
                        printW.println(2);
                        break;
                    case "EXIT":
                        printW.println(3);
                        break;
                }
            }
        }
        try {
            fw.close();
            JOptionPane.showMessageDialog(null, "+1");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Serio??!!!?? Pliku nie umiesz wybrac??? :P");
        }

    }

}
