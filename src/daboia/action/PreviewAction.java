
package daboia.action;

import daboia.Main;
import daboia.domain.Player;
import daboia.ui.PreviewDialog;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Map;
import javax.swing.AbstractAction;

public class PreviewAction extends AbstractAction {
    
    public PreviewAction(String name) {
        super(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PreviewDialog dialog = new PreviewDialog(Main.getMainInterface().getFrame());
        Map<String, Object> settings = dialog.showDialog();
        
        if (settings != null) {
            Collection<Player> players = (Collection) settings.get("players");
            int speed = (int) settings.get("speed");
            int width = (int) settings.get("width");
            int height = (int) settings.get("height");
            Main.launchPreview(players, speed, width, height);
        }
    }
}
