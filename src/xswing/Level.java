/*
 * @version 0.0 25.04.2008
 * @author 	Tobse F
 */
package xswing;

public class Level extends Ball{

	public Level(int nr, int x, int y, XSwing swing) {
		super(nr, x, y, swing);
		weight=nr+1;
		setNr(nr);
	}

	@Override
	public void update() {
		//do nothing
	}
	
	public int getLevel(){
		return	getNr();
	}
	
	public void nextLevel(){
		setNr(getNr()+1);
		weight=getNr()+1;
	}
}
