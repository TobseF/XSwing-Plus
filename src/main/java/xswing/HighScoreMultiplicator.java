/*
 * @version 0.0 28.04.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.MyTimer;
import lib.mylib.object.Resetable;
import lib.mylib.object.SObject;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

/**
 * Draws and returns the score multiplicatorSprites which can be set to the maximum of four and decrease automatically
 */
public class HighScoreMultiplicator extends SObject implements Resetable {

    private final SpriteSheet multiplicatorSprites;
    /**
     * The ScoreMuliplication value (for the HighScoreFormatter calculation)
     */
    private int multiplicator = 1;
    /**
     * Time in ms, before for one light burns out
     */
    private final int timerStep = 2500;

    private final MyTimer timer;
    private Sound score1x, score2x, score3x, score4x;

    public HighScoreMultiplicator(SpriteSheet multiplicatorSprites) {
        this.multiplicatorSprites = multiplicatorSprites;
        timer = new MyTimer(timerStep, true, false) {

            @Override
            protected void timerAction() {
                multiply();
            }
        };
    }

    /**
     * Sets the score multiplicatorSprites to 4
     */
    public void score() {
        timer.reset();
        multiplicator = 4;
        timer.start();
        playScore();
    }

    public void playScore() {
        if (score1x != null && score2x != null && score3x != null && score4x != null) {
            switch (multiplicator) {
                case 1:
                    score1x.play();
                    break;
                case 2:
                    score2x.play();
                    break;
                case 3:
                    score3x.play();
                case 4:
                    score4x.play();

                default:
                    break;
            }
        }
    }

    @Override
    public void update(int delta) {
        timer.update(delta);
    }

    private void multiply() {
        if (multiplicator > 1) {
            multiplicator--;
            playScore();
        } else {
            timer.reset();
        }
    }

    @Override
    public void render(Graphics g) {
        if (isVisible && multiplicator > 1) {// Only multiplicator states 2,3 & 4 are in the spritesheet
            g.drawImage(multiplicatorSprites.getSprite(0, 4 - multiplicator), x, y);
        }
    }

    /**
     * Returns the Score multiplication factor
     */
    public int getMulti() {
        return multiplicator;
    }

    @Override
    public void reset() {
        multiplicator = 1;
        timer.reset();
    }
}