package com.github.tilastokeskus.daboia.core.game;

public interface StateSaver {
    public GameState getInitialState();
    public GameState getCurrentState();
    public boolean nextState();
    public boolean previousState();
    public int numStates();
}
