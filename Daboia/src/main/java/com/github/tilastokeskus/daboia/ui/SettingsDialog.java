
package com.github.tilastokeskus.daboia.ui;

import java.awt.Container;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

public class SettingsDialog extends JDialog {
    
    private Frame parent;
    
    public SettingsDialog() {
        this(null);
    }
    
    public SettingsDialog(Frame parent) {
        super(parent, "Settings", JDialog.DEFAULT_MODALITY_TYPE);
        this.parent = parent;
        
        addContents(this.getContentPane());
    }
    
    public void showDialog() {
        this.pack();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }
    
    private void addContents(Container container) {        
        JPanel networkPanel = createNetworkPanel();
        JPanel savePanel = createSavePanel();
        
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab("Network", networkPanel);
        
        container.setLayout(new MigLayout());
        container.add(tabPane, "wrap");
        container.add(savePanel);        
    }
    
    private JPanel createNetworkPanel() {
        JPanel panel = new JPanel(new MigLayout());
        
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameTextField = new JTextField(10);
        
        panel.add(usernameLabel);
        panel.add(usernameTextField);
        return panel;
    }
    
    private JPanel createSavePanel() {
        JPanel panel = new JPanel(new MigLayout());
        
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        panel.add(saveButton);
        panel.add(cancelButton);
        
        return panel;        
    }

}
