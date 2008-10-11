package tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import tests.Shader;

public class Program
extends BasicGame {
    private Image image = null;
    private Shader shader;

    public Program(String title) {
        super(title);
    }

    public void init(GameContainer container) throws SlickException {
        try {
            this.image = new Image("res/balls1.png");
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
        this.shader = new Shader("data/shader/test");
    }

    public void update(GameContainer container, int delta) throws SlickException {
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawString("Fixed pipeline:", 10.0f, 216.0f);
        g.drawString("Shader:", 148.0f, 216.0f);
        g.drawImage(this.image, 10.0f, 236.0f);
        this.shader.render(this.image, 148.0f, 236.0f);
    }

    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new Program("Shader Test"));
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
