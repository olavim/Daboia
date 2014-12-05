
package daboia;

import daboia.domain.DaboiaGame;
import daboia.domain.Direction;
import daboia.domain.Player;
import daboia.ui.GameInterface;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameHandler {    
    
    public static final int DEFAULT_SPEED = 40;
    
    private static GameHandler gameHandler;
    private static Thread gameThread;
    
    public static void setup(Collection<Player> players, int width, int height) {
        gameHandler = new GameHandler(players, width, height);
    }
    
    public static void launch(int speed) throws LaunchException {
        if (gameHandler == null) {
            throw new LaunchException("No game handler initialized.");
        } else if (gameHandler.daboiaGame.numPlayers() == 0) {
            throw new LaunchException("No players registered.");
        } else if (gameThread != null) {
            throw new LaunchException("Previous game hasn't finished.");
        }
        
        gameThread = new Thread(() -> gameHandler.startGame(speed));        
        gameThread.start();
    }
    
    public static GameHandler getGameHandler() {
        return gameHandler;
    }
    
    public static void cleanSession(int delay) {
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
        
        if (gameHandler != null &&
            gameHandler.gameInterface != null) {
            gameHandler.gameInterface.getFrame().dispose();
        }
        
        gameHandler.interrupt();
        gameHandler = null;
        gameThread = null;
    }
    
    public static boolean registerPlayer(Player player) {
        DaboiaLogic logicHandler = player.getLogicHandler();        
        logicHandler.setDaboiaGame(gameHandler.daboiaGame);
        logicHandler.init();
        return true;
    }
    
    public static DaboiaGame getDaboiaGame() {        
        return gameHandler.daboiaGame;
    }
    
    
    private final DaboiaGame daboiaGame;
    private final GameInterface gameInterface;
    
    private final ScheduledExecutorService moveSchedule;

    public GameHandler(Collection<Player> players, int width, int height) {
        daboiaGame = new DaboiaGame(width, height, players);
        gameInterface = new GameInterface(daboiaGame);
        moveSchedule = Executors.newSingleThreadScheduledExecutor();
    }
    
    public void startGame(int speed) {
        gameInterface.showWindow();
        
        Runnable moveCmd = () -> {
            for (Player player : daboiaGame.getPlayers()) {
                if (player.isDead()) {
                    continue;
                }

                String move = player.getLogicHandler().getMove();
                Direction direction = Direction.directionFromString(move);
                daboiaGame.makeMove(player, direction);
            }

            gameInterface.getFrame().repaint();
            daboiaGame.wait(speed);
            
            if (daboiaGame.gameOver()) {
                cleanSession(2000);
                moveSchedule.shutdown();
            }
        };
        
        moveSchedule.scheduleAtFixedRate(moveCmd, 1000, speed, TimeUnit.MILLISECONDS);        
    }
    
    public void interrupt() {
        moveSchedule.shutdownNow();
    }

}
