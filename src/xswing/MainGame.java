package xswing;

import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import java.util.ArrayList;
import java.util.List;
import lib.mylib.BasicGameState;
import lib.mylib.Reset;
import lib.mylib.Resetable;
import lib.mylib.SObject;
import lib.mylib.Sound;
import lib.mylib.SpriteSheet;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.state.StateBasedGame;
import xswing.Ball;
import xswing.BallCounter;
import xswing.BallKiller;
import xswing.BallTable;
import xswing.Cannon;
import xswing.Clock;
import xswing.Effects;
import xswing.HighScoreCounter;
import xswing.HighScoreMultiplicator;
import xswing.Level;
import xswing.Mechanics;
import xswing.SeesawTable;
import xswing.extras.ExtraJoker;
import xswing.gui.ScoreScreenController;

public class MainGame
extends BasicGameState
implements Resetable {
    private GameContainer game = null;
    public static final String RES = "res/";
    Effects effects;
    Cannon canon;
    Clock timer;
    BallTable ballTable;
    Mechanics mechanics;
    SeesawTable seesawTable;
    BallCounter ballCounter;
    HighScoreCounter scoreCounter;
    HighScoreMultiplicator multiplicator;
    BallKiller ballKiller;
    Level levelBall;
    Reset reset;
    List<Ball> ballsToMove = new ArrayList<Ball>();
    List<SObject> gui = new ArrayList<SObject>();
    private Image background;
    private SpriteSheet balls;
    private SpriteSheet balls1;
    private SpriteSheet balls2;
    private SpriteSheet multipl;
    private SpriteSheet cannons;
    private SpriteSheetFont font;
    private SpriteSheetFont ballFont;
    private Sound klack1;
    private Sound kran1;
    private Sound wup;
    private Sound shrinc;
    private Sound warning;
    private Music music;
    private final int rasterX = 248;
    private final int rasterY = 289;
    private final int canonX = 248;
    private final int canonY = 166;

    public MainGame(int id) {
        super(id);
    }

    private void gameOver(GameContainer container, StateBasedGame game) {
        System.out.println("Game Over !");
        NiftyGameState highScore = new NiftyGameState(3);
        highScore.fromXml("res/gui/HighScore.xml", new ScreenController[]{new ScoreScreenController(game, this.scoreCounter.getScore())});
        game.addState(highScore);
        game.enterState(3);
    }

    private void addNewBall() {
        Ball ball = this.getNewBall(this.canon.getX(), this.canon.getY());
        this.canon.releaseBall(ball);
        this.effects.addEffect(this.canon.getBall(), 3);
        this.ballCounter.count();
    }

    private Ball getNewBall(int x, int y) {
        Ball ball = new Ball(this.levelBall.getLevel(), x, y, this.balls);
        ball.setGrid(this.ballTable);
        ball.setFont(this.ballFont);
        ball.setCollsionSound(this.klack1);
        ball.setEffects(this.effects);
        this.ballsToMove.add(ball);
        return ball;
    }

    private void addNewJoker() {
        ExtraJoker ball = new ExtraJoker(33, this.canon.getX(), this.canon.getY());
        ball.setGrid(this.ballTable);
        ball.setCollsionSound(this.klack1);
        this.ballsToMove.remove(this.canon.getBall());
        this.ballsToMove.add(ball);
        this.canon.setBall(ball);
        this.effects.addEffect(this.canon.getBall(), 3);
        this.ballCounter.count();
    }

    private void addTopBalls() {
        int row = 12;
        while (row > 10) {
            int column = 0;
            while (column < 8) {
                int[] pos = this.ballTable.getFieldPosOnScreen(column, row);
                Ball newBall = this.getNewBall(pos[0], pos[1]);
                this.ballTable.setBall(column, row, newBall);
                ++column;
            }
            --row;
        }
    }

    private void checkKeys(Input in) {
        if (in.isKeyPressed(25)) {
            this.game.setPaused(!this.game.isPaused());
        }
        if (in.isKeyPressed(49)) {
            this.newGame();
        }
        if (!this.game.isPaused()) {
            if (in.isKeyPressed(203)) {
                this.canon.moveLeft();
            }
            if (in.isKeyPressed(205)) {
                this.canon.moveRight();
            }
            if (in.isKeyPressed(208)) {
                this.addNewBall();
            }
            if (in.isKeyPressed(36)) {
                this.addNewJoker();
            }
            if (in.isKeyPressed(18)) {
                this.effects.setShowParticles(!this.effects.isShowParticles());
            }
            if (in.isKeyPressed(48)) {
                this.balls = this.balls.equals(this.balls1) ? this.balls2 : this.balls1;
                this.newGame();
                this.levelBall.setBallsSpriteSheet(this.balls);
            }
            if (in.isKeyPressed(33)) {
                this.game.setShowFPS(!this.game.isShowingFPS());
            }
        }
    }

    public void newGame() {
        System.out.println("ResetGame");
        this.game.setMouseGrabbed(true);
        this.reset.reset();
        this.ballsToMove.clear();
        this.addTopBalls();
        this.game.setPaused(false);
        this.game.getInput().clearControlPressedRecord();
        this.game.getInput().clearKeyPressedRecord();
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        this.newGame();
    }

    private void updateBalls() {
        int i = 0;
        while (i < this.ballsToMove.size()) {
            Ball b = this.ballsToMove.get(i);
            if (b.isReadyToKill()) {
                if (b.getReadyToKill() == 1) {
                    this.ballKiller.addBall(b);
                    this.effects.addEffect(b, 2);
                    b.kill(3);
                }
                if (b.getReadyToKill() == 4) {
                    this.shrinc.play();
                    b.kill(2);
                }
                if (b.getReadyToKill() == 2) {
                    this.effects.addEffect(b, 2);
                    int[] field = this.ballTable.getField(b);
                    this.ballTable.setBall(field[0], field[1], null);
                    this.ballsToMove.remove(i);
                }
            } else {
                b.update();
            }
            ++i;
        }
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = container;
        this.background = new Image("res/swing_background_b.jpg");
        this.multipl = new SpriteSheet(new Image("res/multiplicator_sp.jpg"), 189, 72);
        this.cannons = new SpriteSheet(new Image("res/cannons.png"), 72, 110);
        this.balls1 = new SpriteSheet(new Image("res/Balls1.png"), 48, 48);
        this.balls2 = new SpriteSheet(new Image("res/Balls2.png"), 48, 48);
        this.balls = this.balls1;
        this.font = new SpriteSheetFont(new SpriteSheet(new Image("res/numberFont_s19.png"), 15, 19), '.');
        this.ballFont = new SpriteSheetFont(new SpriteSheet(new Image("res/spriteFontBalls2.png"), 11, 16), '.');
        this.klack1 = new Sound("res/KLACK4.WAV");
        this.kran1 = new Sound("res/KRAN1.WAV");
        this.wup = new Sound("res/DREIER.WAV");
        this.wup.setMaxPlyingTime(1000L);
        this.shrinc = new Sound("res/SPRATZ2.WAV");
        this.warning = new Sound("res/ALARM1.WAV");
        this.music = new Music("res/music.mod");
        this.effects = new Effects();
        this.reset = new Reset();
        this.ballTable = new BallTable(248, 289);
        this.mechanics = new Mechanics(this.ballTable);
        this.timer = new Clock(this.font, 85, 718);
        this.canon = new Cannon(this.cannons, 248, 166, new Sound[]{this.kran1, this.warning});
        this.canon.setBallTable(this.ballTable);
        this.multiplicator = new HighScoreMultiplicator(59, 93, this.multipl);
        this.scoreCounter = new HighScoreCounter(this.font, 970, 106, this.multiplicator);
        this.seesawTable = new SeesawTable(this.font, this.ballTable);
        this.seesawTable.setPos(285, 723);
        this.levelBall = new Level(3, 25, 15, this.balls);
        this.levelBall.setFont(this.ballFont);
        this.ballCounter = new BallCounter(this.ballFont, 160, 22);
        this.ballCounter.setLevel(this.levelBall);
        this.ballKiller = new BallKiller(this.mechanics, this.scoreCounter);
        this.effects.setSound(this.wup, 2);
        this.reset.add(this.timer);
        this.reset.add(this.ballCounter);
        this.reset.add(this.levelBall);
        this.reset.add(this.scoreCounter);
        this.reset.add(this.ballTable);
        this.reset.add(this.effects);
        this.reset.add(this.multiplicator);
        this.gui.add(this.canon);
        this.gui.add(this.timer);
        this.gui.add(this.seesawTable);
        this.gui.add(this.levelBall);
        this.gui.add(this.ballCounter);
        this.gui.add(this.scoreCounter);
        this.gui.add(this.multiplicator);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(this.background, 0.0f, 0.0f);
        int i = 0;
        while (i < this.gui.size()) {
            this.gui.get(i).draw(g);
            ++i;
        }
        i = 0;
        while (i < this.ballsToMove.size()) {
            this.ballsToMove.get(i).draw(g);
            ++i;
        }
        this.effects.draw(g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input in = container.getInput();
        this.checkKeys(in);
        if (!this.music.playing()) {
            this.music.loop();
        }
        this.timer.tick();
        this.canon.update(delta);
        this.seesawTable.update();
        this.effects.update(delta);
        this.mechanics.checkOfFive();
        this.mechanics.checkOfThree();
        if (this.mechanics.checkHight()) {
            this.gameOver(container, game);
        }
        this.ballKiller.update(delta);
        this.updateBalls();
        this.multiplicator.update(delta);
        if (in.isKeyDown(1)) {
            if (this.getID() == 2) {
                game.enterState(1);
            } else {
                container.exit();
            }
        }
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        this.music.pause();
        container.setMouseGrabbed(false);
    }

    @Override
    public void reset() {
        this.newGame();
    }
}
