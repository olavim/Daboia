
package daboia.domain.game;

import daboia.domain.Piece;
import daboia.domain.Player;
import daboia.domain.Snake;
import java.util.HashMap;
import java.util.Map;

public class GameState {
    
    private final int id;
    private final Map<Player, Snake> snakes;
    private final Map<Player, Boolean> aliveStatuses;
    private final Map<Player, Boolean> shouldDrawStatuses;
    private final Piece apple;
    
    private GameState next;

    public GameState(GameState previous, Piece apple) {
        this.id = (previous == null) ? 1 : previous.id + 1;
        this.apple = apple;
        this.next = null;
        
        if (previous != null) {
            this.snakes = new HashMap<>(previous.snakes);
            this.aliveStatuses = new HashMap<>(previous.aliveStatuses);
            this.shouldDrawStatuses = new HashMap<>(previous.shouldDrawStatuses);
        } else {
            this.snakes = new HashMap<>();
            this.aliveStatuses = new HashMap<>();
            this.shouldDrawStatuses = new HashMap<>();
        }
    }

    public void setSnake(Player player, Snake snake) {
        this.snakes.put(player, snake);
    }

    public Snake getSnake(Player player) {
        return this.snakes.get(player);
    }

    public void setAliveStatus(Player player, boolean alive) {
        this.aliveStatuses.put(player, alive);
    }

    public boolean getAliveStatus(Player player) {
        return this.aliveStatuses.get(player);
    }

    public void setShouldDrawStatus(Player player, boolean shouldDraw) {
        this.shouldDrawStatuses.put(player, shouldDraw);
    }

    public boolean getShouldDrawStatus(Player player) {
        return this.shouldDrawStatuses.get(player);
    }
    
    public int numPlayers() {
        return this.snakes.size();
    }

    public Piece getApple() {
        return this.apple;
    }

    public GameState getNext() {
        return this.next;
    }

    public void setNext(GameState state) {
        this.next = state;
    }
    
    public int getId() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return "id: " + this.id + "\n" + 
               "  apple: " + this.apple;
    }

}