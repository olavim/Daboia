
package com.github.tilastokeskus.daboia.core.game;

public class GameStatePlayer extends ControllableWindowedGameHandler {
    
    public GameStatePlayer(SavedStateGame game) {
        super(game);
    }

    @Override
    protected void playRound() {
        
        if (this.game.shouldBeTerminated())
            this.stopGame();
        
        if (this.isPaused()) return;
        
        /* set the game's state to represent the "next" moment */
        boolean nextStateExists = this.game.nextState();
        
        if (!nextStateExists) {
            this.setPaused(true);
        }        
    }

}
