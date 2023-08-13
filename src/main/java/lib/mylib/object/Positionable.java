/*
 * @version 0.0 24.02.2009
 * @author Tobse F
 */
package lib.mylib.object;

public interface Positionable {

    /**
     * Sets the X-Position on the Screen
     *
     * @param x -in pixels
     */
    void setX(int x);

    /**
     * Sets the Y-Position on the Screen
     *
     * @param y -in pixels
     */
    void setY(int y);

    /**
     * @return x position
     */
    int getX();

    /**
     * @return y position
     */
    int getY();

}
