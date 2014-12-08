
package daboia.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DaboiaGame {
    
    private final List<Player> players;
    
//    private int[][] board;
    private Board board;
    private boolean gameOver;    
    private int numPlayersAlive;
    private int numMovesNotEaten;
    
    public DaboiaGame(Collection<Player> players, int width, int height) {
        this.players = new ArrayList<>(players);
        this.gameOver = false;
//        this.board = new int[height][width];
        this.board = new Board(width, height);
        this.numPlayersAlive = players.size();
        this.numMovesNotEaten = 0;
    }
    
    public int numPlayers() {
        return this.players.size();
    }
    
    public void sendKeyInput(char c) {
        for (Player player : this.players) {
            player.getLogicHandler().sendKey(c);
        }
    }
    
    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players);
    }
    
    public Piece getApple() {
        return this.board.getApple();
    }
    
    public Board getBoard() {
        return this.board.getCopy();
    }
    
    public boolean isGameOver() {
        return this.gameOver;
    }
    
    public void makeMove(Player player, Direction direction) {
        Snake snake = player.getSnake();
        
        Piece previousHead = snake.getHead(); 
        Piece previousTail = snake.getTail();
        
        snake.move(direction);
        this.numMovesNotEaten++;
        
        Piece currentHead = snake.getHead();
        Piece currentTail = snake.getTail();
        
        this.board.set(previousHead.x, previousHead.y, Board.SNAKE_BODY);
        this.board.set(previousTail.x, previousTail.y, Board.FLOOR);
        this.board.set(currentHead.x, currentHead.y, Board.SNAKE_HEAD);
        this.board.set(currentTail.x, currentTail.y, Board.SNAKE_BODY);
        
        if (snake.doesCollideWith(this.getApple())) {
            snake.grow();
            this.board.placeApple();
            this.numMovesNotEaten = 0;
        }
        
        if (playerIsDead(player)) {
            handlePlayerDeath(player);
        }
        
        if (this.isInfiniteGame() || this.numFreeSpots() == 0 || this.numPlayersAlive == 0
                || (this.numPlayers() > 1 && this.numPlayersAlive < 2)) {
            this.gameOver = true;
        }
    }
    
    /**
     * Check if the game has been running for too long
     * without an apple being eaten.
     * 
     * @return   True or false.
     */
    public boolean isInfiniteGame() {
        int area = this.board.getWidth() * this.board.getHeight();
        return this.numMovesNotEaten > this.numPlayersAlive * area * 3;
    }
    
    private void handlePlayerDeath(Player player) {
        System.out.println("Player " + (player.getID() + 1) + " died with snake length " + player.getSnake().getLength());

        player.kill();
        numPlayersAlive--;
        
        for (Piece piece : player.getSnake().getPieces()) {
            this.board.set(piece.x, piece.y, Board.FLOOR);
        }
        
        if (this.numPlayers() > 1 && numPlayersAlive > 1) {
            player.doNotDraw();
        }
    }
    
    private boolean playerIsDead(Player player) {        
        Snake snake = player.getSnake();
        int x = snake.getHead().x;
        int y = snake.getHead().y;
        if (x < 0 || x >= this.board.getWidth()
                || y < 0 || y >= this.board.getHeight()) {
            return true;
        }
        
        /* also checks for collision with itself */
        for (Player otherPlayer : this.players) {
            if (snake.doesCollideWith(otherPlayer.getSnake())) {
                return true;
            }
        }    
        
        return false;
    }
    
    private int numFreeSpots() {
        int freeSpots = this.board.getWidth() * this.board.getHeight();
        for (Player player : this.players) {
            freeSpots -= player.getSnake().getTrueLength();
        }
        
        return freeSpots;
    }
    
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
        }
    }

}
