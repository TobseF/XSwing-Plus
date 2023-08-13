/*
 * @version 0.0 15.04.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.SpriteSheet;
import lib.mylib.object.Countable;
import lib.mylib.object.Resetable;
import lib.mylib.object.SObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import xswing.EffectCatalog.EffectType;
import xswing.ball.Ball;
import xswing.ball.BallTable;

/**
 * Provides a movable Cannon which releases the Balls
 */
public class Cannon extends SObject implements Resetable {

    /**
     * The current column
     */
    private int cannonPosition = 0;

    private final Countable ballCounter;

    private Ball ball = null;
    private final BallTable ballTable;
    private Sound move;
    private Sound stackingAlarm;
    private Sound dropBall;
    //	private SpriteSheet cannonSprites;
    private Animation animationWarning;
    private Animation animationDanger;
    /**
     * period of time of one animation frame
     */
    private final int duration = 180;
    private int positionCorrecionX;
    private int positionCorrecionY;
    private Image normal;
    private Image spriteSheet;

    public static final int ROW_WITH_ORANE_WARNING = 7;
    public static final int ROW_WITH_RED_WARNING = 8;

    private final EffectCatalog effectCatalog;

    private Ball lastreleasedBall = null;

    /**
     * @param cannons   Sprite sheet with four three frames
     * @param x         The x position of the cannon when it's over the first hole (extremist left)
     * @param y         The y position of the cannon when it's over the first hole (extremist left)
     * @param sounds    The moving sound (0) and the alarm sound
     * @param ballTable
     */
    public Cannon(BallTable ballTable,
                  Countable ballCounter, EffectCatalog effectCatalog) {
        this.ballTable = ballTable;
        this.effectCatalog = effectCatalog;
        this.ballCounter = ballCounter;
    }

    public void setSpites() {
        setSpites(new SpriteSheet(spriteSheet, getWidth(), getHeight()));
    }

    public void setSpites(SpriteSheet cannons) {
        animationWarning = new Animation(cannons.getSprites(1), duration);
        animationDanger = new Animation(cannons.getSprites(2), duration);
        animationWarning.start();
        animationWarning.setPingPong(true);
        animationDanger.start();
        animationDanger.setPingPong(true);
        normal = cannons.getSprite(0, 0);
    }

    public void setSoundDropBall(Sound dropBall) {
        this.dropBall = dropBall;
    }

    public void setSoundStackingAlarm(Sound stackingAlarm) {
        this.stackingAlarm = stackingAlarm;
    }

    public void setSoundMove(Sound move) {
        this.move = move;
    }

    /**
     * Moves the canon one step left
     */
    public void moveLeft() {
        if (cannonPosition >= 1) {
            cannonPosition--;
            move.play();
        }
        setBallPos(ball);
    }

    /**
     * Moves the canon one step right
     */
    public void moveRight() {
        if (cannonPosition < 7) {
            cannonPosition++;
            move.play();
        }
        setBallPos(ball);
    }

    @Override
    public int getX() {
        return x + ballTable.gapBetweenBalls + cannonPosition * (Ball.A + ballTable.gapBetweenBalls);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, getX() - positionCorrecionX, y - positionCorrecionY);
    }

    private void setWarningImage() {
        int ballPileHeight = ballTable.getColumnHeight(cannonPosition);
        if (ballPileHeight < ROW_WITH_ORANE_WARNING) {
            setImage(normal);
        } else if (ballPileHeight == ROW_WITH_ORANE_WARNING) {
            setImage(animationWarning.getCurrentFrame());
        } else if (ballPileHeight == ROW_WITH_RED_WARNING) {
            setImage(animationDanger.getCurrentFrame());
            if (!stackingAlarm.playing()) {
                stackingAlarm.play();
            }
        }
    }

    /**
     * Lets the ball start moving and loads the next Ball from the BallTable
     */
    public void releaseBall(Ball nextBall) {
        if (isReadyToReleaseBall()) {
            if (ball != null) {
                ball.toggleMoving(); // Ball should start falling
                ball.setPos(getX(), y + 30); // Set the Ball position
                lastreleasedBall = ball;
            }
            setBall(ballTable.getBall(cannonPosition, 11));
            ballTable.setBall(cannonPosition, 11, ballTable.getBall(cannonPosition, 12));
            ballTable.setBall(cannonPosition, 12, nextBall);
            ball.setPos(getX(), y - 22);
            effectCatalog.addEffect(ball, EffectType.FLASH);
            ballCounter.count();
            dropBall.play();
        }
    }

    /**
     * Returns the current column
     */
    public int getPos() {
        return cannonPosition;
    }

    /**
     * Returns the current Ball in the Cannon
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * Sets a current Ball to the Cannon
     */
    public void setBall(Ball ball) {
        this.ball = ball;
        setBallPos(ball);
    }

    /**
     * Sets the position from the Cannon to the Ball
     */
    private void setBallPos(Ball ball) {
        if (ball != null) {
            ball.setPos(getX(), ball.getY());
        }
    }

    @Override
    public void update(int delta) {
        animationWarning.update(delta);
        animationDanger.update(delta);
        setWarningImage();
        doBallEnterAnimation();
    }

    private void doBallEnterAnimation() {
        int ballPosition = y + 8;
        if (ball != null && ball.getY() < ballPosition) {
            ball.setY(ball.getY() + 3);
        }
    }

    @Override
    public void reset() {
        cannonPosition = 0;
        ball = null;
        lastreleasedBall = null;
    }

    public boolean isReadyToReleaseBall() {
        return lastreleasedBall == null || lastreleasedBall.getY() > getY() + 70;
    }
}