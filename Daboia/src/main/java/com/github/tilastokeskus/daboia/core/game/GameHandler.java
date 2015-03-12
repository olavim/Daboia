
package com.github.tilastokeskus.daboia.core.game;

import com.github.tilastokeskus.daboia.core.Player;
import java.util.Collection;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GameHandler<T extends DaboiaGame> extends Observable {
    
    private static final Logger logger = Logger.getLogger(GameStatePlayer.class.getName());
    
    /* time to wait before starting game */
    private static final int START_DELAY = 1000;
    
    /* time to wait before stopping the game */
    private static final int STOP_DELAY = 1000;
    
    protected final T game;

    private final ScheduledExecutorService moveSchedule;
    
    private boolean isPaused;
    
    public GameHandler(T game) {
        this.game = game;
        this.moveSchedule = Executors.newSingleThreadScheduledExecutor();
        this.isPaused = false;
    }
    
    public Collection<Player> getRegisteredPlayers() {
        return game.getPlayers();
    }
    
    public int getRegisteredWidth() {
        return game.getWidth();
    }
    
    public int getRegisteredHeight() {
        return game.getHeight();
    }
    
    public T getRegisteredGame() {
        return this.game;
    }
    
    public void startGame(int refreshrate) {
        Runnable roundCmd = () -> {
            
            /* Runnable swallows exceptions, thus we wrap the function in a
             * try-catch
             */
            try {
                this.playRound();
            
                /* notify possible observers that a change has occurred */
                this.setChanged();
                this.notifyObservers();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Exception while replaying game:", ex);
                this.stopGame(STOP_DELAY);
            }
        };
        
        /* schedule roundCmd to be executed every <refreshrate> milliseconds,
         * after <START_DELAY> milliseconds
         */
        moveSchedule.scheduleAtFixedRate(roundCmd,
                                         START_DELAY,
                                         refreshrate,
                                         TimeUnit.MILLISECONDS);
    }
    
    /**
     * Stops the running game, and disposes the game window, if any.
     * @param delay  Milliseconds to wait before disposing the game window,
     * if one exists.
     */
    public void stopGame(int delay) {
        this.moveSchedule.shutdown();
    }
    
    public void stopGame() {
        this.stopGame(STOP_DELAY);
    }
    
    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }
    
    public boolean isPaused() {
        return this.isPaused;
    }
    
    protected abstract void playRound();
    
}
