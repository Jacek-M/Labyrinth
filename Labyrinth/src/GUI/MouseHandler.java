package GUI;

import Map.Maze;
import Map.TileStatus;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    private boolean mouseStatus = false;
    private boolean eraseMode = false;
    private TileStatus currentTileStatus;
    private Maze maze;

    public MouseHandler(Maze maze) {
        this.maze = maze;
    }

    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public boolean isMouseStatus() {
        return mouseStatus;
    }

    public void setMouseStatus(boolean mouseStatus) {
        this.mouseStatus = mouseStatus;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getButton() == MouseEvent.BUTTON3) // right button
        {
            this.eraseMode = true;
        } else {
            this.mouseStatus = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (me.getButton() == MouseEvent.BUTTON3) {
            this.eraseMode = true;
        } else {
            this.mouseStatus = true;
        }
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
            if (this.eraseMode == true) {
                this.maze.getTiles()[tilex][tiley].setTileStatus(TileStatus.PATH);
            } else {
                if (currentTileStatus == TileStatus.START) {
                    if (maze.getStartPoint() != null) {
                        this.maze.getTiles()[this.maze.getStartPoint().x][this.maze.getStartPoint().y].setTileStatus(TileStatus.PATH);
                    }

                    this.maze.setStartPoint(new Point(tilex, tiley));
                } else if (currentTileStatus == TileStatus.EXIT) {
                    if (maze.getEndPoint() != null) {
                        this.maze.getTiles()[this.maze.getEndPoint().x][this.maze.getEndPoint().y].setTileStatus(TileStatus.PATH);
                    }
                    this.maze.setEndPoint(new Point(tilex, tiley));
                }
                if (this.maze.getTiles()[tilex][tiley].getTileStatus() == TileStatus.START) {
                    this.maze.setStartPoint(null);
                } else if (this.maze.getTiles()[tilex][tiley].getTileStatus() == TileStatus.EXIT) {
                    this.maze.setEndPoint(null);
                }
                this.maze.getTiles()[tilex][tiley].setTileStatus(currentTileStatus);
            }
        }
    }

    public TileStatus getCurrentTileStatus() {
        return currentTileStatus;
    }

    public void setCurrentTileStatus(TileStatus currentTileStatus) {
        this.currentTileStatus = currentTileStatus;
    }

}
