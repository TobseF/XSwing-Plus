/*
 * @version 0.0 02.05.2009
 * @author Tobse F
 */
package lib.mylib.util;

import java.io.*;
import org.newdawn.slick.util.DefaultLogSystem;

public class MyLogSystem extends DefaultLogSystem {

	private static StringBuilder logMessage = new StringBuilder();
	private static boolean printLogInConsole = true;

	public MyLogSystem() {
		PrintStream newOut = new PrintStream(new OutputStream() {
			@Override
			public void write(int arg0) throws IOException {
				logMessage.append((char) arg0);
				if(printLogInConsole){
					System.out.print((char) arg0);
				}
			}
		});
		out = new PrintStream(newOut);
	}

	public static String getLog() {
		return logMessage.toString();
	}
	
	/** Sets if the LogSystem should print the log in the console
	 * @param printLogInConsole <code>true</code> for console output
	 */
	public static void setPrintLogInConsole(boolean printLogAlsoInConsole) {
		printLogInConsole = printLogAlsoInConsole;
	}
	
	@Override
	public String toString() {
		 return logMessage.toString();
	}
}
