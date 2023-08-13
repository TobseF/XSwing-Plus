/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package tests;

import lib.mylib.color.TransparancySlider;
import org.newdawn.slick.*;
import trash.FrameAverage;

public class TranparencySliderTest extends BasicGame {

    static AppGameContainer container;
    Image background, balls;
    TransparancySlider fadeIn, fadeOut;
    FrameAverage frameAverage;

    public TranparencySliderTest() {
        super("XSwing");
        fadeIn = new TransparancySlider(50, 2000, TransparancySlider.LINEAR_FADE_IN);
        fadeOut = new TransparancySlider(50, 2000, TransparancySlider.LINEAR_FADE_OUT);
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new TranparencySliderTest());
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
        frameAverage = new FrameAverage(container);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
            fadeIn.reset();
            fadeOut.reset();
        }
        if (container.getInput().isKeyPressed(Input.KEY_ENTER)) {
            fadeIn.invert();
            fadeOut.invert();
        }
        fadeIn.update(delta);
        fadeOut.update(delta);
        frameAverage.update(delta);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        g.setBackground(Color.white);
        // g.setColor(new Color(100, 100, 100, 0.5f));
        g.drawImage(balls, 0, 0, fadeIn.getTranparency());
        g.drawImage(balls, 0, 400, fadeOut.getTranparency());
        g.drawString("[" + frameAverage.getAverageFrameRate() + "]", 400, 60);
        // g.fillOval( 0, 0, 0, 90);
    }

}
