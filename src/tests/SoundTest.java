package tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundTest
extends BasicGame {
    Sound sound1;

    public SoundTest() {
        super("SOund Test");
    }

    public static void main(String[] args) {
        try {
            AppGameContainer con = new AppGameContainer(new SoundTest());
            con.setDisplayMode(400, 400, false);
            con.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
        this.sound1 = new Sound("res/wupp.wav");
    }

    public void update(GameContainer container, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(2)) {
            this.sound1.play();
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        if (this.sound1.playing()) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.green);
        }
        g.fillOval(50.0f, 50.0f, 200.0f, 200.0f);
    }
}
