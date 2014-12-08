
package daboia.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    
    public static final byte FLOOR = 0;
    public static final byte APPLE = 8;
    public static final byte SNAKE_BODY = -1;
    public static final byte SNAKE_HEAD = -2;
    
    private final List<Point> unoccupied;    
    private final Random random;
    
    private byte[][] core;
    private Piece apple;
    
    public Board(int width, int height) {
        this.core = new byte[height][width];
        this.unoccupied = new ArrayList<>();
        this.random = new Random();
        this.apple = new Piece(width/2, height/2);
        this.core[apple.y][apple.x] = 8;
    }
    
    public Board(Board board) {
        this.core = new byte[board.getHeight()][board.getWidth()];
        for (int y = 0; y < this.core.length; y++) {
            for (int x = 0; x < this.core[0].length; x++) {
                this.core[y][x] = board.get(x, y);
            }
        }
        
        this.apple = new Piece(board.apple);
        this.unoccupied = new ArrayList<>(board.unoccupied);
        this.random = new Random();
    }
    
    public byte get(int x, int y) {
        return this.core[y][x];
    }
    
    public void set(int x, int y, byte data) {
        this.core[y][x] = data;
        
        Point p = new Point(x, y);
        if (data == FLOOR) {
            this.unoccupied.add(p);
        } else {
            this.unoccupied.remove(p);
        }
    }
    
    public void placeApple() {
        int index = this.random.nextInt(this.unoccupied.size());
        Point p = this.unoccupied.get(index);
        this.core[p.y][p.x] = APPLE;
        this.apple = new Piece(p.x, p.y);
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
    
    public Board getCopy() {
        return new Board(this);
    }
    
    public byte[][] getCore() {
        byte[][] corecpy = new byte[this.core.length][this.core[0].length];
        for (int i = 0; i < corecpy.length; i++) {
            System.arraycopy(this.core[i], 0, corecpy[i], 0, corecpy[i].length);
        }
        
        return corecpy;
    }
    
}
