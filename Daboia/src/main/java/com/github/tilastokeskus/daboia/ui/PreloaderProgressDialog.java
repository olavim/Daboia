
package com.github.tilastokeskus.daboia.ui;

import com.github.tilastokeskus.daboia.core.game.GamePreloader;
import java.awt.Frame;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

public class PreloaderProgressDialog implements GUI {

    private final JFrame frame;
    private final GamePreloader preloader;
    private final ProgressSlider slider;
    private final ScheduledExecutorService executor;
    
    public PreloaderProgressDialog(GamePreloader preloader) {
        this.frame = new JFrame("Preloader");
        this.preloader = preloader;
        this.executor = Executors.newSingleThreadScheduledExecutor();        
        this.slider = new ProgressSlider(0, 100, 1);
    }

    @Override
    public void run() {
        this.showWindow();
        this.executor.scheduleAtFixedRate(
                () -> slider.setPercent(preloader.getProgress()),
                0, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public void showWindow() {
        this.frame.setLayout(new MigLayout("", "[grow]", ""));
        this.frame.add(new JLabel("Loading..."), "wrap");
        this.frame.add(slider, "h 20, w 200, grow");
        this.frame.pack();
        this.frame.setLocationByPlatform(true);
        this.frame.setVisible(true);
    }

    @Override
    public void closeWindow() {
        this.executor.shutdown();
        this.frame.dispose();
    }

    @Override
    public Frame getFrame() {
        return this.frame;
    }

    @Override
    public void refresh() {
        frame.revalidate();
    }
    
}
