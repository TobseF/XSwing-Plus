/*
 * @version 0.0 16.11.2008
 * @author Tobse F
 */
package tools;

import java.awt.Dimension;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import lib.mylib.highscore.HighScoreFormatter;
import lib.mylib.swing.SwingUtils;
import org.newdawn.slick.*;
import xswing.HighScore;
import xswing.start.XSwing;

public class HighScoreReader extends JFrame implements ActionListener {

	private String HighScoreFile = XSwing.class.getSimpleName() + "_high_score.hscr";
	private SavedState localFile;
	private HighScoreFormatter scoreFormatter;
	private String[][] highScoreTable;
	private JButton save, load, clear, add;
	private JTable table;
	private JScrollPane pane;
	private JCheckBox sorted;
	/** Score is stored in one line of the property file. This it's Key. */
	private static final String SCORE_LABEL = "score";

	public HighScoreReader() {
		super("HighScoreReader");
		setSize(300, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(300, 300));
		SwingUtils.setCoolLookAndFeel();
		try {
			localFile = new SavedState(HighScoreFile);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		initButtons();
		scoreFormatter = new HighScoreFormatter();
		readScore();
		initTable();
		setVisible(true);
	}

	private void initButtons() {
		load = new JButton("load");
		save = new JButton("save");
		clear = new JButton("clear");
		add = new JButton("add");
		sorted = new JCheckBox();
		sorted.setToolTipText("Sort Score on loading");
		load.addActionListener(this);
		save.addActionListener(this);
		clear.addActionListener(this);
		add.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(load);
		panel.add(save);
		panel.add(clear);
		panel.add(add);
		panel.add(sorted);
		add(panel, "North");
	}

	private void initTable() {
		String[] columnNames = { "Score", "Name" };
		if (table != null) {
			remove(pane);
			remove(table);
			table = null;
		}
		table = new JTable(highScoreTable, columnNames);
		this.add(pane = new JScrollPane(table));
		validate();
		repaint();
	}

	private void saveScore() {
		int tabelRows = table.getRowCount() ;
		if(tabelRows != 0){
		String[][] sortedScore = new String[tabelRows][2];
		for (int i = 0; i < table.getRowCount(); i++) {
			sortedScore[i][0] = (String) table.getValueAt(i, 0);
			sortedScore[i][1] = (String) table.getValueAt(i, 1);
		}
		System.out.println("Data read from table");
		scoreFormatter.printTable(highScoreTable);
		String[][] cryptedHighScoreTable = scoreFormatter.cryptScore(highScoreTable);
		System.out.println("crypted table");
		scoreFormatter.printTable(cryptedHighScoreTable);
		localFile.setString(HighScore.HIGH_SCORE_KEY, scoreFormatter
				.shrincScoreInOneLine(cryptedHighScoreTable));
		}else{
			localFile.setString(HighScore.HIGH_SCORE_KEY, "");
		}
		try {
			localFile.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readScore() {
		String scoreInOneLine = localFile.getString(SCORE_LABEL);
		System.out.println("score in one line: " + scoreInOneLine);
		if (scoreInOneLine != null && !scoreInOneLine.isEmpty()) {
			highScoreTable = scoreFormatter.decryptScore(scoreFormatter
					.deShrincHighScoreFromOneLine(localFile.getString(SCORE_LABEL)));
		} else {
			highScoreTable = new String[0][0];
		}
		if (sorted.isSelected()) {
			highScoreTable = scoreFormatter.sortScore(highScoreTable);
		}
	}

	public static void main(String[] args) {
		new HighScoreReader();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(save)) {
			System.out.println("save");
			saveScore();
		}
		if (e.getSource().equals(load)) {
			System.out.println("load");
			readScore();
			initTable();
		}
		if (e.getSource().equals(clear)) {
			System.out.println("clar");
			highScoreTable = new String[0][2];
			initTable();
		}
		if (e.getSource().equals(add)) {
			System.out.println("add");
			addNewLine();
		}
	}

	private void addNewLine() {
		List<String[]> newScoreTable = new ArrayList<String[]>();
		for (String[] line : highScoreTable) {
			newScoreTable.add(line);
		}
		newScoreTable.add(new String[] { "0", "--" });
		highScoreTable = newScoreTable.toArray(new String[][] {});
		scoreFormatter.printTable(highScoreTable);

		initTable();
	}

}