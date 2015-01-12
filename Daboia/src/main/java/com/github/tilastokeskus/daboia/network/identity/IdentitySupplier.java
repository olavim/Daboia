package com.github.tilastokeskus.daboia.network.identity;

import java.io.IOException;
import java.util.UUID;

public interface IdentitySupplier {
    public UUID get(String message) throws IOException;
}
