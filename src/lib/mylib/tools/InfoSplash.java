/*
 * @version 0.0 21.02.2009
 * @author Tobse F
 */
package lib.mylib.tools;

import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import javax.swing.*;
import lib.mylib.swing.SwingUtils;

public class InfoSplash extends JFrame {

	private String info = "     Tobses lib -an free and \n" + "OpenSource extension to Slick",
			website = "http://www.tobsefritz.de", title = "MyLib Info";

	public InfoSplash() {
		setTitle(title);
		setResizable(false);
		setSize(200, 120);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		SwingUtils.setCoolLookAndFeel();
		SwingUtils.setLocationToCenter(this);
		JTextArea text = new JTextArea(info);
		text.setEditable(false);
		add(text);
		JButton moreInfos = new JButton("More Infos");
		moreInfos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(website));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		add(moreInfos);
		setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new InfoSplash();
	}

}