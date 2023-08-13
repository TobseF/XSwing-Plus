/*
 * @version 0.0 04.04.2010
 * @author 	Tobse F
 */
package xswing.testcases;

import lib.mylib.math.Vector2D;
import org.junit.*;


public class Vector2DTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void toDegreeTest() {
        Assert.assertEquals(360, new Vector2D(0, 1).getDegree(), 0.0);
        Assert.assertEquals(360, new Vector2D(0, 6).getDegree(), 0.0);
        Assert.assertEquals(45, new Vector2D(1, 1).getDegree(), 0.0);
        Assert.assertEquals(90, new Vector2D(1, 0).getDegree(), 0.0);
        Assert.assertEquals(135, new Vector2D(1, -1).getDegree(), 0.0);
        Assert.assertEquals(180, new Vector2D(0, -1).getDegree(), 0.0);
        Assert.assertEquals(225, new Vector2D(-1, -1).getDegree(), 0.0);
        Assert.assertEquals(270, new Vector2D(-1, 0).getDegree(), 0.0);
        Assert.assertEquals(315, new Vector2D(-1, 1).getDegree(), 0.0);
    }
}
