/*
 * @version 0.0 16.11.2008
 * @author Tobse F
 */
package tools;

import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import lib.mylib.highscore.*;
import lib.mylib.swing.SwingUtils;
import lib.mylib.util.MyPropertys;
import org.newdawn.slick.util.Log;
import xswing.start.XSwing;

public class HighScoreReader extends JFrame implements ActionListener {

	private HighScoreTable highScoreTable;
	private JButton save, load, clear, add;
	private JTable table;
	private JScrollPane panel;
	private JCheckBox crypted;

	public HighScoreReader() {
		super("HighScoreReader");
		MyPropertys.setFile(XSwing.class);
		setSize(300, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(300, 300));
		SwingUtils.setCoolLookAndFeel();
		initButtons();
		highScoreTable = new HighScoreTable();
		if (crypted.isSelected()) {
			highScoreTable.setCryptor(new EasyCrypter());
		}
		highScoreTable.load();
		initTable();
		setVisible(true);
	}

	private void initButtons() {
		load = new JButton("load");
		save = new JButton("save");
		clear = new JButton("clear");
		add = new JButton("add");
		crypted = new JCheckBox();
		crypted.setToolTipText("Is HighScoreTable crypted");
		load.addActionListener(this);
		save.addActionListener(this);
		clear.addActionListener(this);
		add.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(load);
		panel.add(save);
		panel.add(clear);
		panel.add(add);
		panel.add(crypted);
		add(panel, "North");
	}

	private void initTable() {
		if (table != null) {
			remove(panel);
			remove(table);
			table = null;
		}
		table = new JTable(highScoreTable);
		table.setRowSorter(new TableRowSorter<TableModel>(highScoreTable));
		this.add(panel = new JScrollPane(table));
		validate();
		repaint();
	}

	public static void main(String[] args) {
		new HighScoreReader();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(save)) {
			System.out.println("save");
			save();
		}
		if (e.getSource().equals(load)) {
			System.out.println("load");
			highScoreTable.load();
			initTable();
		}
		if (e.getSource().equals(clear)) {
			System.out.println("clar");
			highScoreTable.clear();
			initTable();
		}
		if (e.getSource().equals(add)) {
			System.out.println("add");
			highScoreTable.addScore(new HighScoreLine(99999, "", 0, 0,0));
			initTable();
		}
	}

	public void save() {
		highScoreTable = new HighScoreTable();
		if (crypted.isSelected()) {
			highScoreTable.setCryptor(new EasyCrypter());
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			HighScoreLine scoreLine = new HighScoreLine(0, "");
			String score = (String) table.getValueAt(i, 0);
			String name = (String) table.getValueAt(i, 1);
			String time = (String) table.getValueAt(i, 2);
			if (score != null && !score.isEmpty() && name != null && !name.isEmpty() && time != null && !time.isEmpty()) {
				scoreLine.setScore(Integer.parseInt(score));
				scoreLine.setName(name);
				scoreLine.setTime(Long.parseLong(time));
				highScoreTable.addScore(scoreLine);
			} else {
				if (table.getRowCount() == 1 && score == null && name == null && time == null) {
					highScoreTable.clear();
					Log.info("Cleared and saved HighScore Table");
				} else {
					Log.warn("Could not save higscore because of wrong table data");
				}
			}
		}
		highScoreTable.save();
	}

}