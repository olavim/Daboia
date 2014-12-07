
package daboia.ui;

import daboia.domain.DaboiaGame;
import daboia.domain.Piece;
import daboia.domain.Player;
import daboia.domain.Snake;
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
    
    private final static int snakeSize = 9;
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
        
        for (Player player : daboiaGame.getPlayers()) {            
            Path2D path = getSnakePath(player.snake());
            
            g2.setColor(player.getSnakeColor());
            g2.setStroke(new BasicStroke(snakeSize));
            g2.draw(path);
            
            Piece head = player.snake().head();
            int hx = translateToBoard(head.x);
            int hy = translateToBoard(head.y);
            
            g2.setColor(player.getSnakeColor().brighter());
            g2.drawLine(hx, hy, hx, hy);
        }
        
        Piece apple = daboiaGame.apple();
        if (apple != null) {
            int ax = translateToBoard(apple.x);
            int ay = translateToBoard(apple.y);
            g2.setColor(appleColor);
            g2.drawLine(ax, ay, ax, ay);
        }
    }
    
    private Path2D getSnakePath(Snake snake) {
        Path2D path = new Path2D.Double();

        List<Piece> snakePieces = snake.pieces();
        Piece tail = snake.tail();
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
        return new Dimension(daboiaGame.width() * squareSize + padding * 2, daboiaGame.height() * squareSize + padding * 2);
    }
    
    @Override
    public Dimension getSize() {
        return getPreferredSize();
    }
    
}
