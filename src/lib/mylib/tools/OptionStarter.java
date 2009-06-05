/*
 * @version 0.0 19.02.2009
 * @author Tobse F
 */
package lib.mylib.tools;

import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.*;
import lib.mylib.swing.SwingUtils;
import lib.mylib.util.*;
import org.newdawn.slick.*;
import org.newdawn.slick.util.ResourceLoader;

public class OptionStarter extends JFrame implements ActionListener {

	private boolean showOptionPanelOnStart, isGermanSetAsDefaultlanguage,
			startGameInFullscreen, checkForUpdates = true;
	private JRadioButton selectGerman, selectEnglish, selectFullscreen, selectWindow;
	private ButtonGroup languageButtons, windoSizeButtons;
	private JButton startGame, exitOptions;
	private JComboBox resolution;
	private JPanel languageSelectPanel, windoSizePanel;
	private JCheckBox showOptionsOnStart, checkForUpdatesOnStart;
	private Class<?> gameToStart;
	private String[] resolutions = new String[] { "1024 x 768" };
	private SavedState savedState;
	public static boolean haveToshowOptionStarter;
	private String[] args = new String[] { "" };

	public OptionStarter() {
		this(InfoSplash.class, null);
	}

	public OptionStarter(Class<?> gameToStart, String[] args) {
		if (args != null) {
			this.args = args;
			evaluateArgs(args);
		}

		setGameToStart(gameToStart);
		if (haveToshowOptionStarter || showOptionPanelOnStart) {
			initCompoments();
			setVisible(true);
		} else {
			startGame();
		}
	}

	public OptionStarter(Class<?> gameToStart) {
		this(gameToStart, null);
	}

	public void setGameToStart(Class<?> gameToStart) {
		this.gameToStart = gameToStart;
		setSavedStateFile(gameToStart.getSimpleName());
		/*
		 * if(gameToStart!=null){ startGame.setEnabled(true); startGame.setSelected(true);
		 * startGame.requestFocusInWindow(); }
		 */
	}

	private void initCompoments() {
		setSize(300, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		SwingUtils.setCoolLookAndFeel();
		SwingUtils.setIcons(this, "lib/mylib/res/", "preferences-system");
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());

		selectEnglish = new JRadioButton();
		selectEnglish.addActionListener(this);
		setIcon(selectEnglish, "res/flag_english.png");
		selectGerman = new JRadioButton();
		selectGerman.addActionListener(this);
		setIcon(selectGerman, "res/flag_german.png");

		selectFullscreen = new JRadioButton();
		setIcon(selectFullscreen, "res/option_fullscreen.png");
		selectFullscreen.addActionListener(this);
		selectFullscreen.setSelected(true);
		selectWindow = new JRadioButton();
		setIcon(selectWindow, "res/option_window.png");
		selectWindow.addActionListener(this);

		selectFullscreen.setSelected(startGameInFullscreen);
		selectWindow.setSelected(!startGameInFullscreen);

		languageButtons = new ButtonGroup();
		languageButtons.add(selectEnglish);
		languageButtons.add(selectGerman);

		languageSelectPanel = new JPanel(new FlowLayout());

		languageSelectPanel.setOpaque(false);
		add(languageSelectPanel);
		languageSelectPanel.add(selectEnglish);
		languageSelectPanel.add(selectGerman);

		windoSizeButtons = new ButtonGroup();
		windoSizeButtons.add(selectFullscreen);
		windoSizeButtons.add(selectWindow);

		windoSizePanel = new JPanel(new FlowLayout());
		windoSizePanel.setOpaque(false);
		add(windoSizePanel);
		windoSizePanel.add(selectFullscreen);
		windoSizePanel.add(selectWindow);

		resolution = new JComboBox(resolutions);
		resolution.setPreferredSize(new Dimension(200, 34));
		add(resolution);

		showOptionsOnStart = new JCheckBox();
		showOptionsOnStart.setSelected(showOptionPanelOnStart);
		showOptionsOnStart.addActionListener(this);
		add(showOptionsOnStart);

		selectLanguage();
		initUpdateCheck();

		startGame = new JButton();
		// startGame.setEnabled(false);
		startGame.addActionListener(this);
		exitOptions = new JButton();
		exitOptions.addActionListener(this);
		JPanel runButtons = new JPanel(new FlowLayout());
		runButtons.setOpaque(false);
		add(runButtons);
		runButtons.add(startGame);
		runButtons.add(exitOptions);
		setStrings();
		SwingUtils.addGlobalKeyListener(new AWTEventListener() {

			@Override
			public void eventDispatched(AWTEvent e) {
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					if (((KeyEvent) e).getKeyCode() == KeyEvent.VK_ENTER) {
						startGame();
					}
				}
			}
		});
	}

	private void initUpdateCheck() {
		if (checkForUpdates) {
			checkForUpdatesOnStart = new JCheckBox();
			add(checkForUpdatesOnStart);
			setSize(getWidth(), getHeight() + 25);
		}
	}

	public boolean startGame() {
		MainStarter mainStarter = new MainStarter(gameToStart, getGameOptions());
		// MainStarter mainStarter = new MainStarter(gameToStart, new String[]{});
		if (mainStarter.isMothodStarted()) { // TODO: Wait for isMothodStarted() in Thread
			setVisible(false);
		}
		return mainStarter.isMothodStarted();
	}

	private void setIcon(JToggleButton button, String iconPath) {
		button.setSelectedIcon(new ImageIcon(ResourceLoader.getResource(iconPath)));
		button.setIcon(getBlassIIcon(iconPath, 0.7f));
	}

	private String[] getGameOptions() {
		if (selectFullscreen != null) {
			String[] options = new String[] { "fullscreen=" + selectFullscreen.isSelected(),
					"language=" + LanguageSelector.getSelectedLanguage() };
			String[] startOptions = new String[args.length + options.length];
			System.arraycopy(options, 0, startOptions, 0, options.length);
			System.arraycopy(args, 0, startOptions, options.length, args.length);
			return startOptions;
		} else
			return args;
	}

	private ImageIcon getBlassIIcon(String filename, float alpha) {
		Image image = null;
		try {
			image = ImageIO.read(ResourceLoader.getResource(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.fillRect(0, 0, 200, 200);
		return new ImageIcon(image);
	}

	private void selectLanguage() {
		LanguageSelector.setSystemLanguage();
		if (isGermanSetAsDefaultlanguage) {
			selectGerman.setSelected(true);
		} else if (LanguageSelector.getSelectedLanguage().equals("German")) {
			selectGerman.setSelected(true);
		} else {
			selectEnglish.setSelected(true);
		}
	}

	private void setStrings() {
		setTitle(LanguageSelector.getString("game") + " "
				+ LanguageSelector.getString("options"));
		languageSelectPanel.setBorder(BorderFactory.createTitledBorder(LanguageSelector
				.getString("language")));
		windoSizePanel.setBorder(BorderFactory.createTitledBorder(LanguageSelector
				.getString("window_size")));
		startGame.setText(LanguageSelector.getString("start"));
		exitOptions.setText(LanguageSelector.getString("exit_")
				+ LanguageSelector.getString("options"));
		selectEnglish.setText(LanguageSelector.getString("english"));
		selectWindow.setText(LanguageSelector.getString("window"));
		selectGerman.setText(LanguageSelector.getString("german"));
		showOptionsOnStart.setText(LanguageSelector.getString("show_options_on_start"));
		selectFullscreen.setText(LanguageSelector.getString("fullscreen"));
		if (checkForUpdatesOnStart != null) {
			checkForUpdatesOnStart.setText(LanguageSelector
					.getString("check_for_updates_on_start"));
		}
		validate();
	}

	public void setResolutions(String[] resolutions) {
		this.resolutions = resolutions;
	}

	public static void main(String[] args) {
		Properties argsProperties = PropertiesTools.convertArgsToLinkedHashMap(args);
		String gameToStartName = argsProperties.getProperty("gameToStart");
		evaluateArgs(args);
		if (gameToStartName == null) {
			new OptionStarter();
		} else {
			try {
				Class<?> gameToStart = Class.forName(gameToStartName);
				new OptionStarter(gameToStart);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private static void evaluateArgs(String[] args) {
		Properties argsProperties = PropertiesTools.convertArgsToLinkedHashMap(args);
		haveToshowOptionStarter = Boolean.parseBoolean(argsProperties
				.getProperty("showOptionStarter"));
	}

	public void setSavedStateFile(String savedStateFileName) {
		try {
			savedState = new SavedState(savedStateFileName);
			savedState.load();
		} catch (SlickException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadSavedOptions();
	}

	private void loadSavedOptions() {
		if (savedState != null) {
			showOptionPanelOnStart = Boolean.parseBoolean(savedState.getString(
					"showOptionPanelOnStart", "true"));
			isGermanSetAsDefaultlanguage = Boolean.parseBoolean(savedState.getString(
					"isGermanSetAsDefaultlanguage", "false"));
			startGameInFullscreen = Boolean.parseBoolean(savedState.getString(
					"startGameInFullscreen", "true"));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(selectGerman)) {
			LanguageSelector.setLanguage("German");
			savedState.setString("isGermanSetAsDefaultlanguage", "true");
		}
		if (e.getSource().equals(selectEnglish)) {
			LanguageSelector.setLanguage("English");
			savedState.setString("isGermanSetAsDefaultlanguage", "false");
		}
		if (e.getSource().equals(exitOptions)) {
			System.exit(DISPOSE_ON_CLOSE);
		}
		if (e.getSource().equals(startGame)) {
			startGame();
		}
		if (e.getSource().equals(showOptionsOnStart)) {
			savedState.setString("showOptionPanelOnStart", showOptionsOnStart.isSelected()
					+ "");
		}
		if (e.getSource().equals(selectFullscreen)) {
			savedState.setString("startGameInFullscreen", "true");
		}
		if (e.getSource().equals(selectWindow)) {
			savedState.setString("startGameInFullscreen", "false");
		}
		saveOptions();
		setStrings();

	}

	private void saveOptions() {
		try {
			savedState.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}