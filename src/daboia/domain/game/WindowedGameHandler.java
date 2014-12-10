
package daboia.domain.game;

import daboia.ui.GameWindow;

public abstract class WindowedGameHandler extends GameHandler {
    
    private final GameWindow window;

    public WindowedGameHandler(DaboiaGame game) {
        super(game);
        this.window = new GameWindow(this);
    }
    
    public GameWindow getWindow() {
        return this.window;
    }
    
    public void showWindow() {
        this.window.showWindow();
    }
    
    public void refreshWindow() {
        this.window.refresh();
    }
    
}
