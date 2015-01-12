
package com.github.tilastokeskus.daboia.core;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    
    private List<Piece> pieces;
    private int length;
    
    public Snake(int x, int y) {        
        pieces = new ArrayList<>();
        pieces.add(new Piece(x, y));
        this.length = 3;
    }
    
    public Snake() {
        pieces = new ArrayList<>();        
    }
    
    public List<Piece> getPieces() {
        return new ArrayList<>(this.pieces);
    }
    
    public Piece getHead() {
        return pieces.get(pieces.size() - 1);
    }
    
    public Piece getTail() {
        return pieces.get(0);
    }
    
    public void move(Direction direction) {
        int px = getHead().x + direction.xTransform();
        int py = getHead().y + direction.yTransform();
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
            if (piece != otherPiece && piece.equals(otherPiece)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean collidesWith(Snake otherSnake) {
        if (otherSnake == this) {
            return this.doesCollideWithItself();
        }
        
        for (Piece piece : otherSnake.getPieces()) {
            if (this.collidesWith(piece)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean doesCollideWithItself() {
        return Snake.this.collidesWith(getHead());
    }
    
    public void setPieces(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }
    
    public Snake copy() {
        Snake snakeCopy = new Snake();
        snakeCopy.setPieces(this.pieces);
        return snakeCopy;
    }
    
    /**
     * The potential length of the snake.
     * 
     * @return  The amount of pieces the snake will consist of after X moves,
     *          or in other words, the amount of apples the snake has eaten in
     *          default gametypes.
     */
    public int getLength() {
        return this.length;
    }
    
    /**
     * The visible length of the snake.
     * 
     * @return  The amount of pieces the snake consists of.
     */
    public int getTrueLength() {
        return this.pieces.size();
    }
    
    @Override
    public String toString() {
        return "length: " + this.length + "\nhead: " + this.getHead();
    }

}
