
package com.github.tilastokeskus.daboia.ui;

import com.github.tilastokeskus.daboia.core.game.GameHandler;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;

public class GameWindow implements GUI {

    private final static Color background = new Color(40, 40, 40);
    
    private JFrame frame;
    private GameHandler gameHandler;
    
    public GameWindow(GameHandler handler) {
        this.gameHandler = handler;
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
    public void run() {
        frame = new JFrame("Daboia Game");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
    
    public void refresh() {
        frame.repaint();
    }
    
    private void addContents(Container container) {
        container.add(new GamePanel(gameHandler.getRegisteredGame()));
    }
    
    private void addListeners(JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // also disposes of the window
                gameHandler.stopGame(0);
            }
        });
        
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gameHandler.getRegisteredGame().sendKeyInput(e.getKeyChar());
            }
        });
    }
    
}