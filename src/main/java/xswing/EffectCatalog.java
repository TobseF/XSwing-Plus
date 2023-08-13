/*
 * @version 0.0 18.04.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.Sound;
import lib.mylib.object.Drawable;
import lib.mylib.object.Resetable;
import lib.mylib.object.Updateable;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import xswing.ball.Ball;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import static lib.mylib.options.Paths.RES_DIR;

/**
 * Provides particle & sound effects
 */
public class EffectCatalog implements Resetable, Updateable, Drawable {

    private ParticleSystem paticleSystem = null;
    private static final String EFFECT_EXTENSION = ".xml";

    public enum EffectType {
        /**
         * Effect for the ball when it drops to the ground
         */
        BOUNCING, EXPLOSION,
        /**
         * Effect for the cannon when releasing a ball
         */
        FLASH, SHRINC, SHRINC1, SHRINC2, SHRINC3, SHRINC4
    }

    private final Map<EffectType, ConfigurableEmitter> emitterList = new EnumMap<EffectType, ConfigurableEmitter>(
            EffectType.class);
    private final Map<EffectType, Sound> sounds = new EnumMap<EffectType, Sound>(
            EffectType.class);
    private boolean showParticles = true;

    public void setSound(Sound sound, EffectType effect) {
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

            for (EffectType particleEffect : EffectType.values()) {
                ConfigurableEmitter emitter = null;
                if (particleEffect != EffectType.SHRINC) {
                    try {
                        emitter = ParticleIO.loadEmitter(RES_DIR
                                + particleEffect.toString().toLowerCase() + EFFECT_EXTENSION);
                    } catch (IOException e) {
                    }
                }
                if (emitter != null) {
                    emitterList.put(particleEffect, emitter);
                }
            }
        }
        paticleSystem.setRemoveCompletedEmitters(true);
    }

    private void addEmitterAndSetPosition(int x, int y, ConfigurableEmitter effect) {
        effect.setPosition(x, y, false);
        effect.replay();
        paticleSystem.addEmitter(effect);
    }

    /**
     * Adds an Paticle effect to a ball
     *
     * @param ball     which executes an effect
     * @param effectNr
     */
    public void addEffect(Ball ball, EffectType effect) {
        if (showParticles) {
            int a = Ball.A;
            ConfigurableEmitter currentEmitter = null;

            switch (effect) {
                case BOUNCING:
                    currentEmitter = emitterList.get(effect).duplicate();
                    addEmitterAndSetPosition(ball.getX() + a / 2, ball.getY() + a, currentEmitter);
                    break;
                case EXPLOSION:
                    currentEmitter = emitterList.get(effect).duplicate();
                    addEmitterAndSetPosition(ball.getX() + a / 2, ball.getY() + a / 2, currentEmitter);
                    break;
                case FLASH:
                    currentEmitter = emitterList.get(effect).duplicate();
                    addEmitterAndSetPosition(ball.getX() - 42, ball.getY() + 50, currentEmitter);
                    break;
                case SHRINC:
                    int lenght = EffectType.values().length;
                    for (int i = lenght - 1; i >= (lenght - 4); i--) {
                        addEmitterAndSetPosition(ball.getX() + a / 2, ball.getY() + a / 2, emitterList.get(
                                EffectType.values()[i]).duplicate());
                    }
                    break;
                default:
                    break;
            }
            if (sounds.get(effect) != null
                //&& !sounds.get(effect).playing()
            ) {
                sounds.get(effect).play();
            }
        }
    }

    @Override
    public void reset() {
        paticleSystem.removeAllEmitters();
        paticleSystem.reset();
        for (EffectType effect : EffectType.values()) {
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