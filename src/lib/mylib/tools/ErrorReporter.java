/*
 * @version 0.0 02.05.2009
 * @author Tobse F
 */
package lib.mylib.tools;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import lib.mylib.swing.SwingUtils;
import lib.mylib.util.*;

/**
 * Displays a Bug warning and gives the user the ability to easily send a bug report.
 * Pressing the submit button executes the request action in the {@link #submitRequest}.  
 * @author Tobse
 */
public class ErrorReporter extends JFrame implements ActionListener {

	private String errorMessage;
	private String stackTrace;
	private String logFile;
	private String systemInfo;
	private JTextArea errorMessageField, stackTraceField, 
					  logFileField, systemInfoField,userInput;
	private JButton submit;
	private JCheckBox includeSystemInfo;
	/** Executeted on a submit -genreic server request*/
	private final SubmitRequest submitRequest;
	/** New Line */
	private static String NL = "\n";

	public ErrorReporter(Throwable e, SubmitRequest submitRequest) {
		this.submitRequest = submitRequest;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(stream);
		e.printStackTrace(writer);
		writer.flush();
		errorMessage = e.getMessage();
		stackTrace = new String(stream.toByteArray());
		logFile = MyLogSystem.getLog();
		systemInfo = getSystemInformation();
		initComponents();
	}

	public ErrorReporter(String errorMessage, SubmitRequest submitRequest) {
		this.errorMessage = errorMessage;
		this.submitRequest = submitRequest;
		systemInfo = getSystemInformation();
		initComponents();
	}

	private void initComponents() {
		setSize(800, 600);
		int width = 780;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Bugreporter");
		setLocationRelativeTo(null);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		SwingUtils.setCoolLookAndFeel();
		SwingUtils.setIcons(this, "lib/mylib/res/", "dialog-warning");
		add(new JLabel("Error:"));
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

		panel2.add(new JLabel("System Information:"));
		systemInfoField = new JTextArea(systemInfo);
		systemInfoField.setEditable(false);
		JScrollPane userInputPane = new JScrollPane(systemInfoField);
		userInputPane.setPreferredSize(new Dimension(width / 2 - 4, 150));
		panel2.setPreferredSize(new Dimension(width / 2 - 3, 170));
		panel2.setOpaque(false);
		panel2.add(userInputPane);
		add(panel2);

		add(new JLabel("Your Comment:"));
		userInput = new JTextArea(
		"Yout Text ...eg. E-Mail and a problem description -optional");
		JScrollPane systemInfoFieldPane = new JScrollPane(userInput);
		systemInfoFieldPane.setPreferredSize(new Dimension(width, 85));
		add(systemInfoFieldPane);

		submit = new JButton("Submit Bug Report");
		submit.addActionListener(this);
		add(submit);

		includeSystemInfo = new JCheckBox("Include System Information");
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
		JOptionPane.showMessageDialog(this, "A programm error occured. "
				+ "Please send the report to help me making this app better");

	}

	private String getSystemInformation() {
		StringBuilder systemInfos = new StringBuilder();
		systemInfos.append("java runtime: " + System.getProperty("java.vendor") + NL);
		systemInfos.append("java version: " + System.getProperty("java.runtime.name") + " "
				+ System.getProperty("java.version") + NL);
		systemInfos.append("os name: " + System.getProperty("os.name") + " "
				+ System.getProperty("sun.os.patch.level") + NL);
		systemInfos.append("language: " + System.getProperty("user.language") + " "
				+ System.getProperty("user.country"));
		return systemInfos.toString();
	}

	public static void main(String[] args) {
		new ErrorReporter("Test Bug", new ServerRequest(""));
	}

	public String getReport(){
		StringBuilder bugReport = new StringBuilder();
		bugReport.append("Error: ");
		bugReport.append(errorMessage + NL);
		bugReport.append("StackTrace:\n");
		bugReport.append(stackTrace + NL);
		bugReport.append("Log File: " + NL);
		bugReport.append(logFile + NL);
		bugReport.append("User input: " + NL);
		String userText = userInput.getText();
		if (userText.length() > 1024 * 4) {
			userText = userText.substring(0, 1024 * 4);
		}
		bugReport.append(userText);
		bugReport.append(NL);
		if (includeSystemInfo.isSelected()) {
			bugReport.append("System Info: " + NL);
			bugReport.append(systemInfo);
		}
		return bugReport.toString();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(submit) && submitRequest != null) {
			submitRequest.request(getReport());
		}
	}
}