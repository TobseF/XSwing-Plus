package tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageJar
extends BasicGame {
    static AppGameContainer container;
    Image background;
    static long ttime;

    public ImageJar() {
        super("JarTest");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new ImageJar());
            container.setMinimumLogicUpdateInterval(20);
            container.setDisplayMode(640, 500, false);
            container.setClearEachFrame(false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
        this.background = new Image(Thread.currentThread().getContextClassLoader().getResource("res/swing_background.jpg").toString());
    }

    public void update(GameContainer container, int delta) throws SlickException {
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(this.background, 0.0f, 0.0f);
    }
}
