/*
 * @version 0.0 15.04.2008
 * @author Tobse F
 */
package xswing.ball;

import lib.mylib.SpriteSheet;
import lib.mylib.math.Point;
import lib.mylib.object.SObject;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.Mover;
import xswing.EffectCatalog;
import xswing.EffectCatalog.EffectType;
import xswing.events.BallEvent;
import xswing.events.BallEvent.BallEventType;
import xswing.events.BallEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * The ball which moves on the screen. It can be stored in a BallTable
 */
public class Ball extends SObject implements Cloneable, Mover {

    /**
     * Font for drawing the weight
     */
    protected Font font;
    /**
     * The number of the ball (0-49)
     */
    protected int nr = 0;
    /**
     * The weight of the ball
     */
    protected int weight = 0;
    /**
     * The effect number of the ball (1=hidden)
     */
    private final int effect = 0;
    /**
     * Pixels to move in one update step
     */
    private final int speed = 20;
    /**
     * If the balls has to to be moved in update
     */
    private boolean moving = false;

    public int fontCorrection = 21;

    private final int id;
    private static int id_counter = 0;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    public static final Ball NO_BALL = new Ball(0);


    /**
     * The directions in which the Ball can move
     */
    private enum MovingDirection {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * The current moving type
     */
    private final MovingDirection movingType = MovingDirection.DOWN;

    protected BallTable ballTable = null;
    protected EffectCatalog effectCatalog = null;
    private SpriteSheet ballsSpriteSheet = null;

    private final List<BallEventListener> eventListenerList = new LinkedList<BallEventListener>();

    /**
     * Length of an edge
     */
    public static int A;

    public Ball(int x, int y) {
        super(x, y);
        id = id_counter++;
    }

    public Ball(int level, int x, int y) {
        this(level, x, y, null);
    }

    /**
     * @param level
     * @param x     Position on screen
     * @param y     Position on screen
     * @param balls Sprite sheet for 45 ball levels
     */
    public Ball(int level, int x, int y, SpriteSheet ballsSpriteSheet) {
        this(x, y);
        nr = (int) (Math.random() * level);
        weight = 1 + (int) (Math.random() * (level + 1));
        this.ballsSpriteSheet = ballsSpriteSheet;
        if (ballsSpriteSheet != null) {
            setImage(ballsSpriteSheet.getSprite(nr));
        }
    }

    /**
     * @param nr
     * @param wieght
     * @param x
     * @param y
     * @param balls
     */
    public Ball(int nr, int weight, int x, int y, SpriteSheet ballsSpriteSheet) {
        this(x, y);
        this.nr = nr + 1;
        this.weight = weight;
        this.ballsSpriteSheet = ballsSpriteSheet;
        if (ballsSpriteSheet != null) {
            setImage(ballsSpriteSheet.getSprite(nr));
        }
    }

    public Ball(int nr) {
        this(0, 0);
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

    /**
     * Returns the effect number of the ball
     */
    public int getEffect() {
        return effect;
    }

    /**
     * Returns the number (color) of the ball
     */
    public int getNr() {
        return nr;
    }

    /**
     * @param visitor the ball which wants to know this {@link #nr}
     * @return the number (color) of this ball
     * @see #getNr()
     */
    public int getNr(Ball visitor) {
        return getNr();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        drawNumber(g);
    }

    /**
     * Draws the weight number on the Ball
     */
    protected void drawNumber(Graphics g) {
        if (font != null) {
            font.drawString(x + (A / 2) - (font.getWidth(weight + "") / 2) - 1, y + fontCorrection, weight + "");
        }
    }

    /**
     * Returns the weight of the ball
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Returns whether the Ball is moving
     */
    public boolean isMoving() {
        return moving;
    }

    public void setSpiteSheet(SpriteSheet spriteSheet) {
        ballsSpriteSheet = spriteSheet;
        setNr(nr);
    }

    /**
     * Sets a {@link #nr} to the ball -it also changes the icon. Only values between 0 and 45
     * are allowed.
     */
    public void setNr(int nr) {
        if (nr < 0 || nr > 44) {
            throw new IllegalArgumentException("0>= nr <= 44 !");
        }
        this.nr = nr;
        if (ballsSpriteSheet != null) {
            image = ballsSpriteSheet.getSprite(nr);
        }
    }

    /**
     * Returns the weight of the ball
     */
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
                    moveDown();
                    break;
                case LEFT:
                    break;
                case RIGHT:
                    break;
            }
        } else if (ballTable.isOverGrid(x, y)) {
            Point ballPosInTable = ballTable.getField(x, y);
            if (!(isCollidingWithBall(ballPosInTable) || isCollidingWithSoil(ballPosInTable))) {

                toggleMoving();
            }
        }
    }

    protected void moveDown() {
        y += speed;
        Point ballPosInTable = ballTable.getField(x, y);
        if (isCollidingWithBall(ballPosInTable) || isCollidingWithSoil(ballPosInTable)) {
            collide(ballPosInTable);
        }
    }

    /**
     * Changes the moving BallState and (if necessary) removes the ball from the BallTable
     */
    public void toggleMoving() {
        moving = !moving;
        if (moving) {
            ballTable.remove(this);
        }
    }

    /**
     * Checks if the ball intersects the ground
     *
     * @return <code>true</code> if the {@link Ball} collides with the soil
     */
    private boolean isCollidingWithSoil(Point ballPosInTable) {
        return ballPosInTable.y == 0;
    }

    /**
     * Checks if the ball collides with another ball
     *
     * @return <code>true</code> if the {@link Ball} collides with another ball
     */
    private boolean isCollidingWithBall(Point ballPosInTable) {
        return !isCollidingWithSoil(ballPosInTable) && ballPosInTable.y <= BallTable.LINES
                && !ballTable.isEmpty(ballPosInTable.x, ballPosInTable.y - 1);
    }

    /**
     * Ads the ball to the {@link BallTable} and performs a collision with a (ball | soil)
     * below.
     */
    private void collide(Point ballPosInTable) {
        toggleMoving();
        ballTable.addBall(this);
        if (isCollidingWithSoil(ballPosInTable)) {
            notifyListener(new BallEvent(this, this, BallEventType.BALL_HITS_GROUND));
        }
        if (isCollidingWithBall(ballPosInTable)) {
            notifyListener(new BallEvent(this, this, BallEventType.BALL_HITS_BALL));
        }
        if (effectCatalog != null) {
            effectCatalog.addEffect(this, EffectType.BOUNCING);
        }
    }

    /**
     * Adds an {@code BallEventListener} to the Ball.
     *
     * @param listener the {@code BallEventListener} to be added
     */
    public void addBallEventListener(BallEventListener listener) {
        eventListenerList.add(listener);
    }

    /**
     * Removes an {@code BallEventListener} from the Ball
     *
     * @param listener to be removed
     */
    public void removeBallEventListener(BallEventListener listener) {
        eventListenerList.remove(listener);
    }

    /**
     * Notifies all {@code BallEventListener}s about a {@code BallEvent}
     *
     * @param event the {@code BallEvent} object
     * @see EventListenerList
     */
    protected synchronized void notifyListener(BallEvent event) {
        for (BallEventListener l : eventListenerList) {
            l.ballEvent(event);
        }
    }

    /**
     * Fires a ball Event. All Listeners will be notified and if registered the event will be
     * executed.
     *
     * @param eventType event to execute
     */
    protected void fireBallEvent(BallEventType eventType) {
        notifyListener(new BallEvent(this, this, eventType));
    }

    @Override
    public String toString() {
        return String.format("Id:%d Col:%d Wgh:%d x:%3d y%3d", id, nr, weight, x, y);
    }

    /**
     * Checks whether the given ball has the same {@link #nr} (Color), or if the given is a
     * joker
     *
     * @param ball Ball to check with
     * @return whether the ball has the same {@link #nr} as the given, or the given is a joker#
     */
    public boolean compare(Ball ball) {
        return (ball != null && nr == ball.nr);
    }

    @Override
    public Ball clone() {
        Ball newBall = new Ball(nr, weight, x, y, ballsSpriteSheet);
        newBall.setFont(font);
        newBall.setEffects(effectCatalog);
        newBall.setBallTable(ballTable);
        return newBall;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Ball && ((Ball) obj).id == id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}