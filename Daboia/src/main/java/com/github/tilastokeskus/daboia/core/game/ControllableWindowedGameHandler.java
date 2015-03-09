
package com.github.tilastokeskus.daboia.core.game;

import com.github.tilastokeskus.daboia.ui.GameWindow;

public abstract class ControllableWindowedGameHandler extends WindowedGameHandler {

    public ControllableWindowedGameHandler(SavedStateGame game) {
        super(game, new GameWindow(game));
    }
    
    public void setController(GameHandlerController controller) {
        this.window.setController(controller);
    }

}
