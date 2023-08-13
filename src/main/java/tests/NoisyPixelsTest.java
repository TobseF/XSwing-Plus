/*
 * @version 0.0 01.06.2009
 * @author Tobse F
 */
package tests;

import org.newdawn.slick.*;

public class NoisyPixelsTest extends BasicGame {

    private static final String IMAGE_PATH = "restest/hand.png";

    private NoisyPixelsTest() {
        super("");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (container.getInput().isKeyDown(Input.KEY_1)) {
            container.setMouseCursor(IMAGE_PATH, 0, 0);
        } else if (container.getInput().isKeyDown(Input.KEY_2)) {
            container.setMouseCursor(new Image(IMAGE_PATH), 0, 0);
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        String loc = container.getInput().getMouseX() + "," + container.getInput().getMouseY();
        g.drawString(loc, 10, 100);
        g.drawString("Press 1 and 2 to switch the cursor method.", 10, 130);
    }

    public static void main(String[] argv) throws SlickException {
        AppGameContainer container = new AppGameContainer(new NoisyPixelsTest());
        container.setDisplayMode(800, 600, false);
        container.start();
    }
}