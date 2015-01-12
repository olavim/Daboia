
package com.github.tilastokeskus.daboia.ui.button;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.AbstractAction;
import javax.swing.JComponent;

public class Button extends JComponent {
    
    private Collection<ActionListener> actionListeners;
    private AbstractAction action;    
    private ButtonState state;
    
    protected String label;
    
    public Button(AbstractAction action) {
        this.addMouseListener(new ButtonListener(this));
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.actionListeners = new ArrayList<>();
        this.state = ButtonState.DEFAULT;
        this.action = action;
        this.label = (String) action.getValue(AbstractAction.NAME);
    }
    
    public Button(String label) {
        this.state = ButtonState.DEFAULT;
        this.label = label;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public void addActionListener(ActionListener listener) {
        this.actionListeners.add(listener);
    }
    
    public void notifyListeners(MouseEvent e) {
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, new String(), e.getWhen(), e.getModifiers());
        action.actionPerformed(evt);
        
        for (ActionListener listener : this.actionListeners) {
            ActionEvent event = new ActionEvent(e.getSource(), e.getID(), e.paramString());
            listener.actionPerformed(event);
        }
    }
    
    public void setState(ButtonState state) {
        this.state = state;
        repaint();
    }
    
    public ButtonState getState() {
        return this.state;
    }

}
