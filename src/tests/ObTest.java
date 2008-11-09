/*
 * @version 0.0 30.05.2008
 * @author 	Tobse F
 */
package tests;

import java.awt.Point;

public class ObTest {
	String s="X";
	Point p=new Point(8,8);
	
	public ObTest() {
		System.out.println(p.x);
		p=modP(p);
		System.out.println(p.x);
		
		System.out.println(s);
		modS(s);
		
		System.out.println(s);
		
//		OfObTest2 ob=new OfObTest2(s);
//		System.out.println(s);
	}
	
	public void modS(String sn){
		sn=sn.toLowerCase();
	}
	
	
	
	public Point modP(Point p){
//		p2.setLocation(4, 4);
		p.setLocation(1, 1);
		//this.p=p;
		return p;
	}
	
	public static void main(String[] args) {
		new ObTest();

	}

}
