/*
 * @version 0.0 02.05.2009
 * @author Tobse F
 */
package lib.mylib.tools;

import lib.mylib.options.Paths;
import lib.mylib.swing.SwingUtils;
import lib.mylib.util.LanguageSelector;
import lib.mylib.util.MyLogSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

/**
 * Displays a Bug warning and gives the user the ability to easily send a bug report. Pressing
 * the submit button executes the request action in the {@link #submitRequest}.
 *
 * @author Tobse
 */
public class ErrorReporter extends JFrame implements ActionListener {

    private final String errorMessage;
    private final String stackTrace;
    private final String logFile;
    private final String systemInfo;
    private JTextArea errorMessageField, stackTraceField, logFileField, systemInfoField, userInput;
    private JButton submit;
    private JCheckBox includeSystemInfo;
    /**
     * Executed on a submit -generic server request
     */
    private final SubmitRequest submitRequest;
    /**
     * New Line Separator
     */
    private static final String NEW_LINE = "\n";
    /**
     * If the ErrorReporter should exit the VM after submitting the error.
     */
    private final boolean exitSystemOnSubmit;

    /**
     * @param e                  Error which should be reported
     * @param submitRequest      Listener which should receive the error report
     * @param exitSystemOnSubmit if the ErrorReporter should exit the VM after submitting the error
     */
    public ErrorReporter(Throwable e, SubmitRequest submitRequest, boolean exitSystemOnSubmit) {
        this.submitRequest = submitRequest;
        this.exitSystemOnSubmit = exitSystemOnSubmit;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(stream);
        e.printStackTrace(writer);
        writer.flush();
        errorMessage = e.getMessage();
        stackTrace = stream.toString();
        logFile = MyLogSystem.getLog();
        systemInfo = getSystemInformation();
        initComponents();
    }

    public ErrorReporter(Throwable e, SubmitRequest submitRequest) {
        this(e, submitRequest, true);
    }

    public ErrorReporter(String errorMessage, SubmitRequest submitRequest, boolean exitSystemOnSubmit) {
        this(new Throwable(errorMessage), submitRequest, exitSystemOnSubmit);
    }

    private void initComponents() {
        LanguageSelector.setSystemLanguage();
        setSize(800, 600);
        int width = 780;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(LanguageSelector.getString("bugreporter"));
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        SwingUtils.setCoolLookAndFeel();
        SwingUtils.setIcons(this, Paths.RES_DIR_LIB, "dialog-warning");
        add(new JLabel(LanguageSelector.getString("error") + ":"));
        errorMessageField = new JTextArea(errorMessage);
        errorMessageField.setEditable(false);
        JScrollPane errorMessagePane = new JScrollPane(errorMessageField);
        errorMessagePane.setPreferredSize(new Dimension(width, 35));
        add(errorMessagePane);

        add(new JLabel("StackTrace:"));
        stackTraceField = new JTextArea(stackTrace);
        stackTraceField.setEditable(false);
        JScrollPane stackTracePane = new JScrollPane(stackTraceField);
        stackTracePane.setPreferredSize(new Dimension(width, 150));
        add(stackTracePane);

        FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
        flow.setHgap(0);
        flow.setVgap(0);

        JPanel panel1 = new JPanel(flow);
        panel1.setOpaque(false);
        panel1.add(new JLabel("Logfile:"));
        logFileField = new JTextArea(logFile);
        logFileField.setEditable(false);
        JScrollPane logFileFieldPane = new JScrollPane(logFileField);
        logFileFieldPane.setPreferredSize(new Dimension(width / 2 - 4, 150));
        panel1.setPreferredSize(new Dimension(width / 2 - 3, 170));
        panel1.add(logFileFieldPane);
        add(panel1);

        JPanel panel2 = new JPanel(flow);
        panel2.add(new JLabel(LanguageSelector.getString("system_information") + ":"));
        systemInfoField = new JTextArea(systemInfo);
        systemInfoField.setEditable(false);
        JScrollPane userInputPane = new JScrollPane(systemInfoField);
        userInputPane.setPreferredSize(new Dimension(width / 2 - 4, 150));
        panel2.setPreferredSize(new Dimension(width / 2 - 3, 170));
        panel2.setOpaque(false);
        panel2.add(userInputPane);
        add(panel2);

        add(new JLabel(LanguageSelector.getString("your_comment") + ":"));
        userInput = new JTextArea(LanguageSelector.getString("your_text"));
        JScrollPane systemInfoFieldPane = new JScrollPane(userInput);
        systemInfoFieldPane.setPreferredSize(new Dimension(width, 85));
        add(systemInfoFieldPane);

        submit = new JButton(LanguageSelector.getString("submit_bug_report"));
        submit.addActionListener(this);
        add(submit);

        includeSystemInfo = new JCheckBox(LanguageSelector.getString("include_system_information"));
        includeSystemInfo.setSelected(true);
        includeSystemInfo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                systemInfoField.setEnabled(includeSystemInfo.isSelected());
            }
        });
        add(includeSystemInfo);
        setVisible(true);
        userInput.setSelectionStart(0);
        userInput.setSelectionEnd(userInput.getText().length());
        userInput.requestFocus();
        JOptionPane.showMessageDialog(this, LanguageSelector.getString("program_error_occured"));

    }

    private String getSystemInformation() {
        String systemInfos = "java runtime: " + System.getProperty("java.vendor") + NEW_LINE +
                "java version: " + System.getProperty("java.runtime.name") + " "
                + System.getProperty("java.version") + NEW_LINE +
                "os name: " + System.getProperty("os.name") + " " + System.getProperty("sun.os.patch.level")
                + NEW_LINE +
                "language: " + System.getProperty("user.language") + " "
                + System.getProperty("user.country");
        return systemInfos;
    }

    public static void main(String[] args) {
        new ErrorReporter("Test Bug", new ServerRequest(""), true);
    }

    public String getReport() {
        StringBuilder bugReport = new StringBuilder();
        bugReport.append("Error: ");
        bugReport.append(errorMessage + NEW_LINE);
        bugReport.append("StackTrace:\n");
        bugReport.append(stackTrace + NEW_LINE);
        bugReport.append("Log File: " + NEW_LINE);
        bugReport.append(logFile + NEW_LINE);
        bugReport.append("User input: " + NEW_LINE);
        String userText = userInput.getText();
        if (userText.length() > 1024 * 4) {
            userText = userText.substring(0, 1024 * 4);
        }
        bugReport.append(userText);
        bugReport.append(NEW_LINE);
        if (includeSystemInfo.isSelected()) {
            bugReport.append("System Info: " + NEW_LINE);
            bugReport.append(systemInfo);
        }
        return bugReport.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(submit) && submitRequest != null) {
            submitRequest.request(getReport());
            JOptionPane.showMessageDialog(this, LanguageSelector.getString("submitted_error_thanks"), LanguageSelector
                    .getString("submitted_error_window"), JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            if (exitSystemOnSubmit) {
                System.exit(NORMAL);
            }
        }
    }

}