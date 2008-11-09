/*
 * @version 0.0 15.04.2008
 * @author 	Tobse F
 */
package xswing;

import lib.mylib.Resetable;
import lib.mylib.SObject;
import lib.mylib.Updateable;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

/**The timer which counts the seconds since the game was started*/
public class Clock extends SObject implements Resetable,Updateable{
	/** The time on timer start in ms -for calculating the timeSinceStart*/
	private long timeOnStart;
	/** The Clock Font*/
	private Font font;
	/** The Current time after timer was started in seconds */
	private long timeSinceStart;
	
	public Clock(Font font,int x, int y) {
		super(x,y);
		this.font=font;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void reset(){
		timeSinceStart=0;
	}
	
	@Override
	public void draw(Graphics g) {
		renderTimer();
	}

	@Override
	 public void update(int delta){
		timeOnStart += delta;
	        if(timeOnStart > 1000)
	        {
	            timeSinceStart++;
	            timeOnStart = 1000 - timeOnStart;
	        }
	    }
	
	public void renderTimer(){
		String s=String.format("%02d",(int)timeSinceStart%60);
		String m=String.format("%02d",(int)(timeSinceStart/60)%60);
		String h=String.format("%02d",(int)((timeSinceStart/60)/60)%60);
		font.drawString(x,y,h+":"+m+":"+s);
	}

}
