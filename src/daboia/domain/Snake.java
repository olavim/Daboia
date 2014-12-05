
package daboia.domain;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    
    private List<Piece> pieces;
    private boolean grow;
    private int length;
    
    public Snake(int x, int y) {        
        pieces = new ArrayList<>();
        pieces.add(new Piece(x, y));
        
        this.grow = true;
        this.length = 3;
    }
    
    public Snake() {
        pieces = new ArrayList<>();        
    }
    
    public List<Piece> pieces() {
        return new ArrayList<>(this.pieces);
    }
    
    public Piece head() {
        return pieces.get(pieces.size() - 1);
    }
    
    public Piece tail() {
        return pieces.get(0);
    }
    
    public void move(Direction direction) {
        int px = head().x + direction.xTransform();
        int py = head().y + direction.yTransform();
        pieces.add(new Piece(px, py));
        
        if (this.pieces.size() > this.length) {
            pieces.remove(0);
        }
    }
    
    public void grow() {
        this.length++;
    }
    
    public boolean collidesWith(Piece otherPiece) {
        for (Piece piece : this.pieces) {
            if (piece == otherPiece) {
                continue;
            }
            
            if (piece.equals(otherPiece)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean collidesWithItself() {
        return collidesWith(head());
    }
    
    public void setPieces(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }
    
    public Snake copy() {
        Snake snakeCopy = new Snake();
        snakeCopy.setPieces(this.pieces);
        return snakeCopy;
    }
    
    public int length() {
        return this.length;
    }
    
    public int trueLength() {
        return this.pieces.size();
    }

}
