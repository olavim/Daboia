
package daboia.domain.game;

import daboia.DaboiaLogic;
import daboia.domain.Direction;
import daboia.domain.Player;
import java.util.List;

public final class GamePreloader {
    
    /**
     * Sets up and plays a SavedStateGame that is set up according to
     * the given players and the desired size of the board.
     * 
     * @param players  A list of Players that are participating in the game.
     * @param width    The desired width of the game board.
     * @param height   The desired height of the game board.
     * @return         A rewinded SavedStateGame.
     */
    public static SavedStateGame preload(List<Player> players, int width, int height) {
        System.out.println("Loading...");
        SavedStateGame game = new SavedStateGame(players, width, height);

        for (Player player : players) {
            DaboiaLogic logicHandler = player.getLogicHandler();        
            logicHandler.setDaboiaGame(game);
            logicHandler.init();
        }

        double lastP = -1;
        while (!game.isGameOver()) {
            double percent = 1 - (double) game.numPlayersAlive / (double) game.numPlayers();
            if (percent > lastP) {
                System.out.println((int) (100 * percent) + "%... ");
                lastP = percent;
            }
            
            for (Player player : game.getPlayers()) {
                if (!player.isAlive()) continue;                
                if (game.isGameOver()) break;

                String directionStr = player.getLogicHandler().getMove();
                Direction direction = Direction.directionFromString(directionStr);
                game.makeMove(player, direction);
            }
        }

        game.rewind();

        System.out.println("Ready!");
        return game;
    }
}
