/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package xswing.tests.fun;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class ParticleStripes extends BasicGame {
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
			// container.setMaximumLogicUpdateInterval(15);
			container.setMinimumLogicUpdateInterval(15);

			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		background = new Image("restest/swing_background.jpg");
		try {
			configurableEmitter = ParticleIO.loadEmitter("restest/explosion2.xml");
			paticleSystem = ParticleIO.loadConfiguredSystem("restest/emptySystem.xml");
			paticleSystem.setRemoveCompletedEmitters(true);
			// paticles2 =
			// ParticleIO.loadConfiguredSystem("res/balls_system2.xml");

		} catch (IOException e) {
			throw new SlickException("Failed to load particle systems", e);
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
			part(100, 100);
		}
		if (container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			part(container.getInput().getMouseX(), container.getInput().getMouseY());
		}
		if (container.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
			part(container.getInput().getMouseX() + 20, container.getInput().getMouseY() + 20);
		}
		if (container.getInput().isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON)) {
			part(container.getInput().getMouseX() - 20, container.getInput().getMouseY() - 20);
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
		ConfigurableEmitter temp = configurableEmitter.duplicate();
		// temp.reset();
		temp.setPosition(x, y);
		paticleSystem.addEmitter(temp);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.drawImage(background, 0, 0);
		// paticles2.render(200,200);
		paticleSystem.render(0, 0);
		g.drawString("PART: " + paticleSystem.getParticleCount(), 5, 20);
	}

}
