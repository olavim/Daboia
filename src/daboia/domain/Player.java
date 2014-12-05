
package daboia.domain;

import daboia.DaboiaLogic;
import daboia.util.RandomColor;
import java.awt.Color;
import java.util.Objects;

public class Player {
    
    private final int id;
    
    private String name;
    private Snake snake;
    private DaboiaLogic logicHandler;
    private Color snakeColor;
    private boolean isDead;
    private boolean presenceRemains;
    
    public Player(int initialX, int initialY, int id, String name) {
        this.snake = new Snake(initialX, initialY);
        this.id = id;        
        this.isDead = false;
        this.presenceRemains = true;
        this.snakeColor = new RandomColor(Color.WHITE).nextColor();
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setSnakeColor(Color color) {
        this.snakeColor = color;
    }
    
    public Color getSnakeColor() {
        return this.snakeColor;
    }
    
    public void setLogicHandler(DaboiaLogic logicHandler) {
        this.logicHandler = logicHandler;
        this.logicHandler.setPlayer(this);
    }
    
    public void setPosition(int x, int y) {
        
        /* Setting position not allowed after game has started */
        if (this.snake.trueLength() > 1) {
        
            /* The snake's true length (the amount of pieces it consists of)
             * can never be 1 or less if it has moved at least once.
             */
            throw new IllegalStateException("Cannot modify player position while game is running");
        }
        
        this.snake = new Snake(x, y);
    }
    
    public DaboiaLogic getLogicHandler() {
        return this.logicHandler;
    }
    
    public int getID() {
        return this.id;
    }
    
    public Snake snake() {
        return this.snake;
    }
    
    public boolean isDead() {
        return this.isDead;
    }
    
    public boolean presenceRemains() {
        return this.presenceRemains;
    }
    
    public void exorcise() {
        this.presenceRemains = false;
    }
    
    public void kill() {
        this.isDead = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.name);
        return hash;
    }

}
