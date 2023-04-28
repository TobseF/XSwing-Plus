/*
 * @version 0.0 23.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.util.ArrayList;
import java.util.List;

public class Mechanics {
	private Ball[][] balls;
	private BallTable ballTable;
	List<Ball> ballsTemp =new ArrayList<Ball>();
	HighScoreCounter score;
	
	public Mechanics(BallTable ballTable) {
		this.ballTable=ballTable;
		this.balls=this.ballTable.getBalls();
	}
	
	public void setScore(HighScoreCounter score) {
		this.score = score;
	}
	
	/**Kills balls if ther're more than two alikes in one row -successive*/
	public void checkOfThree(){
		for(int row=0;row<8;row++){
			for(int column=0;column<8;column++){
				Ball ball=balls[column][row];
				if(ball!=null){ //is field empty?
					ballsTemp.add(ball);
					if(ballsTemp.size()>1)
						if(ballsTemp.get(ballsTemp.size()-2).getNr()!=ball.getNr()){
							ballsTemp.clear();
							ballsTemp.add(ball);
						}else
							checkRow();
				}else
					ballsTemp.clear();
			}
			ballsTemp.clear();//clear every row
		}
	}
	
	/** Kills the saved balls if the're more than two*/
	private void checkRow(){
		if(ballsTemp.size()>2){

			for(int i=0;i<ballsTemp.size();i++){
				getSurroundings(ballsTemp.get(i));
				ballsTemp.get(i).kill(1);
			}
			if(score!=null)
				score.score(calculateScore(ballsTemp));
		}	
	}
	
	/** Calculates the score of the balls to kill*/
	private int calculateScore(List<Ball> ballsTemp){
		int score=0;
		for(int i=0;i<ballsTemp.size();i++){
			score+=ballsTemp.get(i).getWeight();
		}
		return score*ballsTemp.size();
	}
	
	/**Checks Surrounding four Balls of the given wether they're null, an other or the same balls as the given.
	 * In last case, the ball will be added to ballsTemp -only if not happened*/	
	private void getSurroundings(Ball ball){
		int[] pos=ballTable.getField(ball);
		Ball checkinBall;
		int[][] positions={{0,1},{1,0},{0,-1},{-1,0}};
		for(int i=0;i<positions.length;i++){
			checkinBall=ballTable.getPlayFieldBall(pos[0]+positions[i][0],pos[1]+positions[i][1]);
			if(checkinBall!=null)
				if(checkinBall.getNr()==ball.getNr())
					if(!ballsTemp.contains(checkinBall))
						ballsTemp.add(checkinBall);
		}
		
	}
	
	/**Checks wether there are five balls on top of the other -and shrincs them*/
	public void checkOfFive(){
		List<Ball> ballsT =new ArrayList<Ball>();
		for(int column=0;column<8;column++){
			for(int row=0;row<8;row++){
				Ball b=ballTable.getBall(column,row);
				if(b!=null){
					ballsT.add(b);
					if(ballsT.size()>1){
						if(ballsT.get(ballsT.size()-2).getNr()!=b.getNr()){
							ballsT.clear();
							ballsT.add(b);
						}
						else{
							if(ballsT.size()>4)
								shrinkRow(ballsT);
						}
					}			
				}
			}
			ballsT.clear();
		}
	}
	
	/**Shrincs five balls to one havy*/
	private void shrinkRow(List<Ball> ballsT){
		int weight=0;
		for(int i=ballsT.size()-1;i>0;i--){
			weight+=ballsT.get(i).getWeight();
			ballsT.get(i).kill(2);
		}
		weight+=ballsT.get(0).getWeight();
		ballsT.get(0).setWeight(weight);
	}
	
}

