/*
 * @version 0.0 29.05.2008
 * @author Tobse F
 */
package tests;

import org.newdawn.slick.*;

public class AppletTest extends BasicGame {

    private int r = 0;

    public AppletTest() {
        super("AppletTest");
    }

    public static void main(String[] args) {
        AppGameContainer game = null;
        // Log.setVerbose(false); //no debug infos
        try {
            game = new AppGameContainer(new AppletTest());
            game.setDisplayMode(300, 300, false);
            game.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        r++;
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setAntiAlias(true);
        g.rotate(150, 50, r);
        g.fillRoundRect(50, 50, 200, 100, 5);
    }

}
