/*
 * @version 0.0 02.03.2009
 * @author 	Tobse F
 */
package xswing.extras;

import xswing.Ball;
import xswing.BallTable;
import xswing.EffectCatalog;

public class Extra extends Ball{

	public Extra(int nr, int x, int y, BallTable ballTable, EffectCatalog effectCatalog) {
		super(x, y);
		this.nr = nr;
		this.weight = 0;
		this.ballTable = ballTable;
		this.effectCatalog = effectCatalog;
	}

}