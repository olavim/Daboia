
package daboia.domain.game;

import daboia.domain.Player;
import java.util.List;

public class GameSettings {
    
    private final List<Player> players;
    private final int width;
    private final int height;
    private final int framerate;
        
    public GameSettings(List<Player> players, int width, int height, int speed) {
        String error = validateSettings(players, width, height, speed);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        
        this.players = players;
        this.width = width;
        this.height = height;
        this.framerate = this.calculateGameSpeed(speed);
    }
    
    private String validateSettings(List<Player> players, int width, int height, int speed) {        
        if (players.isEmpty()) {
            return "No players specified";
        } else if (players.size() > 6) {
            return "Maximum amount of players is 6";
        }
        
        if (width < 7 || height < 7) {
            return "Width and height must be at least 7";
        }
        
        for (Player player : players) {
            if (player.getLogicHandler() == null) {
                return player.getName() + " does not have a logic";
            }
        }
        
        if (speed < 0 || speed > 100) {
            return "Speed must be between 0 and 100";
        }
        
        return null;
    }

    public List<Player> getPlayers() {
        return this.players;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getFramerate() {
        return this.framerate;
    }
    
    private int calculateGameSpeed(int sliderValue) {
        /* log10 needs a value greater than 0 */
        if (sliderValue < 1) {
            sliderValue = 1;
        }
        
        double speed = 100 - 50 * Math.log10(sliderValue);
        return (int) (speed * 5);
    }
    
}
