/*
 * @version 0.0 31.05.2008
 * @author Tobse F
 */
package lib.mylib;

import lib.mylib.util.TimedInstanceCounter;
import org.newdawn.slick.SlickException;

/**
 * Extendened version of the Slick Sound class
 */
public class Sound extends org.newdawn.slick.Sound {

    private long playingTime;
    private boolean preventSimulaniousPlay = false;

    private final TimedInstanceCounter instanceCounter = new TimedInstanceCounter();
    public int maximalInstances = 0;

    public Sound(String ref) throws SlickException {
        super(ref);
    }

    public Sound(String ref, int maximalInstances, long maxPlayingTime) throws SlickException {
        this(ref);
        this.maximalInstances = maximalInstances;
        setMaxPlyingTime(maxPlayingTime);
    }

    /**
     * Sets the maxiumum time before {@link #playing()} returns false.
     *
     * @param maxPlayingTime in ms
     */
    public void setMaxPlyingTime(long maxPlayingTime) {
        this.playingTime = maxPlayingTime;
    }

    @Override
    public void play() {
        if (maximalInstances == 0 || instanceCounter.count() < maximalInstances) {
            if (!preventSimulaniousPlay || (preventSimulaniousPlay && !playing())) {
                super.play();
                instanceCounter.add(playingTime);
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
        instanceCounter.clear();
    }

//	@Override
//	public boolean playing() {
//		long now = System.currentTimeMillis();
//		if (maxPlayingTime != 0) {
//			if (now - startTime > maxPlayingTime) {
//				return false;
//			}
//		}
//		return super.playing();
//	}

    public void setPreventSimulaniousPlay(boolean preventSimulaniousPlay) {
        this.preventSimulaniousPlay = preventSimulaniousPlay;
    }

    public boolean isPreventSimulaniousPlay() {
        return preventSimulaniousPlay;
    }

}