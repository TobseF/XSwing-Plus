/*
 * @version 0.0 16.11.2008
 * @author Tobse F
 */
package xswing.testcases;

import lib.mylib.highscore.HighScoreFormatter;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class HighScoreFormatterTest {

    HighScoreFormatter scoreFormatter = new HighScoreFormatter();

    public HighScoreFormatterTest() {
        scoreFormatter = new HighScoreFormatter();
    }

    @Test
    public void testHighScoreFormatter() {
        String[][] testHighScoreList = {{"10", "Tobse"}, {"2", "Marco"},
                {"150", "Nina"}, {"3000", "Karl"}};
        /*
         * [10][Tobse] [2][Marco] [150][Nina] [3000][Karl]
         */

        scoreFormatter.printTable(testHighScoreList);

        // Sort list
        String[][] sortedHighScoreList = scoreFormatter.sortScore(testHighScoreList);
        System.out.println(testHighScoreList.length + " elements sorted:");
        scoreFormatter.printTable(sortedHighScoreList);

        // Add new Element
        System.out.println("Added Nico");
        sortedHighScoreList = scoreFormatter.addScore(testHighScoreList, "Nico", 13, 10);
        scoreFormatter.printTable(sortedHighScoreList);
        /*
         * [3000][Karl] [150][Nina] [13][Nico] [10][Tobse] [2][Marco]
         */

        // Crypt score
        String[][] cryptedHighScoreList = scoreFormatter.cryptScore(sortedHighScoreList);
        System.out.println("Crypted Score");
        scoreFormatter.printTable(cryptedHighScoreList);

        // Shrinc score in one line
        String cryptedScoreInOneLine = scoreFormatter
                .shrincScoreInOneLine(cryptedHighScoreList);
        System.out.println("Score, shrinced in one Line");
        System.out.println(cryptedScoreInOneLine);

        // deShrinc score from one line
        String[][] enCryptedHighScoreList = scoreFormatter
                .deShrincHighScoreFromOneLine(cryptedScoreInOneLine);
        System.out.println();
        System.out.println("Score, deShrinced from one score line:");
        scoreFormatter.printTable(enCryptedHighScoreList);

        // enCrypt score
        enCryptedHighScoreList = scoreFormatter.decryptScore(enCryptedHighScoreList);
        System.out.println("enCrypted Score:");
        scoreFormatter.printTable(enCryptedHighScoreList);

        // compare sorted & enCrypted highScoreTable
        assertArrayEquals(sortedHighScoreList, enCryptedHighScoreList);
    }

    @Test
    public void removeLinesWithEmptyFieldsTest() {
        String[][] testHighScoreList = {{"10", "Tobse"}, {"", "Marco"}, {"", ""},
                {"3000", ""}, {"100", "Tim"}};
        testHighScoreList = scoreFormatter.removeLinesWithEmptyFields(testHighScoreList);
        assertArrayEquals(testHighScoreList, new String[][]{{"10", "Tobse"},
                {"100", "Tim"}});
    }
}
