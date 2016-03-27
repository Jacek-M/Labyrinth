package Map;

import java.awt.Point;

public class Tile {

    private Point point;
    private TileStatus tileStatus;
    private int score = 0;
    private boolean visited = false;
    private Tile parent;

    public Tile getParent() {
        return parent;
    }

    public void setParent(Tile parent) {
        this.parent = parent;
    }

    public Tile(Point point, TileStatus tileStatus) {
        this.point = point;
        this.tileStatus = tileStatus;
    }

    public Tile(Point point, TileStatus tileStatus, int score) {
        this.point = point;
        this.tileStatus = tileStatus;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Point getPoint() {
        return point;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public TileStatus getTileStatus() {
        return tileStatus;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setTileStatus(TileStatus tileStatus) {
        this.tileStatus = tileStatus;
    }
}
