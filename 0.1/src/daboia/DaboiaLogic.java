package daboia;
 
import daboia.domain.DaboiaGame;
import daboia.domain.Player;
import daboia.util.ClassUtils;
import java.util.Arrays;

public abstract class DaboiaLogic {    
    protected static final int APPLE     = 8;
    protected static final int FLOOR     = 0;
    protected static final int OBSTACLE  = -1;
    protected static final int UNDEFINED = -2;
    
    protected static <E extends DaboiaLogic> void launchPreview(Class<E> clazz, int speed, int width, int height) 
            throws InstantiationException, IllegalAccessException {
        
        E logicHandler = ClassUtils.getClassInstance(clazz);
        Player player = new Player(1, 1, 0, "Player");
        player.setLogicHandler(logicHandler);
        Main.launchPreview(Arrays.asList(player), speed, width, height);
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
    
    public void setDaboiaGame(DaboiaGame daboiaGame) {
        this.daboiaGame = daboiaGame;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public void sendKey(char c) {
        this.lastKeyTyped = c;
    }
    
    public String title() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    protected DaboiaGame daboiaGame() {
        return this.daboiaGame;
    }
    
    protected Player player() {
        return this.player;
    }
    
    protected char lastKeyTyped() {
        return this.lastKeyTyped;
    }
    
    public abstract void onLaunch(); 
    public abstract String getMove();
    
    @Override
    public String toString() {
        return this.title;
    }
   
}