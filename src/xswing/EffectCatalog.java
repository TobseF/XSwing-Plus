/*
 * @version 0.0 18.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.io.IOException;

import lib.mylib.Resetable;
import lib.mylib.Sound;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.*;

/**Provides the particle effects*/
public class EffectCatalog implements Resetable{
	private static ParticleSystem paticleSystem=null;
	private ConfigurableEmitter[] effects=new ConfigurableEmitter[4];
	private final int effect=0;
	public static final int effectBouncing = 1 ;
	public static final int effectDisappearing = 2 ;
	public static final int efectFlash = 3 ;
	private Sound[] sounds=new Sound[4];
	private boolean showParticles=true;
	
	public void setSound(Sound sound, int effectNr) {
		sounds[effectNr] = sound;
	}
	
	public EffectCatalog() {
		if(paticleSystem==null)
			try {
				paticleSystem = ParticleIO.loadConfiguredSystem("res/emptySystem.xml");
				paticleSystem.setRemoveCompletedEmitters(true);
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
		if(showParticles){
			int a=Ball.A;
			switch(effectNr){
				case effectBouncing:
					effects[effect]=effects[effectBouncing].duplicate();
					addEmitter(ball.getX()+a/2,ball.getY()+a);
					break;
				case effectDisappearing:
					effects[effect]=effects[effectDisappearing].duplicate();
					addEmitter(ball.getX()+a/2,ball.getY()+a/2);
					break;
				case efectFlash:
					effects[effect]=effects[efectFlash].duplicate();
					addEmitter(ball.getX()-42,ball.getY()+50);
					break;
				default:
					break;
			}
			if(sounds[effectNr]!=null)
				if(!sounds[effectNr].playing())
					sounds[effectNr].play();
		}
	}
	
	public void reset(){
		paticleSystem.removeAllEmitters();
		paticleSystem.reset();
	}
	
	public void draw(Graphics g) {
		if(showParticles)
			paticleSystem.render();
	}
	
	public void update(int delta) {
		if(showParticles)
			paticleSystem.update(delta);
	}
	
	public void setShowParticles(boolean showParticles) {
		this.showParticles = showParticles;
	}
	
	public boolean isShowParticles() {
		return showParticles;
	}
}
