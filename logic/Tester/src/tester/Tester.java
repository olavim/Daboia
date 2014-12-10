
package tester;

import daboia.DaboiaLogic;
import daboia.domain.Direction;
import daboia.domain.Piece;
import daboia.domain.Snake;

public class Tester extends DaboiaLogic {

    public static void main(String[] args) throws Exception {
        DaboiaLogic.launchPreview(Tester.class, 8, 8, 50);
    }

    @Override
    public void onLaunch() {
        
    }

    @Override
    public String getMove() {
        Snake snake = this.getPlayer().getSnake();
        Piece head = snake.getHead();
        Piece apple = this.getGameInstance().getApple();
        String move = getBestMove(head.x, head.y, apple.x, apple.y);
        
        return move;
    }
    
    private String getBestMove(int x, int y, int goalX, int goalY) {
        String bestMove = "UP";
        int smallestDistance = Integer.MAX_VALUE;
        
        Direction[] directions = new Direction[] { Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT };        
        for (Direction dir : directions) {
            int nx = x + dir.xTransform();
            int ny = y + dir.yTransform();
            
            if (canMove(nx, ny)) {            
                int distance = distance(nx, ny, goalX, goalY);
                if (distance(nx, ny, goalX, goalY) < smallestDistance) {
                    smallestDistance = distance;
                    bestMove = dir.name();
                }
            }
        }
        
        return bestMove;
    }
    
    private boolean canMove(int x, int y) {
        if (x < 0 || x >= this.getGameInstance().getWidth()
                || y < 0 || y >= this.getGameInstance().getHeight()) {
            return false;
        }
        
        int[][] board = this.getGameInstance().getBoard();        
        return board[y][x] == 0 || board[y][x] == 8;
    }
    
    private int distance(int x, int y, int goalX, int goalY) {
        return Math.abs(x - goalX) + Math.abs(y - goalY);
    }
    
}
