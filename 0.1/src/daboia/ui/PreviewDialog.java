
package daboia.ui;

import daboia.ui.list.PlayerList;
import daboia.action.AddPlayerAction;
import daboia.domain.Player;
import daboia.ui.button.LabelButton;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

public class PreviewDialog extends JDialog {
    
    private Frame parent;
    private JTextField widthField;
    private JTextField heightField;
    private JSlider speedSlider;
    private PlayerList playerList;
    
    public PreviewDialog() {
        this(null);
    }
    
    public PreviewDialog(Frame parent) {
        super(parent, "Preview", JDialog.DEFAULT_MODALITY_TYPE);
        this.parent = parent;
        
        addContents(this.getContentPane());
    }
    
    public void showDialog() {
        this.pack();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }
    
    private void addContents(Container container) {        
        LabelButton startButton = new LabelButton("Start", true);
        
        container.setLayout(new MigLayout("wrap 1, insets 0", "[grow]", "[]0"));
        
        container.add(createSpeedPanel(), "grow");
        container.add(new PlainSeparator(true), "grow");
        container.add(createSizePanel(), "grow");
        container.add(new PlainSeparator(true), "grow, gapy 0 15");
        container.add(createPlayerPanel());
        container.add(startButton, "center, grow, gapy 10 10");        
    }
    
    private JPanel createSpeedPanel() {
        JLabel speedSliderLabel = new JLabel("Game speed");
        speedSliderLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        speedSliderLabel.setForeground(new Color(80, 80, 80));
        
        speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        speedSlider.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        speedSlider.setPaintLabels(true);
        speedSlider.setMinorTickSpacing(10);
        speedSlider.setMajorTickSpacing(50);
        speedSlider.setPaintTicks(true);
        speedSlider.setOpaque(false);
        
        JPanel container = new JPanel(new MigLayout("wrap 1", "[grow]", "[]0"));
        container.setBackground(new Color(230, 230, 230));
        container.add(speedSliderLabel, "center");
        container.add(speedSlider, "center, grow, gapy 10 10");
        
        return container;
    }
    
    private JPanel createSizePanel() {
        Border fieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(160, 160, 160), 1), new EmptyBorder(0, 2, 0, 2));
        
        JLabel widthLabel = new JLabel("Width:");
        widthLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        
        widthField = new JTextField(3);
        widthField.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
        widthField.setBorder(fieldBorder);
        widthField.setText("10");
        
        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        
        heightField = new JTextField(3);
        heightField.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
        heightField.setBorder(fieldBorder);
        heightField.setText("10");
        
        JPanel container = new JPanel(new MigLayout("", "[grow]", "[]0"));
        container.setBackground(new Color(215, 215, 215));
        
        container.add(widthLabel, "west, gap 10 5 5 5");
        container.add(widthField, "west, gap 5 5 6 5");
        container.add(heightLabel, "west, gap 15 5 5 5");
        container.add(heightField, "west, gap 5 5 6 5");
        
        return container;
    }
    
    private JPanel createPlayerPanel() {
        playerList = new PlayerList();
        playerList.addObject(new Player(1, 1, 0, "Player 1"));
        
        LabelButton addPlayerButton = new LabelButton(new AddPlayerAction("Add Player", playerList, this), true);
        addPlayerButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        addPlayerButton.setForeground(new Color(160, 160, 160));
        
        JPanel container = new JPanel(new MigLayout("wrap 1", "[grow]", "[]0"));
        container.add(addPlayerButton, "right, gapy 0 10");
        
        container.add(new PlainSeparator(true), "grow");
        container.add(playerList, "grow");        
        container.add(new PlainSeparator(true), "grow");
        
        return container;
    }

}
