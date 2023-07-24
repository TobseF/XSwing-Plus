/*
 * @version 0.0 25.04.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.object.SObject;
import org.newdawn.slick.*;
import xswing.ball.*;

/** Draws the weight sum per column on the screen */
public class SeesawTable extends SObject {

	private final Font font;
	private BallTable ballTable;
	private int[] weights = new int[8];
	private int gapBetweenBalls;

	public SeesawTable(Font font, BallTable ballTable) {
		this.font = font;
		this.ballTable = ballTable;
		gapBetweenBalls = LocationController.getGapBetweenBalls();
	}

	@Override
	public void render(Graphics g) {
		for (int i = 0; i < 8; i++) {
			font.drawString(x + (gapBetweenBalls + Ball.A) * (i + 1) - font.getWidth(weights[i] + "") / 2, y,
					weights[i] + "");
		}
	}

	@Override
	public void update(int delta) {
		for (int i = 0; i < 8; i++) {
			weights[i] = ballTable.getColumnWeight(i);
		}
	}

	public int[] getWeights() {
		return weights;
	}

}