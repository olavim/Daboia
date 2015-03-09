
package com.github.tilastokeskus.daboia.core.game;

import com.github.tilastokeskus.daboia.util.Command;

public class GameHandlerController {
    
    private final GameHandler<SavedStateGame> gameHandler;
    
    public GameHandlerController(GameHandler<SavedStateGame> gameHandler) {
        this.gameHandler = gameHandler;
    }
    
    public void play() { play(() -> {}); }
    public void play(Command cmd) {
        gameHandler.setPaused(false);
        cmd.execute();
    }
    
    public void pause() { pause(() -> {}); }
    public void pause(Command cmd) {
        gameHandler.setPaused(true);
        cmd.execute();
    }
    
    public void rewind(int to) { rewind(to, () -> {}); }
    public void rewind(int to, Command cmd) {
        SavedStateGame game = gameHandler.game;
        while (to < game.getCurrentState().getId()) {
            game.previousState();
            cmd.execute();
        }
    }
    
    public void fastForward(int to) { fastForward(to, () -> {}); }
    public void fastForward(int to, Command cmd) {
        SavedStateGame game = gameHandler.game;
        while (to > game.getCurrentState().getId()) {
            game.nextState();
            cmd.execute();
        }
    }

}
