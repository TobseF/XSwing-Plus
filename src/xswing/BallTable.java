/*
 * @version 0.0 16.04.2008
 * @author Tobse F
 */
package xswing;

import java.awt.Point;
import java.util.Arrays;

import lib.mylib.object.Resetable;
import lib.mylib.object.SObject;

/**
 * Saves the Balls in a table and links them to the table positions on the screen
 */
public class BallTable extends SObject implements Resetable, Cloneable {
	/** Ball side lenght */
	private int ballA;
	/** gab between the balls */
	public int gab_between_balls = 16;
	/** Hight and Weight of the Ball Table in pixels */
	private int height;
	private Ball[][] balls = new Ball[8][13];
	/** The last Ball wich was insert into the <code>BallTable</code>*/
	private Ball lastInsertBall;

	//TODO: add ballTablechangedEvent
	
	public BallTable() {
		ballA = Ball.A;
		height = ballA * 8;
		gab_between_balls = LocationController.getGapBetweenBalls();
	}

	public Ball[][] getBalls() {
		return balls;
	}

	/** Sets a ball to the TabllTable on the given column */
	public void setBall(int x, int y, Ball ball) {
		if (ball != null) {
			ball.setPos(getFieldPosOnScreen(x, y));
		}
		balls[x][y] = ball;
		lastInsertBall = ball;
	}

	/** Sets a ball to the TabllTable */
	public void addBall(Ball ball) {
		if (ball != null) {
			Point ballPositionInTable = getField(ball.getX(), ball.getY());
			setBall(ballPositionInTable.x, ballPositionInTable.y, ball);
		}
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
		if (x >= 0 && x < 8 && y >= 0 && y < 13) {
			return balls[x][y];
		} else {
			return null;
		}
	}

	@Override
	public BallTable clone() {
		try {
			BallTable balltable = (BallTable) super.clone();
			balltable.balls = new Ball[8][13];
			for (int y = 0; y < balls[0].length; y++) {
				for (int x = 0; x < 8; x++) { //TODO: change to System.arraycopy -faster!
					Ball b = balls[x][y];
					if (b != null) {
						balltable.balls[x][y] = (Ball) balls[x][y].clone();
					}
				}
			}
			return balltable;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the field in which the ball resides. <br> E.g.: (3/2)
	 */
	public Point getField(int x, int y) {
		int posX = (int) ((x - this.x + gab_between_balls) / (double) (ballA + gab_between_balls));
		double posYTemp = ((this.y + height - y) / (double) ballA);
		int posY = (int) posYTemp;
		if (posYTemp % 1 == 0) {// if it's exacly on the grid
			posY -= 1;
		}
		return new Point (posX, posY );
	}

	/**
	 * Returns the field in which the ball resides. <br> E.g.: (3/2)
	 
	public int[] getField(SObject ball) {
		return getField(ball.getX(), ball.getY());
	}*/
	
	/**
	 * Returns the field in which the ball resides. <br> E.g.: (3/2)
	 */
	public Point getField(SObject ball) {
		return getField(ball.getX(), ball.getY());
	}
	
	/** Returns the last Ball which was insert into the BallTable */
	public Ball getLastInsertBall() {
		return lastInsertBall;
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

	/**
	 * Checks, if the given field is empty
	 * 
	 * @see #isEmpty(int posX,int posY)
	 */
	public boolean isEmpty(int[] pos) {
		return isEmpty(pos[0], pos[1]);
	}

	/** Returns the BallTable in the console */
	public String printBallTable() {
		return BallTable.printBallTable(balls);
	}

	/**
	 * @param ballTable
	 * @return the given BallTable as String
	 */
	public static String printBallTable(Ball[][] ballTable) {
		String ballTableAsString = "";
		for (int i = ballTable[0].length; i > 0; i--) {
			for (Ball[] ball : ballTable) {
				if (ball[i - 1] != null) {
					ballTableAsString += " " + String.format("%02d", ball[i - 1].getNr());
				} else {
					ballTableAsString += " --";
				}
			}
			ballTableAsString += "\n";
		}
		return ballTableAsString;
	}

	/**
	 * @param x Position on Screen
	 * @param y Position on Screen
	 * @return Wether the Posiotion is over the BallTable
	 */
	public boolean isOverGrid(int x, int y) {
		return (y >= this.y - 48);
	}

	/** Returns the coordinates of a given BallTable cell on the display */
	public int[] getFieldPosOnScreen(int posX, int posY) {
		return new int[] { x + gab_between_balls + posX * (ballA + gab_between_balls), y + height - ((posY + 1) * ballA) };
	}

	/** Returns the coordinates of a given BallTable cell on the display */
	public int[] getFieldPosOnScreen(int[] pos) {
		return getFieldPosOnScreen(pos[0], pos[1]);
	}

	/** Returns the coordinates of fields in the BallTable */
	public int[] getFieldPos(int posX, int posY) {
		return new int[] { gab_between_balls + posX * (ballA + gab_between_balls), height - posY * ballA };
	}

	/** Removes the given balls from the BallTable */
	public void removeBall(Ball ball) {
		Point tabelPos = getField(ball.getX(), ball.getY());
		setBall(tabelPos.x, tabelPos.y, null);
	}

	/** Returns the sum of all ball weight on the given column */
	public int getColumnWeight(int column) {
		int columnWeight = 0;
		for (int row = 0; row < 8; row++) {
			if (balls[column][row] != null) {
				columnWeight += balls[column][row].getWeight();
			}
		}
		return columnWeight;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BallTable) {
			Ball[][] balls = ((BallTable) obj).getBalls();
			for (int y = 12; y >= 0; y--) {
				for (int x = 0; x < 8; x++) {
					if (this.balls[x][y] != null && !this.balls[x][y].isSameNr(balls[x][y])) {
						return false;
					}
				}
			}
			return true;
		} else {
			return super.equals(obj);
		}
	}

	/** Returns the number of balls in the given column */
	public int getColumnHeight(int column) {
		int columnHeight = 0;
		for (int row = 0; row < 8; row++) {
			if (balls[column][row] != null) {
				columnHeight++;
			}
		}
		return columnHeight;
	}

	/** Cleares all balls from the BallTable */
	@Override
	public void reset() {
		for (Ball[] b : balls) {
			Arrays.fill(b, null);
		}
	}

}