
package com.github.tilastokeskus.daboia.network;

import com.github.tilastokeskus.daboia.network.identity.IdentitySupplier;
import java.io.IOException;
import java.util.UUID;

public abstract class IdentifiedConnection extends Connection {

    private final UUID uuid;
    
    public IdentifiedConnection(String serverAddress, int port, IdentitySupplier supplier) throws IOException {
        super(serverAddress, port);
        this.uuid = supplier.get(in.readLine());
    }

    public UUID getIdentifier() {
        return this.uuid;
    }

}
