/*
 * @version 0.0 30.05.2008
 * @author Tobse F
 */
package xswing;

import java.util.*;
import lib.mylib.MyTimer;
import lib.mylib.object.*;
import xswing.EffectCatalog.particleEffects;
import xswing.events.*;
import xswing.events.BallEvent.BallEventType;

public class BallKiller implements Resetable, Updateable, BallEventListener {

	private List<Ball> ballsToKill = new ArrayList<Ball>();
	private static final int WAITING_BEFORE_KILL = 320;
	private Mechanics mechanics;
	private HighScoreCounter score;
	private MyTimer timer;
	private EffectCatalog effectCatalog;

	public BallKiller(Mechanics mechanics, HighScoreCounter score, EffectCatalog effectCatalog) {
		this.mechanics = mechanics;
		this.score = score;
		this.effectCatalog = effectCatalog;
		timer = new MyTimer(WAITING_BEFORE_KILL, false, false) {

			@Override
			protected void timerAction() {
				killBalls();
			}
		};
		reset();
	}

	public void addBall(Ball ball) {
		if (!ballsToKill.contains(ball)) {
			ballsToKill.add(ball);
			timer.reset();
		}
		timer.start();
	}

	@Override
	public void reset() {
		timer.reset();
	}

	@Override
	public void update(int delta) {
		timer.update(delta);
	}

	private void killBalls() {
		Ball bTemp = ballsToKill.get(0);
		score
				.score(mechanics.calculateScore(ballsToKill = mechanics
						.getConnectedBalls(bTemp)));
		for (int i = 0; i < ballsToKill.size(); i++) {
			Ball b = ballsToKill.get(i);
			if (i == 0) {
				b.fireBallEvent(BallEventType.BALL_EXPLODED);
			}
			effectCatalog.addEffect(b, particleEffects.EXPLOSION); // TODO: move to EffectLib
			b.fireBallEvent(BallEventType.BALL_CAUGHT_BY_EXPLOSION);
		}
		ballsToKill.clear();
		reset();
	}

	private void explodeRow() {
	// TODO: implements row exploding
	}

	@Override
	public void ballEvent(BallEvent e) {
	// if(e.getSource())

	}
}