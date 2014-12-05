
package daboia.ui.list.listener;

import daboia.domain.Player;
import daboia.ui.list.PlayerList;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RemovePlayerListener extends MouseAdapter {
    
    private Component parent;
    private PlayerList list;
    private Player player;
    
    public RemovePlayerListener(Component parent, PlayerList list, Player player) {
        this.parent = parent;
        this.list = list;
        this.player = player;
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (list.getObjects().size() > 1) {
            list.removeObject(player);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        parent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
