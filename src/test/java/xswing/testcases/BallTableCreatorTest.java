/*
 * @version 0.0 13.02.2011
 * @author Tobse F
 */
package xswing.testcases;

import org.junit.Before;
import org.junit.Test;
import xswing.ball.BallTable;
import xswing.testcases.util.BallTableCreator;

public class BallTableCreatorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public final void testGetBallTableInt() {
        Integer[][] fieldData = new Integer[8][8];
        fieldData[0][0] = 0;
        fieldData[0][1] = 0;
        fieldData[0][2] = 0;
        fieldData[1][0] = 1;
        fieldData[1][1] = 0;
        fieldData[2][0] = 0;
        fieldData[2][1] = 0;
        fieldData[7][0] = 2;

        BallTable table = BallTableCreator.getBallTable(fieldData);
        System.out.println(table);
    }

    @Test
    public final void testGetBallTableString() {
        String field = "-- -- -- -- -- -- -- -- " + //
                "-- -- -- -- -- -- -- -- " + //
                "-- -- -- -- -- -- -- -- " + //
                "-- -- -- -- -- -- -- -- " + //
                "-- -- -- -- -- -- -- -- " + //
                "01 -- -- -- -- -- -- -- " + //
                "01 01 01 -- -- -- -- -- " + //
                "01 02 01 -- -- -- -- 03";
        BallTable table = BallTableCreator.getBallTable(field, 8, 8);
        System.out.println(table);
    }

}
