
package com.github.tilastokeskus.daboia.ui;

import com.github.tilastokeskus.daboia.util.StateSaver;

public class StateSlider extends AbstractStateSlider {
    
    public StateSlider(StateSaver stateSaver) {
        super(stateSaver);
        System.out.println(this.getParent().getSize().width);
    }

}
