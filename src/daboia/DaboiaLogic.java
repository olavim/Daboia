package daboia;
 
import daboia.domain.DaboiaGame;
import daboia.domain.GameSettings;
import daboia.domain.Player;
import daboia.util.ClassUtils;
import java.util.Arrays;

public abstract class DaboiaLogic implements Cloneable {    
    protected static final int APPLE     = 8;
    protected static final int FLOOR     = 0;
    protected static final int OBSTACLE  = -1;
    protected static final int UNDEFINED = -2;
    
    protected static <E extends DaboiaLogic> void launchPreview(Class<E> clazz, int speed, int width, int height) 
            throws InstantiationException, IllegalAccessException {
        
        E logicHandler = ClassUtils.getClassInstance(clazz);
        Player player = new Player(1, 1, 0, "Player");
        player.setLogicHandler(logicHandler);
        GameSettings settings = new GameSettings(Arrays.asList(player), speed, width, height);
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