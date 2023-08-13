/*
 * @version 0.0 04.08.2008
 * @author Tobse F
 */
package tests;

import lib.mylib.ValueSlider;
import lib.mylib.ValueSlider.ValueSliderType;
import org.newdawn.slick.*;

public class ValueSliderTest extends BasicGame {

    private ValueSlider valueSlider;

    public ValueSliderTest() {
        super("TextFieldTest");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        valueSlider = new ValueSlider(20, 100, 50, 1000, ValueSliderType.SLIDE_IN);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        valueSlider.update(delta);
        if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
            valueSlider.reset();
        }
        if (container.getInput().isKeyPressed(Input.KEY_ENTER)) {
            valueSlider.invert();
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawString(valueSlider.getValue() + "", 100, 100);
    }

    public static void main(String[] args) throws Exception {
        AppGameContainer container = new AppGameContainer(new ValueSliderTest());
        container.setDisplayMode(640, 480, false);
        container.setMinimumLogicUpdateInterval(25);
        container.start();
    }
}