/*
 * @version 0.0 25.04.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.object.Countable;
import lib.mylib.object.Resetable;
import lib.mylib.object.SObject;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

/**
 * @author Tobse GUI class which draws the number of already released balls
 */
public class BallCounter extends SObject implements Resetable, Countable {

    private final Font font;
    private int balls = 0;
    private Level level;
    private final int letterLenght;
    private final int ballsPerLevel = 50;

    public BallCounter(Font font) {
        this.font = font;
        letterLenght = font.getWidth("0");
    }

    public void reset() {
        balls = 0;
    }

    public void count() {
        balls++;
        if (level != null) {
            if (balls % ballsPerLevel == 0) {
                level.nextLevel();
            }
        }
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public void render(Graphics g) {
        if (isVisible) {
            font.drawString(x - ((balls + "").length() - 1) * letterLenght / 2, y, "" + balls);
        }
    }

    public int getBalls() {
        return balls;
    }
}
