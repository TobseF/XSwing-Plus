/*
 * @version 0.0 09.12.2008
 * @author Tobse F
 */
package tests;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.*;
import de.lessvoid.nifty.slick.NiftyGameState;

public class NiftyXMLTester extends StateBasedGame implements ScreenController {

	public NiftyXMLTester() {
		super("NiftyXMLTester");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer game = new AppGameContainer(new NiftyXMLTester());
			game.setMinimumLogicUpdateInterval(26);
			game.setMaximumLogicUpdateInterval(26);
			game.setDisplayMode(1024, 768, false);
			game.setClearEachFrame(false);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		NiftyGameState niftyGameState = new NiftyGameState(0);

		JFileChooser fileChooser = new JFileChooser(new File("restest/gui/"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("xml", "xml"));
		int state = fileChooser.showOpenDialog(null);
		File inputFile = null;
		if (state == JFileChooser.APPROVE_OPTION) {
			inputFile = fileChooser.getSelectedFile();

		} else {
			try {
				throw new IOException("Datei-Auswahl abgebrochen");
			} catch (IOException e) {}
		}
		if (inputFile != null) {
			niftyGameState.fromXml(inputFile.getAbsolutePath(), this);
			addState(niftyGameState);
		}
	}

	public final void buttonPressed() {
		System.out.println("button pressed");
	}

	public void quit() {
		System.out.println("quit");
	}

	@Override
	public void bind(Nifty nifty, Screen screen) {}

	@Override
	public void onEndScreen() {}

	@Override
	public void onStartScreen() {}

}