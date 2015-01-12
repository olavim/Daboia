
package com.github.tilastokeskus.daboia.core.game;

import com.github.tilastokeskus.daboia.core.Direction;
import com.github.tilastokeskus.daboia.core.Player;
import com.github.tilastokeskus.daboia.core.Snake;
import com.github.tilastokeskus.daboia.util.StateSaver;
import java.util.List;

public class SavedStateGame extends AbstractDaboiaGame implements StateSaver {
    
    private GameState initialState;
    private GameState currentState;
    private GameState lastState;
    
    public SavedStateGame(List<Player> players, int width, int height) {
        super(players, width, height);
        this.initialState = new GameState(null);
        this.currentState = this.initialState;
    }
    
    /**
     * Moves the snake by the rules defined by
     * {@link AbstractDaboiaGame#makeMove(Player, Direction)}, and additionally
     * saves the player's state to memory, which can then later be accessed.
     * <p>
     * The player's state is saved into a {@link GameState} object, which
     * represents a moment in the game. To be more precise, the object holds
     * information of each player's snake, alive status and whether or not it
     * should be drawn at that particular moment.
     * <p>
     * {@link #startNextState() startNextState} should be called after all
     * alive players have moved once, or in other words, after each round.
     */
    @Override
    public void makeMove(Player player, Direction direction) {
        super.makeMove(player, direction);
        
        this.currentState.setAliveStatus(player, player.isAlive());
        this.currentState.setShouldDrawStatus(player, player.getShouldBeDrawn());
        this.currentState.setSnake(player, player.getSnake().copy());
    }
    
    /**
     * Finalizes the current state and sets it to point to a new state that is
     * initially identical to the previous one.
     */
    public void startNextState() {
        this.currentState.setApple(this.getApple());
        GameState next = new GameState(currentState);
        currentState.setNext(next);
        currentState = next;
        this.lastState = currentState;
    }
    
    /**
     * Sets the game to represent the next state of the recorded states.
     * In most cases the game should be rewinded before traversing through the
     * states.
     * <p>
     * To be more specific, this method first places the apple as it appears in
     * the current state's memory. Then it syncs all the players' states with
     * the current state of the game by calling the
     * {@link #recallPlayerStates() recallPlayerStates} method. Lastly, the
     * current state is set to point to the next state of the game. If this next
     * state is null, this method returns false, and otherwise true.
     * 
     * @return  true if there is a next state, otherwise false.
     */
    @Override
    public boolean nextState() {
        this.setApple(this.currentState.getApple());        
        this.recallPlayerStates();
        
        this.currentState = this.currentState.getNext();
        return this.currentState != null;
    }

    @Override
    public boolean previousState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public int numStates() {
        return this.lastState.getId();
    }
    
    /**
     * Gets the data held by the current GameState object, and sets the states
     * of all the players to be in sync with the current game state.
     * <p>
     * To be more precise, each player's snake, isAlive and shouldBeDrawn states
     * are set to correspond the current game state.
     */
    public void recallPlayerStates() {
        for (Player player : this.getPlayers()) {
            boolean isAlive = this.currentState.getAliveStatus(player);
            boolean shouldDraw = this.currentState.getShouldDrawStatus(player);
            Snake snake = this.currentState.getSnake(player);

            player.setIsAlive(isAlive);
            player.setShouldBeDrawn(shouldDraw);
            player.setSnake(snake);
        }
    }
    
    
    /**
     * Sets the next state to null. This has no practical effect when only one
     * game has ever been saved in this game instance, but should be called
     * after overriding an existing game.
     * <p>
     * This can be seen as recording new material to a VHS-tape with existing
     * material: after watching the new recording, the old one jumps on the
     * screen, unless the new recording was longer in length than the previous
     * one. With this analogy, this method can be seen as a pair of scissors:
     * it cuts the tape where the new recording ends.
     * <p>
     * For example, let's say we have recorded 5 states in an instance of this
     * class: states A, B, C, D and E. Let's say we have rewinded the recording,
     * and so the current state points to state A - the beginning of the
     * recording. Now let's say we start a new recording. This new recording has
     * 3 states: states X, Y and Z. Now the instance of this class would hold
     * the states X, Y, Z, D and E, and the current state would point to the
     * state Z. In this scenario, calling this method would make the state after
     * Z null, that is, D would now be null, and since there is no next state
     * after null, the instance of this class would then hold the states
     * X, Y and Z. The state E is truncated since no state points to it any
     * longer, as well as any states after E, if there were any.
     */
    public void finalizeRecording() {
        this.currentState.setNext(null);
    }
    
    @Override
    public GameState getCurrentState() {
        return this.currentState;
    }
    
    @Override
    public GameState getInitialState() {
        return this.initialState;
    }
    
    /**
     * Resets the game and assigns its current state to point to its initial
     * state. The default use is to rewind, then traverse through the states
     * with the {@link #nextState() nextState} method
     */
    public void rewind() {
        super.reset();
        this.currentState = this.initialState;
    }
    
    /**
     * Checks if the current state is the initial one. That is, the current
     * state is the same as the first recorded state.
     * @return  true if current state is the initial one, otherwise false.
     */
    public boolean isRewinded() {
        return this.currentState == this.initialState;
    }

}
