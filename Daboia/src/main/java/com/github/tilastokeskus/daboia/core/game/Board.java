
package com.github.tilastokeskus.daboia.core.game;

import com.github.tilastokeskus.daboia.core.Piece;
import java.awt.Point;
import java.util.*;
import org.apache.commons.collections4.list.TreeList;

public class Board {
    
    private final TreeList<Point> unoccupied;
    private final Random random;
    
    private final int[][] core;
    private Piece apple;
    
    public Board(int width, int height) {
        this.core = new int[height][width];
        this.unoccupied = new TreeList<>();
        this.random = new Random();
        this.apple = new Piece(width/2, height/2);
        this.core[apple.y][apple.x] = 8;
        
        for (int y = 0; y < core.length; y++) {
            for (int x = 0; x < core[0].length; x++) {
                this.unoccupied.add(new Point(x, y));
            }
        }
    }
    
    public int get(int x, int y) {
        return this.core[y][x];
    }
    
    public void set(int x, int y, int data) {
        if (x >= 0 && y >= 0 && x < getWidth() && y < getHeight()) {
            this.core[y][x] = data;

            Point p = new Point(x, y);
            if (data == BoardConstants.FLOOR) {
                this.unoccupied.add(p);
            } else {
                this.unoccupied.remove(p);
            }
        }
    }
    
    public void setApple(Piece apple) {
        this.apple = apple;
    }
    
    public void placeApple() {
        if (!unoccupied.isEmpty()) {
            int index = random.nextInt(unoccupied.size());
            Point p = unoccupied.get(index);            
            this.apple = new Piece(p.x, p.y);
        } else {
            this.apple = null;
        }
    }
    
    public Piece getApple() {
        return this.apple;
    }
    
    public int getWidth() {
        return this.core[0].length;
    }
    
    public int getHeight() {
        return this.core.length;
    }
    
    public int numUnoccupied() {
        return this.unoccupied.size();
    }
    
    public int[][] getCore() {
        int[][] corecpy = new int[core.length][core[0].length];
        for (int i = 0; i < corecpy.length; i++) {
            System.arraycopy(core[i], 0, corecpy[i], 0, corecpy[i].length);
        }
        
        return corecpy;
    }
    
}
