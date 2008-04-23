/*
 * @version 0.0 25.04.2008
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class BallCounter extends SObject{
	private Font font;
	private int balls=0;
	private Level level;
	
	public BallCounter(Font font,int x, int y) {
		super(x,y);
		this.font=font;
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
		font.drawString(x-((balls+"").length()-1)*5, y,""+balls);
	}
}
