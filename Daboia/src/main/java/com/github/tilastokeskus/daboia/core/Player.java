
package com.github.tilastokeskus.daboia.core;

import com.github.tilastokeskus.daboia.core.game.ai.DaboiaLogic;
import com.github.tilastokeskus.daboia.util.ColorFactory;
import java.awt.Color;
import java.util.Objects;

public class Player implements java.io.Serializable {
    
    private static final long serialVersionUID = 2015_03_10_18_17L;
    
    private final int id;    
    private final String name;
    private final int initialX;
    private final int initialY;
    
    private transient DaboiaLogic logicHandler;
    
    private Snake snake;
    private Color snakeColor;
    private boolean isAlive;
    private boolean shouldBeDrawn;
    
    public Player(int initialX, int initialY, int id, String name) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.id = id;
        this.name = name;
        this.snake = new Snake(initialX, initialY);
        
        this.snakeColor = ColorFactory.getNextColor();
        this.isAlive = true;
        this.shouldBeDrawn = true;
    }
    
    /**
     * Resets all fields to what they were when an instance of this class was
     * constructed, with the exception of the snake's color and a possibly set
     * {@link DaboiaLogic logic handler}, which are not touched.
     * @see DaboiaLogic
     */
    public void reset() {
        this.snake = new Snake(initialX, initialY);
        this.shouldBeDrawn = true;
        this.isAlive = true;
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
    
    public DaboiaLogic getLogicHandler() {
        return this.logicHandler;
    }
    
    public int getID() {
        return this.id;
    }
    
    public Snake getSnake() {
        return this.snake;
    }
    
    public void setSnake(Snake snake) {
        this.snake = snake;
    }
    
    public boolean isAlive() {
        return this.isAlive;
    }
    
    public void setIsAlive(boolean alive) {
        this.isAlive = alive;
    }
    
    public void setShouldBeDrawn(boolean draw) {
        this.shouldBeDrawn = draw;
    }
    
    public boolean getShouldBeDrawn() {
        return this.shouldBeDrawn;
    }
    
    public Player copy() {
        Player p = new Player(initialX, initialY, id, name);
        p.setIsAlive(isAlive);
        p.setShouldBeDrawn(shouldBeDrawn);
        p.setSnakeColor(snakeColor);
        p.setSnake(snake.copy());
        
        if (this.logicHandler != null)
            p.setLogicHandler(logicHandler.clone());
        
        return p;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        final Player other = (Player) obj;
        if (id != other.id
                || !Objects.equals(name, other.name)
                || !Objects.equals(logicHandler, other.logicHandler)
                || !Objects.equals(snake, other.snake)
                || !Objects.equals(snakeColor, other.snakeColor)
                || initialX != other.initialX
                || initialY != other.initialY
                || isAlive != other.isAlive
                || shouldBeDrawn != other.shouldBeDrawn)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + this.initialX;
        hash = 53 * hash + this.initialY;
        hash = 53 * hash + Objects.hashCode(this.logicHandler);
        hash = 53 * hash + Objects.hashCode(this.snake);
        hash = 53 * hash + Objects.hashCode(this.snakeColor);
        hash = 53 * hash + (this.isAlive ? 1 : 0);
        hash = 53 * hash + (this.shouldBeDrawn ? 1 : 0);
        return hash;
    }

}
