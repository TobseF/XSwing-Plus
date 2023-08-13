/*
 * @version 0.0 15.04.2008
 * @author Tobse F
 */
package lib.mylib.object;

import org.newdawn.slick.Graphics;

/**
 * For things that can be drawn on the screen.
 *
 * @author Tobse
 */
public interface Drawable {

    /**
     * All draw actions shoulb be executed here
     *
     * @param g The Grahics to draw on
     */
    void render(Graphics g);
}