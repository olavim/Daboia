
package com.github.tilastokeskus.daboia.ui;

import com.github.tilastokeskus.daboia.action.PreviewAction;
import com.github.tilastokeskus.daboia.action.PlayOnlineAction;
import com.github.tilastokeskus.daboia.action.SettingsAction;
import com.github.tilastokeskus.daboia.ui.button.LabelButton;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import javax.swing.*;
import java.awt.Container;
import java.net.URL;

public class MainWindow implements GUI {
    
    private static final Color backgroundColor = new Color(60, 60, 60);
    
    private JFrame frame;
    private MessagePanel outputPanel;
    
    @Override
    public void run() {
        frame = new JFrame("Daboia");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        addContents(frame.getContentPane());
        
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    @Override
    public void showWindow() {
        SwingUtilities.invokeLater(this);
    }
    
    @Override
    public void closeWindow() {
        this.frame.dispose();
    }
    
    @Override
    public JFrame getFrame() {
        return frame;
    }
    
    private void addContents(Container container) {
        this.outputPanel = new MessagePanel(new Color(40, 40, 40));
        
        URL url = this.getClass().getClassLoader().getResource("resource/images/logo.png");
        JLabel logo = new JLabel(new ImageIcon(url));
        
        container.setBackground(backgroundColor);
        container.setLayout(new MigLayout("", "[grow]", "[]"));
        
        container.add(logo, "dock north, width 300");
        container.add(new NavPanel(), "dock center");
        container.add(outputPanel, "dock south, gap 0 0 20 0");
    }
    
    public MessageArea getOutputPanel() {
        return this.outputPanel;
    }
    
    private class NavPanel extends JPanel {
        
        LabelButton previewButton = new LabelButton(new PreviewAction("Preview"));
        LabelButton settingsButton = new LabelButton(new SettingsAction("Settings"));
        LabelButton playOnlineButton = new LabelButton(new PlayOnlineAction("Play online"));
        
        private NavPanel() {
            super(new MigLayout("", "[grow]", "[grow]"));
            this.setBackground(backgroundColor);            
            this.addContents();
        }
        
        private void addContents() {
            this.add(previewButton, "center, wrap");
            this.add(settingsButton, "center, wrap");
            this.add(playOnlineButton, "center");
        }
        
    }

}
