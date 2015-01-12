
package com.github.tilastokeskus.daboia.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

public class RectangleComponent extends JComponent {
    
    private final int size;
    private final int borderSize;
    private final Color color;
    private final Color borderColor;
    
    public RectangleComponent(int size, Color color, int borderSize, Color borderColor) {
        this.size = size;
        this.borderSize = borderSize;
        this.color = color;
        this.borderColor = borderColor;
        this.setMinimumSize(new Dimension(size, size));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(this.borderColor);
        g.fillRect(0, 0, size, size);
        
        g.setColor(this.color);
        g.fillRect(this.borderSize, this.borderSize,
                this.size - this.borderSize * 2, this.size - this.borderSize * 2);
    }
    
}
