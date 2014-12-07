
package daboia.ui;

import daboia.ui.list.ComponentList;
import daboia.ui.button.LabelButton;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;

public class ComponentListChooser <T> extends JDialog {
    
    private Window parent;
    private ComponentList<T> componentList;
    private T selected;
    
    private ComponentListChooser() {
        throw new UnsupportedOperationException();
    }
    
    public ComponentListChooser(ComponentList<T> list) {
        this(null, list);
    }
    
    public ComponentListChooser(Window parent, ComponentList<T> list) {
        super(parent, "Chooser", Dialog.DEFAULT_MODALITY_TYPE);
        
        this.componentList = list;
        this.parent = parent;
        this.selected = null;
        
        addContents(this.getContentPane());
    }
    
    public T showDialog() {
        this.pack();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);        
        return selected;
    }
    
    private void addContents(Container container) {
        container.setLayout(new MigLayout("", "[grow]", "[grow]"));
        
        SelectAction selectAction = new SelectAction("Select");        
        LabelButton selectButton = new LabelButton(selectAction, true);
        
        container.add(componentList, "north, width 220, span");
        container.add(new PlainSeparator(true), "north, growx, span");
        container.add(selectButton, "gaptop 10, gapbottom 10, center, grow, span");
    }

    private class SelectAction extends AbstractAction {
        public SelectAction(String name) {
            super(name);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            selected = componentList.getSelected();
            dispose();
        }            
    };
    
}
