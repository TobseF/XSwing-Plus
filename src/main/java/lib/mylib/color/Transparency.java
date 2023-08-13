/*
 * @version 0.0 20.12.2008
 * @author Tobse F
 */
package lib.mylib.color;

import org.newdawn.slick.Color;

public class Transparency {

    public static Color getTransparency(float alpha) {
        return new Color(255, 255, 255, alpha);
    }

    public static Color setTransparency(Color color, float alpha) {
        color.a = alpha;
        return color;
    }
}
