/*
 * @version 0.0 13.02.2011
 * @author Tobse F
 */
package xswing.testcases.util;

import xswing.ball.Ball;
import xswing.ball.BallTable;

public class BallTableCreator {

    public static BallTable getBallTable(Integer[][] field) {
        BallTable table = new BallTable();
        for (int y = 0; y < field[0].length; y++) {
            for (int x = 0; x < field.length; x++) {
                if (field[x][y] != null) {
                    table.setBall(x, y, new Ball(field[x][y]));
                }
            }
        }
        return table;
    }

    public static BallTable getBallTable(String ballTableOutput, int widht, int height) {
        String[] values = ballTableOutput.split("\\s");
        BallTable table = new BallTable();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < widht; x++) {
                String value = values[y * widht + x].trim();
                if (!value.equals("--")) {
                    Ball ball = new Ball(Integer.parseInt(value));
                    table.setBall(x, height - 1 - y, ball);
                }
            }
        }
        return table;
    }
}
