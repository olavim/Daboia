
package com.github.tilastokeskus.daboia.action;

import com.github.tilastokeskus.daboia.core.Player;
import com.github.tilastokeskus.daboia.ui.PlayerList;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.Collection;
import javax.swing.AbstractAction;

public class AddPlayerAction extends AbstractAction {

    private PlayerList list;
    private Container parent;
    
    public AddPlayerAction(String label, PlayerList list, Container parent) {
        super(label);
        this.list = list;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Collection<Player> players = list.getObjects();
        for (int i = 0; i < 6; i++) {
            String playerName = "Player " + (i + 1);
            Player player = new Player(1, 1, i, playerName);
            if (!players.contains(player)) {
                list.addObject(player);
                resizeList();
                break;
            }
        }
    }
    
    private void resizeList() {
        if (parent.getHeight() < parent.getPreferredSize().height) {
            parent.setSize(parent.getWidth(), parent.getPreferredSize().height);
        }
        
        if (parent.getWidth() < parent.getPreferredSize().width) {
            parent.setSize(parent.getPreferredSize().width, parent.getHeight());
        }
    }
    
}
