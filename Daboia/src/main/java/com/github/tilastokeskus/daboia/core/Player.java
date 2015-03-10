
package com.github.tilastokeskus.daboia.core;

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
        this.snake = new Snake(initialX, initialY);
        this.id = id;
        this.initialX = initialX;
        this.initialY = initialY;
        this.isAlive = true;
        this.snakeColor = ColorFactory.getNextColor();
        this.name = name;
        this.shouldBeDrawn = true;
    }
    
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
    
    public void setPosition(int x, int y) {
        
        /* Setting position not allowed after game has started */
        if (this.snake.getTrueLength() > 1) {
        
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
    
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        
        if (getClass() != obj.getClass())
            return false;
        
        final Player other = (Player) obj;
        return this.id == other.id && this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.name);
        return hash;
    }

}
