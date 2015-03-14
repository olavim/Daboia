package com.github.tilastokeskus.daboia.core.game;

public interface GameHandler {
    
    /**
     * Starts the game with the given refreshrate.
     * @param refreshrate Milliseconds between each refresh.
     */
    void startGame(int refreshrate);
    
    /**
     * Stops and shuts down the running game.
     */
    void stopGame();
    
    /**
     * Sets the paused state of the game.
     * @param paused True for paused, false for not.
     */
    void setPaused(boolean paused);
    
    boolean isPaused();
}
