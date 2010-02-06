/*
 * @version 0.0 24.02.2009
 * @author Tobse F
 */
package xswing;

import java.awt.Point;
import java.util.*;
import lib.mylib.object.SObject;
import lib.mylib.util.Clock;
import com.sun.xml.internal.bind.v2.schemagen.episode.Klass;

public class LocationController {

	public enum GameComponentLocation {
		CENTER, LEFT, RIGHT
	};

	private static boolean multiplayer = false;
	private List<SObject> objectList = new ArrayList<SObject>();
	private HashMap<Class<?>, Point> positions = new LinkedHashMap<Class<?>, Point>();

	public LocationController(GameComponentLocation location) {
		multiplayer = (location == GameComponentLocation.LEFT || location == GameComponentLocation.RIGHT);
		switch (location) {
		case CENTER:
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
			positions.put(BallCounter.class, new Point(222, 25));
			positions.put(BallTable.class, new Point(12, 316));
			positions.put(Level.class, new Point(489, 10));
			positions.put(Cannon.class, new Point(12, 180));
			positions.put(SeesawTable.class, new Point(-13, 733));
			positions.put(HighScoreCounter.class, new Point(420, 24));
			break;

		case RIGHT:
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
		return multiplayer ? 12 : 16;
	}

	public Point getLocation(Klass klass) {
		return positions.get(klass);
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