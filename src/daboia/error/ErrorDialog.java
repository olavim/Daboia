
package daboia.error;

import java.awt.Container;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

public class ErrorDialog extends JFrame {
    
    public static void showMsg(String message) {
        showMsg(null, message);
    }
    
    public static void showMsg(final Container parent, String message) {
        SwingUtilities.invokeLater(() -> {
            new ErrorDialog(parent, message);
        });
    }
    
    private ErrorDialog(Container parent, String message) {
        super("Error");
        this.setResizable(false);
        
        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new MigLayout());
        
        JLabel icon = new JLabel(UIManager.getIcon("OptionPane.errorIcon"));
        JButton okButton = new JButton("OK");
        okButton.addActionListener((ActionEvent e) -> {
            dispose();
        });
        
        contentPane.add(icon, "gap top 5");
        contentPane.add(new JLabel(message), "wrap, gap left 5, gap right 10");
        contentPane.add(okButton, "span, center, gap 10");
        
        this.pack();
        this.setLocationRelativeTo(parent);
//        this.setLocationByPlatform(true);
        this.setVisible(true);
    }

}
