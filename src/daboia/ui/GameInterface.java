
package daboia.ui;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import daboia.domain.DaboiaGame;
import daboia.GameHandler;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;

public class GameInterface implements UserInterface {

    private final static Color background = new Color(40, 40, 40);
    
    private JFrame frame;
    private DaboiaGame daboiaGame;
    
    public GameInterface(DaboiaGame daboiaGame) {
        this.daboiaGame = daboiaGame;
    }
    
    @Override
    public void showWindow() {
        SwingUtilities.invokeLater(this);
    }

    @Override
    public void run() {
        frame = new JFrame("Daboia Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(background);
        
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
        container.add(new GamePanel(daboiaGame));
    }
    
    private void addListeners(JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // also disposes of the window
                GameHandler.cleanSession(0);
            }
        });
        
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                daboiaGame.sendKeyInput(e.getKeyChar());
            }
        });
    }
    
}