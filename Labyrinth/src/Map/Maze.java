package Map;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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
            System.out.println("BŁĄD PRZY OTWIERANIU PLIKU!");
        }

        Scanner scanner = new Scanner(fr);
        size = scanner.nextInt();
        this.tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                value = scanner.nextInt();
                if (value == 0) {
                    tiles[i][j] = new Tile(new Point(i, j), TileStatus.PATH);
                } else if (value == 1) {
                    tiles[i][j] = new Tile(new Point(i, j), TileStatus.WALL);
                } else if (value == 2) {
                    tiles[i][j] = new Tile(new Point(i, j), TileStatus.START);
                } else if (value == 3) {
                    tiles[i][j] = new Tile(new Point(i, j), TileStatus.EXIT);
                }
            }
        }
        try {
            fr.close();
        } catch (IOException e) {
            System.out.println("BŁĄD PRZY ZAMYKANIU PLIKU!");
        }

    }

    public void saveMazeToFile() {

    }

}
