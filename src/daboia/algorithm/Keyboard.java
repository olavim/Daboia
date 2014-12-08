
package daboia.algorithm;

import daboia.domain.Piece;
import daboia.DaboiaLogic;

public class Keyboard extends DaboiaLogic {
    
    private final char[] keys = new char[] { 'w', 'a', 's', 'd' };
    
    private String move;

    @Override
    public void onLaunch() {
        this.move = "UP";
    }
    
    @Override
    public String getMove() {
        char lastKey = lastKeyTyped();
        
        if     (lastKey == keys[0] && !isNeck(0, -1)) move = "UP";
        else if(lastKey == keys[1] && !isNeck(-1, 0)) move = "LEFT";
        else if(lastKey == keys[2] && !isNeck(0, 1))  move = "DOWN";
        else if(lastKey == keys[3] && !isNeck(1, 0))  move = "RIGHT";
        
        return move;
    }
    
    private boolean isNeck(int dirX, int dirY) {
        Piece snakeHead = player().getSnake().getHead();
        Piece neck = player().getSnake().getPieces().get(player().getSnake().getLength() - 2);
        
        return snakeHead.x + dirX == neck.x && snakeHead.y + dirY == neck.y;
    }

}
