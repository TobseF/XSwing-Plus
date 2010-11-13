/*
 * @version 0.0 01.12.2008
 * @author Tobse F
 */
package xswing.ball;

import java.util.*;
import lib.mylib.SpriteSheet;
import lib.mylib.object.SObjectList;
import org.newdawn.slick.Font;
import xswing.*;
import xswing.EffectCatalog.particleEffects;
import xswing.events.BallEventListener;
import xswing.extras.*;

/**
 * Provides factory methods for creating balls and extras
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
	private List<BallEventListener> ballEventListener = new LinkedList<BallEventListener>();
	private int ballID =0;
	
	
	public BallFactory(BallTable ballTable,
			SObjectList ballsToMove, Font font, SpriteSheet[] spriteSheet,
			EffectCatalog effectCatalog, Cannon canon, Level levelBall) {
		this.ballTable = ballTable;
		this.ballsToMove = ballsToMove;
		this.font = font;
		this.spriteSheet = spriteSheet;
		this.effectCatalog = effectCatalog;
		this.canon = canon;
		this.levelBall = levelBall;
	}
	
	public void addBallEventListener(BallEventListener eventListener){
		ballEventListener.add(eventListener);
	}

	private Ball getNewBall(int x, int y, int level) {
		Ball ball = new Ball(level, x, y, spriteSheet[currentSpriteSheetNr]);
		ball.setBallTable(ballTable);
		ball.setFont(font);
		ball.setEffects(effectCatalog);
		ballsToMove.add(ball);
		ball.setId(ballID++);
		for(BallEventListener listener: ballEventListener){
			ball.addBallEventListener(listener);
		}
		return ball;
	}

	public Ball getNewBall() {
		return getNewBall(0,0, levelBall.getLevel());
	}

	public void addNewJoker() {
		ExtraJoker ball = new ExtraJoker(canon.getX(), canon.getY(), ballTable, effectCatalog);
		ballsToMove.remove(canon.getBall());
		ballsToMove.add(ball);
		canon.setBall(ball);
		for(BallEventListener listener: ballEventListener){
			ball.addBallEventListener(listener);
		}
		effectCatalog.addEffect(canon.getBall(), particleEffects.FLASH);
	}

	public void addNewStone() {
		Stone ball = new Stone(1, canon.getX(), canon.getY());
		ball.setBallTable(ballTable);
		ballsToMove.remove(canon.getBall());
		ballsToMove.add(ball);
		canon.setBall(ball);
		for(BallEventListener listener: ballEventListener){
			ball.addBallEventListener(listener);
		}
		effectCatalog.addEffect(canon.getBall(), particleEffects.FLASH);
	}

	public void addTopBalls() {
		for (int row = 12; row > 10; row--) {
			for (int column = 0; column < 8; column++) {
				Ball newBall = getNewBall(0,0, levelBall.getLevel());
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