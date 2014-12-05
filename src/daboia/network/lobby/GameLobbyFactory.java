
package daboia.network.lobby;

public class GameLobbyFactory {
    
    private static final String serverAddress = "88.195.103.68";
    private static final int portNumber = 2350;
    
    private static GameLobby lobby;
    
    /**
     * Creates and shows a new GameLobby instance, if one does not already exist.
     * 
     * @return    True if an instance of GameLobby doesn't exist already,
     *            or if the previous instance's connection has ended.
     *            Otherwise false.
     */
    public static boolean showNewLobby() {
        if (lobby == null || lobby.connectionHasEnded()) {            
            lobby = new GameLobby(serverAddress, portNumber);
            new Thread(lobby).start();            
            return true;
        }
        
        return false;
    }
    
    public static GameLobby getLobby() {
        return lobby;
    }
}
