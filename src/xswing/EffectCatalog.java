/*
 * @version 0.0 18.04.2008
 * @author Tobse F
 */
package xswing;

import static lib.mylib.options.Paths.RES_DIR;
import java.io.IOException;
import java.util.*;
import lib.mylib.Sound;
import lib.mylib.object.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.*;

/** Provides particle & sound effects */
public class EffectCatalog implements Resetable, Updateable, Drawable {

	private ParticleSystem paticleSystem = null;
	private static final String EFFECT_EXTENSION = ".xml";

	public enum particleEffects {
		BOUNCING, EXPLOSION, FLASH, SHRINC, SHRINC1, SHRINC2, SHRINC3, SHRINC4
	};

	private Map<particleEffects, ConfigurableEmitter> emitterList = new EnumMap<particleEffects, ConfigurableEmitter>(
			particleEffects.class);
	private Map<particleEffects, Sound> sounds = new EnumMap<particleEffects, Sound>(
			particleEffects.class);
	private boolean showParticles = true;

	public void setSound(Sound sound, particleEffects effect) {
		sounds.put(effect, sound);
	}

	public EffectCatalog() {
		if (paticleSystem == null) {
			try {
				paticleSystem = ParticleIO.loadConfiguredSystem(RES_DIR + "emptySystem"
						+ EFFECT_EXTENSION);
			} catch (IOException e) {
				e.printStackTrace();
			}

			for (particleEffects particleEffect : particleEffects.values()) {
				ConfigurableEmitter emitter = null;
				if (particleEffect != particleEffects.SHRINC) {
					try {
						emitter = ParticleIO.loadEmitter(RES_DIR
								+ particleEffect.toString().toLowerCase() + EFFECT_EXTENSION);
					} catch (IOException e) {}
				}
				if (emitter != null) {
					emitterList.put(particleEffect, emitter);
				}
			}
		}
		paticleSystem.setRemoveCompletedEmitters(true);
	}

	private void addEmitter(int x, int y, ConfigurableEmitter effect) {
		effect.replay();
		effect.setPosition(x, y);
		paticleSystem.addEmitter(effect);
	}

	/**
	 * Adds an Paticle effect to a ball
	 * 
	 * @param ball which executes an effect
	 * @param effectNr
	 */
	public void addEffect(Ball ball, particleEffects effect) {
		if (showParticles) {
			int a = Ball.A;
			ConfigurableEmitter currentEmitter = null;

			switch (effect) {
			case BOUNCING:
				currentEmitter = emitterList.get(effect).duplicate();
				addEmitter(ball.getX() + a / 2, ball.getY() + a, currentEmitter);
				break;
			case EXPLOSION:
				currentEmitter = emitterList.get(effect).duplicate();
				addEmitter(ball.getX() + a / 2, ball.getY() + a / 2, currentEmitter);
				break;
			case FLASH:
				currentEmitter = emitterList.get(effect).duplicate();
				addEmitter(ball.getX() - 42, ball.getY() + 50, currentEmitter);
				break;
			case SHRINC:
				int lenght = particleEffects.values().length;
				for (int i = lenght - 1; i >= (lenght - 4); i--) {
					addEmitter(ball.getX() + a / 2, ball.getY() + a / 2, emitterList.get(
							particleEffects.values()[i]).duplicate());
				}
				break;
			default:
				break;
			}
			if (sounds.get(effect) != null && !sounds.get(effect).playing()) {
				sounds.get(effect).play();
			}
		}
	}

	public void reset() {
		paticleSystem.removeAllEmitters();
		paticleSystem.reset();
		for (particleEffects effect : particleEffects.values()) {
			if (sounds.get(effect) != null) {
				sounds.get(effect).stop();
			}
		}
	}

	@Override
	public void render(Graphics g) {
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

	/**
	 * @return The current number of particles
	 */
	public int getParticleCount() {
		return paticleSystem.getParticleCount();
	}

	public void setShowParticles(boolean showParticles) {
		this.showParticles = showParticles;
	}

	/**
	 * @return Whether particles are printed on screen (true if yes)
	 */
	public boolean isShowParticles() {
		return showParticles;
	}
}