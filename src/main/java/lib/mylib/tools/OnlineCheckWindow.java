/*
 * @version 0.0 23.09.2009
 * @author Tobse F
 */
package lib.mylib.tools;

import lib.mylib.http.OnlineChecker;
import lib.mylib.options.Paths;
import lib.mylib.swing.SwingUtils;
import org.newdawn.slick.util.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class OnlineCheckWindow extends JFrame implements ActionListener {

    private JButton checkingConnection;
    private JButton retry, abort, further;
    private JProgressBar progressBar;
    private JLabel statusBar;
    private final int onlineChecks = 10;
    private final int timeBetweenChecks = 500;
    private boolean doCheckingConnection = true;
    private boolean isOnline = false;
    public static final String GOT_ONLINE = "got_online";
    public static final String CLOSED_ONLINE_CHECKER = "closed_online_checker";

    /**
     * listner which will be informed about a succesful online connection (command
     * {@link #GOT_ONLINE}) or an abort action| window closed (command
     * {@link #CLOSED_ONLINE_CHECKER})
     */
    private ActionListener actionListener;

    public OnlineCheckWindow() {
        SwingUtils.setCoolLookAndFeel();
        setTitle("Online Checker");
        setSize(290, 190);
        setAlwaysOnTop(true);
        SwingUtils.setLocationToCenter(this);
        SwingUtils.setIcons(this, Paths.RES_DIR_LIB, "internet-checker");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                if (actionListener != null) {
                    actionListener.actionPerformed(new ActionEvent(this, 13,
                            "CLOSED_ONLINE_CHECKER"));
                }
            }
        });
        initComponents();

        setVisible(true);
        startOnlineChecking();
    }

    private void initComponents() {
        JPanel top = new JPanel(new FlowLayout());
        top.setOpaque(false);
        checkingConnection = new JButton();
        setIcon(checkingConnection, "internet-find.png");
        checkingConnection.setFocusable(false);
        checkingConnection.setRequestFocusEnabled(false);
        checkingConnection.setRolloverEnabled(false);
        top.add(checkingConnection);
        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(180, 45));
        progressBar.setEnabled(true);
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(false);
        top.add(progressBar);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        retry = new JButton("Retry");
        retry.setEnabled(false);
        retry.addActionListener(this);
        center.add(retry);

        abort = new JButton("Abort");
        abort.addActionListener(this);
        center.add(abort);

        further = new JButton("Further");
        further.addActionListener(this);
        further.setEnabled(false);
        center.add(further);

        add(center, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel();
        statusBar = new JLabel("Status: checking connection");
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        statusPanel.add(statusBar);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void startOnlineChecking() {
        statusBar.setText("Status: Trying to get Online");

        doCheckingConnection = true;
        retry.setEnabled(false);
        abort.setEnabled(true);
        progressBar.setIndeterminate(true);

        progressBar.setStringPainted(false);
        new Thread() {

            @Override
            public void run() {
                for (int i = 0; i < onlineChecks && doCheckingConnection && isVisible(); i++) {
                    try {
                        Thread.sleep(timeBetweenChecks);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (OnlineChecker.isOnline()) {
                        connectionOK();
                    }
                }
                if (doCheckingConnection) {
                    connectionFailed();
                }
            }
        }.start();
    }

    private void stopOnlineChecking() {
        doCheckingConnection = false;
        retry.setEnabled(true);
        progressBar.setIndeterminate(false);
        progressBar.setStringPainted(false);
        progressBar.setValue(0);
        retry.setEnabled(true);
        abort.setEnabled(false);
        statusBar.setText("Status: Online Cheking Stopped");
    }

    private void connectionFailed() {
        stopOnlineChecking();
        setIcon(checkingConnection, "internet-error.png");
        statusBar.setText("Status: Connection Failed!");
    }

    private void connectionOK() {
        doCheckingConnection = false;
        isOnline = true;
        setIcon(checkingConnection, "internet-ok.png");
        progressBar.setIndeterminate(false);
        progressBar.setValue(100);
        progressBar.setStringPainted(true);
        further.setEnabled(true);
        statusBar.setText("Status: Connected");
        abort.setEnabled(false);
    }

    private void setIcon(JButton button, String iconFileName) {
        button.setIcon(new ImageIcon(ResourceLoader.getResource("lib/mylib/res/"
                + iconFileName)));
    }

    public static void main(String[] args) {
        new OnlineCheckWindow();
    }

    public boolean isOnline() {
        return isOnline;
    }

    /**
     * Adds the listner which will be informed about a succesful online connection (command
     * {@link #GOT_ONLINE}) or an abort action| window closed (command
     * {@link #CLOSED_ONLINE_CHECKER})
     *
     * @param actionListener with the coomands: {@link #GOT_ONLINE} or
     *                       {@link #CLOSED_ONLINE_CHECKER}
     */
    public void addActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(retry)) {
            startOnlineChecking();
        } else if (e.getSource().equals(abort)) {
            stopOnlineChecking();
        } else if (e.getSource().equals(further)) {
            setVisible(false);
            if (actionListener != null) {
                actionListener.actionPerformed(new ActionEvent(this, 13, GOT_ONLINE));
            }
        }

    }

}
