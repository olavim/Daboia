package daboia;

import daboia.config.ConfigurationManager;
import daboia.domain.GameSettings;
import daboia.domain.Player;
import daboia.ui.MainInterface;
import daboia.plugin.PluginManager;
import java.io.IOException;
import java.util.Collection;

public class Main {
    
    private static MainInterface mainInterface;
    
    public static void main(String[] args) throws IOException {
        ConfigurationManager.loadConfigs();        
        PluginManager.loadPlugins();
        showMainInterface();
    }
    
    public static MainInterface getMainInterface() {
        return mainInterface;
    }
    
    private static void showMainInterface() {
        mainInterface = new MainInterface();
        mainInterface.showWindow();
    }
    
    public static <E extends DaboiaLogic> void launchPreview(GameSettings settings) {
        Collection<Player> players = settings.getPlayers();
        int speed = settings.getFramerate();
        int width = settings.getWidth();
        int height = settings.getHeight();
        
        try {
            GameHandler.setup(players, width, height);
            launchGame(speed + 1);
        } catch (LaunchException ex) {
            System.err.println("Could not launch game: " + ex.getMessage());
            System.err.println("Terminating...");
        }
    }
    
    public static void launchGame(int speed) throws LaunchException {
        GameHandler.launch(speed);
    }
    
}
