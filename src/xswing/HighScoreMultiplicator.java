/*
 * @version 0.0 28.04.2008
 * @author 	Tobse F
 */
package xswing;

import lib.Resetable;
import lib.SObject;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

/**Draws and returns the score multiplicator which can be set to the maximum of four and decrease automatically*/
public class HighScoreMultiplicator extends SObject implements Resetable{
	private SpriteSheet multiplicator;
	private int multi=0;
	private int timer=2500, timerTemp=timer;
	
	public HighScoreMultiplicator(int x, int y,SpriteSheet multiplicator) {
		super(x,y);
		this.multiplicator=multiplicator;
	}
	
	/**Sets the score multiplicator to 4*/
	public void score(){
		multi=4;
		timerTemp=timer;
	}
	
	public void update(int delta) {
		timerTemp+=delta;
		if(timerTemp>=timer){
			if(multi>0){
				multi--;
			}
			timerTemp=0;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		if(multi>0)
			g.drawImage(multiplicator.getSprite(0, 3-multi),x,y);
	}
	
	/** Returns the Score multiplication factor*/
	public int getMulti() {
		return multi+1;
	}

	@Override
	public void reset() {
		multi=0;
		timerTemp=0;
	}

}
