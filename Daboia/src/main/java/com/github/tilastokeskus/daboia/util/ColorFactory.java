
package com.github.tilastokeskus.daboia.util;

import java.awt.Color;
import java.util.Random;

public class ColorFactory {
    
    private final static float GOLDEN_ANGLE = 137.5f;
    private final static int PALETTE_SIZE = 360;
    private final static Color[] colors;
    
    private static double index = new Random().nextDouble() * PALETTE_SIZE;
    
    static {
        colors = new Color[PALETTE_SIZE];
        for (int i = 0; i < PALETTE_SIZE; i++) {
            Color c = Color.getHSBColor(i / (float) PALETTE_SIZE, 0.3f, 0.85f );
            colors[i] = c;
        }
    }
    
    public static Color getNextColor() {
        index += GOLDEN_ANGLE;
        Color color = colors[(int) index % PALETTE_SIZE];
        
        return color;
    }
    
}
