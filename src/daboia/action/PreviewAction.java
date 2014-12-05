
package daboia.action;

import daboia.Main;
import daboia.ui.PreviewDialog;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class PreviewAction extends AbstractAction {
    
    public PreviewAction(String name) {
        super(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PreviewDialog dialog = new PreviewDialog(Main.getMainInterface().getFrame());
        dialog.showDialog();
    }
}
