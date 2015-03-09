
package com.github.tilastokeskus.daboia.core;

import java.awt.Point;

public class Piece {
    
    public final int x;
    public final int y;
    
    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Piece(Piece piece) {
        this.x = piece.x;
        this.y = piece.y;
    }
    
    public Point asPoint() {
        return new Point(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        
        if (getClass() != obj.getClass())
            return false;
        
        final Piece other = (Piece) obj;        
        return this.x == other.x && this.y == other.y;
    }    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.x;
        hash = 53 * hash + this.y;
        return hash;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

}
