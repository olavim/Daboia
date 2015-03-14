
package com.github.tilastokeskus.daboia.core;

import java.awt.Point;
import java.util.*;

public class CoreBoard implements java.io.Serializable {
    
    private static final long serialVersionUID = 2015_03_10_18_20L;
    
    private final Set<Point> unoccupied;    
    private final BoardConstant[][] core;
    
    private Piece apple;
    
    public CoreBoard(int width, int height) {
        this.core = new BoardConstant[height][width];
        this.unoccupied = new HashSet<>();
        this.apple = new Piece(width/2, height/2);
        
        initCore(height, width);
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
            Point p = getRandomUnoccupiedPoint();            
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
    
    public BoardConstant[][] getMatrix() {        
        return core;
    }

    private Point getRandomUnoccupiedPoint() {
        int index = (int) (Math.random() * unoccupied.size());
        Iterator<Point> iter = unoccupied.iterator();
        while (index-- > 0) iter.next();
        return iter.next();
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
