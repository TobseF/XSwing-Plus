package lib;

import lib.Drawable;
import lib.Updateable;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class SObject
implements Drawable,
Updateable {
    protected int x;
    protected int y;
    protected Image pic = null;

    public SObject() {
    }

    public SObject(Image pic) {
        this.pic = pic;
    }

    public void setPic(Image pic) {
        this.pic = pic;
    }

    public SObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public SObject(Image pic, int x, int y) {
        this(x, y);
        this.pic = pic;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPos(int[] pos) {
        this.setPos(pos[0], pos[1]);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics g) {
        if (this.pic != null) {
            g.drawImage(this.pic, this.x, this.y);
        }
    }

    @Override
    public void draw(float x, float y) {
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void update() {
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public void update(int delta) {
    }
}
