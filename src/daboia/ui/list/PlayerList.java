
package daboia.ui.list;

import daboia.domain.Player;
import daboia.ui.RectangleComponent;
import daboia.ui.list.listener.RemovePlayerListener;
import daboia.ui.list.listener.SelectLogicListener;
import daboia.util.Pair;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

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
            
            URL url = this.getClass().getClassLoader().getResource("resource/images/delete.png");
            JLabel removePlayerIcon = new JLabel(new ImageIcon(url));
            removePlayerIcon.addMouseListener(new RemovePlayerListener(PlayerList.this, player));
            removePlayerIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            this.add(westPanel, "west, grow");
            this.add(eastPanel, "west, grow, w 100%");
            this.add(removePlayerIcon, "east, grow, gap 10 10 2 0");
        }
        
        private JPanel createWestPanel() {
            JComponent colorRectangle = new RectangleComponent(16, player.getSnakeColor(), 1, player.getSnakeColor().darker());            
            JLabel playerName = new JLabel(player.getName());
            playerName.setFont(PlayerList.this.getFont());
            playerName.setForeground(PlayerList.this.getForeground());
            
            JPanel westPanel = new JPanel(new MigLayout("", "[grow, fill]0"));
            westPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(200, 200, 200)));
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
            selectLogicShape.addMouseListener(new SelectLogicListener(PlayerList.this.parent, PlayerList.this, player));
            selectLogicShape.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            JPanel eastPanel = new JPanel(new MigLayout("", "[grow, fill]0"));
            eastPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(200, 200, 200)));
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
        
    }

    
}
