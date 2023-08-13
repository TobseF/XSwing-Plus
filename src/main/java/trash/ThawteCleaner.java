/*
 * @version 0.0 08.06.2008
 * @author Tobse F
 */
package trash;

import java.io.*;

public class ThawteCleaner {

    public final static int LINE_LENGTH = 76;
    public final static String START = "BEGIN PKCS";
    public final static String END = "END PKCS";

    public static void main(String[] args) throws IOException {
        /*
         * if(args.length != 1) { System.out.println("Usage: java " +
         * ThawteCleaner.class.getName() + " inFile"); System.exit(1); }
         */
        args = new String[]{"my.cert"};
        BufferedReader r = new BufferedReader(new FileReader(args[0]));
        BufferedWriter w = new BufferedWriter(new FileWriter(args[0] + ".clean"));
        String line;
        while ((line = r.readLine()).indexOf(START) == -1) {
        }
        w.write(line);
        w.newLine();
        int outIndex = 0;
        int inIndex = 0;
        while ((line = r.readLine()) != null) {
            if (line.indexOf(END) != -1) {
                if (outIndex != 0) {
                    w.newLine();
                }
                w.write(line);
                break;
            }
            inIndex = 0;
            while (inIndex < line.length()) {
                w.write(line.charAt(inIndex));
                outIndex++;
                if (outIndex == LINE_LENGTH) {
                    w.newLine();
                    outIndex = 0;
                }
                inIndex++;
            }
        }
        w.close();
        r.close();
    }
}