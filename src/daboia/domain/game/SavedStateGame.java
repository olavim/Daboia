
package daboia.domain.game;

import daboia.domain.Direction;
import daboia.domain.Player;
import java.util.List;

public class SavedStateGame extends AbstractDaboiaGame {
    
    private final GameState initialState;
    private GameState currentState;
    
    public SavedStateGame(List<Player> players, int width, int height) {
        super(players, width, height);
        this.initialState = new GameState(1, this.getApple());
        this.currentState = this.initialState;
    }
    
    @Override
    public void makeMove(Player player, Direction direction) {        
        super.makeMove(player, direction);
        
        this.currentState.addDirection(player, direction);
        if (this.currentState.numPlayers() >= this.numPlayersAlive) {
            this.startNextState();
        }
    }
    
    /**
     * Sets the game to represent the next state of the recorded states.
     * Usually, the game should be rewinded before traversing through the states.
     * 
     * @return  true if there is a next state, otherwise false.
     */
    public boolean nextState() {        
        for (Player player : getPlayers()) {
            if (this.currentState.getDirection(player) == null) {
                continue;
            }

            Direction direction = this.currentState.getDirection(player);
            super.makeMove(player, direction);
        }
        
        this.currentState = this.currentState.getNext();
        if (this.currentState != null) {
            this.setApple(this.currentState.getApple());
        }
        
        return this.currentState != null;
    }
    
    private void startNextState() {
        GameState next = new GameState(currentState.getId() + 1, this.getApple());
        currentState.setNext(next);
        currentState = next;
    }
    
    public GameState getCurrentGameState() {
        return this.currentState;
    }
    
    public GameState getInitialGameState() {
        return this.initialState;
    }
    
    /**
     * Resets the game and assigns its current state to point to its initial state.
     * The default use is to rewind, then traverse through the states with the
     * {@link #nextState() nextState} method
     */
    public void rewind() {
        super.reset();
        this.currentState = this.initialState;
    }
    
    public boolean isRewinded() {
        return this.initialState == this.currentState;
    }

}
