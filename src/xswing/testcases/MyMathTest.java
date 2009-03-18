/*
 * @version 0.0 19.12.2008
 * @author 	Tobse F
 */
package xswing.testcases;

import static org.junit.Assert.assertTrue;
import lib.mylib.math.MyMath;

import org.junit.Test;


public class MyMathTest {
	
	@Test
	public void getRandom(){
		for(int i = 0; i < 10; i++){
			int random = MyMath.getRandomAberrance(100, 5);
			assertTrue(random >= 95 && random <= 105);
		}
	}
	
	@Test
	public void getFloat(){
		for(int i = 0; i < 10; i++){
			float random = MyMath.getFloat(0.2f, 0.8f);
			//System.out.println(random);
			assertTrue(random >= 0.2f && random <= 	0.8f	);
		}
	}
	
	@Test
	public void getInt(){
		for(int i = 0; i < 40; i++){
			int random = MyMath.getInt(20, 100);
			assertTrue(random >= 20 && random <= 100);
		}
	}
	
	@Test
	public void getDegree(){
		System.out.println(MyMath.getDegree(0, 0, 0, -1));
		System.out.println(Math.toRadians(MyMath.getDegree(0, 0, 0, -1)));
	}
}
