/*
 * @version 0.0 28.04.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.MyTimer;
import lib.mylib.object.*;
import org.newdawn.slick.*;

/**
 * Draws and returns the score multiplicatorSprites which can be set to the maximum of four and decrease automatically
 */
public class HighScoreMultiplicator extends SObject implements Resetable {

	private SpriteSheet multiplicatorSprites;
	/** The ScoreMuliplication value (for the HighScoreFormatter calculation) */
	private int multiplicator = 1;
	/** Time in ms, before for one light burns out */
	private int timerStep = 2500;

	private MyTimer timer;
	private Sound score;

	public HighScoreMultiplicator(SpriteSheet multiplicatorSprites) {
		this.multiplicatorSprites = multiplicatorSprites;
		timer = new MyTimer(timerStep, true, false) {

			@Override
			protected void timerAction() {
				multiply();
			}
		};
	}

	/** Sets the score multiplicatorSprites to 4 */
	public void score() {
		timer.reset();
		multiplicator = 4;
		timer.start();
		if(score!=null){
			score.play();
		}
	}

	@Override
	public void update(int delta) {
		timer.update(delta);
	}

	private void multiply() {
		if (multiplicator > 1) {
			multiplicator--;
		} else {
			timer.reset();
		}
	}

	@Override
	public void render(Graphics g) {
		if (isVisible && multiplicator > 1) {// Only multiplicator states 2,3 & 4 are in the spritesheet
			g.drawImage(multiplicatorSprites.getSprite(0, 4 - multiplicator), x, y);
		}
	}

	/** Returns the Score multiplication factor */
	public int getMulti() {
		return multiplicator;
	}

	@Override
	public void reset() {
		multiplicator = 1;
		timer.reset();
	}
}