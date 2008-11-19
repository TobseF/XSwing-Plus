/*
 * @version 0.0 18.04.2008
 * @author Tobse F
 */
package xswing;

import java.io.IOException;

import lib.mylib.Drawable;
import lib.mylib.Resetable;
import lib.mylib.Sound;
import lib.mylib.Updateable;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

/** Provides the particle effects */
public class EffectCatalog implements Resetable, Updateable, Drawable {
	private static ParticleSystem paticleSystem = null;
	private ConfigurableEmitter[] effects = new ConfigurableEmitter[7];
	public static final int effectBouncing = 1;
	public static final int effectExplosion = 2;
	public static final int efectFlash = 3;
	public static final int efectShrinc = 4;
	private Sound[] sounds = new Sound[4];
	private boolean showParticles = true;

	public void setSound(Sound sound, int effectNr) {
		sounds[effectNr] = sound;
	}

	public EffectCatalog() {
		if (paticleSystem == null) {
			try {
				paticleSystem = ParticleIO.loadConfiguredSystem("res/emptySystem.xml");
				effects[effectBouncing] = ParticleIO.loadEmitter("res/bounce3.xml");
				effects[effectExplosion] = ParticleIO.loadEmitter("res/explosion2.xml");
				effects[efectFlash] = ParticleIO.loadEmitter("res/glitter2.xml");
				for(int i = 0; i<3; i++){
					effects[efectShrinc+i] = ParticleIO.loadEmitter("res/shrink"+(i+1)+".xml");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		paticleSystem.setRemoveCompletedEmitters(true);
	}

	private void addEmitter(int x, int y, ConfigurableEmitter effect) {
		effect.replay();
		effect.setPosition(x, y);
		paticleSystem.addEmitter(effect);
	}

	public void addEffect(Ball ball, int effectNr) {
		if (showParticles) {
			int a = Ball.A;
			ConfigurableEmitter effect=null;
			switch (effectNr) {
			case effectBouncing:
				effect = effects[effectBouncing].duplicate();
				addEmitter(ball.getX() + a / 2, ball.getY() + a, effect);
				break;
			case effectExplosion:
				effect = effects[effectExplosion].duplicate();
				addEmitter(ball.getX() + a / 2, ball.getY() + a / 2, effect);
				break;
			case efectFlash:
				effect = effects[efectFlash].duplicate();
				addEmitter(ball.getX() - 42, ball.getY() + 50, effect);
				break;
			case efectShrinc:
				for(int i = 0; i<3; i++){
					effect = effects[efectShrinc+i].duplicate();
					addEmitter(ball.getX() + a / 2, ball.getY() + a / 2, effect);
				}
				break;
			default:
				break;
			}
			if (effectNr<4 && sounds[effectNr] != null && !sounds[effectNr].playing()) {
				sounds[effectNr].play();
			}
		}
	}

	public void reset() {
		paticleSystem.removeAllEmitters();
		paticleSystem.reset();
		for (Sound sound : sounds) {
			if (sound != null) {
				sound.stop();
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		if (showParticles) {
			paticleSystem.render();
		}
	}

	@Override
	public void update(int delta) {
		if (showParticles) {
			paticleSystem.update(delta);
		}
	}

	public int getParticleCount() {
		return paticleSystem.getParticleCount();
	}

	public void setShowParticles(boolean showParticles) {
		this.showParticles = showParticles;
	}

	public boolean isShowParticles() {
		return showParticles;
	}
}