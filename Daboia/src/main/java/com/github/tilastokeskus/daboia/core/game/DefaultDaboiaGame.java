
package com.github.tilastokeskus.daboia.core.game;

import com.github.tilastokeskus.daboia.core.Direction;
import com.github.tilastokeskus.daboia.core.Player;
import com.github.tilastokeskus.daboia.core.Snake;
import java.util.List;

public class DefaultDaboiaGame extends AbstractDaboiaGame {

    protected int numMovesNotEaten;
    
    public DefaultDaboiaGame(List<Player> players, int width, int height) {
        super(players, width, height);
        this.numMovesNotEaten = 0;
    }
    
    @Override
    public boolean isGameOver() {
        return isInfiniteGame() || super.isGameOver();
    }
    
    @Override
    public void reset() {
        super.reset();
        this.numMovesNotEaten = 0;
    }
    
    @Override
    protected void moveSnake(Snake snake, Direction direction) {
        super.moveSnake(snake, direction);
        this.numMovesNotEaten++;
    }

    @Override
    protected void handleAppleEaten(Player player) {        
        player.getSnake().grow();
        placeNewApple();
        numMovesNotEaten = 0;
    }
    
    protected void placeNewApple() {
        board.randomlyPlaceApple();
    }
    
    /**
     * Check if the game has been running for too long without an apple being
     * eaten.
     * @return   true or false.
     */
    private boolean isInfiniteGame() {
        int area = board.getWidth() * board.getHeight();
        return numMovesNotEaten > numPlayersAlive * area * 3;
    }

}
