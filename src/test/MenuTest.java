package test;

import java.util.List;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import test.Menu;

public class MenuTest
extends BasicGame {
    private Menu menu;

    public MenuTest() {
        super("MenuTest");
    }

    public void init(GameContainer container) throws SlickException {
        this.menu = new Menu(new AngelCodeFont("res/font.fnt", "res/font.tga"), container.getInput(), null);
        this.menu.setBounds(100, 40, 300, 400);
        List entries = this.menu.getEntries();
        int i = 1;
        while (i <= 10) {
            entries.add("Entry" + i);
            ++i;
        }
        this.menu.setWrapAround(true);
        this.menu.setGradientHeight(100);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        this.menu.render(g);
        g.setColor(Color.white);
        g.drawRect(100.0f, 40.0f, 300.0f, 400.0f);
    }

    public void update(GameContainer container, int delta) throws SlickException {
        this.menu.update(delta);
    }

    public static void main(String[] args) throws Exception {
        AppGameContainer con = new AppGameContainer(new MenuTest());
        con.setDisplayMode(640, 480, false);
        con.start();
    }
}
