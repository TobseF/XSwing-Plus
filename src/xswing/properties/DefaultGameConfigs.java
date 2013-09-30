package xswing.properties;

import static lib.mylib.options.Paths.SOUND_DIR;

import java.awt.Cursor;
import java.util.ArrayList;
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
import xswing.GameOver;
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
	private transient  ObjectConfig cannon
									, background
									, cusor
									, ballFactory
									, ball
									, numberFont
									, highScoreMultiplicator
									, ballTable
									, highScoreCounter
									, level
									, ballCounter
									, clock
									, seesawTable
									, highScorePanel
									, ballFont
									, effectCatalog,gameOver;
	
	private transient int resolutionIdex=1,configIndex=1;

	@SuppressWarnings("serial")
	public DefaultGameConfigs() {

		GameConfig gameConfig = new GameConfig("DesktopPC");
		addGameConfig(gameConfig);
		gameConfig.setConfigSetIndex(configIndex);
		gameConfig.setDescription("High resolution desktop system (Win, Linux or Mac)");
		gameConfig.setMinimumLogicUpdateInterval(26);
		gameConfig.setMusicPlayList(new LinkedList<String>() {
			{
				add("Song2.ogg");
			}
		});

		DisplayConfig displayConfig = new DisplayConfig();
		gameConfig.setDisplayConfig(displayConfig);
		displayConfig.setFullscreen(false);
		displayConfig.setResolutionIndex(resolutionIdex);
		displayConfig.setResolutions(new LinkedList<Resolution>() {
			{
				add(new Resolution(1024, 768, "XGA"));
				add(new Resolution(1920, 1080, "Full-HD"));
			}
		});

		SoundConfig soundConfig = new SoundConfig();
		gameConfig.setSoundConfig(soundConfig);
		soundConfig.setFxVoulme(100);
		soundConfig.setMusicVolume(20);

		// GameObjects
		ObjectConfigSet singlePlayerSD = new ObjectConfigSet("SinglePlayer SD");
		gameConfig.addObjectConfigSet(singlePlayerSD);
		initSingleSplayerSD(singlePlayerSD);

		ObjectConfigSet singlePlayerHD = new ObjectConfigSet("SinglePlayer HD");
		gameConfig.addObjectConfigSet(singlePlayerHD);
		initSingleSplayerHD(singlePlayerHD);
	}

	private void initSingleSplayerHD(ObjectConfigSet singlePlayer) {
		initObjects();
//		cannon.setPosition(610, 231);  FIXME:gapBetweenBalls-20 bug
		cannon.setBounds(610-20, 231,72,110);
		cannon.setProperyInt("cannonPosition", 3);
		cannon.setProperyInt("millisecondsPerAnimationFrame", 180);
		cannon.addImage("spriteSheet", "cannons_hd.png");
		cannon.setProperyInt("positionCorrecionX", 0);
		cannon.setProperyInt("positionCorrecionY", 0);
		singlePlayer.addObjectConfig(cannon);

		background.setImage("background_preview_hd.png");
		singlePlayer.addObjectConfig(background);

		ball.setSize(70, 70);
		ball.setProperyInt("A", 70);
		// ball.setProperyInt("fontCorrection", 16);
		ball.setProperyInt("speed", 20);
//		ball.setPropery("font", "arial_black_71");
		singlePlayer.addObjectConfig(ball);

		ballFactory.addImage("ballSet01", "balls1_hd.png");
		singlePlayer.addObjectConfig(ballFactory);

		numberFont.setImage("fonts/" + "numberFont_s19.png");
		numberFont.setSize(15, 19);
		singlePlayer.addObjectConfig(numberFont);

		ballFont.setImage("fonts/" + "spriteFontBalls2.png");
		ballFont.setSize(11, 16);
		singlePlayer.addObjectConfig(ballFont);

		cusor.setImage("cursor.png");
		singlePlayer.addObjectConfig(cusor);

		singlePlayer.addObjectConfig(highScoreMultiplicator);
		highScoreMultiplicator.setPosition(145, 145);
		highScoreMultiplicator.setImage("multiplicator_sp.jpg");
		highScoreMultiplicator.addSound("score", "KLACK4.WAV");

		ballTable.setProperyInt("topBallYCorrection", 35);
		singlePlayer.addObjectConfig(ballTable);
//		ballTable.setPosition(610, 394); FIXME:gapBetweenBalls-20 bug
		ballTable.setPosition(610-20, 394);
		ballTable.setProperyInt("gapBetweenBalls", 20);

		singlePlayer.addObjectConfig(highScoreCounter);
		highScoreCounter.setProperyInt("bonusLineSpace", 42);
		highScoreCounter.setPosition(1483, 0);
		singlePlayer.addObjectConfig(highScorePanel);
		highScorePanel.setPosition(1497, 14);

		singlePlayer.addObjectConfig(ballCounter);
		ballCounter.setPosition(145, 20);

		singlePlayer.addObjectConfig(level);
		level.setPosition(315, 20);

		singlePlayer.addObjectConfig(seesawTable);
		seesawTable.setPosition(610, 1013);

		singlePlayer.addObjectConfig(clock);
		clock.setPosition(250, 986);

		singlePlayer.addObjectConfig(effectCatalog);
		
		singlePlayer.addObjectConfig(gameOver);
		
		addSounds();
	}

	private void initSingleSplayerSD(ObjectConfigSet singlePlayer) {
		initObjects();

		singlePlayer.addObjectConfig(cannon);
		cannon.setBounds(248, 166,72,110);
		cannon.setProperyInt("cannonPosition", 3);
		cannon.setProperyInt("millisecondsPerAnimationFrame", 180);
		
		cannon.setProperyInt("positionCorrecionX", 12);
		cannon.setProperyInt("positionCorrecionY", 3);

		cannon.addImage("spriteSheet", "cannons.png");

		singlePlayer.addObjectConfig(background);
		background.setImage("swing_background_b.jpg");

		singlePlayer.addObjectConfig(ball);
		ball.setSize(48, 48);
		ball.setProperyInt("A", 48);
		// ball.setProperyInt("fontCorrection", 16);
		ball.setProperyInt("speed", 20);
//		ball.setPropery("font", "arial_black_71");

		singlePlayer.addObjectConfig(ballFactory);
		ballFactory.addImage("ballSet01", "balls1.png");
		ballFactory.addImage("ballSet02", "balls2.png");

		numberFont.setImage("fonts/" + "numberFont_s19.png");
		numberFont.setSize(15, 19);
		singlePlayer.addObjectConfig(numberFont);

		ballFont.setImage("fonts/" + "spriteFontBalls2.png");
		ballFont.setSize(11, 16);
		singlePlayer.addObjectConfig(ballFont);

		cusor.setImage("cursor.png");
		singlePlayer.addObjectConfig(cusor);

		singlePlayer.addObjectConfig(highScoreMultiplicator);
		highScoreMultiplicator.setImage("multiplicator_sp.jpg");
		highScoreMultiplicator.setPosition(59, 92);


		// ballTable.setProperyInt("topBallYCorrection", 0);
		ballTable.setProperyInt("gapBetweenBalls", 16);
		ballTable.setPosition(248, 289);
		singlePlayer.addObjectConfig(ballTable);

		singlePlayer.addObjectConfig(highScoreCounter);
		highScoreCounter.setProperyInt("bonusLineSpace", 42);
		highScoreCounter.setPosition(970, 106);

		singlePlayer.addObjectConfig(level);
		level.setPosition(25, 15);

		singlePlayer.addObjectConfig(ballCounter);
		ballCounter.setPosition(160, 22);

		singlePlayer.addObjectConfig(seesawTable);
		seesawTable.setPosition(228, 723);

		singlePlayer.addObjectConfig(highScorePanel);
		highScorePanel.setPosition(980, 30);

		singlePlayer.addObjectConfig(clock);
		clock.setPosition(85, 718);

		singlePlayer.addObjectConfig(effectCatalog);
				
		singlePlayer.addObjectConfig(gameOver);
		
		addSounds();
	}

	private void addSounds() {
		cannon.addSound("move", "KRAN1.WAV");
		cannon.addSound("stackingAlarm", "ALARM1.WAV");
		cannon.addSound("dropBall", "abwurf.WAV");
		
		highScoreMultiplicator.addSound("score1x","Score1x.wav");
		highScoreMultiplicator.addSound("score2x","Score2x.wav");
		highScoreMultiplicator.addSound("score3x","Score3x.wav");
		highScoreMultiplicator.addSound("score4x","Score4x.wav");
		
		effectCatalog.addSound("explosion", "DREIER.WAV");
		effectCatalog.addSound("shrinc", "SPRATZ2.WAV");
		effectCatalog.addSound("bouncing", "KLACK4.WAV");
		
		gameOver.addSound("gameOver", "SPRATZ2.WAV");
	}

	private void initObjects() {
		cannon = new ObjectConfig(Cannon.class);
		background = new ObjectConfig(Background.class);
		cusor = new ObjectConfig(Cursor.class);
		ballFactory = new ObjectConfig(BallFactory.class);
		ball = new ObjectConfig(Ball.class);
		numberFont = new ObjectConfig(NumberFont.class);
		highScoreMultiplicator = new ObjectConfig(HighScoreMultiplicator.class);
		ballTable = new ObjectConfig(BallTable.class);
		highScoreCounter = new ObjectConfig(HighScoreCounter.class);
		level = new ObjectConfig(Level.class);
		ballCounter = new ObjectConfig(BallCounter.class);
		clock = new ObjectConfig(Clock.class);
		seesawTable = new ObjectConfig(SeesawTable.class);
		highScorePanel = new ObjectConfig(HighScorePanel.class);
		ballFont = new ObjectConfig(BallFont.class);
		effectCatalog = new ObjectConfig(EffectCatalog.class);
		gameOver= new ObjectConfig(GameOver.class);
	}

	public static void main(String[] args) {
		System.out.println(new DefaultGameConfigs().toJson());
	}
}
