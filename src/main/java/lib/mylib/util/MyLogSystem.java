/*
 * @version 0.0 02.05.2009
 * @author Tobse F
 */
package lib.mylib.util;

import org.newdawn.slick.util.DefaultLogSystem;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class MyLogSystem extends DefaultLogSystem {

    /**
     * Stores the complete log message
     */
    private static final StringBuilder logMessage = new StringBuilder();
    /**
     * <code>True</code> if the Log should be also send to the console
     */
    private static boolean printLogInConsole = true;
    /**
     * Line seperator
     */
    private static final String newLine = System.getProperty("line.separator");

    public MyLogSystem() {
        PrintStream newOut = new PrintStream(new OutputStream() {

            @Override
            public void write(int arg0) throws IOException {
                logMessage.append((char) arg0);
                if (printLogInConsole) {
                    System.out.print((char) arg0);
                }
            }
        });
        out = new PrintStream(newOut);
    }

    /**
     * Returns the Log as String
     *
     * @return the Log as String
     */
    public static String getLog() {
        return logMessage.toString();
    }

    /**
     * Returns the last (the newest) Log entry as String
     *
     * @return the last (the newest) Log entry as String
     */
    public static String getLastLine() {
        return logMessage.substring(logMessage.lastIndexOf(newLine));
    }

    /**
     * Sets if the LogSystem should print the log in the console
     *
     * @param printLogInConsole <code>true</code> for console output
     */
    public static void setPrintLogInConsole(boolean printLogAlsoInConsole) {
        printLogInConsole = printLogAlsoInConsole;
    }

    @Override
    public String toString() {
        return getLog();
    }
}
