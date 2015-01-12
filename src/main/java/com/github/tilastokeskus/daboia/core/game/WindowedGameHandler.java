
package com.github.tilastokeskus.daboia.core.game;

import com.github.tilastokeskus.daboia.ui.GameWindow;
import javax.swing.Timer;

public abstract class WindowedGameHandler<T extends SavedStateGame> extends GameHandler<T> {
    
    private final GameWindow window;

    public WindowedGameHandler(T game) {
        super(game);
        this.window = new GameWindow(game);
        this.addObserver(this.window);
    }
    
    public GameWindow getWindow() {
        return this.window;
    }
    
    public void showWindow() {
        this.window.showWindow();
    }
    
    @Override
    public void startGame(int refreshrate) {
        super.startGame(refreshrate);
        this.showWindow();
    }
    
    @Override
    public void stopGame(int delay) {
        super.stopGame(delay);
        
        /* Set a timer to close the game window after specified delay */
        Timer timer = new Timer(delay, e -> { getWindow().closeWindow(); });
        
        timer.setRepeats(false); /* set to fire only once */
        timer.start();
    }
    
}
