/*
 * @version 0.0 22.09.2009
 * @author Tobse F
 */
package tools;

import lib.mylib.ident.Identable;
import lib.mylib.ident.TimeIdent;
import lib.mylib.options.DefaultArgs.Args;
import lib.mylib.swing.SwingUtils;
import lib.mylib.util.Clock;
import lib.mylib.util.MyOptions;
import lib.mylib.util.MyPropertys;
import org.newdawn.slick.util.Log;
import xswing.start.XSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsReader extends JFrame implements ActionListener {

    private JTextField playedGames;
    private JTextField canceldGames;
    private JTextField timePlayedAllGames;
    private JTextField ident;
    private JButton read, clear;
    private final Identable identer;

    public StatisticsReader() {
        setSize(240, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        SwingUtils.setCoolLookAndFeel();
        MyPropertys.setFile(XSwing.class);
        identer = new TimeIdent();
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // setLayout(new FlowLayout());
        setLayout(new GridLayout(5, 2));
        add(new JLabel("Played Games:"));
        playedGames = new JTextField(10);
        add(playedGames);
        add(new JLabel("Canceled Games:"));
        canceldGames = new JTextField(10);
        add(canceldGames);
        add(new JLabel("Total Time:"));
        timePlayedAllGames = new JTextField(10);
        add(timePlayedAllGames);

        add(new JLabel("Identity:"));
        ident = new JTextField(8);
        add(ident);

        read = new JButton("Read");
        read.addActionListener(this);
        add(read);
        clear = new JButton("Claer");
        clear.addActionListener(this);
        add(clear);
        read();
    }

    public void read() {
        MyOptions.load();
        playedGames.setText(MyOptions.getNumber(Args.playedGames) + "");
        canceldGames.setText(MyOptions.getNumber(Args.canceledGames) + "");
        long totalTime = (long) MyOptions.getNumber(Args.totalTime);
        timePlayedAllGames.setText(Clock.getFormattedTimeAsString(totalTime / 1000));
        ident.setText(identer.getIdentity());
    }

    public void clear() {
        Log.info("Cleared Statistic");
        MyOptions.setNumber(Args.playedGames, 0);
        MyOptions.setNumber(Args.canceledGames, 0);
        MyOptions.setNumber(Args.totalTime, 0);
        identer.newIdentity();
    }

    public static void main(String[] args) {
        new StatisticsReader();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(read)) {
            read();
        } else if (e.getSource().equals(clear)) {
            clear();
        }
    }

}
