/*
 * @version 0.0 14.10.2008
 * @author 	Tobse F
 */
package lib.mylib;

/**
 * @author Tobse
 *
 * A blinking effect.
 * @see #getBlink() 
 */
public class EffectBlinking implements Resetable{
	/**Times the Blinker should blink	 */
	private int blincCount=0;
	/**Times the Blinker did blinked*/
	private int blincedCount=0;
	/**Duration of a "blink" in ms*/
	private long blincDuration=100;
	/**Let the blinker proceed ToDO: implemnt runiing true & false*/
	private boolean running=true;
	/**The blinc statuse*/
	private boolean blinc=false;

	/**time of last blinc*/
	private long lastBlinc;
	

	/**
	 * @param blincCount Times the Blinker did blinked
	 * @param blincDuration Duration of a "blink" in ms
	 * @param running start stats
	 * @see {@link #getBlink()}
	 */
	public EffectBlinking(int blincCount,long blincDuration, boolean running) {
		this.blincCount=blincCount*2;
		this.blincDuration=blincDuration;
		this.running=running;
		reset();
	}
	
	/**A blinking Effect, animation starts immediately
	 * @param blincCount Times the Blinker did blinked
	 * @param blincDuration Duration of a "blink" in ms
	 * @see {@link #getBlink()}
	 */
	public EffectBlinking(int blincCount,long blincDuration) {
		this(blincCount,blincDuration,true);
	}
	
	/**Returns the current blincing state*/
	public boolean getBlink(){
		long now=System.currentTimeMillis();
		long timeSinceLastFlash=now-lastBlinc;
		
		if(running||blincedCount>blincCount)
			return true;
		
		if(timeSinceLastFlash>=blincDuration){
			//lastBlinc=timeSinceLastFlash-blincDuration;
			lastBlinc=System.currentTimeMillis();
			blinc=!blinc;
			blincedCount++;
		}
		return blinc;
	}

	@Override
	public void reset() {
		lastBlinc=System.currentTimeMillis();
		blincedCount=0;
	}

}
