
package com.github.tilastokeskus.daboia.core.game.ai;

import com.github.tilastokeskus.daboia.core.BoardConstant;

/**
 * An immutable wrapper for the internal representation of the game's situation.
 */
public final class Board {

    private final BoardConstant[][] core;
    
    public Board(BoardConstant[][] core) {
        this.core = core;
    }
    
    /**
     * Returns a {@link BoardConstant} representing the data that resides in the
     * coordinates (x, y)
     * <p>
     * Getting data from the board through this method is preferrable over
     * {@link matrix() matrix()}, which returns a copy of the internal matrix
     * and is therefore bound to be much slower.
     * 
     * @param x X-coordinate in the board.
     * @param y Y-coordinate in the board.
     * @return  A {@link BoardConstant} representing the data that resides in
     *          the coordinate.
     */
    public BoardConstant get(int x, int y) {
        return core[y][x];
    }
    
    /**
     * Returns a 2-dimensional matrix representing the current situation of the
     * game. The board is vertically indiced - that is,
     * <pre>board[y][x]</pre>
     * returns the data in the <code>x</code>th column from the <code>y</code>th
     * row.
     * 
     * @return  A 2-dimensional {@link BoardConstant} array representing the
     *          game's current situation.
     */
    public BoardConstant[][] matrix() {
        int h = core.length;
        int w = core[0].length;
        BoardConstant[][] copy = new BoardConstant[h][w];
        for (int i = 0; i < h; i++)
            System.arraycopy(core[i], 0, copy[i], 0, w);
        return copy;
    }
    
}
