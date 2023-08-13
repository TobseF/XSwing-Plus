/*
 * @version 0.0 21.12.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.object.SObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Pause extends SObject {

    private final Color colorB;
    private final Color colorF;
    private final int height;
    private final int widht;
    private final String pause = "Pause";
    private final Font font;

    public Pause(Font font, int widht, int height) {
        this.height = height;
        this.widht = widht;
        this.font = font;
        colorB = Color.black;
        colorB.a = 0.6f;
        x = widht / 2 - font.getWidth(pause) / 2;
        y = height / 2 - font.getHeight(pause) / 2;
        colorF = Color.white;
    }

    @Override
    public void render(Graphics g) {

        if (isVisible) {
            g.setColor(colorB);
            g.fillRect(0, 0, widht, height);
            g.setColor(colorF);
            g.setFont(font);
            g.drawString(pause, x, y);
        }
    }

}