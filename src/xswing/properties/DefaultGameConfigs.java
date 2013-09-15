package xswing.properties;

import static lib.mylib.options.Paths.SOUND_DIR;

import java.awt.Cursor;
import java.util.LinkedList;

import lib.mylib.Sound;
import lib.mylib.math.Point;
import lib.mylib.properties.DisplayConfig;
import lib.mylib.properties.GameConfig;
import lib.mylib.properties.GameConfigs;
import lib.mylib.properties.ObjectConfig;
import lib.mylib.properties.ObjectConfigSet;
import lib.mylib.properties.Resolution;
import lib.mylib.properties.SoundConfig;
import lib.mylib.util.Clock;
import xswing.Background;
import xswing.BallCounter;
import xswing.BallFont;
import xswing.Cannon;
import xswing.EffectCatalog;
import xswing.HighScoreCounter;
import xswing.HighScoreMultiplicator;
import xswing.HighScorePanel;
import xswing.Level;
import xswing.NumberFont;
import xswing.SeesawTable;
import xswing.ball.Ball;
import xswing.ball.BallFactory;
import xswing.ball.BallTable;

public class DefaultGameConfigs extends GameConfigs {
	private transient ObjectConfig cannon = new ObjectConfig(Cannon.class, 248, 166);
	private transient ObjectConfig background = new ObjectConfig(Background.class);
	private transient ObjectConfig cusor = new ObjectConfig(Cursor.class);
	private transient ObjectConfig ballFactory = new ObjectConfig(BallFactory.class);
	private transient ObjectConfig ball = new ObjectConfig(Ball.class, 48, 48);
	private transient ObjectConfig numberFont = new ObjectConfig(NumberFont.class);
	private transient ObjectConfig highScoreMultiplicator = new ObjectConfig(HighScoreMultiplicator.class, 59, 92);
	private transient ObjectConfig ballTable = new ObjectConfig(BallTable.class, 248, 289);
	private transient ObjectConfig highScoreCounter = new ObjectConfig(HighScoreCounter.class, 970, 106);
	private transient ObjectConfig level = new ObjectConfig(Level.class, 25, 15);
	private transient ObjectConfig ballCounter = new ObjectConfig(BallCounter.class, 160, 22);
	private transient ObjectConfig clock = new ObjectConfig(Clock.class, 85, 718);
	private transient ObjectConfig seesawTable = new ObjectConfig(SeesawTable.class, 228, 723);
	private transient ObjectConfig highScorePanel = new ObjectConfig(HighScorePanel.class, 980, 30);
	private transient ObjectConfig ballFont = new ObjectConfig(BallFont.class);
	private transient ObjectConfig effectCatalog = new ObjectConfig(EffectCatalog.class);

	@SuppressWarnings("serial")
	public DefaultGameConfigs() {

		GameConfig gameConfig = new GameConfig("DesktopPC");
		addGameConfig(gameConfig);
		gameConfig.setDescription("High resolution desktop system (Win, Linux or Mac)");
		gameConfig.setMinimumLogicUpdateInterval(26);
		gameConfig.setMusicPlayList(new LinkedList<String>() {
			{
				add("Song2.ogg");
				add("Song3.ogg");
				add("Song4.ogg");
			}
		});

		ObjectConfigSet singlePlayer = new ObjectConfigSet("SinglePlayer");
		gameConfig.addObjectConfigSet(singlePlayer);

		DisplayConfig displayConfig = new DisplayConfig();
		gameConfig.setDisplayConfig(displayConfig);
		displayConfig.setFullscreen(false);
		displayConfig.setResolutionIndex(0);
		displayConfig.setResolutions(new LinkedList<Resolution>() {
			{
				add(new Resolution(1024, 768));
			}
		});

		SoundConfig soundConfig = new SoundConfig();
		gameConfig.setSoundConfig(soundConfig);
		soundConfig.setFxVoulme(100);
		soundConfig.setMusicVolume(20);

		// GameObjects
		singlePlayer.addObjectConfig(cannon);
		cannon.setProperyInt("cannonPosition", 3);
		cannon.setProperyInt("millisecondsPerAnimationFrame", 180);
		cannon.addSound("move", "KRAN1.WAV");
		cannon.addSound("stackingAlarm", "ALARM1.WAV");
		cannon.addSound("dropBall", "KRAN1.WAV");

		cannon.addImage("SpriteSheet", "cannons.png");

		singlePlayer.addObjectConfig(background);
		background.setImage("swing_background_b.jpg");

		singlePlayer.addObjectConfig(ball);
//		ball.setProperyInt("fontCorrection", 16);
		ball.setProperyInt("speed", 20);
		ball.setPropery("font", "arial_black_71");

		singlePlayer.addObjectConfig(ballFactory);
		ballFactory.addImage("ballSet01", "balls1.png");
		ballFactory.addImage("ballSet02", "balls2.png");
		ballFactory.setProperyInt("gapBetweenBalls", 16);

		numberFont.setImage("fonts/"+"numberFont_s19.png");
		numberFont.setSize(15, 19);
		singlePlayer.addObjectConfig(numberFont);

		ballFont.setImage("fonts/"+"spriteFontBalls2.png");
		ballFont.setSize(11, 16);
		singlePlayer.addObjectConfig(ballFont);

		cusor.setImage("cursor.png");
		singlePlayer.addObjectConfig(cusor);

		singlePlayer.addObjectConfig(highScoreMultiplicator);
		highScoreMultiplicator.setImage("multiplicator_sp.jpg");

//		ballTable.setProperyInt("topBallYCorrection", 0);
		singlePlayer.addObjectConfig(ballTable);

		highScoreCounter.setProperyInt("bonusLineSpace", 42);

		singlePlayer.addObjectConfig(level);

		singlePlayer.addObjectConfig(ballCounter);

		singlePlayer.addObjectConfig(seesawTable);

		singlePlayer.addObjectConfig(highScoreCounter);

		singlePlayer.addObjectConfig(highScorePanel);

		singlePlayer.addObjectConfig(clock);
		
		singlePlayer.addObjectConfig(effectCatalog);
		effectCatalog.addSound("explosion", "DREIER.WAV");
		effectCatalog.addSound("shrinc", "SPRATZ2.WAV");
		effectCatalog.addSound("bouncing", "KLACK4.WAV");
		
	}

	public static void main(String[] args) {
		System.out.println(new DefaultGameConfigs().toJson());
	}

}
