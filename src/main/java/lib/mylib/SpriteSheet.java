/*
 * @version 0.0 23.04.2008
 * @author Tobse F
 */
package lib.mylib;

import org.newdawn.slick.Image;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Extended version of the Slick Spritesheet class
 */
public class SpriteSheet extends org.newdawn.slick.SpriteSheet implements Iterable<Image> {

    protected int spriteWidth;
    protected int spriteHeight;

    public SpriteSheet(Image image, int spriteWidth, int spriteHeight) {
        super(image, spriteWidth, spriteHeight);
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
    }

    /**
     * Get get the sprite with the specifid number
     *
     * @param nr The number of the Sprite
     * @return The single {@link Image} of the Sprite Sheet
     */
    public Image getSprite(int nr) {
        int y = 0;
        if (nr > 0) {
            y = nr / getHorizontalCount();
        }
        int x = nr - (y * getHorizontalCount());
        return getSprite(x, y);
    }

    /**
     * Get get all Sprites of a specifid row
     *
     * @param row The row (Y-Pos.) of the Sprites
     * @return Array with Images
     */
    public Image[] getSprites(int row) {
        Image[] images = new Image[getHorizontalCount()];
        for (int i = 0; i < getHorizontalCount(); i++) {
            images[i] = getSprite(i, row);
        }
        return images;
    }

    /**
     * Get get all Sprites of a specifid row
     *
     * @param row The row (Y-Pos.) of the Sprites
     * @return Array with Images
     */
    public Image[] getSprites() {
        Image[] images = new Image[getSpriteCount()];
        for (int i = 0; i < getSpriteCount(); i++) {
            images[i] = getSprite(i);
        }
        return images;
    }

    /**
     * @return The number of Sprites in the SpriteSheet
     */
    public int getSpriteCount() {
        return getHorizontalCount() * getVerticalCount();
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    @Override
    public Iterator<Image> iterator() {
        return Arrays.asList(getSprites()).iterator();
    }
}
