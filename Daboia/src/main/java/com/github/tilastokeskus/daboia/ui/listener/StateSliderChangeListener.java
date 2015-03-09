
package com.github.tilastokeskus.daboia.ui.listener;

import com.github.tilastokeskus.daboia.core.game.SavedStateGame;
import com.github.tilastokeskus.daboia.core.game.GameHandlerController;
import com.github.tilastokeskus.daboia.ui.ProgressSlider;
import com.github.tilastokeskus.daboia.ui.Refreshable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class StateSliderChangeListener implements ChangeListener {

    private final SavedStateGame game;
    private final GameHandlerController controller;
    private final Refreshable refreshableComponent;
    
    public StateSliderChangeListener(SavedStateGame game,
                                     GameHandlerController controller,
                                     Refreshable refreshableComponent) {
        this.game = game;
        this.controller = controller;
        this.refreshableComponent = refreshableComponent;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int val = ((ProgressSlider) e.getSource()).getValue();
        int state = game.getCurrentState().getId();
        if (val > state) {
            controller.fastForward(val, () -> refreshableComponent.refresh());
        } else if (val < state) {
            controller.rewind(val, () -> refreshableComponent.refresh());
        }
    }
}