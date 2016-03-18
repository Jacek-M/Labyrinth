package Map;

import Misc.Tools;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
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

    public boolean loadMazeFromFile(String path) {
        FileReader fr = null;
        int size;
        int value;
        try {
            fr = new FileReader(path);

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
            fr.close();
            JOptionPane.showMessageDialog(null, "Załadowano pomyślnie");
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Nie wybrano pliku");
            return false;
        }
    }

    public boolean saveMazeToFile(String path) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(path);

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

            fw.close();
            JOptionPane.showMessageDialog(null, "Zapisano do pliku");
            return true;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Nie wybrano pliku...");
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private Tile getTileOnPos(int x, int y) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j].getPoint().x == x && tiles[i][j].getPoint().y == y) {
                    return tiles[i][j];
                }
            }
        }
        return null;
    }

    private ArrayList<Tile> getNeighbours(int x, int y) {
        ArrayList<Tile> aTile = new ArrayList<>();

        int[][] offsets
                = {{0, -2}, {-2, 0}, /*tile*/ {2, 0}, {0, 2}};

        for (int i = 0; i < offsets.length; i++) {
            Tile temp = getTileOnPos(x + offsets[i][0], y + offsets[i][1]);
            if (temp != null && !temp.isVisited()) {
                aTile.add(getTileOnPos(x + offsets[i][0], y + offsets[i][1]));
            }
        }
        return aTile;
    }

    public void generateMaze(int mazeSize) {

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                if (i % 2 != 0 && j % 2 != 0) {
                    tiles[i][j].setTileStatus(TileStatus.PATH);
                } else {
                    tiles[i][j].setTileStatus(TileStatus.WALL);
                }
                tiles[i][j].setVisited(false);
            }
        }

        ArrayList<Tile> unvisitedTiles = Tools.arrayToList(tiles);
        System.out.println("UnvisitedTiles Len = " + unvisitedTiles.size());
        ArrayList<Tile> copyTiles = (ArrayList<Tile>) unvisitedTiles.clone();
        Stack visited = new Stack();

        Random r = null;
        Tile currentTile = null;
        do {
            r = new Random();
            currentTile = unvisitedTiles.get(r.nextInt(unvisitedTiles.size()));
            System.out.println("LOOKING FOR PATH CELL BEFOR WHILE");
        } while (currentTile.getTileStatus().equals(TileStatus.WALL));

        unvisitedTiles.remove(currentTile);
        currentTile.setVisited(true);
        currentTile.setTileStatus(TileStatus.PATH);
        while (unvisitedTiles.size() > 0) {
            int currx = currentTile.getPoint().x;
            int curry = currentTile.getPoint().y;

            ArrayList<Tile> neighbours = getNeighbours(currx, curry);
            if (neighbours.size() > 0) {
                Tile randomNTile = null;
                do {
                    r = new Random();
                    randomNTile = neighbours.get(r.nextInt(neighbours.size()));
                    System.out.println("LOOKING FOR PATH CELL INSIDE WHILE");
                } while (randomNTile.getTileStatus().equals(TileStatus.WALL));
                visited.push(currentTile);

                int ofsx = currentTile.getPoint().x - randomNTile.getPoint().x;
                int ofsy = currentTile.getPoint().y - randomNTile.getPoint().y;
                Tile tile = getTileOnPos(randomNTile.getPoint().x + (ofsx / 2), randomNTile.getPoint().y + (ofsy / 2));
                if (tile != null) {
                    tile.setTileStatus(TileStatus.PATH);
                }

                currentTile = randomNTile;
                currentTile.setVisited(true);
                unvisitedTiles.remove(currentTile);

                System.out.println("===>VISITING");
            } else {
                if (visited.size() > 0) {
                    currentTile = (Tile) visited.pop();
                    System.out.println("====>POPING");
                } else {
                    System.out.println("BLAD WYKONANIA");
                }
            }
            try {
                Thread.sleep(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("END OF GENERATING");

        int empty = 0, fill = 0;
        for (int i = 0;
                i < mazeSize;
                i++) {
            for (int j = 0; j < mazeSize; j++) {
                if (tiles[i][j].getTileStatus().equals(TileStatus.PATH)) {
                    empty++;
                } else {
                    fill++;
                }

            }
        }

        System.out.println(
                "EMPTY === " + empty + " FILLED = " + fill);
    }

}
