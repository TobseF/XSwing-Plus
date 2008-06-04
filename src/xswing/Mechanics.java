/*
 * @version 0.0 23.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.util.*;

/** The mechanic which executes all game logics (finding 3 Balls in a row...)*/
public class Mechanics {
	private Ball[][] balls;
	private BallTable ballTable;
	private List<Ball> ballsTemp =new ArrayList<Ball>();
	
	public Mechanics(BallTable ballTable) {
		this.ballTable=ballTable;
		this.balls=this.ballTable.getBalls();
	}
	
	/**Kills balls if ther're more than two alikes in one row -successive*/
	public void checkOfThree(){
		for(int row=0;row<8;row++){
			for(int column=0;column<8;column++){
				Ball ball=balls[column][row];
				if(ball!=null){ //is field filled?
					ballsTemp.add(ball);
					if(ballsTemp.size()>1)
						if(!ball.compare(ballsTemp.get(ballsTemp.size()-2))){
						//if(ballsTemp.get(ballsTemp.size()-2).getNr()!=ball.getNr()){
							ballsTemp.clear();
							ballsTemp.add(ball);
						}else
							checkRow(ballsTemp);
				}else
					ballsTemp.clear();//clear if theres an ampty field
			}
			ballsTemp.clear();//clear every row
		}
	}
	
	public Ball[][] getBalls() {
		return balls;
	}

	/** Kills the saved balls if the're more than two*/
		private void checkRow(List<Ball> ballsTemp){
			if(ballsTemp.size()>2){
					ballsTemp.get(0).kill(Ball.WAITING_FOR_KILL);		
			}	
		}
		
	public void getConnectedBalls(Ball b,int i){
		getConnectedBalls(ballTable.getBall(ballTable.getField(b)[0],ballTable.getField(b)[1]));
	}

	/**Checks alls surrounding Balls of the given*/
	public List<Ball> getConnectedBalls(Ball ball){
		List<Ball> ballsTemp =new ArrayList<Ball>();
		ballsTemp.add(ball);
		for(int i=0;i<ballsTemp.size();i++){
			ballsTemp=getSurroundings(ballsTemp.get(i),ballsTemp);
		}
		return ballsTemp;
	}
	
	/**Checks surrounding four Balls of the given wether they're null, an other or the same balls as the given.
	 * In last case, the ball will be added to ballsTemp -only if not happened*/	
	private List<Ball> getSurroundings(Ball ball,List<Ball> ballsTemp){
		int[] pos=ballTable.getField(ball);
		Ball checkinBall;
		int[][] positions={{0,1},{1,0},{0,-1},{-1,0}};
		for(int i=0;i<positions.length;i++){
			checkinBall=ballTable.getPlayFieldBall(pos[0]+positions[i][0],pos[1]+positions[i][1]);
			if(checkinBall!=null)
				if(checkinBall.compare(ball))
					if(!ballsTemp.contains(checkinBall))
						ballsTemp.add(checkinBall);
		}
		return ballsTemp;
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
			ballsT.get(i).kill(Ball.WAITING_FOR_SHRINK);
		}
		weight+=ballsT.get(0).getWeight();
		ballsT.get(0).setWeight(weight);
	}
	
	public void performWeight(int[] weights){

		for(int i=0;0<8;i=i+2){
			int w1=weights[i];
			int w2=weights[i+1];
			
		}
	}

	/** Calculates the score of the balls to kill*/
	public int calculateScore(List<Ball> ballsTemp){
		int score=0;
		for(int i=0;i<ballsTemp.size();i++){
			score+=ballsTemp.get(i).getWeight();
		}
		return score*ballsTemp.size();
	} 
	
}

