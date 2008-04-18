/*
 * @version 0.0 18.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.io.IOException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.*;

public class Effects{
	private static ParticleSystem paticleSystem=null;
	private ConfigurableEmitter[] effects=new ConfigurableEmitter[4];
	private final int effect=0;
	public static final int effectBouncing=1,effectDisaperaing=2,efectFlash=3;
	
	public Effects() {
		if(paticleSystem==null)
			try {
				paticleSystem = ParticleIO.loadConfiguredSystem("res/bounceSystem.xml");
				paticleSystem.setRemoveCompletedEmitters(true);
				effects[effectBouncing]=ParticleIO.loadEmitter("res/bounce.xml");
				effects[effectDisaperaing]=ParticleIO.loadEmitter("res/explosion.xml");
				effects[efectFlash]=ParticleIO.loadEmitter("res/glitter.xml");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void addEmitter(int x, int y){
		effects[effect].replay();
		effects[effect].setPosition(x, y);
    	paticleSystem.addEmitter(effects[effect]);
	}
	
	public void addEffect(Ball ball, int effectNr){
		switch(effectNr){
			case effectBouncing:
				effects[effect]=effects[effectBouncing].duplicate();
				addEmitter(ball.getX()+ball.getA()/2,ball.getY()+ball.getA());
				break;
			case effectDisaperaing:
				effects[effect]=effects[effectDisaperaing].duplicate();
				addEmitter(ball.getX()+ball.getA()/2,ball.getY()+ball.getA()/2);
				break;
			case efectFlash:
				effects[effect]=effects[efectFlash].duplicate();
				addEmitter(ball.getX()-80,ball.getY()+55);
				break;
			default:
				break;
		}
	}
	
	public void reset(){
		paticleSystem.removeAllEmitters();
		paticleSystem.reset();
	}
	
	public void draw(Graphics g) {
		paticleSystem.render();
	}
	
	public void update(int delta) {
		paticleSystem.update(delta);
	}
}
