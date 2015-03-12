
package com.github.tilastokeskus.daboia.ui;

import com.github.tilastokeskus.daboia.core.game.DaboiaLogic;
import com.github.tilastokeskus.daboia.core.Player;
import com.github.tilastokeskus.daboia.plugin.PluginManager;
import com.github.tilastokeskus.daboia.util.Pair;

import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class PlayerList extends LabelList<Player> {
    
    private final Window parent;
    
    public PlayerList(Window parent) {
        this.parent = parent;
        this.setSelectable(false);
        this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
    }

    @Override
    protected Iterable<Pair<Component, Player>> generateComponents() {
        List<Pair<Component, Player>> components = new ArrayList<>();
        
        for (Player player : this.getObjects()) {
            PlayerPanel panel = new PlayerPanel(player);
            components.add(new Pair(panel, player));
        }
        
        return components;
    }
    
    private class PlayerPanel extends JPanel {
        
        JLabel selectedLogicLabel;
        Player player;
        
        private PlayerPanel(Player player) {
            super(new MigLayout("insets 0", "[grow, fill]"));
            this.player = player;
            this.addComponents();
        }
        
        private void addComponents() {
            JPanel westPanel = createWestPanel();            
            JPanel eastPanel = createEastPanel();
            
            URL url = this.getClass().getResource("/images/delete.png");
            JLabel removePlayerIcon = new JLabel(new ImageIcon(url));
            
            removePlayerIcon.addMouseListener(
                    new RemovePlayerListener(PlayerList.this, player));
            
            removePlayerIcon.setCursor(
                    Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            this.add(westPanel, "west, grow");
            this.add(eastPanel, "west, grow, w 100%");
            this.add(removePlayerIcon, "east, grow, gap 10 10 2 0");
        }
        
        private JPanel createWestPanel() {
            JComponent colorRectangle = new RectangleComponent(
                    16, player.getSnakeColor(), 1, player.getSnakeColor().darker());
            
            JLabel playerName = new JLabel(player.getName());
            playerName.setFont(PlayerList.this.getFont());
            playerName.setForeground(PlayerList.this.getForeground());
            
            JPanel westPanel = new JPanel(new MigLayout("", "[grow, fill]0"));
            
            westPanel.setBorder(BorderFactory.createMatteBorder(
                    0, 1, 0, 0, new Color(200, 200, 200)));
            
            westPanel.setBackground(new Color(230, 230, 230));
            westPanel.add(colorRectangle, "west, grow, gap 10 0 6 0");
            westPanel.add(playerName, "west, grow, gap 10 10 6 0");
            
            return westPanel;
        }
        
        private JPanel createEastPanel() {
            Color logicLabelColor = PlayerList.this.getForeground().brighter().brighter();
            String text = "No logic";
            if (player.getLogicHandler() != null) {
                text = player.getLogicHandler().getTitle();
                logicLabelColor = PlayerList.this.getForeground().brighter();
            }
            
            this.selectedLogicLabel = new JLabel(text);
            this.selectedLogicLabel.setFont(PlayerList.this.getFont());
            this.selectedLogicLabel.setForeground(logicLabelColor);
            
            SelectLogicShape selectLogicShape = new SelectLogicShape(10);
            
            selectLogicShape.addMouseListener(new SelectLogicListener(
                    PlayerList.this.parent, PlayerList.this, player));
            
            selectLogicShape.setCursor(Cursor.getPredefinedCursor(
                    Cursor.HAND_CURSOR));
            
            JPanel eastPanel = new JPanel(new MigLayout("", "[grow, fill]0"));
            
            eastPanel.setBorder(BorderFactory.createMatteBorder(
                    0, 1, 0, 1, new Color(200, 200, 200)));
            
            eastPanel.setBackground(new Color(220, 220, 220));
            eastPanel.add(this.selectedLogicLabel, "dock center, grow, gap 12 0 6 6");
            eastPanel.add(selectLogicShape, "east, grow, gap 20 10 10 0");
            
            return eastPanel;
        }
        
        private class SelectLogicShape extends JComponent {
            private final int size;

            public SelectLogicShape(int size) {
                this.setMinimumSize(new Dimension(size, size));
                this.size = size;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                g2.setStroke(new BasicStroke(2));
                g2.setColor(new Color(140, 120, 100));
                g2.drawLine(2, 2, size - 2, 2);
                g2.setColor(new Color(130, 100, 80));
                g2.drawLine(2, size / 2, size - 2, size / 2);
                g2.setColor(new Color(120, 100, 80));
                g2.drawLine(2, size - 2, size - 2, size - 2);
            }
        }
        
        private class RemovePlayerListener extends MouseAdapter {    
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
        
        private class SelectLogicListener extends MouseAdapter {    
            private final Window parent;
            private final PlayerList list;
            private final Player player;

            public SelectLogicListener(Window parent, PlayerList list, Player player) {
                this.parent = parent;
                this.list = list;
                this.player = player;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ComponentList componentList = new LabelList();
                componentList.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
                
                List<DaboiaLogic> handlers = PluginManager.getLogicHandlers();
                if (handlers != null) {
                    componentList.addObjects(PluginManager.getLogicHandlers());
                }

                ComponentListChooser chooser = new ComponentListChooser<>(parent, componentList);
                DaboiaLogic logicHandler = (DaboiaLogic) chooser.showDialog();

                if (logicHandler != null) {
                    logicHandler = logicHandler.clone();
                    player.setLogicHandler(logicHandler);
                    list.refresh();
                }
            }
        }
        
    }

}
