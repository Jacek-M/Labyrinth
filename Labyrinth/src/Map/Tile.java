package Map;

import Map.TileStatus;
import java.awt.Point;

public class Tile {

    private Point point;
    private TileStatus tileStatus;

    public Tile(Point point, TileStatus tileStatus) {
        this.point = point;
        this.tileStatus = tileStatus;
    }

    public Point getPoint() {
        return point;
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
