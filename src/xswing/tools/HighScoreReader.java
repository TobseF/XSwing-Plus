/*
 * @version 0.0 16.11.2008
 * @author 	Tobse F
 */
package xswing.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import lib.mylib.HighScoreFormatter;

import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;

public class HighScoreReader extends JFrame implements ActionListener{
	String HighScoreFile="properties.txt";
	private SavedState localFile;
	private HighScoreFormatter scoreFormatter;
	private String[][] highScoreTable;
	JButton save, load, clear;
	JTable table;
	
	public HighScoreReader() {
		super("HighScoreReader");
		setSize(300, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		try {
			localFile=new SavedState(HighScoreFile);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		scoreFormatter=new HighScoreFormatter();
		readScore();
		initTable();
		
		load=new JButton("load");
		save=new JButton("save");
		clear=new JButton("clear");
		load.addActionListener(this);
		save.addActionListener(this);
		clear.addActionListener(this);
		JPanel panel=new JPanel();
		panel.add(load);
		panel.add(save);
		panel.add(clear);
		add(panel,"North");
		setVisible(true);
	}
	JScrollPane pane;
	
	private void initTable(){
		String[] columnNames =  {"Name", "Score"};
		table = new JTable(highScoreTable,columnNames);
		this.add( pane=new JScrollPane( table ) );
		validate();
		repaint();
		//this.add(table);
	}
	
	private void saveScore(){
		String[][] sortedScore=new String[table.getRowCount()][2];
		for(int i=0;i<table.getRowCount();i++){
			sortedScore[i][0]=(String) table.getValueAt(i,0);
			sortedScore[i][1]=(String) table.getValueAt(i,1);
		}
		System.out.println("Data read from table");
		scoreFormatter.printTable(highScoreTable);
		String[][] cryptedHighScoreTable=scoreFormatter.cryptScore(highScoreTable);
		System.out.println("crypted table");
		scoreFormatter.printTable(cryptedHighScoreTable);
		localFile.setString("score", scoreFormatter.shrincScoreInOneLine(cryptedHighScoreTable));
		try {
			localFile.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readScore(){
		String scoreInOneLine=localFile.getString("score");
		if(scoreInOneLine!=null&&!scoreInOneLine.isEmpty())
			highScoreTable=scoreFormatter.decryptScore(scoreFormatter.deShrincHighScoreFromOneLine(localFile.getString("score")));
		else
			highScoreTable=new String[0][0];
		System.out.println("read score?: "+scoreFormatter.printTable(highScoreTable));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new HighScoreReader();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(save)){
			System.out.println("save");
			saveScore();
		}
		if(e.getSource().equals(load)){
			System.out.println("load");
			removeTable();
			readScore();
			initTable();
		}	
		if(e.getSource().equals(clear)){
			System.out.println("clar");
			removeTable();
			initTable();
		}
	}
	
	private void removeTable(){
		highScoreTable=new String[0][0];
		String[] columnNames =  {"Name", "Score"};
		remove(pane);
		remove(table);
		table=null;
		table = new JTable(highScoreTable,columnNames);
	}

}
