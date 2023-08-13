/*
 * @version 0.0 24.02.2009
 * @author Tobse F
 */
package xswing;

import lib.mylib.math.Point;
import lib.mylib.object.SObject;
import lib.mylib.util.Clock;
import xswing.ball.Ball;
import xswing.ball.BallTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class LocationController {

    public enum GameComponentLocation {
        CENTER, LEFT, RIGHT, ANDROID
    }

    private static boolean multiplayer = false;
    private final List<SObject> objectList = new ArrayList<SObject>();
    private final HashMap<Class<?>, Point> positions = new LinkedHashMap<Class<?>, Point>();

    private static int gapBetweenBalls = 16;

    public LocationController(GameComponentLocation location) {

        multiplayer = (location == GameComponentLocation.LEFT || location == GameComponentLocation.RIGHT);
        switch (location) {
            case ANDROID:
                Ball.A = 38;
                //Ball.fontCorrection = 16;
                BallTable.topBallYCorrection = 25;
                //HighScoreCounter.bonusLineSpace = 42;
                gapBetweenBalls = 11;
                //BallTable.gapBetweenBalls =gapBetweenBalls;
                positions.put(BallTable.class, new Point(199, 166));
                positions.put(Level.class, new Point(20, 10));
                positions.put(Cannon.class, new Point(199, 86));
                positions.put(HighScoreCounter.class, new Point(764, 38));
                positions.put(BallCounter.class, new Point(126, 20));
                positions.put(Clock.class, new Point(28, 439));
                positions.put(HighScoreMultiplicator.class, new Point(37, 74));
                positions.put(SeesawTable.class, new Point(1000, 1000));
                positions.put(HighScorePanel.class, new Point(1000, 1000));
                break;
            case CENTER:
                gapBetweenBalls = 16;
                positions.put(BallTable.class, new Point(248, 289));
                positions.put(Level.class, new Point(25, 15));
                positions.put(Cannon.class, new Point(248, 166));
                positions.put(SeesawTable.class, new Point(228, 723));
                positions.put(HighScoreCounter.class, new Point(970, 106));
                positions.put(HighScoreMultiplicator.class, new Point(59, 92));
                positions.put(HighScorePanel.class, new Point(980, 30));
                positions.put(BallCounter.class, new Point(160, 22));
                positions.put(Clock.class, new Point(85, 718));
                break;

            case LEFT:
                gapBetweenBalls = 12;
                positions.put(BallCounter.class, new Point(222, 25));
                positions.put(BallTable.class, new Point(12, 316));
                positions.put(Level.class, new Point(489, 10));
                positions.put(Cannon.class, new Point(12, 180));
                positions.put(SeesawTable.class, new Point(-13, 733));
                positions.put(HighScoreCounter.class, new Point(420, 24));
                break;

            case RIGHT:
                gapBetweenBalls = 12;
                positions.put(BallCounter.class, new Point(790, 25));
                positions.put(BallTable.class, new Point(520, 316));
                // positions.put(Level.class, new Point(774, 10));
                positions.put(Cannon.class, new Point(520, 180));
                positions.put(SeesawTable.class, new Point(497, 733));
                positions.put(HighScoreCounter.class, new Point(693, 24));
                break;

            default:
                break;
        }
    }

    public static void setMultiplayer(boolean multiplayer) {
        LocationController.multiplayer = multiplayer;
    }

    public static boolean isMultiplayer() {
        return multiplayer;
    }

    public static int getGapBetweenBalls() {
        return gapBetweenBalls;
    }

    public void resetPositions() {
        for (SObject object : objectList) {
            setLocationToObject(object);
        }
    }

    public void setLocationToObject(SObject object) {
        objectList.add(object);
        Point point = positions.get(object.getClass());
        if (point == null) { // throw new
            // IllegalArgumentException("Klass not in LocationController List");
            object.setVisible(false);
        } else {
            object.setX(point.x);
            object.setY(point.y);
        }
    }

}