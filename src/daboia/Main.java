package daboia;

import daboia.config.ConfigurationManager;
import daboia.domain.game.GamePreloader;
import daboia.domain.game.GameSettings;
import daboia.domain.Player;
import daboia.domain.game.GameHandler;
import daboia.domain.game.GameStatePlayer;
import daboia.domain.game.SavedStateGame;
import daboia.ui.MainWindow;
import daboia.plugin.PluginManager;
import java.io.IOException;
import java.util.List;

public class Main {
    
    private static MainWindow mainInterface;
    
    public static void main(String[] args) throws IOException {
        ConfigurationManager.loadConfigs();        
        PluginManager.loadPlugins();
        showMainInterface();
    }
    
    public static MainWindow getMainInterface() {
        return mainInterface;
    }
    
    private static void showMainInterface() {
        mainInterface = new MainWindow();
        mainInterface.showWindow();
    }
    
    public static void launchPreview(GameSettings settings) {
        List<Player> players = settings.getPlayers();
        int width = settings.getWidth();
        int height = settings.getHeight();
        int refreshrate = settings.getFramerate();
        
        try {
            SavedStateGame game = GamePreloader.preload(players, width, height);
            GameHandler handler = new GameStatePlayer(game);
            
            /* framerate must be at least 1 */
            handler.startGame(refreshrate + 1);
        } catch (IllegalArgumentException ex) {
            System.err.println("Could not launch game: " + ex.getMessage());
            System.err.println("Terminating...");
        }
    }
    
}
