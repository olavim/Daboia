package com.github.tilastokeskus.daboia.core.game.ai;
 
import com.github.tilastokeskus.daboia.Main;
import com.github.tilastokeskus.daboia.core.BoardConstant;
import com.github.tilastokeskus.daboia.core.Piece;
import com.github.tilastokeskus.daboia.core.Player;
import com.github.tilastokeskus.daboia.core.game.DaboiaGame;
import com.github.tilastokeskus.daboia.core.game.GameSettings;
import com.github.tilastokeskus.daboia.util.ClassUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DaboiaLogic implements Cloneable {
    
    protected static void launchPreview(Class<? extends DaboiaLogic> clazz,
                                        int width, int height, int speed,
                                        boolean savePreview) {
        DaboiaLogic logicHandler = ClassUtils.getClassInstance(clazz);
        
        Player player = new Player(1, 1, 0, "Player");
        player.setLogicHandler(logicHandler);
        
        GameSettings settings = new GameSettings(Arrays.asList(player), width, height, speed);
        
        Main.launchPreview(settings, savePreview);
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
    
    /**
     * Returns the player this logic handler is controlling.
     * @return A Player that is controlled by this handler.
     */
    public final Player getPlayer() {
        return this.player.copy();
    }
    
    /**
     * Returns a list of all the alive players.
     * @return A List containing all the alive players.
     */
    public final List<Player> getPlayers() {
        return daboiaGame.getPlayers().stream()
                .filter(p -> p.isAlive())
                .map(p -> p.copy())
                .collect(Collectors.toList());
    }
    
    /**
     * Returns a Piece object that is representing the apple currently on the
     * game area.
     * @return  A Piece object representing the current apple.
     */
    public final Piece getApple() {
        return daboiaGame.getApple();
    }    
    
    /**
     * Returns a 2-dimensional matrix representing the current situation of the
     * game. The board is vertically indiced - that is,
     * <pre>board[y][x]</pre>
     * returns the <code>x</code>th column from the <code>y</code>th row.
     * 
     * @return  A 2-dimensional {@link BoardConstant} representing the game's
     *          current situation.
     */
    public Board getGameBoard() {
        return new Board(daboiaGame.getBoard());
    }
    
    /**
     * Returns the number of cells the game area has horizontally.
     * @return Width of the game area - number of horizontal cells.
     */
    public final int getWidth() {
        return daboiaGame.getWidth();
    }
    
    /**
     * Returns the number of cells the game area has vertically.
     * @return Height of the game area - number of vertical cells.
     */
    public final int getHeight() {
        return daboiaGame.getHeight();
    }
    
    public abstract void onLaunch(); 
    public abstract String getMove();
   
}