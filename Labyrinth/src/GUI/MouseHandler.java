package GUI;

import Map.Tile;
import Map.TileStatus;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    private boolean mouseStatus = false;
    private boolean eraseMode = false;
    private Tile[][] tiles;

    public MouseHandler(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public boolean isMouseStatus() {
        return mouseStatus;
    }

    public void setMouseStatus(boolean mouseStatus) {
        this.mouseStatus = mouseStatus;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getButton() == MouseEvent.BUTTON3) // right button
            this.eraseMode = true;
        else
            this.mouseStatus = true;
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if(me.getButton() == MouseEvent.BUTTON3)
            this.eraseMode = true;
        else
            this.mouseStatus = true;
        setWall(me);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        this.mouseStatus = false;
        this.eraseMode = false;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (this.mouseStatus || this.eraseMode) {
            setWall(me);
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }

    private void setWall(MouseEvent me) {
        int tilex = (int) ((me.getX()) / LabyrinthGUI.TILE_SIZE);
        int tiley = (int) ((me.getY()) / LabyrinthGUI.TILE_SIZE);
        if (tilex < LabyrinthGUI.MAZE_SIZE && tilex >= 0 && tiley < LabyrinthGUI.MAZE_SIZE && tiley >= 0) {
            if(this.eraseMode == true)
                this.tiles[tilex][tiley].setTileStatus(TileStatus.PATH);
            else
                this.tiles[tilex][tiley].setTileStatus(TileStatus.WALL);
        }
    }

}
