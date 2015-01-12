
package com.github.tilastokeskus.daboia.ui;

import com.github.tilastokeskus.daboia.util.Pair;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class LabelList<T> extends ComponentList<T> {
    
    public LabelList() {
        this(new ArrayList<>());
    }
    
    public LabelList(Collection<T> elements) {
        super(elements);
        this.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
        this.setForeground(new Color(80, 80, 80));
    }
    
    @Override
    protected Iterable<Pair<Component, T>> generateComponents() {
        List<Pair<Component, T>> components = new ArrayList<>();
        for (T element : this.getObjects()) {
            JPanel panel = new JPanel(new MigLayout("insets 4"));            
            JLabel label = new JLabel(element.toString());
            label.setFont(this.getFont());
            label.setForeground(this.getForeground());
            
            panel.add(label);
            panel.setOpaque(false);
            
            components.add(new Pair(panel, element));
        }
        
        return components;
    }

}
