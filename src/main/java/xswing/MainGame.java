/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.Sound;
import lib.mylib.SpriteSheet;
import lib.mylib.hacks.NiftyGameState;
import lib.mylib.highscore.HighScoreTable;
import lib.mylib.object.*;
import lib.mylib.properties.GameConfig;
import lib.mylib.properties.ObjectConfig;
import lib.mylib.util.Clock;
import lib.mylib.util.MusicJukebox;
import lib.mylib.util.SlickUtils;
import org.newdawn.slick.*;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;
import tools.BallDropSimulator;
import tools.InitMusicPlayList;
import xswing.EffectCatalog.EffectType;
import xswing.LocationController.GameComponentLocation;
import xswing.ai.AIInterface;
import xswing.ball.*;
import xswing.events.BallEvent;
import xswing.events.BallEvent.BallEventType;
import xswing.events.BallEventListener;
import xswing.events.XSwingEvent;
import xswing.events.XSwingEvent.GameEventType;
import xswing.events.XSwingListener;
import xswing.gui.ScreenControllerScore;
import xswing.properties.ConfigToObjectMapper;
import xswing.properties.ResourcesLoader;
import xswing.start.XSwing;

import javax.swing.event.EventListenerList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static lib.mylib.options.Paths.*;
import static xswing.EffectCatalog.EffectType.*;
import static xswing.properties.XSGameConfigs.getConfig;
import static xswing.start.XSwing.GAME_OVER;

/**
 * The main container class, which combines all container elements
 *
 * @author Tobse
 */
public class MainGame extends BasicGameState implements Resetable, BallEventListener, XSwingListener {

    private final Background background = new Background();
    private GameComponentLocation gameLocation;
    private GameContainer container = null;
    private StateBasedGame game = null;
    /**
     * Folder with resources
     */
    public static final String HIGH_SCORE_FILE = XSwing.class.getSimpleName() + "_high_score.hscr";
    private int keyLeft = Input.KEY_LEFT, keyRight = Input.KEY_RIGHT, keyDown = Input.KEY_DOWN;
    private final int controllerID = 0;
    // private LocationController locationController;
    private EffectCatalog effectCatalog;
    private Cannon canon;
    private Clock clock;
    private BallTable ballTable;
    private Mechanics mechanics;
    private SeesawTable seesawTable;
    private BallCounter ballCounter;
    private HighScoreTable scoreTable;
    private HighScoreCounter highScoreCounter;
    private HighScoreMultiplicator multiplicator;
    private HighScorePanel highScore;
    private BallKiller ballKiller;
    private Level levelBall;
    private Reset reset;
    private SObjectList ballsToMove;
    private SObjectList gui;
    private BallFactory ballFactory;
    private SObjectList scorePopups;
    private LocalXSwingStatistics statistics;
    private final GameOver gameOver = new GameOver();

    private SpriteSheet multipl;
    private SpriteSheetFont numberFont, ballFont;
    private AngelCodeFont fontText, fontScore, pauseFont;
    private MusicJukebox music;
    private Pause pause;

    /**
     * Toggles if the {@link #highScoreState} should be drawn
     */
    private boolean isGameOver = false;
    private AIInterface ai;
    private BallDropSimulator ballDropSimulator;
    private GameStatistics gameStatistics;

    private final EventListenerList gameEventListeners = new EventListenerList();

    /**
     * Highscore submit Panel
     */
    private NiftyGameState highScoreState = null;

    private ScreenControllerScore scoreScreenController;

    private final int startLevel = 4;
    private GameConfig config;
    private Map<String, ObjectConfig> objectStore;

    private boolean reloadHighScoreUI = true;

    private List<SpriteSheet> loadBallSets() throws SlickException {
        List<SpriteSheet> ballSets = new ArrayList<>(5);
        ObjectConfig ballFactoryConf = getConf(BallFactory.class);
        for (String image : ballFactoryConf.getImages().values()) {
            ballSets.add(new SpriteSheet(new Image(RES_DIR + image), Ball.A, Ball.A));
        }
        return ballSets;
    }

    private void intiMusicJukebox() throws SlickException {
        music = new MusicJukebox();
        List<String> playList = config.isSetMusicPlayList() ? config.getMusicPlayList() : InitMusicPlayList.getAllMusicFiles();
        Collections.shuffle(playList);
        for (String musicFile : playList) {
            music.addMusic(new Music(MUSIC_DIR + musicFile.trim(), true));
        }
        SoundStore.get().setSoundVolume(config.getSoundConfig().getfxVolume() / 100f);
        SoundStore.get().setMusicVolume(config.getSoundConfig().getMusicVolume() / 100f);
    }

    public <T extends SObject> T map(T object) throws SlickException {
        ObjectConfig config = objectStore.get(object.getClass().getSimpleName());
        if (config == null) {
            throw new IllegalArgumentException("Couldn't fin a config for " + object.getClass().getName() + ":" + object);
        }
        ConfigToObjectMapper.map(object, config);
        return object;
    }

    private void setSounds(EffectCatalog catalog, EffectType... effects) throws SlickException {
        for (EffectType effect : effects) {
            setSound(catalog, effect);
        }
    }

    private void setSound(EffectCatalog catalog, EffectType effect) throws SlickException {
        ObjectConfig config = objectStore.get(EffectCatalog.class.getSimpleName());
        catalog.setSound(new Sound(SOUND_DIR + config.getSound(effect.toString().toLowerCase())), effect);
    }

    public ObjectConfig getConf(Class<?> sObject) {
        return objectStore.get(sObject.getSimpleName());
    }

    public void setKeys(int keyCodeLeft, int keyCodeRight, int keyCodeDown) {
        keyLeft = keyCodeLeft;
        keyRight = keyCodeRight;
        keyDown = keyCodeDown;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        newGame();
        this.game = game;
        this.container = container;
        music.shuffle();
        music.play();
    }

    /* (non-Javadoc)
     * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.container = container;
        config = getConfig();
        objectStore = ResourcesLoader.getObjectStore(config.getSelctedObjectConfigSet());
        map(new Ball(0));// Load Ball.A

        ResourcesLoader.accesAllResources(config);

        // Images
        multipl = new SpriteSheet(new Image(RES_DIR + "multiplicator_sp.jpg"), 189, 72);

        // Fonts
        fontText = newFont("font_arial_16_bold");
        pauseFont = newFont("arial_black_71");
        fontScore = newFont("berlin_sans_fb_demi_38");
        numberFont = newNumberFont("numberFont_s19", 15, 19, ',');
        ballFont = newNumberFont("spriteFontBalls2", 11, 16, '.');

        // Music
        intiMusicJukebox();

        // Objects
        map(background);

        addXSwingListener(this);
        gameStatistics = new GameStatistics();
        addXSwingListener(gameStatistics);

        ballsToMove = new SObjectList();
        gui = new SObjectList();
        effectCatalog = new EffectCatalog();
        reset = new Reset();
        ballTable = map(new BallTable());
        ballTable.addBallEventListerner(this);
        mechanics = new Mechanics(ballTable);
        mechanics.addBallEventListener(this);
        clock = map(new Clock(numberFont));
        ballCounter = map(new BallCounter(ballFont));
        levelBall = map(new Level(startLevel, null, ballFont));
        ballCounter.setLevel(levelBall);
        canon = map(new Cannon(ballTable, ballCounter, effectCatalog));
        canon.setSpites();

        multiplicator = map(new HighScoreMultiplicator(multipl));
        highScoreCounter = map(new HighScoreCounter(numberFont, multiplicator));

        scoreTable = new HighScoreTable();
        scoreTable.load();
        highScore = map(new HighScorePanel(fontText, scoreTable));

        statistics = new LocalXSwingStatistics();
        addXSwingListener(statistics);
        seesawTable = map(new SeesawTable(numberFont, ballTable));

        map(gameOver);

        setSounds(effectCatalog, EXPLOSION, SHRINC, BOUNCING);

        ballKiller = new BallKiller(mechanics, highScoreCounter, ballTable);
        ballTable.addBallEventListerner(ballKiller);
        ballFactory = new BallFactory(ballTable, ballsToMove, ballFont, loadBallSets(), effectCatalog, canon, levelBall);
        ballFactory.addBallEventListener(this);
        ballFactory.addBallEventListener(gameStatistics);

        scoreScreenController = new ScreenControllerScore(game, scoreTable, clock, gameStatistics);
        scorePopups = new SObjectList();

        pause = new Pause(pauseFont, container.getWidth(), container.getHeight());
        pause.setVisible(false);
        reset.add(gameStatistics, ballTable, gui, ballsToMove, scorePopups, effectCatalog);
        gui.add(background, canon, clock, seesawTable, levelBall, ballCounter, multiplicator, highScoreCounter, highScore);

        boolean activateAI = false;
        if (activateAI && gameLocation == GameComponentLocation.CENTER) {
            ai = new AIInterface(this, ballTable, canon);
        }
        Log.warn("MainGame..............");
        highScoreState = new NiftyGameState(GAME_OVER);
        highScoreState.init(container, game);
        highScoreState.enableMouseImage(new Image("res/cursor.png"), 2, 2);
        container.getInput().removeListener(highScoreState);
        container.getInput().addListener(highScoreState);
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        music.pause();
    }

    @Override
    public void reset() {
        newGame();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        gui.render(g);
        ballsToMove.render(g);
        effectCatalog.render(g);
        scorePopups.render(g);
        pause.render(g);
        if (highScoreState != null && isGameOver) {
            highScoreState.render(container, game, g);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        if (!isGameOver) {
            checkKeysMain(input);
            if (!container.isPaused()) { // no Input while game is paused
                checkKeysDuringGamee(input);
            }
        }

        if (!container.isPaused()) {
            gui.update(delta);
            effectCatalog.update(delta);
            scorePopups.update(delta);
            ballKiller.update(delta);
            if (mechanics.checkHight()) {
                gameOver(game);
            }
            ballKiller.update(delta);
            ballFactory.updateBalls(delta);
            if (ai != null) {
                ai.update(delta);
            }
        }
        if (highScoreState != null && isGameOver) {
            highScoreState.update(container, game, delta);
        }
    }

    /**
     * Performs KeyEvents, which should be also performed during game is paused
     *
     * @param input GameInput
     */
    private void checkKeysMain(Input input) {
        if (input.isKeyPressed(Input.KEY_P)) {
            // || (!container.isFullscreen() && !Mouse.isInsideWindow() &&
            // !container.isPaused())
            if (container.isPaused()) {
                fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_PAUSED));
            } else {
                fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_RESUMED));
            }
            container.setPaused(!container.isPaused());
            pause.setVisible(!pause.isVisible());
            resetInput();
        }
        if (input.isKeyPressed(Input.KEY_N)) {
            fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_STOPPED));
            newGame();
        }
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_STOPPED));
            if (game.getCurrentState().getID() == XSwing.GAME_PANEL) {
                // Game is started with Menu
                Log.info("ESC pressed, swiching to main menu");
                SlickUtils.hideMouse(game.getContainer(), false);
                game.enterState(XSwing.START_SCREEN);
            } else {
                // Game is started without Menu
                Log.info("Exit Game wit ESC -no main menu");
                container.exit();
            }
        }

    }

    boolean controllerPressedLeft = false;
    boolean controllerPressedRight = false;
    boolean controllerPressedDown = false;

    /**
     * Performs KeyEvents which should be not performed, during the game is paused
     *
     * @param input GameInput
     */
    private void checkKeysDuringGamee(Input input) {

        if (input.isKeyPressed(keyLeft) || (!controllerPressedLeft && input.isControllerLeft(controllerID))) {
            notifyListener(new XSwingEvent(this, GameEventType.CANNON_MOVED_LEFT));
        }
        if (input.isKeyPressed(keyRight) || (!controllerPressedRight && input.isControllerRight(controllerID))) {
            notifyListener(new XSwingEvent(this, GameEventType.CANNON_MOVED_RIGHT));
        }
        if (input.isKeyPressed(keyDown) || (!controllerPressedDown && input.isControllerDown(controllerID))) {
            notifyListener(new XSwingEvent(this, GameEventType.PRESSED_DOWN));
        }
        if (input.isKeyPressed(Input.KEY_J)) {
            // ballFactory.addNewJoker();
            System.out.println(ballTable);
        }
        if (input.isKeyPressed(Input.KEY_K)) {
            // if (ballDropSimulator == null) {
            // ballDropSimulator = new BallDropSimulator();
            // }
            // ballDropSimulator.setBallTable(ballTable.clone());
        }
        if (input.isKeyPressed(Input.KEY_E)) {
            effectCatalog.setShowParticles(!effectCatalog.isShowParticles());
        }
        if (input.isKeyPressed(Input.KEY_B)) {
            ballFactory.toggleSpriteSheet();
        }
        if (input.isKeyPressed(Input.KEY_M)) {
            // ballFactory.addNewStone();
        }
        if (input.isKeyPressed(Input.KEY_F)) {
            container.setShowFPS(!container.isShowingFPS());
        }
        if (input.isKeyPressed(Input.KEY_S)) {
            // shrinkGame(game);
        }
        if (input.isKeyPressed(Input.KEY_H)) {
            highScore.setVisible(!highScore.isVisible());
        }
        controllerPressedLeft = input.isControllerLeft(controllerID);
        controllerPressedRight = input.isControllerRight(controllerID);
        controllerPressedDown = input.isControllerDown(controllerID);
        // if (input.isKeyPressed(Input.KEY_1)) {
        // canon.getBall().setNr(0);
        // }
        // if (input.isKeyPressed(Input.KEY_2)) {
        // canon.getBall().setNr(1);
        // }
        // if (input.isKeyPressed(Input.KEY_2)) {
        // canon.getBall().setNr(2);
        // }
        // if (input.isKeyPressed(Input.KEY_3)) {
        // canon.getBall().setNr(3);
        // }
        // if (input.isKeyPressed(Input.KEY_4)) {
        // canon.getBall().setNr(4);
        // }
        // if (input.isKeyPressed(Input.KEY_5)) {
        // canon.getBall().setNr(5);
        // }
        if (input.isKeyPressed(Input.KEY_F2)) {
            try {
                container.setFullscreen(!container.isFullscreen());
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Resets all values and starts a new game
     */
    public void newGame() {
        Log.info("New Game");
        resetInput();
        reset.reset();
        ballsToMove.clear();
        ballFactory.addTopBalls();
        container.setPaused(false);
        isGameOver = false;
        fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_STARTED));
    }

    /**
     * Finishes the current game and switches to the highScoreTable
     *
     */
    private void gameOver(StateBasedGame game) throws SlickException {
        isGameOver = true;
        Log.info("Game Over");
        scoreScreenController.setHighScore(highScoreCounter.getScore());
        if (reloadHighScoreUI) {
            highScoreState.fromXml("xswing/gui/high_score.xml", scoreScreenController);
            reloadHighScoreUI = false;
        }
        fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_OVER));
        // highScoreState.init(container, game);
        highScoreState.enter(container, game);
        // highScoreState.gotoScreenXSwing.GAME_OVER(; //VOID: ScreenID of NiftyGameState?)
        gameOver.play();
        container.setPaused(true);
    }

    /**
     * Clears all inputs. Useful to prevent keyEvents during pause mode or while entering the HighScore.
     */
    private void resetInput() {
        container.getInput().clearControlPressedRecord();
        container.getInput().clearKeyPressedRecord();
        container.getInput().clearMousePressedRecord();
    }

    @Override
    public void gameEvent(XSwingEvent e) {
        if (e.getGameEventType() == GameEventType.CANNON_MOVED_LEFT) {
            canon.moveLeft();
        } else if (e.getGameEventType() == GameEventType.CANNON_MOVED_RIGHT) {
            canon.moveRight();
        }
        if (e.getGameEventType() == GameEventType.PRESSED_DOWN) {
            if (canon.isReadyToReleaseBall()) {
                Ball ball = ballFactory.getNewBall();
                notifyListener(new XSwingEvent(this, GameEventType.BALL_DROPPED, ball));
            }
        } else if (e.getGameEventType() == GameEventType.BALL_DROPPED) {
            canon.releaseBall(e.getBall());
        }
    }

    @Override
    public void ballEvent(BallEvent e) {
        if (e.getBallEventType() == BallEventType.BALL_HITS_GROUND || e.getBallEventType() == BallEventType.BALL_HITS_BALL) {
            mechanics.checkOfFive(e.getBall());
            mechanics.checkOfThree(e.getBall());
        } else if (e.getBallEventType() == BallEventType.BALL_EXPLODED) {
            scorePopups.add(new ScorePopup(fontScore, e.getBall().getX(), e.getBall().getY(), highScoreCounter.getBonus() + ""));
        } else if (e.getBallEventType() == BallEventType.BALL_WITH_THREE_IN_A_ROW) {
            // e.getBall().addBallEventListener(ballKiller);
        } else if (e.getBallEventType() == BallEventType.BALL_CAUGHT_BY_EXPLOSION) {
            effectCatalog.addEffect(e.getBall(), EffectType.EXPLOSION);
            ballsToMove.remove(e.getBall());
            ballTable.remove(e.getBall());

        } else if (e.getBallEventType() == BallEventType.BALL_CAUGHT_BY_SHRINC) {
            effectCatalog.addEffect(e.getBall(), EffectType.SHRINC);
        }
    }

    /**
     * Adds an {@code BallEventListener} to the Ball.
     *
     * @param listener the {@code BallEventListener} to be added
     */
    public void addXSwingListener(XSwingListener listener) {
        gameEventListeners.add(XSwingListener.class, listener);
    }

    /**
     * Removes an {@code BallEventListener} from the Ball
     *
     * @param listener to be removed
     */
    public void removeBallEventListener(XSwingListener listener) {
        gameEventListeners.remove(XSwingListener.class, listener);
    }

    /**
     * Notifies all {@code XSwingListener}s about a {@code XSwingEvent}
     *
     * @param event the {@code XSwingEvent}
     * @see EventListenerList
     */
    protected void notifyListener(XSwingEvent event) {
        for (XSwingListener listener : gameEventListeners.getListeners(XSwingListener.class)) {
            listener.gameEvent(event);
        }
    }

    public void fireXSwingEvent(XSwingEvent event) {
        notifyListener(event);
    }

    public int getScore() {
        return highScoreCounter.getScore();
    }

    public void addJoker() {
        ballFactory.addNewJoker();
    }

    public void addStone() {
        ballFactory.addNewStone();
    }

    private static AngelCodeFont newFont(String font) throws SlickException {
        return new AngelCodeFont(FONT_DIR + font + ".fnt", FONT_DIR + font + ".png");
    }

    private static SpriteSheetFont newNumberFont(String font, int letterWidth, int letterHight, char startCharacter) throws SlickException {
        return new SpriteSheetFont(new SpriteSheet(new Image(FONT_DIR + font + ".png"), letterWidth, letterHight), startCharacter);
    }

}