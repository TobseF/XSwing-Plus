/*
 * @version 0.0 15.04.2008
 * @author Tobse F
 */
package xswing;

import java.awt.Point;
import javax.swing.event.EventListenerList;
import lib.mylib.SpriteSheet;
import lib.mylib.object.SObject;
import org.newdawn.slick.*;
import xswing.EffectCatalog.particleEffects;
import xswing.events.*;
import xswing.events.BallEvent.BallEventType;

/** The ball which moves on the screen. It can be stored in a BallTable */
public class Ball extends SObject implements Cloneable {

	/** Font for drawing the weight */
	protected Font font;
	/** The number of the ball (0-49) */
	protected int nr = 0;
	/** The weight of the ball */
	protected int weight = 0;
	/** The effect number of the ball (1=hidden) */
	private int effect = 0;
	/** Pixels to move in one update step */
	private int speed = 20;
	/** If the balls has to to be moved in update */
	private boolean moving = false;

	/** The directions in which the Ball can move */
	private enum MovingDirection {
		UP, DOWN, LEFT, RIGHT
	};

	/** The current moving type */
	private MovingDirection movingType = MovingDirection.DOWN;

	protected BallTable ballTable = null;
	protected EffectCatalog effectCatalog = null;
	private SpriteSheet ballsSpriteSheet = null;

	private EventListenerList eventListenerList = new EventListenerList();

	/** Lenght of an edge */
	public static final int A = 48;

	/**
	 * @param level
	 * @param x Position on screen
	 * @param y Position on screen
	 * @param balls Sprite sheet for 45 ball levels
	 */
	public Ball(int level, int x, int y, SpriteSheet balls) {
		super(x, y);
		nr = (int) (Math.random() * level);
		weight = 1 + (int) (Math.random() * (level + 1));
		ballsSpriteSheet = balls;
		if (balls != null) {
			setImage(ballsSpriteSheet.getSprite(nr));
		}
	}

	public Ball(int x, int y) {
		super(x, y);
	}

	/**
	 * @param nr
	 * @param wieght
	 * @param x
	 * @param y
	 * @param balls
	 */
	public Ball(int nr, int wieght, int x, int y, SpriteSheet balls) {
		super(x, y);
		this.nr = nr + 1;
		weight = wieght;
		ballsSpriteSheet = balls;
		if (balls != null) {
			setImage(ballsSpriteSheet.getSprite(nr));
		}
	}

	public Ball(int level, int x, int y) {
		this(level, x, y, null);
	}

	public Ball(int nr) {
		weight = nr;
		this.nr = nr + 1;
	}

	/**
	 * Sets the {@link Font} which is used to draw the {@link #weight}
	 * 
	 * @param font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public Ball clone() {
		Ball newBall = null;
		try {
			newBall = (Ball) super.clone();
		} catch (CloneNotSupportedException e) {}
		return newBall;
	}

	public void setEffects(EffectCatalog effectCatalog) {
		this.effectCatalog = effectCatalog;
	}

	public void setBallsSpriteSheet(SpriteSheet ballsSpriteSheet) {
		this.ballsSpriteSheet = ballsSpriteSheet;
		setNr(nr);
	}

	public void setBallTable(BallTable ballTable) {
		this.ballTable = ballTable;
	}

	/** Returns the effect number of the ball */
	public int getEffect() {
		return effect;
	}

	/** Returns the number of the ball */
	public int getNr() {
		return nr;
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		drawNumber(g);
	}

	/** Draws the weight number on the Ball */
	protected void drawNumber(Graphics g) {
		if (font != null) {
			font.drawString(x + A / 2 - font.getWidth(weight + "") / 2 - 1, y + 21, weight
					+ "");
		}
	}

	/** Returns the weight of the ball */
	public int getWeight() {
		return weight;
	}

	/** Returns wether the Ball is moving */
	public boolean isMoving() {
		return moving;
	}

	/**
	 * Changes the moving BallState and (if necessary) removes the ball from the BallTable
	 */
	public void toggleMoving() {
		moving = !moving;
		if (moving && ballTable.isOverGrid(x, y)) {
			// when moving ball is remomved from the BallTable
			ballTable.removeBall(this);
		}
	}

	/** Sets the falling speed */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setSpiteSheet(SpriteSheet spriteSheet) {
		this.ballsSpriteSheet = spriteSheet;
		setNr(nr);
	}

	/** Checks wether the given ball has the same nr, 99 is the Joker */
	public boolean compare(Ball ball) {
		return getNr() == ball.getNr() || ball.getNr() == 99;
	}

	/**
	 * Sets a <code>nr</code> to the ball -it also changes the icon. Only values between 0 and
	 * 45 are allowed.
	 */
	public void setNr(int nr) {
		if (nr >= 0 && nr < 44) {
			this.nr = nr;
			if (ballsSpriteSheet != null) {
				image = ballsSpriteSheet.getSprite(nr);
			}
		} else
			throw new IllegalArgumentException("0>= nr <= 44 !");
	}

	/** Returns the current sped of the ball */
	public int getSpeed() {
		return speed;
	}

	/** Returns the weight of the ball */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public void update(int delta) {
		if (moving) {
			switch (movingType) {
			case UP:
				break;
			case DOWN:
				y += speed;
				if (isCollidingWithBall() || isCollidingWithSoil()) {
					collide();
				}
				break;
			case LEFT:
				break;
			case RIGHT:
				break;
			}
		} else if (ballTable.isOverGrid(x, y)
				&& !(isCollidingWithBall() || isCollidingWithSoil())) {
			toggleMoving();
		}

	}

	@Override
	public String toString() {
		return "[" + getNr() + "]";
	}

	/**
	 * Checks if the ball itersects the ground
	 * 
	 * @return <code>true</code> if the {@link Ball} collides with the soil
	 */
	private boolean isCollidingWithSoil() {
		return ballTable.getField(x, y).y == 0;
	}

	/**
	 * Checks if the ball collides with another ball
	 * 
	 * @return <code>true</code> if the {@link Ball} collides with another ball
	 */
	private boolean isCollidingWithBall() {
		Point positionInBallTable = ballTable.getField(x, y);
		return !isCollidingWithSoil() && positionInBallTable.y <= 8
				&& !ballTable.isEmpty(positionInBallTable.x, positionInBallTable.y - 1);
	}

	/** Performs a collsion with a ball below */
	private void collide() {
		toggleMoving();
		ballTable.addBall(this);
		if (isCollidingWithSoil())
			notifyListener(new BallEvent(this, this, BallEventType.BALL_HITS_GROUND));
		if (isCollidingWithBall())
			notifyListener(new BallEvent(this, this, BallEventType.BALL_HITS_BALL));

		if (effectCatalog != null) {
			effectCatalog.addEffect(this, particleEffects.BOUNCING);
		}
	}

	/**
	 * Checks wether the ball has the same Nr (Color)
	 * 
	 * @param ball Ball to check with
	 * @return wether the ball has the same Nr as the given
	 */
	public boolean isSameNr(Ball ball) {
		if (ball == null) {
			return false;
		} else {
			return ball.getNr() == getNr();
		}
	}

	/**
	 * Adds an {@code BallEventListener} to the Ball.
	 * 
	 * @param listener the {@code BallEventListener} to be added
	 */
	public void addBallEventListener(BallEventListener listener) {
		eventListenerList.add(BallEventListener.class, listener);
	}

	/**
	 * Removes an {@code BallEventListener} from the Ball
	 * 
	 * @param listener to be removed
	 */
	public void removeBallEventListener(BallEventListener listener) {
		eventListenerList.remove(BallEventListener.class, listener);
	}

	/**
	 * Notifies all {@code BallEventListener}s about a {@code BallEvent}
	 * 
	 * @param event the {@code BallEvent} object
	 * @see EventListenerList
	 */
	protected synchronized void notifyListener(BallEvent event) {
		for (BallEventListener l : eventListenerList.getListeners(BallEventListener.class))
			l.ballEvent(event);
	}

	/**
	 * Fires a ball Event. All Listeners will be nitified and if registered the event will be
	 * executed.
	 * 
	 * @param eventType event to execute
	 */
	protected void fireBallEvent(BallEventType eventType) {
		notifyListener(new BallEvent(this, this, eventType));
	}

}