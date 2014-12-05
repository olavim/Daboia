
package daboia.util;

import java.awt.Color;
import java.util.Random;

public class RandomColor {
    
    private final Random generator;
    private final Color mix;
    
    public RandomColor() {
        this(null);
    }
    
    public RandomColor(Color mix) {
        this.generator = new Random();
        this.mix = mix;
    }
    
    public Color nextColor() {
        return nextColor(0, 255, 0, 255, 0, 255);
    }
    
    public Color nextColor(int min) {
        return nextColor(min, 255, min, 255, min, 255);
    }
    
    public Color nextColor(int min, int max) {
        return nextColor(min, max, min, max, min, max);
    }
    
    public Color nextColor(int minR, int minG, int minB) {
        return nextColor(minR, 255, minG, 255, minB, 255);
    }
    
    public Color nextColor(int minR, int maxR, int minG, int maxG, int minB, int maxB) {
        int red = generator.nextInt(maxR - minR) + minR;
        int green = generator.nextInt(maxG - minG) + minG;
        int blue = generator.nextInt(maxB - minB) + minB;
        
        if (this.mix != null) {
            red = (red + this.mix.getRed()) / 2;
            green = (green + this.mix.getGreen()) / 2;
            blue = (blue + this.mix.getBlue()) / 2;
        }
        
        return new Color (red, green, blue);
    }

}
