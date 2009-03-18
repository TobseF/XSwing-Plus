/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package tests;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class ParticleScoreTest extends BasicGame {
	private Image background;
	private ParticleSystem paticles;
	private ParticleEmitter emitter;
	static AppGameContainer container;
	ConfigurableEmitter scoreEmitter;

	public ParticleScoreTest() {
		super("ParticleScoreTest");
	}

	public static void main(String[] args) {
		try {
			container = new AppGameContainer(new ParticleScoreTest());
			container.setDisplayMode(640, 480, false);
			container.setClearEachFrame(false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		background = new Image("restest/swing_background.jpg");
		try {
			paticles = ParticleIO.loadConfiguredSystem("res/emptysystem.xml");
			emitter = ParticleIO.loadEmitter("res/score.xml");
			scoreEmitter= (ConfigurableEmitter) emitter;
		} catch (IOException e) {
			throw new SlickException("Failed to load particle systems", e);
		}
		//paticles.addEmitter(emitter);
		paticles.addEmitter(scoreEmitter);
		scoreEmitter.setImageName("res/48.png");
	}

	int i;
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		paticles.update(delta);
		if(container.getInput().isKeyPressed(Input.KEY_SPACE)){
			i++;
			Image image = new Image(100,20);
			image.getGraphics().drawString(""+i, 0, 0);
			//TODO: is it possible to give a image instead of settin the path
			paticles.reset();
		}
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.drawImage(background, 0, 0);
		paticles.render(200, 200);
	}

}