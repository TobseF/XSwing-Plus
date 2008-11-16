/*
 * @version 0.0 14.04.2008
 * @author 	Tobse F
 */
package tests;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class ParticleBallStress extends BasicGame{
	Image background;
	private ParticleSystem paticles1,paticles2;
	static AppGameContainer container;
	public ParticleBallStress() {
		super("sad");
	}


	public static void main(String[] args) {
		try {
			container = new AppGameContainer(new ParticleBallStress());
			container.setDisplayMode(640,480,false);
			container.setClearEachFrame(false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		background=new Image("restest/swing_background.jpg");
		try {
			paticles1 = ParticleIO.loadConfiguredSystem("restest/balls_system.xml");
			paticles2 = ParticleIO.loadConfiguredSystem("restest/balls_system2.xml");
			
		} catch (IOException e) {
			throw new SlickException("Failed to load particle systems", e);
		}
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		paticles1.update(delta);
		paticles2.update(delta);
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.drawImage(background,0,0);
		paticles2.render(200,200);
		paticles1.render(590,70);
	}

}