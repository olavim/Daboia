
package com.github.tilastokeskus.daboia.core.game;

public class GameStatePlayer extends WindowedGameHandler<SavedStateGame> {
    
    public GameStatePlayer(SavedStateGame game) {
        super(game);
        game.enableApples(false);
    }

    @Override
    protected void playRound() {
        
        /* if the game is requesting termination, terminate it immediately */
        if (this.game.shouldBeTerminated())
            this.stopGame(0);
        
        /* set the game's state to represent the "next" moment */
        boolean nextStateExists = this.game.nextState();
        
        if (!nextStateExists) {
            
            /* if there was no next moment, the game has ended */
            this.stopGame();
        }        
    }

}
