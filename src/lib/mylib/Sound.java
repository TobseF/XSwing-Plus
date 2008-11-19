/*
 * @version 0.0 31.05.2008
 * @author Tobse F
 */
package lib.mylib;

import org.newdawn.slick.SlickException;

/** Extendened version of the Slick Sound class */
public class Sound extends org.newdawn.slick.Sound {
	private long startTime;
	private long maxPlayingTime;

	public Sound(String ref) throws SlickException {
		super(ref);
	}

	/**
	 * Sets the maxiumum time before {@link #playing()} returns false.
	 * 
	 * @param maxPlayingTime in ms
	 */
	public void setMaxPlyingTime(long maxPlayingTime) {
		this.maxPlayingTime = maxPlayingTime;
	}

	@Override
	public void play() {
		super.play();
		startTime = System.currentTimeMillis();
	}

	@Override
	public boolean playing() {
		long now = System.currentTimeMillis();
		if (maxPlayingTime != 0) {
			if (now - startTime > maxPlayingTime) {
				return false;
			}
		}
		return super.playing();
	}

}