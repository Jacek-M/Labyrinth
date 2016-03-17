/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Misc;

import Map.Tile;
import Map.TileStatus;
import java.util.ArrayList;

/**
 *
 * @author layfl
 */
public class Tools {

    public static ArrayList<Tile> arrayToList(Tile[][] tiles) {
        ArrayList<Tile> tileList = new ArrayList<>();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j].getTileStatus().equals(TileStatus.PATH)) {
                    tileList.add(tiles[i][j]);
                }
            }
        }
        return tileList;
    }

    public static Tile[][] listToArray2d(ArrayList<Tile> list, int mazeSize) {
        Tile[][] tiles = new Tile[mazeSize][mazeSize];
        int k = 0;
        int r = 0;
        for (int i = 0; i < mazeSize * mazeSize; i++) {
            if (k % mazeSize == 0) {
                r++;
                k = 0;
            }
            tiles[r][k] = list.get(i);
        }
        return tiles;
    }
}
