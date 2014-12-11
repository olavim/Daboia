
package com.github.tilastokeskus.daboia.core.game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

public class GameStatePlayer extends WindowedGameHandler {
    
    private static final Logger logger = Logger.getLogger(GameStatePlayer.class.getName());
    
    /* time to wait before starting game */
    private static final int START_DELAY = 1000;
    
    /* time to wait before disposing game window */
    private static final int DISPOSE_DELAY = 1000;

    private final ScheduledExecutorService moveSchedule;
    
    public GameStatePlayer(SavedStateGame game) {
        super(game);
        game.enableApples(false);
        this.moveSchedule = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void startGame(int refreshrate) {
        SavedStateGame game = (SavedStateGame) this.getRegisteredGame();
        showWindow();
        
        Runnable roundCmd = () -> {
            
            /* Runnable swallows all exceptions, thus we wrap the function
             * in a try-catch
             */
            try {
                this.playRound(game);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Exception while replaying game:", ex);
                this.stopGame(DISPOSE_DELAY);
            }
        };
        
        /* schedule roundCmd to be executed every <refreshrate> milliseconds,
         * after <START_DELAY> milliseconds
         */
        moveSchedule.scheduleAtFixedRate(roundCmd,
                                         START_DELAY,
                                         refreshrate,TimeUnit.MILLISECONDS);
    }

    private void playRound(SavedStateGame game) {
        
        /* set the game's state to represent the "next" moment */
        if (game.nextState()) {
            
            /* refresh the window to show the current state */
            refreshWindow();
        } else {
            
            /* if there was no next moment, the game has ended */
            this.stopGame(DISPOSE_DELAY);
        }        
    }
    
    /**
     * Stops the running game, and disposes the game window, if any.
     * @param delay  Milliseconds to wait before disposing the game window,
     * if one exists.
     */
    @Override
    public void stopGame(int delay) {
        this.moveSchedule.shutdown();
        
        /* Set a timer to close the game window after specified delay */
        Timer timer = new Timer(delay, (e) -> { getWindow().closeWindow(); });
        
        timer.setRepeats(false); /* set to fire only once */
        timer.start();
    }

}
