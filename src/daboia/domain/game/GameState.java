
package daboia.domain.game;

import daboia.domain.Direction;
import daboia.domain.Piece;
import daboia.domain.Player;
import java.util.HashMap;
import java.util.Map;

public class GameState {
    
    private final int id;
    private final Map<Player, Direction> directions;
    private final Piece apple;
    
    private GameState next;

    public GameState(int id, Piece apple) {
        this.id = id;
        this.directions = new HashMap<>();
        this.apple = apple;
        this.next = null;
    }

    public void addDirection(Player player, Direction direction) {
        this.directions.put(player, direction);
    }

    public Direction getDirection(Player player) {
        return this.directions.get(player);
    }
    
    public int numPlayers() {
        return this.directions.size();
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
               "  apple: " + this.apple + "\n" + 
               "  players: " + this.directions.keySet();
    }

}