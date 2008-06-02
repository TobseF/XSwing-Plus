package xswing;

import java.io.IOException;
import lib.Sound;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import xswing.Ball;
import xswing.Resetable;

public class Effects
implements Resetable {
    private static ParticleSystem paticleSystem = null;
    private ConfigurableEmitter[] effects = new ConfigurableEmitter[4];
    private final int effect = 0;
    public static final int effectBouncing = 1;
    public static final int effectDisappearing = 2;
    public static final int efectFlash = 3;
    private Sound[] sounds = new Sound[4];

    public void setSound(Sound sound, int effectNr) {
        this.sounds[effectNr] = sound;
    }

    public Effects() {
        if (paticleSystem == null) {
            try {
                paticleSystem = ParticleIO.loadConfiguredSystem("res/emptySystem.xml");
                this.effects[1] = ParticleIO.loadEmitter("res/bounce2.xml");
                this.effects[2] = ParticleIO.loadEmitter("res/explosion2.xml");
                this.effects[3] = ParticleIO.loadEmitter("res/glitter2.xml");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addEmitter(int x, int y) {
        this.effects[0].replay();
        this.effects[0].setPosition(x, y);
        paticleSystem.addEmitter(this.effects[0]);
    }

    public void addEffect(Ball ball, int effectNr) {
        int a = 48;
        switch (effectNr) {
            case 1: {
                this.effects[0] = this.effects[1].duplicate();
                this.addEmitter(ball.getX() + a / 2, ball.getY() + a);
                break;
            }
            case 2: {
                this.effects[0] = this.effects[2].duplicate();
                this.addEmitter(ball.getX() + a / 2, ball.getY() + a / 2);
                break;
            }
            case 3: {
                this.effects[0] = this.effects[3].duplicate();
                this.addEmitter(ball.getX() - 42, ball.getY() + 50);
                break;
            }
        }
        if (this.sounds[effectNr] != null && !this.sounds[effectNr].playing()) {
            this.sounds[effectNr].play();
        }
    }

    @Override
    public void reset() {
        paticleSystem.removeAllEmitters();
        paticleSystem.reset();
    }

    public void draw(Graphics g) {
        paticleSystem.render();
    }

    public void update(int delta) {
        paticleSystem.update(delta);
    }
}
