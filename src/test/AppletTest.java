package test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class AppletTest
extends BasicGame {
    int r = 0;

    public AppletTest() {
        super("AppletTest");
    }

    public static void main(String[] args) {
        AppGameContainer game = null;
        try {
            game = new AppGameContainer(new AppletTest());
            game.setDisplayMode(300, 300, false);
            game.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
    }

    public void update(GameContainer container, int delta) throws SlickException {
        ++this.r;
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setAntiAlias(true);
        g.rotate(150.0f, 50.0f, this.r);
        g.fillRoundRect(50.0f, 50.0f, 200.0f, 100.0f, 5);
    }
}
