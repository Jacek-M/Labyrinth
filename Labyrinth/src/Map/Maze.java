package Map;

import Misc.Tools;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Klasa przechwująca dane o labiryncie
 */
public class Maze {

    private Tile[][] tiles;
    private Point endPoint;
    private Point startPoint;
    private ArrayList<Point> pathCoords;

    private volatile boolean generationStatus = false;

    public Maze(Tile[][] tiles, Point endPoint, Point startPoint) {
        this.tiles = tiles;
        this.endPoint = endPoint;
        this.startPoint = startPoint;
        this.pathCoords = new ArrayList<>();
    }

    public Maze(int size) {
        this.tiles = new Tile[size][size];
        this.pathCoords = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.tiles[i][j] = new Tile(new Point(i, j), TileStatus.PATH);
            }
        }
    }

    public Maze() {
    }

    public boolean isGenerationStatus() {
        return generationStatus;
    }

    public void setGenerationStatus(boolean generationStatus) {
        this.generationStatus = generationStatus;
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

    /**
     * Metoda wczytująca labirynt z podanego pliku
     * @param path - ścieżka do pliku
     * @return - czy operacjia się powiodła
     */
    public boolean loadMazeFromFile(String path) {
        FileReader fr = null;
        int size;
        int value;
        try {
            fr = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fr);
            String line = bufferedReader.readLine();
            if (line == null || !(line.contains("#Labirynt"))) {
                JOptionPane.showMessageDialog(null, "Plik nie zawiera labiryntu :(");
                return false;
            }
            size = Integer.parseInt(bufferedReader.readLine());
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    value = bufferedReader.read();
                    switch (value) {
                        case 48:
                            this.tiles[i][j] = new Tile(new Point(i, j), TileStatus.PATH);
                            break;
                        case 49:
                            this.tiles[i][j] = new Tile(new Point(i, j), TileStatus.WALL);
                            break;
                        case 50:
                            this.tiles[i][j] = new Tile(new Point(i, j), TileStatus.START);
                            this.startPoint = this.tiles[i][j].getPoint();
                            break;
                        case 51:
                            this.tiles[i][j] = new Tile(new Point(i, j), TileStatus.EXIT);
                            this.endPoint = this.tiles[i][j].getPoint();
                            break;
                        default:
                            this.tiles[i][j] = new Tile(new Point(i, j), TileStatus.PATH);
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

    /**
     * Metoda zapisująca ścieżke wyjścia z labiryntu do pliku
     * @param filePath - ścieżka do pliku
     * @return - czy operacjia się powiodła
     */
    public boolean exportPath(String filePath) {
        FileWriter fileWriter = null;
        if(this.pathCoords == null || this.pathCoords.size() <= 0) {
            return false;
        }
        try {
            fileWriter = new FileWriter(filePath);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("#Path");
            
            for(Point p : pathCoords) {
                printWriter.println(p.x + " " + p.y );
            }

            fileWriter.close();
            printWriter.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Nie mozna zapisać do pliku");
        }
        return true;
    }

    /**
     * Metoda zapisująca aktualny stan labiryntu do pliku
     * @param path - ścieżka do pliku
     * @return - czy operacjia się powiodła
     */
    public boolean saveMazeToFile(String path) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(path);

            PrintWriter printW = new PrintWriter(fw);
            printW.println("#Labirynt");
            printW.println(this.tiles.length);

            for (int i = 0; i < this.tiles.length; i++) {
                for (int j = 0; j < this.tiles[i].length; j++) {
                    switch (tiles[i][j].getTileStatus().toString()) {
                        case "PATH":
                            printW.print(0);
                            break;
                        case "WALL":
                            printW.print(1);
                            break;
                        case "START":
                            printW.print(2);
                            break;
                        case "EXIT":
                            printW.print(3);
                            break;
                        default:
                            printW.print(0);
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

    /**
     * Metoda zwracająca komórke z podanej pozycji
     * @param x - wartość x
     * @param y - wartość y
     */
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

    /**
     * Metoda zwracająca sąsiadów podanej komórki
     * @param x - wartość x
     * @param y - wartość y
     */
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

    /**
     * Metoda tworząca siatke potrzebną do wygenerowania labiryntu
     * @param mazeSize - wielkość labiryntu
     */
    private void createTemplate(int mazeSize) {
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
    }

    public void generateMaze(int mazeSize, int mazeInterval) {
        this.generationStatus = true;
        createTemplate(mazeSize);

        ArrayList<Tile> unvisitedTiles = Tools.arrayToList(tiles);
        Stack visited = new Stack();

        Random r = null;
        Tile currentTile = null;
        do {
            r = new Random();
            currentTile = unvisitedTiles.get(r.nextInt(unvisitedTiles.size()));
        } while (currentTile.getTileStatus().equals(TileStatus.WALL));

        unvisitedTiles.remove(currentTile);
        currentTile.setVisited(true);
        currentTile.setTileStatus(TileStatus.START);
        this.startPoint = currentTile.getPoint();
        while (unvisitedTiles.size() > 0) {
            int currx = currentTile.getPoint().x;
            int curry = currentTile.getPoint().y;

            ArrayList<Tile> neighbours = getNeighbours(currx, curry);
            if (neighbours.size() > 0) {
                Tile randomNTile = null;
                do {
                    r = new Random();
                    randomNTile = neighbours.get(r.nextInt(neighbours.size()));
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

            } else if (visited.size() > 0) {
                currentTile = (Tile) visited.pop();
            } else {
                System.out.println("BLAD WYKONANIA");
            }
            try {
                Thread.sleep(mazeInterval);
            } catch (InterruptedException ex) {
                Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.generationStatus = false;
    }

    public boolean mazeReady() {
        if (this.startPoint != null && this.endPoint != null) {
            return true;
        }
        return false;
    }

    public void mazeSolver() {
        Tile current;
        int actualScore = Integer.MAX_VALUE;

        ArrayList<Tile> open = new ArrayList<>();
        ArrayList<Tile> closed = new ArrayList<>();
        ArrayList<Tile> neighbours = new ArrayList<>();

        open.add(this.tiles[startPoint.x][startPoint.y]);

        while (1 == 1) {
            if (open.size() == 0) {
                JOptionPane.showMessageDialog(null, "Brak śćieżki ;/");
                break;
            }
            current = open.get(0);
            for (Tile tile : open) {
                if (tile.getScore() < current.getScore()) {
                    current = tile;
                }
            }

            open.remove(current);
            closed.add(current);

            if (current.getTileStatus() == TileStatus.EXIT) {
                System.out.println("Koniec");
                markPath(current.getParent());
                break;
            }

            neighbours = getNeighboursToSolver(current.getPoint().x, current.getPoint().y);
            for (Tile tile : neighbours) {
                if (tile.getTileStatus() == TileStatus.WALL || isInList(closed, tile)) {
                    continue;
                }

                if (actualScore > tile.getScore() || !isInList(open, tile)) {
                    tile.setParent(current);
                    tile.setScore(tile.getParent().getScore() + 1);
                    actualScore = tile.getScore();
                    if (!isInList(open, tile)) {
                        open.add(tile);
                    }
                }
            }

        }
    }

    public boolean isInList(ArrayList<Tile> list, Tile current) {
        if (list.contains(current)) {
            return true;
        }
        return false;
    }

    private void markPath(Tile tile) {
        while (tile.getParent() != null && tile.getTileStatus() != TileStatus.START) {
            tile.setTileStatus(TileStatus.RUN);
            this.pathCoords.add(tile.getPoint());
            tile = tile.getParent();
        }
    }

    private ArrayList<Tile> getNeighboursToSolver(int x, int y) {
        ArrayList<Tile> aTile = new ArrayList<>();

        int[][] offsets
                = {{0, -1}, {-1, 0}, /*tile*/ {1, 0}, {0, 1}};

        for (int i = 0; i < offsets.length; i++) {
            if (getTileOnPos(x + offsets[i][0], y + offsets[i][1]) != null) {
                aTile.add(getTileOnPos(x + offsets[i][0], y + offsets[i][1]));
            }
        }
        return aTile;
    }
}
