package xswing;

import java.util.ArrayList;
import java.util.List;
import lib.SObject;
import lib.Sound;
import lib.SpriteSheet;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.loading.LoadingList;
import xswing.Ball;
import xswing.BallCounter;
import xswing.BallKiller;
import xswing.BallTable;
import xswing.Cannon;
import xswing.Clock;
import xswing.Effects;
import xswing.ExtraJoker;
import xswing.HighScoreCounter;
import xswing.HighScoreMultiplicator;
import xswing.Level;
import xswing.LoadingScreen;
import xswing.Mechanics;
import xswing.Reset;
import xswing.WeightTable;

public class XSwing
extends BasicGame {
    private static AppGameContainer game = null;
    String res = "res/";
    Image background;
    Image ball;
    SpriteSheet balls;
    SpriteSheet balls1;
    SpriteSheet balls2;
    Cannon canon;
    List<Ball> ballsToMove = new ArrayList<Ball>();
    List<SObject> gui = new ArrayList<SObject>();
    LoadingScreen loading;
    Clock timer;
    BallTable ballTable;
    Mechanics mechanics;
    WeightTable weightTable;
    BallCounter ballCounter;
    HighScoreCounter scoreCounter;
    HighScoreMultiplicator multiplicator;
    BallKiller ballKiller;
    Level levelBall;
    Reset reset;
    Effects effects;
    public Sound klack1;
    public Sound kran1;
    public Sound wup;
    public Sound shrinc;
    public Sound warning;
    private Music music;
    SpriteSheet ballImages;
    SpriteSheet multipl;
    SpriteSheet cannons;
    public SpriteSheetFont font;
    public SpriteSheetFont ballFont;
    int rasterX = 248;
    int rasterY = 289;
    int canonX = 248;
    int canonY = 166;
    boolean particles = true;
    boolean img2 = false;

    public XSwing() {
        super("XSwing");
    }

    public static void main(String[] args) {
        boolean fullsceen = true;
        try {
            game = new AppGameContainer(new XSwing());
            game.setMinimumLogicUpdateInterval(20);
            game.setMaximumLogicUpdateInterval(20);
            game.setDisplayMode(1024, 768, fullsceen);
            game.setClearEachFrame(false);
            game.setIcons(new String[]{"res/16.png", "res/32.png"});
            game.setMouseGrabbed(true);
            game.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
        this.loading = new LoadingScreen();
        LoadingList.setDeferredLoading(true);
        this.background = new Image(String.valueOf(this.res) + "swing_background_b.jpg");
        this.multipl = new SpriteSheet(new Image(String.valueOf(this.res) + "multiplicator_sp.jpg"), 189, 72);
        this.cannons = new SpriteSheet(new Image(String.valueOf(this.res) + "cannons.png"), 72, 110);
        this.balls1 = new SpriteSheet(new Image(String.valueOf(this.res) + "Balls1.png"), 48, 48);
        this.balls2 = new SpriteSheet(new Image(String.valueOf(this.res) + "Balls2.png"), 48, 48);
        this.balls = this.balls1;
        this.ball = new Image(String.valueOf(this.res) + "ball.png");
        this.font = new SpriteSheetFont(new SpriteSheet(new Image("res/NumerFont_s19.png"), 15, 19), '.');
        this.ballFont = new SpriteSheetFont(new SpriteSheet(new Image("res/spriteFontBalls2.png"), 11, 16), '.');
        this.ballImages = new SpriteSheet(this.balls1, 48, 48);
        this.effects = new Effects();
        this.klack1 = new Sound(String.valueOf(this.res) + "klack4.wav");
        this.kran1 = new Sound(String.valueOf(this.res) + "kran1.wav");
        this.wup = new Sound(String.valueOf(this.res) + "dreier.wav");
        this.wup.setMaxPlyingTime(1000L);
        this.shrinc = new Sound(String.valueOf(this.res) + "spratz2.wav");
        this.warning = new Sound(String.valueOf(this.res) + "ALARM1.wav");
        this.music = new Music("res/MOD.X-OCEANS GO EDITION.mod");
        this.reset = new Reset();
        this.ballTable = new BallTable(this.rasterX, this.rasterY);
        this.mechanics = new Mechanics(this.ballTable);
        this.timer = new Clock(this.font, 85, 718);
        this.canon = new Cannon(this.cannons, this.canonX, this.canonY, new Sound[]{this.kran1, this.warning});
        this.canon.setBallTable(this.ballTable);
        this.multiplicator = new HighScoreMultiplicator(59, 93, this.multipl);
        this.scoreCounter = new HighScoreCounter(this.font, 970, 106, this.multiplicator);
        this.weightTable = new WeightTable(this.font, this.ballTable);
        this.weightTable.setPos(285, 723);
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
        this.gui.add(this.weightTable);
        this.gui.add(this.levelBall);
        this.gui.add(this.ballCounter);
        this.gui.add(this.scoreCounter);
        this.gui.add(this.multiplicator);
        this.newGame();
    }

    public void update(GameContainer container, int delta) throws SlickException {
        if (!this.loading.isFinish()) {
            this.loading.update();
        } else {
            Input in = container.getInput();
            this.checkKeys(in);
            if (delta > 0) {
                if (!this.music.playing()) {
                    this.music.loop();
                }
                this.timer.tick();
                this.canon.update(delta);
                this.weightTable.update();
                if (this.particles) {
                    this.effects.update(delta);
                }
                this.mechanics.checkOfFive();
                this.mechanics.checkOfThree();
                this.ballKiller.update(delta);
                this.updateBalls();
                this.multiplicator.update(delta);
            }
        }
    }

    public void addNewBall() {
        Ball ball = this.getNewBall(this.canon.getX(), this.canon.getY());
        this.canon.releaseBall(ball);
        this.effects.addEffect(this.canon.getBall(), 3);
        this.ballCounter.count();
    }

    public Ball getNewBall(int x, int y) {
        Ball ball = new Ball(this.levelBall.getLevel(), x, y, this.balls);
        ball.setGrid(this.ballTable);
        ball.setFont(this.ballFont);
        ball.setCollsionSound(this.klack1);
        ball.setEffects(this.effects);
        this.ballsToMove.add(ball);
        return ball;
    }

    public void addNewJoker() {
        ExtraJoker ball = new ExtraJoker(33, this.canon.getX(), this.canon.getY());
        ball.setGrid(this.ballTable);
        ball.setCollsionSound(this.klack1);
        this.ballsToMove.remove(this.canon.getBall());
        this.ballsToMove.add(ball);
        this.canon.setBall(ball);
        this.effects.addEffect(this.canon.getBall(), 3);
        this.ballCounter.count();
    }

    public void addTopBalls() {
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

    public void render(GameContainer container, Graphics g) throws SlickException {
        if (!this.loading.isFinish()) {
            this.loading.draw(g);
        } else {
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
            if (this.particles) {
                this.effects.draw(g);
            }
        }
    }

    private void checkKeys(Input in) {
        if (in.isKeyDown(1)) {
            game.exit();
        }
        if (in.isKeyPressed(25)) {
            game.setPaused(!game.isPaused());
        }
        if (in.isKeyPressed(49)) {
            this.newGame();
        }
        if (!game.isPaused()) {
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
                boolean bl = this.particles = !this.particles;
            }
            if (in.isKeyPressed(48)) {
                this.balls = this.img2 ? this.balls1 : this.balls2;
                this.img2 = !this.img2;
                this.newGame();
                this.levelBall.setBallsSpriteSheet(this.balls);
            }
        }
    }

    public void newGame() {
        this.reset.reset();
        this.ballsToMove.clear();
        this.addTopBalls();
        game.setPaused(false);
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
}
