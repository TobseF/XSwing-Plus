/*
 * @version 0.0 25.04.2010
 * @author Tobse F
 */
package lib.mylib.util;

import java.awt.*;
import lib.mylib.swing.SwingUtils;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;

public class SlickUtils {

	private static Robot robot;

	/**
	 * Can hide ore show the mouse cursor.<br>
	 * In Fullscreen mode this will be done with setMouseGrabbed() and in window mode, with
	 * moving the cursor in or outside the window.<br>
	 * <b>Warning:</b> Can only called after the container was created!
	 * 
	 * @param container gameContainer
	 * @param hide if the cursor should be visible (true) or not (false)
	 */
	public static void hideMouse(GameContainer container, boolean hide) {
		if (container.isFullscreen()) {
			container.setMouseGrabbed(hide);
		} else {
			if (robot == null) {
				try {
					robot = new Robot();
				} catch (AWTException e) {}
			}
			if (hide) {
				robot.mouseMove(0, 0);
			} else {
				boolean isMouseInCorner = (MouseInfo.getPointerInfo().getLocation().distance(new Point(0, 0)) < 4);
				if (!Mouse.isInsideWindow() || isMouseInCorner) {
					Point center = SwingUtils.getScreenCenter();
					robot.mouseMove(center.x, center.y);
				}
			}
		}
	}
}
