
package daboia.ui.button;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;

public class Button extends JComponent {
    
    private AbstractAction action;    
    private ButtonState state;
    
    protected String label;
    
    public Button(AbstractAction action) {
        this.addMouseListener(new ButtonListener(this));
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
    
    public Component lastEntered() {
        return ButtonListener.lastEnteredComponent();
    }
    
    public void notifyListeners(MouseEvent e) {
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, new String(), e.getWhen(), e.getModifiers());
        action.actionPerformed(evt);
    }
    
    public void setState(ButtonState state) {
        this.state = state;
        repaint();
    }
    
    public ButtonState getState() {
        return this.state;
    }

}
