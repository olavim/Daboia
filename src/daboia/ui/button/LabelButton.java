
package daboia.ui.button;

import daboia.action.EmptyAction;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.AbstractAction;

public class LabelButton extends Button {
    
    private static final Font defaultFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    
    private static final Color defaultColor = new Color(180, 180, 180);
    private static final Color hoverColor   = new Color(220, 160, 120);
    private static final Color activeColor  = new Color(200, 140, 100);
    private static final Color defaultColorInv = new Color(100, 100, 100);
    private static final Color hoverColorInv   = new Color(180, 100, 80);
    private static final Color activeColorInv  = new Color(140, 90, 80);
    
    private final int leftMargin;
    private final int topMargin;
    private final int rightMargin;
    private final int bottomMargin;
    
    protected int width;
    protected int height;
    
    private boolean enabled;
    private boolean invertedColors;
    
    public LabelButton(String label) {
        this(new EmptyAction(label), 0, 0, 0, 0, false);
    }
    
    public LabelButton(AbstractAction action) {
        this(action, 0, 0, 0, 0, false);
    }
    
    public LabelButton(String label, boolean invertedColors) {
        this(new EmptyAction(label), 0, 0, 0, 0, invertedColors);
    }
    
    public LabelButton(AbstractAction action, boolean invertedColors) {
        this(action, 0, 0, 0, 0, invertedColors);
    }
    
    public LabelButton(String label, int horizMargin, int vertMargin) {
        this(new EmptyAction(label), horizMargin, vertMargin, horizMargin, vertMargin, false);
    }
    
    public LabelButton(AbstractAction action, int horizMargin, int vertMargin) {
        this(action, horizMargin, vertMargin, horizMargin, vertMargin, false);
    }
    
    public LabelButton(String label, int horizMargin, int vertMargin, boolean invertedColors) {
        this(new EmptyAction(label), horizMargin, vertMargin, horizMargin, vertMargin, invertedColors);
    }
    
    public LabelButton(AbstractAction action, int horizMargin, int vertMargin, boolean invertedColors) {
        this(action, horizMargin, vertMargin, horizMargin, vertMargin, invertedColors);
    }
    
    public LabelButton(String label, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        this(new EmptyAction(label), leftMargin, topMargin, rightMargin, bottomMargin, false);
    }
    
    public LabelButton(AbstractAction action, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        this(action, leftMargin, topMargin, rightMargin, bottomMargin, false);
    }
    
    public LabelButton(String label, int leftMargin, int topMargin, int rightMargin, int bottomMargin, boolean invertedColors) {
        this(new EmptyAction(label), leftMargin, topMargin, rightMargin, bottomMargin, invertedColors);
    }
    
    public LabelButton(AbstractAction action, int leftMargin, int topMargin, int rightMargin, int bottomMargin, boolean invertedColors) {
        super(action);
        this.setFont(defaultFont);
        this.width = this.getFontMetrics(defaultFont).stringWidth(getLabel());
        this.height = this.getFontMetrics(defaultFont).getHeight();
        this.leftMargin = leftMargin;
        this.topMargin = topMargin;
        this.rightMargin = rightMargin;
        this.bottomMargin = bottomMargin;
        
        this.enabled = true;
        this.invertedColors = invertedColors;
    }
    
    @Override
    public void setFont(Font font) {
        super.setFont(font);
        this.width = this.getFontMetrics(font).stringWidth(getLabel());
        this.height = this.getFontMetrics(font).getHeight();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHints(new RenderingHints(
             RenderingHints.KEY_TEXT_ANTIALIASING,
             RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        
        g2.setFont(this.getFont());
        g2.setColor(determineColor());
        
        FontMetrics metrics = g2.getFontMetrics(this.getFont());
        
        int centerX = this.getWidth() / 2 - this.width / 2;
        int centerY = this.getHeight() / 2 + metrics.getAscent() / 2;
        g2.drawString(getLabel(), centerX, centerY);
        
        g2.setColor(defaultColor);
        this.revalidate();
    }
    
    private Color determineColor() {
        if (!enabled) {
            return invertedColors ? defaultColor : defaultColorInv;
        }
        
        if (getState() == ButtonState.HOVER) {
            return invertedColors ? hoverColorInv : hoverColor;
        } else if (getState() == ButtonState.DOWN) {
            return invertedColors ? activeColorInv : activeColor;
        }
        
        return invertedColors ? defaultColorInv : defaultColor;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.enabled = enabled;
        repaint();
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(this.width, this.height);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.width + this.leftMargin + this.rightMargin, this.height + this.topMargin + this.bottomMargin);
    }
    
    @Override
    public Dimension getSize() {
        return this.getPreferredSize();
    }

}
