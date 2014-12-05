
package daboia.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class MessagePanel extends JPanel implements MessageArea {

    private static final Font font = new Font(Font.DIALOG, Font.PLAIN, 11);
    
    private JLabel message;
    
    public MessagePanel(Color backgroundColor) {
        super(new MigLayout("", "[grow]", "[]"));
        this.setBackground(backgroundColor);
        this.message = new JLabel("");
        
        this.add(message);
    }
    
    @Override
    public void clearText() {
        this.remove(this.message);
        this.revalidate();
        this.repaint();
    }
    
    @Override
    public void setText(String message) {
        this.clearText();
        this.message = new JLabel(message);
        this.message.setFont(font);
        this.message.setForeground(new Color(160, 100, 90));
        this.add(this.message, "center");
        this.revalidate();
        this.repaint();
    }
    
    @Override
    public String getText() {
        return this.message.getText();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 30);
    }

}