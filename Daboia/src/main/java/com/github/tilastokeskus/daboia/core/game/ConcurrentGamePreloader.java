
package com.github.tilastokeskus.daboia.core.game;

/**
 * Provides a threaded alternative to {@link GamePreloader}.
 */
public class ConcurrentGamePreloader extends GamePreloader {

    public ConcurrentGamePreloader(SavedStateGame game) {
        super(game);
    }
    
    /**
     * {@inheritDoc GamePreloader}
     * <p>
     * The preloading is done in a separate thread.
     */
    @Override
    public void preload() {
        this.preload(() -> {});
    }
    
    /**
     * {@inheritDoc GamePreloader}
     * <p>
     * The preloading is done in a separate thread.
     * <p>
     * After preloading has finished, the provided callback runnable will be
     * executed.
     * 
     * @param callback A function to fire after preloading has finished.
     */
    public void preload(Runnable callback) {
        new Thread(() -> {
            super.preload();
            callback.run();
        }).start();
    }

}
