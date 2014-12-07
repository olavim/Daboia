
package daboia.ui.list.listener;

import daboia.domain.Player;
import daboia.ui.list.PlayerList;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RemovePlayerListener extends MouseAdapter {
    
    private final PlayerList list;
    private final Player player;
    
    public RemovePlayerListener(PlayerList list, Player player) {
        this.list = list;
        this.player = player;
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (list.getObjects().size() > 1) {
            list.removeObject(player);
        }
    }
}
