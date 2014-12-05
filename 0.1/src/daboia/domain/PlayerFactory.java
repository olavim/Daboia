
package daboia.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

public final class PlayerFactory {

    public static Collection<Player> generatePlayers(int amount, int boardWidth, int boardHeight) {
        if (amount < 1) {
            throw new IllegalArgumentException("There must be a positive amount of players");
        } else if (amount > 6) {
            throw new IllegalArgumentException("There may be at most 6 players");
        }
        
        Collection<Player> players = new ArrayList<>();
        
        for (int i = 0; i < amount; i++) {
            players.add(generatePlayer(i, boardWidth, boardHeight));
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
