package com.github.tilastokeskus.daboia;

import com.github.tilastokeskus.daboia.config.ConfigurationManager;
import com.github.tilastokeskus.daboia.core.game.GamePreloader;
import com.github.tilastokeskus.daboia.core.game.GameSettings;
import com.github.tilastokeskus.daboia.core.Player;
import com.github.tilastokeskus.daboia.core.game.GameHandler;
import com.github.tilastokeskus.daboia.core.game.GameStatePlayer;
import com.github.tilastokeskus.daboia.core.game.SavedStateGame;
import com.github.tilastokeskus.daboia.ui.MainWindow;
import com.github.tilastokeskus.daboia.plugin.PluginManager;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {    
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    private static MainWindow mainInterface;
    
    public static void main(String[] args) throws IOException {
        ResourceManager.exportConfigIfNeeded();
        ResourceManager.exportLogicsIfNeeded();
        
        try {
            ConfigurationManager.loadConfigs();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        
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
            SavedStateGame game = new SavedStateGame(players, width, height);
            GamePreloader preloader = new GamePreloader(game);
            preloader.preload();
            
            GameHandler handler = new GameStatePlayer(game);
            
            /* framerate must be at least 1 */
            handler.startGame(refreshrate + 1);
        } catch (IllegalArgumentException ex) {
            System.err.println("Could not launch game: " + ex.getMessage());
            System.err.println("Terminating...");
        }
    }
    
}
