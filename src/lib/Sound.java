/*
 * @version 0.0 31.05.2008
 * @author 	Tobse F
 */
package lib;

import org.newdawn.slick.SlickException;

public class Sound extends org.newdawn.slick.Sound{
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
		startTime=System.currentTimeMillis();
	}
	
	
	@Override
	public boolean playing() {
		long now=System.currentTimeMillis();
		if(maxPlayingTime!=0)
			if(now-startTime>maxPlayingTime)
				return false;
		return super.playing();
	}
	

}
