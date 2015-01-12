
package com.github.tilastokeskus.daboia.network.identity;

import java.io.IOException;
import java.util.UUID;

public class LobbyIdentitySupplier implements IdentitySupplier {
    
    @Override
    public UUID get(String message) throws IOException {
        /*
         * The server returns either a uuid-string or a message specifying
         * why an identifier couldn't be provided.
         * An example non-uuid response could be "Server is busy."
         */
        try {
            
            /* try to return an identifier built from the response */
            return UUID.fromString(message);
            
        } catch (IllegalArgumentException ex) {
            
            /* if the response was not an identifier, throw it as an exception */
            throw new IOException(message);
            
        }
    }

}
