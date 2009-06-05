/*
 * @version 0.0 01.12.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.SpriteSheet;
import lib.mylib.object.*;
import org.newdawn.slick.Font;
import xswing.EffectCatalog.particleEffects;
import xswing.events.BallEventListener;
import xswing.extras.*;

/**
 * Provides factory mothods for creating balls and extras
 * 
 * @author Tobse
 */
public class BallFactory {

	private BallTable ballTable;
	private Font font;
	private SpriteSheet[] spriteSheet;
	private EffectCatalog effectCatalog;
	private Cannon canon;
	private int currentSpriteSheetNr = 0;
	private Level levelBall;
	private SObjectList ballsToMove;
	private BallEventListener ballEventListener;

	public BallFactory(BallEventListener ballEventListener, BallTable ballTable,
			SObjectList ballsToMove, Font font, SpriteSheet[] spriteSheet,
			EffectCatalog effectCatalog, Cannon canon, Level levelBall) {
		this.ballEventListener = ballEventListener;
		this.ballTable = ballTable;
		this.ballsToMove = ballsToMove;
		this.font = font;
		this.spriteSheet = spriteSheet;
		this.effectCatalog = effectCatalog;
		this.canon = canon;
		this.levelBall = levelBall;
	}

	private Ball getNewBall(int x, int y, int level) {
		Ball ball = new Ball(level, x, y, spriteSheet[currentSpriteSheetNr]);
		ball.setBallTable(ballTable);
		ball.setFont(font);
		ball.setEffects(effectCatalog);
		ballsToMove.add((SObject) ball);
		ball.addBallEventListener(ballEventListener);
		return ball;
	}

	public Ball getNewBall() {
		return getNewBall(canon.getX(), canon.getY(), levelBall.getLevel());
	}

	public void addNewJoker() {
		ExtraJoker ball = new ExtraJoker(canon.getX(), canon.getY(), ballTable, effectCatalog);
		ballsToMove.remove((SObject) canon.getBall());
		ballsToMove.add(ball);
		canon.setBall(ball);
		ball.addBallEventListener(ballEventListener);
		effectCatalog.addEffect(canon.getBall(), particleEffects.FLASH);
	}

	public void addNewStone() {
		Stone ball = new Stone(1, canon.getX(), canon.getY());
		ball.setBallTable(ballTable);
		ballsToMove.remove((SObject) canon.getBall());
		ballsToMove.add(ball);
		canon.setBall(ball);
		ball.addBallEventListener(ballEventListener);
		effectCatalog.addEffect(canon.getBall(), particleEffects.FLASH);
	}

	public void addTopBalls() {
		for (int row = 12; row > 10; row--) {
			for (int column = 0; column < 8; column++) {
				int[] pos = ballTable.getFieldPosOnScreen(column, row);
				Ball newBall = getNewBall(pos[0], pos[1], levelBall.getLevel());
				ballTable.setBall(column, row, newBall);
			}
		}
	}

	public SpriteSheet getSpriteSheet() {
		return spriteSheet[currentSpriteSheetNr];
	}

	public void toggleSpriteSheet() {
		currentSpriteSheetNr = currentSpriteSheetNr < spriteSheet.length - 1 ? currentSpriteSheetNr + 1
				: 0;

		for (int i = 0; i < ballsToMove.size(); i++) {
			if (ballsToMove.get(i).getClass().equals(Ball.class)) {
				((Ball) ballsToMove.get(i)).setSpiteSheet(getSpriteSheet());
			}
		}

		levelBall.setSpiteSheet(getSpriteSheet());
	}

	public void updateBalls(int delta) {
		for (int i = 0; i < ballsToMove.size(); i++) {
			Ball b = (Ball) ballsToMove.get(i);
			b.update(delta);
		}
	}

}