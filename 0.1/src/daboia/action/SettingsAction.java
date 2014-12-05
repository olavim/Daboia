
package daboia.action;

import daboia.Main;
import daboia.ui.SettingsDialog;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class SettingsAction extends AbstractAction {
    
    public SettingsAction(String name) {
        super(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SettingsDialog window = new SettingsDialog(Main.getMainInterface().getFrame());
        window.showDialog();
    }            
}