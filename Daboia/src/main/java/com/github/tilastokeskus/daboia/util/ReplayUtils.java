
package com.github.tilastokeskus.daboia.util;

import com.github.tilastokeskus.daboia.core.game.SavedStateGame;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class ReplayUtils {

    private ReplayUtils() {}
    
    public static String getReplayName() {
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        return "replay-" + sdf.format(cal.getTime()) + ".dab";
    }
    
    public static void saveReplay(SavedStateGame game, String path)
            throws FileNotFoundException, IOException {
        try (FileOutputStream fileOut = new FileOutputStream(path);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(game);
        }
    }
    
    public static SavedStateGame loadReplay(String path)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(path);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (SavedStateGame) in.readObject();
        }
    }
    
}
