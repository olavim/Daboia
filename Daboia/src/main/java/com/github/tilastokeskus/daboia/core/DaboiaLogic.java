package com.github.tilastokeskus.daboia.core;
 
import com.github.tilastokeskus.daboia.Main;
import com.github.tilastokeskus.daboia.core.game.DaboiaGame;
import com.github.tilastokeskus.daboia.core.game.GameSettings;
import com.github.tilastokeskus.daboia.util.ClassUtils;
import java.util.Arrays;

public abstract class DaboiaLogic implements Cloneable {
    
    protected static void launchPreview(Class<? extends DaboiaLogic> clazz,
                                        int width, int height, int speed) {
        DaboiaLogic logicHandler = ClassUtils.getClassInstance(clazz);
        
        Player player = new Player(1, 1, 0, "Player");
        player.setLogicHandler(logicHandler);
        
        GameSettings settings = new GameSettings(Arrays.asList(player), width, height, speed);
        
        Main.launchPreview(settings);
    }
    
    private DaboiaGame daboiaGame;
    private Player player;
    
    private char lastKeyTyped;
    private String title;
    
    public DaboiaLogic() {
        this.lastKeyTyped = 'w';
    }
    
    public final void init() {
        if (this.player == null) {
            throw new IllegalStateException("Player not set");
        } else if (this.daboiaGame == null) {
            throw new IllegalStateException("DaboiaGame not set");
        }
        
        this.onLaunch();
    }
    
    public final void setDaboiaGame(DaboiaGame daboiaGame) {
        this.daboiaGame = daboiaGame;
    }
    
    public final void setPlayer(Player player) {
        this.player = player;
    }
    
    public final void sendKey(char c) {
        this.lastKeyTyped = c;
    }
    
    public final String getTitle() {
        return this.title;
    }
    
    public final void setTitle(String title) {
        this.title = title;
    }
    
    public final DaboiaGame getGameInstance() {
        return this.daboiaGame;
    }
    
    public final Player getPlayer() {
        return this.player;
    }
    
    public final char getLastKeyTyped() {
        return this.lastKeyTyped;
    }
    
    @Override
    public final DaboiaLogic clone() {
        try {
            return (DaboiaLogic) super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    @Override
    public final String toString() {
        return this.title;
    }
    
    public abstract void onLaunch(); 
    public abstract String getMove();
   
}