/*
 * @version 0.0 29.11.2010
 * @author 	Tobse F
 */
package lib.mylib.object;

import org.newdawn.slick.Image;


public class SAreaObject extends SObject {
    protected int height;
    protected int width;

    public SAreaObject() {

    }

    /**
     * SObject with position
     *
     * @param image default image which is drawn with draw
     * @param x
     * @param y
     */
    public SAreaObject(Image image, int x, int y) {
        super(x, y);
        this.image = image;
    }

    /**
     * SObject with position
     *
     * @param x
     * @param y
     */
    public SAreaObject(int x, int y) {
        super(x, y);
    }

    /**
     * SObject with position
     *
     * @param x
     * @param y
     */
    public SAreaObject(int x, int y, int width, int height) {
        super(x, y);
        this.height = height;
        this.width = width;
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


}
