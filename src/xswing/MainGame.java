/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package xswing;

import java.awt.Point;

import javax.swing.event.EventListenerList;

import lib.mylib.Sound;
import lib.mylib.SpriteSheet;
import lib.mylib.object.BasicGameState;
import lib.mylib.object.Reset;
import lib.mylib.object.Resetable;
import lib.mylib.object.SObjectList;
import lib.mylib.util.Clock;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.state.StateBasedGame;

import tools.BallDropSimulator;
import xswing.EffectCatalog.particleEffects;
import xswing.LocationController.GameComponentLocation;
import xswing.ai.AIInterface;
import xswing.events.BallEvent;
import xswing.events.BallEventListener;
import xswing.events.XSwingEvent;
import xswing.events.XSwingListener;
import xswing.events.BallEvent.BallEventType;
import xswing.events.XSwingEvent.GameEventType;
import xswing.gui.ScoreScreenController;
import xswing.start.XSwing;
import de.lessvoid.nifty.slick.NiftyGameState;

/** 
 * The main container class, which combines all container elements
 * @author Tobse
 */
public class MainGame extends BasicGameState implements Resetable, BallEventListener, XSwingListener{
	public MainGame(GameComponentLocation gameLocation) {
		this.gameLocation = gameLocation;
		//super(id);
	}
	private GameComponentLocation gameLocation;
	private GameContainer container = null;
	private StateBasedGame game = null;
	/** Sceensize in weight height */
	private int widht, height;
	/** Folder with resources */
	public static final String RES = "res/";
	private int keyLeft = Input.KEY_LEFT, 
		keyRight = Input.KEY_RIGHT, 
		keyDown = Input.KEY_DOWN;
	private LocationController locationController;
	private EffectCatalog effectCatalog;
	private Cannon canon;
	private Clock timer;
	private BallTable ballTable;
	private Mechanics mechanics;
	private SeesawTable seesawTable;
	private BallCounter ballCounter;
	private HighScoreCounter highScoreCounter;
	private HighScoreMultiplicator multiplicator;
	private HighScore highScore;
	private BallKiller ballKiller;
	private Level levelBall;
	private Reset reset;
	private SObjectList ballsToMove;
	private SObjectList gui;
	private BallFactory ballFactory;
	private SObjectList scorePopups;

	private SpriteSheet balls1, balls2, multipl, cannons;
	private SpriteSheetFont numberFont, ballFont;
	private AngelCodeFont fontText, fontScore, pauseFont;
	private Sound klack1, kran1, wup, shrinc, warning;
	private Music music;
	private Pause pause;
	
	private AIInterface ai;
	private BallDropSimulator ballDropSimulator;
	
	private EventListenerList gameEventListeners;
	/** Current scale of the graphics */
	private float graphicsScale = 1f;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		container.setSoundVolume(0.5f); // calm down the Effect Sounds
		height = container.getHeight();
		widht = container.getWidth();
		// Images
		//background = new Image(RES + "swing_background_b.jpg");
		// background=new BigImage(RES+"swing_background_b.jpg",
		multipl = new SpriteSheet(new Image(RES + "multiplicator_sp.jpg"), 189, 72);
		cannons = new SpriteSheet(new Image(RES + "cannons.png"), 72, 110);
		balls1 = new SpriteSheet(new Image(RES + "balls1.png"), Ball.A, Ball.A);
		balls2 = new SpriteSheet(new Image(RES + "balls2.png"), Ball.A, Ball.A);
		fontText = new AngelCodeFont(RES + "font_arial_16_bold.fnt", 
				RES + "font_arial_16_bold.png");
		pauseFont = new AngelCodeFont(RES + "arial_black_71.fnt", 
				RES + "arial_black_71.png");
		fontScore = new AngelCodeFont(RES + "berlin_sans_fb_demi_38.fnt", 
				RES + "berlin_sans_fb_demi_38.png");
		numberFont = new SpriteSheetFont(new SpriteSheet(new Image(RES + "numberFont_s19.png"), 15, 19), ',');
		ballFont = new SpriteSheetFont(new SpriteSheet(
				new Image(RES + "spriteFontBalls2.png"), 11, 16), '.');
		// Sounds
		klack1 = new Sound(RES + "KLACK4.WAV");
		klack1.setMaxPlyingTime(5);
		kran1 = new Sound(RES + "KRAN1.WAV");
		wup = new Sound(RES + "DREIER.WAV");
		wup.setMaxPlyingTime(1000);
		shrinc = new Sound(RES + "SPRATZ2.WAV");
		shrinc.setMaxPlyingTime(5);
		warning = new Sound(RES + "ALARM1.WAV");
		music = new Music(RES + "music.mod", true);
		// Objects
		gameEventListeners = new EventListenerList();
		addBallEventListener(this);
		locationController = new LocationController(gameLocation);
		ballsToMove = new SObjectList();
		gui = new SObjectList();
		effectCatalog = new EffectCatalog();
		reset = new Reset();
		ballTable = new BallTable();
		locationController.setLocationToObject(ballTable);
		mechanics = new Mechanics(ballTable);
		mechanics.addBallEventListener(this);
		timer = new Clock(numberFont);
		locationController.setLocationToObject(timer);
		ballCounter = new BallCounter(ballFont);
		locationController.setLocationToObject(ballCounter);
		levelBall = new Level(4, balls1, ballFont);
		locationController.setLocationToObject(levelBall);
		ballCounter.setLevel(levelBall);
		canon = new Cannon(cannons, new Sound[] { kran1, warning }, 
				ballTable, ballCounter, effectCatalog);
		locationController.setLocationToObject(canon);
		multiplicator = new HighScoreMultiplicator(multipl);
		locationController.setLocationToObject(multiplicator);
		highScoreCounter = new HighScoreCounter(numberFont, multiplicator);
		locationController.setLocationToObject(highScoreCounter);
		highScore = new HighScore(fontText, "properties.txt");
		locationController.setLocationToObject(highScore);
		seesawTable = new SeesawTable(numberFont, ballTable);
		locationController.setLocationToObject(seesawTable);
		effectCatalog.setSound(wup, particleEffects.EXPLOSION);
		effectCatalog.setSound(shrinc, particleEffects.SHRINC);
		effectCatalog.setSound(klack1, particleEffects.BOUNCING);
		ballKiller = new BallKiller(mechanics, highScoreCounter, effectCatalog);
		ballFactory = new BallFactory(this, ballTable, ballsToMove, ballFont, 
				new SpriteSheet[]{balls1, balls2}, effectCatalog, 
				canon, levelBall);
		scorePopups = new SObjectList();
		pause = new Pause(pauseFont, container.getWidth(), container.getHeight());
		pause.setVisible(false);
		reset.add(ballTable);
		reset.add(gui);
		reset.add(ballsToMove);
		reset.add(scorePopups);
		reset.add(effectCatalog);
		
		gui.add(canon);
		gui.add(timer);
		gui.add(seesawTable);
		gui.add(levelBall);
		gui.add(ballCounter);
		gui.add(multiplicator);
		gui.add(highScoreCounter);
		gui.add(highScore);
		
		boolean ki = false;
		if(ki && gameLocation == GameComponentLocation.LEFT)
			ai = new AIInterface(this, ballTable, canon);		
	}
	
	public void setKeys(int keyCodeLeft, int keyCodeRight, int keyCodeDown){
		this.keyLeft = keyCodeLeft;
		this.keyRight = keyCodeRight;
		this.keyDown = keyCodeDown;
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
		System.out.println("ResetGame");
		reset.reset();
		ballsToMove.clear();
		ballFactory.addTopBalls();
		container.setPaused(false);
		resetInput();
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
		//g.scale(graphicsScale, graphicsScale);
		//g.drawImage(background, 0, 0);
		gui.render(g);
		ballsToMove.render(g);
		effectCatalog.render(g);
		scorePopups.render(g);
		pause.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		checkKeys(input);

		if (!container.isPaused()) {
			gui.update(delta);
			effectCatalog.update(delta);
			//mechanics.checkOfFive();
			//mechanics.checkOfThree();
			scorePopups.update(delta);
			if (mechanics.checkHight()) {
				gameOver(game);
			}
			ballKiller.update(delta);
			ballFactory.updateBalls(delta);
		}
		if(ai !=null)
			ai.update(delta);
	}
	
	/** Performs all KeyEvents
	 * @param input GameInput
	 */
	private void checkKeys(Input input) {
		if (input.isKeyPressed(Input.KEY_P)) {
			container.setPaused(!container.isPaused());
			pause.setVisible(!pause.isVisible());
			resetInput();
		}
		if (input.isKeyPressed(Input.KEY_N)) {
			newGame();
		}
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			if (game.getCurrentState().getID() == 2) { // Game is started with Menue
				System.out.println("getID() == 2");
				input.pause(); // have to be resumed in MainMenu
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
				if(ballDropSimulator == null)
					ballDropSimulator = new BallDropSimulator();
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
				//shrinkGame(game);
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
	int scale = 1;
	int[] scales = new int[]{1024, 800, 640};
	/** Changes screensize beteen available screen sizes
	 * @param game game with <code>DisplayMode</code>
	 */
	
	public float getGraphicsScale() {
		return graphicsScale;
	}
	
	@SuppressWarnings("unused")
	private void shrinkGame(StateBasedGame game) {
		scale = scale == scales.length-1 ? 0 : scale + 1; 
		graphicsScale = scales[scale]/1024f;
		try {
			((AppGameContainer)container).setDisplayMode((int) (widht * graphicsScale),
					(int) (height * graphicsScale), container.isFullscreen());
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/** Finishes the current game and swichtes to the highScoreTable
	 * @param container
	 * @param game
	 */
	private void gameOver(StateBasedGame game) {
		System.out.println("Game Over !");
		container.setPaused(true);
		NiftyGameState highScoreState = new NiftyGameState(XSwing.GAME_OVER);
		highScoreState.fromXml(RES + "gui/HighScore.xml", new ScoreScreenController(game,
				highScoreCounter.getScore(), highScore));
		game.addState(highScoreState);
		game.enterState(XSwing.GAME_OVER);
		//game.addState(new EmptyState(getID()+1));
		//game.enterState(getID()+1);*/
	}
	
	
	/** Clears all inputs. Useful to prevent keyEvents during pause mode. */
	private void resetInput(){
		container.getInput().clearControlPressedRecord();
		container.getInput().clearKeyPressedRecord();
		container.getInput().clearMousePressedRecord();
	}

	@Override
	public void ballEvent(BallEvent e) {
		if(e.getBallEventType()== BallEventType.BALL_EXPLODED){
			scorePopups.add(new ScorePopup(fontScore, e.getBall().getX(), e.getBall().getY(),
					highScoreCounter.getBonus() + ""));
		}
		if(e.getBallEventType() == BallEventType.BALL_WITH_THREE_IN_A_ROW){
			e.getBall().addBallEventListener(ballKiller);
			ballKiller.addBall(e.getBall());
		}
		if(e.getBallEventType() == BallEventType.BALL_CAUGHT_BY_EXPLOSION){
			Point field = ballTable.getField(e.getBall());
			ballTable.setBall(field.x, field.y, null);
			ballsToMove.remove(e.getBall());
		}
		if(e.getBallEventType() == BallEventType.BALL_CAUGHT_BY_SHRINC){
			effectCatalog.addEffect(e.getBall(), particleEffects.SHRINC);
		}
		if(e.getBallEventType() == BallEventType.BALL_HITS_GROUND ||
				e.getBallEventType() == BallEventType.BALL_HITS_BALL){
				mechanics.checkOfFive(e.getBall());
				mechanics.checkOfThree(e.getBall());
		}
		
	}
	/**
	 * Adds an {@code BallEventListener} to the Ball.
	 * @param listener the {@code BallEventListener} to be added
	 */
	public void addBallEventListener(XSwingListener listener) {
		gameEventListeners.add(XSwingListener.class, listener);
	}

	/**
	 * Removes an {@code BallEventListener} from the Ball
	 * @param listener to be removed
	 */
	public void removeBallEventListener(XSwingListener listener) {
		gameEventListeners.remove(XSwingListener.class, listener);
	}

	/**
	 * Notifies all {@code BallEventListener}s about a {@code BallEvent}
	 * @param event the {@code BallEvent} object
	 * @see EventListenerList
	 */
	protected synchronized void notifyListener(XSwingEvent event) {
		for (XSwingListener l : gameEventListeners.getListeners(XSwingListener.class))
			l.gameEvent(event);
	}
	
	public void fireXSwingEvent(XSwingEvent event){
		notifyListener(event);
	}

	@Override
	public void gameEvent(XSwingEvent e) {
		if(e.getGameEventType() == GameEventType.CANNON_MOVED_LEFT)
			canon.moveLeft();
		if(e.getGameEventType() == GameEventType.CANNON_MOVED_RIGHT)
			canon.moveRight();
		if(e.getGameEventType() == GameEventType.PRESSED_DOWN){
			if(canon.isReadyToReleaseBall()){
				Ball ball = ballFactory.getNewBall();
				notifyListener(new XSwingEvent(this,GameEventType.BALL_DROPPED, ball));
			}
		}
		if(e.getGameEventType() == GameEventType.BALL_DROPPED){
			canon.releaseBall(e.getBall());
		}
	}
	
	public int getScore(){
		return highScoreCounter.getScore();
	}
	
	public void addJoker(){
		ballFactory.addNewJoker();
	}
	
	public void addStone(){
		ballFactory.addNewStone();
	}

}