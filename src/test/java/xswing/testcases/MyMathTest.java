/*
 * @version 0.0 19.12.2008
 * @author Tobse F
 */
package xswing.testcases;

import lib.mylib.math.MyMath;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyMathTest {

    @Test
    public void getRandom() {
        for (int i = 0; i < 10; i++) {
            int random = MyMath.getRandomAberrance(100, 5);
            assertTrue(random >= 95 && random <= 105);
        }
    }

    @Test
    public void getFloat() {
        for (int i = 0; i < 1000; i++) {
            float random = MyMath.getFloat(0.2f, 0.8f);
            if (random >= 0.79 || random <= 0.19) {
                //System.out.println(random);
            }
            assertTrue("Random value " + random + " was not >= 0.2", random >= 0.2f);
            assertTrue("Random value " + random + " was not <= 0.8", random <= 0.8f);
        }
    }

    @Test
    public void getInt() {
        for (int i = 0; i < 40; i++) {
            int random = MyMath.getInt(20, 100);
            if (random >= 20 && random <= 100) {
            } else {
                fail();
            }
        }
    }

    @Test
    public void getDegree() {
        assertEquals(360, MyMath.getDegree(0, 0, 0, 1), 0.0);
        assertEquals(90, MyMath.getDegree(0, 0, 1, 0), 0.0);
        assertEquals(180, MyMath.getDegree(0, 0, 0, -1), 0.0);
        assertEquals(270, MyMath.getDegree(0, 0, -1, 0), 0.0);
        assertEquals(45, MyMath.getDegree(0, 0, 1, 1), 0.0);
    }
}
