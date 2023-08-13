/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package xswing.tests.fun;

import org.newdawn.slick.*;

public class BallStress extends BasicGame {

    Image background, ball;
    static AppGameContainer container;

    public BallStress() {
        super("sad");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new BallStress());
            container.setDisplayMode(640, 480, false);
            container.setClearEachFrame(false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init(GameContainer container) throws SlickException {
        background = new Image("restest/swing_background.jpg");
        ball = new Image("restest/ball.png");
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        for (int i = 0; i < 1000; i++) {
            g.drawImage(ball, (float) (Math.random() * container.getWidth()), (float) (Math
                    .random() * container.getHeight()));
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    }

}
