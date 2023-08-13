/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.Sound;
import lib.mylib.SpriteSheet;
import lib.mylib.object.Reset;
import lib.mylib.object.Resetable;
import lib.mylib.object.SObjectList;
import lib.mylib.options.DefaultArgs.Args;
import lib.mylib.util.Clock;
import lib.mylib.util.MyOptions;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;
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
import xswing.net.ThreadedHighScoreSubmitter;
import xswing.start.XSwing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static lib.mylib.options.Paths.*;

/**
 * The main container class, which combines all container elements
 *
 * @author Tobse
 */
public class MainGameAndroidX extends BasicGame implements Resetable, BallEventListener, XSwingListener {

    public MainGameAndroidX() {
        super("XSwing Plus");
        gameLocation = GameComponentLocation.ANDROID;
    }

    private final GameComponentLocation gameLocation;
    private GameContainer container = null;
    private final StateBasedGame game = null;
    /**
     * Folder with resources
     */
    public static final String HIGH_SCORE_FILE = XSwing.class.getSimpleName() + "_high_score.hscr";
    private int keyLeft = Input.KEY_LEFT, keyRight = Input.KEY_RIGHT, keyDown = Input.KEY_DOWN;
    private final int controllerID = 0;
    private LocationController locationController;
    private EffectCatalog effectCatalog;
    private Cannon canon;
    private Clock clock;
    private BallTable ballTable;
    private Mechanics mechanics;
    private SeesawTable seesawTable;
    private BallCounter ballCounter;
    // private HighScoreTable scoreTable;
    private HighScoreCounter highScoreCounter;
    private HighScoreMultiplicator multiplicator;
    // private HighScorePanel highScore;
    private BallKiller ballKiller;
    private Level levelBall;
    private Reset reset;
    private SObjectList ballsToMove;
    private SObjectList gui;
    private BallFactory ballFactory;
    private SObjectList scorePopups;
    private LocalXSwingStatistics statistics;

    private Image backgroundd;
    private SpriteSheet balls1, balls2, multipl, cannons;
    private SpriteSheetFont numberFont, ballFont;
    private AngelCodeFont fontText, fontScore, pauseFont;
    private Pause pause;

    /**
     * Toggles if the {@link #highScoreState} should be drawn
     */
    private boolean isGameOver = false;
    private AIInterface ai;
    // private BallDropSimulator ballDropSimulator;
    private GameStatistics gameStatistics;

    private Sound klack1, kran1, wup, shrinc, warning;

    private final List<XSwingListener> gameEventListeners = new LinkedList<XSwingListener>();

    private final int startLevel = 4;

    /**
     * Highscore submit Panel
     */
    // private NiftyGameState highScoreState = null;
    // private ScreenControllerScore scoreScreenController;
    @Override
    public void init(GameContainer container) throws SlickException {
        this.container = container;
        container.setMinimumLogicUpdateInterval(25);
        container.setClearEachFrame(false);
        locationController = new LocationController(gameLocation);
        // Images
        multipl = new SpriteSheet(new Image(RES_DIR + "multiplicator.png"), 122, 46);
        cannons = new SpriteSheet(new Image(RES_DIR + "cannons.png"), 65, 99);
        balls1 = new SpriteSheet(new Image(RES_DIR + "balls1_32.png"), Ball.A, Ball.A);
        // balls2 = new SpriteSheet(new Image(RES_DIR + "balls2.png"), Ball.A, Ball.A);
        // Fonts
        fontText = new AngelCodeFont(FONT_DIR + "font_arial_16_bold.fnt", FONT_DIR + "font_arial_16_bold.png");
        pauseFont = new AngelCodeFont(FONT_DIR + "arial_black_71.fnt", FONT_DIR + "arial_black_71.png");
        fontScore = new AngelCodeFont(FONT_DIR + "berlin_sans_fb_demi_38.fnt", FONT_DIR + "berlin_sans_fb_demi_38.png");
        numberFont = new SpriteSheetFont(new SpriteSheet(new Image(FONT_DIR + "numberFont_s19.png"), 15, 19), ',');
        ballFont = new SpriteSheetFont(new SpriteSheet(new Image(FONT_DIR + "spriteFontBalls2.png"), 11, 16), '.');
        // Sounds

        klack1 = new Sound(SOUND_DIR + "KLACK4.WAV", 2, 450);
        kran1 = new Sound(SOUND_DIR + "KRAN1.WAV", 3, 450);
        wup = new Sound(SOUND_DIR + "DREIER.WAV", 1, 2100);
        shrinc = new Sound(SOUND_DIR + "SPRATZ2.WAV", 2, 1800);
        warning = new Sound(SOUND_DIR + "ALARM1.WAV", 1, 1300);
        // klack1.setMaxPlyingTime(5);
        // wup.setMaxPlyingTime(1000);
        // shrinc.setMaxPlyingTime(5);
        // klack1.setPreventSimulaniousPlay(true);
        // kran1.setPreventSimulaniousPlay(true);
        // wup.setPreventSimulaniousPlay(true);
        // shrinc.setPreventSimulaniousPlay(true);
        // warning.setPreventSimulaniousPlay(true);

        // Objects
        addXSwingListener(this);
        gameStatistics = new GameStatistics();
        addXSwingListener(gameStatistics);
        ballsToMove = new SObjectList();
        gui = new SObjectList();
        effectCatalog = new EffectCatalog();
        reset = new Reset();
        ballTable = new BallTable();
        ballTable.addBallEventListerner(this);
        locationController.setLocationToObject(ballTable);
        mechanics = new Mechanics(ballTable);
        mechanics.addBallEventListener(this);
        clock = new Clock(numberFont);
        locationController.setLocationToObject(clock);
        ballCounter = new BallCounter(ballFont);
        locationController.setLocationToObject(ballCounter);
        levelBall = new Level(startLevel, balls1, ballFont);
        locationController.setLocationToObject(levelBall);
        ballCounter.setLevel(levelBall);
        canon = new Cannon(ballTable, ballCounter, effectCatalog);
        canon.setSpites(cannons);
        canon.setSoundMove(kran1);
        canon.setSoundStackingAlarm(warning);
        locationController.setLocationToObject(canon);
        multiplicator = new HighScoreMultiplicator(multipl);
        locationController.setLocationToObject(multiplicator);
        highScoreCounter = new HighScoreCounter(numberFont, multiplicator);
        locationController.setLocationToObject(highScoreCounter);
        // scoreTable = new HighScoreTable();
        // scoreTable.load();
        // highScore = new HighScorePanel(fontText, scoreTable);
        // locationController.setLocationToObject(highScore);
        statistics = new LocalXSwingStatistics();
        addXSwingListener(statistics);
        seesawTable = new SeesawTable(numberFont, ballTable);
        locationController.setLocationToObject(seesawTable);
        effectCatalog.setSound(wup, EffectType.EXPLOSION);
        effectCatalog.setSound(shrinc, EffectType.SHRINC);
        effectCatalog.setSound(klack1, EffectType.BOUNCING);
        ballKiller = new BallKiller(mechanics, highScoreCounter, ballTable);
        ballTable.addBallEventListerner(ballKiller);
        List<SpriteSheet> ballsList = new ArrayList<SpriteSheet>(2);
        ballsList.add(balls1);
        ballsList.add(balls2);
        ballFactory = new BallFactory(ballTable, ballsToMove, ballFont, ballsList, effectCatalog, canon, levelBall);
        ballFactory.addBallEventListener(this);
        ballFactory.addBallEventListener(gameStatistics);
        // scoreScreenController = new ScreenControllerScore(game, scoreTable, clock,
        // gameStatistics);
        scorePopups = new SObjectList();

        pause = new Pause(pauseFont, container.getWidth(), container.getHeight());
        pause.setVisible(false);
        reset.add(gameStatistics);
        reset.add(ballTable);
        reset.add(gui);
        reset.add(ballsToMove);
        reset.add(scorePopups);
        reset.add(effectCatalog);

        backgroundd = new Image(RES_DIR + "background.png");
        gui.add(canon);
        gui.add(clock);
        gui.add(seesawTable);
        gui.add(levelBall);
        gui.add(ballCounter);
        gui.add(multiplicator);
        gui.add(highScoreCounter);
        // gui.add(highScore);

        boolean activateAI = false;
        if (activateAI && gameLocation == GameComponentLocation.CENTER) {
            ai = new AIInterface(this, ballTable, canon);
        }
        Log.warn("MainGame..............");
        /*
         * highScoreState = new NiftyGameState(XSwing.GAME_OVER); highScoreState.init(container, game);
         * highScoreState.enableMouseImage(new Image("res/cursor.png"), 2, 2); //
         * highScoreState.setInput(game.getContainer().getInput()); container.getInput().removeListener(highScoreState);
         * container.getInput().addListener(highScoreState);
         */

        newGame();
    }

    public void setKeys(int keyCodeLeft, int keyCodeRight, int keyCodeDown) {
        keyLeft = keyCodeLeft;
        keyRight = keyCodeRight;
        keyDown = keyCodeDown;
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
        fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_STARTED));
    }

    @Override
    public void reset() {
        newGame();
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        backgroundd.draw();
        gui.render(g);
        ballsToMove.render(g);
        effectCatalog.render(g);
        scorePopups.render(g);
        pause.render(g);
        /*
         * if (highScoreState != null && isGameOver) { highScoreState.render(container, game, g); }
         */
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
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
        /*
         * if (highScoreState != null && isGameOver) { highScoreState.update(container, game, delta); }
         */
    }

    /**
     * Performs KeyEvents, which should be also performed during game is paused
     *
     * @param input GameInput
     */
    private void checkKeysMain(Input input) {
        if (input.isKeyPressed(Input.KEY_P)) {
            if (container.isPaused()) {
                fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_PAUSED));
            } else {
                fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_RESUMED));
            }
            container.setPaused(!container.isPaused());
            pause.setVisible(!pause.isVisible());
            resetInput();
        }
        /*
         * if (input.isKeyPressed(Input.KEY_N)) { fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_STOPPED));
         * newGame(); } if (input.isKeyDown(Input.KEY_ESCAPE)) { fireXSwingEvent(new XSwingEvent(this,
         * GameEventType.GAME_STOPPED)); if (game.getCurrentState().getID() == XSwing.GAME_PANEL) { // Game is started
         * with Menu Log.info("ESC pressed, swiching to main menu"); SlickUtils.hideMouse(game.getContainer(), false);
         * game.enterState(XSwing.START_SCREEN); } else { // Game is started without Menu
         * Log.info("Exit Game wit ESC -no main menu"); container.exit(); } }
         */
    }

    boolean controllerPressedLeft = false;
    boolean controllerPressedRight = false;
    boolean controllerPressedDown = false;

    @Override
    public void mousePressed(int button, int x, int y) {
        super.mousePressed(button, x, y);
        int senetiveAreaSide = 160;
        int senetiveAreaButtom = 405;
        if (x < senetiveAreaSide) {
            notifyListener(new XSwingEvent(this, GameEventType.CANNON_MOVED_LEFT));
        } else if (x > 800 - senetiveAreaSide) {
            notifyListener(new XSwingEvent(this, GameEventType.CANNON_MOVED_RIGHT));
        } else if (y > 480 - senetiveAreaButtom) {
            notifyListener(new XSwingEvent(this, GameEventType.PRESSED_DOWN));
        }
    }

    /**
     * Performs KeyEvents which should be not performed, during the game is paused
     *
     * @param input GameInput
     */
    private void checkKeysDuringGamee(Input input) {
        if (input.isKeyPressed(keyLeft)) {
            notifyListener(new XSwingEvent(this, GameEventType.CANNON_MOVED_LEFT));
        }
        if (input.isKeyPressed(keyRight)) {
            notifyListener(new XSwingEvent(this, GameEventType.CANNON_MOVED_RIGHT));
        }
        if (input.isKeyPressed(keyDown)) {
            notifyListener(new XSwingEvent(this, GameEventType.PRESSED_DOWN));
        }
        /*
         * if (input.isKeyPressed(Input.KEY_J)) { // ballFactory.addNewJoker(); System.out.println(ballTable); } if
         * (input.isKeyPressed(Input.KEY_K)) { // if (ballDropSimulator == null) { // ballDropSimulator = new
         * BallDropSimulator(); // } // ballDropSimulator.setBallTable(ballTable.clone()); } if
         * (input.isKeyPressed(Input.KEY_E)) { effectCatalog.setShowParticles(!effectCatalog.isShowParticles()); } if
         * (input.isKeyPressed(Input.KEY_B)) { ballFactory.toggleSpriteSheet(); } if (input.isKeyPressed(Input.KEY_M)) {
         * // ballFactory.addNewStone(); } if (input.isKeyPressed(Input.KEY_F)) {
         * container.setShowFPS(!container.isShowingFPS()); } if (input.isKeyPressed(Input.KEY_S)) { //
         * shrinkGame(game); } if (input.isKeyPressed(Input.KEY_H)) { // highScore.setVisible(!highScore.isVisible()); }
         * controllerPressedLeft = input.isControllerLeft(controllerID); controllerPressedRight =
         * input.isControllerRight(controllerID); controllerPressedDown = input.isControllerDown(controllerID); // if
         * (input.isKeyPressed(Input.KEY_1)) { // canon.getBall().setNr(0); // } // if (input.isKeyPressed(Input.KEY_2))
         * { // canon.getBall().setNr(1); // } // if (input.isKeyPressed(Input.KEY_2)) { // canon.getBall().setNr(2); //
         * } // if (input.isKeyPressed(Input.KEY_3)) { // canon.getBall().setNr(3); // } // if
         * (input.isKeyPressed(Input.KEY_4)) { // canon.getBall().setNr(4); // } // if (input.isKeyPressed(Input.KEY_5))
         * { // canon.getBall().setNr(5); // } if (input.isKeyPressed(Input.KEY_F2)) { try {
         * container.setFullscreen(!container.isFullscreen()); } catch (SlickException e) { e.printStackTrace(); } }
         */
    }

    /**
     * Finishes the current game and switches to the highScoreTable
     *
     * @param container
     * @param game
     * @throws SlickException
     */
    private void gameOver(StateBasedGame game) throws SlickException {
        isGameOver = true;
        Log.info("Game Over");
        // scoreScreenController.setHighScore(highScoreCounter.getScore());
        fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_OVER));
        /*
         * if (firstStart) { // highScoreState.fromXml("xswing/gui/high_score.xml", scoreScreenController); firstStart =
         * false; }
         */
        // highScoreState.init(container, game);
        // highScoreState.enter(container, game);
        // highScoreState.gotoScreenXSwing.GAME_OVER(; //VOID: ScreenID of NiftyGameState?)
        container.setPaused(true);
        String name = MyOptions.getString(Args.playerName);
        ThreadedHighScoreSubmitter.submitScore(name, highScoreCounter.getScore(), clock.getTimeSinceStart(),
                gameStatistics.getReleasedBalls(), gameStatistics.getDestroyedBalls());
        newGame();
    }

    /**
     * Clears all inputs. Useful to prevent keyEvents during pause mode or while entering the HighScore.
     */
    private void resetInput() {
        /*
         * container.getInput().clearControlPressedRecord(); container.getInput().clearKeyPressedRecord();
         * container.getInput().clearMousePressedRecord();
         */
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
        gameEventListeners.add(listener);
    }

    /**
     * Removes an {@code BallEventListener} from the Ball
     *
     * @param listener to be removed
     */
    public void removeBallEventListener(XSwingListener listener) {
        gameEventListeners.remove(listener);
    }

    /**
     * Notifies all {@code XSwingListener}s about a {@code XSwingEvent}
     *
     * @param event the {@code XSwingEvent}
     * @see EventListenerList
     */
    protected void notifyListener(XSwingEvent event) {
        for (XSwingListener listener : gameEventListeners) {
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

}