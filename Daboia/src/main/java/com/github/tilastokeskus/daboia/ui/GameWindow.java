
package com.github.tilastokeskus.daboia.ui;

import com.github.tilastokeskus.daboia.core.game.GameState;
import com.github.tilastokeskus.daboia.core.game.SavedStateGame;
import com.github.tilastokeskus.daboia.core.game.GameHandlerController;
import com.github.tilastokeskus.daboia.ui.listener.StateSliderChangeListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;

public class GameWindow implements GUI, Observer {
    
    private final static Color gamePanelBackground = new Color(40, 40, 40);
    private final static Color background = new Color(100, 100, 100);
    
    private final SavedStateGame game;
    private GameHandlerController controller;
    private JFrame frame;
    private ProgressSlider stateSlider;
    
    public GameWindow(SavedStateGame game) {
        this(game, null);
    }
    
    public GameWindow(SavedStateGame game,
                      GameHandlerController controller) {
        this.game = game;
        this.controller = controller;
    }
    
    public void setController(GameHandlerController controller) {
        this.controller = controller;
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
        container.setLayout(
                new MigLayout("insets 0, wrap 1", "[grow]", "[grow]5"));
        
        JPanel gamePanel = createGamePanel(game);
        container.add(gamePanel, "center, grow");
        
        stateSlider = new ProgressSlider(1, game.numStates(), 1);
        stateSlider.setOpaque(false);
        stateSlider.setPercent(0);
        container.add(stateSlider,
                      "south, gapy 0 5, gapx 5 5, h 12");
    }

    private JPanel createGamePanel(SavedStateGame game) {
        GamePanel gamePanel = new GamePanel(game);
        gamePanel.setBackground(gamePanelBackground);
        
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 190, 190), 1),
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1));        
        gamePanel.setBorder(border);
        
        JPanel gamePanelWrapper = new JPanel(
                new MigLayout("insets 0", "[grow]", "[grow]"));
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

        if (this.controller != null) {
            stateSlider.addChangeListener(
                    new StateSliderChangeListener(game, controller, this));
            stateSlider.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    controller.pause();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    controller.play();
                }
            });
        }
    }
    
}