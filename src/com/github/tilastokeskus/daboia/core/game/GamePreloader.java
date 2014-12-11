
package com.github.tilastokeskus.daboia.core.game;

import com.github.tilastokeskus.daboia.core.DaboiaLogic;
import com.github.tilastokeskus.daboia.core.Direction;
import com.github.tilastokeskus.daboia.core.Player;

public final class GamePreloader {
    
    private final SavedStateGame game;
    
    public GamePreloader(SavedStateGame game) {
        this.game = game;
    }
    
    /**
     * Silently plays and records a game to the given SavedStateGame instance's
     * memory, and then {@link SavedStateGame#terminate() terminates} and
     * {@link SavedStateGame#rewind() rewinds} it.
     */
    public void preload() {
        if (!game.isRewinded())
            throw new IllegalStateException("Instance of SavedStateGame must be untouched or rewinded");
        
        initPlayerLogics(); /* tell all logic handlers to initialize */
        playGame();             /* do the actual preloading */
        game.terminate();       /* add a termination at the end of the game */
        game.rewind();          /* rewind the game back to the beginning */
    }
    
    public double getProgress() {
        int numPlayers = game.numPlayers();
        int numPlayersAlive = game.numPlayersAlive();
        
        if (numPlayers == 1)
            return 0;
        
        return 1 - (double) numPlayersAlive / (double) (numPlayers - 1);
    }

    private void playGame() {
        while (!game.isGameOver()) {
            playRound();
            game.startNextState();
        }
    }

    private void playRound() {
        for (Player player : game.getPlayers()) {
            if (!player.isAlive()) continue;
            if (game.isGameOver()) break;            
            game.makeMove(player, getDirection(player));
        }
    }

    private Direction getDirection(Player player) {
        String direction = player.getLogicHandler().getMove();
        return Direction.directionFromString(direction);
    }
    
    private void initPlayerLogics() {
        for (Player player : game.getPlayers()) {
            DaboiaLogic logicHandler = player.getLogicHandler();
            logicHandler.setDaboiaGame(game);
            logicHandler.init();
        }
    }
}
