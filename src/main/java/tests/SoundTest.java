/*
 * @version 0.0 31.05.2008
 * @author Tobse F
 */
package tests;

import org.newdawn.slick.*;

public class SoundTest extends BasicGame {

    public SoundTest() {
        super("SOund Test");
    }

    Sound sound1;

    public static void main(String[] args) {
        try {
            AppGameContainer con = new AppGameContainer(new SoundTest());
            con.setDisplayMode(400, 400, false);
            con.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init(GameContainer container) throws SlickException {
        sound1 = new Sound("res/wupp.wav");
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_1)) {
            sound1.play();
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        if (sound1.playing()) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.green);
        }
        g.fillOval(50, 50, 200, 200);

    }

}
