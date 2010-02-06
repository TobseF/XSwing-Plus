/*
 * @version 0.0 25.02.2009
 * @author Tobse F
 */
package xswing;

import lib.mylib.object.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import xswing.LocationController.GameComponentLocation;
import xswing.events.XSwingEvent;
import xswing.events.XSwingEvent.GameEventType;

public class GamePanel extends BasicGameState implements Resetable {

	private MainGame singlePlayer, multiPlayer1, multiPlayer2;
	private Background background;
	private boolean multiplayer = false;

	private boolean coopertive = false; // TODO: implement meue entry for coopertive mode
	private final int scoreStep = 1000;
	private int scorePlayer1 = scoreStep, scorePlayer2 = scoreStep;
	private GameRecorder gameRecorder;
	private GameStatistics statistics;

	public GamePanel(int id) {
		super(id);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		gameRecorder = new GameRecorder();
		statistics = new GameStatistics();

		// make sure all graphics are loaded
		background = new Background(true);
		background = new Background(false);
		singlePlayer = new MainGame(GameComponentLocation.CENTER);

		multiPlayer1 = new MainGame(GameComponentLocation.LEFT);
		singlePlayer.init(container, game);
		multiPlayer1.init(container, game);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.render(g);

		if (!multiplayer) {
			singlePlayer.render(container, game, g);
		} else {
			multiPlayer1.render(container, game, g);
			multiPlayer2.render(container, game, g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		gameRecorder.update(delta);
		if (!multiplayer) {
			singlePlayer.update(container, game, delta);
		} else {
			multiPlayer1.update(container, game, delta);
			multiPlayer2.update(container, game, delta);

			if (multiPlayer1.getScore() >= scorePlayer1) {
				if (coopertive) {
					multiPlayer2.addJoker();
				} else {
					multiPlayer2.addStone();
					multiPlayer2.fireXSwingEvent(new XSwingEvent(this,
							GameEventType.PRESSED_DOWN));
				}
				scorePlayer1 += scoreStep;
			}

			if (multiPlayer2.getScore() >= scorePlayer2) {
				if (coopertive) {
					multiPlayer1.addJoker();
				} else {
					multiPlayer1.addStone();
					multiPlayer1.fireXSwingEvent(new XSwingEvent(this,
							GameEventType.PRESSED_DOWN));
				}
				scorePlayer2 += scoreStep;
			}
		}
	}

	private void initGame(boolean multiplayer, GameContainer container, StateBasedGame game)
			throws SlickException {
		background = new Background(multiplayer);
		if (!multiplayer) {
			singlePlayer = new MainGame(GameComponentLocation.CENTER);
			singlePlayer.init(container, game);
			singlePlayer.addXSwingListener(gameRecorder);
			singlePlayer.addXSwingListener(statistics);
			gameRecorder.play();
			singlePlayer.enter(container, game);
		} else {
			multiPlayer1 = new MainGame(GameComponentLocation.LEFT);
			multiPlayer1.setKeys(Input.KEY_A, Input.KEY_D, Input.KEY_S);
			multiPlayer1.init(container, game);
			multiPlayer1.enter(container, game);
			multiPlayer2 = new MainGame(GameComponentLocation.RIGHT);
			multiPlayer2.init(container, game);
			multiPlayer2.enter(container, game);
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		container.setMinimumLogicUpdateInterval(26);
		container.setMaximumLogicUpdateInterval(26);
		multiplayer = LocationController.isMultiplayer();
		initGame(multiplayer, container, game);
	}

	@Override
	public void reset() {
		singlePlayer.reset();
		// multiPlayer1.reset();
		// multiPlayer2.reset();
	}

}