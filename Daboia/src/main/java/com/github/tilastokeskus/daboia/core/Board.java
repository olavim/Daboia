
package com.github.tilastokeskus.daboia.core;

import java.awt.Point;
import java.util.*;

public class Board implements java.io.Serializable {
    
    private static final long serialVersionUID = 2015_03_10_18_20L;
    
    private final Set<Point> unoccupied;    
    private final BoardConstant[][] core;
    
    private Piece apple;
    
    public Board(int width, int height) {
        this.core = new BoardConstant[height][width];
        this.unoccupied = new HashSet<>();
        this.apple = new Piece(width/2, height/2);
        
        initCore(height, width);
    }
    
    public BoardConstant get(int x, int y) {
        return this.core[y][x];
    }
    
    public void set(int x, int y, BoardConstant data) {
        if (!isInBounds(x, y)) 
            throw new IllegalArgumentException("Coordinates out of bounds");
        
        this.core[y][x] = data;

        Point p = new Point(x, y);
        if (data == BoardConstant.FLOOR) {
            this.unoccupied.add(p);
        } else {
            this.unoccupied.remove(p);
        }
    }
    
    public void setApple(Piece apple) {
        this.apple = apple;
    }
    
    public void randomlyPlaceApple() {
        if (!unoccupied.isEmpty()) {
            int index = (int) (Math.random() * unoccupied.size());
            Iterator<Point> iter = unoccupied.iterator();
            while (index-- > 0) iter.next();
            Point p = iter.next();
            
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
    
    public BoardConstant[][] getCore() {
        BoardConstant[][] corecpy = new BoardConstant[getHeight()][getWidth()];
        for (int i = 0; i < corecpy.length; i++) {
            System.arraycopy(core[i], 0, corecpy[i], 0, corecpy[i].length);
        }
        
        return corecpy;
    }

    private void initCore(int height, int width) {
        for (int y = 0; y < height; y++) {
            Arrays.fill(core[y], BoardConstant.FLOOR);
            for (int x = 0; x < width; x++)
                unoccupied.add(new Point(x, y));
        }
        
        this.core[apple.y][apple.x] = BoardConstant.APPLE;
    }
    
    private boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < getWidth() && y < getHeight();
    }
    
}
