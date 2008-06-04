/*
 * @version 0.0 25.04.2008
 * @author 	Tobse F
 */
package xswing;

import lib.Resetable;
import lib.SObject;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class BallCounter extends SObject implements Resetable{
	private Font font;
	private int balls=0;
	private Level level;
	private int letterLenght;
	
	public BallCounter(Font font,int x, int y) {
		super(x,y);
		this.font=font;
		letterLenght=font.getWidth("0");
	}
	
	public void reset(){
		balls=0;
	}
	
	public void count() {
		balls++;
		if(level!=null)
			if(balls%50==0)
				level.nextLevel();
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	@Override
	public void draw(Graphics g) {
		font.drawString(x-((balls+"").length()-1)*letterLenght/2, y,""+balls);
	}
}
