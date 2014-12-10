
package daboia.domain.game;

import daboia.domain.Direction;
import daboia.domain.Piece;
import daboia.domain.Player;
import daboia.domain.Snake;
import java.util.List;

public abstract class AbstractDaboiaGame extends DaboiaGame {
    
    protected int numPlayersAlive;
    protected int numMovesNotEaten;
    protected Board board;    
    private boolean placeApples;
    
    public AbstractDaboiaGame(List<Player> players, int width, int height) {
        super(players, width, height);
        this.numPlayersAlive = players.size();
        this.numMovesNotEaten = 0;
        this.board = new Board(width, height);
        this.placeApples = true;
    }
    
    @Override
    public void reset() {
        this.numPlayersAlive = players.size();
        this.numMovesNotEaten = 0;
        this.board = new Board(width, height);
        this.placeApples = true;
        
        for (Player player : players) {
            player.reset();
        }
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
    public int[][] getBoard() {
        return this.board.getCore();
    }
    
    @Override
    public void makeMove(Player player, Direction direction) {
        moveSnake(player.getSnake(), direction);
        checkIfAppleEaten(player.getSnake());
        
        if (playerShouldDie(player)) {
            handlePlayerDeath(player);
        }
        
        this.board.refresh(players);
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
    
    /**
     * Check if the game has been running for too long without an apple being eaten.
     * @return   true or false.
     */
    private boolean isInfiniteGame() {
        return this.numMovesNotEaten > this.numPlayersAlive * this.board.getWidth() * this.board.getHeight() * 3;
    }
    
    private boolean playerShouldDie(Player player) {        
        return playerIsOutOfBounds(player) || playerCollidesWithSomeone(player);
    }
    
    private boolean playerIsOutOfBounds(Player player) {
        int x = player.getSnake().getHead().x;
        int y = player.getSnake().getHead().y;
        return x < 0 || x >= this.getWidth() || y < 0 || y >= this.getHeight();
    }
    
    private boolean playerCollidesWithSomeone(Player player) {
        /* also checks for collision with itself */
        for (Player otherPlayer : this.players) {
            if (otherPlayer.isDead()) continue;
            
            if (player.getSnake().collidesWith(otherPlayer.getSnake())) {
                return true;
            }
        }    
        
        return false;
    }
    
    private void moveSnake(Snake snake, Direction direction) {
        this.board.set(snake.getHead().x, snake.getHead().y, BoardConstants.SNAKE_BODY);
        this.board.set(snake.getTail().x, snake.getTail().y, BoardConstants.FLOOR);
        
        snake.move(direction);
        this.numMovesNotEaten++;
        
        this.board.set(snake.getHead().x, snake.getHead().y, BoardConstants.SNAKE_HEAD);
        this.board.set(snake.getTail().x, snake.getTail().y, BoardConstants.SNAKE_BODY);
    }
    
    private void checkIfAppleEaten(Snake snake) {
        if (snake.collidesWith(this.getApple())) {
            snake.grow();
            this.placeApple();
            this.numMovesNotEaten = 0;
        }
    }
    
    private void placeApple() {
        if (this.placeApples) {
            this.board.placeApple();
        }
    }
    
    private void handlePlayerDeath(Player player) {
        player.kill();
        numPlayersAlive--;
        
        for (Piece piece : player.getSnake().getPieces()) {
            this.board.set(piece.x, piece.y, BoardConstants.FLOOR);
        }
        
        if (this.numPlayers() > 1 && numPlayersAlive > 1) {
            player.doNotDraw();
        }
    }

}
