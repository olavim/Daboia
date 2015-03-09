package com.github.tilastokeskus.daboia.ui;

import java.awt.Frame;

public interface GUI extends Runnable, Refreshable {
    public void showWindow();
    public void closeWindow();
    public Frame getFrame();
}
