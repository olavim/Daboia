
package daboia.domain.game;

import daboia.domain.Piece;
import daboia.domain.Player;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    
    private final List<Point> unoccupied;
    private final Random random;
    
    private int[][] core;
    private Piece apple;
    
    public Board(int width, int height) {
        this.core = new int[height][width];
        this.unoccupied = new ArrayList<>();
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
        if (!this.unoccupied.isEmpty()) {
            int index = this.random.nextInt(this.unoccupied.size());
            Point p = this.unoccupied.get(index);
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
    
    public void refresh(List<Player> players) {
        this.core = new int[this.core.length][this.core[0].length];
        
        for (Player player : players) {
            if (!player.isAlive()) {
                continue;
            }
            
            for (Piece piece : player.getSnake().getPieces()) {                
                this.core[piece.y][piece.x] = BoardConstants.SNAKE_BODY;
            }

            Piece head = player.getSnake().getHead();
            this.core[head.y][head.x] = BoardConstants.SNAKE_HEAD;
        }
        
        if (apple != null) {
            this.core[apple.y][apple.x] = BoardConstants.APPLE;
        }
    }
    
    public int[][] getCore() {
        int[][] corecpy = new int[this.core.length][this.core[0].length];
        for (int i = 0; i < corecpy.length; i++) {
            System.arraycopy(this.core[i], 0, corecpy[i], 0, corecpy[i].length);
        }
        
        return corecpy;
    }
    
}
