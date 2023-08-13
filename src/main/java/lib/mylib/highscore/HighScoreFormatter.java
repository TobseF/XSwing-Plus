/*
 * @version 0.0 26.01.2008
 * @author Tobse F
 */
package lib.mylib.highscore;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

/**
 * Provied useful funtions to sort, crypt, print, store and load a ztwo dimensioned
 * highScoreList. ([line][0] = socre, [line][1] = name)
 *
 * @author Tobse
 * @see #sortScore(String[][], boolean)
 * @see #cryptScore(String[][])
 * @see #decryptScore(String[][])
 */
public class HighScoreFormatter {

    private static final String VALUE_SEPERATOR = "_";
    private static final String LINE_SEPERATOR = ";";
    private final EasyCrypter easyCrypter;

    public HighScoreFormatter() {
        easyCrypter = new EasyCrypter();
    }

    /**
     * Converts a highScoreTable to a String in one line
     *
     * @param highScoreTable
     * @return highSoreTableInOneLine
     * @see #deShrincHighScoreFromOneLine(String)
     */
    public String shrincScoreInOneLine(String[][] highScoreTable) {
        String higscoreInOneLine = "";
        for (String[] highScoreValue : highScoreTable) {
            higscoreInOneLine += highScoreValue[0] + VALUE_SEPERATOR + highScoreValue[1]
                    + LINE_SEPERATOR;
        }
        return higscoreInOneLine;
    }

    /**
     * Converts a highScoreTable which is in one line form back to a String[] Array.
     *
     * @param highScoreTable
     * @return highSoreTableInOneLine ([line][0] = socre, [line][1] = name)
     * @see #shrincHighScoreFromOneLine(String[][])
     */
    public String[][] deShrincHighScoreFromOneLine(String higscoreInOneLine) {
        String[] highScoreLines = higscoreInOneLine.split(LINE_SEPERATOR);
        List<String[]> highScoreInTable = new ArrayList<String[]>();
        for (String scoreLine : highScoreLines) {
            highScoreInTable.add(scoreLine.split(VALUE_SEPERATOR));
        }
        return highScoreInTable.toArray(new String[0][0]);
    }

    /**
     * Formats a given highScoreTable t a printable string.
     *
     * @param highScoreTable
     * @return consoleOutputAsString
     */
    public String tableToString(String[][] highScoreTable) {
        String tableAsString = "";
        for (String[] row : highScoreTable) {
            for (String field : row) {
                tableAsString += "[" + field + "]";
            }
            tableAsString += "\n";
        }
        return tableAsString;
    }

    /**
     * Print outs a given highScoreTable in the cosole.
     *
     * @param highScoreTable
     * @return consoleOutputAsString
     * @see #tableToString(String[][])
     */
    public String printTable(String[][] highScoreTable) {
        System.out.println(tableToString(highScoreTable));
        return tableToString(highScoreTable);
    }

    /**
     * Sorts a given HighScoreTable. <code>descending</code> defnies the sorting direction.
     *
     * @param highScoreTable ([line][0] = socre, [line][1] = name)
     * @return sortedHighScoreTable
     */
    public String[][] sortScore(String[][] highScoreTable, boolean descending) {
        highScoreTable = addZeros(highScoreTable);
        JTable tabelle = new JTable(highScoreTable, new String[]{"Score", "name"});
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabelle.getModel());
        List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        if (descending) {
            sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
        } else {
            sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        }
        sorter.setSortKeys(sortKeys);
        tabelle.setRowSorter(sorter);
        String[][] sortedScore = new String[tabelle.getRowCount()][2];
        for (int i = 0; i < tabelle.getRowCount(); i++) {
            sortedScore[i][0] = (String) tabelle.getValueAt(i, 0);
            sortedScore[i][1] = (String) tabelle.getValueAt(i, 1);
        }
        sortedScore = removeZeros(sortedScore);
        return sortedScore;
    }

    /**
     * Sorts a given HighScoreTable in a descending directon. (Player with the highest score
     * value gets the first index)
     *
     * @param highScoreTable ([line][0] = socre, [line][1] = name)
     * @return sortedHighScoreTable
     */
    public String[][] sortScore(String[][] highScoreTable) {
        return sortScore(highScoreTable, true);
    }

    /**
     * Removes all lines with an epmty value.
     *
     * @param higScore
     * @return higSocreWithoutEmptyFields
     */
    public String[][] removeLinesWithEmptyFields(String[][] higScore) {
        List<String[]> list = new ArrayList<String[]>();
        for (String[] line : higScore) {
            if (!line[0].isEmpty() && !line[1].isEmpty()) {
                list.add(line);
            }
        }
        return list.toArray(new String[0][0]);
    }

    public String[][] cryptScore(String[][] score) {
        // copy to let a given score unchanged
        String[][] cryptedScore = new String[score.length][2];
        for (int i = 0; i < score.length; i++) {
            cryptedScore[i][0] = easyCrypter.enCrypt(score[i][0]);
            cryptedScore[i][1] = easyCrypter.enCrypt(score[i][1]);
        }
        return cryptedScore;
    }

    /**
     * Decrypts the given HighScore table. Encryption errors or wrong formattet Strings (e.g.
     * if score is no number), an empty Table will be returned.
     *
     * @param score an with {@link #cryptScore(String[][])} crypted score table
     * @return encrypted high score table ([score][Name]), or an epty table, if there was en
     * encrypton error
     */
    public String[][] decryptScore(String[][] score) {
        boolean errorWhileDecrypting = (score.length == 0 || score[0].length % 2 != 0);

        String[][] cryptedScore = new String[score.length][2];

        for (int i = 0; i < score.length && !errorWhileDecrypting; i++) {
            // Score
            cryptedScore[i][0] = easyCrypter.deCrypt(score[i][0]);
            // Names
            cryptedScore[i][1] = easyCrypter.deCrypt(score[i][1]);

            errorWhileDecrypting = cryptedScore[i][0].isEmpty()
                    || cryptedScore[i][1].isEmpty();
            try {
                Integer.parseInt(cryptedScore[i][0]);
            } catch (NumberFormatException e) {
                errorWhileDecrypting = true;
            }
        }

        if (errorWhileDecrypting) {
            return new String[0][0];
        } else {
            return cryptedScore;
        }
    }

    /**
     * Searches for the lengest String value in score and filles score with leadig zeros.
     * -Useful for sorting<br>
     * e.g.<br>
     * st={13, 200, 1, 1333} ==> {0013, 0200, 0001, 1333}
     *
     * @param highScoreTable
     * @return highScoreTableWithZeroedScore
     * @see #removeZeros(String[][])
     */
    private String[][] addZeros(String[][] highScoreTable) {
        int maxLenght = 0;
        for (String[] scoreValue : highScoreTable) {
            maxLenght = Math.max(maxLenght, scoreValue[0].length());
        }
        for (String[] sc : highScoreTable) {
            String z = "";
            for (int i = maxLenght - sc[0].length(); i > 0; i--) {
                z += "0";
            }
            sc[0] = z + sc[0];
        }
        return highScoreTable;
    }

    /**
     * Reoves all leading zeros of the score.<br>
     * e.g.<br>
     * st={0013, 0200, 0001, 1333} ==> {13, 200, 1, 1333}
     *
     * @param highScoreTableWithZeroedScore
     * @return highScoreTableWithoutZeroedScore
     * @see #addZeros(String[][])
     */
    private String[][] removeZeros(String[][] highScoreTableWithZeroedScore) {
        for (String[] sc : highScoreTableWithZeroedScore) {
            sc[0] = "" + Integer.parseInt(sc[0]);
        }
        return highScoreTableWithZeroedScore;
    }

    /*
     * / Reads the HighScore from a given file and stores values in a String[] Array.
     * @param dataFromFile
     * @return HighScoreTable ([line][0] = socre, [line][1] = name) public String[][]
     * readScore2(String dataFromFile) { String[] daten = dataFromFile.split(LINE_SEPERATOR);
     * List<String[]> score = new ArrayList<String[]>(); for (String d : daten) {
     * score.add(d.split(VALUE_SEPERATOR)); } return score.toArray(new String[0][0]); }
     */

    /**
     * Adds a new score (and name) value to a given HighScoreTable. Result is sorted an table
     * lenght limited to the <code>maxElements</code> lenght.
     *
     * @param scoreTable  HihScoreTable
     * @param name        PlayerName
     * @param score       PlayerScore
     * @param maxElements maximum lenght of the ScoreTable result
     * @return newScoreTable
     */
    public String[][] addScore(String[][] scoreTable, String name, int score, int maxElements) {
        String[][] newScoreTable = new String[scoreTable.length + 1][2];
        System.arraycopy(scoreTable, 0, newScoreTable, 0, scoreTable.length);
        newScoreTable[newScoreTable.length - 1][0] = String.valueOf(score);
        newScoreTable[newScoreTable.length - 1][1] = name;
        newScoreTable = sortScore(newScoreTable);
        // Limit table lenght to maxElements
        if (scoreTable.length > maxElements) {
            String[][] temp2 = new String[maxElements][2];
            System.arraycopy(newScoreTable, 0, temp2, 0, maxElements);
            newScoreTable = temp2;
        }
        return newScoreTable;
    }
}