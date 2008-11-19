/*
 * @version 0.0 16.11.2008
 * @author Tobse F
 */
package xswing.tools;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import lib.mylib.HighScoreFormatter;

import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;

public class HighScoreReader extends JFrame implements ActionListener {
	private String HighScoreFile = "properties.txt";
	private SavedState localFile;
	private HighScoreFormatter scoreFormatter;
	private String[][] highScoreTable;
	private JButton save, load, clear, add;
	private JTable table;
	private JScrollPane pane;

	public HighScoreReader() {
		super("HighScoreReader");
		setSize(300, 350);
		setLocationRelativeTo(null);
		//setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(300,300));
		
		try {  //Try to load system LookAndFeel
			  UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() ); 
		} catch( Exception e ) { e.printStackTrace(); }
			
		try {
			localFile = new SavedState(HighScoreFile);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		scoreFormatter = new HighScoreFormatter();
		readScore();
		initTable();

		load = new JButton("load");
		save = new JButton("save");
		clear = new JButton("clear");
		add = new JButton("add");
		load.addActionListener(this);
		save.addActionListener(this);
		clear.addActionListener(this);
		add.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(load);
		panel.add(save);
		panel.add(clear);
		panel.add(add);
		add(panel, "North");
		setVisible(true);
	}

	private void initTable() {
		String[] columnNames = { "Name", "Score" };
		if(table!=null){
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
		String[][] sortedScore = new String[table.getRowCount()][2];
		for (int i = 0; i < table.getRowCount(); i++) {
			sortedScore[i][0] = (String) table.getValueAt(i, 0);
			sortedScore[i][1] = (String) table.getValueAt(i, 1);
		}
		System.out.println("Data read from table");
		scoreFormatter.printTable(highScoreTable);
		String[][] cryptedHighScoreTable = scoreFormatter.cryptScore(highScoreTable);
		System.out.println("crypted table");
		scoreFormatter.printTable(cryptedHighScoreTable);
		localFile.setString("score", scoreFormatter
				.shrincScoreInOneLine(cryptedHighScoreTable));
		try {
			localFile.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readScore() {
		String scoreInOneLine = localFile.getString("score");
		if (scoreInOneLine != null && !scoreInOneLine.isEmpty()) {
			highScoreTable = scoreFormatter.decryptScore(scoreFormatter
					.deShrincHighScoreFromOneLine(localFile.getString("score")));
		} else {
			highScoreTable = new String[0][0];
		}
		System.out.println("read score?: " + scoreFormatter.printTable(highScoreTable));
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
			highScoreTable = new String[1][2];
			initTable();
		}
		if (e.getSource().equals(add)) {
			System.out.println("add");
			addNewLine();
		}
	}
	
	private void addNewLine(){
		List<String[]> newScoreTable = new ArrayList<String[]>();
		for(String[] line : highScoreTable){
			newScoreTable.add(line);
		} 
		newScoreTable.add(new String[]{"0","--"});
		highScoreTable = newScoreTable.toArray(new String[][]{});
		scoreFormatter.printTable(highScoreTable);

		initTable();
	}

}