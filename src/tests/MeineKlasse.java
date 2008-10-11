package tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MeineKlasse
extends BasicGame {
    Image testBild;

    public MeineKlasse(String title) {
        super(title);
    }

    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new MeineKlasse("MeinSpiel-Fenstername"));
            container.setMinimumLogicUpdateInterval(20);
            container.setDisplayMode(800, 600, false);
            container.setClearEachFrame(false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
        this.testBild = new Image("pfad/bild.jpg");
    }

    public void update(GameContainer container, int delta) throws SlickException {
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        this.testBild.draw();
    }
}
