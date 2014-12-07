
package daboia.error;

import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

public class ErrorDialog extends JDialog {
    
    public static void showMsg(String message) {
        showMsg(null, message);
    }
    
    public static void showMsg(Window parent, String message) {
        SwingUtilities.invokeLater(() -> {
            ErrorDialog dialog = new ErrorDialog(parent, message);
            dialog.showDialog();
        });
    }
    
    private ErrorDialog(Window parent, String message) {
        super(parent, "Error", JDialog.DEFAULT_MODALITY_TYPE);
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
    }
    
    public void showDialog() {
        this.pack();
        this.setVisible(true);
    }

}
