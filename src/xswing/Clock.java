/*
 * @version 0.0 15.04.2008
 * @author 	Tobse F
 */
package xswing;

import lib.Resetable;
import lib.SObject;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

/**The timer which counts the seconds since the game was started*/
public class Clock extends SObject implements Resetable{
	private long timeS;
	private static long ttime;
	private Font font;
	
	public Clock(Font font,int x, int y) {
		super(x,y);
		this.font=font;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}

	public void start(){
		
//		ttime=container.getTime();
		ttime=System.currentTimeMillis();
	}
	
	public void reset(){
		start();
	}
	
	public void tick(){
		timeS=((System.currentTimeMillis()-ttime))/1000;
	}
	
	@Override
	public void draw(Graphics g) {
		renderTimer();
	}
	
	public void renderTimer(){
		String s=String.format("%02d",(int)timeS%60);
		String m=String.format("%02d",(int)(timeS/60)%60);
		String h=String.format("%02d",(int)((timeS/60)/60)%60);
		font.drawString(x,y,h+":"+m+":"+s);
	}

}
