/*
 * @version 0.0 28.04.2008
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

public class Multiplicator extends SObject{
	private SpriteSheet multiplicator;
	private int multi=0;
	private int timer=2500, timerTemp=timer;
	
	public Multiplicator(int x, int y,SpriteSheet multiplicator) {
		super(x,y);
		this.multiplicator=multiplicator;
	}
	
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
	
	/** Returns the Score ,ultiplicator*/
	public int getMulti() {
		System.out.println("multi: "+(multi+1));
		return multi+1;
	}

}
