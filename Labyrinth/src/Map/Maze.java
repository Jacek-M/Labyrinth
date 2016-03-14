package Map;

import java.awt.Point;

public class Maze {

    private Tile[][] tiles;
    private Point endPoint;
    private Point startPoint;

    public Maze(Tile[][] tiles, Point endPoint, Point startPoint) {
        this.tiles = tiles;
        this.endPoint = endPoint;
        this.startPoint = startPoint;
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

}
