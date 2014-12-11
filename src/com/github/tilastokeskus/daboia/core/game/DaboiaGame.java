
package com.github.tilastokeskus.daboia.core.game;

import com.github.tilastokeskus.daboia.core.Direction;
import com.github.tilastokeskus.daboia.core.Piece;
import com.github.tilastokeskus.daboia.core.Player;
import java.util.ArrayList;
import java.util.List;

public abstract class DaboiaGame {
    
    protected final List<Player> players;
    protected int width;
    protected int height;

    public DaboiaGame(List<Player> players, int width, int height) {
        this.players = players;
        this.width = width;
        this.height = height;
    }
    
    public int numPlayers() {
        return this.players.size();
    }
    
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void sendKeyInput(char c) {
        for (Player player : this.players) {
            player.getLogicHandler().sendKey(c);
        }
    }
    
    /**
     * Resets the game to its initial state. That is, the state that is in effect
     * after the constructor has initialized all fields.
     */
    public abstract void reset();
    
    /**
     * Moves the player. By default, implementations of this method should respect
     * the following flow of events:
     * <ol>
     *     <li>Move the player's snake in the specified direction.</li>
     *     <li>Check if the player's snake collides with the apple.</li>
     *     <li>If so, grow the snake and reposition the apple to a random unoccupied position.</li>
     *     <li>Check if the player collides with itself or any other alive player, or has gone out of bounds.</li>
     *     <li>If so, mark the player dead.</li>
     * </ol>
     * @param player     The player who should be moved.
     * @param direction  The direction in which the player should move.
     */
    public abstract void makeMove(Player player, Direction direction);
    
    /**
     * Returns whether the game is over or not. A game should be over if:
     * <ul>
     *     <li>The game has been running for too long. That is, too many moves have been done without some rule being fulfilled.
     *         The rule that should be fulfilled depends on the implementation of this class.</li>
     *     <li>All cells in the board are occupied. That is, there is a snake or snakes that cover the entire board.</li>
     *     <li>There are no alive players left.</li>
     *     <li>If the game started with two or more players, the game should end if there is only one snake left.</li>
     * </ul>
     * Implementations of this class may specify their own set of rules, though.
     * 
     * @return  true if at least one of the above points is fulfilled, otherwise false.
     */
    public abstract boolean isGameOver();
    
    /**
     * Specify whether or not an apple should be automatically placed in a random unoccupied position after the previous apple
     * has been eaten. If this is set to false, apples may be positioned manually with the {@link #setApple(daboia.domain.Piece) setApple} method.
     * @param enable  true if apples should be automatically placed, false if not.
     */
    public abstract void    enableApples(boolean enable);
    
    /**
     * Manually set an apple. The position of the apple should be defined by the {@link Piece} object.
     * @param newApple  A {@link Piece} object representing the apple.
     */
    public abstract void    setApple(Piece newApple);
    
    /**
     * Returns the apple that is currently effective on the board.
     * @return  A {@link Piece} object representing the apple.
     */
    public abstract Piece   getApple();
    
    /**
     * Returns an integer matrix representing the current state of the game. Values in the matrix should obey the values
     * specified in {@link BoardConstants}, though it is not mandatory.
     * <ul>
     *     <li>0 indicates an unoccupied cell.</li>
     *     <li>-1 indicates the body of a snake.</li>
     *     <li>-2 indicates the head of a snake.</li>
     *     <li>8 indicates an apple.</li>
     * </ul>
     * @return  A 2-dimensional integer matrix representing the current state of the game.
     * @see  BoardConstants
     */
    public abstract int[][] getBoard();
    
    public abstract int numUnoccupied();
    public abstract int numPlayersAlive();
    
}
