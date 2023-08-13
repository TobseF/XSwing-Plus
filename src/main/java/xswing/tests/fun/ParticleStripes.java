/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package xswing.tests.fun;

import org.newdawn.slick.*;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import java.io.IOException;

import static lib.mylib.options.Paths.RES_TEST_DIR;

public class ParticleStripes extends BasicGame {

    Image background;
    private ParticleSystem paticleSystem;
    static AppGameContainer container;
    ConfigurableEmitter DEFAULT_EMITTER;
    ConfigurableEmitter emitter1;

    public ParticleStripes() {
        super("ParticleBallStress2");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new ParticleStripes());
            container.setDisplayMode(1024, 768, false);
            container.setClearEachFrame(false);
            // container.setMaximumLogicUpdateInterval(15);
            container.setMinimumLogicUpdateInterval(15);

            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init(GameContainer container) throws SlickException {
        background = new Image(RES_TEST_DIR + "galaxy_klein.jpg");
        try {
            DEFAULT_EMITTER = ParticleIO.loadEmitter(RES_TEST_DIR + "magic_stripe.xml");
            paticleSystem = ParticleIO.loadConfiguredSystem(RES_TEST_DIR + "emptySystem.xml");
            //paticleSystem.setRemoveCompletedEmitters(true);
            emitter1 = DEFAULT_EMITTER.duplicate();
            emitter1.reset();
            paticleSystem.addEmitter(emitter1);
            paticleSystem.setPosition(0, 0);
            // paticles2 =
            // ParticleIO.loadConfiguredSystem("res/balls_system2.xml");

        } catch (IOException e) {
            throw new SlickException("Failed to load particle systems", e);
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        int mouseX = container.getInput().getMouseX();
        int mouseY = container.getInput().getMouseY();
        if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
            part(100, 100);
        }
        if (container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            part(mouseX, mouseY);
        }
        if (container.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
            part(mouseX + 20, mouseY + 20);
        }
        if (container.getInput().isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON)) {
            part(mouseX - 20, mouseY - 20);
        }
        if (container.getInput().isKeyPressed(Input.KEY_1)) {
            partMulti();
            System.out.println("1");
        }
        paticleSystem.update(delta);
        // paticles2.update(delta);
    }

    public void partMulti() {
        for (int i = 8 * 8; i > 0; i--) {
            part((int) (Math.random() * 100) + 100, (int) (Math.random() * 100) + 100);
        }
    }

    public void part(int x, int y) {
        //ConfigurableEmitter temp = DEFAULT_EMITTER.duplicate();
        // temp.reset();
        emitter1.setPosition(x, y);
        //paticleSystem.addEmitter(temp);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        // paticles2.render(200,200);
        paticleSystem.render();
        g.drawString("PART: " + paticleSystem.getParticleCount(), 5, 20);
    }

}
