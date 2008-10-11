package lib.mylib;

import org.newdawn.slick.Image;

public class SpriteSheet
extends org.newdawn.slick.SpriteSheet {
    public SpriteSheet(Image image, int tw, int th) {
        super(image, tw, th);
    }

    public Image getSprite(int nr) {
        int y = 0;
        if (nr > 0) {
            y = nr / this.getHorizontalCount();
        }
        int x = nr - y * this.getHorizontalCount();
        return this.getSprite(x, y);
    }

    public Image[] getSprites(int row) {
        Image[] images = new Image[this.getHorizontalCount()];
        int i = 0;
        while (i < this.getHorizontalCount()) {
            images[i] = this.getSprite(i, row);
            ++i;
        }
        return images;
    }

    public int getSpriteCount() {
        return this.getHorizontalCount() * this.getVerticalCount();
    }
}
