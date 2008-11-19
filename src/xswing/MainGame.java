/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package xswing;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import lib.mylib.BasicGameState;
import lib.mylib.Reset;
import lib.mylib.Resetable;
import lib.mylib.SObject;
import lib.mylib.Sound;
import lib.mylib.SpriteSheet;

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

import xswing.extras.ExtraJoker;
import xswing.gui.ScoreScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;

/** The main container class, which combines all container elements */
public class MainGame extends BasicGameState implements Resetable {
	public MainGame(int id) {
		super(id);
	}

	private GameContainer container = null;
	private StateBasedGame game = null;
	private int widht, height;

	public static final String RES = "res/";
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
	private List<Ball> ballsToMove = new ArrayList<Ball>();
	private List<SObject> gui = new ArrayList<SObject>();

	private Image background;
	private SpriteSheet balls, balls1, balls2, multipl, cannons;
	private SpriteSheetFont font, ballFont;
	private AngelCodeFont fontText;
	private Sound klack1, kran1, wup, shrinc, warning;
	private Music music;

	/**
	 * Current scale of the graphics
	 */
	private float graphicsScale = 1f;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		container.setSoundVolume(0.5f); // calm down the Effect Sounds
		height = container.getHeight();
		widht = container.getWidth();
		// Images
		background = new Image(RES + "swing_background_b.jpg");
		// background=new BigImage(RES+"swing_background_b.jpg",
		multipl = new SpriteSheet(new Image(RES + "multiplicator_sp.jpg"), 189, 72);
		cannons = new SpriteSheet(new Image(RES + "cannons.png"), 72, 110);
		balls1 = new SpriteSheet(new Image(RES + "balls1.png"), Ball.A, Ball.A);
		balls2 = new SpriteSheet(new Image(RES + "balls2.png"), Ball.A, Ball.A);
		balls = balls1;
		fontText = new AngelCodeFont(RES + "font_arial_16_bold.fnt", RES + "font_arial_16_bold.png");
		font = new SpriteSheetFont(new SpriteSheet(new Image(RES + "numberFont_s19.png"), 15,19), '.');
		ballFont = new SpriteSheetFont(new SpriteSheet(
				new Image(RES + "spriteFontBalls2.png"), 11, 16), '.');
		// Sounds
		klack1 = new Sound(RES + "KLACK4.WAV");
		kran1 = new Sound(RES + "KRAN1.WAV");
		wup = new Sound(RES + "DREIER.WAV");
		wup.setMaxPlyingTime(1000);
		shrinc = new Sound(RES + "SPRATZ2.WAV");
		warning = new Sound(RES + "ALARM1.WAV");
		music = new Music(RES + "music.mod");
		// Objects
		effectCatalog = new EffectCatalog();
		reset = new Reset();
		ballTable = new BallTable(248, 289);
		mechanics = new Mechanics(ballTable);
		timer = new Clock(font, 85, 718);
		canon = new Cannon(cannons, 248, 166, new Sound[] { kran1, warning }, ballTable);
		multiplicator = new HighScoreMultiplicator(59, 92, multipl);
		highScoreCounter = new HighScoreCounter(font, 970, 106, multiplicator);
		highScore = new HighScore(980, 30, fontText, "properties.txt");
		seesawTable = new SeesawTable(font, ballTable);
		seesawTable.setPos(285, 723);
		levelBall = new Level(3, 25, 15, balls);
		levelBall.setFont(ballFont);
		ballCounter = new BallCounter(ballFont, 160, 22);
		ballCounter.setLevel(levelBall);
		effectCatalog.setSound(wup, EffectCatalog.effectExplosion);
		ballKiller = new BallKiller(mechanics, highScoreCounter, effectCatalog);

		reset.add(timer);
		reset.add(ballCounter);
		reset.add(levelBall);
		reset.add(highScoreCounter);
		reset.add(ballTable);
		reset.add(effectCatalog);
		reset.add(multiplicator);
		reset.add(canon);
		reset.add(highScore);

		gui.add(canon);
		gui.add(timer);
		gui.add(seesawTable);
		gui.add(levelBall);
		gui.add(ballCounter);
		gui.add(multiplicator);
		gui.add(highScoreCounter);
		gui.add(highScore);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		newGame();
		this.game = game;
		this.container = container;
		if (!music.playing()) {
			music.loop();
		}
	}

	/** Resets all values and starts a new game */
	public void newGame() {
		System.out.println("ResetGame");
		reset.reset();

		ballsToMove.clear();
		addTopBalls();
		container.setPaused(false);

		container.getInput().clearControlPressedRecord();
		container.getInput().clearKeyPressedRecord();
		container.getInput().clearMousePressedRecord();
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
		g.scale(graphicsScale, graphicsScale);
		// g.setAntiAlias(true);
		g.drawImage(background, 0, 0);
		for (int i = 0; i < gui.size(); i++) {
			gui.get(i).draw(g);
		}
		for (int i = 0; i < ballsToMove.size(); i++) {
			ballsToMove.get(i).draw(g);
		}
		effectCatalog.draw(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		checkKeys(input);

		if (!container.isPaused()) {
			ListIterator<SObject> gui = this.gui.listIterator(0);
			while (gui.hasNext()) {
				gui.next().update(delta);
			}
			effectCatalog.update(delta);
			mechanics.checkOfFive();
			mechanics.checkOfThree();
			if (mechanics.checkHight()) {
				gameOver(container, game);
			}
			ballKiller.update(delta);
			updateBalls(delta);
		}
	}

	private void updateBalls(int delta) {
		for (int i = 0; i < ballsToMove.size(); i++) {
			Ball b = ballsToMove.get(i);
			if (b.isReadyToKill()) {
				if (b.getStateType() == Ball.STATE_WAITING_FOR_KILL) {
					ballKiller.addBall(b);
					effectCatalog.addEffect(b, EffectCatalog.effectExplosion);
					b.setStateType(Ball.STATE_KILLING_STARTED);
				}
				if (b.getStateType() == Ball.STATE_WAITING_FOR_SHRINK) {
					shrinc.play();
					effectCatalog.addEffect(b, EffectCatalog.efectShrinc);
					b.setStateType(Ball.STATE_ALIVE);
				}
				if (b.getStateType() == Ball.STATE_KILL_IMMEDIATELY) {
					//effectCatalog.addEffect(b, EffectCatalog.effectExplosion);
					int[] field = ballTable.getField(b);
					ballTable.setBall(field[0], field[1], null);
					ballsToMove.remove(i);
				}
			} else {
				b.update(delta);
			}
		}
	}

	private void checkKeys(Input input) {
		if (input.isKeyPressed(Input.KEY_P)) {
			container.setPaused(!container.isPaused());
		}
		if (input.isKeyPressed(Input.KEY_N)) {
			newGame();
		}

		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			if (getID() == 2) { // Game is started with Menue
				input.pause(); // have to be resumed in MainMenu
				game.enterState(1);
			} else {
				// Game is started without Menue
				container.exit();
			}
		}

		if (!container.isPaused()) { // no Input while game is paused

			if (input.isKeyPressed(Input.KEY_LEFT)) {
				canon.moveLeft();
			}
			if (input.isKeyPressed(Input.KEY_RIGHT)) {
				canon.moveRight();
			}
			if (input.isKeyPressed(Input.KEY_DOWN)) {
				addNewBall();
			}
			if (input.isKeyPressed(Input.KEY_J)) {
				addNewJoker();
			}
			if (input.isKeyPressed(Input.KEY_E)) {
				effectCatalog.setShowParticles(!effectCatalog.isShowParticles());
			}
			if (input.isKeyPressed(Input.KEY_B)) {
				balls = balls.equals(balls1) ? balls2 : balls1;
				newGame();
				levelBall.setBallsSpriteSheet(balls);
			}
			if (input.isKeyPressed(Input.KEY_F)) {
				container.setShowFPS(!container.isShowingFPS());
			}
			if (input.isKeyPressed(Input.KEY_S)) {
				shrinkGame(game);
			}
			if (input.isKeyPressed(Input.KEY_H)) {
				highScore.setVisible(!highScore.isVisible());
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

	private void shrinkGame(StateBasedGame game) {
		graphicsScale = graphicsScale > 0.5 ? graphicsScale - 0.1f : 1;
		try {
			((AppGameContainer) container).setDisplayMode((int) (widht * graphicsScale),
					(int) (height * graphicsScale), false);
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	private void addNewBall() {
		if (canon.isReadyToReleaseBall()) {
			Ball ball = getNewBall(canon.getX(), canon.getY());
			canon.releaseBall(ball);
			effectCatalog.addEffect(canon.getBall(), EffectCatalog.efectFlash);
			ballCounter.count();
		}
	}

	private Ball getNewBall(int x, int y) {
		Ball ball = new Ball(levelBall.getLevel(), x, y, balls);
		ball.setGrid(ballTable);
		ball.setFont(ballFont);
		ball.setCollsionSound(klack1);
		ball.setEffects(effectCatalog);
		ballsToMove.add(ball);
		return ball;
	}

	private void addNewJoker() {
		ExtraJoker ball = new ExtraJoker(1, canon.getX(), canon.getY());
		ball.setGrid(ballTable);
		ball.setCollsionSound(klack1);
		ballsToMove.remove(canon.getBall());
		ballsToMove.add(ball);
		canon.setBall(ball);
		effectCatalog.addEffect(canon.getBall(), EffectCatalog.efectFlash);
		ballCounter.count();
	}

	private void addTopBalls() {
		for (int row = 12; row > 10; row--) {
			for (int column = 0; column < 8; column++) {
				int[] pos = ballTable.getFieldPosOnScreen(column, row);
				Ball newBall = getNewBall(pos[0], pos[1]);
				ballTable.setBall(column, row, newBall);
			}
		}
	}

	private void gameOver(GameContainer container, StateBasedGame game) {
		System.out.println("Game Over !");
		NiftyGameState highScoreState = new NiftyGameState(3);
		highScoreState.fromXml("res/gui/HighScore.xml", new ScoreScreenController(game,
				highScoreCounter.getScore(), highScore));
		game.addState(highScoreState);
		game.enterState(3);
	}
}