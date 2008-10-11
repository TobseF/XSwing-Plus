package tests.fun;

import java.io.IOException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class ParticleStripes
extends BasicGame {
    Image background;
    private ParticleSystem paticleSystem;
    static AppGameContainer container;
    ConfigurableEmitter configurableEmitter;

    public ParticleStripes() {
        super("ParticleBallStress2");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new ParticleStripes());
            container.setDisplayMode(640, 480, false);
            container.setClearEachFrame(false);
            container.setMinimumLogicUpdateInterval(15);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
        this.background = new Image("res/swing_background.jpg");
        try {
            this.configurableEmitter = ParticleIO.loadEmitter("res/explosion2.xml");
            this.paticleSystem = ParticleIO.loadConfiguredSystem("res/emptySystem.xml");
            this.paticleSystem.setRemoveCompletedEmitters(true);
        }
        catch (IOException e) {
            throw new SlickException("Failed to load particle systems", e);
        }
    }

    public void update(GameContainer container, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(57)) {
            this.part(100, 100);
        }
        if (container.getInput().isMouseButtonDown(0)) {
            this.part(container.getInput().getMouseX(), container.getInput().getMouseY());
        }
        if (container.getInput().isMouseButtonDown(1)) {
            this.part(container.getInput().getMouseX() + 20, container.getInput().getMouseY() + 20);
        }
        if (container.getInput().isMouseButtonDown(2)) {
            this.part(container.getInput().getMouseX() - 20, container.getInput().getMouseY() - 20);
        }
        if (container.getInput().isKeyPressed(2)) {
            this.partMulti();
            System.out.println("1");
        }
        this.paticleSystem.update(delta);
    }

    public void partMulti() {
        int i = 64;
        while (i > 0) {
            this.part((int)(Math.random() * 100.0) + 100, (int)(Math.random() * 100.0) + 100);
            --i;
        }
    }

    public void part(int x, int y) {
        ConfigurableEmitter temp = this.configurableEmitter.duplicate();
        temp.setPosition(x, y);
        this.paticleSystem.addEmitter(temp);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(this.background, 0.0f, 0.0f);
        this.paticleSystem.render(0.0f, 0.0f);
        g.drawString("PART: " + this.paticleSystem.getParticleCount(), 5.0f, 20.0f);
    }
}
