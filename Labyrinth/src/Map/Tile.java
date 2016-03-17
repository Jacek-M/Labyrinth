package Map;

import java.awt.Point;

public class Tile {

    private Point point;
    private TileStatus tileStatus;
    private boolean visited = false;

    public Tile(Point point, TileStatus tileStatus) {
        this.point = point;
        this.tileStatus = tileStatus;
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
