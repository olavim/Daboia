package com.github.tilastokeskus.daboia.ui;

import java.awt.Window;

public interface GUI extends Runnable {
    public void showWindow();
    public void closeWindow();
    public Window getFrame();    
}
