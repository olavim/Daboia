
package daboia.action;

import daboia.Main;
import daboia.network.lobby.GameLobbyFactory;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class PlayOnlineAction extends AbstractAction {      
    
    public PlayOnlineAction(String name) {
        super(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Main.getMainInterface().getOutputPanel().setText("Connecting...");
        GameLobbyFactory.showNewLobby();
    }
}