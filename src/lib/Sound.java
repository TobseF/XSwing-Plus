package lib;

import org.newdawn.slick.SlickException;

public class Sound
extends org.newdawn.slick.Sound {
    private long startTime;
    private long maxPlayingTime;

    public Sound(String ref) throws SlickException {
        super(ref);
    }

    public void setMaxPlyingTime(long maxPlayingTime) {
        this.maxPlayingTime = maxPlayingTime;
    }

    @Override
    public void play() {
        super.play();
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public boolean playing() {
        long now = System.currentTimeMillis();
        if (this.maxPlayingTime != 0L && now - this.startTime > this.maxPlayingTime) {
            return false;
        }
        return super.playing();
    }
}
