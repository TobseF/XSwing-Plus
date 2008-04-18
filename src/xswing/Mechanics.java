/*
 * @version 0.0 23.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.util.ArrayList;
import java.util.List;

public class Mechanics {
	private Ball[][] balls;
	List<Ball> ballsTemp =new ArrayList<Ball>();
	
	
	public Mechanics(BallTable ballTable) {
		this.balls=ballTable.getBalls();
	}
	
	
	/**Kills balls if ther're more than two successives in one row  */
	public void checkOfThree(){
		for(int row=0;row<8;row++){
			for(int column=0;column<8;column++){
				Ball ball=balls[column][row];
				if(ball!=null){ //is field empty?
					if(!ballsTemp.isEmpty()) //previous saved balls?
						if(ballsTemp.get(ballsTemp.size()-1).getNr()!=ball.getNr()){
							checkRow();
							ballsTemp.clear();
						}
						ballsTemp.add(ball);
				}
				else{
					checkRow();
					ballsTemp.clear();
				}
			}
			checkRow();
			ballsTemp.clear();//clear every row
		}
	}
	
	/** Kills the saved balls if the're more than two*/
	private void checkRow(){
		if(ballsTemp.size()>2){
			for(int i=0;i<ballsTemp.size();i++){
				ballsTemp.get(i).kill();
			}
		}	
	}
	
	public void checkOfFive(){
		for(int column=0;column<8;column++){
			
		}
	}
}
