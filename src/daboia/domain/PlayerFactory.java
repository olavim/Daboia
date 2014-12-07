
package daboia.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class PlayerFactory {

    public static List<Player> generatePlayers(List<Player> templatePlayers, int boardWidth, int boardHeight) {
        int amount = templatePlayers.size();
        if (amount < 1) {
            throw new IllegalArgumentException("There must be a positive amount of players");
        } else if (amount > 6) {
            throw new IllegalArgumentException("There may be at most 6 players");
        }
        
        List<Player> players = new ArrayList<>();
        
        for (int i = 0; i < amount; i++) {
            Player player = generatePlayer(i, boardWidth, boardHeight);
            if (templatePlayers.get(i).getLogicHandler() == null) {
                throw new IllegalArgumentException(player.getName() + " does not have a logic");
            }
            
            player.setLogicHandler(templatePlayers.get(i).getLogicHandler());
            player.setSnakeColor(templatePlayers.get(i).getSnakeColor());
            players.add(player);
        }
        
        return players;
    }
    
    public static Player generatePlayer(int id, int boardWidth, int boardHeight) {
        Point pos = determinePlayerPosition(id, boardWidth, boardHeight);
        return new Player(pos.x, pos.y, id, "Player " + (id + 1));
    }
    
    private static Point determinePlayerPosition(int id, int boardWidth, int boardHeight) {        
        switch(id) {
            case 0: return new Point(1, 1);
            case 1: return new Point(boardWidth - 2, boardHeight - 2);
            case 2: return new Point(1, boardHeight - 2);
            case 3: return new Point(boardWidth - 2, 1);
            case 4: return new Point(1, boardHeight / 2);
            case 5: return new Point(boardWidth - 2, boardHeight / 2);
            default: return null;
        }
    }
    
}
