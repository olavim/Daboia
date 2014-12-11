
package com.github.tilastokeskus.daboia.ui;

import com.github.tilastokeskus.daboia.ui.button.LabelButton;
import com.github.tilastokeskus.daboia.network.lobby.GameLobbyFactory;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

public class GameLobbyWindow implements GUI {
    
    private JFrame frame;
    
    @Override
    public void showWindow() {
        SwingUtilities.invokeLater(this);
    }
    
    @Override
    public void closeWindow() {
        this.frame.dispose();
    }

    @Override
    public void run() {
        frame = new JFrame("Daboia Game Lobby");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        addContents(frame.getContentPane());
        addListeners(frame);
        
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    @Override
    public JFrame getFrame() {
        return frame;
    }
    
    private void addContents(Container container) {
        container.setLayout(new MigLayout("", "[grow]", "[grow]10"));
        container.setBackground(new Color(60, 60, 60));
        
        JPanel southDock = new JPanel(new MigLayout("", "[grow]", "[grow]"));
        southDock.setOpaque(false);
        
        LabelButton joinGameButton = new LabelButton("Join game");
        joinGameButton.setEnabled(false);
        LabelButton newGameButton = new LabelButton("New game");
        LabelButton refreshButton = new LabelButton("Refresh");
        
        southDock.add(joinGameButton, "center, gap 20 20 5 10");
        southDock.add(newGameButton, "center, gap 20 20 5 10");
        southDock.add(refreshButton, "center, gap 20 20 5 10");
        
        container.add(southDock, "south");
        container.add(new PlainSeparator(0.95), "south");
    }
    
    private void addListeners(JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (GameLobbyFactory.getLobby() != null) {
                    GameLobbyFactory.getLobby().close();
                }
            }
        });
        
    }
    
}
