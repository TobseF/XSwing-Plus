/*
 * @version 0.0 27.04.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.EffectBlinking;
import lib.mylib.object.Resetable;
import lib.mylib.object.SObject;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import java.text.NumberFormat;

/**
 * Draws the HighScoreFormatter and the Bonus number. The position ist the left upper corner of
 * the first digit. Number will be automatically shifted to the left.
 */
public class HighScoreCounter extends SObject implements Resetable {

    private int score = 0;
    private final Font font;
    private int bonus = 0;
    private final HighScoreMultiplicator multiplicator;
    private final int letterLenght;
    private final EffectBlinking blinking;
    public int bonusLineSpace = 55;

    public HighScoreCounter(Font font, HighScoreMultiplicator multiplicator) {
        this.font = font;
        this.multiplicator = multiplicator;
        letterLenght = font.getWidth("0");
        blinking = new EffectBlinking(6, 500, true);
    }

    /**
     * Adds score to the HogScoreCounter
     */
    public void score(int score) {
        bonus = score * multiplicator.getMulti();
        this.score = this.score + bonus;
        multiplicator.score();
        blinking.reset();
    }

    @Override
    public void render(Graphics g) {
        String scoreS = NumberFormat.getInstance().format(score);
        font.drawString(x - (scoreS.length() - 1) * letterLenght, y, scoreS);
        if (blinking.getBlink() && !LocationController.isMultiplayer()) {
            String bonusS = NumberFormat.getInstance().format(bonus);
            font.drawString(x - (bonusS.length() - 1) * letterLenght, y + bonusLineSpace, bonusS);
        }
    }

    /**
     * Resets the score
     */
    public void reset() {
        score = 0;
        bonus = 0;
        blinking.reset();
    }

    /**
     * Returns the highscore
     */
    public int getScore() {
        return score;
    }

    public int getBonus() {
        return bonus;
    }

    @Override
    public void update(int delta) {
        blinking.update(delta);
    }
}