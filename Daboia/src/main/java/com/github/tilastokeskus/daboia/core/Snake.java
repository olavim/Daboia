
package com.github.tilastokeskus.daboia.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Snake implements Cloneable, java.io.Serializable {
    
    private static final long serialVersionUID = 2015_03_10_18_16L;
    
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
        return this.pieces.stream()
                .filter(p -> p != otherPiece)
                .anyMatch(p -> p.equals(otherPiece));
    }
    
    public boolean collidesWith(Snake otherSnake) {
        if (otherSnake == this)
            return this.collidesWithItself();
        
        return otherSnake.getPieces().stream()
                .anyMatch(p -> this.collidesWith(p));
    }
    
    public void setPieces(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }
    
    public Snake copy() {
        Snake snakeCopy = new Snake();
        snakeCopy.setPieces(new ArrayList<>(pieces));
        snakeCopy.length = length;
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
    
    private boolean collidesWithItself() {
        return this.collidesWith(getHead());
    }
    
    @Override
    public String toString() {
        return "length: " + this.length + "\nhead: " + this.getHead();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Snake other = (Snake) obj;
        return pieces.equals(other.pieces) && this.length == other.length;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.pieces);
        hash = 47 * hash + this.length;
        return hash;
    }

}
