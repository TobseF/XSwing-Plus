/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package tests;

import org.newdawn.slick.*;

public class TranparencyTest extends BasicGame {

    static AppGameContainer container;
    Image background, balls;

    public TranparencyTest() {
        super("XSwing");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new TranparencyTest());
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
        balls = new Image("restest/balls1.png");
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        // if(container.getInput().isKeyPressed(Input.KEY_SPACE))
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        g.setBackground(Color.white);
        g.setColor(new Color(100, 100, 100, 0.5f));
        g.drawImage(balls, 0, 0, new Color(255, 255, 255, 0.5f));
        // g.fillOval( 0, 0, 0, 90);
    }

}
