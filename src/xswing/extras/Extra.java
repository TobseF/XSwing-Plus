/*
 * @version 0.0 02.03.2009
 * @author Tobse F
 */
package xswing.extras;

import xswing.*;

public class Extra extends Ball {

	public Extra(int nr, int x, int y, BallTable ballTable, EffectCatalog effectCatalog) {
		super(x, y);
		this.nr = nr;
		weight = 0;
		this.ballTable = ballTable;
		this.effectCatalog = effectCatalog;
	}

}