
package com.github.tilastokeskus.daboia.network.lobby;

import com.github.tilastokeskus.daboia.Main;
import com.github.tilastokeskus.daboia.ui.GameLobbyWindow;
import com.github.tilastokeskus.daboia.ui.GUI;
import com.github.tilastokeskus.daboia.network.ConnectionState;
import com.github.tilastokeskus.daboia.network.identity.LobbyIdentitySupplier;
import java.io.IOException;
import javax.swing.SwingUtilities;

public class GameLobby extends Lobby {    
    private GUI lobbyInterface;
    private GameLobbyConnection connection;

    protected GameLobby(String serverAddress, int portNumber) {
        super(serverAddress, portNumber);
    }
    
    private void disposeInterface() {
        if (lobbyInterface != null) {
            lobbyInterface.getFrame().dispose();
        }
    }
    
    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException ex) {}         
        }
    }
    
    private void establishConnection() throws IOException {
        this.connection = new GameLobbyConnection(getServerAddress(), getPortNumber(), 
                new LobbyIdentitySupplier());
        
        this.setConnectionState(ConnectionState.CONNECTION_ESTABLISHED);
    }

    @Override
    public void show() {
        lobbyInterface = new GameLobbyWindow();
        SwingUtilities.invokeLater(lobbyInterface);
    }

    @Override
    public void close() {
        this.closeConnection();
        this.disposeInterface();
        this.setConnectionState(ConnectionState.CONNECTION_ENDED);
    }

    @Override
    public void run() {
        try {
            this.establishConnection();            
            this.show();
            Main.getMainInterface().getOutputPanel().clearText();
            
            connection.communicate();
        } catch (IOException ex) {
            Main.getMainInterface().getOutputPanel().setText(ex.getMessage());
            System.err.println("Connection ended: " + ex.getMessage());
        } finally {        
            this.close();            
        }
    }

}
