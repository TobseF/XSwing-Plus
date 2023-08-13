/*
 * @version 0.0 25.02.2009
 * @author Tobse F
 */
package xswing.extras;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import xswing.ball.Ball;

import static lib.mylib.options.Paths.RES_DIR;

public class Stone extends Ball {

    public Stone(int level, int x, int y) {
        super(level, x, y);
        nr = 100;
        weight = 0;
        try {
            setImage(new Image(RES_DIR + "stone.png"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean compare(Ball ball) {
        return false;
    }

    @Override
    protected void drawNumber(Graphics g) {
        // do nothing
    }

}
