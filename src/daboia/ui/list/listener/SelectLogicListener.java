
package daboia.ui.list.listener;

import daboia.DaboiaLogic;
import daboia.Main;
import daboia.domain.Player;
import daboia.plugin.PluginManager;
import daboia.ui.ComponentListChooser;
import daboia.ui.list.ComponentList;
import daboia.ui.list.LabelList;
import daboia.ui.list.PlayerList;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
        
public class SelectLogicListener extends MouseAdapter {
    
    private Component parent;
    private PlayerList list;
    private Player player;
    
    public SelectLogicListener(Component parent, PlayerList list, Player player) {
        this.parent = parent;
        this.list = list;
        this.player = player;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ComponentList componentList = new LabelList();
        componentList.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        componentList.addObjects(PluginManager.getLogicHandlers());
        
        ComponentListChooser chooser = new ComponentListChooser<>(Main.getMainInterface().getFrame(), componentList);
        DaboiaLogic logicHandler = (DaboiaLogic) chooser.showDialog();
        
        if (logicHandler != null) {
            player.setLogicHandler(logicHandler);
            list.refresh();
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