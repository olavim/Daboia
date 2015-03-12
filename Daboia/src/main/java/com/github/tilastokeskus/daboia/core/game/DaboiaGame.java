
package com.github.tilastokeskus.daboia.core.game;

import com.github.tilastokeskus.daboia.core.BoardConstant;
import com.github.tilastokeskus.daboia.core.Direction;
import com.github.tilastokeskus.daboia.core.Piece;
import com.github.tilastokeskus.daboia.core.Player;
import java.util.List;

public interface DaboiaGame {
    
    /**
     * Returns the number of players present in the game.
     * @return Number of players in the game.
     */
    int numPlayers();
    
    /**
     * Returns a list of all the players present in the game.
     * @return  List of Players.
     * @see     Player
     */
    List<Player> getPlayers();
    
    /**
     * Returns the number of cells the game area has horizontally.
     * @return Width of the game area - number of horizontal cells.
     */
    int getWidth();
    
    /**
     * Returns the number of cells the game area has vertically.
     * @return Height of the game area - number of vertical cells.
     */
    int getHeight();
    
    void sendKeyInput(char c);
    
    /**
     * Resets the game to its initial state. That is, the state that was in
     * effect after the constructor was called.
     */
    public abstract void reset();
    
    /**
     * Moves the player. By default, implementations of this method should
     * respect the following flow of events:
     * <ol>
     *     <li>Move the player's snake in the specified direction.</li>
     *     <li>Check if the player's snake collides with the apple.</li>
     *     <li>
     *         If so, grow the snake and reposition the apple to a random
     *         unoccupied position.
     *     </li>
     *     <li>
     *         Check if the player collides with itself or any other alive
     *         player, or has gone out of bounds.
     *     </li>
     *     <li>If so, mark the player dead.</li>
     * </ol>
     * @param player     The player who should be moved.
     * @param direction  The direction in which the player should move.
     */
    public abstract void makeMove(Player player, Direction direction);
    
    /**
     * Returns whether the game is over or not. A game should be over if:
     * <ul>
     *     <li>
     *         The game has been running for too long. That is, too many moves
     *         have been done without some rule being fulfilled. The rule that
     *         should be fulfilled depends on the implementation of this class.
     *     </li>
     *     <li>
     *         All cells in the board are occupied. That is, there is a snake or
     *         snakes that cover the entire board.
     *     </li>
     *     <li>There are no alive players left.</li>
     *     <li>
     *         If the game started with two or more players, the game should end
     *         if there is only one snake left.
     *     </li>
     * </ul>
     * Implementations of this class may specify their own set of rules, though.
     * 
     * @return  true if at least one of the above points is fulfilled, otherwise
     *          false.
     */
    public abstract boolean isGameOver();
    
    /**
     * Specify whether or not an apple should be automatically placed in a
     * random unoccupied position after the previous apple has been eaten. If
     * this is set to false, apples may be positioned manually with the
     * {@link #setApple(daboia.domain.Piece) setApple} method.
     * 
     * @param enable  true if apples should be automatically placed, false if
     *                not.
     */
    public abstract void enableApples(boolean enable);
    
    /**
     * Manually set an apple. The position of the apple should be defined by the
     * {@link Piece} object.
     * 
     * @param newApple  A {@link Piece} object representing the apple.
     */
    public abstract void setApple(Piece newApple);
    
    /**
     * Returns the apple that is currently effective on the board.
     * 
     * @return  A {@link Piece} object representing the apple.
     */
    public abstract Piece getApple();
    
    /**
     * Returns a BoardConstant matrix representing the current state of the game.
     * 
     * @return  A 2-dimensional BoardConstant matrix representing the current
     *          state of the game.
     * @see  BoardConstant
     */
    public abstract BoardConstant[][] getBoard();
    
    /**
     * Returns the amount of cells that have nothing in them, that is, the
     * amount of cells where a snake's head, a piece of its body, or an apple
     * is not present.
     * 
     * @return An integer from 0 to the game's area (width * height).
     */
    public abstract int numUnoccupied();
    
    /**
     * Returns the amount of players that have not been killed - players that
     * have not run into an obstacle (itself, another player or the boundaries
     * of the board).
     * 
     * @return An integer from 0 to the amount of players participating in the
     *         game.
     */
    public abstract int numPlayersAlive();
    
    /**
     * Sets a flag suggesting that the game should be terminated as soon as
     * possible. This method should, but doesn't have to, only set a boolean
     * value, which the {@link #shouldBeTerminated() shouldBeTerminated} method
     * should retrieve.
     * <p>
     * All classes using this class should consequently appropriately terminate
     * any games that are requesting termination.
     * 
     * @see shouldBeTerminated
     */
    public abstract void terminateGame();
    
    /**
     * Tells any inquirers whether or not the game should be terminated. The
     * idea is to terminate the game as soon as possible after noticing that
     * this flag has been set.
     * 
     * @return true or false
     * @see terminateGame
     */
    public abstract boolean shouldBeTerminated();
    
}
