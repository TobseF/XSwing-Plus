package test;

import java.io.IOException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class ParticleBallStress
extends BasicGame {
    Image background;
    Image ball;
    private ParticleSystem paticles1;
    private ParticleSystem paticles2;
    static AppGameContainer container;

    public ParticleBallStress() {
        super("sad");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new ParticleBallStress());
            container.setDisplayMode(640, 480, false);
            container.setClearEachFrame(false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
        this.background = new Image("res/swing_background.jpg");
        this.ball = new Image("res/ball.png");
        try {
            this.paticles1 = ParticleIO.loadConfiguredSystem("res/balls_system.xml");
            this.paticles2 = ParticleIO.loadConfiguredSystem("res/balls_system2.xml");
        }
        catch (IOException e) {
            throw new SlickException("Failed to load particle systems", e);
        }
    }

    public void update(GameContainer container, int delta) throws SlickException {
        this.paticles1.update(delta);
        this.paticles2.update(delta);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(this.background, 0.0f, 0.0f);
        this.paticles2.render(200.0f, 200.0f);
        this.paticles1.render(590.0f, 70.0f);
    }
}
