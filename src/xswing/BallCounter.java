/*
 * @version 0.0 25.04.2008
 * @author 	Tobse F
 */
package xswing;

import lib.mylib.Resetable;
import lib.mylib.SObject;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;


/**
 * @author Tobse
 *
 * GUI class which draws the number of already released balls 
 */
public class BallCounter extends SObject implements Resetable{
	private Font font;
	private int balls=0;
	private Level level;
	private int letterLenght;
	private int ballsPerLevel=50;
	
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
			if(balls%ballsPerLevel==0)
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
