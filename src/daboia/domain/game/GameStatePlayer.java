
package daboia.domain.game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

public class GameStatePlayer extends WindowedGameHandler {
    
    private static final Logger logger = Logger.getLogger( GameStatePlayer.class.getName() );

    private ScheduledExecutorService moveSchedule;
    
    public GameStatePlayer(SavedStateGame game) {
        super(game);
        game.enableApples(false);
        this.moveSchedule = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void startGame(int refreshrate) {
        SavedStateGame game = (SavedStateGame) this.getRegisteredGame();
        showWindow();
        
        Runnable moveCmd = () -> {
            try {
                if (game.nextState()) {
                    refreshWindow();
                } else {
                    stopGame(2000);
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Exception while replaying game:", ex);
                stopGame(2000);
            }
        };
        
        moveSchedule.scheduleAtFixedRate(moveCmd, 1000, refreshrate, TimeUnit.MILLISECONDS);
    }
    
    /**
     * Stops the running game, and disposes the game window, if any.
     * @param delay  Milliseconds to wait before disposing the game window, if one exists.
     */
    @Override
    public void stopGame(int delay) {
        this.moveSchedule.shutdown();
        
        /* Set a timer to close the game window after specified delay */
        Timer timer = new Timer(delay, (e) -> { getWindow().closeWindow(); });
        
        /* fire only once */
        timer.setRepeats(false);
        timer.start();
    }

}
