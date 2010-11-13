/*
 * @version 0.0 20.09.2009
 * @author Tobse F
 */
package lib.mylib.highscore;

import java.util.*;
import javax.swing.table.AbstractTableModel;
import xswing.DefaultArgs.Args;
import lib.mylib.util.MyOptions;

/**
 * @author Tobse A Highscore table which is evertime sorted by score and limited by
 *         #maximumElements
 */
public class HighScoreTable extends AbstractTableModel implements Iterable<HighScoreLine>{

	/** Maximum score values which should be stored in this score table */
	private int maximumElements = Integer.MAX_VALUE;
	List<HighScoreLine> highScores = new ArrayList<HighScoreLine>();
	public static char VALUE_SEPERATOR = '#';
	public Cryptable cryptor;

	public HighScoreTable() {

	}

	public void addScore(HighScoreLine highScore) {
		highScores.add(highScore);
		Collections.sort(highScores);
		setMaximumElements(maximumElements);
	}

	public HighScoreTable(String highScoreTableInOneLine) {
		if (highScoreTableInOneLine == null || highScoreTableInOneLine.isEmpty()) {
			throw new IllegalArgumentException(
					"HighScoreTableInOneLine is not formatted correctly");
		}
		loadScoresFromString(highScoreTableInOneLine);
	}

	public void setCryptor(Cryptable cryptor) {
		this.cryptor = cryptor;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < highScores.size(); i++) {
			string.append(highScores.get(i));
			if (i < highScores.size() - 1) {
				string.append(VALUE_SEPERATOR);
			}
		}
		return string.toString();
	}

	public void removeHighScoreLinesWithoutName() {
		List<HighScoreLine> scoresWithoutName = new ArrayList<HighScoreLine>();
		for (HighScoreLine score : highScores) {
			if (score.getName() == null || score.getName().isEmpty()) {
				scoresWithoutName.add(score);
			}
		}
		highScores.removeAll(scoresWithoutName);
	}

	public void setMaximumElements(int maximumElements) {
		this.maximumElements = maximumElements;
		if (highScores.size() > maximumElements) {
			highScores = highScores.subList(0, maximumElements);
		}
	}

	public int size() {
		return highScores.size();
	}

	public int getMaximumElements() {
		return maximumElements;
	}

	public void clear() {
		highScores.clear();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof HighScoreTable) {
			return highScores.equals(highScores);
		} else {
			return false;
		}
	}

	public HighScoreLine getScore(int i) {
		return highScores.get(i);
	}

	public void load() {
		String highScoreTableInOneLine = MyOptions.getString(Args.highScore);
		if (cryptor != null) {
			highScoreTableInOneLine = cryptor.enCrypt(highScoreTableInOneLine);
		}
		try {
			if (highScoreTableInOneLine != null && !highScoreTableInOneLine.isEmpty()) {
				loadScoresFromString(highScoreTableInOneLine);
			} else {
				clear();
			}
		} catch (IndexOutOfBoundsException e) {
			clear();
		} catch (IllegalArgumentException e) {
			clear();
		}
	}

	public void save() {
		String highScoreTableInOneLine = toString();
		if (cryptor != null) {
			highScoreTableInOneLine = cryptor.enCrypt(highScoreTableInOneLine);
		}
		MyOptions.setString(Args.highScore, highScoreTableInOneLine);
	}

	private void loadScoresFromString(String highScoreTableInOneLine) {
		clear();
		String[] scores = highScoreTableInOneLine.split(VALUE_SEPERATOR + "");
		for (String score : scores) {
			addScore(new HighScoreLine(score));
		}
	}

	@Override
	public Iterator<HighScoreLine> iterator() {
		return highScores.iterator();
	}

	@Override
	public int getColumnCount() {
		return HighScoreLine.VALUE_COUNT;
	}

	@Override
	public int getRowCount() {
		return highScores.size();
	}
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Name";
		case 1:
			return "Score";
		case 2:
			return "Balls Released";
		case 3:
			return "Balls Disbanded";
		case 4:
			return "Time";
		case 5:
			return "Date";
		default:
			return null;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		HighScoreLine line = highScores.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return line.getName();
		case 1:
			return line.getScore();
		case 2:
			return line.getDispandedBalls();
		case 3:
			return line.getTime();
		case 4:
			return line.getDate();
		default:
			return null;
		}
	}

}
