/*
 * @version 0.0 16.11.2008
 * @author Tobse F
 */
package tools;

import lib.mylib.highscore.EasyCrypter;
import lib.mylib.highscore.HighScoreLine;
import lib.mylib.highscore.HighScoreTable;
import lib.mylib.swing.SwingUtils;
import lib.mylib.util.MyPropertys;
import org.newdawn.slick.util.Log;
import xswing.start.XSwing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class HighScoreReader extends JFrame implements ActionListener {

    private HighScoreTable highScoreTable;
    private JButton save, load, clear, add;
    private JTable table;
    private HighScoreTableModel tableModel;
    private JScrollPane panel;
    private JCheckBox crypted;
    private boolean editMode = false;

    public HighScoreReader() {
        super("HighScoreReader");
        MyPropertys.setFile(XSwing.class);
        setSize(400, 400);
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
        tableModel = new HighScoreTableModel(highScoreTable, editMode);
        table = new JTable(tableModel);
        table.setRowSorter(new TableRowSorter<TableModel>(tableModel));
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
            System.out.println("clear");
            highScoreTable.clear();
            initTable();
        }
        if (e.getSource().equals(add)) {
            System.out.println("add");
            HighScoreLine highScore = new HighScoreLine(99999, "", 0, 0, 0);
            highScore.setDate(new Date());
            highScoreTable.addScore(highScore);
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

            String name = tableModel.getName(i);
            int score = tableModel.getScore(i);
            int dispandedBalls = tableModel.getDispandedBalls(i);
            long time = tableModel.getTime(i);
            Date date = tableModel.getDate(i);
            if (score > 0 && name != null && !name.isEmpty() && time > 0 && date != null && dispandedBalls > 0) {
                scoreLine.setScore(score);
                scoreLine.setName(name);
                scoreLine.setTime(time);
                scoreLine.setDate(date);
                scoreLine.setDispandedBalls(dispandedBalls);
                highScoreTable.addScore(scoreLine);
            } else {
                if (table.getRowCount() == 1 && score == 0 && name == null && time == 0) {
                    highScoreTable.clear();
                    Log.info("Cleared and saved HighScore Table");
                } else {
                    Log.warn("Could not save higscore because of wrong table data");
                }
            }
        }
        highScoreTable.save();
    }


    public static class HighScoreTableModel extends DefaultTableModel {
        private final HighScoreTable highScoreTable;
        private final boolean editMode;

        public HighScoreTableModel(HighScoreTable highScoreTable, boolean editMode) {
            this.highScoreTable = highScoreTable;
            this.editMode = editMode;
        }

        @Override
        public int getColumnCount() {
            return HighScoreLine.VALUE_COUNT;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return editMode;
        }

        @Override
        public int getRowCount() {
            if (highScoreTable == null) {
                return 0;
            }
            return highScoreTable.size();
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

        public String getName(int rowIndex) {
            return highScoreTable.get(rowIndex).getName();
        }

        public int getScore(int rowIndex) {
            return highScoreTable.get(rowIndex).getScore();
        }

        public int getDispandedBalls(int rowIndex) {
            return highScoreTable.get(rowIndex).getDispandedBalls();
        }

        public long getTime(int rowIndex) {
            return (int) highScoreTable.get(rowIndex).getTime();
        }

        public Date getDate(int rowIndex) {
            return highScoreTable.get(rowIndex).getDate();
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            HighScoreLine line = highScoreTable.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    line.setName((String) aValue);
                    break;
                case 1:
                    line.setScore(Integer.parseInt((String) aValue));
                    break;
                case 2:
                    line.setDispandedBalls(Integer.parseInt((String) aValue));
                    break;
                case 3:
                    line.setReleasedBalls(Integer.parseInt((String) aValue));
                    break;
                case 4:
                    line.setTime(Integer.parseInt((String) aValue));
                    break;
                case 5:
                    line.setDate(new Date(Date.parse((String) aValue)));
                    break;
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            HighScoreLine line = highScoreTable.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return line.getName();
                case 1:
                    return line.getScore();
                case 2:
                    return line.getDispandedBalls();
                case 3:
                    return line.getReleasedBalls();
                case 4:
                    return line.getTime();
                case 5:
                    return line.getDate();
                default:
                    return null;
            }
        }
    }
}