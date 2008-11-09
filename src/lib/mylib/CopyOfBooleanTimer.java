/*
 * @version 0.0 14.10.2008
 * @author 	Tobse F
 */
package lib.mylib;

//TODO: Pause mode with update(int delta)
public class CopyOfBooleanTimer implements Resetable, Updateable{
	/**Number the Blinker should blink*/
	private int blincCount=0;
	/**Number the Blinker did blinked since reset*/
	private int blincedCount=0;
	/**Duration of a "blink" in ms*/
	private int blincDuration=100;
	/**Let the blinker proceed ToDO: implemnt runiing true & false*/
	private boolean running=true;
	/**The blinc statuse*/
	private boolean blinc=false;
	
	public static final int BLINK_INFINITE=-1;
	/**time of last blinc*/
	private int lastBlinc;
	

	/**A blinking Etffect
	 * @param blincCount Times the Blinker did blinked
	 * @param blincDuration Duration of a "blink" in ms
	 * @param running start stats
	 * @see {@link #getBlink()}
	 */
	public CopyOfBooleanTimer(int blincCount,int blincDuration, boolean running) {
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
	public CopyOfBooleanTimer(int blincCount,int blincDuration) {
		this(blincCount,blincDuration,true);
	}
	
	/**Returns the current blincing state*/
	public boolean getBlink(){
	/*	long now=System.currentTimeMillis();
		long timeSinceLastFlash=now-lastBlinc;
		
		if(running||blincedCount>blincCount)
			return true;
		
		if(timeSinceLastFlash>=blincDuration){
			//lastBlinc=timeSinceLastFlash-blincDuration;
			//lastBlinc=System.currentTimeMillis();
			blinc=!blinc;
			blincedCount++;
		}*/
		if(blinc){
			blinc=false;
			return true;
		}
		else
			return false;
	}

	@Override
	public void reset() {
		//lastBlinc=System.currentTimeMillis();
		blincedCount=0;
	}

	@Override
	public void update(int delta) {
		lastBlinc+=delta;
		if(lastBlinc>=blincDuration){
			lastBlinc=blincDuration-lastBlinc;
			blinc=!blinc;
			blincedCount++;
		}
	}

}
