
package com.github.tilastokeskus.daboia.core.game;

import com.github.tilastokeskus.daboia.core.Player;
import java.util.Collection;

public abstract class GameHandler {

    private DaboiaGame game;
    
    public GameHandler(DaboiaGame game) {
        this.game = game;
    }
    
    public Collection<Player> getRegisteredPlayers() {
        return game.players;
    }
    
    public int getRegisteredWidth() {
        return game.width;
    }
    
    public int getRegisteredHeight() {
        return game.height;
    }
    
    public DaboiaGame getRegisteredGame() {
        return this.game;
    }
    
    public abstract void startGame(int refreshrate);
    public abstract void stopGame(int delay);
    
}
