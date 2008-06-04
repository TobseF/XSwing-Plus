/*
 * @version 0.0 30.05.2008
 * @author 	Tobse F
 */
package xswing;

import java.util.ArrayList;
import java.util.List;

public class BallKiller {
	List<Ball>ballsToKill=new ArrayList<Ball>();
	private static int WAITING_BEFORE_KILL=260;
	private int timeBeforeKill=WAITING_BEFORE_KILL;
	private Mechanics mechanics;
	private HighScoreCounter score;
	
	public BallKiller(Mechanics mechanics,HighScoreCounter score) {
		this.mechanics=mechanics;
		this.score=score;
	}
	
	public void addBall(Ball ball){
		ballsToKill.add(ball);
	}
	
	public void reset(){
		timeBeforeKill=WAITING_BEFORE_KILL;
	}

	public void update(int delta){
		if(ballsToKill.size()>0){
			timeBeforeKill-=delta;
			if(timeBeforeKill<=0)
				killBalls();
		}
	}
	
	private void killBalls(){
		Ball bTemp=ballsToKill.get(0);
		score.score(mechanics.calculateScore(ballsToKill=mechanics.getConnectedBalls(bTemp)));
		for(int i=0;i<ballsToKill.size();i++){
			Ball b=ballsToKill.get(i);
			b.kill(Ball.KILL_IMMEDIATELY);
		}
		reset();
		ballsToKill.clear();
	}
	
}
