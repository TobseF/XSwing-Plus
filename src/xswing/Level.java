/*
 * @version 0.0 25.04.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.*;
import lib.mylib.SpriteSheet;
import lib.mylib.object.Resetable;
import org.newdawn.slick.*;

/** The ball which counts the levels. Level can be 1-45 */
public class Level extends Ball implements Resetable {

	/** The Level with wich the game starts (only for reset) */
	private int startLeveL;
	/** The Highest reachable level */
	private static final int MAX_LEVEL = 45;

	private EffectBlinking blinking;

	/*
	 * /A list of Balls which will be released within the next move -for Levels private
	 * ArrayList<Integer>preDefinedBalls=new ArrayList<Integer>();
	 */

	public Level(int level, SpriteSheet ballsSpriteSheet, Font font) {
		super(level, 0, 0, ballsSpriteSheet);
		startLeveL = level;
		this.font = font;
		setLevel(level);
		blinking = new EffectBlinking(8, 300, true);
	}

	public void reset() {
		setLevel(startLeveL);
		blinking.reset();
	}

	/**
	 * Set the current level
	 * 
	 * @param level
	 * @see #getLevel()
	 */
	public void setLevel(int level) {
		setNr(level - 1);
		weight = level;
	}

	@Override
	public void update(int delta) {
		blinking.update(delta);
	}

	@Override
	public void render(Graphics g) {
		if (isVisible && blinking.getBlink()) {
			super.render(g);
		}
	}

	/** Returns the current level */
	public int getLevel() {
		return weight;
	}

	/** Switch to the next level */
	public void nextLevel() {
		if (!(getNr() + 1 >= MAX_LEVEL)) {
			setNr(getNr() + 1);
			weight = getNr() + 1;
			blinking.reset();
		}
	}

	@Override
	public String toString() {
		return "Level: " + weight;
	}
}