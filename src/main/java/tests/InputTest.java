/*
 * @version 0.0 04.06.2010
 * @author 	Tobse F
 */
package tests;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;
import org.newdawn.slick.util.Log;


public class InputTest {
    public static void main(String[] args) throws InterruptedException {
        ControllerEnvironment cEnvironment = ControllerEnvironment.getDefaultEnvironment();
        Controller[] cControllers = cEnvironment.getControllers();

        if (cControllers.length == 0)
            Log.warn("Critical Error, No inputs availaible");

        System.out.println("JInput has found: " + cControllers.length + " Controllers");

        for (int i = 0; i < cControllers.length; i++) {
            if (cControllers[i].getType() != Type.UNKNOWN) {
                System.out.println("\n Controller Name: (" + i + ") " + cControllers[i].getName());
                System.out.println("    Type: " + (cControllers[i].getType()));
                System.out.println("    Rumble: " + ((cControllers[i].getRumblers().length > 0) ? "Yes" : "No"));

                for (int o = 0; o < cControllers[i].getRumblers().length; o++) {
                    System.out.println("        Rumbling Axis: " + cControllers[i].getRumblers()[o].getAxisName());
                    cControllers[i].getRumblers()[o].rumble(0.5f);
                    Thread.sleep(1000);
                }
            }
        }
    }
}