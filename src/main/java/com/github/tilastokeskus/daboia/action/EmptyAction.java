
package com.github.tilastokeskus.daboia.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class EmptyAction extends AbstractAction {
    
    public EmptyAction(String label) {
        super(label);
    }
    
    @Override 
    public void actionPerformed(ActionEvent e) {
    }    
}
