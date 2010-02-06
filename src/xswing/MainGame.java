/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package xswing;

import static lib.mylib.options.Paths.RES_DIR;
import java.awt.Point;
import javax.swing.event.EventListenerList;
import lib.mylib.Sound;
import lib.mylib.SpriteSheet;
import lib.mylib.highscore.HighScoreTable;
import lib.mylib.object.*;
import lib.mylib.util.Clock;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;
import tools.BallDropSimulator;
import xswing.EffectCatalog.particleEffects;
import xswing.LocationController.GameComponentLocation;
import xswing.ai.AIInterface;
import xswing.events.*;
import xswing.events.BallEvent.BallEventType;
import xswing.events.XSwingEvent.GameEventType;
import xswing.gui.ScreenControllerScore;
import xswing.start.XSwing;
import de.lessvoid.nifty.slick.NiftyGameState;

/**
 * The main container class, which combines all container elements
 * 
 * @author Tobse
 */
public class MainGame extends BasicGameState implements Resetable, BallEventListener,
		XSwingListener {

	public MainGame(GameComponentLocation gameLocation) {
		this.gameLocation = gameLocation;
		gameEventListeners = new EventListenerList();
	}

	private GameComponentLocation gameLocation;
	private GameContainer container = null;
	private StateBasedGame game = null;
	/** Sceensize in weight height */
	private int widht, height;
	/** Folder with resources */
	public static final String HIGH_SCORE_FILE = XSwing.class.getSimpleName()
			+ "_high_score.hscr";
	private int keyLeft = Input.KEY_LEFT, keyRight = Input.KEY_RIGHT,
			keyDown = Input.KEY_DOWN;
	private LocationController locationController;
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
	private GameStatistics statistics;

	private SpriteSheet balls1, balls2, multipl, cannons;
	private SpriteSheetFont numberFont, ballFont;
	private AngelCodeFont fontText, fontScore, pauseFont;
	private Sound klack1, kran1, wup, shrinc, warning;
	private Music music;
	private Pause pause;

	/** Toggles if the {@link #highScoreState} should be drawn */
	private boolean isGameOver = false;
	private AIInterface ai;
	private BallDropSimulator ballDropSimulator;

	private EventListenerList gameEventListeners;

	private NiftyGameState highScoreState = null;

	private ScreenControllerScore scoreScreenController;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		container.setSoundVolume(0.5f); // calm down the Effect Sounds
		height = container.getHeight();
		widht = container.getWidth();
		// Images
		multipl = new SpriteSheet(new Image(RES_DIR + "multiplicator_sp.jpg"), 189, 72);
		cannons = new SpriteSheet(new Image(RES_DIR + "cannons.png"), 72, 110);
		balls1 = new SpriteSheet(new Image(RES_DIR + "balls1.png"), Ball.A, Ball.A);
		balls2 = new SpriteSheet(new Image(RES_DIR + "balls2.png"), Ball.A, Ball.A);
		fontText = new AngelCodeFont(RES_DIR + "font_arial_16_bold.fnt", RES_DIR
				+ "font_arial_16_bold.png");
		pauseFont = new AngelCodeFont(RES_DIR + "arial_black_71.fnt", RES_DIR
				+ "arial_black_71.png");
		fontScore = new AngelCodeFont(RES_DIR + "berlin_sans_fb_demi_38.fnt", RES_DIR
				+ "berlin_sans_fb_demi_38.png");
		numberFont = new SpriteSheetFont(new SpriteSheet(new Image(RES_DIR
				+ "numberFont_s19.png"), 15, 19), ',');
		ballFont = new SpriteSheetFont(new SpriteSheet(new Image(RES_DIR
				+ "spriteFontBalls2.png"), 11, 16), '.');
		// Sounds
		klack1 = new Sound(RES_DIR + "KLACK4.WAV");
		klack1.setMaxPlyingTime(5);
		kran1 = new Sound(RES_DIR + "KRAN1.WAV");
		wup = new Sound(RES_DIR + "DREIER.WAV");
		wup.setMaxPlyingTime(1000);
		shrinc = new Sound(RES_DIR + "SPRATZ2.WAV");
		shrinc.setMaxPlyingTime(5);
		warning = new Sound(RES_DIR + "ALARM1.WAV");
		music = new Music(RES_DIR + "music.mod", true);
		// Objects
		addXSwingListener(this);
		locationController = new LocationController(gameLocation);
		ballsToMove = new SObjectList();
		gui = new SObjectList();
		effectCatalog = new EffectCatalog();
		reset = new Reset();
		ballTable = new BallTable();
		locationController.setLocationToObject(ballTable);
		mechanics = new Mechanics(ballTable);
		mechanics.addBallEventListener(this);
		clock = new Clock(numberFont);
		locationController.setLocationToObject(clock);
		ballCounter = new BallCounter(ballFont);
		locationController.setLocationToObject(ballCounter);
		levelBall = new Level(4, balls1, ballFont);
		locationController.setLocationToObject(levelBall);
		ballCounter.setLevel(levelBall);
		canon = new Cannon(cannons, new Sound[] { kran1, warning }, ballTable, ballCounter,
				effectCatalog);
		locationController.setLocationToObject(canon);
		multiplicator = new HighScoreMultiplicator(multipl);
		locationController.setLocationToObject(multiplicator);
		highScoreCounter = new HighScoreCounter(numberFont, multiplicator);
		locationController.setLocationToObject(highScoreCounter);
		scoreTable = new HighScoreTable();
		scoreTable.load();
		highScore = new HighScorePanel(fontText, scoreTable);
		locationController.setLocationToObject(highScore);
		statistics = new GameStatistics();
		addXSwingListener(statistics);
		scoreScreenController = new ScreenControllerScore(game, scoreTable, statistics, clock);
		seesawTable = new SeesawTable(numberFont, ballTable);
		locationController.setLocationToObject(seesawTable);
		effectCatalog.setSound(wup, particleEffects.EXPLOSION);
		effectCatalog.setSound(shrinc, particleEffects.SHRINC);
		effectCatalog.setSound(klack1, particleEffects.BOUNCING);
		ballKiller = new BallKiller(mechanics, highScoreCounter, effectCatalog);
		ballFactory = new BallFactory(this, ballTable, ballsToMove, ballFont,
				new SpriteSheet[] { balls1, balls2 }, effectCatalog, canon, levelBall);
		scorePopups = new SObjectList();


		pause = new Pause(pauseFont, container.getWidth(), container.getHeight());
		pause.setVisible(false);
		reset.add(ballTable);
		reset.add(gui);
		reset.add(ballsToMove);
		reset.add(scorePopups);
		reset.add(effectCatalog);

		gui.add(canon);
		gui.add(clock);
		gui.add(seesawTable);
		gui.add(levelBall);
		gui.add(ballCounter);
		gui.add(multiplicator);
		gui.add(highScoreCounter);
		gui.add(highScore);

		boolean activateAI = false;
		if (activateAI && gameLocation == GameComponentLocation.CENTER) {
			ai = new AIInterface(this, ballTable, canon);
		}
		highScoreState = new NiftyGameState(XSwing.GAME_OVER);
		highScoreState.init(container, game);
		container.getInput().removeListener(highScoreState);
		container.getInput().addListener(highScoreState);
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
		music.loop();
	}

	/** Resets all values and starts a new game */
	public void newGame() {
		Log.info("New Game");
		reset.reset();
		ballsToMove.clear();
		ballFactory.addTopBalls();
		container.setPaused(false);
		resetInput();
		fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_SARTED));
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
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		gui.render(g);
		ballsToMove.render(g);
		effectCatalog.render(g);
		scorePopups.render(g);
		pause.render(g);
		if (isGameOver) {
			highScoreState.render(container, game, g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		checkKeys(input);

		if (!container.isPaused()) {
			gui.update(delta);
			effectCatalog.update(delta);
			scorePopups.update(delta);
			if (mechanics.checkHight()) {
				gameOver(game);
			}
			ballKiller.update(delta);
			ballFactory.updateBalls(delta);
			if (ai != null) {
				ai.update(delta);
			}
		}
		if (highScoreState != null) {
			highScoreState.update(container, game, delta);
		}
	}

	/**
	 * Performs all KeyEvents
	 * 
	 * @param input GameInput
	 */
	private void checkKeys(Input input) {
		if (!isGameOver) {
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
			if (input.isKeyPressed(Input.KEY_N)) {
				fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_STOPPED));
				newGame();
			}
			if (input.isKeyDown(Input.KEY_ESCAPE)) {
				fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_STOPPED));
				if (game.getCurrentState().getID() == 2) { // Game is started with Menue
					System.out.println("getID() == 2");
					// input.pause(); // have to be resumed in MainMenu
					game.enterState(1);
				} else {
					// Game is started without Menue
					System.out.println("container.exit();");
					container.exit();
				}
			}
			if (!container.isPaused()) { // no Input while game is paused
				if (input.isKeyPressed(keyLeft)) {
					notifyListener(new XSwingEvent(this, GameEventType.CANNON_MOVED_LEFT));
				}
				if (input.isKeyPressed(keyRight)) {
					notifyListener(new XSwingEvent(this, GameEventType.CANNON_MOVED_RIGHT));
				}
				if (input.isKeyPressed(keyDown)) {
					notifyListener(new XSwingEvent(this, GameEventType.PRESSED_DOWN));
				}
				if (input.isKeyPressed(Input.KEY_J)) {
					ballFactory.addNewJoker();
				}
				if (input.isKeyPressed(Input.KEY_K)) {
					if (ballDropSimulator == null) {
						ballDropSimulator = new BallDropSimulator();
					}
					ballDropSimulator.setBallTable(ballTable.clone());
				}
				if (input.isKeyPressed(Input.KEY_E)) {
					effectCatalog.setShowParticles(!effectCatalog.isShowParticles());
				}
				if (input.isKeyPressed(Input.KEY_B)) {
					ballFactory.toggleSpriteSheet();
				}
				if (input.isKeyPressed(Input.KEY_M)) {
					ballFactory.addNewStone();
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
				if (input.isKeyPressed(Input.KEY_1)) {
					canon.getBall().setNr(0);
				}
				if (input.isKeyPressed(Input.KEY_2)) {
					canon.getBall().setNr(1);
				}
				if (input.isKeyPressed(Input.KEY_2)) {
					canon.getBall().setNr(2);
				}
				if (input.isKeyPressed(Input.KEY_3)) {
					canon.getBall().setNr(3);
				}
				if (input.isKeyPressed(Input.KEY_4)) {
					canon.getBall().setNr(4);
				}
				if (input.isKeyPressed(Input.KEY_5)) {
					canon.getBall().setNr(5);
				}
				if (input.isKeyPressed(Input.KEY_F2)) {
					try {
						container.setFullscreen(!container.isFullscreen());
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}




	/**
	 * Finishes the current game and swichtes to the highScoreTable
	 * 
	 * @param container
	 * @param game
	 * @throws SlickException
	 */
	private void gameOver(StateBasedGame game) throws SlickException {
		isGameOver = true;
		Log.info("Game Over");
		scoreScreenController.setHighScore(highScoreCounter.getScore());
		fireXSwingEvent(new XSwingEvent(this, GameEventType.GAME_OVER));
		highScoreState.fromXml("xswing/gui/high_score.xml", scoreScreenController);
		highScoreState.enter(container, game);
		container.setPaused(true);
	}

	/**
	 * Clears all inputs. Useful to prevent keyEvents during pause mode or while entering the
	 * HighScore.
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
		if (e.getBallEventType() == BallEventType.BALL_HITS_GROUND
				|| e.getBallEventType() == BallEventType.BALL_HITS_BALL) {
			mechanics.checkOfFive(e.getBall());
			mechanics.checkOfThree(e.getBall());
		} else if (e.getBallEventType() == BallEventType.BALL_EXPLODED) {
			scorePopups.add(new ScorePopup(fontScore, e.getBall().getX(), e.getBall().getY(),
					highScoreCounter.getBonus() + ""));
		} else if (e.getBallEventType() == BallEventType.BALL_WITH_THREE_IN_A_ROW) {
			e.getBall().addBallEventListener(ballKiller);
			ballKiller.addBall(e.getBall());
		} else if (e.getBallEventType() == BallEventType.BALL_CAUGHT_BY_EXPLOSION) {
			Point field = ballTable.getField(e.getBall());
			ballTable.setBall(field.x, field.y, null);
			ballsToMove.remove(e.getBall());
		} else if (e.getBallEventType() == BallEventType.BALL_CAUGHT_BY_SHRINC) {
			effectCatalog.addEffect(e.getBall(), particleEffects.SHRINC);
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
	 * Notifies all {@code BallEventListener}s about a {@code BallEvent}
	 * 
	 * @param event the {@code BallEvent} object
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

}