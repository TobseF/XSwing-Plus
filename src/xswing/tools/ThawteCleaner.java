package xswing.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ThawteCleaner {
    public static final int LINE_LENGTH = 76;
    public static final String START = "BEGIN PKCS";
    public static final String END = "END PKCS";

    public static void main(String[] args) throws IOException {
        String line;
        args = new String[]{"my.cert"};
        BufferedReader r = new BufferedReader(new FileReader(args[0]));
        BufferedWriter w = new BufferedWriter(new FileWriter(String.valueOf(args[0]) + ".clean"));
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
                if (++outIndex == 76) {
                    w.newLine();
                    outIndex = 0;
                }
                ++inIndex;
            }
        }
        w.close();
        r.close();
    }
}
