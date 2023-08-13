/*
 * @version 0.0 21.11.2008
 * @author Tobse F
 */
package lib.mylib.util;

import lib.mylib.SpriteSheet;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.awt.*;

public class FontStringsToSpiteSheetConverter {

    private final SpriteSheet spriteSheet;
    private final String[] strings;
    private final Font font;
    private Image image;
    private final Dimension fontBounds;

    public FontStringsToSpiteSheetConverter(Font font, String[] strings) {
        this.font = font;
        this.strings = strings;
        fontBounds = calculateMaxWidht();
        try {
            image = new Image(fontBounds.width, fontBounds.height * strings.length);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        drawStringsOnImage();
        spriteSheet = new SpriteSheet(image, fontBounds.width, fontBounds.height);
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    private void drawStringsOnImage() {
        Graphics g = null;
        try {
            g = image.getGraphics();
        } catch (SlickException e) {
            e.printStackTrace();
        }
        g.setFont(font);
        for (int i = 0; i < strings.length; i++) {
            g.drawString(strings[i], 0, i * fontBounds.height);
        }
        g.flush();
    }

    private Dimension calculateMaxWidht() {
        int maxWidth = 0, maxHeight = 0;
        for (String string : strings) {
            maxWidth = Math.max(maxWidth, font.getWidth(string));
            maxHeight = Math.max(maxHeight, font.getHeight(string));
        }
        return new Dimension(maxWidth, maxHeight);
    }

}