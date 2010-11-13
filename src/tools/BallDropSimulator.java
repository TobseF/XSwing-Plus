/*
 * @version 0.0 31.01.2009
 * @author Tobse F
 */
package tools;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import lib.mylib.swing.SwingUtils;
import xswing.*;
import xswing.ai.Simulator;
import xswing.ball.*;

public class BallDropSimulator extends JFrame implements ActionListener {

	private JRadioButton[] radioButtons = new JRadioButton[8];
	private JRadioButton clearNr;
	private JButton[][] ballTableView = new JButton[8][13];
	private JButton[] propButtons = new JButton[8];
	private BallTable ballTable;
	private JButton simStep, simAll, clear, dropOny;
	private int ballNr = 0;
	private Simulator simulator;

	public BallDropSimulator() {
		setSize(500, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("BallDropSimulator");
		SwingUtils.setCoolLookAndFeel();
		setLayout(new FlowLayout());
		SwingUtils.setLocationToCenter(this);
		JPanel ballTypeChooser = new JPanel();
		ballTypeChooser.setOpaque(false);
		ButtonGroup ballTypeChooserGroup = new ButtonGroup();
		clearNr = new JRadioButton("[]");
		clearNr.addActionListener(this);
		ballTypeChooserGroup.add(clearNr);
		ballTypeChooser.add(clearNr);
		for (int i = 0; i < 8; i++) {
			radioButtons[i] = new JRadioButton("" + i);
			radioButtons[i].addActionListener(this);
			ballTypeChooser.add(radioButtons[i]);
			ballTypeChooserGroup.add(radioButtons[i]);
		}
		radioButtons[0].setSelected(true);
		add(ballTypeChooser);

		JPanel ballPanel = new JPanel(new GridLayout(14, 8));
		for (int i = 0; i < propButtons.length; i++) {
			propButtons[i] = new JButton("_");
			propButtons[i].addActionListener(this);
			ballPanel.add(propButtons[i]);
		}

		for (int y = 12; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				ballTableView[x][y] = new JButton("");
				ballTableView[x][y].addActionListener(this);
				ballTableView[x][y].setName(x + "" + y);
				if (y >= 9 && y <= 10) {
					ballTableView[x][y].setEnabled(false);
				}
				ballPanel.add(ballTableView[x][y]);
			}
		}
		JPanel controlls = new JPanel();
		controlls.setOpaque(false);
		simStep = new JButton("Sim. Step");
		simStep.addActionListener(this);
		controlls.add(simStep);
		simAll = new JButton("Sim. All");
		simAll.addActionListener(this);
		controlls.add(simAll);
		dropOny = new JButton("Drop Only");
		dropOny.addActionListener(this);
		controlls.add(dropOny);
		clear = new JButton("Clear");
		clear.addActionListener(this);
		controlls.add(clear);
		add(controlls);
		add(ballPanel);
		validate();

		ballTable = new BallTable();
		simulator = new Simulator();
		setVisible(true);
	}

	private void dropBall(int nr, int pos) {
		// ballTable = simulator.simulate(ballTable, new Ball(nr), pos);
		ballTable = simulator.dropBall(ballTable, new Ball(nr), pos);
		refreshBallTableView();
	}

	public void setBallTable(BallTable ballTable) {
		this.ballTable = ballTable;
		refreshBallTableView();
	}

	public static void main(String[] args) {
		new BallDropSimulator();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(dropOny)) {
			ballTable = simulator.dropAllBalls(ballTable);
			refreshBallTableView();
		} else if (e.getSource() instanceof JButton
				&& ((JButton) e.getSource()).getText().equals("_")) {
			for (int i = 0; i < 8; i++) {
				if (((JButton) e.getSource()).equals(propButtons[i])) {
					dropBall(ballNr, i);
				}
			}
		} else if (e.getSource().equals(clear)) {
			claerTable();
		} else if (e.getSource().equals(simAll)) {
			ballTable = simulator.simulateAll(ballTable);
			refreshBallTableView();
		} else if (e.getSource().equals(simStep)) {
			ballTable = simulator.simulate(ballTable, null, 0);
			refreshBallTableView();
		} else if (e.getSource() instanceof JButton) {
			String xy = ((JButton) e.getSource()).getName();
			int x = Integer.parseInt(String.valueOf(xy.substring(0, 1)));
			int y;
			if (xy.length() >= 3) {
				y = Integer.parseInt(String.valueOf(xy.substring(1, 3)));
			} else {
				y = Integer.parseInt(String.valueOf(xy.substring(1, 2)));
			}
			// System.out.println(x + " " + y);
			setBall(ballNr, x, y);
		} else if (e.getSource().equals(clearNr)) {
			ballNr = -1;
		} else if (e.getSource() instanceof JRadioButton) {
			ballNr = Integer.parseInt(((JRadioButton) e.getSource()).getText());
		}
	}

	private void claerTable() {
		for (int y = 12; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				ballTable.setBall(x, y, null);
			}
		}
		refreshBallTableView();
	}

	private void setBall(int nr, int x, int y) {
		if (ballNr == -1) {
			ballTable.setBall(x, y, null);
		} else {
			ballTable.setBall(x, y, new Ball(nr));
		}
		refreshBallTableView();
	}

	private void refreshBallTableView() {
		Ball[][] balls = ballTable.getBalls();
		for (int y = 12; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				if (balls[x][y] != null) {
					ballTableView[x][y].setText("" + balls[x][y].getNr());
				} else {
					ballTableView[x][y].setText(" ");
				}
			}
		}
	}

}