package com.github.tilastokeskus.daboia.ui;

import java.awt.Frame;

public interface GUI extends Runnable {
    public void showWindow();
    public void closeWindow();
    public Frame getFrame();    
}
