
package com.github.tilastokeskus.daboia.core.game;

import com.github.tilastokeskus.daboia.core.Board;
import com.github.tilastokeskus.daboia.core.BoardConstant;
import com.github.tilastokeskus.daboia.core.Direction;
import com.github.tilastokeskus.daboia.core.Piece;
import com.github.tilastokeskus.daboia.core.Player;
import com.github.tilastokeskus.daboia.core.Snake;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDaboiaGame implements DaboiaGame, java.io.Serializable {
    
    protected final List<Player> players;
    protected int width;
    protected int height;
    
    protected int numPlayersAlive;
    protected int numMovesNotEaten;
    protected Board board;
    
    private boolean placeApples;
    private boolean gameShouldEnd;
    
    public AbstractDaboiaGame(List<Player> players, int width, int height) {
        this.players = players;
        this.width = width;
        this.height = height;
        this.numPlayersAlive = players.size();
        this.numMovesNotEaten = 0;
        this.board = new Board(width, height);
        this.placeApples = true;
        this.gameShouldEnd = false;
    }
    
    @Override
    public int numPlayers() {
        return this.players.size();
    }
    
    @Override
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public void sendKeyInput(char c) {
        for (Player player : this.players) {
            player.getLogicHandler().sendKey(c);
        }
    }
    
    @Override
    public void reset() {
        this.numPlayersAlive = players.size();
        this.numMovesNotEaten = 0;
        this.board = new Board(width, height);        
        players.forEach(Player::reset);
    }
    
    @Override
    public void enableApples(boolean enable) {
        this.placeApples = enable;
    }
    
    @Override
    public void setApple(Piece newApple) {
        this.board.setApple(newApple);
    }
    
    @Override
    public Piece getApple() {
        return this.board.getApple();
    }
    
    @Override
    public BoardConstant[][] getBoard() {
        return this.board.getCore();
    }
    
    @Override
    public void makeMove(Player player, Direction direction) {
        moveSnake(player.getSnake(), direction);
        checkIfAppleEaten(player.getSnake());
        
        if (playerShouldDie(player))
            handlePlayerDeath(player);
    }
    
    @Override
    public boolean isGameOver() {
        return isInfiniteGame()
               || board.numUnoccupied() == 0
               || numPlayersAlive == 0
               || (numPlayers() > 1 && numPlayersAlive < 2);
    }
    
    @Override
    public int numUnoccupied() {
        return this.board.numUnoccupied();
    }
    
    @Override
    public int numPlayersAlive() {
        return this.numPlayersAlive;
    }
    
    @Override
    public void terminateGame() {
        this.gameShouldEnd = true;
    }
    
    @Override
    public boolean shouldBeTerminated() {
        return this.gameShouldEnd;
    }
    
    /**
     * Check if the game has been running for too long without an apple being eaten.
     * @return   true or false.
     */
    private boolean isInfiniteGame() {
        int area = this.board.getWidth() * this.board.getHeight();
        return this.numMovesNotEaten > this.numPlayersAlive * area * 3;
    }
    
    private boolean playerShouldDie(Player player) {        
        return playerIsOutOfBounds(player) || playerCollidesWithSomeone(player);
    }
    
    private boolean playerIsOutOfBounds(Player player) {
        int x = player.getSnake().getHead().x;
        int y = player.getSnake().getHead().y;
        return x < 0 || x >= getWidth() || y < 0 || y >= getHeight();
    }
    
    private boolean playerCollidesWithSomeone(Player player) {
        Snake playerSnake = player.getSnake();
        
        /* also checks for collision with itself */
        return players.stream()
                .filter(p -> p.isAlive())
                .anyMatch(p -> playerSnake.collidesWith(p.getSnake()));
    }
    
    protected void moveSnake(Snake snake, Direction direction) {
        board.set(snake.getHead().x, snake.getHead().y, BoardConstant.SNAKE_BODY);
        board.set(snake.getTail().x, snake.getTail().y, BoardConstant.FLOOR);
        
        snake.move(direction);
        numMovesNotEaten++;
        
        board.set(snake.getHead().x, snake.getHead().y, BoardConstant.SNAKE_HEAD);
        board.set(snake.getTail().x, snake.getTail().y, BoardConstant.SNAKE_BODY);
    }
    
    protected void checkIfAppleEaten(Snake snake) {
        if (snake.collidesWith(this.getApple())) {
            snake.grow();
            placeApple();
            numMovesNotEaten = 0;
        }
    }
    
    private void placeApple() {
        if (this.placeApples)
            board.randomlyPlaceApple();
    }
    
    protected void handlePlayerDeath(Player player) {
        player.setIsAlive(false);
        numPlayersAlive--;
        
        for (Piece piece : player.getSnake().getPieces())
            board.set(piece.x, piece.y, BoardConstant.FLOOR);
        
        if (numPlayers() > 1 && numPlayersAlive > 1)
            player.setShouldBeDrawn(false);
    }

}
