/*
 * @version 0.0 20.12.2008
 * @author Tobse F
 */
package tests;

import org.newdawn.slick.*;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import java.io.IOException;

public class ParticleExplosionTest extends BasicGame {

    private Image background;
    private ParticleSystem paticles1;

    public ParticleExplosionTest() {
        super("ParticleExplosionTest");
    }

    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new ParticleExplosionTest());
            container.setDisplayMode(640, 480, false);
            container.setClearEachFrame(false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        ParticleSystem.setRelativePath(_TestProps.RES);
        background = new Image(_TestProps.RES + "swing_background.jpg");
        try {
            paticles1 = ParticleIO.loadConfiguredSystem(_TestProps.RES + "explosion.xml");
        } catch (IOException e) {
            throw new SlickException("Failed to load particle systems", e);
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        Input in = container.getInput();
        if (in.isKeyPressed(Input.KEY_SPACE)) {
            paticles1.reset();
        }
        paticles1.update(delta);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        paticles1.render(590, 70);
    }

}
