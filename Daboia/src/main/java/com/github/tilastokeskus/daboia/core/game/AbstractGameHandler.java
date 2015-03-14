
package com.github.tilastokeskus.daboia.core.game;

import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractGameHandler<T extends DaboiaGame>
        extends Observable implements GameHandler {
    
    private static final Logger LOGGER = Logger.getLogger(GameStatePlayer.class.getName());
    
    /* time to wait before starting game */
    private static final int START_DELAY = 1000;
    
    protected final T game;

    private final ScheduledExecutorService moveSchedule;
    
    private boolean isPaused;
    
    public AbstractGameHandler(T game) {
        this.game = game;
        this.moveSchedule = Executors.newSingleThreadScheduledExecutor();
        this.isPaused = false;
    }
    
    @Override
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
                LOGGER.log(Level.SEVERE, "Exception while replaying game:", ex);
                this.stopGame();
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
    
    @Override
    public void stopGame() {
        this.moveSchedule.shutdown();
    }
    
    @Override
    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }
    
    @Override
    public boolean isPaused() {
        return this.isPaused;
    }
    
    protected abstract void playRound();
    
}
