/*
 * @version 0.0 15.04.2008
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public class Timer extends SObject{
	String time="sdsd",t="";
	long timeS;
	static long ttime;
	Font font;
	
	public Timer(Font font,int x, int y) {
		super(x,y);
		this.font=font;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}

	public void start(AppGameContainer game){
		ttime=game.getTime();
	}
	
	public void tick(GameContainer container){
		timeS=((container.getTime()-ttime))/1000;
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}
	
	public void renderTimer(){
		String s=String.format("%02d",(int)timeS%60);
		String m=String.format("%02d",(int)(timeS/60)%60);
		String h=String.format("%02d",(int)((timeS/60)/60)%60);
		font.drawString(x,y,h+":"+m+":"+s);
	}

}
