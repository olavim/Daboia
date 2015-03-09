
package com.github.tilastokeskus.daboia.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ProgressSlider extends JComponent {
    
    private static final Color BACKGROUND_TOP = new Color(180, 180, 180);
    private static final Color BACKGROUND_BOTTOM = new Color(220, 220, 220);
    private static final Color BAR_TOP = new Color(60, 60, 60);
    private static final Color BAR_BOTTOM = new Color(80, 80, 80);
    
    private final List<ChangeListener> changeListeners;
    
    private final int minValue;
    private final int maxValue;
    private final int valueInterval;
    private double value;
    
    public ProgressSlider(int min, int max, int interval) {
        this.minValue = min;
        this.maxValue = max;
        this.valueInterval = interval;
        this.value = (min + max) / 2.0;
        this.changeListeners = new ArrayList<>();
        
        SliderMouseListener listener = new SliderMouseListener();
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }
    
    public int getValue() {
        int dist = ((int) value) % valueInterval;
        return (int) value - dist;
    }
    
    public void setValue(double value) {
        if (value < minValue || value > maxValue)
            throw new IllegalArgumentException();
        this.value = value;
        notifyListeners();
    }
    
    public void setPercent(double percent) {
        double p = percent * maxValue + minValue;
        setValue(p);
    }
    
    public void addChangeListener(ChangeListener listener) {
        this.changeListeners.add(listener);
    }
    
    private void notifyListeners() {
        ChangeEvent e = new ChangeEvent(this);
        for (ChangeListener listener : this.changeListeners)
            listener.stateChanged(e);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int barWidth = (int) (width * value / (1.0 * (minValue + maxValue)));
        
        Rectangle rect = new Rectangle(0, 0, width, height);
        Rectangle outlineRect = new Rectangle(0, 0, width - 1, height - 1);
        Rectangle barRect = new Rectangle(0, 0, barWidth, height);
        
        GradientPaint backgroundGradient = new GradientPaint(
                0, 0, BACKGROUND_TOP, 0, 4, BACKGROUND_BOTTOM);
        GradientPaint barGradient = new GradientPaint(
                0, 0, BAR_TOP, 0, height, BAR_BOTTOM);
        
        g2.setPaint(backgroundGradient);
        g2.fill(rect);        
        g2.setPaint(barGradient);
        g2.fill(barRect);        
        g2.setColor(new Color(60, 60, 60));
        g2.draw(outlineRect);
    }
    
    private class SliderMouseListener extends MouseAdapter {
        
        @Override
        public void mousePressed(MouseEvent e) {
            updateValue(e.getX());
            revalidate();
            repaint();
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
            updateValue(e.getX());
            revalidate();
            repaint();
        }
        
        private void updateValue(int mouseX) {
            double ratio = 1.0 * mouseX / getWidth();
            double v = ratio * (minValue + maxValue);
            v = Math.min(maxValue, v);
            v = Math.max(minValue, v);
            setValue(v);
        }
        
    }
    
}
