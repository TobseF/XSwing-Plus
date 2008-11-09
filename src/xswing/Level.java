/*
 * @version 0.0 25.04.2008
 * @author 	Tobse F
 */
package xswing;

import lib.mylib.EffectBlinking;
import lib.mylib.Resetable;
import lib.mylib.SpriteSheet;

import org.newdawn.slick.Graphics;

/** The ball which counts the levels */
public class Level extends Ball implements Resetable{
	
	/**The Level with wich the game starts (only for reset)*/
	private int startLeveL=3;
	/**The Highest reachable level*/
	private int maxLeveL=45;
	
	EffectBlinking blinking;
	/*/**A list of Balls which will be released within the next move -for Levels
	private ArrayList<Integer>preDefinedBalls=new ArrayList<Integer>();*/

	public Level(int nr, int x, int y,SpriteSheet ballsSpriteSheet) {
		super(nr, x, y,ballsSpriteSheet);
		weight=nr+1;
		setNr(nr);
		blinking=new EffectBlinking(9,250,false);
	}
	
	public void reset(){
		setLevel(startLeveL);
		blinking.reset();
	}
	
	public void setLevel(int level){
		setNr(level+1);
		weight=level+1;
	}

	@Override
	public void update(int delta) {
		//do nothing
	}
	
	@Override
	public void draw(Graphics g) {
		if(blinking.getBlink())
			super.draw(g);
	}
	
	/** Returns the current level */
	public int getLevel(){
			return	getNr();
	}
	
	/** Swicht to the next level */
	public void nextLevel(){
		if(!(getNr()+1>=maxLeveL)){
			setNr(getNr()+1);
			weight=getNr()+1;
			blinking.reset();
		}
	}
}
