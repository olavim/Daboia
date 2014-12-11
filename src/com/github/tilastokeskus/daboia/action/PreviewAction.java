
package com.github.tilastokeskus.daboia.action;

import com.github.tilastokeskus.daboia.Main;
import com.github.tilastokeskus.daboia.core.game.GameSettings;
import com.github.tilastokeskus.daboia.ui.PreviewDialog;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class PreviewAction extends AbstractAction {
    
    public PreviewAction(String name) {
        super(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PreviewDialog dialog = new PreviewDialog(Main.getMainInterface().getFrame());
        GameSettings settings = dialog.showDialog();
        
        if (settings != null) {
            Main.launchPreview(settings);
        }
    }
}
