
package com.github.tilastokeskus.daboia.core.game;

import com.github.tilastokeskus.daboia.core.Piece;
import com.github.tilastokeskus.daboia.core.Player;
import java.util.HashMap;
import java.util.Map;

public class GameState implements java.io.Serializable {
    
    private static final long serialVersionUID = 2015_03_10_19_42L;
    
    private final int id;
    private final Map<String, Map<Player, Object>> map;
    
    private transient GameState next;
    private transient GameState previous;
    
    private Piece apple;
    
    public GameState(int id) {
        this(id, null);
    }

    public GameState(int id, GameState state) {
        this.id = id;
        this.previous = null;
        this.next = null;
        
        this.map = new HashMap<>();
        
        if (state != null) {
            map.put("snake", new HashMap<>(state.map.get("snake")));
            map.put("alive", new HashMap<>(state.map.get("alive")));
            map.put("shouldDraw", new HashMap<>(state.map.get("shouldDraw")));
        } else {
            map.put("snake", new HashMap<>());
            map.put("alive", new HashMap<>());
            map.put("shouldDraw", new HashMap<>());
        }
    }
    
    public void setApple(Piece apple) {
        this.apple = apple;
    }

    public void set(String key, Player player, Object obj) {
        if (!map.containsKey(key))
            throw new IllegalArgumentException();
        map.get(key).put(player, obj);
    }
    
    public Object getValue(String key, Player player) {
        if (!map.containsKey(key))
            throw new IllegalArgumentException();
        if (!map.get(key).containsKey(player))
            throw new IllegalArgumentException();
        return map.get(key).get(player);
    }
    
    public int numPlayers() {
        return map.get("snake").size();
    }

    public Piece getApple() {
        return this.apple;
    }

    public GameState getNext() {
        return this.next;
    }

    public GameState getPrevious() {
        return this.previous;
    }

    public void setNext(GameState state) {
        this.next = state;
    }

    public void setPrevious(GameState state) {
        this.previous = state;
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