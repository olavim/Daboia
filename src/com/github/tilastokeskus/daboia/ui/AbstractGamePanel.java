
package com.github.tilastokeskus.daboia.ui;

import com.github.tilastokeskus.daboia.core.Piece;
import com.github.tilastokeskus.daboia.core.Player;
import com.github.tilastokeskus.daboia.core.game.DaboiaGame;
import java.awt.BasicStroke;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
        Graphics2D g2 = (Graphics2D) g;
        
        double strokeSize = 2.0 / 3 * this.getSquareSize();
        g2.setStroke(new BasicStroke((int) strokeSize));
        
        drawPlayers(this.game.getPlayers(), g);        
        drawApple(this.game.getApple(), g);
    }
    
    public double getAspectRatio() {
        return 1.0 * this.game.getWidth() / this.game.getHeight();
    }
    
    protected int getSquareSize() {
        int parentWidth = getParent().getWidth();
        int parentHeight = getParent().getHeight();
        double width = parentWidth * getAspectRatio();
        double height = parentHeight / getAspectRatio();
        double factorX = 1.0 * parentWidth / getMinimumSize().width;
        double factorY = 1.0 * parentHeight / getMinimumSize().height;
        
        if (width < height)
            return (int) (squareSize * factorX);
        
        return (int) (squareSize * factorY);
    }
    
    protected int translateToBoard(int p) {
        return (int) (padding
                      + this.getSquareSize() / 2
                      + p * this.getSquareSize());
    }
    
    @Override
    public Dimension getPreferredSize() {
        int sSize = getSquareSize();
        int width = this.game.getWidth() * sSize + padding * 2;
        int height = this.game.getHeight() * sSize + padding * 2;
        return new Dimension(width, height);
    }
    
    @Override
    public Dimension getMinimumSize() {
        int width = this.game.getWidth() * squareSize + padding * 2;
        int height = this.game.getHeight() * squareSize + padding * 2;
        return new Dimension(width, height);
    }
    
    protected abstract void drawPlayers(Iterable<Player> players, Graphics g);
    protected abstract void drawApple(Piece apple, Graphics g);
    
}
