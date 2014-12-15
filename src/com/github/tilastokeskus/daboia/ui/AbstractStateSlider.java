
package com.github.tilastokeskus.daboia.ui;

import com.github.tilastokeskus.daboia.util.StateSaver;
import javax.swing.JComponent;

public abstract class AbstractStateSlider extends JComponent {
    
    private final StateSaver stateSaver;
    private double sliderPosition;
    
    public AbstractStateSlider(StateSaver stateSaver) {
        this.stateSaver = stateSaver;
        this.sliderPosition = 0.5;
    }
    
    public StateSaver getStateSaver() {
        return this.stateSaver;
    }

    double getPosition() {
        return this.sliderPosition;
    }

    void setPosition(double pos) {
        this.sliderPosition = pos;
    }
    
    
}
