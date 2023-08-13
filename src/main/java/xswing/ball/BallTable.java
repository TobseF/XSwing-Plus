/*
 * @version 0.0 16.04.2008
 * @author Tobse F
 */
package xswing.ball;

import lib.mylib.math.Point;
import lib.mylib.object.Resetable;
import lib.mylib.object.SObject;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import xswing.events.BallEvent;
import xswing.events.BallEvent.BallEventType;
import xswing.events.BallEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Saves the Balls in a table and links them to the table positions on the screen
 */
public class BallTable extends SObject implements Resetable, Cloneable, TileBasedMap {

    /**
     * Gap between the balls
     */
    public int gapBetweenBalls = 16;
    /**
     * Height and Weight of the BallTable in pixels
     */
    private final int height;
    /**
     * The height of a ball stack. The upper two lines are for the ball magazine.
     */
    public static final int ROWS = 13;
    // TODO: make field size Variable, make values clear (height == 13 ??)
    /**
     * The number of ball lines, which can be filled each with one stack.
     */
    public static final int LINES = 8;
    /**
     * BallTable with [lines][rows]. Field [0][0] is the first ball low in the left corner.
     * Field [0][1] is on top of the first.
     */
    private Ball[][] balls = new Ball[LINES][ROWS];


    public static int topBallYCorrection = 0;

    private static final String NEW_LINE = System.getProperty("line.separator");

    private final List<BallEventListener> listener = new LinkedList<BallEventListener>();

    // TODO: add ballTablechangedEvent

    public BallTable() {
        height = Ball.A * LINES;
//		gab_between_balls = LocationController.getGapBetweenBalls();
    }

    public Ball[][] getBalls() {
        return balls;
    }

    /**
     * Returns all balls in the given line with the given limits. Starts with minRow and ends
     * with first null element, or maxRow.
     *
     * @param line   number of the line (ball stack number)
     * @param minRow lowermost ball (inclusive) row (position in stack)
     * @param maxRow topmost ball (inclusive) row (position in stack)
     * @return all balls in the given row. The lowermost, will be the first.
     */
    public List<Ball> getBalls(int line, int minRow, int maxRow) {
        ArrayList<Ball> ballsInRow = new ArrayList<Ball>(ROWS);
        if (line < 0 || line >= LINES) {
            throw new IllegalArgumentException("Line " + line + " is outside BallTable");
        }
        if (minRow < 0 || maxRow < 0 || minRow > maxRow || maxRow >= ROWS) {
            throw new IllegalArgumentException("Row " + minRow + " or " + maxRow
                    + " is outside BallTable or minRow>maxRow. ");
        }
        for (int row = minRow; row <= maxRow && balls[line][row] != null; row++) {
            ballsInRow.add(balls[line][row]);
        }
        return ballsInRow;
    }

    /**
     * Returns all balls in the given line up to the given maxRow
     *
     * @param line   number of the line (ball stack number)
     * @param maxRow topmost ball row (position in stack)
     * @return all balls in the given row. The lowermost, will be the first.
     */
    public List<Ball> getBalls(int line, int maxRow) {
        return getBalls(line, 0, maxRow);
    }

    /**
     * Returns all balls in the given line
     *
     * @param line   number of the line (ball stack number)
     * @param maxRow topmost ball row (position in stack)
     * @return all balls in the given row. The lowermost, will be the first.
     */
    public List<Ball> getBalls(int line) {
        return getBalls(line, 0, LINES - 1);
    }

    /**
     * Sets a ball to the TabllTable on the given column
     */
    public void setBall(int x, int y, Ball ball) {
        if (ball != null) {
            ball.setPos(getFieldPosOnScreen(x, y));
            notifyListener(new BallEvent(this, ball, BallEventType.ADDED_TO_BALLTABLE, new Point(x, y)));
            if (y <= LINES) {
                notifyListener(new BallEvent(this, ball, BallEventType.ADDED_TO_PLAY_FIELD, new Point(x, y)));
            }
        }
        balls[x][y] = ball;
    }


    /**
     * @param x Position on screen in pixels
     * @param y Position on screen in pixels
     * @return Whether the position is over the BallTable play BallTable area, or not
     */
    public boolean isOverGrid(int x, int y) {
        return (y >= this.y - Ball.A);
    }

    /**
     * Sets a ball to the TabllTable, accordingly to its position.
     */
    public void addBall(Ball ball) {
        if (ball != null) {
            Point ballPositionInTable = getField(ball.getX(), ball.getY());
            setBall(ballPositionInTable.x, ballPositionInTable.y, ball);
        }
    }

    /**
     * Throws a ball into the BallTable and let it fall up to the ground or a ball beneath.
     *
     * @param ball which should be insert
     * @param line The number of ball line (Each can be filled with one stack).
     */
    public void addBall(Ball ball, int line) {
        if (balls[line][ROWS - 1] != null) {
            throw new StackOverflowError("Line " + line + " is full (" + ROWS + " rows)");
        }
        int firstTakenRow = ROWS - 1;
        for (; firstTakenRow > 0 && balls[line][firstTakenRow] == null; firstTakenRow--) {
        }
        if (balls[line][0] == null) {
            firstTakenRow = -1;
        }
        balls[line][firstTakenRow + 1] = ball;

        ball.setPos(getFieldPosOnScreen(line, firstTakenRow + 1));

        notifyListener(new BallEvent(this, ball, BallEventType.ADDED_TO_BALLTABLE, new Point(line, firstTakenRow + 1)));
    }

    /**
     * Returns a ball out of the BallTable or null if the Position is outside the PlayField
     * (0x0)-(7x7)
     *
     * @param x Position in the Grid
     * @param y Position in the Grid
     * @return <code>null</code> if no Ball is found
     */
    public Ball getBall(int x, int y) {
        if (isFieldInTable(x, y)) {
            return balls[x][y];
        } else {
            return null;
        }
    }

    /**
     * @param x
     * @param y
     * @return Whether the position lays in the ball table play size;
     */
    public static boolean isFieldInTable(int x, int y) {
        return x >= 0 && x < LINES && y >= 0 && y < ROWS;
    }

    @Override
    public BallTable clone() {
        BallTable balltable = new BallTable();
        balltable.balls = new Ball[LINES][ROWS];
        for (int y = 0; y < balls[0].length; y++) {
            for (int x = 0; x < LINES; x++) {
                balltable.balls[x][y] = balls[x][y] == null ? null : balls[x][y].clone();
            }
        }
        return balltable;
    }

    /**
     * Returns the field in which the ball resides.
     *
     * @param x Pixel coordinates
     * @param y Pixel coordinates
     * @return Point(x, y) respectively Point(line,row)
     */
    public Point getField(int x, int y) {
        int posX = (x - this.x + gapBetweenBalls) / (Ball.A + gapBetweenBalls);
        double posYTemp = (this.y + height - y) / (double) Ball.A;
        int posY = (int) posYTemp;
        if (posYTemp % 1 == 0) {// if it's exactly on the grid
            posY -= 1;
        }
        return new Point(posX, posY);
    }

    /**
     * Returns the field in which the Ball resides. <br>
     *
     * @return Point(x, y) respectively Point(line,row) according to the Balls pixel position
     */
    public Point getField(SObject ball) {
        return getField(ball.getX(), ball.getY());
    }

    /**
     * Checks, if the given field in the BallTable is empty
     *
     * @param posX of the BallTable
     * @param posY of the BallTable
     * @return if the given field in the BallTable is empty
     * @see #isEmpty(int[])
     */
    public boolean isEmpty(int posX, int posY) {
        return (balls[posX][posY] == null);
    }

    @Override
    public String toString() {
        StringBuilder ballTableAsString = new StringBuilder();
        for (int i = LINES; i > 0; i--) {
            for (Ball[] ball : balls) {
                if (ball[i - 1] != null) {
                    ballTableAsString.append(" ");
                    ballTableAsString.append(String.format("%02d", ball[i - 1].getNr()));
                } else {
                    ballTableAsString.append(" --");
                }
            }
            ballTableAsString.append(NEW_LINE);
        }
        return ballTableAsString.toString();
    }

    /**
     * Returns the coordinates of a given BallTable cell on the display
     */
    public Point getFieldPosOnScreen(int posX, int posY) {
        int yCorrection = 0;
        if (posY >= 11) {
            yCorrection += topBallYCorrection;//FIXME: What is topBallYCorrection?
        }
        return new Point(x + gapBetweenBalls + posX * (Ball.A + gapBetweenBalls), yCorrection + y + height - ((posY + 1) * Ball.A));
    }

    /**
     * Returns the coordinates of a given BallTable cell on the display
     */
    public Point getFieldPosOnScreen(Point point) {
        return getFieldPosOnScreen(point.x, point.y);
    }

    /**
     * Returns the coordinates of fields in the BallTable
     */
    public Point getFieldPos(int posX, int posY) {
        return new Point(gapBetweenBalls + posX * (Ball.A + gapBetweenBalls), height - posY * Ball.A);
    }

    /**
     * Returns the sum of all ball weight on the given column
     */
    public int getColumnWeight(int column) {
        int columnWeight = 0;
        for (int row = 0; row < LINES; row++) {
            if (balls[column][row] != null) {
                columnWeight += balls[column][row].getWeight();
            }
        }
        return columnWeight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BallTable) {
            return Arrays.deepEquals(balls, ((BallTable) obj).getBalls());
        } else {
            return super.equals(obj);
        }
    }

    /**
     * Returns the number of balls in the given column
     */
    public int getColumnHeight(int column) {
        int columnHeight = 0;
        for (int row = 0; row < LINES; row++) {
            if (balls[column][row] != null) {
                columnHeight++;
            }
        }
        return columnHeight;
    }

    /**
     * Clears all balls from the BallTable
     */
    @Override
    public void reset() {
        for (Ball[] b : balls) {
            Arrays.fill(b, null);
        }
    }

    @Override
    public boolean blocked(PathFindingContext context, int tx, int ty) {
        return balls[tx][ty] == null || !((Ball) context.getMover()).compare(balls[tx][ty]);
    }

    @Override
    public float getCost(PathFindingContext context, int tx, int ty) {
        return 1;
    }

    @Override
    public int getHeightInTiles() {
        return ROWS;
    }

    @Override
    public int getWidthInTiles() {
        return LINES;
    }

    @Override
    public void pathFinderVisited(int x, int y) {
        // no debug output is needed
    }

    public void addBallEventListerner(BallEventListener ballEventListener) {
        listener.add(ballEventListener);
    }

    /**
     * Notifies all {@code BallEventListener}s about a {@code BallEvent}
     *
     * @param event the {@code BallEvent} object
     * @see EventListenerList
     */
    protected void notifyListener(BallEvent event) {
        for (BallEventListener listenerInList : listener) {
            listenerInList.ballEvent(event);
        }
    }

    /**
     * Removes the given balls from the BallTable
     */
    public void remove(Ball ball) {
        Point position = getField(ball);
        balls[position.x][position.y] = null;
//		}
    }

}