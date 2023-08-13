/*
 * @version 0.0 19.02.2009
 * @author Tobse F
 */
package lib.mylib.tools;

import lib.mylib.options.DefaultArgs.Args;
import lib.mylib.options.Paths;
import lib.mylib.swing.SwingUtils;
import lib.mylib.util.LanguageSelector;
import lib.mylib.util.MyOptions;
import lib.mylib.util.MyPropertys;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;

import static lib.mylib.options.Paths.RES_DIR;

public class OptionStarter extends JFrame implements ActionListener {

    private boolean showOptionPanelOnStart, showUpdateOption = true, isGermanSetAsDefaultlanguage,
            startGameInFullscreen, checkForUpdates = true;
    private JRadioButton selectGerman, selectEnglish, selectFullscreen, selectWindow;
    private ButtonGroup languageButtons, windoSizeButtons;
    private JButton startGame, exitOptions;
    private JComboBox resolution;
    private JPanel languageSelectPanel, windoSizePanel;
    private JCheckBox showOptionsOnStart, checkForUpdatesOnStart;
    private final Class<?> gameToStart;
    private String[] resolutions = new String[]{"1024 x 768"};
    private String[] args = new String[]{""};

    public OptionStarter() {
        this(OptionStarter.class, null);
    }

    public OptionStarter(Class<?> gameToStart, String[] args) {
        this.gameToStart = gameToStart;
        Log.info("OptionStarter Args: " + Arrays.toString(args));
        MyPropertys.setFile(gameToStart);
        loadSavedOptions();
        this.args = args;
        MyPropertys.setStrings(args);

        if (showOptionPanelOnStart) {
            initCompoments();
            setVisible(true);
        } else {
            startGame();
        }
    }

    public OptionStarter(Class<?> gameToStart) {
        this(gameToStart, null);
    }

    private void initCompoments() {
        setSize(300, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        SwingUtils.setCoolLookAndFeel();
        SwingUtils.setIcons(this, Paths.RES_DIR_LIB, "preferences-system");
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        selectEnglish = new JRadioButton();
        selectEnglish.addActionListener(this);
        setIcon(selectEnglish, RES_DIR + "flag_english.png");
        selectGerman = new JRadioButton();
        selectGerman.addActionListener(this);
        setIcon(selectGerman, RES_DIR + "flag_german.png");

        selectFullscreen = new JRadioButton();
        setIcon(selectFullscreen, RES_DIR + "option_fullscreen.png");
        selectFullscreen.addActionListener(this);
        selectFullscreen.setSelected(true);
        selectWindow = new JRadioButton();
        setIcon(selectWindow, RES_DIR + "option_window.png");
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

        // resolution = new JComboBox(resolutions);
        // resolution.setPreferredSize(new Dimension(200, 34));
        // add(resolution);

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
        setGuiStrings();
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

    /**
     * Adds the checkForOnlineUpdatesButton if {@link #showUpdateOption} is <code>true</code>
     * (default)
     */
    private void initUpdateCheck() {
        if (showUpdateOption) {
            checkForUpdatesOnStart = new JCheckBox();
            checkForUpdatesOnStart.setSelected(checkForUpdates);
            checkForUpdatesOnStart.addActionListener(this);
            add(checkForUpdatesOnStart);
            setSize(getWidth(), getHeight() + 25);
        }
    }

    public boolean startGame() {
        setVisible(false);
        dispose();
        MainStarter mainStarter = new MainStarter(gameToStart, getMainArgs());
        return mainStarter.isMethodStarted();
    }

    private void setIcon(JToggleButton button, String iconPath) {
        button.setSelectedIcon(new ImageIcon(ResourceLoader.getResource(iconPath)));
        button.setIcon(getBlassIIcon(iconPath, 0.7f));
    }

    /**
     * Reads the fullscreen option from gui converts it in a key=value pair, and adds it to the
     * given args
     *
     * @return the fullscreen option from gui converted in a key=value pair, added to the given
     * args
     */
    private String[] getMainArgs() {
        if (selectFullscreen != null) {
            String[] options = new String[]{"fullscreen=" + selectFullscreen.isSelected(),
                    "language=" + LanguageSelector.getSelectedLanguage()};
            String[] startOptions = new String[args.length + options.length];
            System.arraycopy(options, 0, startOptions, 0, options.length);
            System.arraycopy(args, 0, startOptions, options.length, args.length);
            return startOptions;
        } else {
            return args;
        }
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

    /**
     * Selects the default Language or if {@link #isGermanSetAsDefaultlanguage} German
     */
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

    /**
     * Sets the texts of the giu components according the selected language in the
     * {@link LanguageSelector}
     */
    private void setGuiStrings() {
        setTitle(LanguageSelector.getString("game") + " " + LanguageSelector.getString("options"));
        languageSelectPanel.setBorder(BorderFactory.createTitledBorder(LanguageSelector.getString("language")));
        windoSizePanel.setBorder(BorderFactory.createTitledBorder(LanguageSelector.getString("window_size")));
        startGame.setText(LanguageSelector.getString("start"));
        exitOptions.setText(LanguageSelector.getString("exit_") + LanguageSelector.getString("options"));
        selectEnglish.setText(LanguageSelector.getString("english"));
        selectWindow.setText(LanguageSelector.getString("window"));
        selectGerman.setText(LanguageSelector.getString("german"));
        showOptionsOnStart.setText(LanguageSelector.getString("show_options_on_start"));
        selectFullscreen.setText(LanguageSelector.getString("fullscreen"));
        if (checkForUpdatesOnStart != null) {
            checkForUpdatesOnStart.setText(LanguageSelector.getString("check_for_updates_on_start"));
        }
        validate();
    }

    public void setResolutions(String[] resolutions) {
        this.resolutions = resolutions;
    }

    public static void main(String[] args) {
        MyPropertys.setCheckForDefaults(true);
        MyPropertys.setFile(OptionStarter.class);
        MyPropertys.setStrings(args);
        String gameToStartName = MyOptions.getString(Args.gameToStart);
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

    private void loadSavedOptions() {
        showOptionPanelOnStart = MyOptions.getBoolean(Args.showOptionPanelOnStart, true);
        isGermanSetAsDefaultlanguage = MyOptions.getBoolean(Args.isGermanSetAsDefaultLanguage, false);
        startGameInFullscreen = MyOptions.getBoolean(Args.fullscreen, false);
        checkForUpdates = MyOptions.getBoolean(Args.checkForUpdates, true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(selectGerman)) {
            LanguageSelector.setLanguage("German");
            MyOptions.setBoolean(Args.isGermanSetAsDefaultLanguage, true);
        } else if (e.getSource().equals(selectEnglish)) {
            LanguageSelector.setLanguage("English");
            MyOptions.setBoolean(Args.isGermanSetAsDefaultLanguage, false);
        } else if (e.getSource().equals(exitOptions)) {
            System.exit(DISPOSE_ON_CLOSE);
        } else if (e.getSource().equals(startGame)) {
            startGame();
        } else if (e.getSource().equals(showOptionsOnStart)) {
            MyPropertys.setString("showOptionPanelOnStart", showOptionsOnStart.isSelected() + "");
        } else if (e.getSource().equals(selectFullscreen)) {
            MyOptions.setString(Args.fullscreen, "true");
        } else if (e.getSource().equals(selectWindow)) {
            MyOptions.setString(Args.fullscreen, "false");
        } else if (e.getSource().equals(checkForUpdatesOnStart)) {
            System.out.println(checkForUpdatesOnStart.isSelected());
            MyOptions.setBoolean(Args.checkForUpdates, checkForUpdatesOnStart.isSelected());
        }
        setGuiStrings();
    }

    /**
     * Sets if there should be a checkForUpdates checkBox
     *
     * @param showUpdateOption if the checkForUpdates checkBox should be added to the gui
     *                         (default ist <code>true</code>)
     */
    public void setShowUpdateOption(boolean showUpdateOption) {
        this.showUpdateOption = showUpdateOption;
    }

}