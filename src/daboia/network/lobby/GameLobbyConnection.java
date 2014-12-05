
package daboia.network.lobby;

import daboia.network.IdentifiedConnection;
import daboia.network.identity.IdentitySupplier;
import java.io.IOException;

public class GameLobbyConnection extends IdentifiedConnection {

    public GameLobbyConnection(String serverAddress, int port, IdentitySupplier supplier) throws IOException {
        super(serverAddress, port, supplier);
    }
    
    @Override
    public void communicate() throws IOException {
        try {
            String message;
            while ((message = in.readLine()) != null) {            
                System.out.println(message);
            }
        } catch (IOException ex) {
            throw new IOException("Disconnected", ex);
        }
    }
    
}
