
package com.github.tilastokeskus.daboia.ui;

import com.github.tilastokeskus.daboia.core.Piece;
import com.github.tilastokeskus.daboia.core.Player;
import com.github.tilastokeskus.daboia.core.Snake;
import com.github.tilastokeskus.daboia.core.game.DaboiaGame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GamePanel extends JPanel {
    
    private final static int pieceSize = 9;
    private final static int squareSize = 14;
    private final static int padding = 5;
    
    private final static Color appleColor = new Color(255, 100, 100);
    
    private final DaboiaGame daboiaGame;
    
    public GamePanel(DaboiaGame daboiaGame) {
        this.daboiaGame = daboiaGame;
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(pieceSize));        
        
        for (Player player : daboiaGame.getPlayers()) {
            if (!player.getShouldBeDrawn()) {
                continue;
            }
            
            Path2D path = getSnakePath(player.getSnake());
            
            g2.setColor(player.getSnakeColor());
            g2.draw(path);
            
            Piece head = player.getSnake().getHead();
            int hx = translateToBoard(head.x);
            int hy = translateToBoard(head.y);
            
            g2.setColor(player.getSnakeColor().brighter());
            g2.drawLine(hx, hy, hx, hy);
        }
        
        Piece apple = daboiaGame.getApple();
        if (apple != null) {
            int ax = translateToBoard(apple.x);
            int ay = translateToBoard(apple.y);
            g2.setColor(appleColor);
            g2.drawLine(ax, ay, ax, ay);
        }
    }
    
    private Path2D getSnakePath(Snake snake) {
        Path2D path = new Path2D.Double();

        List<Piece> snakePieces = snake.getPieces();
        Piece tail = snake.getTail();
        path.moveTo(translateToBoard(tail.x), translateToBoard(tail.y));            

        for (int s = 1; s < snakePieces.size(); s++) {
            Piece piece = snakePieces.get(s);                
            path.lineTo(translateToBoard(piece.x), translateToBoard(piece.y));
        }
        
        return path;
    }
    
    private int translateToBoard(int p) {
        return padding + (p * squareSize) + (squareSize / 2);
    }
    
    @Override
    public Dimension getPreferredSize() {
        int width = daboiaGame.getWidth() * squareSize + padding * 2;
        int height = daboiaGame.getHeight() * squareSize + padding * 2;
        return new Dimension(width, height);
    }
    
    @Override
    public Dimension getSize() {
        return getPreferredSize();
    }
    
}
