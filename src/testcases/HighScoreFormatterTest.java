/*
 * @version 0.0 16.11.2008
 * @author 	Tobse F
 */
package testcases;

import lib.mylib.HighScoreFormatter;

import org.junit.Test;

public class HighScoreFormatterTest {
	public HighScoreFormatterTest() {
		 testHighScoreFormatter();
	}	
	
	public static void main(String[] args) {
		new HighScoreFormatterTest();
	}
	@Test
	public void testHighScoreFormatter() {
		String[][] testHighScoreList={{"10","Tobse"},{"2","Marco"},{"150","Nina"},{"3000","Karl"}};
		HighScoreFormatter scoreFormatter=new HighScoreFormatter();
		
		scoreFormatter.printTable(testHighScoreList);
		System.out.println("Sorted ("+testHighScoreList.length+" elements)");
		testHighScoreList=scoreFormatter.sortScore(testHighScoreList);
		scoreFormatter.printTable(testHighScoreList);
		
		System.out.println("Added Nico");
		testHighScoreList=scoreFormatter.addScore(testHighScoreList, "Nico", 13);
		scoreFormatter.printTable(testHighScoreList);
		
		testHighScoreList=scoreFormatter.cryptScore(testHighScoreList);
		System.out.println("Crypted Score");
		scoreFormatter.printTable(testHighScoreList);
		
		String oneLine=scoreFormatter.shrincScoreInOneLine(testHighScoreList);
		
		System.out.println("Score, shrinced in one Line");
		System.out.println(oneLine);
		System.out.println("");
		System.out.println("Score, deShrinced from one score line");
		testHighScoreList=scoreFormatter.deShrincHighScoreFromOneLine(oneLine);
		scoreFormatter.printTable(testHighScoreList);
		
		testHighScoreList=scoreFormatter.decryptScore(testHighScoreList);
		scoreFormatter.printTable(testHighScoreList);
	}

}
