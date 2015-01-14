
package com.github.tilastokeskus.daboia.ui;

import com.github.tilastokeskus.daboia.core.game.GameState;
import com.github.tilastokeskus.daboia.core.game.SavedStateGame;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;

public class GameWindow implements GUI, Observer {
    
    private final static Color gamePanelBackground = new Color(40, 40, 40);
    private final static Color background = new Color(100, 100, 100);
    
    private final SavedStateGame game;
    private JFrame frame;
    private JSlider stateSlider;
    
    public GameWindow(SavedStateGame game) {
        this.game = game;
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
        frame.setMinimumSize(frame.getSize());
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    @Override
    public JFrame getFrame() {
        return frame;
    }
    
    @Override
    public void refresh() {
        if (this.stateSlider == null || this.frame == null)
            throw new IllegalStateException("Window has not been initialized");
        
        GameState currentState = game.getCurrentState();
        if (currentState != null) {
            stateSlider.setValue(game.getCurrentState().getId());
        }
        
        frame.repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.refresh();
    }
    
    private void addContents(Container container) {        
        container.setLayout(new MigLayout("insets 5, wrap 1", "[grow]", "[grow]"));
        
        JPanel gamePanel = createGamePanel(game);
        container.add(gamePanel, "center, grow");
        
        stateSlider = new JSlider(1, game.numStates(), 1);
        stateSlider.setOpaque(false);
        container.add(stateSlider, "south, grow, w " + gamePanel.getPreferredSize().width);
    }

    private JPanel createGamePanel(SavedStateGame game) {
        GamePanel gamePanel = new GamePanel(game);
        gamePanel.setBackground(gamePanelBackground);
        
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 190, 190), 1),
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1));        
        gamePanel.setBorder(border);
        
        JPanel gamePanelWrapper = new JPanel(new MigLayout("insets 0", "[grow]", "[grow]"));
        gamePanelWrapper.add(gamePanel, "center");
        return gamePanelWrapper;
    }
    
    private void addListeners(Window window) {
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                window.dispose();
                game.terminateGame();
            }
        });
        
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                game.sendKeyInput(e.getKeyChar());
            }
        });
    }
    
}