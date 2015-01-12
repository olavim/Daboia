
package com.github.tilastokeskus.daboia.ui;

import com.github.tilastokeskus.daboia.core.Piece;
import com.github.tilastokeskus.daboia.core.Player;
import com.github.tilastokeskus.daboia.core.game.DaboiaGame;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public abstract class AbstractGamePanel extends JPanel {

    private final int squareSize;
    private final int padding;
    
    protected final DaboiaGame game;
    
    public AbstractGamePanel(DaboiaGame game, int squareSize, int padding) {
        this.game = game;
        this.squareSize = squareSize;
        this.padding = padding;
    }
    
    public DaboiaGame getRegisteredGame() {
        return this.game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);        
        drawPlayers(this.game.getPlayers(), g);        
        drawApple(this.game.getApple(), g);
    }
    
    protected int getSquareSize() {
        return squareSize * Math.min(getParent().getWidth()  / getMinimumSize().width,
                                     getParent().getHeight() / getMinimumSize().height);
    }
    
    protected int translateToBoard(int p) {
        return padding
               + this.getSquareSize() / 2
               + p * this.getSquareSize();
    }
    
    @Override
    public Dimension getPreferredSize() {
        return getDimensionWithSquareSize(this.getSquareSize());
    }
    
    @Override
    public Dimension getMinimumSize() {
        return getDimensionWithSquareSize(this.squareSize);
    }
    
    private Dimension getDimensionWithSquareSize(int size) {
        int width = this.game.getWidth() * size + padding * 2;
        int height = this.game.getHeight() * size + padding * 2;
        return new Dimension(width, height);
    }
    
    protected abstract void drawPlayers(Iterable<Player> players, Graphics g);
    protected abstract void drawApple(Piece apple, Graphics g);
    
}
