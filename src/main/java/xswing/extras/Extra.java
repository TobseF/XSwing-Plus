/*
 * @version 0.0 02.03.2009
 * @author Tobse F
 */
package xswing.extras;

import xswing.EffectCatalog;
import xswing.ball.Ball;
import xswing.ball.BallTable;

public class Extra extends Ball {

    public Extra(int nr, int x, int y, BallTable ballTable, EffectCatalog effectCatalog) {
        super(x, y);
        this.nr = nr;
        weight = 0;
        this.ballTable = ballTable;
        this.effectCatalog = effectCatalog;
    }

}