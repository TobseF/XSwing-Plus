/*
 * @version 0.0 05.05.2008
 * @author Tobse F
 */
package xswing.extras;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import xswing.EffectCatalog;
import xswing.ball.Ball;
import xswing.ball.BallTable;

import static lib.mylib.options.Paths.RES_DIR;

public class ExtraJoker extends Extra {

    public ExtraJoker(int x, int y, BallTable ballTable, EffectCatalog effectCatalog) {
        super(99, x, y, ballTable, effectCatalog);
        try {
            setImage(new Image(RES_DIR + "joker.png"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean compare(Ball ball) {
        return lastVisitor == null || (lastVisitor != null && lastVisitor.getNr() == ball.getNr())
                || ball instanceof ExtraJoker;
    }

    @Override
    protected void drawNumber(Graphics g) {
        // do nothing
    }

    @Override
    public int getNr(Ball visitor) {
        lastVisitor = visitor;
        return visitor.getNr();
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        lastVisitor = null;
    }

    private Ball lastVisitor = null;
}
