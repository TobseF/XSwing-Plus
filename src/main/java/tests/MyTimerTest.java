/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package tests;

import lib.mylib.MyTimer;
import org.newdawn.slick.*;

public class MyTimerTest extends BasicGame {

    private static AppGameContainer container;
    private Image background;
    private MyTimer timer;
    private boolean drawOval = true;

    public MyTimerTest() {
        super("MySpriteSheetFont vs AngelCodeFont Test");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new MyTimerTest());
            container.setMinimumLogicUpdateInterval(20);
            container.setDisplayMode(800, 600, false);
            container.setClearEachFrame(false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        background = new Image("restest/swing_background.jpg");
        timer = new MyTimer(500, true) {

            @Override
            protected void timerAction() {
                drawOval = !drawOval;
            }
        };
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        timer.update(delta);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        if (drawOval) {
            g.fillOval(100, 100, 100, 100);
        }
    }

}
