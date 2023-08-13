/*
 * @version 0.0 18.09.2009
 * @author Tobse F
 */
package xswing.testcases;

import lib.mylib.options.DefaultArgs.Args;
import lib.mylib.util.MyOptions;
import lib.mylib.util.MyPropertys;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class MyPropertysTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public final void myPropertys() {
        MyPropertys.clear();

        MyPropertys.setString("value_before_File", "saved1?");

        assertEquals("saved1?", MyPropertys.getString("value_before_File"));
        assertNull(MyPropertys.getString("value_after_File"));

        assertEquals(0, MyPropertys.getNumber("not_setted"), 0);
        assertFalse(MyPropertys.getBoolean("not_setted"));

        MyPropertys.setFile("junit_test");
        MyPropertys.setString("value_after_File", "saved2?");

        assertEquals("saved1?", MyPropertys.getString("value_before_File"));
        assertEquals("saved2?", MyPropertys.getString("value_after_File"));

        assertFalse(MyPropertys.getBoolean("not_setted"));
        assertTrue(MyPropertys.getBoolean("not_setted", true));
        MyPropertys.setBoolean("is_correct", true);
        assertTrue(MyPropertys.getBoolean("is_correct", true));

        assertEquals(0, MyPropertys.getNumber("not_setted"), 0);
        assertEquals(42, MyPropertys.getNumber("not_setted", 42), 0);
    }

    @Test
    public void MyOptions() {
        MyPropertys.setString(Args.checkForUpdates + "", "Yes!!");
        assertEquals("Yes!!", MyPropertys.getString(Args.checkForUpdates + ""));

        MyOptions.setString(Args.checkForUpdates, "No!!");
        assertEquals("No!!", MyPropertys.getString(Args.checkForUpdates + ""));
        assertNotSame("Yes!!", MyPropertys.getString(Args.checkForUpdates + ""));
        assertEquals("No!!", MyPropertys.getString(Args.checkForUpdates + ""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void regisredArgs() {
        HashSet<String> noAllowedString = new HashSet<String>();
        MyPropertys.setDefaultArgs(noAllowedString);
        MyPropertys.setCheckForDefaults(true);
        MyPropertys.setThrowExeptonOnArgsCheking(true);
        MyPropertys.setString("not_registered", "xxx");
    }
}
