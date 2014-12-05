
package daboia.network.lobby;

import daboia.network.ConnectionState;

public abstract class Lobby implements Runnable {
    
    private final String serverAddress;
    private final int portNumber;
    
    private ConnectionState connectionState;

    public Lobby(String serverAddress, int portNumber) {
        this.serverAddress = serverAddress;
        this.portNumber = portNumber;
        this.connectionState = ConnectionState.UNDEFINED;
    }
    
    public String getServerAddress() {
        return this.serverAddress;
    }
    
    public int getPortNumber() {
        return this.portNumber;
    }
    
    public ConnectionState getConnectionState() {
        return this.connectionState;
    }
    
    public void setConnectionState(ConnectionState state) {
        this.connectionState = state;
    }
    
    public boolean connectionHasEnded() {
        return this.connectionState == ConnectionState.CONNECTION_ENDED;
    }
    
    public abstract void show();
    public abstract void close();
    
}
