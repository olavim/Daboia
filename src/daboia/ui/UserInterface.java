package daboia.ui;

import javax.swing.JFrame;

public interface UserInterface extends Runnable {    
    public void showWindow();            
    public JFrame getFrame();    
}
