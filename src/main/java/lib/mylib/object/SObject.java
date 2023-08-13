/*
 * @version 0.0 15.04.2008
 * @author Tobse F
 */
package lib.mylib.object;

import lib.mylib.math.Point;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * A basic Object on the Screen, which can be moved and drawn
 */
public class SObject implements Drawable, Updateable, Positionable {

    /**
     * X and Y- position on the screen.
     */
    protected int x, y;
    protected int width, height;
    /**
     * Default image which is rendered in {@link #render(Graphics)}
     */
    protected Image image = null;

    /**
     * Whether the SObject should be rendered or not. Every SObject has to add this behavior by
     * it self. So it's not guaranteed that it's really invisible.
     */
    protected boolean isVisible = true;
    /**
     * Whether lifetime of the SObject is finished. Useful for the SObjectList which can remove
     * finished object automatically.
     *
     * @see #isFinished()
     * @see #finish()
     */
    private boolean finished;

    public SObject() {
    }

    public SObject(Image image) {
        this.image = image;
    }

    /**
     * Sets an image which is drawn with draw
     *
     * @param image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * SObject with position
     *
     * @param x
     * @param y
     */
    public SObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * SObject with position
     *
     * @param image default image which is drawn with draw
     * @param x
     * @param y
     */
    public SObject(Image image, int x, int y) {
        this(x, y);
        this.image = image;
    }

    /**
     * Sets the X-Position on the Screen
     *
     * @param x -in pixels
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the Y-Position on the Screen
     *
     * @param y -in pixels
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return x position
     */
    public int getX() {
        return x;
    }

    /**
     * @return y position
     */
    public int getY() {
        return y;
    }

    /**
     * Sets x and y position on screen
     *
     * @param pos array with x and y position (pos[0] = x, po[1] = y)
     */
    public void setPos(Point pos) {
        setPos(pos.x, pos.y);
    }

    /**
     * @return the position of the <code>SObject</code>
     */
    public Point getPosition() {
        return new Point(x, y);
    }

    /**
     * Sets x and y position on screen
     *
     * @param x
     * @param y
     */
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y);
        }
    }

    @Override
    public void update(int delta) {
    }

    /**
     * @return ifTheCompomentShouldBeDrawn
     * @see #finish()
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Sets whether the component should be drawn (true). It's depends on the draw()
     * implementation whether it would be considered.
     *
     * @param isVisible
     * @see #finish()
     */
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * Tags the <code>SObject</code> as finished. Useful for the <code>SObjectList</code> which
     * can remove finished object automatically.
     */
    public void finish() {
        finished = true;
    }

    /**
     * @return Whether the object is ready to be get removed
     */
    public boolean isFinished() {
        return finished;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SObject) {
            SObject object = (SObject) obj;
            return object.x == x && object.y == y && object.finished == finished && object.isVisible == isVisible;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 3 * x + 7 * y + (finished ? 1 : 0) + (isVisible ? 2 : 0);
    }

    public Image getImage() {
        return image;
    }

    public void translate(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setSize(int width, int height) {
        this.height = height;
        this.width = width;
    }
}