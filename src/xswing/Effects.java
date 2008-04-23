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
	public static final int effectBouncing=1,effectDisappearing=2,efectFlash=3;
	
	public Effects() {
		if(paticleSystem==null)
			try {
				paticleSystem = ParticleIO.loadConfiguredSystem("res/emptySystem.xml");
				paticleSystem.setRemoveCompletedEmitters(false);
				effects[effectBouncing]=ParticleIO.loadEmitter("res/bounce2.xml");
				effects[effectDisappearing]=ParticleIO.loadEmitter("res/explosion2.xml");
				effects[efectFlash]=ParticleIO.loadEmitter("res/glitter2.xml");
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
			case effectDisappearing:
				effects[effect]=effects[effectDisappearing].duplicate();
				addEmitter(ball.getX()+ball.getA()/2,ball.getY()+ball.getA()/2);
				break;
			case efectFlash:
				effects[effect]=effects[efectFlash].duplicate();
				addEmitter(ball.getX()-42,ball.getY()+50);
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
		for(int i=0;i<paticleSystem.getEmitterCount();i++){
			if(paticleSystem.getEmitter(i).completed())
				paticleSystem.removeEmitter(paticleSystem.getEmitter(i));
			}
	}
}
